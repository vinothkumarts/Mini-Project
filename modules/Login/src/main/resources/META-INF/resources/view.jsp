<%@ include file="/init.jsp" %>

<portlet:actionURL var="LoginURL" name="Register" />

<liferay-ui:success key="success" message="Register saved successfully!" />
<liferay-ui:error key="error" message="Exception occured" />
<liferay-ui:error key="duplicateScreenName" message="Duplicate Username, Kindly try with diffrent Username" />
<liferay-ui:error key="duplicateEmail" message="Duplicate Email, Kindly try with diffrent Email" />

<h1>Register New User Form</h1>

<aui:form action="${LoginURL}" method="post">
	<aui:input name="userName" type="text" label="User Name" />
	<aui:input name="password" type="password" label="Password" />
	<aui:select name="role" label="Role">
		<aui:option value="Manager">Manager</aui:option>
		<aui:option value="HR Manager">HR Manager</aui:option>
		<aui:option value="HR Executive">HR Executive</aui:option>
		<aui:option value="Employee">Employee</aui:option>
	</aui:select>
	
	<aui:button type="submit" value="Submit" />
</aui:form>