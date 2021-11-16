<%@page import="com.employee.service.model.Employee"%>
<%@page import="javax.portlet.PortletURL"%>
<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="com.liferay.portal.kernel.dao.search.ResultRow"%>
<%@ include file="/init.jsp" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="java.util.LinkedList" %>
<%@taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%
	ResultRow row = (ResultRow) request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
	
	Employee employee = (Employee)row.getObject();
%>

<liferay-ui:icon-menu>

	<portlet:actionURL name="delete" var="deleteURL">
		<portlet:param name="employeeId" value="<%= String.valueOf(employee.getEmployeeId()) %>"/>
	</portlet:actionURL>
	
	<portlet:renderURL var="viewEmployeeURL">
		<portlet:param name="employeeId" value="<%= String.valueOf(employee.getEmployeeId()) %>" />
		<portlet:param name="mvcPath" value="/viewEmployee.jsp"></portlet:param>
	</portlet:renderURL>

	<portlet:actionURL var="editURL">
		<portlet:param name="employeeId" value="<%= String.valueOf(employee.getEmployeeId()) %>" />
		<portlet:param name="jspPage" value="/editEmployee.jsp"/>
	</portlet:actionURL>
	
	<liferay-ui:icon image="view" message="View"url="${viewEmployeeURL}"/>
	<liferay-ui:icon image="edit" message="Edit" url="${editURL}" />
	<liferay-ui:icon-delete image="delete" url="${deleteURL}" confirmation = "Are you sure want to delete?"/>
	
</liferay-ui:icon-menu>

