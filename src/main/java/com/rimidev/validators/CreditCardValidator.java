package com.rimidev.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author plang
 */
@FacesValidator("com.rimidev.CreditCardValidator")
public class CreditCardValidator implements Validator{

    private static final String NUMBER_PATTERN = "\\d{16}";
    private Pattern pattern;
    private Matcher matcher;
    
    public CreditCardValidator(){
        pattern = Pattern.compile(NUMBER_PATTERN);
    }
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
