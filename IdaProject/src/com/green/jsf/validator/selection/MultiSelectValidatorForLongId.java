package com.green.jsf.validator.selection;

import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class MultiSelectValidatorForLongId implements Validator {
    
    
	
	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
            
             Map<String,Object> attributes = component.getAttributes();        
                String label = "";
                if(attributes != null && attributes.get("label") != null){
                    label =  attributes.get("label").toString() + ": ";
                }
                
                    Long[] ids = (Long[])value;
                    if(ids.length == 0){
                        FacesMessage message = new FacesMessage();
                            message.setDetail(label + "Select a valid value for multi-select list");
                            message.setSummary(label + "Select a valid value for multi-select list");
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            throw new ValidatorException(message);
                    }
               

	}
        
      
        
        

}
