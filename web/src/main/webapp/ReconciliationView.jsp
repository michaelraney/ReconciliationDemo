<%@page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html"/>
<title>Reconciliation Demo</title>
</head>
<body>
<img src="html/images/xaplogo.png" alt="xap" width="286" height="108" />
</br>
<h2>Reconciliation</h2>
<% 	
	String hostName = request.getServerName(); 
    String sessionId = session.getId();
%>
<br/>

<table>
	<tr>
		<td>Servlet host address:</td>
		<td><%= hostName %></td>
	</tr>
	<tr>
		<td>Session id:</td>
		<td><%= sessionId %></td>
	</tr>
	
</table>

<br/>

<form action="<%= response.encodeURL("ReconServlet") %>" method="post">

<input type="submit" value="Statistics" name="countButton"/>
</form>
<h3>Statistics</h3>
<table>
	<tr>
		<%  Object orders = session.getAttribute("orderAmount");
			if(orders != null){ %>
				<td>Total Orders</td>
				<td><%= orders %></td>
			<%} 
		%>
	</tr>
	<tr>
		<%  Object orphan = session.getAttribute("orderOrphaned");
			if(orphan != null){%>
				<td>Total Orphaned</td>
				<td><%= orphan %></td>
			<%} 
		%>
	</tr>
	<tr>
		<%  Object orphanPercent = session.getAttribute("orderOrphanPercent");
			if(orphanPercent != null){%>
				<td>Orphan Percent</td>
				<td><%= orphanPercent %></td>
			<%} 
		%>
	</tr>

	<tr>
		<%  Object payment = session.getAttribute("paymentAmount");
			if(payment != null){%>
				<td>Total Payments</td>
				<td><%= payment %></td>
			<%} 
		%>
	</tr>
	<tr>
		<%  Object paymentAdj = session.getAttribute("paymentAdjusted");
			if(paymentAdj != null){%>
				<td>Total Payments Adjusted</td>
				<td><%= paymentAdj %></td>
			<%} 
		%>
	</tr>
	<tr>
		<%  Object paymentPer= session.getAttribute("paymentPercent");
			if(paymentPer != null){%>
				<td>Payment Adjusted Percent</td>
				<td><%= paymentPer %></td>
			<%} 
		%>
	</tr>
	
</table>
</body>
</html>
