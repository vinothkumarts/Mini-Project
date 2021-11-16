<%@page import="javax.portlet.PortletURL"%>
<%@page import="com.liferay.portal.kernel.service.UserLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.model.User"%>
<%@page import="java.util.List"%>
<%@ include file="init.jsp" %>

<%

PortletURL iteratorURL = renderResponse.createRenderURL();
iteratorURL.setParameter("mvcPath", "/view.jsp");
	List<User> users = (List<User>)renderRequest.getAttribute("userList");
%>
<liferay-ui:search-container total="<%=users.size()%>" iteratorURL="${iteratorURL}" emptyResultsMessage="there are no users">
	<liferay-ui:search-container-results results="<%=users%>" />
	<liferay-ui:search-container-row className="User" modelVar="user" keyProperty="userId">
		<liferay-ui:search-container-column-text name="FirstName" property="firstName" />
		<liferay-ui:search-container-column-text name="LastName" property="lastName" />
		<liferay-ui:search-container-column-text name="EmailAddress" property="emailAddress" />
	</liferay-ui:search-container-row>
	<liferay-ui:search-iterator />
</liferay-ui:search-container>