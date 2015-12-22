<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<script type="text/javascript" src="${ctx}/js/excel.js"></script>
<html>
<head>
<title>ExcelDemo</title>
</head>

<body>

	<script>
		$(function(){
			
			$("#filebox").filebox({
			    buttonText: '选择文件',
			    buttonAlign: 'right'
			})
			
            $("#excelUploadForm").form({
            	onSubmit: function(){ 
			    	loading("导入中..");
			    }, 
        		success:function(data){
        			data = $.parseJSON(data)
        			loaded()
					if(data.flag){
						parent.$.modalDialog.handler.dialog('close');
						parent.$.modalDialog.dg.datagrid('reload');
						showmsg("上传成功")
					}else{
						if(data.excelempty){
							alerterror("数据为空")
							return false
						}
						alerterror("上传失败")
					}
			    }
            });

			$('#btn-excel-ok').click(function(){
				$("#excelUploadForm").submit();
			})
		})
		
	</script>	
	
	<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="excelUploadForm" action = "${ctx }/store/importExcel" method="post" align="center" enctype="multipart/form-data" style="margin-top: 60px">
			<input id='filebox' name="file" style="width: 200px;"/>
			<input id="excel-clazz" type='hidden' name="clazz" value="tianYaPostServiceImpl">
			<input id="excel-method" type='hidden' name="method" value="importExcel">
			<input id="excel-modual" type='hidden' name="module" value="lucene">
			<input id="excel-tn" type='hidden' name="tn" value="tianya_post">
			<input id="excel-pojo" type='hidden' name="pojo" value="TianYaPost">
		</form>
	</div>
</div>
	
	
	
</body>


</html>