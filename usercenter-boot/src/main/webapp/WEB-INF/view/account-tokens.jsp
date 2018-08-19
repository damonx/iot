<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FPA USER CENTRE</title>
</head>
<body>
	<div style="font-size: 12px">
		<p>GettingAccessToken Error Code:<%=request.getAttribute("GettingAccessTokenErrorCode") %></p>
		<p>GettingAccessToken Error Info:<%=request.getAttribute("GettingAccessTokenErrorInfo") %></p>
		<p>GettingUserInfo Error Code:<%=request.getAttribute("GettingUserInfoErrorCode") %></p>
		<p>GettingUserInfo Error Info:<%=request.getAttribute("GettingUserInfoErrorInfo") %></p>
		<p>GettingUplusAccessToken Return Code:<%=request.getAttribute("GettingUplusAccessTokenErrorCode") %></p>
		<p>GettingUplusAccessToken Return Info:<%=request.getAttribute("GettingUplusAccessTokenErrorInfo") %></p>
		<hr/>
		<p>Salesforce_access_token:<%=request.getAttribute("sf_access_token") %></p>
		<p>Salesforce_refresh_token:<%=request.getAttribute("sf_refresh_token") %></p>
		<p>uplus_access_token:<%=request.getAttribute("uplus_access_token") %></p>
		<p>Salesforce_id_normalized:<%=request.getAttribute("id") %></p>
		<p>uplus_id:<%=request.getAttribute("uplus_id") %></p>
		<p>token_type:<%=request.getAttribute("token_type") %></p>	
	</div>
		
	<div style="font-size: 12px">
		<a href="querydevicelist?uplusToken=<%=request.getAttribute("uplus_access_token") %>
		&uplusId=<%=request.getAttribute("uplus_id") %>"
		 target="view_frame">Device List</a>
	</div>		
		
</body>
</html>