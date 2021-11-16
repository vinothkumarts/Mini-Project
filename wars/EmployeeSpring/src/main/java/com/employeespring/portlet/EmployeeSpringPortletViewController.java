package com.employeespring.portlet;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.employee.service.model.Employee;
import com.employee.service.service.EmployeeLocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

/**
 * @author parameshwaran
 */
@Controller
@RequestMapping("VIEW")
public class EmployeeSpringPortletViewController {

	@RenderMapping
	public String view(RenderRequest request, RenderResponse response) {
		return "view";
		
	}
	@RenderMapping(params = "action=editemployee")
	public String edit(RenderRequest request, RenderResponse response, Model model) {
		return "editemployee";
	}
	
	
	@RenderMapping(params = "action=search")
	public String search(RenderRequest request, RenderResponse response, Model model) {
		return "view";
	}
	
	@RenderMapping(params = "action=editPage")
	public String editpage(RenderRequest request, RenderResponse response, Model model) {
		return "editemployee";
	}
	
	@ActionMapping(params = "action=addemployee")
	public void addemployeeMethod(ActionRequest request, ActionResponse response) {
		String employeeName = ParamUtil.getString(request, "employeeName");
		String mobile = ParamUtil.getString(request, "mobile");
		String email = ParamUtil.getString(request, "email");
		String currentDesignation = ParamUtil.getString(request, "currentDesignation");
		String department = ParamUtil.getString(request, "department");
		
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(request);
        
		String fileName=uploadPortletRequest.getFileName("uploadedFile");		 			
		File file = uploadPortletRequest.getFile("uploadedFile");
		String mimeType = uploadPortletRequest.getContentType("uploadedFile");
		String description = "";
		String changeLog = "";
		long fileSize = 0;
		
		Random rand = new Random(); 
        int rand_int1 = rand.nextInt(100000);
        
        String title = rand_int1 + fileName;
        
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        long repositoryId = themeDisplay.getScopeGroupId();
        
        User user = themeDisplay.getUser();

		
		long employeeId = ParamUtil.getLong(request, "employeeId");
		Employee employee = null;		
		
		if(Validator.isNotNull(employeeId)){
			try {
				employee = EmployeeLocalServiceUtil.getEmployee(employeeId);
			} catch (PortalException e) {
				e.printStackTrace();
			}
		}
		
		if(Validator.isNull(employee)){
			employee = EmployeeLocalServiceUtil.createEmployee(CounterLocalServiceUtil.increment());
		}
		employee.setEmployeeName(employeeName);
		employee.setMobile(mobile);
		employee.setEmail(email);
		employee.setCurrentDesignation(currentDesignation);
		employee.setDepartment(department);
		
		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(), request);
			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(repositoryId, 0l, fileName, mimeType, title, description, changeLog, file, serviceContext);
			long uploadPhoto = fileEntry.getFileEntryId();
			employee.setUploadPhoto(uploadPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		if(Validator.isNull(employee)){
			EmployeeLocalServiceUtil.addEmployee(employee);
			SessionMessages.add(request, "success");
		}else{
			EmployeeLocalServiceUtil.updateEmployee(employee);
		}
	}
	
	@ActionMapping(params = "action=editEmployee")
	public void editEmployee(ActionRequest request, ActionResponse response) throws IOException {
		long employeeId = ParamUtil.getLong(request, "employeeId");
		Employee employee = null;
		if(Validator.isNotNull(employeeId) && employeeId > 0){
			try {
				employee = EmployeeLocalServiceUtil.getEmployee(employeeId);
			} catch (PortalException e) {
				e.printStackTrace();
			}
			request.setAttribute("employee", employee);
		}
		response.setRenderParameter("action", "editPage");
	}
	
	@ActionMapping(params = "action=delete")
	public void delete(ActionRequest request, ActionResponse response) throws PortalException {
		long employeeId = ParamUtil.getLong(request, "employeeId");
		EmployeeLocalServiceUtil.deleteEmployee(employeeId);
	}
	
	@ActionMapping(params = "action=search")
	public void doSomething(ActionRequest request, ActionResponse response) {
		
		String employeeName = ParamUtil.getString(request, "employeeName");
		List<Employee> employee = EmployeeLocalServiceUtil.getProjectByemployeeName(employeeName);
		request.setAttribute("employeeName", employee);
		System.out.println("employeeName >>>>>>>" + employee);
		response.setRenderParameter("action", "view");
	}
}
