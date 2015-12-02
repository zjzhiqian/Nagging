package com.hzq.store.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hzq.common.entity.QueryCondition;
import com.hzq.common.util.CommonUtils;

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
	
	public static <T> ResponseEntity<byte[]> Exeport(List<T> list,QueryCondition con) throws IOException{
		LinkedHashMap<String,String> gridMap=SYS_MAP.get(con.getCondition().get("ExcelTable"));
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
			T obj = list.get(rowNum);
			Map<String, Object> dataMap = CommonUtils.describe(obj,true);
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
				} else {
					cell.setCellValue("");
				}

			}
		}
		//自适应 列宽
//		int i = 0;
//		for (Entry<String, String> entry : gridMap.entrySet()) {
//			sheet.autoSizeColumn(i);
//			int maxColumnWidth = sheet.getColumnWidth(i);
//			sheet.setColumnWidth(i, maxColumnWidth + 500);
//			titleCell++;
//		}
		book.write(os);
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment;filename=\""+new String((con.getCondition().get("ExcelTitle")+".xls").getBytes("gb2312"), "ISO8859-1" )+"\"");
        HttpStatus statusCode = HttpStatus.OK;
		return new ResponseEntity<byte[]>(os.toByteArray(), headers, statusCode);
	}
	
	
	

}
