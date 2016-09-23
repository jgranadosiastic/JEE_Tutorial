package com.jgranados.journals.utils;

import java.util.Locale;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * JEE_Tutorial-war
 *
 * @author jose - 19.09.2016
 * @Title: MessagesUtil
 * @Description: description
 *
 * Changes History
 */
public class MessagesUtil {

	public static final String PATH_MESSAGES_BUNDLE = "com.jgranados.journals.i18n.messages";
	public static final String PATH_LABELS_BUNDLE = "com.jgranados.journals.i18n.labels";

	public static void addErrorMessage(String msg) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	}

	public static void addSuccessMessage(String msg) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	}

	public static void addWarningMessage(String msg) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, msg);
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	}

	public static String getLocalizedMessage(String key) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Locale locale = facesContext.getViewRoot().getLocale();
		try {
			return ResourceBundle.getBundle(PATH_MESSAGES_BUNDLE, locale).getString(key);
		} catch (Exception e) {
			return "";
		}
	}

	public static String getLocalizedLabel(String key) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Locale locale = facesContext.getViewRoot().getLocale();
		try {
			return ResourceBundle.getBundle(PATH_LABELS_BUNDLE, locale).getString(key);
		} catch (Exception e) {
			return "";
		}
	}
}
