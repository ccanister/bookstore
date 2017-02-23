<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>

	<frameset rows="15%,*">
		<frame src="<%=request.getContextPath()%>/admin/head.jsp"/>
		 <frameset cols="20%,*">		
			<frame src="<%=request.getContextPath()%>/admin/left_2.jsp" name="left_2"/>
			<frame src="<%=request.getContextPath()%>/admin/right_2.jsp" name="right_2"/>
		</frameset>	
	</frameset>
	

</html>