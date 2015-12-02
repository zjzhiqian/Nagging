package com.hzq.store.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ExcelUtils {
	private static final Map<String,LinkedHashMap<String,String>> SYS_MAP=new HashMap<String,LinkedHashMap<String,String>>();
	static {
		List<String> titleList = Arrays.asList("内容","超链接","标题","添加人","最后回复时间");
		List<String> fieldList = Arrays.asList("content","url","title","adduserName","lastReplyTime");
		LinkedHashMap<String,String> gridMap=new LinkedHashMap<String,String>();
		for(int i = 0 ;i<titleList.size();i++){
			gridMap.put(titleList.get(i), fieldList.get(i));
		}
		SYS_MAP.put("tianya_post", gridMap);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> ResponseEntity<byte[]> Exeport(List<T> list,String title,String tableName) throws IOException{
		LinkedHashMap<String,String> gridMap=SYS_MAP.get(tableName);
		//输出流
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		
		
		HSSFWorkbook book = new HSSFWorkbook();
		HSSFSheet sheet = book.createSheet("sheet1");
		
		//表头 
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
		//数据
		for (int rowNum = 0; rowNum < list.size(); rowNum++) { //一条数据
			T t = list.get(rowNum);
			Map<Object, Object> dataMap = null;
			try {
				 //TODO 自己写一个Object TO Map
				dataMap = BeanUtils.describe(t);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//所有字段这样都是String的
			HSSFRow row = sheet.createRow(rowNum+1);
			int cellNum = 0;
			for (Entry<String, String> entry : gridMap.entrySet()) {  //一条数据的列
				Object object = dataMap.get(entry.getValue());
				HSSFCell cell = row.createCell(cellNum++);
				if (null != object) {
					if (object instanceof String) {
						cell.setCellValue((String) object);
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					} 
//					else if (object instanceof BigDecimal) {
//						BigDecimal bignum = (BigDecimal) object;
//						cell.setCellValue(bignum+"");
//						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//					} else if (object instanceof Date) {
//						Date date = (Date) object;
//						cell.setCellValue("'"+sdf.format(date));
//					} else if (object instanceof Integer) {
//						Integer intnum = (Integer) object;
//						cell.setCellValue(intnum);
//					}else if (object instanceof Long){
//						Long longnum=(Long)object;
//						cell.setCellValue(longnum);
//					}
				} else {
					cell.setCellValue("");
				}

			}
		}
		
		//自适应 列宽
		int i = 0;
		for (Entry<String, String> entry : gridMap.entrySet()) {
			sheet.autoSizeColumn(i);
			int maxColumnWidth = sheet.getColumnWidth(i);
			if(maxColumnWidth>5000){
				maxColumnWidth=5000;
			}
			sheet.setColumnWidth(i, maxColumnWidth + 500);
			titleCell++;
		}
		
		
		
		
		book.write(os);
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment;filename=\""+new String((title+".xls").getBytes("gb2312"), "ISO8859-1" )+"\"");
        HttpStatus statusCode = HttpStatus.OK;
		return new ResponseEntity<byte[]>(os.toByteArray(), headers, statusCode);
	}
	
	
	

}
