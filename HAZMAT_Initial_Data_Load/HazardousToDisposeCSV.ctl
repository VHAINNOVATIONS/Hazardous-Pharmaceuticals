load data
    infile 'HazardousToDisposeCSVDOS.csv' 
    into table PPSNEPL.HAZARD_DISPOSE_INPUT
    fields terminated by "," optionally enclosed by '"'
     TRAILING NULLCOLS
    (NDC_NUMBER, PRODUCT_NAME, PRIMARY_EPA_CODE, WASTE_SORT_CODE, DOT_SHIPPING_NAME)
    
