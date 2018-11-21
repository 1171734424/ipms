drop table TB_C_CAMPAIGNS;
create table TB_C_CAMPAIGNS (
	data_id        	VARCHAR2(16) not null,
	campaigns_name 	VARCHAR2(30) not null,
	campaigns_type 	VARCHAR2(2) not null,
	campaigns_desc 	VARCHAR2(200) not null,
	using_range    	VARCHAR2(2) not null,
	using_person   	VARCHAR2(12) not null,
	using_type     	VARCHAR2(6) not null,
	start_time     	DATE not null,
	end_time       	DATE not null,
	compaighs_cycle	VARCHAR2(30),
	business_id    	VARCHAR2(3) not null,
	record_time    	DATE	default	sysdate,
	record_user    	VARCHAR2(8) not null,
	priority       	VARCHAR2(2),
	remark         	VARCHAR2(200)
)
tablespace PMS_DAT 
	pctfree 10 
	initrans 1 
	maxtrans 255 storage(
	initial 16K 
	minextents 1 
	maxextents unlimited
);
comment on table TB_C_CAMPAIGNS is '订单表';
comment on column TB_C_CAMPAIGNS.data_id is '活动编号	格式:YYMMDD+门店编号+4位序列';
comment on column TB_C_CAMPAIGNS.campaigns_name is '活动名称';
comment on column TB_C_CAMPAIGNS.campaigns_type is '活动分类	大类1-充值优惠，2-消费优惠';
comment on column TB_C_CAMPAIGNS.campaigns_desc is '活动描述';
comment on column TB_C_CAMPAIGNS.using_range is '适用范围	0-所有，1-线上，2-线下';
comment on column TB_C_CAMPAIGNS.using_person is '适用对象	0-所有，1-注册，2-银卡，3-金卡，4-铂金，5-黑钻，6-团体（多选）';
comment on column TB_C_CAMPAIGNS.using_type is '适用类型	0-所有，1-酒店，2-公寓，3-民宿（多选）';
comment on column TB_C_CAMPAIGNS.start_time is '开始时间';
comment on column TB_C_CAMPAIGNS.end_time is '结束时间';
comment on column TB_C_CAMPAIGNS.compaighs_cycle is '活动周期';
comment on column TB_C_CAMPAIGNS.business_id is '针对业务	1-推荐有礼，2-开卡送抵用劵，3-充值满送，4-首住优惠价，5-推荐有礼，6-限时特价';
comment on column TB_C_CAMPAIGNS.record_time is '记录时间	更新数据时间';
comment on column TB_C_CAMPAIGNS.record_user is '操作人	员工';
comment on column TB_C_CAMPAIGNS.priority is '优先级	1-普通，2-中级，3-高级，4-超高级';
comment on column TB_C_CAMPAIGNS.remark is '备注';
alter table TB_C_CAMPAIGNS 
	add constraint PRIMARYKEY_TB_C_CAMPAIGNS primary key (data_id)
	using index 
	tablespace PMS_IDX 
	pctfree 10 
	initrans 2 
	maxtrans 255
	storage 
	( 
	 initial 64K 
	 minextents 1 
	 maxextents unlimited 
	);

drop table TB_C_CAMPAIGNS_RULE;
create table TB_C_CAMPAIGNS_RULE (
	data_id        	VARCHAR2(10) not null,
	campaign_id    	VARCHAR2(16) not null,
	member_rank    	VARCHAR2(8),
	coupon_price   	NUMBER(9,2),
	coupon1        	VARCHAR2(10),
	oper_money     	NUMBER(9,2)	default	0,
	oper_type      	VARCHAR2(2),
	oper_score     	VARCHAR2(10),
	room_count     	VARCHAR2(3),
	gift           	VARCHAR2(10),
	goods_service  	VARCHAR2(10),
	experience_count	VARCHAR2(2),
	branch_id      	VARCHAR2(6),
	room_type      	VARCHAR2(2),
	count          	VARCHAR2(3),
	count_price    	NUMBER(9,2)	default	0,
	live_day       	VARCHAR2(4),
	current_day    	VARCHAR2(4),
	day_count      	VARCHAR2(3),
	status         	VARCHAR2(2) not null
)
tablespace PMS_DAT 
	pctfree 10 
	initrans 1 
	maxtrans 255 storage(
	initial 16K 
	minextents 1 
	maxextents unlimited
);
comment on table TB_C_CAMPAIGNS_RULE is '活动详情表';
comment on column TB_C_CAMPAIGNS_RULE.data_id is '数据编号';
comment on column TB_C_CAMPAIGNS_RULE.campaign_id is '活动编号	格式:YYMMDD+门店编号+4位序列';
comment on column TB_C_CAMPAIGNS_RULE.member_rank is '会员等级	详见会员等级表';
comment on column TB_C_CAMPAIGNS_RULE.coupon_price is '抵用劵总额';
comment on column TB_C_CAMPAIGNS_RULE.coupon1 is '优惠券组合编号	详见优惠券组合表';
comment on column TB_C_CAMPAIGNS_RULE.oper_money is '操作金额	充值或消费';
comment on column TB_C_CAMPAIGNS_RULE.oper_type is '操作类型	1-正，2-负';
comment on column TB_C_CAMPAIGNS_RULE.oper_score is '积分	送';
comment on column TB_C_CAMPAIGNS_RULE.room_count is '房晚';
comment on column TB_C_CAMPAIGNS_RULE.gift is '礼包	可对应礼包表';
comment on column TB_C_CAMPAIGNS_RULE.goods_service is '商品服务';
comment on column TB_C_CAMPAIGNS_RULE.experience_count is '体验次数';
comment on column TB_C_CAMPAIGNS_RULE.branch_id is '归属门店';
comment on column TB_C_CAMPAIGNS_RULE.room_type is '优惠房型';
comment on column TB_C_CAMPAIGNS_RULE.count is '折扣	9.5折写95';
comment on column TB_C_CAMPAIGNS_RULE.count_price is '折扣价格	298元';
comment on column TB_C_CAMPAIGNS_RULE.live_day is '常住总天数';
comment on column TB_C_CAMPAIGNS_RULE.current_day is '第几天';
comment on column TB_C_CAMPAIGNS_RULE.day_count is '当天折扣';
comment on column TB_C_CAMPAIGNS_RULE.status is '状态	0-无效,1-有效';
alter table TB_C_CAMPAIGNS_RULE 
	add constraint PRIMARYKEY_TB_C_CAMPAIGNS_RULE primary key (data_id)
	using index 
	tablespace PMS_IDX 
	pctfree 10 
	initrans 2 
	maxtrans 255
	storage 
	( 
	 initial 64K 
	 minextents 1 
	 maxextents unlimited 
	);

drop table TB_C_COUPON_GROUP;
create table TB_C_COUPON_GROUP (
	data_id        	VARCHAR2(10) not null,
	total_price    	NUMBER(9,2)	default	0,
	ten_coupon     	VARCHAR2(2),
	twenty_coupon  	VARCHAR2(2),
	thirty_coupon  	VARCHAR2(2),
	fifty_coupon   	VARCHAR2(2),
	hundred_coupon 	VARCHAR2(2)
)
tablespace PMS_DAT 
	pctfree 10 
	initrans 1 
	maxtrans 255 storage(
	initial 16K 
	minextents 1 
	maxextents unlimited
);
comment on table TB_C_COUPON_GROUP is '优惠券组合表';
comment on column TB_C_COUPON_GROUP.data_id is '数据编号';
comment on column TB_C_COUPON_GROUP.total_price is '抵用劵面额';
comment on column TB_C_COUPON_GROUP.ten_coupon is '10元抵用劵';
comment on column TB_C_COUPON_GROUP.twenty_coupon is '20元抵用劵	详见优惠券组合表';
comment on column TB_C_COUPON_GROUP.thirty_coupon is '30元抵用劵';
comment on column TB_C_COUPON_GROUP.fifty_coupon is '50元抵用劵';
comment on column TB_C_COUPON_GROUP.hundred_coupon is '100元抵用劵';
alter table TB_C_COUPON_GROUP 
	add constraint PRIMARYKEY_TB_C_COUPON_GROUP primary key (data_id)
	using index 
	tablespace PMS_IDX 
	pctfree 10 
	initrans 2 
	maxtrans 255
	storage 
	( 
	 initial 64K 
	 minextents 1 
	 maxextents unlimited 
	);

