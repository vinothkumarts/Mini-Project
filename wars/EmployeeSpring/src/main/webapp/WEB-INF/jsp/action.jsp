<%@page import="com.employee.service.model.Employee"%>
<%@ include file="/WEB-INF/jsp/init.jsp" %>
<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="com.liferay.portal.kernel.dao.search.ResultRow"%>
<%@page import="java.util.List"%>

<%
	ResultRow resultRow = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

	Employee employee = (Employee)resultRow.getObject();
%>
	<portlet:actionURL var="deleteURL">
		<portlet:param name="action" value="delete"/>
		<portlet:param name="employeeId" value="<%=String.valueOf(employee.getEmployeeId())%>"/>
	</portlet:actionURL>
	<portlet:actionURL var="editURL">
		<portlet:param name="employeeId" value="<%=String.valueOf(employee.getEmployeeId())%>"/>
		<portlet:param name="action" value="editEmployee" />
	</portlet:actionURL>
<liferay-ui:icon-menu>
	<liferay-ui:icon image="edit" message="Edit" url="${editURL}" />
	<liferay-ui:icon-delete image="delete" message="Delete" url="${deleteURL}" confirmation = "Are you sure want to delete?"/>
</liferay-ui:icon-menu>
