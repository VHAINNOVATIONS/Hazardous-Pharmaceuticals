load data
    infile 'HazardousToHandleCSVDOS.csv' 
    into table PPSNEPL.HAZARD_HANDLE_INPUT
    fields terminated by "," optionally enclosed by '"'
     TRAILING NULLCOLS
    (PRODUCT_NAME, SOURCE_NAME, AHFS_PHARM_CLASS)
    
