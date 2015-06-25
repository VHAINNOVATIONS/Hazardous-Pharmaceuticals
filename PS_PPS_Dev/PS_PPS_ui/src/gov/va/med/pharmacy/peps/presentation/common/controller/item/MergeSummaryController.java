/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller.item;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import gov.va.med.pharmacy.peps.common.exception.OptimisticLockingException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.MergeVo;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.EditManagedItemBean;
import gov.va.med.pharmacy.peps.service.common.session.ManagedItemService;


/**
 * MergeSummaryController's brief summary
 * 
 * Details of MergeSummaryController's operations, special dependencies
 * or protocols developers shall know about when using the class.
 * (at)Controller - sgw 02/10/12 - disabling for possible future use rather than deleting the class
 */
@RequestMapping("{" + ControllerConstants.ENTITY_TYPE + "}/{" + ControllerConstants.ITEM_ID + "}")
public class MergeSummaryController extends AbstractManageItemController {

    /** managedItemService */
    @Autowired
    ManagedItemService managedItemService;

    /**
     * Default constructor
     *
     */
    public MergeSummaryController() {
        super();
    }

    /**
     * 
     * Display the merge modifications page
     *
     * @param itemId of item     
     * @param model Spring Model
     * @return merge summary view
     */
    @RequestMapping(value = "/mergeModifications.go", method = RequestMethod.GET)
    public String mergeModifications(@PathVariable(value = ControllerConstants.ITEM_ID) String itemId, Model model) {

        pageTrail.addPage("mergeSummary", "optimisticLock.mergeSummary", true);

        EditManagedItemBean editBean = (EditManagedItemBean) flowScope.get("editManagedItemBean");
        MergeVo mergeVo = (MergeVo) flowScope.get("mergeVo");
        
        if (editBean == null || mergeVo == null) {
            return REDIRECT + "/" + pageTrail.goToPreviousFlowUrl("/searchItems.go");
        }

        model.addAttribute(ControllerConstants.ITEM_KEY, editBean.getItem());
        model.addAttribute("mergeVo", mergeVo);
        model.addAttribute("modificatonSummary", new ModificationSummary());

        return "merge.summary";
    }

    /**
     * 
     * Accept the merge modification
     *
     * @param modifcationSummary modifcationSummary
     * @return redirect to confirm modifications view or merge modifications view if optimistic locking exception thrown
     * @throws ValidationException ValidationException
     */
    @RequestMapping(value = "/mergeModifications.go", method = RequestMethod.POST)
    public String commitMergeModifications(@ModelAttribute ModificationSummary modifcationSummary)
        throws ValidationException {

        EditManagedItemBean editBean = (EditManagedItemBean) flowScope.get("editManagedItemBean");
        MergeVo mergeVo = (MergeVo) flowScope.get("mergeVo");

        int index = 0;

        for (ModDifferenceVo difference : mergeVo.getMergeRequestDetails()) {
            difference.setAcceptChange(modifcationSummary.getModifications().get(index).getAcceptChange());
            index++;
        }

        ManagedItemVo item = editBean.getItem();

        ManagedItemVo oldItem = managedItemService.retrieve(item.getId(), item.getEntityType());

        try {
            item = managedItemService.commitMergeModifications(mergeVo, oldItem, getUser());
            flowScope.put(ControllerConstants.IS_REDIRECTED_FROM_MERGE_SUMMARY, true);
        } catch (OptimisticLockingException e) {
            flowInputScope.put("mergeVo",
                managedItemService.computeMergeInformation(editBean.getItem(), editBean.getDifferences(), getUser()));
            flowInputScope.put("editManagedItemBean", editBean);

            return REDIRECT + "/" + item.getEntityType().toString().toLowerCase() + "/" + item.getId()
                + "/mergeModifications.go";
        }

        return REDIRECT + "/" + item.getEntityType().toString().toLowerCase() + "/" + item.getId()
            + "/confirmModifications.go";
    }

    /**
     * 
     * Cancels the merge summary, redirects to the last URL in the page trail.
     *
     * @return the last URL in the page trail
     */
    @RequestMapping(value = "/mergeSummaryCancel.go", method = RequestMethod.POST)
    public String cancelMergeSummary() {
        return REDIRECT + pageTrail.goToPreviousUrl();
    }
}
