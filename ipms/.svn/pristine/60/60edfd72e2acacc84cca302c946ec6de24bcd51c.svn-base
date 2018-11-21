-----------------------------1.创建项目表空间-----------------------------
CREATE TABLESPACE "PMS_DAT" LOGGING 
DATAFILE 'C:\oracle\product\10.2.0\oradata\orcl\PMS_DAT.DBF'
SIZE 100M AUTOEXTEND ON NEXT 100M MAXSIZE UNLIMITED
EXTENT MANAGEMENT LOCAL SEGMENT SPACE MANAGEMENT AUTO;
 
CREATE TABLESPACE "PMS_IDX" LOGGING 
DATAFILE 'C:\oracle\product\10.2.0\oradata\orcl\PMS_IDX.DBF' 
SIZE 50M AUTOEXTEND ON NEXT 50M MAXSIZE UNLIMITED
EXTENT MANAGEMENT LOCAL SEGMENT SPACE MANAGEMENT AUTO;

-----------------------------2.创建用户并赋权-----------------------------
drop user ucr_pms cascade;
create user ucr_pms identified by ucr_pms ;
grant connect,resource to ucr_pms ;
grant dba to ucr_pms ;