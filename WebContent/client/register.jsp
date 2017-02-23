<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
	table {
		padding:100px 0px 0px 300px;
	}
</style>	
<script type="text/javascript" src="../jquery-1.4.2.min.js"></script>
<script type="text/javascript">
	function check(){
	    var user_name = new Array("Username","Password","Phone","Email","Address");
		var user_check = new Array(/^\w{5,8}/,/^\w+/,/^((13[0-9])|(15[^4])|(18[0.2.3,5-9])|(17[0-8]!147)\d(8)$)/,
				/(([a-z0-9A-Z]+[-|\.]?)+[a-z0-9A-Z]?@([a-z0-9A-Z]+\.)+[a-zA-Z]{2,})/,/^[a-zA-Z]+/);
		var user_mess = new Array("要求5~8个字母\n","","要求11位手机号\n","请输入正确email地址\n","请输入正确地址");
		var message = "";
		for(var i=0; i<user_name.length; i++){
			var ct = document.getElementsByName(user_name[i]);
			var re = user_check[i];
			if(!re.test(ct[0].value)){
				message += user_mess[i];
			}
		}  
		if(message!=""){
			alert(message);
			return false;
		}else{
			return true; 
		}
	}
	$(function(){
		$("tr").first().find("td input").bind("blur",function(){
			var curr = $(this);
			var _value = String(curr.val());
			var _param = String(curr.attr("name"));
			var _data = JSON.stringify({"username":_value});
			$.ajax({
				type:"post",
				url:"UserServlet",
				data:{"method":"isExist","data":_data},
				success:function(value){
					
					var str = "此用户名已被占用";
					console.log(value!=str&&curr.parent().text().indexOf(str)>-1);
					console.log(value);
					if(value==str&&curr.parent().text().indexOf(str)<0){
						curr.parent().append(value);
						console.log("value");
					}
					if(value!=str&&curr.parent().text().indexOf(str)>-1){
						curr.parent().text().replace(str,value);
						console.log("va");
					}
				}
			})
			
		})
	})
</script>
<title>Insert title here</title>
</head>
<body background=file:///E:/test.jpg> 
	<form action="UserServlet" method="post" onsubmit="return check()">
		<input type="hidden" name="method" value="userRegister">
		<table >
			<tr>
				<td>用户名：</td>
				<td><input type="text" name="Username"/></td>
			</tr>
			<tr>
				<td>密&nbsp;码：</td>
				<td><input type="password" name="Password"/></td>
			</tr>
			<tr>
				<td>手机号：</td>
				<td><input type="text" name="Phone"/></td>
			</tr>
			<tr>
				<td>邮&nbsp;箱：</td>
				<td><input type="text" name="Email"/></td>
			</tr>
			<tr>
				<td>住&nbsp;址：</td>
				<td><input type="text" name="Address"/></td>
			</tr>
			<tr>
				<td></td>
				<td align="right"><input type="submit" value="注册" id="btn"></td>
			</tr>
		</table>
	</form>
	
</body>
</html>