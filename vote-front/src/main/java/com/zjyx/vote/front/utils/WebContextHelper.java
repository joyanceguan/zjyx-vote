package com.zjyx.vote.front.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zjyx.vote.api.model.persistence.UserLogin;
import com.zjyx.vote.common.constants.VoteConstants;



public class WebContextHelper {

	private static HttpServletRequest getRequest() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes(); 
		HttpServletRequest request = attributes.getRequest();
        return request;
    }
	
	public static void setSessionValue(String name,Object value){
		getRequest().getSession().setAttribute(name, value);
	}
	
	public static Object getSessionValue(String name){
		return getRequest().getSession().getAttribute(name);
	}
	
	
	public static void cleanSession(){
		HttpSession session = getRequest().getSession();  
		session.invalidate();  
	}
	
	public static UserLogin getUser(){
		UserLogin userLogin = (UserLogin) getSessionValue(VoteConstants.USER_SESSION);
		return userLogin;
	}
	
	public static boolean isLogin(){
		return getSessionValue(VoteConstants.USER_SESSION)!=null;
	}
	
	public static Long getUserId(){
		UserLogin userLogin = (UserLogin) getSessionValue(VoteConstants.USER_SESSION);
		return userLogin == null ? null : userLogin.getId();
	}
}
