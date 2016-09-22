package com.jgranados.journals.logout.view;

import com.jgranados.journals.utils.MessagesUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * POC
 * @author jose - 28.05.2014 
 * @Title: LogoutView
 * @Description: description
 *
 * Changes History
 */
@Named
@RequestScoped
public class LogoutView {
    /**
     * Close the user session
     */
    public String logout() {
	    System.out.println("----------------------logout");
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.logout();
            request.getSession().invalidate();
		  return "logout";
        } catch (ServletException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		  return null;
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            MessagesUtil.addErrorMessage(
                    MessagesUtil.getLocalizedMessage("processingerror"));
		  return null;
        }

    }
}