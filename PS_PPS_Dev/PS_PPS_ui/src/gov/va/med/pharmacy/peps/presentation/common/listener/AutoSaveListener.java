/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.listener;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.PartialSaveVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.EditManagedItemBean;
import gov.va.med.pharmacy.peps.presentation.common.pagetrail.FlowScope;
import gov.va.med.pharmacy.peps.service.common.session.ManagedItemService;


/**
 * AutoSaveListener detects when a session is about to be destroyed (auto-logout) and will save partial work that exists on 
 * the flowScope.
 */
public class AutoSaveListener implements HttpSessionListener {

    private static final Logger LOG = Logger.getLogger(AutoSaveListener.class);

    private ManagedItemService managedItemService;

    /**
     * do stuff on session creation
     * 
     * @param event session event object
     * 
     * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
     */
    @Override
    public void sessionCreated(HttpSessionEvent event) {

        // nothing to do for session creation.
    }

    /**
     * do stuff right before session destruction
     * 
     * @param event session event object
     * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();

        initSpringBasedService(event);

        if (this.getManagedItemService() == null) {
            LOG.error("ManagedItemService is null, will not be able to auto-save work in progress!");

            return;
        }

        Map<String, Object> fs = (Map<String, Object>) session.getAttribute(FlowScope.class.getName());

        if (fs == null) {
            LOG.error("FlowScope is not available from session.");

            return;
        }

        UserVo user = (UserVo) session.getAttribute("user");

        ManagedItemVo item = null;

        EditManagedItemBean emib = (EditManagedItemBean) fs.get(EditManagedItemBean.class.getName());

        if (emib != null) {
            item = emib.getItem();
        }

        if (item == null) {
            LOG.warn("Auto-save: item object is null.");

            return;
        }

        if (item.getEntityType() == null) {
            LOG.warn("Auto-save: item entity type is null.");

            return;
        }

        if (item.getEntityType().isDomainType()) {
            LOG.info("Entity type is a domain type, so item is not to be saved.");

            return;
        }

        PartialSaveVo partialSave = emib.getPartialSave();

        if (partialSave == null) {
            partialSave = new PartialSaveVo();
        }

        item.setPartialSave(true);
        partialSave.setComment(PPSConstants.AUTOSAVE_COMMENT);

        List<PartialSaveVo> partialSaves = getManagedItemService().retrievePartialSaves(user);
        List<PartialSaveVo> deletedPartialSaves = new ArrayList<PartialSaveVo>();

        for (PartialSaveVo p : partialSaves) {
            if (PPSConstants.AUTOSAVE_COMMENT.equals(p.getComment())) {
                deletedPartialSaves.add(p);
                partialSaves.remove(p);
            }
        }

        getManagedItemService().savePartial(item, partialSave.getComment(), user);
    }

    /**
     * get the managedItemService
     * 
     * @return the managedItemService
     */
    public ManagedItemService getManagedItemService() {
        return managedItemService;
    }

    /**
     * sets managedItemService field
     * 
     * @param managedItemService the managedItemService to set
     */
    public void setManagedItemService(ManagedItemService managedItemService) {
        this.managedItemService = managedItemService;
    }

    /**
     * initSpringBasedService
     *
     * @param event HttpSessionEvent
     */
    private void initSpringBasedService(HttpSessionEvent event) {
        if (this.managedItemService != null) {
            return;
        }

        HttpSession session = event.getSession();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
        Object obj = ctx.getBean("managedItemService");

        if (!(obj instanceof ManagedItemService)) {
            return;
        }

        if (obj != null) {
            this.managedItemService = (ManagedItemService) obj;
        }
    }
}
