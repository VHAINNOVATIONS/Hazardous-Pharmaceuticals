-- Insert new hazmat fields to store values for hazard to dispose data
insert into PPSNEPL.EPL_VA_DFS(ID, VADF_NAME, VADF_TYPE, MULTI_SELECT_YN, VADF_USAGES, CREATED_BY, CREATED_DTM)
select max(ID)+1, 'primary.epa.code', 'STRING', 'N', 'P', 'DEVELOPER',to_date('6/4/2013', 'mm/dd/yyyy') 
FROM PPSNEPL.EPL_VA_DFS;

insert into PPSNEPL.EPL_VA_DFS(ID, VADF_NAME, VADF_TYPE, MULTI_SELECT_YN, VADF_USAGES, CREATED_BY, CREATED_DTM)
select max(ID)+1, 'waste.sort.code', 'STRING', 'N', 'P', 'DEVELOPER', to_date('6/4/2013', 'mm/dd/yyyy')
FROM PPSNEPL.EPL_VA_DFS;

insert into PPSNEPL.EPL_VA_DFS(ID, VADF_NAME, VADF_TYPE, MULTI_SELECT_YN, VADF_USAGES, CREATED_BY, CREATED_DTM)
select max(ID)+1, 'dot.shipping.name', 'STRING', 'N', 'P', 'DEVELOPER', to_date('6/4/2013', 'mm/dd/yyyy')
FROM PPSNEPL.EPL_VA_DFS;

COMMIT;

