select * from 
(select
hthTbl.ndfIen as ndf_ien,
hthTbl.productName as product_name,
hthTbl.hth as haz_to_handle,
htdTbl.htd as haz_to_dispose,
pepaTbl.pepa as primary_epa_code,
wscTbl.wsc as waste_sort_code,
shipNameTbl.shipName as shipping_name
from 
(select
PRODUCTS.NDF_PRODUCT_IEN ndfIen,
PRODUCTS.VA_PRODUCT_NAME productName,
NLVS.VA_DF_VALUE as hth,
NLVS.VADF_OWNER_ID_FK ownerId,
OWNERS.EPL_ID_PRODUCT_FK productId
from
EPL_VADF_NONLIST_VALUES nlvs join EPL_VA_DFS vadfs on NLVS.VADF_ID_FK=VADFS.ID 
join EPL_VADF_OWNERS owners on NLVS.VADF_OWNER_ID_FK=OWNERS.ID 
join EPL_PRODUCTS products on OWNERS.EPL_ID_PRODUCT_FK = PRODUCTS.EPL_ID
where VADFS.ID = 35 ) hthTbl
join
(select
PRODUCTS.NDF_PRODUCT_IEN ndfIen,
PRODUCTS.VA_PRODUCT_NAME productName,
NLVS.VA_DF_VALUE as htd,
NLVS.VADF_OWNER_ID_FK ownerId,
OWNERS.EPL_ID_PRODUCT_FK productId
from
EPL_VADF_NONLIST_VALUES nlvs join EPL_VA_DFS vadfs on NLVS.VADF_ID_FK=VADFS.ID 
join EPL_VADF_OWNERS owners on NLVS.VADF_OWNER_ID_FK=OWNERS.ID 
join EPL_PRODUCTS products on OWNERS.EPL_ID_PRODUCT_FK = PRODUCTS.EPL_ID
where VADFS.ID = 34 ) htdTbl
on htdTbl.ndfIen=hthTbl.ndfIen
left join
(select
PRODUCTS.NDF_PRODUCT_IEN ndfIen,
PRODUCTS.VA_PRODUCT_NAME productName,
NLVS.VA_DF_VALUE as pepa,
NLVS.VADF_OWNER_ID_FK ownerId,
OWNERS.EPL_ID_PRODUCT_FK productId
from
EPL_VADF_NONLIST_VALUES nlvs join EPL_VA_DFS vadfs on NLVS.VADF_ID_FK=VADFS.ID 
join EPL_VADF_OWNERS owners on NLVS.VADF_OWNER_ID_FK=OWNERS.ID 
join EPL_PRODUCTS products on OWNERS.EPL_ID_PRODUCT_FK = PRODUCTS.EPL_ID
where VADFS.ID = 149 ) pepaTbl on htdTbl.ndfIen=pepaTbl.ndfIen
left join
(select
PRODUCTS.NDF_PRODUCT_IEN ndfIen,
PRODUCTS.VA_PRODUCT_NAME productName,
NLVS.VA_DF_VALUE as wsc,
NLVS.VADF_OWNER_ID_FK ownerId,
OWNERS.EPL_ID_PRODUCT_FK productId
from
EPL_VADF_NONLIST_VALUES nlvs join EPL_VA_DFS vadfs on NLVS.VADF_ID_FK=VADFS.ID 
join EPL_VADF_OWNERS owners on NLVS.VADF_OWNER_ID_FK=OWNERS.ID 
join EPL_PRODUCTS products on OWNERS.EPL_ID_PRODUCT_FK = PRODUCTS.EPL_ID
where VADFS.ID = 150 ) wscTbl on htdTbl.ndfIen=wscTbl.ndfIen
left join
(select
PRODUCTS.NDF_PRODUCT_IEN ndfIen,
PRODUCTS.VA_PRODUCT_NAME productName,
NLVS.VA_DF_VALUE as shipName,
NLVS.VADF_OWNER_ID_FK ownerId,
OWNERS.EPL_ID_PRODUCT_FK productId
from
EPL_VADF_NONLIST_VALUES nlvs join EPL_VA_DFS vadfs on NLVS.VADF_ID_FK=VADFS.ID 
join EPL_VADF_OWNERS owners on NLVS.VADF_OWNER_ID_FK=OWNERS.ID 
join EPL_PRODUCTS products on OWNERS.EPL_ID_PRODUCT_FK = PRODUCTS.EPL_ID
where VADFS.ID = 151 ) shipNameTbl on htdTbl.ndfIen=shipNameTbl.ndfIen ) t
where t.haz_to_handle='true' or t.haz_to_dispose='true' order by t.product_name ASC;
