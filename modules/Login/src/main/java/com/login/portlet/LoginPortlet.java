package com.login.portlet;

import com.login.constants.LoginPortletKeys;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.exception.EmailAddressException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.UserScreenNameException;
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author vinoth kumar
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=Login Portlet",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + LoginPortletKeys.Login,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class LoginPortlet extends MVCPortlet {
	@Override
	public void render(RenderRequest request, RenderResponse response) throws IOException, PortletException {
		long userId = ParamUtil.getLong(request, "userId");

		User user = null;
		if(Validator.isNotNull(userId) && userId > 0){
			try {
				user = UserLocalServiceUtil.getUser(userId);
			} catch (PortalException e) {
				e.printStackTrace();
			}
			request.setAttribute("user", user);
		}
		super.render(request, response);
	}

	public void Register(ActionRequest request, ActionResponse response){
		String userName = ParamUtil.getString(request, "userName");
		String password = ParamUtil.getString(request, "password");
		String roleName = ParamUtil.getString(request, "role");
		boolean autoPassword = false;
		boolean autoScreenName = false;
		long facebookId = 0;
		String openId="";
		String firstName = "Fname"+CounterLocalServiceUtil.increment("Fname");
		String emailAddress = "email"+CounterLocalServiceUtil.increment(EmailAddress.class.getName())+"@test.com";
		String middleName = "";
		String lastName = "Lname";
		long prefixId = 0;
		long suffixId = 0;
		boolean male = true;
		int birthdayMonth = 10;
		int birthdayDay = 1;
		int birthdayYear = 1997;
		String jobTitle = "";
		long[] groupIds = {};
		long[] organizationIds = {};
		long[] roleIds = {};
		long[] userGroupIds = {};
		boolean sendEmail = false;
		ServiceContext serviceContext = new ServiceContext();


		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

		User user = null;

		try {
			user = UserLocalServiceUtil.addUser(themeDisplay.getUserId(), themeDisplay.getCompanyId(), autoPassword, password, password, autoScreenName, userName, emailAddress, facebookId, openId, themeDisplay.getLocale(), firstName, middleName, lastName, prefixId, suffixId, male, birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds, organizationIds, roleIds, userGroupIds, sendEmail, serviceContext);
			System.out.println("user >>>>>>>" + user);
		}catch(UserScreenNameException eu){
			SessionErrors.add(request,"duplicateScreenName");
		}catch(EmailAddressException em){
			SessionErrors.add(request,"duplicateEmail");
		}catch (PortalException e) {
			SessionErrors.add(request,"error");
			e.printStackTrace();
		}
		if(Validator.isNotNull(user)){
			try {
				Role role = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), roleName);
				UserLocalServiceUtil.addRoleUser(role.getRoleId(), user);
			} catch (PortalException e) {
				e.printStackTrace();
			}

			try {
				Organization org = OrganizationLocalServiceUtil.getOrganization(themeDisplay.getCompanyId(),roleName.contains("Manager") ? "Manager" : roleName.contains("HR Executive") ? "HR Executive" : roleName);
				UserLocalServiceUtil.addOrganizationUser(org.getOrganizationId(), user);
			} catch (PortalException e) {
				e.printStackTrace();
			}

			try {
				UserGroup userGroup = UserGroupLocalServiceUtil.getUserGroup(themeDisplay.getCompanyId(), roleName.contains("Manager") ? "Manager" : roleName.contains("HR Executive") ? "HR Executive" : roleName);
				UserLocalServiceUtil.addUserGroupUser(userGroup.getUserGroupId(), user);
			} catch (PortalException e) {
				e.printStackTrace();
			}

		}		
	}
}