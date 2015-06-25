/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller.domainmapping;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.FdbDomainType;
import gov.va.med.pharmacy.peps.common.vo.FdbDomainVo;
import gov.va.med.pharmacy.peps.common.vo.GenericNameVo;
import gov.va.med.pharmacy.peps.common.vo.IngredientVo;
import gov.va.med.pharmacy.peps.common.vo.Role;
import gov.va.med.pharmacy.peps.presentation.common.controller.PepsController;
import gov.va.med.pharmacy.peps.presentation.common.spring.annotation.RoleNeeded;
import gov.va.med.pharmacy.peps.service.common.session.DomainService;
import gov.va.med.pharmacy.peps.service.common.session.FdbMappingService;
import gov.va.med.pharmacy.peps.service.common.utility.DomainMappingCsvFile;


/**
 * This controller handles the user guided mapping of terms located in the 
 * FDB "Enhanced Therapeutic Classification", "Ingredients", "Units of Measure",
 * "Dosage Form", and "Generic Name" tables to corresponding VA EPL tables.
 */
@Controller
public class DomainMappingController extends PepsController {

    private static final Logger LOG = Logger.getLogger(DomainMappingController.class);
    private static final String DOMAIN_MAPPING_BEAN_KEY = "domainMappingBean";

    /**
     * Mapping Service
     */
    @Autowired
    private FdbMappingService fdbMappingService;

    /**
     * Domain Service
     */
    @Autowired
    private DomainService eplDomainService;

    /**
     * Domain Choice
     * 
     * @return domainChoices
     */
    @ModelAttribute("domainChoices")
    public SortedMap<FdbDomainType, String> createDomainChoicesMap() {
        SortedMap<FdbDomainType, String> domainChoices = new TreeMap<FdbDomainType, String>();

        for (FdbDomainType type : FdbDomainType.values()) {
            domainChoices.put(
                type,
                getMessageSource().getMessage(
                    org.springframework.util.StringUtils.unqualify(type.getClass().getName()) + "." + type.name(), null,
                    request.getLocale()));

        }

        return domainChoices;
    }

    /**
     * Domain Tab
     * 
     * @param domainMappingBean domainMappingBean
     * @param domainChoice domainChoice
     * @param model model
     * @param doQuery doQuery
     * @param request request
     * @return domain.mapping
     */
    @RequestMapping(value = "domainMapping.go", method = RequestMethod.GET)
    public String getDomainMappingPage(
        @ModelAttribute(value = DOMAIN_MAPPING_BEAN_KEY) DomainMappingBean domainMappingBean,
        @RequestParam(value = "domainChoice", defaultValue = "DRUG_CLASS") FdbDomainType domainChoice,
        Model model,
        @RequestParam(value = "doQuery", required = false, defaultValue = "false") Boolean doQuery,
        HttpServletRequest request) {

        pageTrail.clearTrail();
        pageTrail.addPage("domainMapping", "Domain Mapping", true);

        domainMappingBean.setDomainChoice(domainChoice);

        List<FdbDomainVo> fdbTerms = null;

        if (doQuery) {
            fdbTerms = fdbMappingService.getFdbDomains(domainChoice, domainMappingBean.getFilterDate());

            if (fdbTerms != null) {
                Map<String, Long> associationMap = new HashMap<String, Long>();

                for (FdbDomainVo fdbTerm : fdbTerms) {
                    associationMap.put(fdbTerm.getFdbDomainId(), fdbTerm.getEplDomainId());
                }

                domainMappingBean.setAssociationMap(associationMap);

                model.addAttribute("fdbTerms", fdbTerms);

                model.addAttribute("eplTerms", getEplTerms(domainMappingBean, model));
            }
        }

        model.addAttribute(DOMAIN_MAPPING_BEAN_KEY, domainMappingBean);
        model.addAttribute("showResults", doQuery);


        return "domain.mapping";

    }

    /**
     * Post
     * 
     * @param request request
     * @param doQuery doQuery
     * @param domainMappingBean domainMappingBean
     * @return qualified url
     * @throws  ValidationException ValidationException 
     */
    @RoleNeeded(roles = { Role.PSS_PPSN_SUPERVISOR })
    @RequestMapping(value = "submitDomainMappingPage.go", method = RequestMethod.POST)
    public String submitDomainMappingPage(HttpServletRequest request,
        @ModelAttribute DomainMappingBean domainMappingBean)
        throws ValidationException {

        Map<String, Long> associationMap = domainMappingBean.getAssociationMap();

        List<FdbDomainVo> domains = new ArrayList<FdbDomainVo>();

        if (associationMap != null) {
            for (String fdbDomainId : associationMap.keySet()) {
                FdbDomainVo fdbDomain = new FdbDomainVo();
                fdbDomain.setFdbDomainId(fdbDomainId);
                fdbDomain.setEplDomainId(associationMap.get(fdbDomainId));
                domains.add(fdbDomain);
            }
        }

        fdbMappingService.assignFdbDomainAssociation(domainMappingBean.getDomainChoice(), domains, getUser());
        
        if (domainMappingBean.isSearchAfterSave()) {
            return REDIRECT + "domainMapping.go?isFirstRun=false&domainChoice=" + domainMappingBean.getDomainChoice()
                + "&filterDate=" + domainMappingBean.getFilterDate() + "&doQuery=true";
        } else {
            return REDIRECT + pageTrail.getCurrentUrl();
        }
    }

    /**
     * Import Domain Mapping
     * @param pRequest HttpServletRequest
     * @param pResponse HttpServletResponse
     * @return String redirect
     * @throws IOException IOException
     */
    @RequestMapping(value = "domainMappingImport.go", method = RequestMethod.POST)
    public String upload(HttpServletRequest pRequest, HttpServletResponse pResponse) throws IOException {

        MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest) pRequest;

        try {
            for (Iterator<String> iterator = mpRequest.getFileNames(); iterator.hasNext();) {
                String filename = iterator.next();
                LOG.debug("file: " + filename);

                MultipartFile multipartFile = mpRequest.getFile(filename);
                MultipartFile domainMappingFile = null;

                if ("domainMappingFile".equals(filename)) {
                    domainMappingFile = multipartFile;

                    String name = domainMappingFile.getOriginalFilename();

                    InputStream inputStream = domainMappingFile.getInputStream();

                    File f = new File("./tmp/" + name);
                    OutputStream out = new FileOutputStream(f);
                    byte[] buf = new byte[PPSConstants.I1024];
                    int len;

                    while ((len = inputStream.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }

                    out.close();
                    inputStream.close();

//                    System.out.println("\nFile is created...................................");
                    LOG.debug("path: " + f.getAbsolutePath());
                    LOG.debug("name: " + f.getName());

                    fdbMappingService.importDomainMapping(name, getUser());

                }

            }
        } catch (IllegalStateException e) {
            LOG.error("==>getFileFromMultipartFile(): IllegalStateExeption " + e.getMessage());
        } catch (Exception e) {
            LOG.error("Problem Importing Domain Mapping file: " + e);
        }

        return REDIRECT + pageTrail.getCurrentUrl();
    }

    /**
     * download Domain Mapping File
     * @param pRequest HttpServletRequest
     * @param pResponse HttpServletResponse
     * @return exported file
     * @throws IOException IOException
     */
    @RequestMapping(value = "domainMappingExport.go", method = RequestMethod.GET)
    public String download(HttpServletRequest pRequest, HttpServletResponse pResponse)
        throws IOException {

        List<FdbDomainVo> fdbDrugClassList = fdbMappingService.getFdbDomains(FdbDomainType.DRUG_CLASS, null);
        List<FdbDomainVo> fdbDrugIngredientList = fdbMappingService.getFdbDomains(FdbDomainType.DRUG_INGREDIENT, null);
        List<FdbDomainVo> fdbDrugUnitList = fdbMappingService.getFdbDomains(FdbDomainType.DRUG_UNIT, null);
        List<FdbDomainVo> fdbDosageFormList = fdbMappingService.getFdbDomains(FdbDomainType.DOSAGE_FORM, null);
        List<FdbDomainVo> fdbGenericNameList = fdbMappingService.getFdbDomains(FdbDomainType.GENERIC_NAME, null);

        DomainMappingCsvFile domainMappingCsvFile = new DomainMappingCsvFile();

        try {
            domainMappingCsvFile.createFile();
        } catch (Exception e) {
            LOG.error("Could not open Domain Mapping file for export: " + e);
        }

        try {
            domainMappingCsvFile.printDomainMapping(fdbDrugClassList, FdbDomainType.DRUG_CLASS,
                getEplTerms(FdbDomainType.DRUG_CLASS));
            domainMappingCsvFile.printDomainMapping(fdbDrugIngredientList, FdbDomainType.DRUG_INGREDIENT,
                getEplTerms(FdbDomainType.DRUG_INGREDIENT));
            domainMappingCsvFile.printDomainMapping(fdbDrugUnitList, FdbDomainType.DRUG_UNIT,
                getEplTerms(FdbDomainType.DRUG_UNIT));
            domainMappingCsvFile.printDomainMapping(fdbDosageFormList, FdbDomainType.DOSAGE_FORM,
                getEplTerms(FdbDomainType.DOSAGE_FORM));
            domainMappingCsvFile.printDomainMapping(fdbGenericNameList, FdbDomainType.GENERIC_NAME,
                getEplTerms(FdbDomainType.GENERIC_NAME));
        } catch (Exception e) {
            LOG.error("Problem writing Fdb Domain Mapping list: " + e);
        }

        domainMappingCsvFile.closeExport();

        downloadCSV(pResponse);

        return null;
    }

    /**
     * @param pResponse domainmapping file
     * @throws IOException 
     */
    private void downloadCSV(HttpServletResponse pResponse) throws IOException {
        InputStream in = null;
        ServletOutputStream out = null;
        String path = "./tmp/";
        String file = (path + "DomainMapping.csv");

        try {
            in = new FileInputStream(file);
            int length = file.length();

            pResponse.setContentType("application/octet-stream");
            pResponse.setHeader("Content-Disposition", "attachment;filename=" + "DomainMapping.csv");

            out = pResponse.getOutputStream();
            byte[] outputByte = new byte[length];
            int size = 0;

            while ((size = in.read(outputByte, 0, length)) != -1) {
                out.write(outputByte, 0, size);
            }

            in.close();
            out.flush();
            out.close();

        } catch (Exception x) {

            out.flush();
            out.close();
            LOG.error("==>downloadCSVFile() " + x.getMessage());

        }

    }

    /**
     * 
     * @param year year
     * @return days in Feb
     */
    private Integer daysInFebruary(Integer year) {

        // February has 29 days in any year evenly divisible by four,
        // EXCEPT for centennial years which are not also divisible by 400.

        return (((year % PPSConstants.I4 == 0) && ((!(year % PPSConstants.I100 == 0)) || (year % PPSConstants.I400 == 0))) 
            ? PPSConstants.I29 : PPSConstants.I28);
    }

    /**
     * 
     * @param domainMappingBean domainMappingBean
     * @param model model
     * @return eplTerms
     */
    private Map<Long, String> getEplTerms(DomainMappingBean domainMappingBean, Model model) {

        Map<Long, String> eplTerms = new LinkedHashMap<Long, String>();
        switch (domainMappingBean.getDomainChoice()) {

            // DomainMappingController process cases
            // process the DOSAGE_FORM term.
            case DOSAGE_FORM:
                for (DosageFormVo dosageFormVo : eplDomainService.getDosageForms()) {
                    eplTerms.put(Long.valueOf(dosageFormVo.getId()), dosageFormVo.getDosageFormName());
                }

                break;
                
             // process the DRUG_CLASS term.
            case DRUG_CLASS:
                for (DrugClassVo drugClassVo : eplDomainService.getDrugClasses()) {
                    eplTerms.put(Long.valueOf(drugClassVo.getId()), drugClassVo.getValue());
                }

                break;
                
             // process the DRUG_INGREDIENT term.
            case DRUG_INGREDIENT:
                for (IngredientVo ingredientVo : eplDomainService.getIngredientName()) {
                    eplTerms.put(Long.valueOf(ingredientVo.getId()), ingredientVo.getValue());
                }

                break;
                
             // process the DRUG_UNIT term.
            case DRUG_UNIT:
                for (DrugUnitVo drugUnitVo : eplDomainService.getUnits()) {
                    eplTerms.put(Long.valueOf(drugUnitVo.getId()), drugUnitVo.getValue());
                }

                break;
                
             // process the GENERIC_NAME term.
            case GENERIC_NAME:
                for (GenericNameVo genericNameVo : eplDomainService.getGenericNames()) {
                    eplTerms.put(Long.valueOf(genericNameVo.getId()), genericNameVo.getValue());
                }

                break;
            default:
        }

        return eplTerms;
    }

    /**
     * getEplTerms for DomainMappingController.
     * @param domain FdbDomainType
     * @return Map<Long, String>
     */
    private Map<Long, String> getEplTerms(FdbDomainType domain) {

        Map<Long, String> eplTerms = new LinkedHashMap<Long, String>();
        switch (domain) {

            case DOSAGE_FORM:
                for (DosageFormVo dosageFormVo : eplDomainService.getDosageForms()) {
                    eplTerms.put(Long.valueOf(dosageFormVo.getId()), dosageFormVo.getDosageFormName());
                }

                break;
            case DRUG_CLASS:
                for (DrugClassVo drugClassVo : eplDomainService.getDrugClasses()) {
                    eplTerms.put(Long.valueOf(drugClassVo.getId()), drugClassVo.getValue());
                }

                break;
            case DRUG_INGREDIENT:
                for (IngredientVo ingredientVo : eplDomainService.getIngredientName()) {
                    eplTerms.put(Long.valueOf(ingredientVo.getId()), ingredientVo.getValue());
                }

                break;
            case DRUG_UNIT:
                for (DrugUnitVo drugUnitVo : eplDomainService.getUnits()) {
                    eplTerms.put(Long.valueOf(drugUnitVo.getId()), drugUnitVo.getValue());
                }

                break;
            case GENERIC_NAME:
                for (GenericNameVo genericNameVo : eplDomainService.getGenericNames()) {
                    eplTerms.put(Long.valueOf(genericNameVo.getId()), genericNameVo.getValue());
                }

                break;
            default:

        }

        return eplTerms;
    }

    /**
     * isDate
     * @param dtStr dtStr
     * @return boolean
     */
    private boolean isDate(String dtStr) {
        Character separator = '/';
        Character zero = '0';
        boolean b = false;
        int minYear = PPSConstants.I1900;
        int maxYear =  PPSConstants.I2500;
        int[] daysInMonth = { 0, PPSConstants.I31, PPSConstants.I29, PPSConstants.I31, PPSConstants.I30, PPSConstants.I31, 
            PPSConstants.I30, PPSConstants.I31, PPSConstants.I31, PPSConstants.I30, PPSConstants.I31, PPSConstants.I30, 
            PPSConstants.I31 };

        int pos1 = dtStr.indexOf(separator);
        int pos2 = dtStr.indexOf(separator, pos1 + 1);

        if ((pos1 == -1) || (pos2 == -1)) {
            LOG.error("Domain Mapping Filter Date Exception : " + "MM/DD/YYYY");

            return false;
        }

        String strMonth = dtStr.substring(0, pos1);
        b = isInteger(strMonth);

        if (!b) {
            LOG.error("Domain Mapping Filter Date Exception (month) : " + "MM/DD/YYYY");

            return false;
        }

        String strDay = dtStr.substring(pos1 + 1, pos2);
        b = isInteger(strDay);

        if (!b) {
            LOG.error("Domain Mapping Filter Date Exception (day) : " + "MM/DD/YYYY");

            return false;
        }

        String strYear = dtStr.substring(pos2 + 1);
        b = isInteger(strYear);

        if (!b) {
            LOG.error("Domain Mapping Filter Date Exception (year) : " + "MM/DD/YYYY");

            return false;
        }

        String strYr = strYear;

        if ((strDay.charAt(0) == zero) && (strDay.length() > 1)) {
            strDay = strDay.substring(1);
        }

        if ((strMonth.charAt(0) == zero) && (strMonth.length() > 1)) {
            strMonth = strMonth.substring(1);
        }

        for (int i = 1; i <= PPSConstants.I3; i++) {

            if ((strYr.charAt(0) == zero) && (strYr.length() > 1)) {
                strYr = strYr.substring(1);
            }
        }

        int month = Integer.parseInt(strMonth);
        int day = Integer.parseInt(strDay);
        int year = Integer.parseInt(strYr);

        if ((strMonth.length() < 1) || (month < 1) || (month > PPSConstants.I12)) {
            LOG.error("Domain Mapping Filter Date Exception : " + "Please enter a valid month");

            return false;
        }

//        if (strDay.length()<1 || day<1 || day>31 || (month==2 && day> daysInFebruary(year)) || day > DaysInMonth.days[month]){

        if ((strDay.length() < 1) || (day < 1) || (day > PPSConstants.I31) || ((month == 2) && (day > daysInFebruary(year)))
            || (day > daysInMonth[month])) {
            LOG.error("Domain Mapping Filter Date Exception : " + "Please enter a valid day of month");

            return false;
        }

        if ((strYear.length() != PPSConstants.I4) || (year == 0) || (year < minYear) || (year > maxYear)) {
            LOG.error("Domain Mapping Filter Date Exception : " + "Please enter a valid 4 digit year between " + minYear
                + " and " + maxYear);

            return false;
        }

        if ((dtStr.indexOf(separator, pos2 + 1) != -1)
            || !(isInteger(stripCharsInBag(dtStr, Character.toString(separator))))) {
            LOG.error("Domain Mapping Filter Date Exception : " + "Please enter a valid date");

            return false;
        }

        // Entered date is valid

        return true;
    }

    /**
     * 
     * @param s s
     * @return boolean
     */
    private boolean isInteger(String s) {
        int i;

        for (i = 0; i < s.length(); i++) {

            // Check that current character is a number or not.
            Character c = s.charAt(i);

            if (!Character.isDigit(c)) {
                return false;
            }
        }

        // All characters are numbers.

        return true;
    }

    /**
     * 
     * @param s s
     * @param bag bag
     * @return returnString
     */
    private String stripCharsInBag(String s, String bag) {
        int i;
        String returnString = "";

        // Search through string's characters one by one.
        // If character is not in bag, append to returnString.

        for (i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);

            if (bag.indexOf(c) == -1) {
                returnString += c;
            }
        }

        return returnString;
    }

    /**
     * 
     * @param dt dt
     * @return boolean
     */
    private boolean validateDate(String dt) {
        if (!isDate(dt)) {

            return false;
        }

        return true;
    }

    /**
     * get the fdbMappingService
     * 
     * @return the fdbMappingService
     */
    public FdbMappingService getFdbMappingService() {
        return fdbMappingService;
    }

    /**
     * sets fdbMappingService field
     * 
     * @param fdbMappingService the fdbMappingService to set
     */
    public void setFdbMappingService(FdbMappingService fdbMappingService) {
        this.fdbMappingService = fdbMappingService;
    }

    /**
     * get the eplDomainService
     * 
     * @return the eplDomainService
     */
    public DomainService getEplDomainService() {
        return eplDomainService;
    }

    /**
     * sets eplDomainService field
     * 
     * @param eplDomainService the eplDomainService to set
     */
    public void setEplDomainService(DomainService eplDomainService) {
        this.eplDomainService = eplDomainService;
    }
}
