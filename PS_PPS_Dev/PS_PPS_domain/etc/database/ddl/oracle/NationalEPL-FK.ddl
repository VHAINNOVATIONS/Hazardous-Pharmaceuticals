
ALTER TABLE EPL_ADMIN_SCHEDULES
       ADD PRIMARY KEY (EPL_ID);


ALTER TABLE EPL_ATC_CANISTERS
       ADD PRIMARY KEY (ID);


ALTER TABLE EPL_NATIONAL_SETTINGS
       ADD PRIMARY KEY (ID);


ALTER TABLE EPL_CLASS_TYPES
       ADD PRIMARY KEY (ID);

ALTER TABLE EPL_NDF_SYNCH_QUEUE
       ADD PRIMARY KEY (ID);       

ALTER TABLE EPL_CMOP_ID_GENERATOR
       ADD PRIMARY KEY (PROD_NAME_INITIAL_LETTER);

ALTER TABLE EPL_FDB_PRODUCT
	ADD PRIMARY KEY (PRODUCT_ID_FK);
       
ALTER TABLE EPL_FDB_NDC
       ADD PRIMARY KEY (NDC_ID_FK);
       
ALTER TABLE EPL_CMOP_ID_HISTORY
       ADD PRIMARY KEY (CMOP_ID_USED);


ALTER TABLE EPL_CS_FED_SCHEDULES
       ADD PRIMARY KEY (EPL_ID);


ALTER TABLE EPL_DF_MED_RT_DF_ASSOCS
       ADD PRIMARY KEY (DOSAGE_FORM_ID_FK, MED_ROUTE_ID_FK);


ALTER TABLE EPL_DF_NOUNS
       ADD PRIMARY KEY (DOSAGE_FORM_EPL_ID_FK, NOUN);


ALTER TABLE EPL_DF_UNITS
       ADD PRIMARY KEY (DOSAGE_FORM_EPL_ID_FK, DRUG_UNIT_EPL_ID_FK);


ALTER TABLE EPL_DISPENSE_UNITS_PER_DOSE
       ADD PRIMARY KEY (DOSAGE_FORM_EPL_ID_FK, DISPENSE_UNIT_PER_DOSE);


ALTER TABLE EPL_DOSAGE_FORMS
       ADD PRIMARY KEY (EPL_ID);


ALTER TABLE EPL_DOSE_UNIT_SYNONYMS
       ADD PRIMARY KEY (DOSE_UNIT_SYNONYM, DOSE_UNIT_EPL_ID_FK);


ALTER TABLE EPL_DOSE_UNITS
       ADD PRIMARY KEY (EPL_ID);


ALTER TABLE EPL_DRUG_TEXT
       ADD PRIMARY KEY (EPL_ID);


ALTER TABLE EPL_DRUG_UNITS
       ADD PRIMARY KEY (EPL_ID);


ALTER TABLE EPL_DT_SYNONYMS
       ADD PRIMARY KEY (EPL_ID_DT_FK, DRUG_TEXT_SYNONYM);


ALTER TABLE EPL_HOSPITAL_LOCATIONS
       ADD PRIMARY KEY (ID);


ALTER TABLE EPL_IFCAP_ITEM_NUMBERS
       ADD PRIMARY KEY (ID);


ALTER TABLE EPL_INGREDIENTS
       ADD PRIMARY KEY (EPL_ID);


ALTER TABLE EPL_INTENDED_USES
       ADD PRIMARY KEY (EPL_ID);


ALTER TABLE EPL_INTERFACE_COUNTERS
       ADD PRIMARY KEY (INTERFACE_NAME, COUNTER_NAME);


ALTER TABLE EPL_ITEM_AUDIT_HISTORY
       ADD PRIMARY KEY (EPL_ID);


ALTER TABLE EPL_ITEM_AUDIT_HISTORY_DETAILS
       ADD PRIMARY KEY (EPL_ID);


ALTER TABLE EPL_LABS
       ADD PRIMARY KEY (ID);


ALTER TABLE EPL_LOCAL_CONSOLE_INFO
       ADD PRIMARY KEY (SITE_NUMBER, SOFTWARE_UPDATE_TYPE);


ALTER TABLE EPL_LOCAL_MED_ROUTES
       ADD PRIMARY KEY (EPL_ID);


ALTER TABLE EPL_LOCAL_POSSIBLE_DOSAGES
       ADD PRIMARY KEY (ID);


ALTER TABLE EPL_MANUFACTURERS
       ADD PRIMARY KEY (EPL_ID);


ALTER TABLE EPL_MED_INSTRUCT_WARDS
       ADD PRIMARY KEY (ID);


ALTER TABLE EPL_MEDICATION_INSTRUCTIONS
       ADD PRIMARY KEY (EPL_ID);


ALTER TABLE EPL_MIGRATION_FILE
       ADD PRIMARY KEY (FILE_ID);


ALTER TABLE EPL_MIGRATION_CONTROL
       ADD PRIMARY KEY (EPL_ID);


ALTER TABLE EPL_MULTI_TEXT
       ADD PRIMARY KEY (VADF_OWNER_ID_FK, VADF_ID_FK, TEXT);


ALTER TABLE EPL_NATIONAL_POSSIBLE_DOSAGES
       ADD PRIMARY KEY (ID);


ALTER TABLE EPL_NDC_BY_OUTPATIENT_SITE_NDC
       ADD PRIMARY KEY (ID);


ALTER TABLE EPL_NDCS
       ADD PRIMARY KEY (EPL_ID);


ALTER TABLE EPL_NOTIF_USER_PREFS
       ADD PRIMARY KEY (NOTIFICATION_TYPE, USER_ID_FK);


ALTER TABLE EPL_NOTIFICATION_STATUS
       ADD PRIMARY KEY (NOTIFICATION_ID_FK, USER_ID_FK);


ALTER TABLE EPL_NOTIFICATIONS
       ADD PRIMARY KEY (ID);


ALTER TABLE EPL_OI_ADMIN_SCHED_ASSOCS
       ADD PRIMARY KEY (EPL_ID_ADMIN_SCHED_FK, EPL_ID_OI_FK);


ALTER TABLE EPL_OI_DRUG_TEXT_L_ASSOCS
       ADD PRIMARY KEY (EPL_ID_OI_FK, DRUG_TEXT_ID_FK);


ALTER TABLE EPL_OI_DRUG_TEXT_N_ASSOCS
       ADD PRIMARY KEY (EPL_ID_OI_FK, DRUG_TEXT_ID_FK);


ALTER TABLE EPL_OI_MED_ROUTE_ASSOCS
       ADD PRIMARY KEY (EPL_ID_OI_FK, MED_ROUTE_ID_FK);


ALTER TABLE EPL_OI_SCHEDULE_TYPES
       ADD PRIMARY KEY (ID);


ALTER TABLE EPL_ORDER_UNITS
       ADD PRIMARY KEY (EPL_ID);


ALTER TABLE EPL_ORDERABLE_ITEMS
       ADD PRIMARY KEY (EPL_ID);


ALTER TABLE EPL_PACKAGE_TYPES
       ADD PRIMARY KEY (EPL_ID);


ALTER TABLE EPL_PACKAGE_USAGES
       ADD PRIMARY KEY (ID);


ALTER TABLE EPL_PACKAGE_USE_ASSOCS
       ADD PRIMARY KEY (EPL_ID_PACKAGE_USE_FK, 
              EPL_ID_LOCAL_MED_ROUTE_FK);


ALTER TABLE EPL_PARTIAL_SAVE_MGT
       ADD PRIMARY KEY (ID);


ALTER TABLE EPL_PHARMACY_SYSTEM
       ADD PRIMARY KEY (SITE_NUMBER);


ALTER TABLE EPL_PRINT_FIELDS
       ADD PRIMARY KEY (EPL_ID_PRINT_TEMPLATE_FK, PRINT_FIELD_NAME);


ALTER TABLE EPL_PRINT_TEMPLATES
       ADD PRIMARY KEY (EPL_ID);


ALTER TABLE EPL_PROD_DRUG_CLASS_ASSOCS
       ADD PRIMARY KEY (DRUG_CLASS_ID_FK, EPL_ID_PRODUCT_FK);


ALTER TABLE EPL_PROD_DRUG_TEXT_L_ASSOCS
       ADD PRIMARY KEY (DRUG_TEXT_ID_FK, EPL_ID_PROD_FK);


ALTER TABLE EPL_PROD_DRUG_TEXT_N_ASSOCS
       ADD PRIMARY KEY (EPL_ID_PROD_FK, DRUG_TEXT_ID_FK);


ALTER TABLE EPL_PROD_INGREDIENT_ASSOCS
       ADD PRIMARY KEY (INGREDIENT_ID_FK, EPL_ID_PRODUCT_FK);


ALTER TABLE EPL_PROD_SPEC_HANDLING_ASSOCS
       ADD PRIMARY KEY (EPL_ID_PROD_FK, EPL_ID_SPEC_HANDLING_FK);


ALTER TABLE EPL_PROD_WARN_LABEL_L_ASSOCS
       ADD PRIMARY KEY (EPL_ID_PROD_FK, EPL_ID_WARN_LABEL_FK, 
              SEQ_NUMBER);


ALTER TABLE EPL_PROD_WARN_LABEL_N_ASSOCS
       ADD PRIMARY KEY (EPL_ID_PROD_FK, EPL_ID_WARN_LABEL_FK, 
              SEQ_NUMBER);


ALTER TABLE EPL_PRODUCT_LABS
       ADD PRIMARY KEY (ID);


ALTER TABLE EPL_PRODUCT_VITALS
       ADD PRIMARY KEY (ID);


ALTER TABLE EPL_PRODUCTS
       ADD PRIMARY KEY (EPL_ID);


ALTER TABLE EPL_REQUEST_DETAILS
       ADD PRIMARY KEY (ID);


ALTER TABLE EPL_REQUESTS
       ADD PRIMARY KEY (ID);


ALTER TABLE EPL_SCHEDULE_TYPES
       ADD PRIMARY KEY (ID);


ALTER TABLE EPL_SEARCH_CRITERIA
       ADD PRIMARY KEY (EPL_ID_SEARCH_TEMPLATE_FK, SEARCH_FIELD_NAME);


ALTER TABLE EPL_SEARCH_TEMPLATES
       ADD PRIMARY KEY (EPL_ID);


ALTER TABLE EPL_SEQ_NUMS
       ADD PRIMARY KEY (TABLE_NAME);


ALTER TABLE EPL_SESSION_PREFERENCES
       ADD PRIMARY KEY (USER_ID_FK, PREF_NAME);


ALTER TABLE EPL_SITE_CONFIGS
       ADD PRIMARY KEY (SITE_NUMBER);


ALTER TABLE EPL_SITE_UPDATE_SCHEDULES
       ADD PRIMARY KEY (ID);


ALTER TABLE EPL_SPECIAL_HANDLING
       ADD PRIMARY KEY (EPL_ID);


ALTER TABLE EPL_STANDARD_MED_ROUTES
       ADD PRIMARY KEY (EPL_ID);


ALTER TABLE EPL_SYNONYMS
       ADD PRIMARY KEY (ID);


ALTER TABLE EPL_USERS
       ADD PRIMARY KEY (ID);


ALTER TABLE EPL_VA_DFS
       ADD PRIMARY KEY (ID);


ALTER TABLE EPL_VA_DISPENSE_UNITS
       ADD PRIMARY KEY (EPL_ID);


ALTER TABLE EPL_VA_DRUG_CLASSES
       ADD PRIMARY KEY (EPL_ID);


ALTER TABLE EPL_VA_GEN_NAMES
       ADD PRIMARY KEY (EPL_ID);


ALTER TABLE EPL_VADF_ASSOC_VALUES
       ADD PRIMARY KEY (VADF_OWNER_ID_FK, LIST_VALUE, VADF_ID_FK);


ALTER TABLE EPL_VADF_EDITABLE_PROPERTIES
       ADD PRIMARY KEY (VADF_OWNER_ID_FK, VADF_ID_FK);


ALTER TABLE EPL_VADF_LOVS
       ADD PRIMARY KEY (VADF_ID_FK, LIST_VALUE);


ALTER TABLE EPL_VADF_NONLIST_VALUES
       ADD PRIMARY KEY (VADF_OWNER_ID_FK, VADF_ID_FK);


ALTER TABLE EPL_VADF_OWNERS
       ADD PRIMARY KEY (ID);


ALTER TABLE EPL_VITALS
       ADD PRIMARY KEY (ID);


ALTER TABLE EPL_VUID_STATUS_HISTORY
       ADD PRIMARY KEY (ID);


ALTER TABLE EPL_WARDS
       ADD PRIMARY KEY (ID);


ALTER TABLE EPL_WARN_LABELS
       ADD PRIMARY KEY (EPL_ID);


ALTER TABLE EPL_FDB_ADD
       ADD PRIMARY KEY (NDC);

ALTER TABLE EPL_FDB_AUTO_ADD
       ADD PRIMARY KEY (NDC);
       
       
ALTER TABLE EPL_FDB_AUTO_UPDATE
       ADD PRIMARY KEY (EPL_ID);


ALTER TABLE EPL_FDB_UPDATE
       ADD PRIMARY KEY (EPL_ID);


ALTER TABLE EPL_FDB_DOSAGE_FORM
       ADD PRIMARY KEY (DRUG_DOSAGE_FORM_DESC);


ALTER TABLE EPL_FDB_DRUG_CLASS
       ADD PRIMARY KEY (FDB_DRUG_CLASS);


ALTER TABLE EPL_FDB_DRUG_INGREDIENT
       ADD PRIMARY KEY (FDB_DRUG_INGREDIENT);


ALTER TABLE EPL_FDB_DRUG_UNITS
       ADD PRIMARY KEY (FDB_DRUG_STRENGTHUNITS);

ALTER TABLE EPL_FDB_GENERIC_NAME
       ADD PRIMARY KEY (FDB_GENERIC_DRUGNAME);

ALTER TABLE EPL_FDB_MONOGRAPH_PEM
       ADD PRIMARY KEY (MONOGRAPH_ID);
       
ALTER TABLE EPL_FDB_GCNSEQNO_PEM
       ADD PRIMARY KEY (EPL_ID);
       
ALTER TABLE EPL_REDUCED_COPAY
       ADD PRIMARY KEY (ID);
   
ALTER TABLE EPL_REDUCED_COPAY
       ADD FOREIGN KEY (PRODUCT_FK)
                             REFERENCES EPL_PRODUCTS (EPL_ID);


ALTER TABLE EPL_ADMIN_SCHEDULES
       ADD FOREIGN KEY (SCHEDULE_TYPE_ID_FK)
                             REFERENCES EPL_SCHEDULE_TYPES (ID);


ALTER TABLE EPL_ATC_CANISTERS
       ADD FOREIGN KEY (EPL_ID_PRODUCT_FK)
                             REFERENCES EPL_PRODUCTS (EPL_ID);

ALTER TABLE EPL_DF_MED_RT_DF_ASSOCS
       ADD FOREIGN KEY (MED_ROUTE_ID_FK)
                             REFERENCES EPL_LOCAL_MED_ROUTES (EPL_ID);


ALTER TABLE EPL_DF_MED_RT_DF_ASSOCS
       ADD FOREIGN KEY (DOSAGE_FORM_ID_FK)
                             REFERENCES EPL_DOSAGE_FORMS (EPL_ID);


ALTER TABLE EPL_DF_NOUNS
       ADD FOREIGN KEY (DOSAGE_FORM_EPL_ID_FK)
                             REFERENCES EPL_DOSAGE_FORMS (EPL_ID);


ALTER TABLE EPL_DF_UNITS
       ADD FOREIGN KEY (DOSAGE_FORM_EPL_ID_FK)
                             REFERENCES EPL_DOSAGE_FORMS (EPL_ID);


ALTER TABLE EPL_DF_UNITS
       ADD FOREIGN KEY (DRUG_UNIT_EPL_ID_FK)
                             REFERENCES EPL_DRUG_UNITS (EPL_ID);


ALTER TABLE EPL_DISPENSE_UNITS_PER_DOSE
       ADD FOREIGN KEY (DOSAGE_FORM_EPL_ID_FK)
                             REFERENCES EPL_DOSAGE_FORMS (EPL_ID);


ALTER TABLE EPL_DOSE_UNIT_SYNONYMS
       ADD FOREIGN KEY (DOSE_UNIT_EPL_ID_FK)
                             REFERENCES EPL_DOSE_UNITS (EPL_ID);


ALTER TABLE EPL_DOSE_UNITS
       ADD FOREIGN KEY (ASSOC_REPLACED_BY_DU_ID_FK)
                             REFERENCES EPL_DOSE_UNITS (EPL_ID);


ALTER TABLE EPL_DT_SYNONYMS
       ADD FOREIGN KEY (EPL_ID_DT_FK)
                             REFERENCES EPL_DRUG_TEXT (EPL_ID);


ALTER TABLE EPL_HOSPITAL_LOCATIONS
       ADD FOREIGN KEY (EPL_ID_ADMIN_SCHED_FK)
                             REFERENCES EPL_ADMIN_SCHEDULES (EPL_ID);


ALTER TABLE EPL_IFCAP_ITEM_NUMBERS
       ADD FOREIGN KEY (PRODUCT_EPL_ID_FK)
                             REFERENCES EPL_PRODUCTS (EPL_ID);


ALTER TABLE EPL_INGREDIENTS
       ADD FOREIGN KEY (EPL_ID_PRIMARY_INGREDIENT_FK)
                             REFERENCES EPL_INGREDIENTS (EPL_ID);


ALTER TABLE EPL_ITEM_AUDIT_HISTORY
       ADD FOREIGN KEY (NOTIFICATION_ID_FK)
                             REFERENCES EPL_NOTIFICATIONS (ID);


ALTER TABLE EPL_ITEM_AUDIT_HISTORY_DETAILS
       ADD FOREIGN KEY (IAH_ID_FK)
                             REFERENCES EPL_ITEM_AUDIT_HISTORY (EPL_ID);


ALTER TABLE EPL_LABS
       ADD FOREIGN KEY (ORDERABLE_ITEM_ID_FK)
                             REFERENCES EPL_ORDERABLE_ITEMS (EPL_ID);


ALTER TABLE EPL_LOCAL_MED_ROUTES
       ADD FOREIGN KEY (EPL_ID_STANDARD_MED_RT_FK)
                             REFERENCES EPL_STANDARD_MED_ROUTES (EPL_ID);


ALTER TABLE EPL_LOCAL_POSSIBLE_DOSAGES
       ADD FOREIGN KEY (EPL_ID_PRODUCT_FK)
                             REFERENCES EPL_PRODUCTS (EPL_ID);


ALTER TABLE EPL_LOCAL_POSSIBLE_DOSAGES
       ADD FOREIGN KEY (EPL_DOSE_UNIT_ID_FK)
                             REFERENCES EPL_DOSE_UNITS (EPL_ID);


ALTER TABLE EPL_MED_INSTRUCT_WARDS
       ADD FOREIGN KEY (EPL_ID_MED_INSTRUCT_FK)
                             REFERENCES EPL_MEDICATION_INSTRUCTIONS (EPL_ID);


ALTER TABLE EPL_MEDICATION_INSTRUCTIONS
       ADD FOREIGN KEY (MED_ROUTE_ID_FK)
                             REFERENCES EPL_LOCAL_MED_ROUTES (EPL_ID);


ALTER TABLE EPL_MULTI_TEXT
       ADD FOREIGN KEY (VADF_OWNER_ID_FK)
                             REFERENCES EPL_VADF_OWNERS (ID);


ALTER TABLE EPL_MULTI_TEXT
       ADD FOREIGN KEY (VADF_ID_FK)
                             REFERENCES EPL_VA_DFS (ID);


ALTER TABLE EPL_NATIONAL_POSSIBLE_DOSAGES
       ADD FOREIGN KEY (EPL_ID_PRODUCT_FK)
                             REFERENCES EPL_PRODUCTS (EPL_ID);


ALTER TABLE EPL_NDC_BY_OUTPATIENT_SITE_NDC
       ADD FOREIGN KEY (EPL_ID_PRODUCT_FK)
                             REFERENCES EPL_PRODUCTS (EPL_ID);


ALTER TABLE EPL_NDCS
       ADD FOREIGN KEY (PACKAGE_TYPE_ID_FK)
                             REFERENCES EPL_PACKAGE_TYPES (EPL_ID);


ALTER TABLE EPL_NDCS
       ADD FOREIGN KEY (MANUFACTURER_ID_FK)
                             REFERENCES EPL_MANUFACTURERS (EPL_ID);


ALTER TABLE EPL_NDCS
       ADD FOREIGN KEY (ORDER_UNIT_ID_FK)
                             REFERENCES EPL_ORDER_UNITS (EPL_ID);


ALTER TABLE EPL_NDCS
       ADD FOREIGN KEY (EPL_ID_PRODUCT_FK)
                             REFERENCES EPL_PRODUCTS (EPL_ID);


ALTER TABLE EPL_NOTIF_USER_PREFS
       ADD FOREIGN KEY (USER_ID_FK)
                             REFERENCES EPL_USERS (ID);


ALTER TABLE EPL_NOTIFICATION_STATUS
       ADD FOREIGN KEY (USER_ID_FK)
                             REFERENCES EPL_USERS (ID);


ALTER TABLE EPL_NOTIFICATION_STATUS
       ADD FOREIGN KEY (NOTIFICATION_ID_FK)
                             REFERENCES EPL_NOTIFICATIONS (ID);


ALTER TABLE EPL_OI_ADMIN_SCHED_ASSOCS
       ADD FOREIGN KEY (EPL_ID_ADMIN_SCHED_FK)
                             REFERENCES EPL_ADMIN_SCHEDULES (EPL_ID);


ALTER TABLE EPL_OI_ADMIN_SCHED_ASSOCS
       ADD FOREIGN KEY (EPL_ID_OI_FK)
                             REFERENCES EPL_ORDERABLE_ITEMS (EPL_ID);


ALTER TABLE EPL_OI_DRUG_TEXT_L_ASSOCS
       ADD FOREIGN KEY (DRUG_TEXT_ID_FK)
                             REFERENCES EPL_DRUG_TEXT (EPL_ID);


ALTER TABLE EPL_OI_DRUG_TEXT_L_ASSOCS
       ADD FOREIGN KEY (EPL_ID_OI_FK)
                             REFERENCES EPL_ORDERABLE_ITEMS (EPL_ID);


ALTER TABLE EPL_OI_DRUG_TEXT_N_ASSOCS
       ADD FOREIGN KEY (DRUG_TEXT_ID_FK)
                             REFERENCES EPL_DRUG_TEXT (EPL_ID);


ALTER TABLE EPL_OI_DRUG_TEXT_N_ASSOCS
       ADD FOREIGN KEY (EPL_ID_OI_FK)
                             REFERENCES EPL_ORDERABLE_ITEMS (EPL_ID);


ALTER TABLE EPL_OI_MED_ROUTE_ASSOCS
       ADD FOREIGN KEY (MED_ROUTE_ID_FK)
                             REFERENCES EPL_LOCAL_MED_ROUTES (EPL_ID);


ALTER TABLE EPL_OI_MED_ROUTE_ASSOCS
       ADD FOREIGN KEY (EPL_ID_OI_FK)
                             REFERENCES EPL_ORDERABLE_ITEMS (EPL_ID);


ALTER TABLE EPL_ORDERABLE_ITEMS
       ADD FOREIGN KEY (ASSOC_NATIONAL_OI_ID_FK)
                             REFERENCES EPL_ORDERABLE_ITEMS (EPL_ID);


ALTER TABLE EPL_ORDERABLE_ITEMS
       ADD FOREIGN KEY (DOSAGE_FORM_ID_FK)
                             REFERENCES EPL_DOSAGE_FORMS (EPL_ID);

ALTER TABLE EPL_ORDERABLE_ITEMS
       ADD FOREIGN KEY (STANDARD_MED_ROUTE_ID_FK)
                             REFERENCES EPL_STANDARD_MED_ROUTES (EPL_ID);

ALTER TABLE EPL_ORDERABLE_ITEMS
       ADD FOREIGN KEY (OI_SCHED_TYPE_ID_FK)
                             REFERENCES EPL_OI_SCHEDULE_TYPES (ID);


ALTER TABLE EPL_PACKAGE_USE_ASSOCS
       ADD FOREIGN KEY (EPL_ID_PACKAGE_USE_FK)
                             REFERENCES EPL_PACKAGE_USAGES (ID);


ALTER TABLE EPL_PACKAGE_USE_ASSOCS
       ADD FOREIGN KEY (EPL_ID_LOCAL_MED_ROUTE_FK)
                             REFERENCES EPL_LOCAL_MED_ROUTES (EPL_ID);


ALTER TABLE EPL_PARTIAL_SAVE_MGT
       ADD FOREIGN KEY (EPL_ID_NDC_FK)
                             REFERENCES EPL_NDCS (EPL_ID);


ALTER TABLE EPL_PARTIAL_SAVE_MGT
       ADD FOREIGN KEY (EPL_ID_PRODUCT_FK)
                             REFERENCES EPL_PRODUCTS (EPL_ID);


ALTER TABLE EPL_PARTIAL_SAVE_MGT
       ADD FOREIGN KEY (EPL_ID_OI_FK)
                             REFERENCES EPL_ORDERABLE_ITEMS (EPL_ID);


ALTER TABLE EPL_PRINT_FIELDS
       ADD FOREIGN KEY (EPL_ID_PRINT_TEMPLATE_FK)
                             REFERENCES EPL_PRINT_TEMPLATES (EPL_ID);


ALTER TABLE EPL_PROD_DRUG_CLASS_ASSOCS
       ADD FOREIGN KEY (EPL_ID_PRODUCT_FK)
                             REFERENCES EPL_PRODUCTS (EPL_ID);


ALTER TABLE EPL_PROD_DRUG_CLASS_ASSOCS
       ADD FOREIGN KEY (DRUG_CLASS_ID_FK)
                             REFERENCES EPL_VA_DRUG_CLASSES (EPL_ID);


ALTER TABLE EPL_PROD_DRUG_TEXT_L_ASSOCS
       ADD FOREIGN KEY (EPL_ID_PROD_FK)
                             REFERENCES EPL_PRODUCTS (EPL_ID);


ALTER TABLE EPL_PROD_DRUG_TEXT_L_ASSOCS
       ADD FOREIGN KEY (DRUG_TEXT_ID_FK)
                             REFERENCES EPL_DRUG_TEXT (EPL_ID);


ALTER TABLE EPL_PROD_DRUG_TEXT_N_ASSOCS
       ADD FOREIGN KEY (DRUG_TEXT_ID_FK)
                             REFERENCES EPL_DRUG_TEXT (EPL_ID);


ALTER TABLE EPL_PROD_DRUG_TEXT_N_ASSOCS
       ADD FOREIGN KEY (EPL_ID_PROD_FK)
                             REFERENCES EPL_PRODUCTS (EPL_ID);


ALTER TABLE EPL_PROD_INGREDIENT_ASSOCS
       ADD FOREIGN KEY (EPL_ID_PRODUCT_FK)
                             REFERENCES EPL_PRODUCTS (EPL_ID);

                             
ALTER TABLE EPL_PROD_INGREDIENT_ASSOCS
       ADD FOREIGN KEY (DRUG_UNIT_ID_FK)
                             REFERENCES EPL_DRUG_UNITS (EPL_ID);


ALTER TABLE EPL_PROD_INGREDIENT_ASSOCS
       ADD FOREIGN KEY (INGREDIENT_ID_FK)
                             REFERENCES EPL_INGREDIENTS (EPL_ID);


ALTER TABLE EPL_PROD_SPEC_HANDLING_ASSOCS
       ADD FOREIGN KEY (EPL_ID_PROD_FK)
                             REFERENCES EPL_PRODUCTS (EPL_ID);


ALTER TABLE EPL_PROD_SPEC_HANDLING_ASSOCS
       ADD FOREIGN KEY (EPL_ID_SPEC_HANDLING_FK)
                             REFERENCES EPL_SPECIAL_HANDLING (EPL_ID);


ALTER TABLE EPL_PROD_WARN_LABEL_L_ASSOCS
       ADD FOREIGN KEY (EPL_ID_PROD_FK)
                             REFERENCES EPL_PRODUCTS (EPL_ID);


ALTER TABLE EPL_PROD_WARN_LABEL_L_ASSOCS
       ADD FOREIGN KEY (EPL_ID_WARN_LABEL_FK)
                             REFERENCES EPL_WARN_LABELS (EPL_ID);


ALTER TABLE EPL_PROD_WARN_LABEL_N_ASSOCS
       ADD FOREIGN KEY (EPL_ID_PROD_FK)
                             REFERENCES EPL_PRODUCTS (EPL_ID);


ALTER TABLE EPL_PROD_WARN_LABEL_N_ASSOCS
       ADD FOREIGN KEY (EPL_ID_WARN_LABEL_FK)
                             REFERENCES EPL_WARN_LABELS (EPL_ID);


ALTER TABLE EPL_PRODUCT_LABS
       ADD FOREIGN KEY (EPL_ID_PRODUCT_FK)
                             REFERENCES EPL_PRODUCTS (EPL_ID);


ALTER TABLE EPL_PRODUCT_VITALS
       ADD FOREIGN KEY (EPL_ID_PRODUCT_FK)
                             REFERENCES EPL_PRODUCTS (EPL_ID);


ALTER TABLE EPL_PRODUCTS
       ADD FOREIGN KEY (EPL_ID_OI_FK)
                             REFERENCES EPL_ORDERABLE_ITEMS (EPL_ID);


ALTER TABLE EPL_PRODUCTS
       ADD FOREIGN KEY (ORDER_UNIT_ID_FK)
                             REFERENCES EPL_ORDER_UNITS (EPL_ID);


ALTER TABLE EPL_PRODUCTS
       ADD FOREIGN KEY (DISPENSE_UNIT_ID_FK)
                             REFERENCES EPL_VA_DISPENSE_UNITS (
              EPL_ID);


ALTER TABLE EPL_PRODUCTS
       ADD FOREIGN KEY (VA_GEN_NAME_ID_FK)
                             REFERENCES EPL_VA_GEN_NAMES (EPL_ID);


ALTER TABLE EPL_PRODUCTS
       ADD FOREIGN KEY (DOSE_UNIT_ID_FK)
                             REFERENCES EPL_DOSE_UNITS (EPL_ID);

ALTER TABLE EPL_PRODUCTS
       ADD FOREIGN KEY (DRUG_UNIT_ID_FK)
                             REFERENCES EPL_DRUG_UNITS (EPL_ID);


ALTER TABLE EPL_PRODUCTS
       ADD FOREIGN KEY (CS_FED_SCHED_ID_FK)
                             REFERENCES EPL_CS_FED_SCHEDULES (EPL_ID);


ALTER TABLE EPL_REQUEST_DETAILS
       ADD FOREIGN KEY (REQUEST_ID_FK)
                             REFERENCES EPL_REQUESTS (ID);


ALTER TABLE EPL_REQUESTS
       ADD FOREIGN KEY (EPL_ID_OI_FK)
                             REFERENCES EPL_ORDERABLE_ITEMS (EPL_ID);


ALTER TABLE EPL_REQUESTS
       ADD FOREIGN KEY (EPL_ID_NDC_FK)
                             REFERENCES EPL_NDCS (EPL_ID);


ALTER TABLE EPL_REQUESTS
       ADD FOREIGN KEY (EPL_ID_PRODUCT_FK)
                             REFERENCES EPL_PRODUCTS (EPL_ID);


ALTER TABLE EPL_SEARCH_CRITERIA
       ADD FOREIGN KEY (EPL_ID_SEARCH_TEMPLATE_FK)
                             REFERENCES EPL_SEARCH_TEMPLATES (EPL_ID);


ALTER TABLE EPL_SEARCH_TEMPLATES
       ADD FOREIGN KEY (EPL_ID_PRINT_TEMPLATE_FK)
                             REFERENCES EPL_PRINT_TEMPLATES (EPL_ID);


ALTER TABLE EPL_SEARCH_TEMPLATES
       ADD FOREIGN KEY (USER_ID_FK)
                             REFERENCES EPL_USERS (ID);


ALTER TABLE EPL_SESSION_PREFERENCES
       ADD FOREIGN KEY (USER_ID_FK)
                             REFERENCES EPL_USERS (ID);


ALTER TABLE EPL_STANDARD_MED_ROUTES
       ADD FOREIGN KEY (ASSOC_REPL_BY_VHA_STD_TERM_FK)
                             REFERENCES EPL_STANDARD_MED_ROUTES (EPL_ID);


ALTER TABLE EPL_SYNONYMS
       ADD FOREIGN KEY (INTENDED_USE_ID_FK)
                             REFERENCES EPL_INTENDED_USES (EPL_ID);


ALTER TABLE EPL_SYNONYMS
       ADD FOREIGN KEY (ORDER_UNIT_ID_FK)
                             REFERENCES EPL_ORDER_UNITS (EPL_ID);


ALTER TABLE EPL_SYNONYMS
       ADD FOREIGN KEY (EPL_ID_PRODUCT_FK)
                             REFERENCES EPL_PRODUCTS (EPL_ID);


ALTER TABLE EPL_VA_DRUG_CLASSES
       ADD FOREIGN KEY (CLASS_TYPE_ID_FK)
                             REFERENCES EPL_CLASS_TYPES (ID);


ALTER TABLE EPL_VA_DRUG_CLASSES
       ADD FOREIGN KEY (PARENT_CLASS_ID_FK)
                             REFERENCES EPL_VA_DRUG_CLASSES (EPL_ID);


ALTER TABLE EPL_VADF_ASSOC_VALUES
       ADD FOREIGN KEY (VADF_OWNER_ID_FK)
                             REFERENCES EPL_VADF_OWNERS (ID);


ALTER TABLE EPL_VADF_ASSOC_VALUES
       ADD FOREIGN KEY (VADF_ID_FK, LIST_VALUE)
                             REFERENCES EPL_VADF_LOVS (VADF_ID_FK, 
              LIST_VALUE);


ALTER TABLE EPL_VADF_EDITABLE_PROPERTIES
       ADD FOREIGN KEY (VADF_OWNER_ID_FK)
                             REFERENCES EPL_VADF_OWNERS (ID);


ALTER TABLE EPL_VADF_EDITABLE_PROPERTIES
       ADD FOREIGN KEY (VADF_ID_FK)
                             REFERENCES EPL_VA_DFS (ID);


ALTER TABLE EPL_VADF_LOVS
       ADD FOREIGN KEY (VADF_ID_FK)
                             REFERENCES EPL_VA_DFS (ID);


ALTER TABLE EPL_VADF_NONLIST_VALUES
       ADD FOREIGN KEY (VADF_OWNER_ID_FK)
                             REFERENCES EPL_VADF_OWNERS (ID);


ALTER TABLE EPL_VADF_NONLIST_VALUES
       ADD FOREIGN KEY (VADF_ID_FK)
                             REFERENCES EPL_VA_DFS (ID);


ALTER TABLE EPL_VADF_OWNERS
       ADD FOREIGN KEY (EPL_ID_OI_FK)
                             REFERENCES EPL_ORDERABLE_ITEMS (EPL_ID);


ALTER TABLE EPL_VADF_OWNERS
       ADD FOREIGN KEY (EPL_ID_NDC_FK)
                             REFERENCES EPL_NDCS (EPL_ID);


ALTER TABLE EPL_VADF_OWNERS
       ADD FOREIGN KEY (EPL_ID_PRODUCT_FK)
                             REFERENCES EPL_PRODUCTS (EPL_ID);


ALTER TABLE EPL_VITALS
       ADD FOREIGN KEY (ORDERABLE_ITEM_ID_FK)
                             REFERENCES EPL_ORDERABLE_ITEMS (EPL_ID);


ALTER TABLE EPL_WARDS
       ADD FOREIGN KEY (EPL_ID_ADMIN_SCHED_FK)
                             REFERENCES EPL_ADMIN_SCHEDULES (EPL_ID);


ALTER TABLE EPL_WARN_LABELS
       ADD FOREIGN KEY (ASSOC_WARNING_MAPPING_ID_FK)
                             REFERENCES EPL_WARN_LABELS (EPL_ID);


CREATE INDEX INDEX2_PROD ON EPL_PRODUCTS (VA_PRODUCT_NAME);
CREATE INDEX INDEX2_NDC ON EPL_NDCS (NDC_NUMBER);
CREATE INDEX INDEX2_OI ON EPL_ORDERABLE_ITEMS (OI_NAME);
CREATE INDEX INDEX2_VUID_STATUS_HIST ON EPL_VUID_STATUS_HISTORY (VUID);
CREATE UNIQUE INDEX INDEX2_SETTINGS ON EPL_NATIONAL_SETTINGS (KEY_NAME);


