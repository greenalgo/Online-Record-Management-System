package com.green.jsf.validator.selection;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

public class SelectValidatorDependsIsRequired extends SelectValidator {

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		String isRequiredValue = context.getExternalContext()
				.getRequestParameterMap().get("isRequired");
		if (!"true".equalsIgnoreCase(isRequiredValue)) {
			return;
		}
		super.validate(context, component, value);

	}

}
