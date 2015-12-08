package com.hzq.store.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hzq.common.entity.QueryCondition;
import com.hzq.common.util.Utils;

public class ExcelUtils {
	public final static Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();

	static {
		FILE_TYPE_MAP.put("xls", "D0CF11E0"); // MS Excel 2003
		FILE_TYPE_MAP.put("xlsx", "504B0304140006"); // MS Excel 2007
	}

	private static final Map<String, LinkedHashMap<String, String>> SYS_MAP = new HashMap<String, LinkedHashMap<String, String>>();
	static {
		List<String> titleList = Arrays.asList("内容", "超链接", "标题", "添加人", "最后回复时间");
		List<String> fieldList = Arrays.asList("content", "url", "title", "adduserName", "lastReplyTime");
		LinkedHashMap<String, String> gridMap = new LinkedHashMap<String, String>();
		for (int i = 0; i < titleList.size(); i++) {
			gridMap.put(titleList.get(i), fieldList.get(i));
		}
		SYS_MAP.put("tianya_post", gridMap);
	}
	
	
	/**
	 * Excel下载
	 * @param list
	 * @param con
	 * @return
	 * @throws IOException
	 * @author huangzhiqian
	 * @date 2015年12月3日
	 */
	public static <T> ResponseEntity<byte[]> Exeport(List<T> list, QueryCondition con) throws IOException {
		LinkedHashMap<String, String> gridMap = SYS_MAP.get(con.getCondition().get("ExcelTable"));
		// 输出流
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		HSSFWorkbook book = new HSSFWorkbook();
		HSSFSheet sheet = book.createSheet("sheet1");
		// 表头
		HSSFRow titleRow = sheet.createRow(0);
		int titleCell = 0;
		for (Entry<String, String> entry : gridMap.entrySet()) {
			HSSFCell cell = titleRow.createCell(titleCell);
			cell.setCellValue(entry.getKey());
			sheet.autoSizeColumn(titleCell);
			int maxColumnWidth = sheet.getColumnWidth(titleCell);
			sheet.setColumnWidth(titleCell, maxColumnWidth + 500);
			titleCell++;
		}
		// 数据
		for (int rowNum = 0; rowNum < list.size(); rowNum++) { // 一条数据
			T obj = list.get(rowNum);
			Map<String, Object> dataMap = Utils.describe(obj, true);
			// 所有字段这样都是String的
			HSSFRow row = sheet.createRow(rowNum + 1);
			int cellNum = 0;
			for (Entry<String, String> entry : gridMap.entrySet()) { // 一条数据的列
				Object object = dataMap.get(entry.getValue());
				HSSFCell cell = row.createCell(cellNum++);
				if (null != object) {
					if (object instanceof String) {
						cell.setCellValue((String) object);
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					}
				} else {
					cell.setCellValue("");
				}

			}
		}
		// 自适应 列宽
		// int i = 0;
		// for (Entry<String, String> entry : gridMap.entrySet()) {
		// sheet.autoSizeColumn(i);
		// int maxColumnWidth = sheet.getColumnWidth(i);
		// sheet.setColumnWidth(i, maxColumnWidth + 500);
		// titleCell++;
		// }
		book.write(os);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment;filename=\"" + new String((con.getCondition().get("ExcelTitle") + ".xls").getBytes("gb2312"), "ISO8859-1") + "\"");
		HttpStatus statusCode = HttpStatus.OK;
		return new ResponseEntity<byte[]>(os.toByteArray(), headers, statusCode);
	}
	
	
	
	/**
	 * 从Excel导入数据
	 * @param byteArray
	 * @param tn 表名
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年12月3日
	 */
	public static List<Map<String, Object>> getExcelData(byte[] byteArray, String tn) throws Exception {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String fileType = getFileTypeByStream(byteArray);
		if(StringUtils.isEmpty(fileType)){ //无法文件类型 返回
			return result;
		}
		List<String> paramList = getExcelFirstRow(byteArray,fileType);
		
		paramList = ExcelUtils.transFerFirstRowtoField(paramList, tn);
		
		Workbook hssfWorkbook = null;
		InputStream is =new ByteArrayInputStream(byteArray);
		if ("xls".equals(fileType)) {
			hssfWorkbook = new HSSFWorkbook(is);
		} else if ("xlsx".equals(fileType)) {
			hssfWorkbook = new XSSFWorkbook(is);
		}
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if (hssfSheet == null) {
				continue;
			}
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				Map<String, Object> map = new HashMap<String, Object>();
				Row hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				for (int cellNum = 0; cellNum < hssfRow.getLastCellNum(); cellNum++) {
					Cell hssfCell = hssfRow.getCell(cellNum);
					if (hssfCell == null || getValue(hssfCell) == null) {
						continue;
					}
					if (paramList.size() > cellNum && !"null".equals(paramList.get(cellNum))) {
						// 当列为id列时，读取的数字不取小数位
						if (null != paramList.get(cellNum) && paramList.get(cellNum).equals("id")) {
							Object o = getValue(hssfCell);
							if (o instanceof String) {
								map.put(paramList.get(cellNum), o);
							} else {
								BigDecimal dou = new BigDecimal(o.toString());
								Integer integer = dou.intValue();
								map.put(paramList.get(cellNum), integer + "");
							}
						} else {
							if (null != getValue(hssfCell) && !"".equals(getValue(hssfCell))) {
								map.put(paramList.get(cellNum), getValue(hssfCell));
							}
						}
					}
				}
				if (!map.isEmpty()) {
					result.add(map);
				}
			}
		}
		return result;
	}
	
	/**
	 * 获取首列Excel值(表字段的描述)
	 * @param byteArray
	 * @param fileType
	 * @return
	 * @throws IOException
	 * @author huangzhiqian
	 * @date 2015年12月3日
	 */
	public static List<String> getExcelFirstRow(byte[] byteArray,String fileType) throws IOException {
		InputStream  is = null ;
		try {
			List<String> fieldList = new ArrayList<String>();
			Workbook hssfWorkbook = null;
			is =new ByteArrayInputStream(byteArray);
			if ("xls".equals(fileType)) {
				hssfWorkbook = new HSSFWorkbook(is);
			} else if ("xlsx".equals(fileType)) {
				hssfWorkbook = new XSSFWorkbook(is);
			}
			for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
				Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
				if (hssfSheet == null) {
					continue;
				}
				Row firstRow = hssfSheet.getRow(0);
				if (firstRow == null) {
					continue;
				}
				for (int firstRowCellNum = 0; firstRowCellNum < firstRow.getLastCellNum(); firstRowCellNum++) {
					Cell hssfCell = firstRow.getCell(firstRowCellNum);
					if (hssfCell == null) {
						continue;
					}
					if (null != getValue(hssfCell)) {
						fieldList.add(getValue(hssfCell).toString());
					} else {
						fieldList.add("null");
					}
				}
				break;
			}
			return fieldList;
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	/**
	 * 获取文件类型
	 * @param b
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月3日
	 */
	public static String getFileTypeByStream(byte[] byteArray) {
		String filetypeHex = String.valueOf(getFileHexString(byteArray));
		Iterator<Entry<String, String>> entryiterator = FILE_TYPE_MAP.entrySet().iterator();
		while (entryiterator.hasNext()) {
			Entry<String, String> entry = entryiterator.next();
			String fileTypeHexValue = entry.getValue();
			if (filetypeHex.toUpperCase().startsWith(fileTypeHexValue)) {
				return entry.getKey();
			}
		}
		return null;
	}

	/**
	 * 获取文件十六进制数字字符串
	 * @param byteArray
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月3日
	 */
	private static String getFileHexString(byte[] byteArray) {
		StringBuilder stringBuilder = new StringBuilder();
		if (byteArray == null || byteArray.length <= 0) {
			return null;
		}
		for (int i = 0; i < byteArray.length; i++) {
			int v = byteArray[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * excel表格中各单元格的值
	 * @param hssfCell
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月3日
	 */
	private static Object getValue(Cell hssfCell) {
		if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
			return hssfCell.getBooleanCellValue();
		} else if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			if (HSSFDateUtil.isCellDateFormatted(hssfCell)) {
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				return sf.format(hssfCell.getDateCellValue());
			} else {
				hssfCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				return hssfCell.getStringCellValue();
			}
		} else if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
			System.out.println(hssfCell);
			return hssfCell.getNumericCellValue();
		} else if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
			return hssfCell.getStringCellValue().trim();
		} else if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
			return null;
		} else {
			return hssfCell.getErrorCellValue();
		}
	}
	
	public static List<String> transFerFirstRowtoField(List<String> fileColumn, String tn) {
		Map<String, String> gridMap = SYS_MAP.get(tn);
		List<String> rsList = new ArrayList<String>();
		for (String field : fileColumn) {
			if (gridMap.get(field) != null) {
				rsList.add(gridMap.get(field));
			}
		}
		return rsList;
	}

}
