<%@page import="com.employee.service.service.EmployeeLocalServiceUtil"%>
<%@page import="com.employee.service.model.Employee"%>
<%@ include file="/init.jsp" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@page import="java.util.List"%>
<%@page import="javax.portlet.PortletURL"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="java.util.LinkedList" %>
<%@taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@taglib prefix = "c" uri= "http://java.sun.com/jsp/jstl/core" %>

<h1>Employee Information</h1>
<br>
<%
	PortletURL iteratorURL=renderResponse.createActionURL();
	iteratorURL.setParameter("mvcPath", "/view.jsp");
%>
		
<%
	List<Employee> empDetails=EmployeeLocalServiceUtil.getEmployees(-1, -1);
%>

<portlet:renderURL var="addEmployeeURL">
	<portlet:param name="mvcPath" value="/addEmployee.jsp"></portlet:param>
</portlet:renderURL>

<aui:button value="Add Employee" onClick="${addEmployeeURL}"/>

<liferay-ui:search-container total="<%= empDetails.size() %>" delta="5" iteratorURL="<%= iteratorURL %>">
	<liferay-ui:search-container-results results="<%=EmployeeLocalServiceUtil.getEmployees(searchContainer.getStart(), searchContainer.getEnd())%>"/>
	<liferay-ui:search-container-row className="Employee" modelVar="Emp" keyProperty="employeeId">
						
		<portlet:renderURL var="updateEmployeeURL">
			<portlet:param name="jspPage" value="/editEmployee.jsp" />
			<portlet:param name="employeeId" value="${Emp.employeeId}"/>
		</portlet:renderURL>
		
		<liferay-ui:search-container-column-text name="Employee Name" property="employeeName" href="${updateEmployeeURL}"/>
		<liferay-ui:search-container-column-text name="Mobile" property="mobile" href="${updateEmployeeURL}"/>
		<liferay-ui:search-container-column-text name="Email" property="email" href="${updateEmployeeURL}"/>
		<liferay-ui:search-container-column-text name="Current Designation" property="currentDesignation" href="${updateEmployeeURL}"/>
		<liferay-ui:search-container-column-text name="Department" property="department" href="${updateEmployeeURL}"/>
		<liferay-ui:search-container-column-jsp name="Action" path="/action.jsp" />
		
	</liferay-ui:search-container-row>
	<liferay-ui:search-iterator/>
</liferay-ui:search-container>

