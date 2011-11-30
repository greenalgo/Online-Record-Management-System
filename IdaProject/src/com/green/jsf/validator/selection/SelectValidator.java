package com.green.jsf.validator.selection;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class SelectValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		Map<String, Object> attributes = component.getAttributes();

		if (attributes != null && attributes.get("byPassOnNull") != null
				&& "true".equals(attributes.get("byPassOnNull"))
				&& value == null) {
			return;
		}
		String label = "";
		if (attributes != null && attributes.get("label") != null) {
			label = attributes.get("label").toString() + ": ";
		}

		String id =  value.toString();

		if (id.equals("0")) {
			FacesMessage message = new FacesMessage();
			message.setDetail(label + "Select a valid value for drop down");
			message.setSummary(label + "Select a valid value for drop down");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			// LOGGER.error("Problem ");
			throw new ValidatorException(message);
		}

	}

}
