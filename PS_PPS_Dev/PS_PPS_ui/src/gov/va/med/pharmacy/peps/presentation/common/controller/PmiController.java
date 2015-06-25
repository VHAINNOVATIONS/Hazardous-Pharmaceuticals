/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.LanguageChoice;
import gov.va.med.pharmacy.peps.common.vo.validator.ErrorKey;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.common.vo.validator.ValidationError;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.session.DrugReferenceService;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.DrugReferenceBean;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.EditManagedItemBean;
import gov.va.med.pharmacy.peps.presentation.common.controller.requests.ManageRequestsController;


/**
 * 
 * This controller handles all of the URL mappings for the COTS services tab and
 * subtabs
 * 
 * Details of PmiController's operations, special dependencies
 * or protocols developers shall know about when using the class.
 * 
 */
@Controller
public final class PmiController extends PepsController {

    @Autowired
    private DrugReferenceService drugReferenceService;

    @Autowired
    private ManageRequestsController manageRequestsController;

    @Autowired
    private Errors errors;

    /**
     * /**
     * PMI Tab
     * Gets the PMI for the manage item edit screen when the user clicks the View PMI button
     *
     * @param entityType of item
     * @param itemId of item
     * @return redirect back to the view
     * @throws ValidationException ValidationException
     */
    @RequestMapping(value = "{" + ControllerConstants.ENTITY_TYPE + "}/{" + ControllerConstants.ITEM_ID + "}/pmi.go",
        method = RequestMethod.POST)
    public String getPmiPage(@PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType, @PathVariable(
        value = ControllerConstants.ITEM_ID) String itemId) throws ValidationException {
        
        EditManagedItemBean editBean = flowScope.get(EditManagedItemBean.class);

        if (editBean == null) {
            return REDIRECT + "/" + pageTrail.goToPreviousFlowUrl("/searchItems.go");
        } else {
            DrugReferenceBean drugReferenceBean = new DrugReferenceBean();
            flowScope.put("drugReferenceBean", drugReferenceBean);

            try {
                String languageChoice = request.getParameter(FieldKey.SPANISH_TEXT.toAttributeName());

                if (StringUtils.isEmpty(languageChoice)) {
                    drugReferenceBean.setLanguageChoice(LanguageChoice.ENGLISH);
                } else {
                    drugReferenceBean.setLanguageChoice(LanguageChoice.valueOf(request.getParameter(FieldKey.SPANISH_TEXT
                        .toAttributeName())));
                }
            } catch (IllegalArgumentException e) {
                drugReferenceBean.setLanguageChoice(LanguageChoice.ENGLISH);
            }

            editBean.getItem().setSpanishText(drugReferenceBean.getLanguageChoice());

            try {
                drugReferenceBean.setGcnSequenceNumber(editBean.getItem().getGcnSequenceNumber());

                drugReferenceBean.setPatientMedicationInstruction(drugReferenceService
                    .retrievePatientMedicationInformation(Long.parseLong(drugReferenceBean.getGcnSequenceNumber()),
                                                          LanguageChoice.SPANISH.equals(drugReferenceBean
                                                              .getLanguageChoice())));
            } catch (NumberFormatException e) {
                errors
                    .addError(new ValidationError(FieldKey.GCN_SEQUENCE_NUMBER, ErrorKey.COMMON_NOT_NUMERIC,
                                                  FieldKey.GCN_SEQUENCE_NUMBER.getLocalizedName(request.getLocale())));
                throw new ValueObjectValidationException(errors);
            }

            if (editBean.getMainRequest() == null) {
                return REDIRECT + "/" + entityType.toString().toLowerCase() + "/" + itemId + "/edit.go?"
                    + ControllerConstants.TAB_KEY + "=" + ControllerConstants.OPTIONS_TAB;
            } else {
                return REDIRECT + "/" + entityType.toString().toLowerCase() + "/" + itemId + "/request/"
                    + editBean.getMainRequest().getId() + manageRequestsController.getRequestEvent(entityType) + "?"
                    + ControllerConstants.TAB_KEY + "=" + ControllerConstants.OPTIONS_TAB;
            }
        }
    }

    /**
     * 
     * Displays the PMI in a popup window
     *
     * @param model Spring Model
     * @return the pmi popup view
     */
    @RequestMapping(value = "/pmi.go", method = RequestMethod.GET)
    public String getPmiPopup(Model model) {
        if (flowScope.get("drugReferenceBean") == null) {
            return REDIRECT + "/" + pageTrail.goToPreviousFlowUrl("/searchItems.go");
        } else {
            model.addAttribute("showPrintLink", true);

            return "pmi.popup";
        }
    }
}
