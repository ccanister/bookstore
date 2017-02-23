
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>表单提交</title>
		<script type="text/javascript" src="jquery-1.4.2.min.js"></script>
		<script type="text/javascript">
			function dosubmit(){
				return true;
			}
		</script>
	</head>
	<body>
		<c:set var="name" value="dabai" scope="request"/>
		<jsp:forward page="test2.jsp" />
		
	</body>
	
</html>