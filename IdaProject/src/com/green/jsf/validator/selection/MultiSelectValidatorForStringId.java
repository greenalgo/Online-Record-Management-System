package com.green.jsf.validator.selection;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class MultiSelectValidatorForStringId implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		String[] ids = (String[]) value;
		if (ids.length == 0) {
			FacesMessage message = new FacesMessage();
			message.setDetail("Select a valid value for multi-select list");
			message.setSummary("Select a valid value for multi-select list");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}

	}

}
