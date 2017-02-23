<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=utf-8"/>
<title>大白网上书店</title>

</head>
	<frameSet border=0 rows="29%,*">
		<frame src="${pageContext.request.contextPath }/client/head_2.jsp" name="head">                                                                  
		<frame src="${pageContext.request.contextPath }/client/IndexServlet?method=getBookList" name="main">
	</frameSet>
</html>