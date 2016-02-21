<!DOCTYPE html PUBLIC "-//W3C//Dtd html 4.01 transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<html>
<head>
<title>平台</title>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/style.css">
<link rel="stylesheet" type="text/css" href="${ctx}/styles/login.css">
<script language="JavaScript">
	if (window != top)
		top.location.href = location.href;
</script>
<style type="text/css">
.indexbtnlink {
	cursor: hand;
	display: block;
	width: 80px;
	height: 29px;
	float: left;
	margin: 12px 28px 12px auto;
	line-height: 30px;
	background: url('${ctx}/images/login/btnbg.jpg') no-repeat;
	font-size: 14px;
	color: #fff;
	font-weight: bold;
	text-decoration: none;
}
</style>

<script type="text/javascript">
	//刷新验证码
	function randomcode_refresh() {
		var time = new Date().getTime();
		$("#randomcode_img").attr("src", "${ctx}/validatecode.jsp?t=" + time);
	}

	//重置btn
	function btnReset() {
		$("#password").val('')
		$("#username").val('')
		$("#randomcode").val('')
		randomcode_refresh();
	}

	//登录提示方法
	function loginsubmit() {
		//避免遮挡验证码
		$("#randomcode").validatebox({
			required : true,
			validType : {
				length : [ 4, 4 ]
			}
		})
		if ($("#loginform").form('validate')) {
			$("#loginform").submit();
			parent.$.messager.progress({
				title : '提示',
				text : '登录中，请稍后....'
			});
		}
	}
</script>
</head>
<body style="background: #f6fdff url(${ctx}/images/login/bg1.jpg) repeat-x;">
	<form id="loginform" name="loginform" action="${ctx}/login" method="post">
		<div class="logincon">
			<div class="cen_con">
				<img alt="" src="${ctx}/images/login/bg2.png">
			</div>

			<div class="tab_con">
				<span style="color: red">${loginErrorMsg }</span>
				<table class="tab" border="0" cellSpacing="6" cellPadding="8">
					<tbody>
						<tr>
							<td>用户名：</td>
							<td colSpan="2"><input type="text" id="username" name="username" style="width: 130px" /></td>
						</tr>
						<tr>
							<td>密 码：</td>
							<td><input type="password" id="password" name="password" style="width: 130px" /></td>
						</tr>
						<tr>
							<td>验证码：</td>
							<td><input id="randomcode" name="randomcode" size="4" /> <img id="randomcode_img" onclick="randomcode_refresh()" src="${ctx}/validatecode.jsp" alt="" width="56" height="20" align='absMiddle' /> <a href=javascript:randomcode_refresh()>刷新</a></td>
						</tr>
						<tr>
							<td colSpan="2" align="center"><input type="button" class="indexbtnlink" onclick="loginsubmit()" value="登&nbsp;&nbsp;录" /> <input id="btn-reset" type="reset" onclick="btnReset()" class="indexbtnlink" value="重&nbsp;&nbsp;置" /></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</form>
</body>
<script type="text/javascript">
	$(function() {

		//判断是否被踢出来
		var iskicked = "${iskicked}";
		if (iskicked) {
			alert("账号异地登陆")
			top.location.href = "${ctx}/login"
		}

		if (browser.isIe) {
			$.messager.show({
				title : '警告',
				msg : '为正常使用系统,建议您使用谷歌或者火狐浏览器！',
				timeout : 1000 * 30
			});
		}

		var key = new Date().getTime() + "";
		$.cookie(key, 'the_value');
		var val = $.cookie(key);
		if (val == null) {
			$.messager.alert('警告', '为正常使用系统,请开启Cookie', 'error');
		}
		$.cookie(key, null);

		$("#username").validatebox({
			required : true,
			validType : {
				length : [ 0, 10 ]
			}
		})

		$("#password").validatebox({
			required : true,
			validType : {
				length : [ 0, 10 ]
			}
		})
		$("#username").on('keyup', function(event) {
			if (event.keyCode == '13') {
				$("#password").focus();
			}
		})
		$("#password").on('keyup', function(event) {
			if (event.keyCode == '13') {
				$("#randomcode").focus();
			}
		})
		$("#randomcode").on('keyup', function(event) {
			if (event.keyCode == '13') {
				loginsubmit()
			}
		})
		$("#username").focus();

		$("#randomcode").val("1111");
		$("#username").val("123");
		$("#password").val("123456");
		//loginsubmit();

	})
</script>
</html>
