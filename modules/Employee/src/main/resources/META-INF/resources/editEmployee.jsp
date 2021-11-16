<%@page import="com.employee.service.service.EmployeeLocalServiceUtil"%>
<%@page import="com.employee.service.model.Employee"%>
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

<%
	List<Employee> empDetails = EmployeeLocalServiceUtil.getEmployees(-1, -1);

	Employee employee = (Employee) request.getAttribute("employee");	
	out.print(employee.getUploadPhoto());
	
	long img = employee.getUploadPhoto();

	ThemeDisplay td = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
	long repositoryId = td.getScopeGroupId();
	
	FileEntry fileEntry = DLAppServiceUtil.getFileEntry(img);
	
	String url = td.getPortalURL() + td.getPathContext() + "/documents/"
			+ td.getScopeGroupId() + "/" + fileEntry.getFolderId() + "/" + fileEntry.getTitle();
%>
<portlet:actionURL var="editEmployeeURL" name="addEmployee" />

<aui:form action="${editEmployeeURL}" method="post">
	<aui:input name="employeeId" type="hidden" value="${employee.employeeId}"/>
	<aui:input name="employeeName" type="text" label="Employee Name" value="${employee.employeeName}"/>
	<aui:input name="mobile" type="text" label="Mobile" value="${employee.mobile}"/>
	<aui:input name="email" type="email" label="Email" value="${employee.email}"/>
	<aui:input name="currentDesignation" type="text" label="Current Designation" value="${employee.currentDesignation}"/>
	<aui:input name="department" type="text" label="Department" value="${employee.department}"/>
	<aui:input name="uploadedFile" type="file">
		<img alt="" src="<%=url%>" class="img-thumbnail">
	</aui:input>
	
	<aui:button type="submit" value="Save"></aui:button>
</aui:form>

