CREATE OR REPLACE PROCEDURE PPSNEPL.LOAD_HAZARD_DISPOSE IS

/******************************************************************************
   NAME:       LOAD_HAZARD_DISPOSE
   PURPOSE:    

   REVISIONS:
   Ver        Date        Author           Description
   ---------  ----------  ---------------  ------------------------------------
   1.0        6/04/2013   vhaispraya       1. Created this procedure.

   NOTES:

   Automatically available Auto Replace Keywords:
      Object Name:     LOAD_HAZARD_DISPOSE
      Sysdate:         6/04/2013
      Date and Time:   6/04/2013
      Username:        vhaispraya (set in TOAD Options, Procedure Editor)
      Table Name:       (set in the "New PL/SQL Object" dialog)

******************************************************************************/
    cursor dispose_input_cursor is
    select ndc_number, PRODUCT_NAME, PRIMARY_EPA_CODE, WASTE_SORT_CODE, DOT_SHIPPING_NAME, LOAD_STATUS, LOAD_DTM 
    from PPSNEPL.hazard_dispose_input
    order by ndc_number;

    cursor ndc_cursor (dispose_ndc_number PPSNEPL.EPL_NDCS.ndc_no_dashes%TYPE) is
    select epl_id, ndc_number, ndc_no_dashes, epl_id_product_fk
    from PPSNEPL.EPL_NDCS
    where ndc_no_dashes = dispose_ndc_number
    order by epl_id_product_fk;
    
    cursor owner_cursor (dispose_product_number PPSNEPL.EPL_VADF_OWNERS.EPL_ID_PRODUCT_FK%TYPE) is
    select OWNERS.ID, VADF_OWNER_TYPE, OWNERS.VADF_OWNER_ID, OWNERS.EPL_ID_OI_FK, OWNERS.EPL_ID_PRODUCT_FK, 
    PRODUCTS.VA_PRODUCT_NAME, GENERICS.GENERIC_NAME
    from PPSNEPL.EPL_VADF_OWNERS OWNERS left outer join PPSNEPL.EPL_PRODUCTS PRODUCTS
    ON OWNERS.EPL_ID_PRODUCT_FK = PRODUCTS.EPL_ID
    LEFT OUTER JOIN PPSNEPL.EPL_VA_GEN_NAMES GENERICS
    ON PRODUCTS.VA_GEN_NAME_ID_FK = GENERICS.EPL_ID    
    where OWNERS.EPL_ID_PRODUCT_FK = dispose_product_number
    order by OWNERS.ID;

    cursor product_cursor (g_product_name PPSNEPL.EPL_PRODUCTS.VA_PRODUCT_NAME%TYPE) is
    select PRODUCTS.EPL_ID, PRODUCTS.VA_PRODUCT_NAME, OWNERS.ID
    from PPSNEPL.EPL_PRODUCTS PRODUCTS LEFT OUTER JOIN PPSNEPL.EPL_VADF_OWNERS OWNERS
    ON PRODUCTS.EPL_ID  = OWNERS.EPL_ID_PRODUCT_FK
    LEFT OUTER JOIN PPSNEPL.EPL_VA_GEN_NAMES GENERICS
    ON PRODUCTS.VA_GEN_NAME_ID_FK = GENERICS.EPL_ID   
    where GENERICS.GENERIC_NAME = g_product_name
    order by PRODUCTS.EPL_ID;
    
    cursor synonym_cursor (g_product_id PPSNEPL.EPL_SYNONYMS.EPL_ID_PRODUCT_FK%TYPE) is
    select SYNONYMS.ID, SYNONYMS.SYNONYM_NAME
    from PPSNEPL.EPL_SYNONYMS SYNONYMS
    WHERE SYNONYMS.EPL_ID_PRODUCT_FK = g_product_id  
    order by SYNONYMS.SYNONYM_NAME;
    
       

    cursor nonlist_cursor (dispose_owner_number PPSNEPL.EPL_VADF_NONLIST_VALUES.VADF_OWNER_ID_FK%TYPE) is
    select VADF_OWNER_ID_FK, VADF_ID_FK, VA_DF_VALUE
    from PPSNEPL.EPL_VADF_NONLIST_VALUES
    where VADF_OWNER_ID_FK = dispose_owner_number and VADF_ID_FK = 34
    order by VADF_OWNER_ID_FK;
    
    cursor alternate_cursor (dispose_product_name PPSNEPL.HAZARD_DISPOSE_INPUT.PRODUCT_NAME%TYPE) is
    select DISTINCT PRODUCT.VA_PRODUCT_NAME , PRODUCT.PRODUCT_NDC, PRODUCT.EPL_ID, OWNERS.ID, SYNONYM_NAME,
    PRODUCT.VA_GEN_NAME_ID_FK
    from PPSNEPL.HAZARD_DISPOSE_INPUT DISPOSE, PPSNEPL.EPL_PRODUCTS PRODUCT, PPSNEPL.EPL_VADF_OWNERS OWNERS,
    PPSNEPL.EPL_SYNONYMS SYNONYMS
    where DISPOSE.PRODUCT_NAME = dispose_product_name and
    PRODUCT.EPL_ID = OWNERS.EPL_ID_PRODUCT_FK and
    PRODUCT.EPL_ID = SYNONYMS.EPL_ID_PRODUCT_FK and
    UPPER(SYNONYMS.SYNONYM_NAME) like '%'||UPPER(DISPOSE.PRODUCT_NAME)||'%';

    cursor generic_cursor (a_generic_id  PPSNEPL.EPL_PRODUCTS.VA_GEN_NAME_ID_FK%TYPE) is
    select DISTINCT PRODUCT.VA_PRODUCT_NAME , PRODUCT.PRODUCT_NDC, PRODUCT.EPL_ID, OWNERS.ID, GENERIC_NAME,
    PRODUCT.VA_GEN_NAME_ID_FK
    from PPSNEPL.EPL_PRODUCTS PRODUCT LEFT OUTER JOIN PPSNEPL.EPL_VADF_OWNERS OWNERS
    ON PRODUCT.EPL_ID  = OWNERS.EPL_ID_PRODUCT_FK
    LEFT OUTER JOIN PPSNEPL.EPL_VA_GEN_NAMES GENERICS
    ON PRODUCT.VA_GEN_NAME_ID_FK = GENERICS.EPL_ID   
    where GENERICS.EPL_ID = a_generic_id
    order by PRODUCT.EPL_ID;   
    
    d_true                          varchar2(5) := 'true';
    d_vadf_id_epa                   number(30) := 0; 
    d_vadf_id_waste                 number(30) := 0; 
    d_vadf_id_ship                  number(30) := 0;
    d_load_status                   varchar2(50);
    d_load_dtm                      timestamp(6); 

    dispose_ndc_number              number(30) := 0;
    dispose_product_number          number(30) := 0;  
    dispose_owner_number            number(30) := 0;      
    dispose_product_name            varchar2(100);  
    owners_cnt                       number(30) := 0;
    d_epl_id                        number(30) := 0;
    d_ndc_number                    varchar2(50); 
    d_ndc_no_dashes                 varchar2(50);
    d_epl_id_product_fk             number(30) := 0;
    d_id                            number(30) := 0;
    d_owner_type                    varchar2(50);  
    d_vadf_owner_id                 number(30) := 0;
    d_epl_id_product_pk             number(30) := 0;
    d_nonlist_value                 varchar2(2000);  
    d_product_name                  varchar2(100);
    d_primary_epa_code              varchar2(50);
    d_waste_sort_code               varchar2(50);
    d_dot_shipping_name             varchar2(150);
    a_product_name                  varchar2(100);
    a_synonym_name                  varchar2(100);    
    a_ndc_number                    varchar2(50) := 0; 
    v_product_name                  varchar2(100);
    g_product_id                    number(30) := 0;
    g_product_name                  varchar2(100);
    g_va_product_name                  varchar2(100);    
    g_generic_id                    number(30) := 0;
    g_generic_name                  varchar2(100);
    s_synonym_name                  varchar2(100);
    v_owner_id                      number(30) := 0;
    v_product_id                    number(30) := 0;
    a_product_id                      number(30) := 0;
    a_owner_id                      number(30) := 0;
    a_generic_id                    number(30) := 0;
--    v_epl_id                        number(30) := 0;
    v_upd_cnt                       number(30) := 0;
    
  
    ndc_match_cnt                   number(30) := 0;
    va_product_match_cnt            number(30) := 0;
    dispose_product_match_cnt       number(30) := 0;
    sa_match_cnt                    number(30) := 0;
    ag_product_cnt                  number(30) := 0;
    dispose_input_cnt               number(30) := 0;

    dispose_cnt                     number(30) := 0;
    dispose_found_cnt               number(30) := 0;
    owner_found_cnt                 number(30) := 0;
    dispose_upd_cnt                 number(30) := 0;
    dispose_u_cnt                   number(30) := 0;
    dispose_d_cnt                   number(30) := 0;
    dispose_i_cnt                   number(30) := 0;
    total_upd_cnt                   number(30) := 0;
    total_found_cnt                 number(30) := 0;
    total_notfound_cnt              number(30) := 0;
    nonlist_found_cnt               number(30) := 0;
    update_row_count                number(30) := 0;
    a_product_cnt                   number(30) := 0;
    v_product_cnt                   number(30) := 0;
    d_start                         varchar2(30);
    d_end                           varchar2(30);
    u_end                           varchar2(30);
    epa_exists                      number(30) := 0;
    dot_exists                     number(30) := 0;
    waste_exists                   number(30) := 0;
    dflag_exists                   number(30) := 0;
BEGIN
   /* The number of iterations will equal the number of rows
      returned by dispose_input_cursor. */
    -- get ids for new fields      
    select ID INTO d_vadf_id_epa from PPSNEPL.EPL_VA_DFS
    where VADF_NAME = 'primary.epa.code';

    select ID INTO d_vadf_id_waste from PPSNEPL.EPL_VA_DFS
    where VADF_NAME = 'waste.sort.code';

    select ID INTO d_vadf_id_ship from PPSNEPL.EPL_VA_DFS
    where VADF_NAME = 'dot.shipping.name';
    
    SELECT TO_CHAR
    (SYSDATE, 'MM-DD-YYYY HH24:MI:SS') into d_start
     FROM DUAL;
    
    DBMS_OUTPUT.PUT_LINE('EPA STERICYCLE Rx WASTE CHARACTERIZATION - Hazard to Dispose - Initial Load ');
    DBMS_OUTPUT.PUT_LINE('Load Start: '|| d_start);
    DBMS_OUTPUT.PUT_LINE(' ');
    DBMS_OUTPUT.PUT_LINE(' ');
    --DBMS_OUTPUT.PUT_LINE('ID value fields (EPA, SORT, DOT):'|| d_vadf_id_epa || ', ' || d_vadf_id_waste || ', ' || d_vadf_id_ship || '.');
            
    --Dispose (EPA STERICYCLE Rx WASTE CHARACTERIZATION ) Input file looop
    FOR dispose_rec IN dispose_input_cursor LOOP

        dispose_input_cnt := dispose_input_cnt + 1;
        update_row_count := 0;
        --ndc_match_cnt := 0;
        --va_product_match_cnt := 0;
        --dispose_product_match_cnt := 0;
        
        
        --store input values
        dispose_ndc_number := dispose_rec.ndc_number;
        d_product_name := dispose_rec.product_name;
        d_primary_epa_code := dispose_rec.primary_epa_code;
        d_waste_sort_code := dispose_rec.waste_sort_code;
        d_dot_shipping_name := dispose_rec.dot_shipping_name;
        DBMS_OUTPUT.PUT_LINE('      ');
        dbms_output.put_line('Dispose Product -- NDC, PRODUCT, EPA, WASTE_CODE, DOT SHIPPING_NAME: '|| dispose_ndc_number||', '|| d_product_name ||', '||d_primary_epa_code||', '||d_dot_shipping_name||', '||d_waste_sort_code||'.');          
        
        -- NDC Lookup to locate Products link
        FOR ndc_rec in ndc_cursor(dispose_rec.ndc_number) LOOP

            ndc_match_cnt := ndc_match_cnt + 1;
            d_epl_id := ndc_rec.epl_id;
            d_ndc_number := ndc_rec.ndc_number; 
            d_ndc_no_dashes := ndc_rec.ndc_no_dashes;
            d_epl_id_product_fk := ndc_rec.epl_id_product_fk;
            owner_found_cnt :=0;
            --dbms_output.put_line('NDC found for NDC, PRODUCT, PID: '|| d_ndc_no_dashes||', '|| d_product_name ||', '||d_epl_id_product_fk||'.');
            -- Product Owner lookup
            FOR owner_rec in owner_cursor(ndc_rec.epl_id_product_fk) LOOP
                update_row_count := 0;
                owner_found_cnt := owner_found_cnt + 1;
                d_id := owner_rec.ID;
                d_owner_type := owner_rec.VADF_OWNER_TYPE;
                d_vadf_owner_id := owner_rec.VADF_OWNER_ID;
                d_epl_id_product_pk := owner_rec.EPL_ID_PRODUCT_FK;
                v_product_name := owner_rec.VA_PRODUCT_NAME;
                g_product_name := owner_rec.GENERIC_NAME;

                --dbms_output.put_line('Owner found for NDC, VA PRODUCT, GENERIC PRODUCT: '|| d_ndc_no_dashes||', '|| v_product_name  ||', '|| g_product_name ||'.');
                -- Update cursor for single NDC product match NONLIST values
                -- replaced by cursor to update all products with like name
                -- loop internal to product name search instead of single NDC
                v_upd_cnt := 0;
                    
                --update for all products that contain the va_product_name based on the NDC lookup
                FOR product_rec in product_cursor(owner_rec.GENERIC_NAME) LOOP
                                        
                    va_product_match_cnt := va_product_match_cnt + 1;
                    --use nonlist_value for NDC lookup
                    --d_nonlist_value := nonlist_rec.VA_DF_VALUE;
                    epa_exists := 0;
                    dot_exists := 0;
                    waste_exists := 0;
                    dispose_i_cnt := 0;
                    dispose_u_cnt := 0;
                    dispose_d_cnt := 0;
                        
                    v_owner_id := product_rec.ID;
                    v_product_id := product_rec.EPL_ID;
                    
                    select count(*) into owners_cnt from PPSNEPL.EPL_VADF_OWNERS OWNERS where EPL_ID_PRODUCT_FK = v_product_id;
                    --dbms_output.put_line('Owners count:' || owners_cnt || '.');
                    --dbms_output.put_line('Product record update for GENERIC PRODUCT: '|| g_product_name ||'.');                    
                    --add new nonlist values for each dispose element
                    --update boolean entry to True
                    --update value set
                    SELECT TO_CHAR
                    (SYSDATE, 'MM-DD-YYYY HH24:MI:SS') into u_end
                    FROM DUAL; 
                        
                            
                    select count(*) into dflag_exists from PPSNEPL.EPL_VADF_NONLIST_VALUES where 
                        VADF_OWNER_ID_FK = v_owner_id and VADF_ID_FK = 34;
                    
                    if dflag_exists > 0 then
                        update PPSNEPL.EPL_VADF_NONLIST_VALUES SET VA_DF_VALUE = d_true
                        where VADF_OWNER_ID_FK = v_owner_id and VADF_ID_FK = 34;
                    else
                        insert into PPSNEPL.EPL_VADF_NONLIST_VALUES (VADF_OWNER_ID_FK, VADF_ID_FK, VA_DF_VALUE, CREATED_BY, CREATED_DTM, LAST_MODIFIED_BY, LAST_MODIFIED_DTM)
                        select v_owner_id, 34,  d_true, 'HAZMAT INITIAL LOAD', to_date(u_end, 'MM-DD-YYYY HH24:MI:SS'), 'HAZMAT INITIAL LOAD', to_date(u_end, 'MM-DD-YYYY HH24:MI:SS') FROM DUAL;
                        dispose_i_cnt := dispose_i_cnt + 1; 
                    end if;
                    --DBMS_OUTPUT.PUT_LINE('ID value fields (EPA, SORT, DOT):'|| d_vadf_id_epa || ', ' || d_vadf_id_waste || ', ' || d_vadf_id_ship || '.');                    
                    --DBMS_OUTPUT.PUT_LINE('other key fields (d_id, d_primary_epa_code, d_waste_sort_code, d_dot_shipping-name:'|| d_id || ', ' || d_primary_epa_code || ', ' || d_waste_sort_code || ', ' || d_dot_shipping_name || '.');
                        
                        
                            
                    select count(*) into epa_exists from PPSNEPL.EPL_VADF_NONLIST_VALUES where 
                        VADF_OWNER_ID_FK = v_owner_id and VADF_ID_FK = d_vadf_id_epa;
                            
                     if epa_exists > 0 then
                            
                        if d_primary_epa_code is not null then
                            update PPSNEPL.EPL_VADF_NONLIST_VALUES set VA_DF_VALUE = d_primary_epa_code
                            where VADF_OWNER_ID_FK = v_owner_id and VADF_ID_FK = d_vadf_id_epa;
                            dispose_u_cnt := dispose_u_cnt + 1;
                        else
                            delete from PPSNEPL.EPL_VADF_NONLIST_VALUES 
                            where VADF_OWNER_ID_FK = v_owner_id and VADF_ID_FK = d_vadf_id_epa;
                            dispose_d_cnt := dispose_d_cnt + 1;                            
                        end if;
                    else
                        if d_primary_epa_code is not null then
                            insert into PPSNEPL.EPL_VADF_NONLIST_VALUES (VADF_OWNER_ID_FK, VADF_ID_FK, VA_DF_VALUE, CREATED_BY, CREATED_DTM, LAST_MODIFIED_BY, LAST_MODIFIED_DTM)
                            select v_owner_id, d_vadf_id_epa,  d_primary_epa_code, 'HAZMAT INITIAL LOAD', to_date(u_end, 'MM-DD-YYYY HH24:MI:SS'), 'HAZMAT INITIAL LOAD', to_date(u_end, 'MM-DD-YYYY HH24:MI:SS') FROM DUAL;
                            dispose_i_cnt := dispose_i_cnt + 1; 
                        end if;
                    end if;
                        
                     select count(*) into waste_exists from PPSNEPL.EPL_VADF_NONLIST_VALUES where 
                        VADF_OWNER_ID_FK = v_owner_id and VADF_ID_FK = d_vadf_id_waste;
                                                
                    if  waste_exists>0 then
                            
                        if d_waste_sort_code is not null then
                            update PPSNEPL.EPL_VADF_NONLIST_VALUES set VA_DF_VALUE = d_waste_sort_code
                            where VADF_OWNER_ID_FK = v_owner_id and VADF_ID_FK = d_vadf_id_waste;
                            dispose_u_cnt := dispose_u_cnt + 1;
                        else
                            delete from PPSNEPL.EPL_VADF_NONLIST_VALUES
                            where VADF_OWNER_ID_FK = v_owner_id and VADF_ID_FK = d_vadf_id_waste;
                            dispose_d_cnt := dispose_d_cnt + 1; 
                        end if;
                    else
                        if d_waste_sort_code is not null then
                            insert into PPSNEPL.EPL_VADF_NONLIST_VALUES (VADF_OWNER_ID_FK, VADF_ID_FK, VA_DF_VALUE, CREATED_BY, CREATED_DTM, LAST_MODIFIED_BY, LAST_MODIFIED_DTM)
                            select v_owner_id, d_vadf_id_waste,  d_waste_sort_code, 'HAZMAT INITIAL LOAD', to_date(u_end, 'MM-DD-YYYY HH24:MI:SS'), 'HAZMAT INITIAL LOAD', to_date(u_end, 'MM-DD-YYYY HH24:MI:SS') FROM DUAL;
                            dispose_i_cnt := dispose_i_cnt + 1; 
                        end if;
                    end if; 
                                           
                     select count(*) into dot_exists from PPSNEPL.EPL_VADF_NONLIST_VALUES where 
                        VADF_OWNER_ID_FK = v_owner_id and VADF_ID_FK = d_vadf_id_ship;
                        
                    if dot_exists > 0 then
                            
                        if d_dot_shipping_name is not null then
                            update PPSNEPL.EPL_VADF_NONLIST_VALUES set VA_DF_VALUE = d_dot_shipping_name
                            where VADF_OWNER_ID_FK = v_owner_id and VADF_ID_FK = d_vadf_id_ship;
                            dispose_u_cnt := dispose_u_cnt + 1;
                        else
                            delete from PPSNEPL.EPL_VADF_NONLIST_VALUES
                            where VADF_OWNER_ID_FK = v_owner_id and VADF_ID_FK = d_vadf_id_ship;
                            dispose_d_cnt := dispose_d_cnt + 1; 
                        end if;
                    else
                        if d_dot_shipping_name is not null then
                            insert into PPSNEPL.EPL_VADF_NONLIST_VALUES (VADF_OWNER_ID_FK, VADF_ID_FK, VA_DF_VALUE, CREATED_BY, CREATED_DTM, LAST_MODIFIED_BY, LAST_MODIFIED_DTM)
                            select v_owner_id, d_vadf_id_ship,  d_dot_shipping_name, 'HAZMAT INITIAL LOAD', to_date(u_end, 'MM-DD-YYYY HH24:MI:SS'), 'HAZMAT INITIAL LOAD', to_date(u_end, 'MM-DD-YYYY HH24:MI:SS') FROM DUAL;
                            dispose_i_cnt := dispose_i_cnt + 1; 
                        end if;
                    end if; 

                    v_upd_cnt := v_upd_cnt + 1;
                    update_row_count := update_row_count + 1;
                    -- Added additional lookup to cover Synonyms for NDC/Product Match
                    -- Identify each synonym for a specific product then update all products for each synonym found
                    FOR synonym_rec in synonym_cursor(product_rec.epl_id) LOOP
                                            
                       -- s_product_match_cnt := s_product_match_cnt + 1;
                        --use nonlist_value for NDC lookup
                        --d_nonlist_value := nonlist_rec.VA_DF_VALUE;
                        s_synonym_name := synonym_rec.synonym_name;
                        
                        FOR alternate_rec in alternate_cursor(s_synonym_name) LOOP
                        a_product_name := alternate_rec.va_product_name;
                        a_synonym_name := alternate_rec.synonym_name;
                        a_ndc_number := alternate_rec.product_ndc;
                        a_owner_id := alternate_rec.ID;
                        a_product_id := alternate_rec.epl_id;
                        a_generic_id := alternate_rec.VA_GEN_NAME_ID_FK;
                        a_product_cnt := a_product_cnt + 1;
                        
                        sa_match_cnt := sa_match_cnt + 1;
                        dbms_output.put_line('      ---additional product match updated using synonym search (VA Product, Synonym):  '|| a_product_name||', '||a_synonym_name);
            --add update logic for each alternative product
                                    epa_exists := 0;
                                    dot_exists := 0;
                                    waste_exists := 0;
                                    --dispose_i_cnt := 0;
                                    --dispose_u_cnt := 0;
                                    --dispose_d_cnt := 0;
                                    --add new nonlist values for each dispose element
                                    --update boolean entry to True
                                    --update value set
                                    SELECT TO_CHAR
                                    (SYSDATE, 'MM-DD-YYYY HH24:MI:SS') into u_end
                                    FROM DUAL; 
                                    
                                    update PPSNEPL.EPL_VADF_NONLIST_VALUES SET VA_DF_VALUE = d_true
                                    where VADF_OWNER_ID_FK = a_owner_id and VADF_ID_FK = 34;

                                    --DBMS_OUTPUT.PUT_LINE('ID value fields (EPA, SORT, DOT):'|| d_vadf_id_epa || ', ' || d_vadf_id_waste || ', ' || d_vadf_id_ship || '.');                    
                                    --DBMS_OUTPUT.PUT_LINE('other key fields (d_id, d_primary_epa_code, d_waste_sort_code, d_dot_shipping-name:'|| d_id || ', ' || d_primary_epa_code || ', ' || d_waste_sort_code || ', ' || d_dot_shipping_name || '.');
                                    
                                    
                                        
                                    select count(*) into epa_exists from PPSNEPL.EPL_VADF_NONLIST_VALUES where 
                                        VADF_OWNER_ID_FK = a_owner_id and VADF_ID_FK = d_vadf_id_epa;
                                        
                                     if epa_exists > 0 then
                                        
                                        if d_primary_epa_code is not null then
                                            update PPSNEPL.EPL_VADF_NONLIST_VALUES set VA_DF_VALUE = d_primary_epa_code
                                            where VADF_OWNER_ID_FK = a_owner_id and VADF_ID_FK = d_vadf_id_epa;
                                            dispose_u_cnt := dispose_u_cnt + 1;
                                        else
                                            delete from PPSNEPL.EPL_VADF_NONLIST_VALUES 
                                            where VADF_OWNER_ID_FK = a_owner_id and VADF_ID_FK = d_vadf_id_epa;
                                            dispose_d_cnt := dispose_d_cnt + 1;                            
                                        end if;
                                    else
                                        if d_primary_epa_code is not null then
                                            insert into PPSNEPL.EPL_VADF_NONLIST_VALUES (VADF_OWNER_ID_FK, VADF_ID_FK, VA_DF_VALUE, CREATED_BY, CREATED_DTM, LAST_MODIFIED_BY, LAST_MODIFIED_DTM)
                                            select a_owner_id, d_vadf_id_epa,  d_primary_epa_code, 'HAZMAT INITIAL LOAD', to_date(u_end, 'MM-DD-YYYY HH24:MI:SS'), 'HAZMAT INITIAL LOAD', to_date(u_end, 'MM-DD-YYYY HH24:MI:SS') FROM DUAL;
                                            dispose_i_cnt := dispose_i_cnt + 1; 
                                        end if;
                                    end if;
                                    
                                     select count(*) into waste_exists from PPSNEPL.EPL_VADF_NONLIST_VALUES where 
                                        VADF_OWNER_ID_FK = a_owner_id and VADF_ID_FK = d_vadf_id_waste;
                                                            
                                    if  waste_exists>0 then
                                        
                                        if d_waste_sort_code is not null then
                                            update PPSNEPL.EPL_VADF_NONLIST_VALUES set VA_DF_VALUE = d_waste_sort_code
                                            where VADF_OWNER_ID_FK = a_owner_id and VADF_ID_FK = d_vadf_id_waste;
                                            dispose_u_cnt := dispose_u_cnt + 1;
                                        else
                                            delete from PPSNEPL.EPL_VADF_NONLIST_VALUES
                                            where VADF_OWNER_ID_FK = a_owner_id and VADF_ID_FK = d_vadf_id_waste;
                                            dispose_d_cnt := dispose_d_cnt + 1; 
                                        end if;
                                    else
                                        if d_waste_sort_code is not null then
                                            insert into PPSNEPL.EPL_VADF_NONLIST_VALUES (VADF_OWNER_ID_FK, VADF_ID_FK, VA_DF_VALUE, CREATED_BY, CREATED_DTM, LAST_MODIFIED_BY, LAST_MODIFIED_DTM)
                                            select a_owner_id, d_vadf_id_waste,  d_waste_sort_code, 'HAZMAT INITIAL LOAD', to_date(u_end, 'MM-DD-YYYY HH24:MI:SS'), 'HAZMAT INITIAL LOAD', to_date(u_end, 'MM-DD-YYYY HH24:MI:SS') FROM DUAL;
                                            dispose_i_cnt := dispose_i_cnt + 1; 
                                        end if;
                                    end if; 
                                                       
                                     select count(*) into dot_exists from PPSNEPL.EPL_VADF_NONLIST_VALUES where 
                                        VADF_OWNER_ID_FK = a_owner_id and VADF_ID_FK = d_vadf_id_ship;
                                    
                                    if dot_exists > 0 then
                                        
                                        if d_dot_shipping_name is not null then
                                            update PPSNEPL.EPL_VADF_NONLIST_VALUES set VA_DF_VALUE = d_dot_shipping_name
                                            where VADF_OWNER_ID_FK = a_owner_id and VADF_ID_FK = d_vadf_id_ship;
                                            dispose_u_cnt := dispose_u_cnt + 1;
                                        else
                                            delete from PPSNEPL.EPL_VADF_NONLIST_VALUES
                                            where VADF_OWNER_ID_FK = a_owner_id and VADF_ID_FK = d_vadf_id_ship;
                                            dispose_d_cnt := dispose_d_cnt + 1; 
                                        end if;
                                    else
                                        if d_dot_shipping_name is not null then
                                            insert into PPSNEPL.EPL_VADF_NONLIST_VALUES (VADF_OWNER_ID_FK, VADF_ID_FK, VA_DF_VALUE, CREATED_BY, CREATED_DTM, LAST_MODIFIED_BY, LAST_MODIFIED_DTM)
                                            select a_owner_id, d_vadf_id_ship,  d_dot_shipping_name, 'HAZMAT INITIAL LOAD', to_date(u_end, 'MM-DD-YYYY HH24:MI:SS'), 'HAZMAT INITIAL LOAD', to_date(u_end, 'MM-DD-YYYY HH24:MI:SS') FROM DUAL;
                                            dispose_i_cnt := dispose_i_cnt + 1; 
                                        end if;
                                    end if; 

                        END LOOP; --alternative product matches for Synonym found

                        
                    END LOOP; -- Update cursor for Synonym Name values
                    
                END LOOP; -- Update cursor for VA Product Name values


            END LOOP; -- Product Owner
        
            --dbms_output.put_line('Product found for NDC, PRODUCT, EPA, WASTE_CODE, DOT SHIPPING_NAME: '|| dispose_ndc_number||', '|| d_product_name ||', '||d_primary_epa_code||', '||d_waste_sort_code||', '||d_dot_shipping_name||'.');
            --dbms_output.put_line('-----Actions performed (insert, update, delete): '|| dispose_i_cnt||', '|| dispose_u_cnt ||', '||dispose_d_cnt || '.');
            --dbms_output.put_line('product found: '|| dispose_ndc_number || ', '|| d_epl_id_product_fk || ', '||d_product_name||'.');
            
            --total_upd_cnt := total_upd_cnt + dispose_upd_cnt;
            

        END LOOP; --NDC lookup for Products
        --dbms_output.put_line('ndc_number: '||dispose_ndc_number || ' has ' || dispose_found_cnt || ' associated products.');
        --total_found_cnt := total_found_cnt + dispose_found_cnt;
        if update_row_count = 0 then
 
    DBMS_OUTPUT.PUT_LINE(' ');
            dbms_output.put_line('Product not found for NDC, PRODUCT, EPA, WASTE_CODE, DOT SHIPPING_NAME: '|| dispose_ndc_number||', '|| d_product_name ||', '||d_primary_epa_code||', '||d_dot_shipping_name||', '||d_waste_sort_code||'.');
            -- search similar products by name
            a_product_cnt := 0;
            --dispose_product_match_cnt := 0;
            
            FOR alternate_rec in alternate_cursor(d_product_name) LOOP
                a_product_name := alternate_rec.va_product_name;
                a_synonym_name := alternate_rec.synonym_name;
                a_ndc_number := alternate_rec.product_ndc;
                a_owner_id := alternate_rec.ID;
                a_product_id := alternate_rec.epl_id;
                a_generic_id := alternate_rec.VA_GEN_NAME_ID_FK;
                a_product_cnt := a_product_cnt + 1;
                
                dispose_product_match_cnt := dispose_product_match_cnt + 1;
                dbms_output.put_line('      ---alternative product match updated using synonym search (VA Product, Synonym):  '|| a_product_name||', '||a_synonym_name);
                --add update logic for each alternative product
                epa_exists := 0;
                dot_exists := 0;
                waste_exists := 0;
                --add new nonlist values for each dispose element
                --update boolean entry to True
                --update value set
                SELECT TO_CHAR
                (SYSDATE, 'MM-DD-YYYY HH24:MI:SS') into u_end
                FROM DUAL; 
                        
                update PPSNEPL.EPL_VADF_NONLIST_VALUES SET VA_DF_VALUE = d_true
                where VADF_OWNER_ID_FK = a_owner_id and VADF_ID_FK = 34;

                --DBMS_OUTPUT.PUT_LINE('ID value fields (EPA, SORT, DOT):'|| d_vadf_id_epa || ', ' || d_vadf_id_waste || ', ' || d_vadf_id_ship || '.');                    
                --DBMS_OUTPUT.PUT_LINE('other key fields (d_id, d_primary_epa_code, d_waste_sort_code, d_dot_shipping-name:'|| d_id || ', ' || d_primary_epa_code || ', ' || d_waste_sort_code || ', ' || d_dot_shipping_name || '.');
                        
                        
                            
                select count(*) into epa_exists from PPSNEPL.EPL_VADF_NONLIST_VALUES where 
                    VADF_OWNER_ID_FK = a_owner_id and VADF_ID_FK = d_vadf_id_epa;
                            
                 if epa_exists > 0 then
                            
                    if d_primary_epa_code is not null then
                        update PPSNEPL.EPL_VADF_NONLIST_VALUES set VA_DF_VALUE = d_primary_epa_code
                        where VADF_OWNER_ID_FK = a_owner_id and VADF_ID_FK = d_vadf_id_epa;
                        dispose_u_cnt := dispose_u_cnt + 1;
                    else
                        delete from PPSNEPL.EPL_VADF_NONLIST_VALUES 
                        where VADF_OWNER_ID_FK = a_owner_id and VADF_ID_FK = d_vadf_id_epa;
                        dispose_d_cnt := dispose_d_cnt + 1;                            
                    end if;
                else
                    if d_primary_epa_code is not null then
                        insert into PPSNEPL.EPL_VADF_NONLIST_VALUES (VADF_OWNER_ID_FK, VADF_ID_FK, VA_DF_VALUE, CREATED_BY, CREATED_DTM, LAST_MODIFIED_BY, LAST_MODIFIED_DTM)
                        select a_owner_id, d_vadf_id_epa,  d_primary_epa_code, 'HAZMAT INITIAL LOAD', to_date(u_end, 'MM-DD-YYYY HH24:MI:SS'), 'HAZMAT INITIAL LOAD', to_date(u_end, 'MM-DD-YYYY HH24:MI:SS') FROM DUAL;
                        dispose_i_cnt := dispose_i_cnt + 1; 
                    end if;
                end if;
                        
                 select count(*) into waste_exists from PPSNEPL.EPL_VADF_NONLIST_VALUES where 
                    VADF_OWNER_ID_FK = a_owner_id and VADF_ID_FK = d_vadf_id_waste;
                                                
                if  waste_exists>0 then
                            
                    if d_waste_sort_code is not null then
                        update PPSNEPL.EPL_VADF_NONLIST_VALUES set VA_DF_VALUE = d_waste_sort_code
                        where VADF_OWNER_ID_FK = a_owner_id and VADF_ID_FK = d_vadf_id_waste;
                        dispose_u_cnt := dispose_u_cnt + 1;
                    else
                        delete from PPSNEPL.EPL_VADF_NONLIST_VALUES
                        where VADF_OWNER_ID_FK = a_owner_id and VADF_ID_FK = d_vadf_id_waste;
                        dispose_d_cnt := dispose_d_cnt + 1; 
                    end if;
                else
                    if d_waste_sort_code is not null then
                        insert into PPSNEPL.EPL_VADF_NONLIST_VALUES (VADF_OWNER_ID_FK, VADF_ID_FK, VA_DF_VALUE, CREATED_BY, CREATED_DTM, LAST_MODIFIED_BY, LAST_MODIFIED_DTM)
                        select a_owner_id, d_vadf_id_waste,  d_waste_sort_code, 'HAZMAT INITIAL LOAD', to_date(u_end, 'MM-DD-YYYY HH24:MI:SS'), 'HAZMAT INITIAL LOAD', to_date(u_end, 'MM-DD-YYYY HH24:MI:SS') FROM DUAL;
                        dispose_i_cnt := dispose_i_cnt + 1; 
                    end if;
                end if; 
                                           
                 select count(*) into dot_exists from PPSNEPL.EPL_VADF_NONLIST_VALUES where 
                    VADF_OWNER_ID_FK = a_owner_id and VADF_ID_FK = d_vadf_id_ship;
                        
                if dot_exists > 0 then
                            
                    if d_dot_shipping_name is not null then
                        update PPSNEPL.EPL_VADF_NONLIST_VALUES set VA_DF_VALUE = d_dot_shipping_name
                        where VADF_OWNER_ID_FK = a_owner_id and VADF_ID_FK = d_vadf_id_ship;
                        dispose_u_cnt := dispose_u_cnt + 1;
                    else
                        delete from PPSNEPL.EPL_VADF_NONLIST_VALUES
                        where VADF_OWNER_ID_FK = a_owner_id and VADF_ID_FK = d_vadf_id_ship;
                        dispose_d_cnt := dispose_d_cnt + 1; 
                    end if;
                else
                    if d_dot_shipping_name is not null then
                        insert into PPSNEPL.EPL_VADF_NONLIST_VALUES (VADF_OWNER_ID_FK, VADF_ID_FK, VA_DF_VALUE, CREATED_BY, CREATED_DTM, LAST_MODIFIED_BY, LAST_MODIFIED_DTM)
                        select a_owner_id, d_vadf_id_ship,  d_dot_shipping_name, 'HAZMAT INITIAL LOAD', to_date(u_end, 'MM-DD-YYYY HH24:MI:SS'), 'HAZMAT INITIAL LOAD', to_date(u_end, 'MM-DD-YYYY HH24:MI:SS') FROM DUAL;
                        dispose_i_cnt := dispose_i_cnt + 1; 
                    end if;
                end if; 
                    
                -- Additional search by generic for each synonym product match found
                FOR generic_rec in generic_cursor(a_generic_id) LOOP
                    a_owner_id := generic_rec.ID;
                    g_generic_id := generic_rec.VA_GEN_NAME_ID_FK;
                    g_generic_name := generic_rec.generic_name;
                    g_va_product_name := generic_rec.VA_PRODUCT_NAME;
                    ag_product_cnt := ag_product_cnt + 1;
                            
                    --dispose_product_match_cnt := dispose_product_match_cnt + 1;
                    dbms_output.put_line('      ---additional product match updated using generic search (VA Product, Generic):  '|| g_va_product_name||', '||g_generic_name);
                    --add update logic for each generic product
                    epa_exists := 0;
                    dot_exists := 0;
                    waste_exists := 0;
                    --dispose_i_cnt := 0;
                    --dispose_u_cnt := 0;
                    --dispose_d_cnt := 0;
                    --add new nonlist values for each dispose element
                    --update boolean entry to True
                    --update value set
                    SELECT TO_CHAR
                    (SYSDATE, 'MM-DD-YYYY HH24:MI:SS') into u_end
                    FROM DUAL; 
                                    
                    update PPSNEPL.EPL_VADF_NONLIST_VALUES SET VA_DF_VALUE = d_true
                    where VADF_OWNER_ID_FK = a_owner_id and VADF_ID_FK = 34;

                    --DBMS_OUTPUT.PUT_LINE('ID value fields (EPA, SORT, DOT):'|| d_vadf_id_epa || ', ' || d_vadf_id_waste || ', ' || d_vadf_id_ship || '.');                    
                    --DBMS_OUTPUT.PUT_LINE('other key fields (d_id, d_primary_epa_code, d_waste_sort_code, d_dot_shipping-name:'|| d_id || ', ' || d_primary_epa_code || ', ' || d_waste_sort_code || ', ' || d_dot_shipping_name || '.');
                                    
                                    
                                        
                    select count(*) into epa_exists from PPSNEPL.EPL_VADF_NONLIST_VALUES where 
                        VADF_OWNER_ID_FK = a_owner_id and VADF_ID_FK = d_vadf_id_epa;
                                        
                     if epa_exists > 0 then
                                        
                        if d_primary_epa_code is not null then
                            update PPSNEPL.EPL_VADF_NONLIST_VALUES set VA_DF_VALUE = d_primary_epa_code
                            where VADF_OWNER_ID_FK = a_owner_id and VADF_ID_FK = d_vadf_id_epa;
                            dispose_u_cnt := dispose_u_cnt + 1;
                        else
                            delete from PPSNEPL.EPL_VADF_NONLIST_VALUES 
                            where VADF_OWNER_ID_FK = a_owner_id and VADF_ID_FK = d_vadf_id_epa;
                            dispose_d_cnt := dispose_d_cnt + 1;                            
                        end if;
                    else
                        if d_primary_epa_code is not null then
                            insert into PPSNEPL.EPL_VADF_NONLIST_VALUES (VADF_OWNER_ID_FK, VADF_ID_FK, VA_DF_VALUE, CREATED_BY, CREATED_DTM, LAST_MODIFIED_BY, LAST_MODIFIED_DTM)
                            select a_owner_id, d_vadf_id_epa,  d_primary_epa_code, 'HAZMAT INITIAL LOAD', to_date(u_end, 'MM-DD-YYYY HH24:MI:SS'), 'HAZMAT INITIAL LOAD', to_date(u_end, 'MM-DD-YYYY HH24:MI:SS') FROM DUAL;
                            dispose_i_cnt := dispose_i_cnt + 1; 
                        end if;
                    end if;
                                    
                     select count(*) into waste_exists from PPSNEPL.EPL_VADF_NONLIST_VALUES where 
                        VADF_OWNER_ID_FK = a_owner_id and VADF_ID_FK = d_vadf_id_waste;
                                                            
                    if  waste_exists>0 then
                                        
                        if d_waste_sort_code is not null then
                            update PPSNEPL.EPL_VADF_NONLIST_VALUES set VA_DF_VALUE = d_waste_sort_code
                            where VADF_OWNER_ID_FK = a_owner_id and VADF_ID_FK = d_vadf_id_waste;
                            dispose_u_cnt := dispose_u_cnt + 1;
                        else
                            delete from PPSNEPL.EPL_VADF_NONLIST_VALUES
                            where VADF_OWNER_ID_FK = a_owner_id and VADF_ID_FK = d_vadf_id_waste;
                            dispose_d_cnt := dispose_d_cnt + 1; 
                        end if;
                    else
                        if d_waste_sort_code is not null then
                            insert into PPSNEPL.EPL_VADF_NONLIST_VALUES (VADF_OWNER_ID_FK, VADF_ID_FK, VA_DF_VALUE, CREATED_BY, CREATED_DTM, LAST_MODIFIED_BY, LAST_MODIFIED_DTM)
                            select a_owner_id, d_vadf_id_waste,  d_waste_sort_code, 'HAZMAT INITIAL LOAD', to_date(u_end, 'MM-DD-YYYY HH24:MI:SS'), 'HAZMAT INITIAL LOAD', to_date(u_end, 'MM-DD-YYYY HH24:MI:SS') FROM DUAL;
                            dispose_i_cnt := dispose_i_cnt + 1; 
                        end if;
                    end if; 
                                                       
                     select count(*) into dot_exists from PPSNEPL.EPL_VADF_NONLIST_VALUES where 
                        VADF_OWNER_ID_FK = a_owner_id and VADF_ID_FK = d_vadf_id_ship;
                                    
                    if dot_exists > 0 then
                                        
                        if d_dot_shipping_name is not null then
                            update PPSNEPL.EPL_VADF_NONLIST_VALUES set VA_DF_VALUE = d_dot_shipping_name
                            where VADF_OWNER_ID_FK = a_owner_id and VADF_ID_FK = d_vadf_id_ship;
                            dispose_u_cnt := dispose_u_cnt + 1;
                        else
                            delete from PPSNEPL.EPL_VADF_NONLIST_VALUES
                            where VADF_OWNER_ID_FK = a_owner_id and VADF_ID_FK = d_vadf_id_ship;
                            dispose_d_cnt := dispose_d_cnt + 1; 
                        end if;
                    else
                        if d_dot_shipping_name is not null then
                            insert into PPSNEPL.EPL_VADF_NONLIST_VALUES (VADF_OWNER_ID_FK, VADF_ID_FK, VA_DF_VALUE, CREATED_BY, CREATED_DTM, LAST_MODIFIED_BY, LAST_MODIFIED_DTM)
                            select a_owner_id, d_vadf_id_ship,  d_dot_shipping_name, 'HAZMAT INITIAL LOAD', to_date(u_end, 'MM-DD-YYYY HH24:MI:SS'), 'HAZMAT INITIAL LOAD', to_date(u_end, 'MM-DD-YYYY HH24:MI:SS') FROM DUAL;
                            dispose_i_cnt := dispose_i_cnt + 1; 
                        end if;
                    end if; 
                            

                    END LOOP; --alternative product by generic for synonym lookup



            END LOOP; --alternative product matches for NDC not found
            
            if a_product_cnt = 0 then
            --dbms_output.put_line('Product not found.');
            dbms_output.put_line('---no product name alternative name found using synonym search');
                --dispose_product_nomatch_cnt := a_product_nomatch_cnt + 1;
                update PPSNEPL.HAZARD_DISPOSE_INPUT set LOAD_STATUS='NDC Not Found, no Product match updates found.', LOAD_DTM = to_date(u_end, 'MM-DD-YYYY HH24:MI:SS')
                where PPSNEPL.HAZARD_DISPOSE_INPUT.NDC_NUMBER = dispose_ndc_number;
                total_notfound_cnt := total_notfound_cnt + 1;

            else
                update PPSNEPL.HAZARD_DISPOSE_INPUT set LOAD_STATUS='NDC Not Found, Product match update applied.', LOAD_DTM = to_date(u_end, 'MM-DD-YYYY HH24:MI:SS')
                where PPSNEPL.HAZARD_DISPOSE_INPUT.NDC_NUMBER = dispose_ndc_number;

            end if;
   
    --DBMS_OUTPUT.PUT_LINE(' ');
        else
            update PPSNEPL.HAZARD_DISPOSE_INPUT set LOAD_STATUS='NDC Found, Product Name match update applied.', LOAD_DTM = to_date(u_end, 'MM-DD-YYYY HH24:MI:SS')
            where PPSNEPL.HAZARD_DISPOSE_INPUT.NDC_NUMBER = dispose_ndc_number;            
        end if;
            
   END LOOP; -- Input file lookup
    DBMS_OUTPUT.PUT_LINE('  ');
    DBMS_OUTPUT.PUT_LINE('EPA STERICYCLE Rx WASTE CHARACTERIZATION - Hazard to Dispose - Load Results Summary');
    DBMS_OUTPUT.PUT_LINE('  ');

   dbms_output.put_line('Total STERICYCLE Input Count: '||dispose_input_cnt || '.');
   dbms_output.put_line('Total PPSN NDC Lookup Match: '||ndc_match_cnt || '.');
   dbms_output.put_line('Total PPSN VA Product Updated using NDC/GENERIC name lookup: '||va_product_match_cnt || '.');
   dbms_output.put_line('Total PPSN VA Product Updated using NDC/GENERIC/Synonym name lookup: '||sa_match_cnt || '.');
   dbms_output.put_line('Total PPSN VA Product Updated using SYNONYM Match to STERICYCLE Name lookup: '||dispose_product_match_cnt || '.');
   dbms_output.put_line('Total PPSN VA Product Updated using SYNONYM/GENERIC name lookup: '||ag_product_cnt || '.');   
   dbms_output.put_line('Total STERICYCLE Input Not Found in PPSN: '||total_notfound_cnt || '.');

   SELECT TO_CHAR
    (SYSDATE, 'MM-DD-YYYY HH24:MI:SS') into d_end
     FROM DUAL;
   
   DBMS_OUTPUT.PUT_LINE('Load End: '|| d_end);
   COMMIT;

END LOAD_HAZARD_DISPOSE;
/
