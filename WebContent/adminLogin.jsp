<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>后台首页</title>
</head>

	<frameset rows="15%,*">
		<frame src="<%=request.getContextPath() %>/admin/head.jsp"/>
		
		<frameset cols="20%,*">		
			<frame src="<%=request.getContextPath()%>/admin/left.jsp" name="left"/>
			<frame src="<%=request.getContextPath()%>/admin/right.jsp" name="right"/>
		</frameset>
	</frameset>

</html>