<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<form id="pswChangeForm">
	<table style="margin-left: 100;margin-top: 20px">
		<tr>
			<td><input id="old-psw" type="password" name='oldpsw'></td>
		</tr>
		<tr>
			<td><input id="new-psw" type="password" style="margin-top: 15px"></td>
		</tr>
		<tr>
			<td><input id="new-psw-check" type="password" name='newpsw'></td>
		</tr>
	</table>
</form>
<script type="text/javascript">
	$("#btn-changepsw").click(function() {
		if ($("#pswChangeForm").form('validate')) {
			var oldpsw = $('#old-psw').val();
			var newpsw = $('#old-psw').val();
			if (oldpsw.trim() == "" || newpsw.trim == "") {
				$.messager.alert("提醒", "请输入密码", "info");
				return false;
			}
			$.ajax({
				url : '${ctx}/system/changePsw',
				type : 'post',
				data : $("#pswChangeForm").serialize(),
				dataType : 'json',
				success : function(r) {
					if (r && r.flag) {
						$.messager.show({
							msg:r.msg,
							title:'提示'
						});
						parent.$.modalDialog.handler.dialog('close');
					} else {
						$.messager.alert("提醒", r.msg, "error");
					}
				}

			})
		}

	})
	$(function() {

		$('#old-psw').textbox({
			height : 25,
			prompt : "旧密码",
			validType : [ 'length[6,10]' ]
		})

		$('#new-psw').textbox({
			height : 25,
			iconCls : 'icon-lock',
			iconAlign : 'right',
			prompt : "新密码",
			validType : [ 'length[6,10]' ]
		})

		$('#new-psw-check').textbox({
			height : 25,
			iconCls : 'icon-lock',
			iconAlign : 'right',
			prompt : "确认密码",
			validType : [ "eqPwd['#new-psw']" ]
		})
		
		$("#old-psw").textbox().next("span").find("input").focus();
	})
</script>