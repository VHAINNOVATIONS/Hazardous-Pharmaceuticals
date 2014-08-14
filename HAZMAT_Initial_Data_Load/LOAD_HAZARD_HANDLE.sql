CREATE OR REPLACE PROCEDURE PPSNEPL.LOAD_HAZARD_HANDLE IS

/******************************************************************************
   NAME:       LOAD_HAZARD_HANDLE
   PURPOSE:    

   REVISIONS:
   Ver        Date        Author           Description
   ---------  ----------  ---------------  ------------------------------------
   1.0        6/04/2013   vhaispraya       1. Created this procedure.

   NOTES:

   Automatically available Auto Replace Keywords:
      Object Name:     LOAD_HAZARD_HANDLE
      Sysdate:         6/04/2013
      Date and Time:   6/04/2013
      Username:        vhaispraya (set in TOAD Options, Procedure Editor)
      Table Name:       (set in the "New PL/SQL Object" dialog)

******************************************************************************/
    cursor HANDLE_input_cursor is
    select  PRODUCT_NAME,  LOAD_STATUS, LOAD_DTM 
    from PPSNEPL.hazard_HANDLE_input
    order by product_name;

    cursor product_cursor (HANDLE_product_name PPSNEPL.EPL_PRODUCTS.VA_PRODUCT_NAME%TYPE) is
    select SYNONYMS.SYNONYM_NAME , SYNONYMS.EPL_ID_PRODUCT_FK, PRODUCT.VA_PRODUCT_NAME , PRODUCT.EPL_ID
    from PPSNEPL.EPL_SYNONYMS SYNONYMS, PPSNEPL.EPL_PRODUCTS PRODUCT
    where UPPER(HANDLE_product_name) like '%'||UPPER(SYNONYMS.SYNONYM_NAME)||'%' and
    PRODUCT.EPL_ID = SYNONYMS.EPL_ID_PRODUCT_FK;
       
    
    cursor owner_cursor (HANDLE_product_number PPSNEPL.EPL_VADF_OWNERS.EPL_ID_PRODUCT_FK%TYPE) is
    select PPSNEPL.EPL_VADF_OWNERS.ID, VADF_OWNER_TYPE, VADF_OWNER_ID, EPL_ID_OI_FK, EPL_ID_PRODUCT_FK
    from PPSNEPL.EPL_VADF_OWNERS
    where EPL_ID_PRODUCT_FK = HANDLE_product_number
    order by PPSNEPL.EPL_VADF_OWNERS.ID;

    cursor nonlist_cursor (HANDLE_owner_number PPSNEPL.EPL_VADF_NONLIST_VALUES.VADF_OWNER_ID_FK%TYPE) is
    select VADF_OWNER_ID_FK, VADF_ID_FK, VA_DF_VALUE
    from PPSNEPL.EPL_VADF_NONLIST_VALUES
    where VADF_OWNER_ID_FK = HANDLE_owner_number and VADF_ID_FK = 35
    order by VADF_OWNER_ID_FK;
    
    cursor alternate_cursor (HANDLE_product_name PPSNEPL.HAZARD_HANDLE_INPUT.PRODUCT_NAME%TYPE) is
    select DISTINCT PRODUCT.VA_PRODUCT_NAME , PRODUCT.PRODUCT_NDC, PRODUCT.EPL_ID , OWNERS.ID 
    from PPSNEPL.HAZARD_HANDLE_INPUT HANDLE, PPSNEPL.EPL_PRODUCTS PRODUCT, PPSNEPL.EPL_VADF_OWNERS OWNERS
    where HANDLE.PRODUCT_NAME = HANDLE_product_name and
    UPPER(PRODUCT.VA_PRODUCT_NAME) like '%'||UPPER(HANDLE.PRODUCT_NAME)||'%' and
    OWNERS.EPL_ID_PRODUCT_FK = PRODUCT.EPL_ID ;
    
    d_true                          varchar2(5) := 'true';
    d_vadf_id_epa                   number(30) := 0; 
    d_vadf_id_waste                 number(30) := 0; 
    d_vadf_id_ship                  number(30) := 0;
    d_load_status                   varchar2(50);
    d_load_dtm                      timestamp(6); 

    HANDLE_ndc_number              number(30) := 0;
    HANDLE_product_number          number(30) := 0;  
    HANDLE_owner_number            number(30) := 0;      
    HANDLE_product_name            varchar2(100);  

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
    d_va_product_name                  varchar2(100);
    s_product_name                  varchar2(100);
    d_primary_epa_code              varchar2(50);
    d_waste_sort_code               varchar2(50);
    d_dot_shipping_name             varchar2(150);
    a_product_name                  varchar2(100);
    a_ndc_number                    varchar2(50) := 0; 


    HANDLE_cnt                     number(30) := 0;
    HANDLE_found_cnt               number(30) := 0;
    owner_found_cnt                 number(30) := 0;
    HANDLE_upd_cnt                 number(30) := 0;
    name_upd_cnt                   number(30) := 0;
    HANDLE_u_cnt                   number(30) := 0;
    HANDLE_d_cnt                   number(30) := 0;
    HANDLE_i_cnt                   number(30) := 0;
    total_upd_cnt                   number(30) := 0;
    total_found_cnt                 number(30) := 0;
    total_notfound_cnt              number(30) := 0;
    nonlist_found_cnt               number(30) := 0;
    update_row_count                number(30) := 0;
    a_product_cnt                   number(30) := 0;
    hflag_exists                    number(30) := 0;
    --handle_i_cnt                    number(30) := 0;
    d_start                         varchar2(30);
    d_end                           varchar2(30);
    u_end                           varchar2(30);
    epa_exists                      number(30) := 0;
    dot_exists                     number(30) := 0;
    waste_exists                   number(30) := 0;

BEGIN
   /* The number of iterations will equal the number of rows
      returned by HANDLE_input_cursor. */
    -- get ids for new fields      
   
    SELECT TO_CHAR
    (SYSDATE, 'MM-DD-YYYY HH24:MI:SS') into d_start
     FROM DUAL;
    
    DBMS_OUTPUT.PUT_LINE('NIOSH List of Hazardous Drugs - Hazard to HANDLE - Initial Load ');
    DBMS_OUTPUT.PUT_LINE('Load Start: '|| d_start);
    DBMS_OUTPUT.PUT_LINE(' ');
    DBMS_OUTPUT.PUT_LINE(' ');
    --DBMS_OUTPUT.PUT_LINE('ID value fields (EPA, SORT, DOT):'|| d_vadf_id_epa || ', ' || d_vadf_id_waste || ', ' || d_vadf_id_ship || '.');
            
    --NIOSH List of Hazardous Drugs Input file looop
    FOR HANDLE_rec IN HANDLE_input_cursor LOOP

        HANDLE_cnt := HANDLE_cnt + 1;
        update_row_count := 0;
        --HANDLE_found_cnt :=0;
        --HANDLE_upd_cnt :=0;
        
        --store input values
        HANDLE_product_name := HANDLE_rec.product_name;
        d_product_name := HANDLE_rec.product_name;
          
        
        -- Product Name Lookup to locate Products link
        FOR product_rec in product_cursor(HANDLE_rec.product_name) LOOP

            HANDLE_found_cnt := HANDLE_found_cnt + 1;
            d_epl_id := product_rec.epl_id;
            d_va_product_name := product_rec.va_product_name; 
            s_product_name := product_rec.synonym_name;
            
            -- Product Owner lookup
            FOR owner_rec in owner_cursor(product_rec.epl_id) LOOP

                owner_found_cnt := owner_found_cnt + 1;
                d_id := owner_rec.ID;
                d_owner_type := owner_rec.VADF_OWNER_TYPE;
                d_vadf_owner_id := owner_rec.VADF_OWNER_ID;
                d_epl_id_product_pk := owner_rec.EPL_ID_PRODUCT_FK;


                -- Update cursor for NONLIST values
                --FOR nonlist_rec in nonlist_cursor(owner_rec.ID) LOOP
                    
                    nonlist_found_cnt := nonlist_found_cnt + 1;
                  --  d_nonlist_value := nonlist_rec.VA_DF_VALUE;
                    
                    --add new nonlist values for each HANDLE element
                    --update boolean entry to True
                    --update value set
                    SELECT TO_CHAR
                    (SYSDATE, 'MM-DD-YYYY HH24:MI:SS') into u_end
                    FROM DUAL; 
                    
                    select count(*) into hflag_exists from PPSNEPL.EPL_VADF_NONLIST_VALUES where 
                        VADF_OWNER_ID_FK = d_id and VADF_ID_FK = 35;
                    
                    if hflag_exists > 0 then
                        update PPSNEPL.EPL_VADF_NONLIST_VALUES SET VA_DF_VALUE = d_true
                        where VADF_OWNER_ID_FK = d_id and VADF_ID_FK = 35;
                    else
                        insert into PPSNEPL.EPL_VADF_NONLIST_VALUES (VADF_OWNER_ID_FK, VADF_ID_FK, VA_DF_VALUE, CREATED_BY, CREATED_DTM, LAST_MODIFIED_BY, LAST_MODIFIED_DTM)
                        select d_id, 35,  d_true, 'HAZMAT INITIAL LOAD', to_date(u_end, 'MM-DD-YYYY HH24:MI:SS'), 'HAZMAT INITIAL LOAD', to_date(u_end, 'MM-DD-YYYY HH24:MI:SS') FROM DUAL;
                        handle_i_cnt := handle_i_cnt + 1; 
                    end if;
                       
                  --dbms_output.put_line('Product found for Synonym Lookup (PRODUCT Name, Synonym Name): '|| d_product_name || s_||'.');
                  DBMS_OUTPUT.PUT_LINE('    ');
                  DBMS_OUTPUT.PUT_LINE('Product found for Synonym Lookup (input product_name, va product name, synonym_name):'|| d_product_name || ', ' || d_va_product_name || ', '|| s_product_name ||'.');                    
                    
                    
                        
                    
                    HANDLE_upd_cnt := HANDLE_upd_cnt + 1;
                    update_row_count := update_row_count + 1;
                    


                --END LOOP; -- Update cursor for NONLIST values

            END LOOP; -- Product Owner
            -- added an additional search by product name to catch anomalies with synonym match
            -- search similar products by name
            a_product_cnt := 0;
            FOR alternate_rec in alternate_cursor(HANDLE_rec.product_name) LOOP
                a_product_name := alternate_rec.va_product_name;
                d_id := alternate_rec.ID;
                a_product_cnt := a_product_cnt + 1;
                name_upd_cnt := name_upd_cnt + 1;
                dbms_output.put_line('      ---alternative product match for VA Product Name updated: '|| a_product_name );

                SELECT TO_CHAR
                (SYSDATE, 'MM-DD-YYYY HH24:MI:SS') into u_end
                FROM DUAL; 
                            
                select count(*) into hflag_exists from PPSNEPL.EPL_VADF_NONLIST_VALUES where 
                    VADF_OWNER_ID_FK = d_id and VADF_ID_FK = 35;
                            
                if hflag_exists > 0 then
                    update PPSNEPL.EPL_VADF_NONLIST_VALUES SET VA_DF_VALUE = d_true
                    where VADF_OWNER_ID_FK = d_id and VADF_ID_FK = 35;
                else
                    insert into PPSNEPL.EPL_VADF_NONLIST_VALUES (VADF_OWNER_ID_FK, VADF_ID_FK, VA_DF_VALUE, CREATED_BY, CREATED_DTM, LAST_MODIFIED_BY, LAST_MODIFIED_DTM)
                    select d_id, 35,  d_true, 'HAZMAT INITIAL LOAD', to_date(u_end, 'MM-DD-YYYY HH24:MI:SS'), 'HAZMAT INITIAL LOAD', to_date(u_end, 'MM-DD-YYYY HH24:MI:SS') FROM DUAL;
                    handle_i_cnt := handle_i_cnt + 1; 
                end if;
                               

            END LOOP; --alternative product matches for exact match not found
                    
           
    --DBMS_OUTPUT.PUT_LINE(' ');
        

            

        END LOOP; --Product Name lookup for Products
        --dbms_output.put_line('ndc_number: '||HANDLE_ndc_number || ' has ' || HANDLE_found_cnt || ' associated products.');
        --total_found_cnt := total_found_cnt + HANDLE_found_cnt;
        if update_row_count = 0 then
            --total_notfound_cnt := total_notfound_cnt + 1;
    DBMS_OUTPUT.PUT_LINE(' ');
            dbms_output.put_line('Product not found with synonym lookup : '||  d_product_name ||', '|| s_product_name||'.');
            -- search similar products by name
            a_product_cnt := 0;
            FOR alternate_rec in alternate_cursor(d_product_name) LOOP
                a_product_name := alternate_rec.va_product_name;
                d_id := alternate_rec.ID;
                a_product_cnt := a_product_cnt + 1;
                name_upd_cnt := name_upd_cnt + 1;
                dbms_output.put_line('      ---alternative product match for VA Product Name updated: '|| a_product_name );

                SELECT TO_CHAR
                (SYSDATE, 'MM-DD-YYYY HH24:MI:SS') into u_end
                FROM DUAL; 
                    
                select count(*) into hflag_exists from PPSNEPL.EPL_VADF_NONLIST_VALUES where 
                    VADF_OWNER_ID_FK = d_id and VADF_ID_FK = 35;
                    
                if hflag_exists > 0 then
                    update PPSNEPL.EPL_VADF_NONLIST_VALUES SET VA_DF_VALUE = d_true
                    where VADF_OWNER_ID_FK = d_id and VADF_ID_FK = 35;
                else
                    insert into PPSNEPL.EPL_VADF_NONLIST_VALUES (VADF_OWNER_ID_FK, VADF_ID_FK, VA_DF_VALUE, CREATED_BY, CREATED_DTM, LAST_MODIFIED_BY, LAST_MODIFIED_DTM)
                    select d_id, 35,  d_true, 'HAZMAT INITIAL LOAD', to_date(u_end, 'MM-DD-YYYY HH24:MI:SS'), 'HAZMAT INITIAL LOAD', to_date(u_end, 'MM-DD-YYYY HH24:MI:SS') FROM DUAL;
                    handle_i_cnt := handle_i_cnt + 1; 
                end if;
                       

            END LOOP; --alternative product matches for exact match not found
            
            if a_product_cnt = 0 then
                total_notfound_cnt := total_notfound_cnt + 1;
                dbms_output.put_line('---no alternative product name match found.');
                update PPSNEPL.HAZARD_HANDLE_INPUT set LOAD_STATUS='Product Not Found.', LOAD_DTM = to_date(u_end, 'MM-DD-YYYY HH24:MI:SS')
                where PPSNEPL.HAZARD_HANDLE_INPUT.product_name = HANDLE_product_name;
            else
                update PPSNEPL.HAZARD_HANDLE_INPUT set LOAD_STATUS='Inital Load Successful by VA Product name match.', LOAD_DTM = to_date(u_end, 'MM-DD-YYYY HH24:MI:SS')
                where PPSNEPL.HAZARD_HANDLE_INPUT.product_name = HANDLE_product_name;            
            end if;
   
    --DBMS_OUTPUT.PUT_LINE(' ');
        else
            if a_product_cnt = 0 then
            dbms_output.put_line('---no additional alternative product name match found.');
                update PPSNEPL.HAZARD_HANDLE_INPUT set LOAD_STATUS='Inital Load Successful by Synonym match.', LOAD_DTM = to_date(u_end, 'MM-DD-YYYY HH24:MI:SS')
                where PPSNEPL.HAZARD_HANDLE_INPUT.product_name = HANDLE_product_name;
            else
                update PPSNEPL.HAZARD_HANDLE_INPUT set LOAD_STATUS='Inital Load Successful by Synonym/VA Product name match.', LOAD_DTM = to_date(u_end, 'MM-DD-YYYY HH24:MI:SS')
                where PPSNEPL.HAZARD_HANDLE_INPUT.product_name = HANDLE_product_name;            
            end if;
  
        end if;
            
   END LOOP; -- Input file lookup
    DBMS_OUTPUT.PUT_LINE('  ');
    DBMS_OUTPUT.PUT_LINE('NIOSH List of Hazardous Drugs - Hazard to HANDLE - Load Results Summary');
    DBMS_OUTPUT.PUT_LINE('  ');

   dbms_output.put_line('Total NIOSH Input Count: '||HANDLE_cnt || '.');
   dbms_output.put_line('Total PPSN Products Updated using Synonym Lookup: '||HANDLE_upd_cnt || '.');
   dbms_output.put_line('Total PPSN Products Updated using additional VA Product Name Lookup: '||name_upd_cnt || '.');
   dbms_output.put_line('Total NIOSH Input Not Found in PPSN: '||total_notfound_cnt || '.');
   
   SELECT TO_CHAR
    (SYSDATE, 'MM-DD-YYYY HH24:MI:SS') into d_end
     FROM DUAL;
   
   DBMS_OUTPUT.PUT_LINE('Load End: '|| d_end);
   commit;

END LOAD_HAZARD_HANDLE;
/
