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

$.importExcel = function importExcel(opt){
	parent.$.modalDialog({
		title : 'Excel导入',
		width : 400,
		height : 200,
		href : opt.url,
		buttons : [ {
			id:'btn-excel-ok',
			text : '确定',
			width : 100 ,
			handler :function(){
				if(opt.datagrid){
					parent.$.modalDialog.dg=opt.datagrid;
				}
			}
		}],
		onLoad:function(){
			$("#excel-clazz").val(opt.clazz);
			$("#excel-method").val(opt.method);
			$("#excel-modual").val(opt.clazz);
			$("#excel-tn").val(opt.clazz);
			$("#excel-pojo").val(opt.pojo);
		}
	});
	
	
	
	
}
