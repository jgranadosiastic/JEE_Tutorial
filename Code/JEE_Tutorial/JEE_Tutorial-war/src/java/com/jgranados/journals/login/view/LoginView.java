/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jgranados.journals.login.view;

import com.jgranados.journals.authentication.AuthenticationFacadeLocal;
import com.jgranados.journals.user.model.User;
import com.jgranados.journals.utils.MessagesUtil;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;

/**
 * ClassRoom
 *
 * @author jose - 18.09.2016
 * @Title: LoginView
 * @Description: description
 *
 * Changes History
 */
@Named
@ViewScoped
public class LoginView implements Serializable {

	@EJB
	private AuthenticationFacadeLocal authenticationFacade;

	
	private String userName;
	private String password;
	private String requestedURI;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRequestedURI() {
		return requestedURI;
	}

	public void setRequestedURI(String requestedURI) {
		this.requestedURI = requestedURI;
	}

	@PostConstruct
	public void init() {
		String queryString;
		requestedURI = (String) FacesContext.getCurrentInstance().getExternalContext()
			   .getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);

		if (requestedURI != null && !requestedURI.isEmpty()) {
			queryString = (String) FacesContext.getCurrentInstance().getExternalContext()
				   .getRequestMap().get(RequestDispatcher.FORWARD_QUERY_STRING);
			if (queryString != null && !requestedURI.isEmpty()) {
				requestedURI = requestedURI + "?" + queryString;
			}
		}

	}

	public String login() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		Map<String, Object> sessionMap = context.getExternalContext().getSessionMap();
		try {
			//login using JAAS
			request.login(userName, password);
			//login validating user en sistem
			User currentUser = authenticationFacade.getAuthenticatedUser();
			//delete any user in the map
			sessionMap.remove("USERID");
			//add the user id to the map
			sessionMap.put("USERID", currentUser.getIdUser());
			//add the username to the map
			sessionMap.put("USERNAME", currentUser.getUserName());
			
			if (getRequestedURI() != null && !getRequestedURI().isEmpty()) {
				FacesContext.getCurrentInstance().getExternalContext().redirect(getRequestedURI());
				return null;
			} else {
				return "index" + currentUser.getUserRole();
			}

		} catch (ServletException ex) {// any exception
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
			MessagesUtil.addErrorMessage(
				   MessagesUtil.getLocalizedMessage("loginfailed"));
			try {
				request.logout();   //logout JAAS
			} catch (ServletException e) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
				return null;
			}
			return null;
		} catch (Exception ex) {// any exception
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
			MessagesUtil.addErrorMessage(
				   MessagesUtil.getLocalizedMessage("processingerror"));
			try {
				request.logout();   //logout JAAS
			} catch (ServletException e) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
				return null;
			}
			return null;
		}
	}

}
