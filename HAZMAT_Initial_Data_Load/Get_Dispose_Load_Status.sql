column load_status format a60
set linesize 250
set pagesize 150
select load_status, count(*) from PPSNEPL.HAZARD_DISPOSE_INPUT
group by load_status