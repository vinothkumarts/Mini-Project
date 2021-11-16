<%@ include file="/WEB-INF/jsp/init.jsp" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<portlet:actionURL var="EmployeeURL">
	<portlet:param name="action" value="addemployee"/>
</portlet:actionURL>

<form action="${EmployeeURL}" method="post">
	<input name="employeeId" type="hidden" value="${employee.employeeId}"/>
	Employee Name: <br><input name="employeeName" type="text" value="${employee.employeeName}"/><br><br>
	Mobile: <br><input name="mobile" type="text" value="${employee.mobile}"/><br><br>
	Email: <br><input name="email" type="text" value="${employee.email}"/><br><br>
	Current Designation: <br><input name="currentDesignation" type="text" value="${employee.currentDesignation}" /><br><br>
	Department: <br><input name="department" type="text" value="${employee.department}"/><br><br>
	<input name="uploadedFile" type="file"/><br><br>
	<input type="submit" value="Save"/>
</form>