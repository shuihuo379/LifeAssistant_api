/**
 * 
 */
package com.whty.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author zhangchao
 *
 */
public class ErrorLogFilter implements Filter {

	private static final Log log=LogFactory.getLog(ErrorLogFilter.class);
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		log.info("Exception Log Filter destroy");

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try{
			chain.doFilter(request, response);
			//因为struts2捕捉到异常以后就把异常放到request的javax.servlet.error.exception属性里面，
			//不打印log，所以最好截获一下异常，把log打印出来
			Object exception = request.getAttribute("javax.servlet.error.exception");
			if(exception != null && exception instanceof Exception){
				log.error("Server Intenal Exception",(Exception)exception);
			}
			
		}catch(Exception ex){
			log.error("Server Intenal Exception",ex);
			throw new ServletException(ex);
		}

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		log.info("Exception Log Filter Init");

	}

}
