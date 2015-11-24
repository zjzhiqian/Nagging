<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">



a{
	text-decoration: none
}
a:hover{
	text-decoration: none
}
.info{
	margin-top:6px;
	color: #999999;
	font-size: 10px;
	line-height: 20px;
}
#search {
	text-align: left;
	margin-left:30px;
	margin-top:50px;
	position: relative;
}

#count{
	margin-left: 40px;
	padding-top: 20px;
	color: #999999;
	font-size: 13px;
}

.content{
	margin-left: 30px;
	padding-top: 10px;
}

.title{
	padding-top: 20px;
	
}

.detail{
	font-size: 12px;
	line-height: 20px;
	background-color: #f1f1f1;
	width: 700px;
}


li {
	text-align: left;
	list-style: none;
}

.clickable {
	cursor: default;
}

.highlight {
	background-color: #9ACCFB;
}
</style>

<script type="text/javascript">
	$(function() {
		$autocomplete = $("<div id='suggest'></div>").hide().insertAfter('#submit');
		var $search = $('#search');
		var $searchInput = $search.find('#search-text');
		$searchInput.attr('autocomplete', 'off');
		var clear = function() {
			$autocomplete.empty().hide();
		};
		$searchInput.blur(function() {
			setTimeout(clear, 500);
		});
		//下拉列表中高亮的项目的索引，当显示下拉列表项的时候，移动鼠标或者键盘的上下键就会移动高亮的项目，想百度搜索那样 
		var selectedItem = null;
		//timeout的ID 
		var timeoutid = null;
		//设置下拉项的高亮背景 
		var setSelectedItem = function(item) {
			//更新索引变量 
			selectedItem = item;
			//按上下键是循环显示的，小于0就置成最大的值，大于最大值就置成0 
			if (selectedItem < 0) {
				selectedItem = $autocomplete.find('li').length - 1;
			} else if (selectedItem > $autocomplete.find('li').length - 1) {
				selectedItem = 0;
			}
			//首先移除其他列表项的高亮背景，然后再高亮当前索引的背景 
			$autocomplete.find('li').removeClass('highlight').eq(selectedItem).addClass('highlight');
		};
		var ajax_request = function() {
			if($searchInput.val()==""){
				return false
			}
			//ajax服务端通信 
			$.ajax({
				'url' : '${ctx}/lucene/tianyaSuggestQuery', //服务器的地址 
				'data' : {'content' : $searchInput.val()
				}, //参数 
				'dataType' : 'json', //返回数据类型 
				'type' : 'POST', //请求类型 
				'success' : function(data) {
					if (data.length) {
						//遍历data，添加到自动完成区 
						$.each(data, function(index, perdata) {
							var term=perdata.key+""
							//创建li标签,添加到下拉列表中
							$('<li></li>').html(term).appendTo($autocomplete).addClass('clickable')
								.hover(function() {
											//下拉列表每一项的事件，鼠标移进去的操作 
											$(this).siblings().removeClass('highlight');
											$(this).addClass('highlight');
											selectedItem = index;
								},function() {
										//下拉列表每一项的事件，鼠标离开的操作 
										$(this).removeClass('highlight');
										//当鼠标离开时索引置-1，当作标记 
										selectedItem = -1;
								})
								.click(function() {
										term=term.replaceAll("<font color=\"blue\">","")
										term=term.replaceAll("</font>","")
										$searchInput.val(term);
										sendQueryAjax()
										//清空并隐藏下拉列表 
										$autocomplete.empty().hide();
								});
						});//事件注册完毕 
						//设置下拉列表的位置，然后显示下拉列表 
						var ypos = $searchInput.position().top;
						var xpos = $searchInput.position().left;
						$autocomplete.css('width', $searchInput.css('width'));
						$autocomplete.css({
							'position' : 'relative',
							'left' : xpos + "px",
							'top' : ypos + "px"
						});
						setSelectedItem(0);
						//显示下拉列表 
						$autocomplete.show();
					}
				}
			});
		};
		//对输入框进行事件注册 
		$searchInput.keyup(function(event) {
			//字母数字，退格，空格 
			if (event.keyCode > 40 || event.keyCode == 8|| event.keyCode == 32) {
				//首先删除下拉列表中的信息 
				$autocomplete.empty().hide();
				clearTimeout(timeoutid);
				timeoutid = setTimeout(ajax_request, 100);
			} else if (event.keyCode == 38) {
				//上 
				//selectedItem = -1 代表鼠标离开 
				if (selectedItem == -1) {
					setSelectedItem($autocomplete.find('li').length - 1);
				} else {
					//索引减1 
					setSelectedItem(selectedItem - 1);
				}
				event.preventDefault();
			} else if (event.keyCode == 40) {
				//下 
				//selectedItem = -1 代表鼠标离开 
				if (selectedItem == -1) {
					setSelectedItem(0);
				} else {
					//索引加1 
					setSelectedItem(selectedItem + 1);
				}
				event.preventDefault();
			}
		}).keypress(function(event) {
				//enter键 
				if (event.keyCode == 13) {
					//列表为空或者鼠标离开导致当前没有索引值 
					if ($autocomplete.find('li').length == 0
							|| selectedItem == -1) {
						return;
					}
					$searchInput.val($autocomplete.find('li').eq(selectedItem).text());
					$autocomplete.empty().hide();
					event.preventDefault();
				}
		}).keydown(function(event) {
			//esc键 
			if (event.keyCode == 27) {
				$autocomplete.empty().hide();
				event.preventDefault();
			}
		});
		//注册窗口大小改变的事件，重新调整下拉列表的位置 
		$(window).resize(function() {
			var ypos = $searchInput.position().top;
			var xpos = $searchInput.position().left;
			$autocomplete.css('width', $searchInput.css('width'));
			$autocomplete.css({
				'position' : 'relative',
				'left' : xpos + "px",
				'top' : ypos + "px"
			});
		});
	});
	
	
	
	
</script>

</head>
<body>
	<script type="text/javascript">
		//搜索请求
		function sendQueryAjax(){
			$("#count").empty().hide()
			$(".content").empty().hide()
			$autocomplete.empty().hide();
			var content = $("#search-text").val()
			$.ajax({
				url:"${ctx}/lucene/tianyaIndexQuery/2",
				data:{'content':content,'title':content,'rows':20,'page':1},
				success:function(r){
					if(r.o.flag){
						console.log(r)
						str=""
						str+= $.formatString("<div id=\"count\">为你找到相关结果<span>{0}</span>个 用时{1}ms</div>",r.total,r.o.msg)
						str+="<div class=\"content\">";
						for(var i=0;i<r.rows.length;i++){
							str+=$.formatString("<div class=\"title\"><a href='{0}' target='_blank'>{1}</a><div class='info'><div >发帖人:{2}</div><div >发帖时间:{3}</div></div><div class='detail'>{4}</div></div>",r.rows[i].url,r.rows[i].title,r.rows[i].adduserName,r.rows[i].addTime,r.rows[i].content);
						}
						str+="</div>";
						$(str).insertAfter('#suggest');
					}else{
						showmsg(r.o.msg)
					}
				}
			})
		}
	
		$(function(){
			//搜索
			$("#submit").click(function(){
				sendQueryAjax()
			})	
		})
	
	</script>


	<div id="search">
		<label for="search-text" style="margin-right: 50px">输入关键词</label>
		<input type="text" id="search-text" name="search-text" style="width: 400px;"/> 
		<input type="button" id="submit" value="搜索" />
	</div>
	
	
<!-- 
	<div id="count">
		为你找到相关结果<span>32058</span>个
	</div>
	<div class="content">
		<div class="title">
			<a href="">现在已到了相互砸饭碗的时代！</a>
			<div class="info">
				<div >发帖人:小是是</div>
				<div >发帖时间:2015-05-10</div>
			</div>
			<div class="detail">
				内容 :的我我我我我我我我我我我我签订我我我签订的我我我我我我我我我我我我签订我我我签订签订我我我签订
			</div>
		</div>
	</div>
 -->
</body>
</html>