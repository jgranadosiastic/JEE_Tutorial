package com.jgranados.journals.authentication.service;

import com.jgranados.journals.authentication.query.AuthenticationQueryBean;
import com.jgranados.journals.user.model.User;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

/**
 * JEE Tutorial
 *
 * @author jose - 04.07.2016
 * @Title: AuthenticationService
 * @Description: description
 *
 * Changes History
 */
@Stateless
public class AuthenticationService {

	@EJB
	AuthenticationQueryBean authenticationQueryBean;
	@Resource
	SessionContext securityContext;

	/**
	 * Method to get the authenticated user using the securitycontext and the user
	 * principal
	 *
	 * @return
	 */
	public User getAuthenticatedUser() {
		String userName = securityContext.getCallerPrincipal().getName();
		return authenticationQueryBean.getUser(userName);
	}

}
