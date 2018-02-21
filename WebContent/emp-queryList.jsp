<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<s:form action="emp-save">
		<s:textfield name="firstName" label="first-name"></s:textfield>
		<s:textfield name="lastName" label="last-name"></s:textfield>
		<s:textfield name="email" label="email"></s:textfield>
		<s:submit></s:submit>
	</s:form>

	<br>
	<hr>
	<table border="1" cellpadding="10" cellspacing="0" align="center">
		<thead>
			<tr>
				<td>id</td>
				<td>first-name</td>
				<td>last-name</td>
				<td>email</td>
				<td>operation</td>
			</tr>
		</thead>
		<tbody>
			<!-- 使用排序标签将emp的集合按照id进行升序排序 -->
			<s:sort comparator="#request.comparator" source="#request.emps" var="emps">
			<s:iterator value="#attr.emps">
				<tr>
					<td>${id }</td>
					<td>${firstName }</td>
					<td>${lastName }</td>
					<td>${email }</td>
					<td><a href="emp-delete?id=${id }">delete</a>&nbsp;&nbsp;<a href="emp-edit?id=${id }">update</a></td>
				</tr>				
			</s:iterator>
			</s:sort>
		</tbody>
	</table>
</body>
</html>