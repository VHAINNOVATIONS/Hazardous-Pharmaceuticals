/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.utility.EnvironmentUtility;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.validator.ErrorKey;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.common.vo.validator.ValidationError;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.MultipleSelectItemBean;
import gov.va.med.pharmacy.peps.presentation.common.pagetrail.FlashScope;
import gov.va.med.pharmacy.peps.presentation.common.pagetrail.FlowInputScope;
import gov.va.med.pharmacy.peps.presentation.common.pagetrail.FlowScope;
import gov.va.med.pharmacy.peps.presentation.common.pagetrail.PageTrail;
import gov.va.med.pharmacy.peps.presentation.common.spring.interceptor.PepsSpringInterceptor;
import gov.va.med.pharmacy.peps.service.common.session.RequestService;


/**
 * PepsController
 */
@Controller
public class PepsController implements MessageSourceAware {

    /** DELIM */
    public static final String DELIM = ",";

    /** constant */
    public static final String REDIRECT = "redirect:";

    /** constant */
    public static final String FIELD_ERRORS = "fieldErrors";

    /** constant */
    public static final String ERRORS = "errors";

    /** constant */
    protected static final String NEW_ITEM = "_newItem";

    /** constant */
    protected static final String MULTI_SEL_ITEM_BEAN = "multipleSelectItemBean";

//    /** constant */
//    private static final Logger LOG = Logger.getLogger(PepsController.class);

    /** constant */
    private static final String REFERRER = "referer";



    /** protected variable */
    @Autowired
    protected PageTrail pageTrail;

    /** protected variable */
    @Autowired
    protected FlashScope flashScope;

    /** protected variable */
    @Autowired
    protected FlowScope flowScope;

    /** protected variable */
    @Autowired
    protected FlowInputScope flowInputScope;

    /** protected variable */
    @Autowired
    protected MessageSource messageSource;

    /** protected variable */
    @Autowired
    protected HttpServletRequest request;

    /** protected variable */
    @Autowired
    protected HttpSession session;

    /** protected variable */
    @Autowired
    protected EnvironmentUtility environmentUtility;

    /** requestService */
    @Autowired
    protected RequestService requestService;

    @Autowired
    private Errors errors;



    /**
     * Default constructor
     */
    public PepsController() {
        super();
    }

    /**
     * 
     * Handles any thrown ValueObjectValidationExceptions or
     * ValidationExceptions, put the errors from the exception onto the request
     * for display.
     * 
     * @param ex Exception
     * @return The string view to be rendered by tiles
     */
    @ExceptionHandler({
        ValueObjectValidationException.class, ValidationException.class, BindException.class })
    public String handleValidationException(Exception ex) {

        if (ex instanceof ValueObjectValidationException) {
            flashScope.put(ERRORS, ((ValueObjectValidationException) ex).getErrors().getLocalizedErrors());
            flashScope.put(FIELD_ERRORS, ((ValueObjectValidationException) ex).getErrors());
        } else if (ex instanceof ValidationException) {
            List<String> errorsList = new ArrayList<String>();
            errorsList.add(((ValidationException) ex).getLocalizedMessage());
            flashScope.put(ERRORS, errorsList);
        } else if (ex instanceof BindException) {

            FieldError fe = ((BindException) ex).getFieldError();
            String field = fe.getField();
            FieldKey fieldKey = FieldKey.toFieldKey(field);

            if (Date.class.isAssignableFrom(fieldKey.getFieldClass())) {
                errors.addError(new ValidationError(fieldKey, ErrorKey.COMMON_IMPROPER_DATE_FORMAT, new Object[] {
                    fieldKey.getLocalizedName(getLocale()), fe.getRejectedValue() }));

            } else {
                errors.addError(new ValidationError(fieldKey, ErrorKey.COMMON_NOT_OPTION, new Object[] {
                    fe.getRejectedValue(), fieldKey.getLocalizedName(getLocale()) }));
            }

            flashScope.put(ERRORS, errors.getLocalizedErrors());
            flashScope.put(FIELD_ERRORS, errors);

        }

        return getReferrerRedirect();

    }

    /**
     * 
     * Description updates the MultiEdit Index
     *
     * @param itemId of the item
     */
    protected void updateMultiEditIndex(String itemId) {
        MultipleSelectItemBean multipleSelectItemBean = (MultipleSelectItemBean) flowScope.get(MULTI_SEL_ITEM_BEAN);

        if (multipleSelectItemBean != null) {
            int index = 0;

            for (String id : multipleSelectItemBean.getItemIds()) {
                if (id.equals(itemId)) {
                    multipleSelectItemBean.setCurrentIndex(index);
                    break;
                }

                index++;
            }
        }
    }
    
    /**
     * 
     * Gets the next index of the multi-edit bean
     *
     * @return the index
     */
    protected int getNextIndexOfMultiEdit() {
        MultipleSelectItemBean multipleSelectItemBean = (MultipleSelectItemBean) flowScope.get(MULTI_SEL_ITEM_BEAN);

        int index = -1;

        if (multipleSelectItemBean != null
            && multipleSelectItemBean.getCurrentIndex() + 1 < multipleSelectItemBean.getItemIds().length) {
            index = multipleSelectItemBean.getCurrentIndex() + 1;
        }

        return index;
    }

    /**
     * referrer redirect
     *
     * @return String
     */
    protected String getReferrerRedirect() {
        String contextPath = request.getContextPath();
        String referrer = request.getHeader(REFERRER);

        referrer = referrer.substring(referrer.indexOf(contextPath) + contextPath.length());

        return REDIRECT + referrer;
    }

    /**
     * Checks to see if the item already has a request
     * 
     * @param itemId of the item
     * @param entityType Of the item
     * @return request  
     */
    protected RequestVo checkForRequest(String itemId, EntityType entityType) {
        RequestVo req = null;

        try {
            req = requestService.retrieveNonCompletedRequest(itemId, entityType);

        } catch (ItemNotFoundException e) {
            req = null;
        }

        return req;
    }

    /**
     * Redirects the system to the request page if the item has a non completed request
     *
     * @param entityType of item
     * @param itemId of item
     * @param tab tab to redirect to
     * @return URL of request flow if there is a request, else and empty string.
     */
    protected String redirectToRequest(EntityType entityType, String itemId, String tab) {
        RequestVo req = checkForRequest(itemId, entityType);

        if (req != null) {

            pageTrail.goToPreviousFlowUrl();

            StringBuffer redirectTab = new StringBuffer();

            if (StringUtils.isNotEmpty(tab)) {
                redirectTab.append("?" + ControllerConstants.TAB_KEY + "=" + tab);
            }

            return REDIRECT + "/" + entityType.toString().toLowerCase() + "/" + itemId + "/request/" + req.getId()
                + "/editRequest.go" + redirectTab.toString();
        }

        return "";
    }

    /**
     * Description
     *
     * @return UserVo
     */
    public UserVo getUser() {
        return PepsSpringInterceptor.getUserContext(session).getUser();
    }

    /**
     * 
     * Gets the locale off of the request
     *
     * @return the locale
     */
    public Locale getLocale() {
        return request.getLocale();
    }

    /**
     * 
     * Gets the message source
     * 
     * @return messageSource
     */
    public MessageSource getMessageSource() {
        return messageSource;
    }

    /**
     * See spring docs
     * @see org.springframework.context.MessageSourceAware#setMessageSource(org.springframework.context.MessageSource)
     * 
     * @param messageSource message source to be used by this object
     */
    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;

    }

}
