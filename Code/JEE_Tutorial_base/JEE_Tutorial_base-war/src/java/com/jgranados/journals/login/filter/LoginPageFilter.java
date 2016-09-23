package com.jgranados.journals.login.filter;

import com.jgranados.journals.authentication.enums.RoleEnum;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jgranados
 */
@WebFilter(urlPatterns = {"/login.xhtml"})
public class LoginPageFilter implements Filter {

	private static final boolean debug = true;
	// The filter configuration object we are associated with.  If
	// this value is null, this filter instance is not currently
	// configured. 
	private FilterConfig filterConfig = null;

	public LoginPageFilter() {
	}

	/**
	 *
	 * @param servletRequest The servlet request we are processing
	 * @param servletResponse The servlet response we are creating
	 * @param filterChain The filter chain we are processing
	 *
	 * @exception IOException if an input/output error occurs
	 * @exception ServletException if a servlet error occurs
	 */
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
		   FilterChain filterChain)
		   throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		if (request.getUserPrincipal() != null) { //If user is already authenticated
			if (request.isUserInRole(RoleEnum.PUBLISHER.toString())) {
				response.sendRedirect(request.getContextPath() + "/journals/journals.xhtml");// or, forward using RequestDispatcher
			} else if (request.isUserInRole(RoleEnum.PUBLIC.toString())) {
				response.sendRedirect(request.getContextPath() + "/subscriptions/subscriptions.xhtml");// or, forward using RequestDispatcher
			} else {
				filterChain.doFilter(servletRequest, servletResponse);
			}

		} else {
			filterChain.doFilter(servletRequest, servletResponse);
		}
	}

	/**
	 * Destroy method for this filter
	 */
	@Override
	public void destroy() {
	}

	/**
	 * Init method for this filter
	 */
	@Override
	public void init(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
		if (filterConfig != null) {
			if (debug) {
				log("LoginPageFilter:Initializing filter");
			}
		}
	}

	public void log(String msg) {
		filterConfig.getServletContext().log(msg);
	}
}
