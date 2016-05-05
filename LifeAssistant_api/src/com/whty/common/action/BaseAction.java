package com.whty.common.action;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @ClassName: BaseAction  
 * @Description: 所有Action的基类 
 */
public class BaseAction extends ActionSupport  implements  ServletResponseAware,ServletRequestAware   {
	private static final long serialVersionUID = 8120870313925959519L;
	protected static Logger logger = Logger.getLogger(BaseAction.class);
	/**
	 * HttpServletRequest 对象
	 */
	public HttpServletRequest request;
	/**
	 * HttpServletRequest 对象
	 */
	public HttpServletResponse response;
	
	/**
	 * 获取当前登入用户信息
	 * @return
	 */
	/**
	public User getLoginUser() {
		
		request = ServletActionContext.getRequest();
		
		if (request != null) {
			HttpSession session = request.getSession();
			Object object = session.getAttribute("user");
			if (object == null) {
				return null;
			} else {
				return (User)object;
			}
		} else {
			return null;
		}
	}*/

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response =response;
	}

	@Override
	public void setServletRequest(HttpServletRequest resquest) {
		this.request = resquest;
	}

	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	public String getMethod(){
		String method = request.getMethod();
		return method;
	}
	
	public void printWriter(HttpServletResponse response, String json)  {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json; charset=utf-8");
		if(logger.isDebugEnabled()){
			logger.debug("response info:"+json);
		}
		try {
			PrintWriter pw = response.getWriter();
			pw.write(json);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
