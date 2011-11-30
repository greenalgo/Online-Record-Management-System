package com.green.jsf.faces.util;

import java.io.Serializable;
import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@SuppressWarnings("deprecation")
@Scope
@Component
public class FacesUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3857893644991690056L;

	public void addErrorMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
	}

	public Object getFromSession(String key) {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get(key);
	}

	public Object putInSession(String key, Object value) {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().put(key, value);
	}

	public Object removeFromSession(String key) {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().remove(key);
	}

	public Object getFromRequest(String key) {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getRequestMap().get(key);
	}

	public void addErrorMessage(String clientId, String msg) {
		registerMessage(clientId, msg, FacesMessage.SEVERITY_ERROR);
	}

	public void addInfoMessage(String clientId, String msg) {
		registerMessage(clientId, msg, FacesMessage.SEVERITY_INFO);
	}

	public void addWarningMessage(String clientId, String msg) {
		registerMessage(clientId, msg, FacesMessage.SEVERITY_WARN);
	}

	public Object getActionAttribute(ActionEvent event, String name) {
		return event.getComponent().getAttributes().get(name);
	}

	public void removeAllMessages() {
		Iterator<FacesMessage> messagesIterator = FacesContext
				.getCurrentInstance().getMessages();
		while (messagesIterator.hasNext()) {
			messagesIterator.next();
			messagesIterator.remove();
		}
	}

	public void resetManagedBean(String beanName) {

		getValueBinding(getJsfEl(beanName)).setValue(
				FacesContext.getCurrentInstance(), null);

	}
        
        public boolean isPostBack(){
            return ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getMethod().toLowerCase().equals("post");
        }

	@SuppressWarnings("unchecked")
	public <T> T getManagedBean(Class<T> clazz, String beanName) {
		FacesContext context = FacesContext.getCurrentInstance();
		T myBean = (T) context.getApplication().getExpressionFactory()
				.createValueExpression(context.getELContext(),
						"#{" + beanName + "}", clazz).getValue(
						context.getELContext());

		return myBean;

	}

	private void registerMessage(String clientId, String msg, Severity severity) {
		FacesContext.getCurrentInstance().addMessage(clientId,
				new FacesMessage(severity, msg, msg));
	}

	private ValueBinding getValueBinding(String el) {
		return FacesContext.getCurrentInstance().getApplication()
				.createValueBinding(el);
	}

	private String getJsfEl(String value) {
		return "#{" + value + "}";
	}
}
