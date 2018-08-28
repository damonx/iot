<%@page import="com.fpa.usercenter.dto.UplusDevice"%>
<%@page import="java.util.List"%>
<%@page import="com.fpa.usercenter.dto.UplusDeviceResponseDto"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FPA USER CENTRE DEVICE LIST</title>
</head>
<body>
	<div style="font-size: 12px">

		<% if(request.getAttribute("uplusDeviceResponseDto")!=null){

			UplusDeviceResponseDto dto = (UplusDeviceResponseDto)request.getAttribute("uplusDeviceResponseDto");

			%>
			<p>Return Code:<%=dto.getRetCode() %></p>
			<%

			if(dto.getDevices()!=null){
				List<UplusDevice> deviceList = dto.getDevices();

				for(UplusDevice d : deviceList){
				%>
				<div>
					DeviceId:<%=d.getId() %>
					DeviceName:<%=d.getName() %>
					DeviceType:<%=d.getMac() %>
				</div>
				<%

				}
			}

		}%>
	</div>

</body>
</html>
