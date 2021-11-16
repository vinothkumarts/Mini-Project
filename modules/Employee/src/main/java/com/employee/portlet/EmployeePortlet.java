package com.employee.portlet;

import com.employee.constants.EmployeeInfoPortletKeys;
import com.employee.service.model.Employee;
import com.employee.service.service.EmployeeLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;


@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=Employee Portlet",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + EmployeeInfoPortletKeys.EmployeeInfo,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class EmployeePortlet extends MVCPortlet {
	public void render(RenderRequest renderRequest,RenderResponse renderResponse) throws IOException, PortletException{
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		List<Employee> employeeInfo = EmployeeLocalServiceUtil.getProjectByemployeeName("employeeName");
		renderRequest.setAttribute("employeeInfo", employeeInfo);
		String employeeId = ParamUtil.getString(renderRequest, "employeeId");
		renderRequest.setAttribute("employeeId", employeeId);
		if(employeeId != null){
			Employee employee = null;
			try{
				employee = EmployeeLocalServiceUtil.getEmployee(Long.valueOf(employeeId));
			}catch (NumberFormatException | PortalException e) {
				// TODO: handle exception
			}
			renderRequest.setAttribute("cmd", "viewEmployee");
			renderRequest.setAttribute("employee", employee);
		}
		super.render(renderRequest, renderResponse);
	}
	public void addEmployee(ActionRequest request, ActionResponse response)
	{
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(request);
		String fileName=uploadPortletRequest.getFileName("uploadedFile");
		File file = uploadPortletRequest.getFile("uploadedFile");
		String mimeType = uploadPortletRequest.getContentType("uploadedFile");

		Random rand = new Random(); 
        int rand_int1 = rand.nextInt(100000);
		
        String title = rand_int1 + fileName;
        
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		
		String employeeName=ParamUtil.getString(request, "employeeName");
		String mobile=ParamUtil.getString(request, "mobile");
		String email=ParamUtil.getString(request, "email");
		String currentDesignation=ParamUtil.getString(request, "currentDesignation");
		String department=ParamUtil.getString(request, "department");
		
		System.out.println("Employee Name"+employeeName);
		System.out.println("Mobile"+mobile);
		System.out.println("Email"+email);
		System.out.println("Current Designation"+currentDesignation);
		System.out.println("Department"+department);

		long employeeId = ParamUtil.getLong(request, "employeeId");
		System.out.println("employeeId:::::"+employeeId);
		Employee employeeInfo = null;
		if(employeeId > 0) {
			try {
				employeeInfo = EmployeeLocalServiceUtil.getEmployee(employeeId);
			} catch (PortalException e) {
				e.printStackTrace();
			}
			employeeInfo.setEmployeeName(employeeName);
			employeeInfo.setMobile(mobile);
			employeeInfo.setEmail(email);
			employeeInfo.setCurrentDesignation(currentDesignation);
			employeeInfo.setDepartment(department);
			long repositoryId = themeDisplay.getScopeGroupId();
			try {
				ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(), request);
				FileEntry fileEntry = DLAppServiceUtil.addFileEntry(repositoryId, 0, fileName, mimeType, title, "", "",
						file, serviceContext);
				long uploadPhoto = fileEntry.getFileEntryId();
				employeeInfo.setUploadPhoto(uploadPhoto);
			} catch (Exception e) {
				e.printStackTrace();
			}
			EmployeeLocalServiceUtil.updateEmployee(employeeInfo);
		}else{
			employeeInfo = EmployeeLocalServiceUtil.createEmployee(0);
			employeeInfo.setEmployeeName(employeeName);
			employeeInfo.setMobile(mobile);
			employeeInfo.setEmail(email);
			employeeInfo.setCurrentDesignation(currentDesignation);
			employeeInfo.setDepartment(department);
			long repositoryId = themeDisplay.getScopeGroupId();
			try {
				ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(), request);
				FileEntry fileEntry = DLAppServiceUtil.addFileEntry(repositoryId, 0, fileName, mimeType, title, "", "",
						file, serviceContext);
				long uploadPhoto = fileEntry.getFileEntryId();
				employeeInfo.setUploadPhoto(uploadPhoto);
			} catch (Exception e) {
				e.printStackTrace();
			}
			EmployeeLocalServiceUtil.updateEmployee(employeeInfo);
		}
	}
	public void delete(ActionRequest request, ActionResponse response) throws PortalException
	{
		long employeeId = ParamUtil.getLong(request, "employeeId");
		EmployeeLocalServiceUtil.deleteEmployee(employeeId);
	}
}