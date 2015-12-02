$.exportExcel = function exportExcel(opt){
	
	var mustParam = 0;
	
	var form=$("<form id='extract' style='display:none' target = '' method = 'post' action = '' > ");//定义一个form表单
	var input1=$("<input  type = 'hidden' name =  'ExcelTitle'  value = '' />");
	var input2=$("<input  type = 'hidden' name =  'ExcelTable'  value = '' />");
	
	form.append(input1);
	form.append(input2);
	
	if(opt.url){
		form.attr("action",opt.url);
		mustParam++
	}
	if(opt.title){
	   input1.attr("value",opt.title)
	   mustParam++;
	}
	if(opt.tb){
		
	   console.log(opt.tb)
	   input2.attr("value",opt.tb)
	   mustParam++;
	}
	
	if(mustParam>=3){
		$("body").append(form);
		$("#extract").submit()
	}
	
	
}