<%@page import="com.employee.service.service.EmployeeLocalServiceUtil"%>
<%@page import="com.employee.service.model.Employee"%>
<%@ include file="/WEB-INF/jsp/init.jsp" %>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="java.util.List"%>
<%@page import="javax.portlet.PortletURL"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<liferay-ui:success key="success" message="Employee save successfully!" />

<portlet:renderURL var="addemployeeURL">
	<portlet:param name="action" value="editemployee"/>
</portlet:renderURL>

<a href="${addemployeeURL}">Add Employee</a>

<%
	PortletURL iteratorURL = renderResponse.createRenderURL();
	iteratorURL.setParameter("mvcPath", "/view.jsp");
%>

<portlet:actionURL var="searchEmployeeURL" >
	<portlet:param name="action" value="search"/>
</portlet:actionURL>

<aui:form action="${searchEmployeeURL}" method="post">
	<aui:input name="employeeName" label="Employee Name" />
	<aui:button type="submit" value="Search" />
</aui:form>

<%

List<Employee> employee = (List<Employee>) request.getAttribute("employeeName");

%>
<%
if(Validator.isNull(employee)){
	employee = EmployeeLocalServiceUtil.getEmployees(-1, -1);
}

%>

<liferay-ui:search-container total="<%=employee.size()%>" iteratorURL="${iteratorURL}" emptyResultsMessage="there are no employees">
	<liferay-ui:search-container-results results="<%=employee%>" />
	
	<liferay-ui:search-container-row className="Employee" modelVar="emp" keyProperty="employeeId">
		
		<liferay-ui:search-container-column-text name="Employee Name" property="employeeName" />
		
		<liferay-ui:search-container-column-text name="Mobile" property="mobile" />
		
		<liferay-ui:search-container-column-text name="Email" property="email" />
		
		<liferay-ui:search-container-column-text name="Current Designation" property="currentDesignation" />
		
		<liferay-ui:search-container-column-text name="Department" property="department" />
		
		<liferay-ui:search-container-column-jsp path="/WEB-INF/jsp/action.jsp" />
		
	</liferay-ui:search-container-row>
	
	<liferay-ui:search-iterator />
</liferay-ui:search-container>