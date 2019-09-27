package com.omnicuris.ecommerce.filters;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.omnicuris.ecommerce.service.UserService;



@Component
@Order(1)
@WebFilter(urlPatterns = "/products/*")
public class UserRequest implements Filter{
	private static final Pattern AUTH_HEADER_VALUE_PATTERN = Pattern.compile("^Basic (.*)$", Pattern.CASE_INSENSITIVE);
	
	@Autowired
	private UserService userService;
	 @Override
	 public void init(FilterConfig filterConfig) throws ServletException {
		 
	 }

	 @Override
	 public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

	  HttpServletRequest request = (HttpServletRequest) servletRequest;   
	  HttpServletResponse response = (HttpServletResponse) servletResponse;
	  HttpServletRequest httpServletRequest = (HttpServletRequest) request;
	  try{
		 Matcher m = AUTH_HEADER_VALUE_PATTERN.matcher(httpServletRequest.getHeader("Authorization"));
	      if (!m.matches()) {            
	         // return Optional.empty();
	      }
	      String encoded = m.group(1);
	      String userNameColonPassword = new String(Base64.getDecoder().decode(encoded), StandardCharsets.UTF_8);
	      int indexOfColon = userNameColonPassword.indexOf(':');
	      /* User check for authorization.can be enabled if required
	      if(null==this.userService.findUser(userNameColonPassword.substring(0, indexOfColon)))
	    	  throw new IllegalArgumentException("User not found");
	      */
	  }
	  catch(Exception e){
		  /*
		   * Error when user is not authorized
		   * its commented.can be enabled if required
		   *
		  ((HttpServletResponse) response).sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "The token is not valid.");
		  return;
		  */
	  }
  	    
	  	  //call next filter in the filter chain
	  filterChain.doFilter(request, response);  
	 }

	 @Override
	 public void destroy() {

	 }

}
