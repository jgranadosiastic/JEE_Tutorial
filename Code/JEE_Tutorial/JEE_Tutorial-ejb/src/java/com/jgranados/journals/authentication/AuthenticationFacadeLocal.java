package com.jgranados.journals.authentication;

import com.jgranados.journals.user.model.User;
import javax.ejb.Local;

/**
 * JEE_Tutorial-ejb
 * @author jose - 19.09.2016 
 * @Title: AuthenticationFacadeLocal
 * @Description: description
 *
 * Changes History
 */
@Local
public interface AuthenticationFacadeLocal {
	public User getAuthenticatedUser();

}
