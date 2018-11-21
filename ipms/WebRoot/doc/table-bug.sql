
drop table TB_NEW_BUGS;
create table TB_NEW_BUGS (
	data_id        	VARCHAR2(16) not null,
	bug_kind       	VARCHAR2(10) not null,
	bug_model      	VARCHAR2(20) not null,
	test_point     	VARCHAR2(100),
	bug_desc       	VARCHAR2(200) not null,
	expected_results	VARCHAR2(200),
	actual_results 	VARCHAR2(200),
	bug_origin     	VARCHAR2(4)	default	5,
	bug_source     	VARCHAR2(4),
	bug_root       	VARCHAR2(4),
	ques_kind      	VARCHAR2(4),
	bug_severity   	VARCHAR2(4),
	bug_priority   	VARCHAR2(4),
	status         	VARCHAR2(4),
	person_incharge	VARCHAR2(8),
	cutoff_time    	DATE,
	actual_repairtime	DATE,
	reason         	VARCHAR2(200),
	testor         	VARCHAR2(8),
	test_time      	DATE,
	record_time    	DATE	default	sysdate,
	record_user    	VARCHAR2(8) not null,
	remark         	VARCHAR2(200)
)
tablespace IDEAS_DAT 
	pctfree 10 
	initrans 1 
	maxtrans 255 storage(
	initial 16K 
	minextents 1 
	maxextents unlimited
);
comment on table TB_NEW_BUGS is 'bug表';
comment on column TB_NEW_BUGS.data_id is '数据编号	格式:YYMMDD+8位序列';
comment on column TB_NEW_BUGS.bug_kind is 'bug种类';
comment on column TB_NEW_BUGS.bug_model is 'bug所在模块	功能模块编号';
comment on column TB_NEW_BUGS.test_point is '测试点';
comment on column TB_NEW_BUGS.bug_desc is 'bug描述';
comment on column TB_NEW_BUGS.expected_results is '预期结果';
comment on column TB_NEW_BUGS.actual_results is '实际结果';
comment on column TB_NEW_BUGS.bug_origin is 'bug起源	1-需求阶段，2-架构阶段，3-设计阶段，4-编码阶段，5-测试阶段';
comment on column TB_NEW_BUGS.bug_source is 'bug来源	1-需求，2-架构，3-设计，4-编码，5-测试，6-集成';
comment on column TB_NEW_BUGS.bug_root is 'bug根源	1-目标，2-过程工具方法，3-人，4-沟通，5-软件，6-环境';
comment on column TB_NEW_BUGS.ques_kind is '问题种类	1-严重，2-一般，3-优化';
comment on column TB_NEW_BUGS.bug_severity is 'bug严重程度	1-故障，2-优化，3-变更';
comment on column TB_NEW_BUGS.bug_priority is 'bug优先级	1-高，2-中，3-低';
comment on column TB_NEW_BUGS.status is 'bug状态	1-new，2-open，3-fixed，0-unmodified，4-communicate，5-suspend，6-reopen，7-closed';
comment on column TB_NEW_BUGS.person_incharge is '负责人';
comment on column TB_NEW_BUGS.cutoff_time is '截止修复时间';
comment on column TB_NEW_BUGS.actual_repairtime is '实际修复时间';
comment on column TB_NEW_BUGS.reason is '说明原因';
comment on column TB_NEW_BUGS.testor is '测试人';
comment on column TB_NEW_BUGS.test_time is '测试时间';
comment on column TB_NEW_BUGS.record_time is '记录时间	更新数据时间';
comment on column TB_NEW_BUGS.record_user is '提交bug人';
comment on column TB_NEW_BUGS.remark is '备注';
alter table TB_NEW_BUGS 
	add constraint PRIMARYKEY_TB_NEW_BUGS primary key (data_id)
	using index 
	tablespace IDEAS_IDX 
	pctfree 10 
	initrans 2 
	maxtrans 255
	storage 
	( 
	 initial 64K 
	 minextents 1 
	 maxextents unlimited 
	);

drop table TB_BUG_KIND;
create table TB_BUG_KIND (
	data_id        	VARCHAR2(10) not null,
	super_kind     	VARCHAR2(8)	default	0,
	bugkind_desc   	VARCHAR2(200),
	record_time    	DATE	default	sysdate,
	record_user    	VARCHAR2(8) not null,
	remark         	VARCHAR2(200)
)
tablespace IDEAS_DAT 
	pctfree 10 
	initrans 1 
	maxtrans 255 storage(
	initial 16K 
	minextents 1 
	maxextents unlimited
);
comment on table TB_BUG_KIND is 'bug种类表';
comment on column TB_BUG_KIND.data_id is '数据编号';
comment on column TB_BUG_KIND.super_kind is '父类';
comment on column TB_BUG_KIND.bugkind_desc is '描述';
comment on column TB_BUG_KIND.record_time is '记录时间	更新数据时间';
comment on column TB_BUG_KIND.record_user is '操作人	员工';
comment on column TB_BUG_KIND.remark is '备注';
alter table TB_BUG_KIND 
	add constraint PRIMARYKEY_TB_BUG_KIND primary key (data_id)
	using index 
	tablespace IDEAS_IDX 
	pctfree 10 
	initrans 2 
	maxtrans 255
	storage 
	( 
	 initial 64K 
	 minextents 1 
	 maxextents unlimited 
	);

drop table TB_P_SOFTWARE;
create table TB_P_SOFTWARE (
	data_id        	VARCHAR2(16) not null,
	soft_name      	VARCHAR2(20) not null,
	soft_manager   	VARCHAR2(8),
	record_time    	DATE	default	sysdate,
	record_user    	VARCHAR2(8) not null,
	remark         	VARCHAR2(200)
)
tablespace IDEAS_DAT 
	pctfree 10 
	initrans 1 
	maxtrans 255 storage(
	initial 16K 
	minextents 1 
	maxextents unlimited
);
comment on table TB_P_SOFTWARE is '软件项目表';
comment on column TB_P_SOFTWARE.data_id is '数据编号	格式:YYMMDD+8位序列';
comment on column TB_P_SOFTWARE.soft_name is '项目名称';
comment on column TB_P_SOFTWARE.soft_manager is '项目负责人';
comment on column TB_P_SOFTWARE.record_time is '记录时间	更新数据时间';
comment on column TB_P_SOFTWARE.record_user is '记录人';
comment on column TB_P_SOFTWARE.remark is '备注';
alter table TB_P_SOFTWARE 
	add constraint PRIMARYKEY_TB_P_SOFTWARE primary key (data_id)
	using index 
	tablespace IDEAS_IDX 
	pctfree 10 
	initrans 2 
	maxtrans 255
	storage 
	( 
	 initial 64K 
	 minextents 1 
	 maxextents unlimited 
	);

drop table TB_SOFT_MEMBERS;
create table TB_SOFT_MEMBERS (
	data_id        	VARCHAR2(10) not null,
	soft_id        	VARCHAR2(16),
	soft_manager   	VARCHAR2(8),
	record_time    	DATE	default	sysdate,
	remark         	VARCHAR2(200)
)
tablespace IDEAS_DAT 
	pctfree 10 
	initrans 1 
	maxtrans 255 storage(
	initial 16K 
	minextents 1 
	maxextents unlimited
);
comment on table TB_SOFT_MEMBERS is '软件项目成员表';
comment on column TB_SOFT_MEMBERS.data_id is '数据编号	格式:8位序列';
comment on column TB_SOFT_MEMBERS.soft_id is '项目名称';
comment on column TB_SOFT_MEMBERS.soft_manager is '项目成员';
comment on column TB_SOFT_MEMBERS.record_time is '记录时间	更新数据时间';
comment on column TB_SOFT_MEMBERS.remark is '备注';
alter table TB_SOFT_MEMBERS 
	add constraint PRIMARYKEY_TB_SOFT_MEMBERS primary key (data_id)
	using index 
	tablespace IDEAS_IDX 
	pctfree 10 
	initrans 2 
	maxtrans 255
	storage 
	( 
	 initial 64K 
	 minextents 1 
	 maxextents unlimited 
	);

drop table TB_P_MODEL;
create table TB_P_MODEL (
	data_id        	VARCHAR2(10) not null,
	model_id       	VARCHAR2(20) not null,
	soft_no        	VARCHAR2(16) not null,
	model_desc     	VARCHAR2(100),
	father_model   	VARCHAR2(10),
	model_developer	VARCHAR2(8),
	record_time    	DATE	default	sysdate,
	record_user    	VARCHAR2(8) not null,
	remark         	VARCHAR2(200)
)
tablespace IDEAS_DAT 
	pctfree 10 
	initrans 1 
	maxtrans 255 storage(
	initial 16K 
	minextents 1 
	maxextents unlimited
);
comment on table TB_P_MODEL is '项目功能模块表';
comment on column TB_P_MODEL.data_id is '数据编号	10位序列';
comment on column TB_P_MODEL.model_id is '模块编号';
comment on column TB_P_MODEL.soft_no is '所属项目编号';
comment on column TB_P_MODEL.model_desc is 'model描述';
comment on column TB_P_MODEL.father_model is '父类model';
comment on column TB_P_MODEL.model_developer is '开发人';
comment on column TB_P_MODEL.record_time is '记录时间	更新数据时间';
comment on column TB_P_MODEL.record_user is '记录人	员工';
comment on column TB_P_MODEL.remark is '备注';
alter table TB_P_MODEL 
	add constraint PRIMARYKEY_TB_P_MODEL primary key (data_id)
	using index 
	tablespace IDEAS_IDX 
	pctfree 10 
	initrans 2 
	maxtrans 255
	storage 
	( 
	 initial 64K 
	 minextents 1 
	 maxextents unlimited 
	);

drop table TB_BUGSTATUS_LOGS;
create table TB_BUGSTATUS_LOGS (
	data_id        	VARCHAR2(10) not null,
	bug_id         	VARCHAR2(16) not null,
	alter_kind     	VARCHAR2(4) not null,
	alter_value    	VARCHAR2(50) not null,
	content_desc   	VARCHAR2(100),
	record_time    	DATE	default	sysdate,
	record_user    	VARCHAR2(8) not null,
	remark         	VARCHAR2(200)
)
tablespace IDEAS_DAT 
	pctfree 10 
	initrans 1 
	maxtrans 255 storage(
	initial 16K 
	minextents 1 
	maxextents unlimited
);
comment on table TB_BUGSTATUS_LOGS is 'BUG状态修改日志表';
comment on column TB_BUGSTATUS_LOGS.data_id is '数据编号	10位序列';
comment on column TB_BUGSTATUS_LOGS.bug_id is 'bugs编号';
comment on column TB_BUGSTATUS_LOGS.alter_kind is '修改种类	1-bug状态，2-开发人，3-测试人';
comment on column TB_BUGSTATUS_LOGS.alter_value is '修改值';
comment on column TB_BUGSTATUS_LOGS.content_desc is '内容描述';
comment on column TB_BUGSTATUS_LOGS.record_time is '记录时间	更新数据时间';
comment on column TB_BUGSTATUS_LOGS.record_user is '记录人	员工';
comment on column TB_BUGSTATUS_LOGS.remark is '备注';
alter table TB_BUGSTATUS_LOGS 
	add constraint PRIMARYKEY_TB_BUGSTATUS_LOGS primary key (data_id)
	using index 
	tablespace IDEAS_IDX 
	pctfree 10 
	initrans 2 
	maxtrans 255
	storage 
	( 
	 initial 64K 
	 minextents 1 
	 maxextents unlimited 
	);

