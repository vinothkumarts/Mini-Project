<%@ include file="/init.jsp" %>
<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="com.liferay.portal.kernel.repository.model.FileEntry"%>
<%@page import="com.liferay.portal.kernel.theme.ThemeDisplay"%>
<%@page import="com.liferay.document.library.kernel.service.DLAppServiceUtil"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.kernel.service.CompanyLocalServiceUtil"%>
<%@page import="com.liferay.taglib.search.ResultRow"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>

<portlet:actionURL var="addEmployeeURL" name="addEmployee" />

<aui:form action="${addEmployeeURL}" method="post">
	<aui:input name="employeeId" type="hidden"/>
	<aui:input name="employeeName" type="text" label="Employee Name"/>
	<aui:input name="mobile" type="text" label="Mobile"/>
	<aui:input name="email" type="email" label="Email"/>
	<aui:input label="Upload Image" type="file" name="uploadedFile" />
	<aui:input name="currentDesignation" type="text" label="Current Designation"/>
	<aui:input name="department" type="text" label="Department"/>
	
	<aui:button type="submit" value="Save"></aui:button>
</aui:form>

