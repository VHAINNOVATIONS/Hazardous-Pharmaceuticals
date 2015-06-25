/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller.item;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.ReflectionUtility;
import gov.va.med.pharmacy.peps.common.vo.ActiveIngredientVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.GenericNameVo;
import gov.va.med.pharmacy.peps.common.vo.IngredientVo;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProcessedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.Role;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.common.vo.validator.ErrorKey;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.AddManagedItemBean;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.EditManagedItemBean;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.MultipleSelectItemBean;
import gov.va.med.pharmacy.peps.presentation.common.spring.annotation.RoleNeeded;
import gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility;
import gov.va.med.pharmacy.peps.service.common.session.ManagedItemService;
import gov.va.med.pharmacy.peps.service.common.session.RequestService;


/**
 * AddItemController's brief summary
 * 
 * Details of AddItemController's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
@Component
@RequestMapping("{" + ControllerConstants.ENTITY_TYPE + "}")
@RoleNeeded(roles = { Role.PSS_PPSN_MANAGER, Role.PSS_PPSN_SUPERVISOR })
public class AddItemController extends AbstractManageItemController {

    @Autowired
    private ManagedItemService managedItemService;

    @Autowired
    private RequestService requestService;

    /**
     * 
     * Create an item using an existing item as a template
     *
     * @param entityType entityType
     * @param itemId itemId
     * @param templateType templateType
     * @param model model
     * @return Add view
     * @throws ValidationException ValidationException
     * 
     */
    @RequestMapping(value = "/{" + ControllerConstants.ITEM_ID + "}/{" + ControllerConstants.TEMPLATE_TYPE + "}/add.go",
                    method = RequestMethod.GET)
    public String createItemFromExisting(@PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @PathVariable(value = ControllerConstants.ITEM_ID) String itemId,
        @PathVariable(value = ControllerConstants.TEMPLATE_TYPE) String templateType, Model model)
        throws ValidationException {

        if ("openBlankChildTemplate".equals(templateType)) {
            EditManagedItemBean editBean = flowScope.get(EditManagedItemBean.class);

            flowInputScope.put(ControllerConstants.ITEM_KEY, editBean.getItem());
            flowInputScope.put(ControllerConstants.TEMPLATE_TYPE, templateType);
        }

        pageTrail.addPage("add." + entityType.getFieldKey(),
            "Add " + messageSource.getMessage(entityType.getFieldKey() + ".name", null, request.getLocale()), true);

        return createItemFromTemplate(entityType, itemId, templateType, model);
    }

    /**
     * 
     * Creates an item using a blank template
     *
     * @param entityType ValidationException
     * @param templateType blank or existing template
     * @param model Spring Model
     * @return user to add view
     * @throws ItemNotFoundException  ItemNotFoundException
     * 
     */
    @RequestMapping(value = "/{" + ControllerConstants.TEMPLATE_TYPE + "}/add.go", method = RequestMethod.GET)
    public String createItemFromBlank(@PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @PathVariable(value = ControllerConstants.TEMPLATE_TYPE) String templateType, Model model)
        throws ItemNotFoundException {

        if (flowScope.get(ControllerConstants.ADDING_NEW_PARENT) != null
            && Boolean.valueOf((Boolean) flowScope.get(ControllerConstants.ADDING_NEW_PARENT))) {
            flowInputScope.put(ControllerConstants.ADDING_NEW_PARENT, flowScope.get(ControllerConstants.ADDING_NEW_PARENT));
            flowInputScope.put(ControllerConstants.ITEM_KEY, flowScope.get(ControllerConstants.ITEM_KEY));
        }

        pageTrail.addPage("add." + entityType.getFieldKey(),
            "Add " + messageSource.getMessage(entityType.getFieldKey() + ".name", null, request.getLocale()), true);

        return createItemFromTemplate(entityType, null, templateType, model);
    }

    /**
     * 
     * Handles create an item using an existing item from a table click
     *
     * @param entityType entityType
     * @param itemId itemId  
     * @return Redirect to openTemplate/add.go - add from exisiting item
     * @throws ItemNotFoundException ItemNotFoundException
     * 
     */
    @RequestMapping(value = "/{" + ControllerConstants.ITEM_ID + "}/createFromTable.go", method = RequestMethod.GET)
    public String createItemFromExisting(@PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @PathVariable(value = ControllerConstants.ITEM_ID) String itemId) throws ItemNotFoundException {

        flowInputScope.put(ControllerConstants.ADDING_NEW_PARENT, flowScope.get(ControllerConstants.ADDING_NEW_PARENT));
        flowInputScope.put(ControllerConstants.ITEM_KEY, flowScope.get(ControllerConstants.ITEM_KEY));

        return REDIRECT + "/" + entityType.name() + "/" + itemId + "/openTemplate/add.go";
    }

    /**
     * Helper method to create an item from any template
     *
     * @param entityType entityType
     * @param itemId itemId
     * @param templateType templateType
     * @param model model
     * @return the view
     * @throws ItemNotFoundException ItemNotFoundException
     */
    public String createItemFromTemplate(EntityType entityType, String itemId, String templateType, Model model)
        throws ItemNotFoundException {

        if (entityType.isNdc() && flowScope.get(ControllerConstants.COPYING_NDCS_FROM_PRODUCT) != null) {
            updateMultiEditIndex(itemId);
        }

        AddManagedItemBean addManagedItemBean = flowScope.get(AddManagedItemBean.class, true);

        ManagedItemVo item = addManagedItemBean.getItem();

        if (item == null || (item.getId() != itemId + NEW_ITEM && item.getEntityType() != entityType)) {
            item = createNewItem(entityType, itemId, templateType, addManagedItemBean);
        }

        model.addAttribute("item", item);

        if (flowScope.get("hasParentItem") != null && (Boolean) flowScope.get("hasParentItem")) {
            model.addAttribute("parentItem", flowScope.get(ControllerConstants.ITEM_KEY));
        }

        if (entityType.isDomainType()) {
            return entityType.getViewName() + ".wizard";
        } else {
            return entityType.getViewName() + ".wizard.input.spring.1";
        }
    }

    /**
     * 
     * Prepare to forward to the add managed domain screen from the add screen
     *
     * @param entityType entityType
     * @param formElementName formElementName
     * @param model model
     * @return add managed domain view
     * @throws ItemNotFoundException ItemNotFoundException
     * @throws ValueObjectValidationException Validation Exception
     */
    @RequestMapping(value = "/**/add/prepareAddManagedDomain.go", method = RequestMethod.POST)
    public String prepareForAddManagedDomain(
        @RequestParam(value = ControllerConstants.DOMAIN_ENTITY_TYPE) EntityType entityType,
        @RequestParam(value = ControllerConstants.FORM_ELEMENT_NAME, required = true) String formElementName, Model model)
        throws ItemNotFoundException, ValueObjectValidationException {

        AddManagedItemBean addBean = flowScope.get(AddManagedItemBean.class, true);
        ManagedItemVo item = addBean.getItem();
        
        Map<String, Object> keyMap = JspTagUtility.getParentAndChildFieldKeys(formElementName);
        FieldKey fieldKey = ((FieldKey) keyMap.get(JspTagUtility.PARENT_FIELD_KEY));
        
        if (fieldKey == null) {
            fieldKey = ((FieldKey) keyMap.get(JspTagUtility.FIELD_KEY));
        }
        
        String key = fieldKey.fromDotsToCamelCase();
                
        flowScope.put(key + "_" + ControllerConstants.PLUS_BUTTON_INDEX, (Integer) keyMap.get(JspTagUtility.INDEX));
        
        bindParameters(item);
        
        flowInputScope.put(key + "_" + ControllerConstants.PLUS_BUTTON_INDEX, 
            flowScope.get(key + "_" + ControllerConstants.PLUS_BUTTON_INDEX));
        flowInputScope.put(ControllerConstants.ITEM_KEY, item);
        flowInputScope.put("hasParentItem", item.getEntityType() == entityType);

        return getAddManagedDomainRedirectUrl(entityType, formElementName);
    }

    /**
     * Helper method for prepareForAddManagedDomain
     *
     * @param entityType entityType
     * @param formElementName formElementName
     * @return URL
     */
    public String getAddManagedDomainRedirectUrl(EntityType entityType, String formElementName) {
        return REDIRECT + "/" + entityType.name().toLowerCase() + "/addManagedDomain.go?"
            + ControllerConstants.FORM_ELEMENT_NAME + "=" + formElementName;
    }

    /**
     * 
     * Get for adding a Managed Domain from an Entity (Product, OI, NDC). Does some prep work for the subsequent POST.
     *
     * @param entityType of item
     * @param formElementName formElementName 
     * @param model Spring model
     * @return item created from template
     * @throws ItemNotFoundException ItemNotFoundException
     * @throws ValueObjectValidationException ValueObjectValidationException
     */
    @RequestMapping(value = "addManagedDomain.go", method = RequestMethod.GET)
    public String getAddManagedDomainFromEntity(
        @PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @RequestParam(value = ControllerConstants.FORM_ELEMENT_NAME, required = true) String formElementName, Model model)
        throws ItemNotFoundException, ValueObjectValidationException {

        Map<String, Object> keyMap = JspTagUtility.getParentAndChildFieldKeys(formElementName);
        FieldKey parentFieldKey = ((FieldKey) keyMap.get(JspTagUtility.PARENT_FIELD_KEY));
        FieldKey currentKey = (FieldKey) keyMap.get(JspTagUtility.FIELD_KEY);
        
        if (parentFieldKey == null) {
            parentFieldKey = currentKey;
        }
        
        String key = parentFieldKey.fromDotsToCamelCase();       
        
        String pageId = "add." + currentKey.getKey();

        pageTrail.addPage(pageId,
            "Add " + messageSource.getMessage(currentKey.getKey() + ".name", null, request.getLocale()), true);

        AddManagedItemBean addBean = flowScope.get(AddManagedItemBean.class, true);

        // If there are errors, do not make a new bean otherwise the flow will break
        if (addBean != null) {

            addBean.setFieldKey(currentKey);
            addBean.setParentFieldKey((FieldKey) keyMap.get(JspTagUtility.PARENT_FIELD_KEY));
            addBean.setIndex(flowScope.get(key + "_" + ControllerConstants.PLUS_BUTTON_INDEX) == null ? (Integer) keyMap
                .get(JspTagUtility.INDEX) : (Integer) flowScope.get(key + "_" + ControllerConstants.PLUS_BUTTON_INDEX));
            addBean.setAddingFromEntity(true);

            flowScope.put(addBean);
        }

        return createItemFromTemplate(entityType, null, "openBlankTemplate", model);
    }

    /**
     * Helper method to create a new item for adding
     *
     * @param entityType of item
     * @param itemId of item
     * @param templateType type of template
     * @param addManagedItemBean the bean containing the item
     * @return the created item
     * @throws ItemNotFoundException ItemNotFoundException
     */
    public ManagedItemVo createNewItem(EntityType entityType, String itemId, String templateType,
        AddManagedItemBean addManagedItemBean) throws ItemNotFoundException {
        ManagedItemVo item;
        item = openTemplate(templateType, itemId, entityType);
        item.setId(itemId + NEW_ITEM);

        if (flowScope.get(ControllerConstants.PARENT_ITEM) != null) {
            ManagedItemVo parentItem = (ManagedItemVo) flowScope.get(ControllerConstants.PARENT_ITEM);
            item = managedItemService.selectParent(item, parentItem.getId(), parentItem.getEntityType());
        }

        addManagedItemBean.setItem(item);

        return item;
    }

    /**
     * 
     * Create an item using an existing item as a template
     *
     * @param entityType of item
     * @param itemId of item
     * @param templateType type of template
     * @param model Spring model
     * @return the created item
     * @throws ValidationException exception
     * 
     */
    @RequestMapping(value = "/{" + ControllerConstants.ITEM_ID + "}/{" + ControllerConstants.TEMPLATE_TYPE + "}/add.go",
                    method = RequestMethod.POST)
    public String postItemFromExisting(@PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @PathVariable(value = ControllerConstants.ITEM_ID) String itemId,
        @PathVariable(value = ControllerConstants.TEMPLATE_TYPE) String templateType, Model model)
        throws ValidationException {

        AddManagedItemBean addManagedItemBean = flowScope.get(AddManagedItemBean.class);
        ManagedItemVo item = addManagedItemBean.getItem();
        bindParameters(item);

        item.validate(getUser(), environmentUtility.getEnvironment());

        flashScope.put(ControllerConstants.WARNINGS, managedItemService.checkForWarnings(item, null, true)
            .getLocalizedErrors());

        model.addAttribute("item", item);

        if (entityType.isDomainType()) {
            return "forward:/" + entityType + "/submitNewItem.go";
        } else {
            pageTrail.addPage("reviewItem" + item.getEntityType(),
                "Review " + messageSource.getMessage(entityType.getFieldKey() + ".name", null, request.getLocale())
                    + " Details");

            return entityType.getViewName() + ".wizard.input.spring.2";
        }
    }

    /**
     * 
     * Generate names for a product
     *
     * @param model Spring model
     * @return item with generated names
     * @throws ValueObjectValidationException ValueObjectValidationException     
     */
    @RequestMapping(value = "generateNames.go", method = RequestMethod.POST)
    public String generateNames(Model model) throws ValueObjectValidationException {

        AddManagedItemBean addManagedItemBean = flowScope.get(AddManagedItemBean.class);
        ManagedItemVo item = addManagedItemBean.getItem();
        bindParameters(item);
        computeProductUniquenessFields((ProductVo) item);
        addManagedItemBean.setItem(item);
        model.addAttribute("item", item);

        return REDIRECT + pageTrail.getCurrentUrl();
    }

    /**
     * 
     * Generate names for a OI
     *
     * @param model Spring model
     * @return item with generated names
     * @throws ValueObjectValidationException ValueObjectValidationException
     */
    @RequestMapping(value = "generateOINames.go", method = RequestMethod.POST)
    public String generateOINames(Model model) throws ValueObjectValidationException {

        AddManagedItemBean addManagedItemBean = flowScope.get(AddManagedItemBean.class);
        ManagedItemVo item = addManagedItemBean.getItem();
        bindParameters(item);
        managedItemService.generateOINames(item);
        addManagedItemBean.setItem(item);
        model.addAttribute("item", item);

        return REDIRECT + pageTrail.getCurrentUrl();
    }

    /**
     * 
     * Post an item from a blank template
     *
     * @param entityType of item
     * @param templateType type of template
     * @param model Spring model
     * @return The view for adding the item
     * @throws ValidationException ValidationException
     */
    @RequestMapping(value = "/{" + ControllerConstants.TEMPLATE_TYPE + "}/add.go", method = RequestMethod.POST)
    public String postItemFromBlank(@PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @PathVariable(value = ControllerConstants.TEMPLATE_TYPE) String templateType, Model model)
        throws ValidationException {

        return postItemFromExisting(entityType, null, templateType, model);
    }

    /**
     * 
     * Post for adding a Managed Domain from an Entity (Product, OI, NDC)
     *
     * @param entityType of item
     * @param model Spring model
     * @return Add a managed domain      
     * @throws ValidationException ValidationException
     */
    @RequestMapping(value = "addManagedDomain.go", method = RequestMethod.POST)
    public String postAddManagedDomainFromEntity(
        @PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        Model model) throws ValidationException {

        return postItemFromExisting(entityType, null, "openBlankTemplate", model);

    }

    /**
     * 
     * Cancels the add
     *
     * @param entityType the entityType of the item
     * @return the user to the previous screen
     * @throws Exception
     */
    @RequestMapping(value = "addCancel.go", method = RequestMethod.POST)
    public String cancelAdd(@PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType) {

        if (entityType.isDomainType()) {
            return REDIRECT + pageTrail.goToPreviousFlowUrl("/searchDataElements.go");
        } else {
            return REDIRECT + pageTrail.goToPreviousFlowUrl("/searchItems.go");
        }
    }

    /**
     * 
     * Checks for unassociated pending domains if the user cancels the add for an entity type
     * and builds a message naming the value and domain entity 
     *
     * @param entityType the entityType of the item
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param model Model
     * @throws ServletException exception
     * @throws IOException exception
     * @throws InterruptedException exception
     */
    @RequestMapping(value = "addProductCancel.go", method = RequestMethod.POST)
    public void cancelAdd(
        @PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        HttpServletRequest request,
        HttpServletResponse response,
        Model model) throws ServletException, IOException, InterruptedException {

        AddManagedItemBean addManagedItemBean = flowScope.get(AddManagedItemBean.class);
        List<ManagedItemVo> addedDomains =
            managedItemService.getPendingNonAffliliatedItems(addManagedItemBean.getItem(), getUser());
        StringBuffer domainResult = new StringBuffer();

        //If there are no domains, don't bother building the message
        if (!addedDomains.isEmpty()) {
            domainResult.append("Unattached Domains in PENDING State Include: \\n \\n");

            for (ManagedItemVo addedDomain : addedDomains) {
                domainResult.append(addedDomain.getValue() + " (" + addedDomain.getEntityType().getName() + ") \\n");
            }

            domainResult.append("\\n You can delete them at the PPS Data Requests tab.\\n");
        }

        String mainUrl = request.getContextPath();
        String loc = mainUrl + pageTrail.goToPreviousFlowUrl("/searchItems.go");
        doPost(request, response, domainResult.toString(), loc);

    }

    /**
     * 
     * Creates the javascript alert box and adds the user defined message 
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param message The message to display in the alert box
     * @param loc The location to redirect to
     * @throws InterruptedException InterruptedException
     * @throws ServletException ServletException
     * @throws IOException IOException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response, String message, String loc)
        throws ServletException, IOException, InterruptedException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<script type=\"text/javascript\">");

        //If there is no message, don't bother creating the alert
        if (StringUtils.isNotEmpty(message)) {
            out.println("alert('" + message + "');");
        }

        out.println("location = '" + loc + "';");
        out.println("</script>");
        out.close();

    }

    /**
     * 
     * Return to the add screen
     *
     * @return to the add screen 
     */
    @RequestMapping(value = "returnToAdd.go", method = RequestMethod.POST)
    public String returnToAdd() {

        return REDIRECT + pageTrail.goToPreviousUrl();
    }

    /**
     * 
     * Submits the new item
     *
     * @return To confirmModification screen for non domain items
     * @throws ValidationException ValidationException
     * 
     */
    @RequestMapping(value = "submitNewItem.go", method = RequestMethod.POST)
    public String submitNewItem() throws ValidationException {

        AddManagedItemBean addManagedItemBean = flowScope.get(AddManagedItemBean.class);
        ManagedItemVo item = addManagedItemBean.getItem();

        if (addManagedItemBean.isAddingFromEntity()) {
            bindNewManagedDomainToItem(item, addManagedItemBean);
        }

        ProcessedItemVo processedItem = createItem(item);
        item = processedItem.getItem();

        if (flowScope.get(ControllerConstants.ADDING_NEW_PARENT) != null) {
            ManagedItemVo childItem = (ManagedItemVo) flowScope.get(ControllerConstants.ITEM_KEY);

            if (childItem != null) {
                childItem.setParent(item);

                if (childItem.isProductItem()) {
                    ((ProductVo) childItem).setCategories(((OrderableItemVo) item).getCategories());
                } else if (childItem.isNdcItem()) {
                    ((NdcVo) childItem).setCategories(((ProductVo) item).getCategories());
                }
            }

        } else if (flowScope.get(ControllerConstants.TEMPLATE_TYPE) != null
            && "openBlankChildTemplate".equals(flowScope.get(ControllerConstants.TEMPLATE_TYPE))) {

            ManagedItemVo parentItem = (ManagedItemVo) flowScope.get(ControllerConstants.ITEM_KEY);

            if (parentItem != null) {
                parentItem.getChildren().add(item);
            }
        }

        // if user enters a PSR Name, update the requestVo with the name
        String psrName = request.getParameter(FieldKey.PSR_NAME.toAttributeName());

        if (!StringUtils.isEmpty(psrName)) {
            savePsrName(item, psrName);
        }

        flashScope.put(ControllerConstants.WARNINGS, processedItem.getWarnings().getLocalizedErrors(getLocale()));

        if (item.getEntityType().isDomainType()) {
            pageTrail.goToPreviousFlowUrl("/searchDataElements.go");

            if (pageTrail.getCurrentUrl().contains("edit.go") && !addManagedItemBean.isAddingFromEntity()) {
                pageTrail.goToPreviousFlowUrl("/searchDataElements.go");
            }

            return REDIRECT + pageTrail.getCurrentUrl();
        } else {
            return REDIRECT + "/" + processedItem.getItem().getEntityType().toLowerCase() + "/confirmModifications.go";
        }
    }

    /**
     * 
     * Saves the new item, then redirects to edit screen
     *
     * @return Redirect user to the edit screen
     * @throws ValidationException ValidationException
     */
    @RequestMapping(value = "addMoreDetails.go", method = RequestMethod.POST)
    public String addMoreDetails() throws ValidationException {

        AddManagedItemBean addManagedItemBean = flowScope.get(AddManagedItemBean.class);
        ManagedItemVo item = addManagedItemBean.getItem();

        ProcessedItemVo processedItem = createItem(item);

        item = processedItem.getItem();

        pageTrail.goToPreviousFlowUrl();
        flowScope.remove(EditManagedItemBean.class.getName());

        EditManagedItemBean editBean = new EditManagedItemBean();

        RequestVo req = checkForRequest(item.getId(), item.getEntityType());

        editBean.setItem(item);
        editBean.setOriginalItem(item);
        editBean.setMainRequest(req);

        flowScope.put(EditManagedItemBean.class.getName(), editBean);

        // if user enters a PSR Name, update the requestVo with the name
        String psrName = request.getParameter(FieldKey.PSR_NAME.toAttributeName());

        if (!StringUtils.isEmpty(psrName)) {
            savePsrName(item, psrName);
        }

        if (req == null) {
            return REDIRECT + "/" + item.getEntityType().toString().toLowerCase() + "/" + item.getId() + "/edit.go";
        } else {
            return REDIRECT + "/" + item.getEntityType().toString().toLowerCase() + "/" + item.getId() + "/request/"
                + req.getId() + "/editRequest.go";
        }
    }

    /**
     * 
     * Saves the new item, then redirects to blank template to create a child
     *
     * @return Redirect user to the blank template
     * @throws ValidationException ValidationException
     */
    @RequestMapping(value = "createBlankTemplate.go", method = RequestMethod.POST)
    public String createBlankTemplate() throws ValidationException {

        AddManagedItemBean addManagedItemBean = flowScope.get(AddManagedItemBean.class);
        ManagedItemVo item = addManagedItemBean.getItem();

        ProcessedItemVo processedItem = createItem(item);

        item = processedItem.getItem();

        // if user enters a PSR Name, update the requestVo with the name that the user entered
        String psrName = request.getParameter(FieldKey.PSR_NAME.toAttributeName());

        if (!StringUtils.isEmpty(psrName)) {
            savePsrName(item, psrName);
        }

        // set the pageTrail values
        pageTrail.goToPreviousFlowUrl();
        flowInputScope.put(ControllerConstants.PARENT_ITEM, item);

        return REDIRECT + "/" + item.getEntityType().getChild().toLowerCase() + "/" + item.getId() + "/"
            + "/openBlankChildTemplate/add.go";

    }

    /**
     * 
     * Saves the new product item when you are using the Copy NDCs function 
     *
     * @return Redirect user to the first ndc template
     * @throws ValidationException ValidationException
     */
    @RequestMapping(value = "saveProductTemplate.go", method = RequestMethod.POST)
    public String saveProductTemplate() throws ValidationException {

        AddManagedItemBean addManagedItemBean = flowScope.get(AddManagedItemBean.class);
        ManagedItemVo item = addManagedItemBean.getItem();
        MultipleSelectItemBean multiEditBean = (MultipleSelectItemBean) flowScope.get(MULTI_SEL_ITEM_BEAN);

        ProcessedItemVo processedItem = createItem(item);
        item = processedItem.getItem();

        // if user enters a PSR Name, update the requestVo with the name
        String psrName = request.getParameter(FieldKey.PSR_NAME.toAttributeName());

        if (!StringUtils.isEmpty(psrName)) {
            savePsrName(item, psrName);
        }

        pageTrail.goToPreviousFlowUrl();

        flowInputScope.put(ControllerConstants.PARENT_ITEM, item);
        flowInputScope.put(MULTI_SEL_ITEM_BEAN, multiEditBean);
        flowInputScope.put(ControllerConstants.COPYING_NDCS_FROM_PRODUCT, true);

        return REDIRECT + "/ndc/" + multiEditBean.getItemIds()[multiEditBean.getCurrentIndex()] + "/openTemplate/add.go";
    }

    /**
     * 
     * Validates and saves the new NDC from the Copy NDCs function 
     *
     * @return Redirect user to the first ndc template
     * @throws ValidationException ValidationException
     */
    @RequestMapping(value = "saveNDCTemplate.go", method = RequestMethod.POST)
    public String saveNDCTemplate() throws ValidationException {

        AddManagedItemBean addManagedItemBean = flowScope.get(AddManagedItemBean.class);
        ManagedItemVo item = addManagedItemBean.getItem();
        MultipleSelectItemBean multiEditBean = (MultipleSelectItemBean) flowScope.get(MULTI_SEL_ITEM_BEAN);

        bindParameters(item);

        item.validate(getUser(), environmentUtility.getEnvironment());

        ProcessedItemVo processedItem = createItem(item);
        item = processedItem.getItem();

        flashScope.put(ControllerConstants.WARNINGS, processedItem.getWarnings().getLocalizedErrors(getLocale()));

        pageTrail.goToPreviousFlowUrl();

        flowInputScope.put(ControllerConstants.PARENT_ITEM, item.getParent());
        flowInputScope.put(MULTI_SEL_ITEM_BEAN, multiEditBean);
        flowInputScope.put(ControllerConstants.COPYING_NDCS_FROM_PRODUCT, true);

        return REDIRECT + "/ndc/" + multiEditBean.getItemIds()[multiEditBean.getCurrentIndex() + 1] + "/openTemplate/add.go";

    }

    /**
     * Save the psrName to the respective RequestVo
     *
     * @param item the item being created
     * @param psrName the psrName to be saved
     * @throws ValidationException ValidationException
     */
    private void savePsrName(ManagedItemVo item, String psrName) throws ValidationException { //NOPMD

        RequestVo req = checkForRequest(item.getId(), item.getEntityType());
        item = managedItemService.retrieve(item.getId(), item.getEntityType(), getUser());
        req = requestService.retrieve(req.getId());
        req.setPsrName(psrName);
        req.setMarkedForPepsSecondReview(true);

        Collection<ModDifferenceVo> differences = new ArrayList<ModDifferenceVo>();
        managedItemService.commitRequest(item, req, differences, getUser(), false);


    }

    /**
     * Prepares the item for creation and calls the service create
     *
     * @param item the item to create
     * @return ProcessedItemVo The processed item
     * @throws ValidationException ValidationException
     */
    private ProcessedItemVo createItem(ManagedItemVo item) throws ValidationException {

        //Set the item id to null so the save can execute correctly
        item.setId(null);

        ProcessedItemVo processedItem = managedItemService.create(item, getUser());

        return processedItem;
    }

    /**
     * 
     * Displays the confirm modifications screen
     *
     * @param model Spring Model
     * @return confirmModifications view
     */
    @RequestMapping(value = "confirmModifications.go", method = RequestMethod.GET)
    public String confirmModifications(Model model) {
        List<String> warnings = (List<String>) request.getAttribute(ControllerConstants.WARNINGS);

        model.addAttribute(ControllerConstants.WARNINGS);
        model.addAttribute("hasWarnings", !CollectionUtils.isEmpty(warnings));

        pageTrail.goToPreviousFlowUrl("/searchItems.go");
        pageTrail.addPage("addItemConfirm", "Confirm Modifications", true);

        return "confirm.modifications";
    }

    /**
     * 
     * Display the acknowledgeModifications screen
     *
     * @return acknowledgeModifications view
     */
    @RequestMapping(value = "acknowledgeModifications.go", method = RequestMethod.POST)
    public String acknowledgeModifications() {

        pageTrail.goToPreviousFlowUrl("/searchItems.go");

        if (pageTrail.getCurrentUrl().contains("edit.go")) {
            pageTrail.goToPreviousFlowUrl("/searchItems.go");
        } else if (flowScope.get(ControllerConstants.ADDING_NEW_PARENT) != null) {

            return REDIRECT + pageTrail.goToPreviousFlowUrl("/searchItems.go");
        }

        String myString = pageTrail.getCurrentUrl();

        if (myString != null && myString.contains("matchResults.go?")) {
            myString = myString.substring(0, myString.indexOf("?"));
          
            return  REDIRECT + myString;
        }
        
        return REDIRECT + pageTrail.getCurrentUrl();
    }

    /**
     * 
     * Return addMoreItems screen
     *
     * @param entityType entityType
     * @return addMoreItems view
     * @throws Exception
     */
    @RequestMapping(value = "addMoreItems.go", method = RequestMethod.POST)
    public String addMoreItems(@PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType) {

        return entityType.getViewName() + ".wizard.input.spring.2b";
    }

    /**
     * 
     * Executes the correct template based on the argument passed.
     *
     * @param templateType The type of template (blank, existing)
     * @param itemId id of item
     * @param entityType of item
     * @return The templated managed item
     * @throws ItemNotFoundException ItemNotFoundException
     */
    public ManagedItemVo openTemplate(String templateType, String itemId, EntityType entityType)
        throws ItemNotFoundException {

        if ("openTemplate".equals(templateType)) {
            return managedItemService.retrieveTemplate(itemId, entityType);
        } else if ("openLocalTemplate".equals(templateType)) {
            return managedItemService.retrieveLocalTemplate(itemId, entityType);
        } else if ("openBlankChildTemplate".equals(templateType)) {
            return managedItemService.retrieveBlankChildTemplate(itemId, entityType.getParent());
        } else if ("openBlankLocalTemplate".equals(templateType)) {
            return managedItemService.retrieveBlankLocalTemplate(itemId, entityType);
        } else {
            return managedItemService.retrieveBlankTemplate(entityType);
        }

    }

    /**
     * Generates the Products and Print Names
     * 
     * @param item to generate the names for
     */
    public void computeProductUniquenessFields(ProductVo item) {
        SortedMap<String, ActiveIngredientVo> ingredientsMap = new TreeMap<String, ActiveIngredientVo>();

        Collection<ActiveIngredientVo> ingredients = item.getActiveIngredients();
        String vaProductName = "NA";
        String vaPrintName = "NA";
        String dosageFormVaProductName = "NA";
        String dosageFormVaPrintName = "NA";
        String dosageFormString = "NA";
        OrderableItemVo orderableItem = item.getOrderableItem();
        DosageFormVo dosageForm = null;
        GenericNameVo genericName = item.getGenericName();
        String nationalFormularyName = item.getNationalFormularyName();
        String genericNameString = "NA";
        String nationalFormularyNameString = "NA";

        if (orderableItem != null) {
            dosageForm = orderableItem.getDosageForm();
        }

        if (orderableItem != null && dosageForm != null && dosageForm.getDosageFormName() != null
            && dosageForm.getDosageFormName().trim().length() > 0) {
            dosageFormString = dosageForm.getDosageFormName();

            // check if the dosage form value has a "," in it
            String[] strArray = dosageFormString.split(",");

            if (strArray.length > 1) {
                String tmp = strArray[0];
                strArray[0] = strArray[1];
                strArray[1] = tmp;
                dosageFormVaPrintName = strArray[0] + " " + strArray[1];
            } else {
                dosageFormVaPrintName = dosageFormString;
            }

            dosageFormVaProductName = dosageFormString;

        }

        if (!ingredients.isEmpty()) {
            for (ActiveIngredientVo ingredient : ingredients) {
                if ((ingredient != null) && (ingredient.getIngredient() != null)) {
                    ingredientsMap.put(ingredient.getIngredient().getValue(), ingredient);
                }
            }

            // now iterate through the set and compute the string
            StringBuffer buf = new StringBuffer();
            ActiveIngredientVo activeIngredient;

            if (!ingredientsMap.isEmpty()) {
                Set keys = ingredientsMap.keySet();
                Iterator itr = keys.iterator();
                Integer count = 0;

                while (itr.hasNext()) {

                    // initial values were NA - should be blank
                    String ingredientName = "";
                    String ingredientStrength = "";
                    String ingredientUnit = "";
                    activeIngredient = ingredientsMap.get(itr.next());

                    IngredientVo ing = activeIngredient.getIngredient();
                    DrugUnitVo ingUnit = activeIngredient.getDrugUnit();

                    if (ing != null && ing.getValue() != null && ing.getValue().trim().length() > 0) {
                        ingredientName = ing.getValue();
                    }

                    if (activeIngredient.getStrength() != null && activeIngredient.getStrength().trim().length() > 0) {
                        ingredientStrength = activeIngredient.getStrength();
                    }

                    if (ingUnit != null && ingUnit.getValue() != null && ingUnit.getValue().trim().length() > 0) {
                        ingredientUnit = ingUnit.getValue();
                    }

                    buf.append(ingredientName + " " + ingredientStrength + ingredientUnit + "/");

                    count++;

                    // copy ingredient strength and unit to product strength and product unit 
                    // when there is only one active ingredient
                    if (!itr.hasNext() && count == 1) {
                        item.setProductStrength(ingredientStrength);
                        item.setProductUnit(ingUnit);
                    }

                }// end while

                if (buf.length() > 0) {

                    buf.replace(buf.length() - 1, buf.length(), " ");
                    vaProductName = buf.toString() + dosageFormVaProductName;
                    vaPrintName = buf.toString() + dosageFormVaPrintName;

                }

            }// end if

        }// end if

        if (vaPrintName.length() > PPSConstants.I40) {
            item.setVaPrintName(vaPrintName.substring(0, PPSConstants.I40));
        } else {
            item.setVaPrintName(vaPrintName);
        }

        if (vaProductName.length() > PPSConstants.I63) {
            item.setVaProductName(vaProductName.substring(0, PPSConstants.I64));
        } else {
            item.setVaProductName(vaProductName);
        }

        //fill in nationalFormularyName when it is null
        if (genericName != null && dosageForm != null && nationalFormularyName == null) {
            genericNameString = genericName.getValue().toString();
            nationalFormularyNameString = genericNameString + " " + dosageFormString;
            item.setNationalFormularyName(nationalFormularyNameString);
        }

        // when selecting the Generate button, clear the Dispense Unit field
        item.setDispenseUnit(null);

    }

    /**
     * 
     * Binds the new domain item to the entity item
     *
     * @param domainItem domainItem
     * @param addManagedItemBean addManagedItemBean
     * @throws ValueObjectValidationException an exception
     */
    private void bindNewManagedDomainToItem(ManagedItemVo domainItem, AddManagedItemBean addManagedItemBean)
        throws ValueObjectValidationException {

        ManagedItemVo item = (ManagedItemVo) flowScope.get(ControllerConstants.ITEM_KEY);

        if (addManagedItemBean.getParentFieldKey() == null) {
            ReflectionUtility.setValue(item, addManagedItemBean.getFieldKey(), domainItem);
        } else if (item != null) {

            // Get the collection of parents
            Collection parentValues = ReflectionUtility.getValue(item, addManagedItemBean.getParentFieldKey());
            
            // Set the parents array index position to the new domain item
            ReflectionUtility.setValue((ValueObject) parentValues.toArray()[addManagedItemBean.getIndex()], //NOPMD
                addManagedItemBean.getFieldKey(), domainItem);

            // Set the collection of parents on the item
            ReflectionUtility.setValue(item, addManagedItemBean.getParentFieldKey(), parentValues);
        } else {
            Errors errors = new Errors();
            errors.addError(ErrorKey.NO_ITEM_TO_BIND_ENTITY);

            throw new ValueObjectValidationException(errors);
        }
    }
}
