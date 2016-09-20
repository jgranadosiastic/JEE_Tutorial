package com.jgranados.journals.authentication;

import com.jgranados.journals.authentication.service.AuthenticationService;
import com.jgranados.journals.user.model.User;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * JEE_Tutorial-ejb
 *
 * @author jose - 19.09.2016
 * @Title: AuthenticationFacade
 * @Description: description
 *
 * Changes History
 */
@Stateless
public class AuthenticationFacade implements AuthenticationFacadeLocal {

	@EJB
	private AuthenticationService authenticationService;
	
	@Override
	public User getAuthenticatedUser() {
		return authenticationService.getAuthenticatedUser();
	}

}
