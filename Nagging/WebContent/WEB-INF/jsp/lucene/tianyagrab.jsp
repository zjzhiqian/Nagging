<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>
<head>
<title>数据抓取页面</title>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true" style="margin-top: 0px">
	
		<div data-options="region:'north'" style="height:30px">
		   <shiro:hasPermission name="lucene:tianyagrab">			
			<a id="data-grab" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" style="height: 25px">开始抓取</a>
		   </shiro:hasPermission>
		   <shiro:hasPermission name="lucene:tianyaindex">			
			<a id="data-index-tianya" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" style="height: 25px">生成天涯索引</a>
		   </shiro:hasPermission>
		   <shiro:hasPermission name="lucene:tianyaindex">			
			<a id="data-index-taobao" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" style="height: 25px">生成淘宝索引</a>
		   </shiro:hasPermission>
		</div>
		<div data-options="region:'center'" style="overflow: hidden;">
			<div style="margin-left: 50px;margin-top: 50px">数据已抓取数量 : <span id="data-num">0</span> </div>
		</div>
	</div>
	

</body>

<script type="text/javascript">
	var JobId="";
	$("#data-grab").click(function(){
		JobId=setInterval(getDataNum,800);
		$(this).linkbutton("disable");
		$.ajax({
			url:"${ctx}/lucene/tianyaBegin",
			success:function(r){
				if(r&&r.flag){
					showmsg(r.msg)
					$("#data-num").html("");
				}else{
					showmsg(r.msg)
					$("#data-num").html("0")
				}
			}
		})
		return false;
	})
	
	function getDataNum(){
		$.ajax({
			url:"${ctx}/lucene/tianyaShowNum",
			success:function(r){
				if(r&&r.flag){
					$("#data-num").html(r.msg)
				}else{
					showmsg(r.msg)
					$("#data-num").html(r.msg)
					clearInterval(JobId);
					$("#data-grab").linkbutton("enable");
				}
			}
		})
	}
	
	
	//索引(TianYa)
	$("#data-index-tianya").click(function(){
		loading("索引生成中,请稍等")
		$.ajax({
			url:"${ctx}/lucene/tianyaPostIndex",
			success:function(r){
				loaded()
				if(r&&r.flag){
					showmsg(r.msg)
				}else{
					alerterror(r.msg)
				}
			}
		})
	})
	
	//索引(TaoBao)
	$("#data-index-taobao").click(function(){
		loading("索引生成中,请稍等")
		$.ajax({
			url:"${ctx}/lucene/taobaoPostIndex",
			success:function(r){
				loaded()
				if(r&&r.flag){
					showmsg(r.msg)
				}else{
					alerterror(r.msg)
				}
			}
		})
	})
	
	

</script>
</html>