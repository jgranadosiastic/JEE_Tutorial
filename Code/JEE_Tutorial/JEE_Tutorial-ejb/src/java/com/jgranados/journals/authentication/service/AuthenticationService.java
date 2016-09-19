package com.jgranados.journals.authentication.service;

import com.jgranados.journals.authentication.query.AuthenticationQueryBean;
import com.jgranados.journals.user.model.User;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

/**
 * SA_Java_Journals
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
	@Context
	SecurityContext securityContext;

	/**
	 * Method to get the authenticated user using the securitycontext and the user
	 * principal
	 *
	 * @return
	 */
	public User getAuthenticatedUser() {
		String userName = securityContext.getUserPrincipal().getName();
		return authenticationQueryBean.getUser(userName);
	}

}
