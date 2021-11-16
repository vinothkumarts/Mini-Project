package com.api.portlet;

import com.api.constants.RestApiPortletKeys;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.osgi.service.component.annotations.Component;


import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;


/**
 * @author vinoth kumar
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=RestApi Portlet",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + RestApiPortletKeys.RestApi,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class RestApiPortlet extends MVCPortlet {
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

			

			String url="http://localhost:8080/api/jsonws/user/get-company-users/company-id/{company-id}/start/-1/end/-1";
		    
		    String companyId= Long.toString(themeDisplay.getCompanyId());
		    String userEmail="test@liferay.com";
		    
		    String[] searchList={"{company-id}"};
		    String[] replList={companyId};

		    //Path params are replaced with args to make web service call
		    url=StringUtil.replace(url, searchList, replList);
		    
		    System.out.println(url);
		    StringBuilder sb = new StringBuilder();
		    
		    JSONArray jsonArray = new JSONArray();
		    try
		    {
		        URL urlVal = new URL(url);
		        HttpURLConnection conn = (HttpURLConnection) urlVal.openConnection();
		        		        
		        String uname ="test@liferay.com";
		        String pswd="test";
		        String authStr=uname+":"+pswd;
		        
		        String authStrEnc=new Base64().encode(authStr.getBytes());

		        conn.setRequestMethod("GET");
		        conn.setRequestProperty("Accept", "application/json");
		        conn.setRequestProperty("Authorization", "Basic "+authStrEnc);
		        
		        BufferedReader brf = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		        JSONParser json=new JSONParser();
		        jsonArray=(JSONArray)json.parse(brf);
		        List<User> userList = new ArrayList<User>();
		        for(int i=0; jsonArray.size() > i ; i++){
		        	JSONObject jsonObject= (JSONObject) jsonArray.get(i);
		        	User user = UserLocalServiceUtil.createUser(Long.valueOf(jsonObject.get("userId").toString()));
		        	user.setFirstName(jsonObject.get("firstName").toString());
		        	user.setLastName(jsonObject.get("lastName").toString());
		        	user.setEmailAddress(jsonObject.get("emailAddress").toString());
		        	userList.add(user);
		        }
		        renderRequest.setAttribute("userList", userList);
		    }
		    catch(IOException e)
		    {
		        System.out.println("Something went wrong while reading/writing in stream!!");
		    } catch (ParseException e) {
				e.printStackTrace();
			}
		super.render(renderRequest, renderResponse);
	}
}