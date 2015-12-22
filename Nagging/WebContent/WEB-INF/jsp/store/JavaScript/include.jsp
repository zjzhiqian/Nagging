<%@ page contentType="text/html; charset=UTF-8"%>


<script type="text/javascript">
	function execute(){
		var xmp = document.getElementsByTagName('xmp')
		var text = xmp[0].innerText
		var script = document.createElement("script");
		script.type = "text/javascript";
		script.text = text
		document.getElementsByTagName('body')[0].appendChild(script);
	}
</script>

<body id = 'body'>
	<input type = 'button' value = '执行JS'  style="margin-top: 20px;margin-left: 400px"   onclick = 'execute()'/>
</body>