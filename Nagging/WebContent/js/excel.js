$.exportExcel = function exportExcel(opt){
	
	var mustParam = 0;
	
	var excelForm=$("<form id='extract' style='display:none' target = '' method = 'post' action = '' > ");//定义一个form表单
	var exceInput=$("<input  type = 'hidden' name =  'ExcelTitle'  value = '' />");
	var exceInput2=$("<input  type = 'hidden' name =  'ExcelTable'  value = '' />");
	
	excelForm.append(exceInput);
	excelForm.append(exceInput2);
	
	if(opt.url){
		excelForm.attr("action",opt.url);
		mustParam++
	}
	if(opt.title){
	   exceInput.attr("value",opt.title)
	   mustParam++;
	}
	if(opt.tb){
	   exceInput2.attr("value",opt.tb)
	   mustParam++;
	}
	
	if(mustParam>=3){
		$("body").append(excelForm);
		excelForm.submit()
		excelForm.remove()
		
	}
	
	
}