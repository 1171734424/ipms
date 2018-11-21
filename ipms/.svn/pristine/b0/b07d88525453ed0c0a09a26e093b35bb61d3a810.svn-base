drop table TL_C_LOGINLOG;
create table TL_C_LOGINLOG (
	log_id         	VARCHAR2(12) not null,
	login_id       	VARCHAR2(8) not null,
	source         	VARCHAR2(1) not null,
	user_id        	VARCHAR2(8) not null,
	login_ip       	VARCHAR2(15) not null,
	status         	VARCHAR2(1) not null,
	browser        	VARCHAR2(10),
	resolution     	VARCHAR2(9),
	record_time    	DATE	default	sysdate
)
tablespace PMS_DAT 
	pctfree 10 
	initrans 1 
	maxtrans 255 storage(
	initial 16K 
	minextents 1 
	maxextents unlimited
);
comment on table TL_C_LOGINLOG is '登录日志表';
comment on column TL_C_LOGINLOG.log_id is '登录日志编号	格式:YYMMDD+6位序列';
comment on column TL_C_LOGINLOG.login_id is '识别编号	门店编号/会员编号';
comment on column TL_C_LOGINLOG.source is '登录端	1-app,2-网站,3-分店,4-wap';
comment on column TL_C_LOGINLOG.user_id is '登录人';
comment on column TL_C_LOGINLOG.login_ip is '登录IP	登录的IP地址,xxx.yyy.zzz.ooo';
comment on column TL_C_LOGINLOG.status is '登录状态	登录是否成功的标识位';
comment on column TL_C_LOGINLOG.browser is '登录浏览器';
comment on column TL_C_LOGINLOG.resolution is '登录分辨率	格式:yyyy,xxxx';
comment on column TL_C_LOGINLOG.record_time is '登录时间';
alter table TL_C_LOGINLOG 
	add constraint PRIMARYKEY_TL_C_LOGINLOG primary key (log_id,login_id)
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

drop table TL_P_OPERATELOG;
create table TL_P_OPERATELOG (
	log_id         	VARCHAR2(15) not null,
	oper_type      	VARCHAR2(1) not null,
	oper_module    	VARCHAR2(3) not null,
	content        	VARCHAR2(30),
	record_user    	VARCHAR2(8) not null,
	record_time    	DATE	default	sysdate,
	oper_ip        	VARCHAR2(15) not null,
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
comment on table TL_P_OPERATELOG is '操作日志表';
comment on column TL_P_OPERATELOG.log_id is '操作日志编号	格式:YYMMDD+门店号+5位序列';
comment on column TL_P_OPERATELOG.oper_type is '操作类型	添加,修改,删除,查询等类容（可放在通用字典表）';
comment on column TL_P_OPERATELOG.oper_module is '操作模块	操作模块,比如订单模块,智能模块等,关联的是菜单表编号';
comment on column TL_P_OPERATELOG.content is '操作内容';
comment on column TL_P_OPERATELOG.record_user is '操作人	操作人信息';
comment on column TL_P_OPERATELOG.record_time is '操作时间	日志记录创建时间';
comment on column TL_P_OPERATELOG.oper_ip is '操作IP	操作人的IP地址,xxx.yyy.zzz.ooo';
comment on column TL_P_OPERATELOG.remark is '备注';
alter table TL_P_OPERATELOG 
	add constraint PRIMARYKEY_TL_P_OPERATELOG primary key (log_id)
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

drop table TB_P_ROOM;
create table TB_P_ROOM (
	branch_id      	VARCHAR2(4) not null,
	room_id        	VARCHAR2(5) not null,
	theme          	VARCHAR2(1) not null,
	room_type      	VARCHAR2(3) not null,
	area           	NUMBER(3),
	floor          	VARCHAR2(2) not null,
	status         	VARCHAR2(1)	default	0,
	record_time    	DATE	default	sysdate,
	record_user    	VARCHAR2(8) not null,
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
comment on table TB_P_ROOM is '客房主表';
comment on column TB_P_ROOM.branch_id is '门店编号';
comment on column TB_P_ROOM.room_id is '房号	房间号';
comment on column TB_P_ROOM.theme is '场景	1-酒店,2-公寓';
comment on column TB_P_ROOM.room_type is '客房类型	见tp_p_roomtype';
comment on column TB_P_ROOM.area is '建筑面积	面积为平方米';
comment on column TB_P_ROOM.floor is '楼层	房间所在楼层';
comment on column TB_P_ROOM.status is '房态	0-空,1-预定,2-已入住,T-停售,Z-脏房,W-维修,O-其他';
comment on column TB_P_ROOM.record_time is '更新时间';
comment on column TB_P_ROOM.record_user is '更新人	员工';
comment on column TB_P_ROOM.remark is '备注';
alter table TB_P_ROOM 
	add constraint PRIMARYKEY_TB_P_ROOM primary key (branch_id,room_id)
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

drop table TP_P_ROOMTYPE;
create table TP_P_ROOMTYPE (
	room_type      	VARCHAR2(3) not null,
	room_name      	VARCHAR2(16) not null,
	theme          	VARCHAR2(1) not null,
	room_bed       	NUMBER(2) not null,
	bed_desc       	VARCHAR2(16) not null,
	broadband      	VARCHAR2(1) not null,
	room_label     	VARCHAR2(40),
	room_desc      	VARCHAR2(2000),
	tips           	VARCHAR2(40),
	room_position  	VARCHAR2(1),
	status         	VARCHAR2(1) not null,
	record_time    	DATE	default	sysdate,
	record_user    	VARCHAR2(8) not null,
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
comment on table TP_P_ROOMTYPE is '房型表';
comment on column TP_P_ROOMTYPE.room_type is '房间类型	客房类型-缩写编码';
comment on column TP_P_ROOMTYPE.room_name is '类型名称	房型名称';
comment on column TP_P_ROOMTYPE.theme is '场景	1-酒店,2-公寓';
comment on column TP_P_ROOMTYPE.room_bed is '床位	人数上限';
comment on column TP_P_ROOMTYPE.bed_desc is '床型	1-大床,2-双床,3-大/双,4-多床';
comment on column TP_P_ROOMTYPE.broadband is '宽带	1-有线免费,2-免费';
comment on column TP_P_ROOMTYPE.room_label is '客房标签	客房标签,如100010111100101010,见通用参数表';
comment on column TP_P_ROOMTYPE.room_desc is '客房描述	客房条件描述';
comment on column TP_P_ROOMTYPE.tips is '入住须知	入住须知,标签化,如100010111100101010,见通用参数表';
comment on column TP_P_ROOMTYPE.room_position is '客房朝向	八个方位,参数化';
comment on column TP_P_ROOMTYPE.status is '状态	0-失效,1-在用';
comment on column TP_P_ROOMTYPE.record_time is '更新时间';
comment on column TP_P_ROOMTYPE.record_user is '更新人	员工';
comment on column TP_P_ROOMTYPE.remark is '备注	同一种房型会有多种早餐搭配,具体见房价码中的包价（包价商品）';
alter table TP_P_ROOMTYPE 
	add constraint PRIMARYKEY_TP_P_ROOMTYPE primary key (room_type)
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

drop table TB_P_HOUSE;
create table TB_P_HOUSE (
	house_id       	VARCHAR2(6) not null,
	house_no       	VARCHAR2(7) not null,
	house_type     	VARCHAR2(2) not null,
	area           	NUMBER(3),
	floor          	VARCHAR2(2) not null,
	beds           	NUMBER(2) not null,
	bed_desc       	VARCHAR2(16) not null,
	broadband      	VARCHAR2(1) not null,
	label          	VARCHAR2(40),
	house_desc     	VARCHAR2(2000),
	tips           	VARCHAR2(40),
	position       	VARCHAR2(1),
	address        	VARCHAR2(60),
	status         	VARCHAR2(2)	default	1,
	record_time    	DATE	default	sysdate,
	record_user    	VARCHAR2(8) not null,
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
comment on table TB_P_HOUSE is '民宿主表';
comment on column TB_P_HOUSE.house_id is '房屋编号	房屋编号-民宿';
comment on column TB_P_HOUSE.house_no is '门牌号';
comment on column TB_P_HOUSE.house_type is '民宿类型';
comment on column TB_P_HOUSE.area is '建筑面积	面积为平方米';
comment on column TB_P_HOUSE.floor is '楼层	房间所在楼层';
comment on column TB_P_HOUSE.beds is '床位	人数上限';
comment on column TB_P_HOUSE.bed_desc is '床型	1-大床,2-双床,3-大/双,4-多床';
comment on column TB_P_HOUSE.broadband is '宽带	1-有线免费,2-免费';
comment on column TB_P_HOUSE.label is '客房标签	客房标签,如100010111100101010,见通用参数表';
comment on column TB_P_HOUSE.house_desc is '客房描述	客房条件描述';
comment on column TB_P_HOUSE.tips is '入住须知	入住须知,标签化,如100010111100101010,见通用参数表';
comment on column TB_P_HOUSE.position is '客房朝向	八个方位,参数化';
comment on column TB_P_HOUSE.address is '地址	详细地址';
comment on column TB_P_HOUSE.status is '房态	0-删除,1-空房,2-预定,3-已入住,T-停售,Z-脏房,W-维修,R-其他';
comment on column TB_P_HOUSE.record_time is '更新时间';
comment on column TB_P_HOUSE.record_user is '更新人';
comment on column TB_P_HOUSE.remark is '备注';
alter table TB_P_HOUSE 
	add constraint PRIMARYKEY_TB_P_HOUSE primary key (house_id)
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

drop table TB_P_ROOMPRICE;
create table TB_P_ROOMPRICE (
	branch_id      	VARCHAR2(4) not null,
	rp_id          	VARCHAR2(3) not null,
	rp_name        	VARCHAR2(8) not null,
	room_type      	VARCHAR2(3) not null,
	room_price     	NUMBER(9,2)	default	0,
	pack_id        	VARCHAR2(3),
	rp_type        	VARCHAR2(1) not null,
	discount       	NUMBER(3,2) not null,
	status         	VARCHAR2(1) not null,
	record_time    	DATE	default	sysdate,
	record_user    	VARCHAR2(8) not null,
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
comment on table TB_P_ROOMPRICE is '房价编码表';
comment on column TB_P_ROOMPRICE.branch_id is '门店编号';
comment on column TB_P_ROOMPRICE.rp_id is '房价码	MSJ-门市价,PKJ-普卡价,YKJ-银卡价,JKJ-金卡价,BJJ-铂金价,HZJ-黑钻价';
comment on column TB_P_ROOMPRICE.rp_name is '房价码名称	MSJ-门市价,PKJ-普卡价,YKJ-银卡价,JKJ-金卡价,BJJ-铂金价,HZJ-黑钻价';
comment on column TB_P_ROOMPRICE.room_type is '客房类型';
comment on column TB_P_ROOMPRICE.room_price is '房价	房价,保留两位小数';
comment on column TB_P_ROOMPRICE.pack_id is '包价	包价=包价商品代码,见tp_room_pack(房价包价表)';
comment on column TB_P_ROOMPRICE.rp_type is '房价码类型	1-基准,2-折扣,3-固定';
comment on column TB_P_ROOMPRICE.discount is '优惠	1,0.95,0.9,0.85,0.8,0.75';
comment on column TB_P_ROOMPRICE.status is '状态	0-失效,1-基准,2-调整';
comment on column TB_P_ROOMPRICE.record_time is '更新时间';
comment on column TB_P_ROOMPRICE.record_user is '更新人	员工';
comment on column TB_P_ROOMPRICE.remark is '备注	价格以调整状态的房价为准,如无调整房价,则取基准状态房价';
alter table TB_P_ROOMPRICE 
	add constraint PRIMARYKEY_TB_P_ROOMPRICE primary key (branch_id,rp_id,status)
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

drop table TP_P_ROOMPACK;
create table TP_P_ROOMPACK (
	pack_id        	VARCHAR2(4) not null,
	pack_name      	VARCHAR2(10) not null,
	status         	VARCHAR2(2) not null,
	record_time    	DATE	default	sysdate,
	record_user    	VARCHAR2(8) not null,
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
comment on table TP_P_ROOMPACK is '包价编码表';
comment on column TP_P_ROOMPACK.pack_id is '包价码	F1-1份,F2-2份,F3-护发素,F4-早餐';
comment on column TP_P_ROOMPACK.pack_name is '包价名	F1-1份,F2-2份,F3-护发素,F4-早餐';
comment on column TP_P_ROOMPACK.status is '状态	0-失效,1-在用';
comment on column TP_P_ROOMPACK.record_time is '更新时间';
comment on column TP_P_ROOMPACK.record_user is '更新人	员工';
comment on column TP_P_ROOMPACK.remark is '备注';
alter table TP_P_ROOMPACK 
	add constraint PRIMARYKEY_TP_P_ROOMPACK primary key (pack_id)
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

drop table TB_P_PRICEAPPLY;
create table TB_P_PRICEAPPLY (
	apply_id       	VARCHAR2(12) not null,
	branch_id      	VARCHAR2(4) not null,
	apply_type     	VARCHAR2(1) not null,
	valid_start    	DATE not null,
	valid_end      	DATE not null,
	valid_day      	VARCHAR2(7),
	filter_day     	VARCHAR2(90),
	status         	VARCHAR2(1) not null,
	apply_time     	DATE	default	sysdate,
	record_user    	VARCHAR2(8) not null,
	record_time    	DATE	default	sysdate,
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
comment on table TB_P_PRICEAPPLY is '价格申请/调整表';
comment on column TB_P_PRICEAPPLY.apply_id is '申请ID	格式:YYMMDD+门店编号+2位序列';
comment on column TB_P_PRICEAPPLY.branch_id is '门店编号';
comment on column TB_P_PRICEAPPLY.apply_type is '调整类型	1-房价调整,2-时租调整';
comment on column TB_P_PRICEAPPLY.valid_start is '生效日期起	价格调整后,价格开始日期';
comment on column TB_P_PRICEAPPLY.valid_end is '生效日期止	价格调整后,价格结束日期';
comment on column TB_P_PRICEAPPLY.valid_day is '生效周期	0123456 代表周日到周六';
comment on column TB_P_PRICEAPPLY.filter_day is '过滤日期	1-31,不可用日期。';
comment on column TB_P_PRICEAPPLY.status is '状态	0-失效,1-待审核,2-生效,3-退回,,7天以上需审核,7天以内不需审核';
comment on column TB_P_PRICEAPPLY.apply_time is '申请日期';
comment on column TB_P_PRICEAPPLY.record_user is '申请人	申请员工';
comment on column TB_P_PRICEAPPLY.record_time is '更新时间	更新数据时间';
comment on column TB_P_PRICEAPPLY.remark is '备注	申请的价格调整见明细表';
alter table TB_P_PRICEAPPLY 
	add constraint PRIMARYKEY_TB_P_PRICEAPPLY primary key (apply_id)
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

drop table TB_P_APPLYDETAIL;
create table TB_P_APPLYDETAIL (
	detail_id      	VARCHAR2(8) not null,
	apply_id       	VARCHAR2(12) not null,
	content        	VARCHAR2(3) not null,
	room_type      	VARCHAR2(2) not null,
	room_price     	NUMBER(9,2)	default	0,
	row_order      	VARCHAR2(2),
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
comment on table TB_P_APPLYDETAIL is '价格申请/调整子表';
comment on column TB_P_APPLYDETAIL.detail_id is '明细ID	8位序列';
comment on column TB_P_APPLYDETAIL.apply_id is '申请ID	格式:YYMMDD+门店编号+2位序列';
comment on column TB_P_APPLYDETAIL.content is '房价码/时租房	MSJ-门市价,PKJ-普卡价,YKJ-银卡价,JKJ-金卡价,BJJ-铂金价,HZJ-黑钻价,SZ-时租';
comment on column TB_P_APPLYDETAIL.room_type is '客房类型';
comment on column TB_P_APPLYDETAIL.room_price is '房价	房价,保留两位小数';
comment on column TB_P_APPLYDETAIL.row_order is '顺序号	1-99';
comment on column TB_P_APPLYDETAIL.remark is '备注	生效后更新进房价表,状态为2';
alter table TB_P_APPLYDETAIL 
	add constraint PRIMARYKEY_TB_P_APPLYDETAIL primary key (detail_id)
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

drop table TB_P_ORDER;
create table TB_P_ORDER (
	order_id       	VARCHAR2(14) not null,
	branch_id      	VARCHAR2(4) not null,
	source         	VARCHAR2(2) not null,
	theme          	VARCHAR2(1) not null,
	activity       	VARCHAR2(5),
	sale_staff     	VARCHAR2(8),
	room_id        	VARCHAR2(6),
	room_type      	VARCHAR2(2) not null,
	order_user     	VARCHAR2(8) not null,
	m_phone        	VARCHAR2(11) not null,
	rp_id          	VARCHAR2(3) not null,
	arrival_time   	DATE not null,
	leave_time     	DATE not null,
	checkout_time  	DATE	default	sysdate,
	room_price     	NUMBER(9,2) not null,
	guarantee      	VARCHAR2(2) not null,
	limited        	VARCHAR2(6) not null,
	user_checkin   	VARCHAR2(60) not null,
	payment_type   	VARCHAR2(1)	default	0,
	advance_cash   	NUMBER(9,2)	default	0,
	advance_score  	NUMBER(9),
	room_remark    	VARCHAR2(200),
	reception_remark	VARCHAR2(200),
	cash_remark    	VARCHAR2(200) not null,
	status         	VARCHAR2(2) not null,
	order_time     	DATE	default	sysdate,
	record_time    	DATE	default	sysdate,
	record_user    	VARCHAR2(8) not null,
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
comment on table TB_P_ORDER is '订单表';
comment on column TB_P_ORDER.order_id is '订单号	格式:YYMMDD+门店编号+4位序列';
comment on column TB_P_ORDER.branch_id is '门店编号';
comment on column TB_P_ORDER.source is '订单来源	1-app,2-网站,3-分店,4-wap,5-合作渠道,6-其他';
comment on column TB_P_ORDER.theme is '场景	1-酒店,1-公寓,3-民宿';
comment on column TB_P_ORDER.activity is '活动	取自营销活动表';
comment on column TB_P_ORDER.sale_staff is '销售员';
comment on column TB_P_ORDER.room_id is '房号	可为空.民宿时为房屋编号';
comment on column TB_P_ORDER.room_type is '房间类型	客房类型';
comment on column TB_P_ORDER.order_user is '预定人';
comment on column TB_P_ORDER.m_phone is '预定人手机';
comment on column TB_P_ORDER.rp_id is '房价码	MSJ-门市价,PKJ-普卡价,YKJ-银卡价,JKJ-金卡价,BJJ-铂金价,HZJ-黑钻价';
comment on column TB_P_ORDER.arrival_time is '抵店日期';
comment on column TB_P_ORDER.leave_time is '离店日期';
comment on column TB_P_ORDER.checkout_time is '预离日期	房价,保留两位小数';
comment on column TB_P_ORDER.room_price is '房价	1无担保,2担保留房';
comment on column TB_P_ORDER.guarantee is '担保	保留到几点,如18:50';
comment on column TB_P_ORDER.limited is '时效	第1人为主客人,多人逗号分隔';
comment on column TB_P_ORDER.user_checkin is '入住人	1-在线付,2-到店付,3-担保';
comment on column TB_P_ORDER.payment_type is '支付类型';
comment on column TB_P_ORDER.advance_cash is '预付-现金';
comment on column TB_P_ORDER.advance_score is '预付-积分';
comment on column TB_P_ORDER.room_remark is '客房备注';
comment on column TB_P_ORDER.reception_remark is '接待备注';
comment on column TB_P_ORDER.cash_remark is '收银备注	取所转订单的订单编号';
comment on column TB_P_ORDER.status is '状态	0-取消,1-新订,2-未到,3-在住/转单,4-离店';
comment on column TB_P_ORDER.order_time is '下单日期	订单日期';
comment on column TB_P_ORDER.record_time is '更新时间	更新数据时间';
comment on column TB_P_ORDER.record_user is '更新人	员工';
comment on column TB_P_ORDER.remark is '备注';
alter table TB_P_ORDER 
	add constraint PRIMARYKEY_TB_P_ORDER primary key (order_id)
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

drop table TB_P_CHECK;
create table TB_P_CHECK (
	check_id       	VARCHAR2(14) not null,
	branch_id      	VARCHAR2(4) not null,
	room_id        	VARCHAR2(6) not null,
	room_type      	VARCHAR2(2) not null,
	rp_id          	VARCHAR2(3) not null,
	room_price     	NUMBER(9,2) not null,
	check_user     	VARCHAR2(60)	default	0,
	checkin_time   	DATE	default	sysdate,
	checkout_time  	DATE	default	sysdate,
	deposit        	NUMBER(9,2)	default	0,
	ttv            	NUMBER(9,2)	default	0,
	cost           	NUMBER(9,2)	default	0,
	pay            	NUMBER(9,2),
	account_fee    	NUMBER(9,2),
	total_fee      	NUMBER(9,2),
	pay_type       	VARCHAR2(1),
	pay_info       	VARCHAR2(21)	default	sysdate,
	payer          	VARCHAR2(8),
	switch_id      	VARCHAR2(14),
	status         	VARCHAR2(2) not null,
	record_time    	DATE not null,
	record_user    	VARCHAR2(8) not null,
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
comment on table TB_P_CHECK is '房单表';
comment on column TB_P_CHECK.check_id is '房单编号	同订单表订单编号';
comment on column TB_P_CHECK.branch_id is '门店编号';
comment on column TB_P_CHECK.room_id is '房号	民宿时为房屋编号';
comment on column TB_P_CHECK.room_type is '房间类型	客房类型';
comment on column TB_P_CHECK.rp_id is '房价码';
comment on column TB_P_CHECK.room_price is '房价	房价,保留两位小数';
comment on column TB_P_CHECK.check_user is '实际入住人	第1人为主客人,多人逗号分隔';
comment on column TB_P_CHECK.checkin_time is '实际入住时间';
comment on column TB_P_CHECK.checkout_time is '预离日期';
comment on column TB_P_CHECK.deposit is '押金	金额';
comment on column TB_P_CHECK.ttv is 'TTV	会员营收。一般为有会员制的酒店集团,针对特定会员等级(例如金卡,白金卡),分析其会员营收。';
comment on column TB_P_CHECK.cost is '消费	实际消费额';
comment on column TB_P_CHECK.pay is '结算	已支付金额';
comment on column TB_P_CHECK.account_fee is '账面金额	应付款（不含当日日结）,+余额,-欠费';
comment on column TB_P_CHECK.total_fee is '平账金额	应付款（含日结）,+余额,-欠费';
comment on column TB_P_CHECK.pay_type is '结账方式';
comment on column TB_P_CHECK.pay_info is '结账信息	银行卡号,预授权卡';
comment on column TB_P_CHECK.payer is '支付主体	关联统一支付时使用';
comment on column TB_P_CHECK.switch_id is '转单号	转单的订单号,取所转的订单编号';
comment on column TB_P_CHECK.status is '状态	1-在住2-离店3-已退未结';
comment on column TB_P_CHECK.record_time is '更新时间	1-现金,2-银行卡,3-预授权,4-支付宝,5-微信';
comment on column TB_P_CHECK.record_user is '更新人	员工';
comment on column TB_P_CHECK.remark is '备注';
alter table TB_P_CHECK 
	add constraint PRIMARYKEY_TB_P_CHECK primary key (check_id)
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

drop table TB_P_ROOMMAPPING;
create table TB_P_ROOMMAPPING (
	room_id        	VARCHAR2(6) not null,
	rela_roomid    	VARCHAR2(6) not null
)
tablespace PMS_DAT 
	pctfree 10 
	initrans 1 
	maxtrans 255 storage(
	initial 16K 
	minextents 1 
	maxextents unlimited
);
comment on table TB_P_ROOMMAPPING is '客房关联表';
comment on column TB_P_ROOMMAPPING.room_id is '主房号	支付主单';
comment on column TB_P_ROOMMAPPING.rela_roomid is '关联房号	被关联房单';

drop table TL_P_SWITCHORDER;
create table TL_P_SWITCHORDER (
	log_id         	VARCHAR2(13) not null,
	check_id       	VARCHAR2(14) not null,
	order_id       	VARCHAR2(14) not null,
	branch_id      	VARCHAR2(4) not null,
	status         	VARCHAR2(1) not null,
	record_time    	DATE	default	sysdate,
	record_user    	VARCHAR2(8) not null
)
tablespace PMS_DAT 
	pctfree 10 
	initrans 1 
	maxtrans 255 storage(
	initial 16K 
	minextents 1 
	maxextents unlimited
);
comment on table TL_P_SWITCHORDER is '转单日志表';
comment on column TL_P_SWITCHORDER.log_id is '日志号	格式:YYMMDD+门店编号+3位序列';
comment on column TL_P_SWITCHORDER.check_id is '房单号	取自房单表';
comment on column TL_P_SWITCHORDER.order_id is '订单号	取自订单表';
comment on column TL_P_SWITCHORDER.branch_id is '门店编号';
comment on column TL_P_SWITCHORDER.status is '状态	0-已转,1-这是笔最新的转单';
comment on column TL_P_SWITCHORDER.record_time is '转单时间';
comment on column TL_P_SWITCHORDER.record_user is '操作人';
alter table TL_P_SWITCHORDER 
	add constraint PRIMARYKEY_TL_P_SWITCHORDER primary key (log_id)
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

drop table TL_P_EXTENSIONLOG;
create table TL_P_EXTENSIONLOG (
	log_id         	VARCHAR2(13) not null,
	branch_id      	VARCHAR2(4) not null,
	check_id       	VARCHAR2(14) not null,
	add_day        	DATE not null,
	last_day       	DATE not null,
	rp_id          	VARCHAR2(3) not null,
	room_price     	NUMBER(9,2)	default	0,
	record_time    	DATE not null,
	record_user    	VARCHAR2(8) not null
)
tablespace PMS_DAT 
	pctfree 10 
	initrans 1 
	maxtrans 255 storage(
	initial 16K 
	minextents 1 
	maxextents unlimited
);
comment on table TL_P_EXTENSIONLOG is '续住日志表';
comment on column TL_P_EXTENSIONLOG.log_id is '日志号	格式:YYMMDD+门店编号+3位序列';
comment on column TL_P_EXTENSIONLOG.branch_id is '门店编号';
comment on column TL_P_EXTENSIONLOG.check_id is '房单号	取自房单表';
comment on column TL_P_EXTENSIONLOG.add_day is '新预离日期	记录完,更新房单表离店时间';
comment on column TL_P_EXTENSIONLOG.last_day is '原预离日期';
comment on column TL_P_EXTENSIONLOG.rp_id is '房价码	MSJ-门市价,PKJ-普卡价,YKJ-银卡价,JKJ-金卡价,BJJ-铂金价,HZJ-黑钻价';
comment on column TL_P_EXTENSIONLOG.room_price is '房价	房价,保留两位小数';
comment on column TL_P_EXTENSIONLOG.record_time is '操作时间';
comment on column TL_P_EXTENSIONLOG.record_user is '操作人';
alter table TL_P_EXTENSIONLOG 
	add constraint PRIMARYKEY_TL_P_EXTENSIONLOG primary key (log_id)
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

drop table TL_P_SWITCHROOM;
create table TL_P_SWITCHROOM (
	log_id         	VARCHAR2(13) not null,
	branch_id      	VARCHAR2(4) not null,
	check_id       	VARCHAR2(14) not null,
	rp_id          	VARCHAR2(3) not null,
	last_roomid    	VARCHAR2(6) not null,
	last_roomtype  	VARCHAR2(2) not null,
	curr_roomid    	VARCHAR2(6) not null,
	curr_roomtype  	VARCHAR2(2) not null,
	record_time    	DATE not null,
	record_user    	VARCHAR2(8) not null
)
tablespace PMS_DAT 
	pctfree 10 
	initrans 1 
	maxtrans 255 storage(
	initial 16K 
	minextents 1 
	maxextents unlimited
);
comment on table TL_P_SWITCHROOM is '换房日志表';
comment on column TL_P_SWITCHROOM.log_id is '日志号	格式:YYMMDD+门店编号+3位序列';
comment on column TL_P_SWITCHROOM.branch_id is '门店编号';
comment on column TL_P_SWITCHROOM.check_id is '房单号	民宿时为房屋编号';
comment on column TL_P_SWITCHROOM.rp_id is '房价码	民宿时为房屋编号';
comment on column TL_P_SWITCHROOM.last_roomid is '原房号';
comment on column TL_P_SWITCHROOM.last_roomtype is '原房间类型';
comment on column TL_P_SWITCHROOM.curr_roomid is '现房号';
comment on column TL_P_SWITCHROOM.curr_roomtype is '现房间类型';
comment on column TL_P_SWITCHROOM.record_time is '操作时间';
comment on column TL_P_SWITCHROOM.record_user is '操作人';
alter table TL_P_SWITCHROOM 
	add constraint PRIMARYKEY_TL_P_SWITCHROOM primary key (log_id)
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

drop table TB_P_BILL;
create table TB_P_BILL (
	bill_id        	VARCHAR2(15) not null,
	branch_id      	VARCHAR2(4) not null,
	check_id       	VARCHAR2(14) not null,
	project_id     	VARCHAR2(4) not null,
	project_name   	VARCHAR2(20) not null,
	describe       	VARCHAR2(40) not null,
	cost           	NUMBER(9,2),
	pay            	NUMBER(9,2),
	payment        	VARCHAR2(1) not null,
	offset         	VARCHAR2(200),
	status         	VARCHAR2(1) not null,
	record_time    	DATE	default	sysdate,
	record_user    	VARCHAR2(8) not null,
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
comment on table TB_P_BILL is '账单表';
comment on column TB_P_BILL.bill_id is '账单号	格式:YYMMDD+门店编号+5位序列';
comment on column TB_P_BILL.branch_id is '门店编号';
comment on column TB_P_BILL.check_id is '房单编号	取自tb_p_check';
comment on column TB_P_BILL.project_id is '项目编号';
comment on column TB_P_BILL.project_name is '项目名称	房费,现金等';
comment on column TB_P_BILL.describe is '说明	日结,续住押金,商品等';
comment on column TB_P_BILL.cost is '消费';
comment on column TB_P_BILL.pay is '结算';
comment on column TB_P_BILL.payment is '支付方式	1-现金,2-银行卡,3-预授权,4-支付宝,5-微信';
comment on column TB_P_BILL.offset is '冲减原因';
comment on column TB_P_BILL.status is '状态	1-正常,2-已冲减,3-已转账';
comment on column TB_P_BILL.record_time is '操作时间';
comment on column TB_P_BILL.record_user is '操作人';
comment on column TB_P_BILL.remark is '备注';
alter table TB_P_BILL 
	add constraint PRIMARYKEY_TB_P_BILL primary key (bill_id)
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

drop table TL_P_RECORDING;
create table TL_P_RECORDING (
	record_id      	VARCHAR2(19) not null,
	branch_id      	VARCHAR2(6) not null,
	check_id       	VARCHAR2(14) not null,
	record_type    	VARCHAR2(4) not null,
	project_id     	VARCHAR2(4) not null,
	fee            	NUMBER(9,2) not null,
	record_time    	DATE	default	sysdate,
	record_user    	VARCHAR2(8) not null,
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
comment on table TL_P_RECORDING is '入账日志表';
comment on column TL_P_RECORDING.record_id is '入账号	格式:YYMMDD+门店编号+5位序列';
comment on column TL_P_RECORDING.branch_id is '门店编号';
comment on column TL_P_RECORDING.check_id is '房单号';
comment on column TL_P_RECORDING.record_type is '入账类型	1-账单入账,2-工作账入账';
comment on column TL_P_RECORDING.project_id is '项目编号';
comment on column TL_P_RECORDING.fee is '金额';
comment on column TL_P_RECORDING.record_time is '操作时间';
comment on column TL_P_RECORDING.record_user is '操作人';
comment on column TL_P_RECORDING.remark is '备注';
alter table TL_P_RECORDING 
	add constraint PRIMARYKEY_TL_P_RECORDING primary key (record_id)
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

drop table TL_P_TRANSFER;
create table TL_P_TRANSFER (
	transfer_id    	VARCHAR2(15) not null,
	branch_id      	VARCHAR2(4) not null,
	last_checkid   	VARCHAR2(14) not null,
	curr_checkid   	VARCHAR2(14) not null,
	record_time    	DATE	default	sysdate,
	record_user    	VARCHAR2(8) not null,
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
comment on table TL_P_TRANSFER is '转账日志表';
comment on column TL_P_TRANSFER.transfer_id is '转账号	格式:YYMMDD+门店编号+5位序列';
comment on column TL_P_TRANSFER.branch_id is '门店编号';
comment on column TL_P_TRANSFER.last_checkid is '原房单号';
comment on column TL_P_TRANSFER.curr_checkid is '现房单号';
comment on column TL_P_TRANSFER.record_time is '操作时间';
comment on column TL_P_TRANSFER.record_user is '操作人';
comment on column TL_P_TRANSFER.remark is '备注	原bill置为2,新插入一条记录';
alter table TL_P_TRANSFER 
	add constraint PRIMARYKEY_TL_P_TRANSFER primary key (transfer_id)
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

drop table TL_P_INTEGRATION;
create table TL_P_INTEGRATION (
	log_id         	VARCHAR2(14) not null,
	branch_id      	VARCHAR2(4) not null,
	check_id       	VARCHAR2(14),
	source         	VARCHAR2(1),
	fee            	NUMBER(9,2) not null,
	integragion    	NUMBER(9) not null,
	rule           	VARCHAR2(10) not null,
	member_id      	VARCHAR2(8) not null,
	record_time    	DATE	default	sysdate
)
tablespace PMS_DAT 
	pctfree 10 
	initrans 1 
	maxtrans 255 storage(
	initial 16K 
	minextents 1 
	maxextents unlimited
);
comment on table TL_P_INTEGRATION is '积分日志表';
comment on column TL_P_INTEGRATION.log_id is '日志号	格式:YYMMDD+门店编号+4位序列';
comment on column TL_P_INTEGRATION.branch_id is '门店编号';
comment on column TL_P_INTEGRATION.check_id is '房单号';
comment on column TL_P_INTEGRATION.source is '来源	1-结算,2-订单';
comment on column TL_P_INTEGRATION.fee is '房费	只含自己预定,自己入住的房费';
comment on column TL_P_INTEGRATION.integragion is '产生积分';
comment on column TL_P_INTEGRATION.rule is '积分规则';
comment on column TL_P_INTEGRATION.member_id is '积分对象';
comment on column TL_P_INTEGRATION.record_time is '操作时间';
alter table TL_P_INTEGRATION 
	add constraint PRIMARYKEY_TL_P_INTEGRATION primary key (log_id)
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

drop table TB_P_TIPS;
create table TB_P_TIPS (
	log_id         	VARCHAR2(9) not null,
	check_id       	VARCHAR2(14) not null,
	content        	VARCHAR2(200) not null,
	record_user    	VARCHAR2(8) not null,
	record_time    	DATE	default	sysdate,
	reader         	VARCHAR2(8),
	read_time      	DATE	default	sysdate,
	status         	VARCHAR2(1) not null
)
tablespace PMS_DAT 
	pctfree 10 
	initrans 1 
	maxtrans 255 storage(
	initial 16K 
	minextents 1 
	maxextents unlimited
);
comment on table TB_P_TIPS is '提示表';
comment on column TB_P_TIPS.log_id is '日志号	格式:YYMMDD+3位序列';
comment on column TB_P_TIPS.check_id is '房单号';
comment on column TB_P_TIPS.content is '内容	详细内容';
comment on column TB_P_TIPS.record_user is '提交人';
comment on column TB_P_TIPS.record_time is '提交时间';
comment on column TB_P_TIPS.reader is '阅读人';
comment on column TB_P_TIPS.read_time is '阅读时间';
comment on column TB_P_TIPS.status is '状态	1-新增,2-取消,3-已读';
alter table TB_P_TIPS 
	add constraint PRIMARYKEY_TB_P_TIPS primary key (log_id)
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

drop table TB_P_WORKBILL;
create table TB_P_WORKBILL (
	workbill_id    	VARCHAR2(10) not null,
	branch_id      	VARCHAR2(4) not null,
	name           	VARCHAR2(100) not null,
	describe       	VARCHAR2(200),
	record_user    	VARCHAR2(8) not null,
	record_time    	DATE	default	sysdate,
	final_user     	VARCHAR2(8),
	final_time     	DATE	default	sysdate,
	status         	VARCHAR2(1) not null,
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
comment on table TB_P_WORKBILL is '工作账';
comment on column TB_P_WORKBILL.workbill_id is '单号	格式:YYMMDD+4位序列';
comment on column TB_P_WORKBILL.branch_id is '门店编号';
comment on column TB_P_WORKBILL.name is '工作账名称';
comment on column TB_P_WORKBILL.describe is '说明';
comment on column TB_P_WORKBILL.record_user is '创建人';
comment on column TB_P_WORKBILL.record_time is '创建时间';
comment on column TB_P_WORKBILL.final_user is '结账人';
comment on column TB_P_WORKBILL.final_time is '结账时间';
comment on column TB_P_WORKBILL.status is '状态	1-新增,2-已结';
comment on column TB_P_WORKBILL.remark is '备注';
alter table TB_P_WORKBILL 
	add constraint PRIMARYKEY_TB_P_WORKBILL primary key (workbill_id)
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

drop table TB_P_WORKBILLDETAIL;
create table TB_P_WORKBILLDETAIL (
	detail_id      	VARCHAR2(15) not null,
	branch_id      	VARCHAR2(4) not null,
	project_id     	VARCHAR2(4) not null,
	project_name   	VARCHAR2(20) not null,
	describe       	VARCHAR2(40) not null,
	cost           	NUMBER(9,2),
	pay            	NUMBER(9,2),
	payment        	VARCHAR2(1) not null,
	offset         	VARCHAR2(200),
	status         	VARCHAR2(1) not null,
	record_time    	DATE	default	sysdate,
	record_user    	VARCHAR2(8) not null,
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
comment on table TB_P_WORKBILLDETAIL is '工作账单';
comment on column TB_P_WORKBILLDETAIL.detail_id is '账单号	格式:YYMMDD+门店编号+5位序列';
comment on column TB_P_WORKBILLDETAIL.branch_id is '门店编号';
comment on column TB_P_WORKBILLDETAIL.project_id is '项目编号';
comment on column TB_P_WORKBILLDETAIL.project_name is '项目名称	房费,现金等';
comment on column TB_P_WORKBILLDETAIL.describe is '说明	日结,续住押金,商品等';
comment on column TB_P_WORKBILLDETAIL.cost is '消费';
comment on column TB_P_WORKBILLDETAIL.pay is '结算';
comment on column TB_P_WORKBILLDETAIL.payment is '支付方式	1-现金,2-银行卡,3-预授权,4-支付宝,5-微信';
comment on column TB_P_WORKBILLDETAIL.offset is '冲减原因';
comment on column TB_P_WORKBILLDETAIL.status is '状态	1-正常,2-已冲减';
comment on column TB_P_WORKBILLDETAIL.record_time is '操作时间';
comment on column TB_P_WORKBILLDETAIL.record_user is '操作人';
comment on column TB_P_WORKBILLDETAIL.remark is '备注';
alter table TB_P_WORKBILLDETAIL 
	add constraint PRIMARYKEY_TB_P_WORKBILLDETAIL primary key (detail_id)
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

drop table TB_P_SALELOG;
create table TB_P_SALELOG (
	log_id         	VARCHAR2(14) not null,
	branch_id      	VARCHAR2(4) not null,
	debts          	VARCHAR2(1) not null,
	check_id       	VARCHAR2(14),
	room_id        	VARCHAR2(6),
	category_id    	VARCHAR2(8) not null,
	goods_name     	VARCHAR2(30) not null,
	amount         	NUMBER(7) not null,
	price          	NUMBER(8,2) not null,
	pay_type       	VARCHAR2(1) not null,
	record_time    	DATE	default	sysdate,
	record_user    	VARCHAR2(8) not null,
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
comment on table TB_P_SALELOG is '售卖日志表';
comment on column TB_P_SALELOG.log_id is '日志号	格式:YYMMDD+门店编号+4位序列';
comment on column TB_P_SALELOG.branch_id is '门店编号';
comment on column TB_P_SALELOG.debts is '挂账类型	1.工作账,2.房账';
comment on column TB_P_SALELOG.check_id is '房单号';
comment on column TB_P_SALELOG.room_id is '房号	民宿时为房屋编号';
comment on column TB_P_SALELOG.category_id is '商品大类';
comment on column TB_P_SALELOG.goods_name is '商品名称';
comment on column TB_P_SALELOG.amount is '数量';
comment on column TB_P_SALELOG.price is '单价';
comment on column TB_P_SALELOG.pay_type is '付款方式	1.挂房账,2现金,3银行卡,4微信,5支付宝';
comment on column TB_P_SALELOG.record_time is '操作时间';
comment on column TB_P_SALELOG.record_user is '操作人';
comment on column TB_P_SALELOG.remark is '备注';
alter table TB_P_SALELOG 
	add constraint PRIMARYKEY_TB_P_SALELOG primary key (log_id)
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

drop table TB_P_ROOMSTATUS;
create table TB_P_ROOMSTATUS (
	log_id         	VARCHAR2(14) not null,
	branch_id      	VARCHAR2(4) not null,
	room_type      	VARCHAR2(3) not null,
	count          	NUMBER(5) not null,
	record_time    	DATE	default	sysdate,
	record_user    	VARCHAR2(8) not null,
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
comment on table TB_P_ROOMSTATUS is '房态表';
comment on column TB_P_ROOMSTATUS.log_id is '日志号	YYMMDD+门店号+4位序列';
comment on column TB_P_ROOMSTATUS.branch_id is '门店编号';
comment on column TB_P_ROOMSTATUS.room_type is '房间类型	客房类型-缩写编码';
comment on column TB_P_ROOMSTATUS.count is '空房数';
comment on column TB_P_ROOMSTATUS.record_time is '操作时间';
comment on column TB_P_ROOMSTATUS.record_user is '操作人';
comment on column TB_P_ROOMSTATUS.remark is '备注';
alter table TB_P_ROOMSTATUS 
	add constraint PRIMARYKEY_TB_P_ROOMSTATUS primary key (log_id)
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

drop table TB_P_ROOMPLAN;
create table TB_P_ROOMPLAN (
	log_id         	VARCHAR2(15) not null,
	branch_id      	VARCHAR2(4) not null,
	valid_day      	DATE not null,
	room_id        	VARCHAR2(6) not null,
	rp_id          	VARCHAR2(3) not null,
	room_price     	NUMBER(9,2) not null,
	cashback       	NUMBER(9,2),
	status         	VARCHAR2(2) not null,
	record_time    	DATE	default	sysdate,
	record_user    	VARCHAR2(8) not null,
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
comment on table TB_P_ROOMPLAN is '房租计划表';
comment on column TB_P_ROOMPLAN.log_id is '日志号	YYMMDD+门店号+5位序列';
comment on column TB_P_ROOMPLAN.branch_id is '门店编号';
comment on column TB_P_ROOMPLAN.valid_day is '生效日期';
comment on column TB_P_ROOMPLAN.room_id is '房号	民宿时为房屋编号';
comment on column TB_P_ROOMPLAN.rp_id is '房价码	MSJ-门市价,PKJ-普卡价,YKJ-银卡价,JKJ-金卡价,BJJ-铂金价,HZJ-黑钻价';
comment on column TB_P_ROOMPLAN.room_price is '房价	房价,保留两位小数';
comment on column TB_P_ROOMPLAN.cashback is '返现	返现,保留两位小数';
comment on column TB_P_ROOMPLAN.status is '状态	0-无效,1-有效';
comment on column TB_P_ROOMPLAN.record_time is '产生时间';
comment on column TB_P_ROOMPLAN.record_user is '操作人';
comment on column TB_P_ROOMPLAN.remark is '备注';
alter table TB_P_ROOMPLAN 
	add constraint PRIMARYKEY_TB_P_ROOMPLAN primary key (log_id)
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

drop table TB_C_MEMBER;
create table TB_C_MEMBER (
	member_id      	VARCHAR2(8) not null,
	member_name    	VARCHAR2(20) not null,
	login_name     	VARCHAR2(20) not null,
	password       	VARCHAR2(20) not null,
	member_rank    	VARCHAR2(1) not null,
	idcard         	VARCHAR2(18) not null,
	gendor         	VARCHAR2(1) not null,
	birthday       	DATE,
	mobile         	VARCHAR2(11) not null,
	email          	VARCHAR2(40),
	address        	VARCHAR2(60),
	postcode       	VARCHAR2(6),
	photos         	VARCHAR2(20),
	source         	VARCHAR2(1) not null,
	valid_time     	DATE not null,
	invalid_time   	DATE not null,
	register_time  	DATE not null,
	record_time    	DATE	default	sysdate,
	status         	VARCHAR2(1) not null,
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
comment on table TB_C_MEMBER is '会员资料表';
comment on column TB_C_MEMBER.member_id is '会员编号';
comment on column TB_C_MEMBER.member_name is '会员姓名';
comment on column TB_C_MEMBER.login_name is '登录名';
comment on column TB_C_MEMBER.password is '密码	MD5加密';
comment on column TB_C_MEMBER.member_rank is '会员等级	见会员等级表';
comment on column TB_C_MEMBER.idcard is '身份证';
comment on column TB_C_MEMBER.gendor is '性别';
comment on column TB_C_MEMBER.birthday is '生日';
comment on column TB_C_MEMBER.mobile is '手机号';
comment on column TB_C_MEMBER.email is '邮件';
comment on column TB_C_MEMBER.address is '地址';
comment on column TB_C_MEMBER.postcode is '邮编';
comment on column TB_C_MEMBER.photos is '照片	存放照片的文件名';
comment on column TB_C_MEMBER.source is '来源	1-官网渠道注册,2-微信公众号,3-IOS端,4-Android端,5-实体卡,6-商务卡';
comment on column TB_C_MEMBER.valid_time is '生效时间';
comment on column TB_C_MEMBER.invalid_time is '失效时间	普通及黑钻会员不会失效';
comment on column TB_C_MEMBER.register_time is '注册时间	仅注册时录入,';
comment on column TB_C_MEMBER.record_time is '录入时间';
comment on column TB_C_MEMBER.status is '状态	0-已删除,1-有效';
comment on column TB_C_MEMBER.remark is '备注';
alter table TB_C_MEMBER 
	add constraint PRIMARYKEY_TB_C_MEMBER primary key (member_id)
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

drop table TP_C_MEMBERRANK;
create table TP_C_MEMBERRANK (
	member_rank    	VARCHAR2(2) not null,
	rank_name      	VARCHAR2(20) not null,
	discount       	NUMBER(3) not null,
	record_time    	DATE	default	sysdate,
	status         	VARCHAR2(1) not null,
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
comment on table TP_C_MEMBERRANK is '会员等级表';
comment on column TP_C_MEMBERRANK.member_rank is '会员级别';
comment on column TP_C_MEMBERRANK.rank_name is '级别名';
comment on column TP_C_MEMBERRANK.discount is '折扣';
comment on column TP_C_MEMBERRANK.record_time is '录入时间';
comment on column TP_C_MEMBERRANK.status is '状态	0-已删除,1-有效';
comment on column TP_C_MEMBERRANK.remark is '备注';

drop table TB_C_MEMBERACCOUNT;
create table TB_C_MEMBERACCOUNT (
	account_id     	VARCHAR2(16) not null,
	member_id      	VARCHAR2(8)	default	0,
	curr_balance   	NUMBER(10,2)	default	0,
	curr_integration	NUMBER(10)	default	0,
	total_recharge 	NUMBER(10,2)	default	0,
	discount_gift  	NUMBER(10,2)	default	0,
	charge_gift    	NUMBER(10,2)	default	0,
	total_consume  	NUMBER(10,2)	default	0,
	total_integration	NUMBER(10)	default	0,
	ingegration_gift	NUMBER(10)	default	0,
	total_cons_integration	NUMBER(10)	default	0,
	total_roomnights	NUMBER(5)	default	0,
	curr_roomnights	NUMBER(5)	default	0,
	total_noshow   	NUMBER(4)	default	0,
	curr_noshow    	NUMBER(4)	default	0,
	record_time    	DATE	default	sysdate,
	status         	VARCHAR2(1) not null
)
tablespace PMS_DAT 
	pctfree 10 
	initrans 1 
	maxtrans 255 storage(
	initial 16K 
	minextents 1 
	maxextents unlimited
);
comment on table TB_C_MEMBERACCOUNT is '会员账户表';
comment on column TB_C_MEMBERACCOUNT.account_id is '账户编号';
comment on column TB_C_MEMBERACCOUNT.member_id is '会员编号';
comment on column TB_C_MEMBERACCOUNT.curr_balance is '当前余额';
comment on column TB_C_MEMBERACCOUNT.curr_integration is '当前积分';
comment on column TB_C_MEMBERACCOUNT.total_recharge is '充值金额';
comment on column TB_C_MEMBERACCOUNT.discount_gift is '折扣赠送';
comment on column TB_C_MEMBERACCOUNT.charge_gift is '充值赠送';
comment on column TB_C_MEMBERACCOUNT.total_consume is '消费金额';
comment on column TB_C_MEMBERACCOUNT.total_integration is '获取积分';
comment on column TB_C_MEMBERACCOUNT.ingegration_gift is '积分赠送';
comment on column TB_C_MEMBERACCOUNT.total_cons_integration is '消费积分';
comment on column TB_C_MEMBERACCOUNT.total_roomnights is '总房晚';
comment on column TB_C_MEMBERACCOUNT.curr_roomnights is '当前房晚';
comment on column TB_C_MEMBERACCOUNT.total_noshow is '总未现天数';
comment on column TB_C_MEMBERACCOUNT.curr_noshow is '当前未现数	sysdate';
comment on column TB_C_MEMBERACCOUNT.record_time is '录入时间';
comment on column TB_C_MEMBERACCOUNT.status is '状态	0-已删除,1-有效';
alter table TB_C_MEMBERACCOUNT 
	add constraint PRIMARYKEY_TB_C_MEMBERACCOUNT primary key (account_id)
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

drop table TL_C_MEMBERCONSUME;
create table TL_C_MEMBERCONSUME (
	consume_id     	VARCHAR2(16),
	member_id      	VARCHAR2(8),
	consume_type   	VARCHAR2(1),
	order_id       	VARCHAR2(14),
	consumption    	NUMBER(10,2),
	payment        	VARCHAR2(1),
	record_time    	DATE	default	sysdate,
	status         	VARCHAR2(1)
)
tablespace PMS_DAT 
	pctfree 10 
	initrans 1 
	maxtrans 255 storage(
	initial 16K 
	minextents 1 
	maxextents unlimited
);
comment on table TL_C_MEMBERCONSUME is '会员消费信息表';
comment on column TL_C_MEMBERCONSUME.consume_id is '消费单号	YYMMDD+8位序列';
comment on column TL_C_MEMBERCONSUME.member_id is '会员编号';
comment on column TL_C_MEMBERCONSUME.consume_type is '消费类型	1-订单,2-消费';
comment on column TL_C_MEMBERCONSUME.order_id is '订单编号';
comment on column TL_C_MEMBERCONSUME.consumption is '消费金额';
comment on column TL_C_MEMBERCONSUME.payment is '支付类型	见支付类型参数';
comment on column TL_C_MEMBERCONSUME.record_time is '录入时间';
comment on column TL_C_MEMBERCONSUME.status is '状态	0-已删除,1-有效';
alter table TL_C_MEMBERCONSUME 
	add constraint PRIMARYKEY_TL_C_MEMBERCONSUME primary key (consume_id)
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

drop table TB_C_STAFF;
create table TB_C_STAFF (
	staff_id       	VARCHAR2(8) not null,
	staff_name     	VARCHAR2(20) not null,
	login_name     	VARCHAR2(20) not null,
	password       	VARCHAR2(20) not null,
	branch_id      	VARCHAR2(4) not null,
	post           	VARCHAR2(4) not null,
	idcard         	VARCHAR2(18) not null,
	gendor         	VARCHAR2(1) not null,
	birthday       	DATE,
	mobile         	VARCHAR2(11) not null,
	email          	VARCHAR2(40),
	address        	VARCHAR2(60),
	entry_time     	DATE not null,
	record_user    	VARCHAR2(8) not null,
	record_time    	DATE	default	sysdate,
	status         	VARCHAR2(1) not null,
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
comment on table TB_C_STAFF is '员工资料表';
comment on column TB_C_STAFF.staff_id is '员工编号';
comment on column TB_C_STAFF.staff_name is '员工姓名';
comment on column TB_C_STAFF.login_name is '登录名';
comment on column TB_C_STAFF.password is '密码	MD5加密';
comment on column TB_C_STAFF.branch_id is '归属分店	民宿人员片区';
comment on column TB_C_STAFF.post is '职位';
comment on column TB_C_STAFF.idcard is '身份证';
comment on column TB_C_STAFF.gendor is '性别';
comment on column TB_C_STAFF.birthday is '生日';
comment on column TB_C_STAFF.mobile is '手机号';
comment on column TB_C_STAFF.email is '邮件';
comment on column TB_C_STAFF.address is '地址';
comment on column TB_C_STAFF.entry_time is '入职时间';
comment on column TB_C_STAFF.record_user is '录入人';
comment on column TB_C_STAFF.record_time is '录入时间';
comment on column TB_C_STAFF.status is '状态	0-已删除,1-有效';
comment on column TB_C_STAFF.remark is '备注';
alter table TB_C_STAFF 
	add constraint PRIMARYKEY_TB_C_STAFF primary key (staff_id)
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

drop table TB_C_BRANCH;
create table TB_C_BRANCH (
	branch_id      	VARCHAR2(4) not null,
	branch_name    	VARCHAR2(30) not null,
	branch_type    	VARCHAR2(1) not null,
	address        	VARCHAR2(60) not null,
	phone          	VARCHAR2(13) not null,
	postcode       	VARCHAR2(6) not null,
	contacts       	VARCHAR2(10) not null,
	mobile         	VARCHAR2(11) not null,
	record_user    	VARCHAR2(8) not null,
	record_time    	DATE	default	sysdate,
	status         	VARCHAR2(1) not null,
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
comment on table TB_C_BRANCH is '分店资料表';
comment on column TB_C_BRANCH.branch_id is '分店编号';
comment on column TB_C_BRANCH.branch_name is '分店名';
comment on column TB_C_BRANCH.branch_type is '分店类型	1-酒店,2-公寓';
comment on column TB_C_BRANCH.address is '详细地址';
comment on column TB_C_BRANCH.phone is '座机';
comment on column TB_C_BRANCH.postcode is '邮编';
comment on column TB_C_BRANCH.contacts is '联系人';
comment on column TB_C_BRANCH.mobile is '手机';
comment on column TB_C_BRANCH.record_user is '录入人';
comment on column TB_C_BRANCH.record_time is '录入时间';
comment on column TB_C_BRANCH.status is '状态	0-已删除,1-有效';
comment on column TB_C_BRANCH.remark is '备注';
alter table TB_C_BRANCH 
	add constraint PRIMARYKEY_TB_C_BRANCH primary key (branch_id)
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

drop table TP_C_POST;
create table TP_C_POST (
	post_id        	VARCHAR2(4) not null,
	post_name      	VARCHAR2(30) not null,
	describe       	VARCHAR2(200),
	record_user    	VARCHAR2(8) not null,
	record_time    	DATE	default	sysdate,
	status         	VARCHAR2(1) not null,
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
comment on table TP_C_POST is '职位信息表';
comment on column TP_C_POST.post_id is '职位编号';
comment on column TP_C_POST.post_name is '职位名';
comment on column TP_C_POST.describe is '职能描述';
comment on column TP_C_POST.record_user is '录入人';
comment on column TP_C_POST.record_time is '录入时间';
comment on column TP_C_POST.status is '状态	0-已删除,1-有效';
comment on column TP_C_POST.remark is '备注';
alter table TP_C_POST 
	add constraint PRIMARYKEY_TP_C_POST primary key (post_id)
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

drop table TP_C_ROLE;
create table TP_C_ROLE (
	role_id        	VARCHAR2(8) not null,
	role_name      	VARCHAR2(20) not null,
	record_user    	VARCHAR2(8) not null,
	record_time    	DATE	default	sysdate,
	status         	VARCHAR2(1) not null,
	remark         	VARCHAR2(200) not null
)
tablespace PMS_DAT 
	pctfree 10 
	initrans 1 
	maxtrans 255 storage(
	initial 16K 
	minextents 1 
	maxextents unlimited
);
comment on table TP_C_ROLE is '角色资料表';
comment on column TP_C_ROLE.role_id is '角色编号';
comment on column TP_C_ROLE.role_name is '角色名称';
comment on column TP_C_ROLE.record_user is '录入人';
comment on column TP_C_ROLE.record_time is '录入时间';
comment on column TP_C_ROLE.status is '状态	0-已删除,1-有效';
comment on column TP_C_ROLE.remark is '备注';
alter table TP_C_ROLE 
	add constraint PRIMARYKEY_TP_C_ROLE primary key (role_id)
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

drop table TP_C_USERROLE;
create table TP_C_USERROLE (
	data_id        	VARCHAR2(8) not null,
	user_id        	VARCHAR2(8) not null,
	role_id        	VARCHAR2(8) not null,
	record_user    	VARCHAR2(8) not null,
	record_time    	DATE	default	sysdate,
	status         	VARCHAR2(1) not null
)
tablespace PMS_DAT 
	pctfree 10 
	initrans 1 
	maxtrans 255 storage(
	initial 16K 
	minextents 1 
	maxextents unlimited
);
comment on table TP_C_USERROLE is '用户角色表';
comment on column TP_C_USERROLE.data_id is '数据编号';
comment on column TP_C_USERROLE.user_id is '用户编号';
comment on column TP_C_USERROLE.role_id is '角色编号';
comment on column TP_C_USERROLE.record_user is '录入人';
comment on column TP_C_USERROLE.record_time is '录入时间	0-已删除,1-有效';
comment on column TP_C_USERROLE.status is '状态';
alter table TP_C_USERROLE 
	add constraint PRIMARYKEY_TP_C_USERROLE primary key (data_id)
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

drop table TP_C_ROLERELATION;
create table TP_C_ROLERELATION (
	data_id        	VARCHAR2(8) not null,
	role_id        	VARCHAR2(8) not null,
	funcright_id   	VARCHAR2(10) not null,
	datarights     	VARCHAR2(100) not null,
	record_user    	VARCHAR2(8) not null,
	record_time    	DATE	default	sysdate,
	status         	VARCHAR2(1) not null
)
tablespace PMS_DAT 
	pctfree 10 
	initrans 1 
	maxtrans 255 storage(
	initial 16K 
	minextents 1 
	maxextents unlimited
);
comment on table TP_C_ROLERELATION is '用户权限关联表';
comment on column TP_C_ROLERELATION.data_id is '数据编号';
comment on column TP_C_ROLERELATION.role_id is '角色编号';
comment on column TP_C_ROLERELATION.funcright_id is '功能编号';
comment on column TP_C_ROLERELATION.datarights is '页面权限';
comment on column TP_C_ROLERELATION.record_user is '录入人';
comment on column TP_C_ROLERELATION.record_time is '录入时间	0-已删除,1-有效';
comment on column TP_C_ROLERELATION.status is '状态';
alter table TP_C_ROLERELATION 
	add constraint PRIMARYKEY_TP_C_ROLERELATION primary key (data_id)
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

drop table TP_C_SYSPARAM;
create table TP_C_SYSPARAM (
	param_id       	VARCHAR2(8) not null,
	param_type     	VARCHAR2(20) not null,
	param_name     	VARCHAR2(30) not null,
	param_desc     	VARCHAR2(100),
	content        	VARCHAR2(50) not null,
	order_no       	NUMBER,
	status         	VARCHAR2(1) not null
)
tablespace PMS_DAT 
	pctfree 10 
	initrans 1 
	maxtrans 255 storage(
	initial 16K 
	minextents 1 
	maxextents unlimited
);
comment on table TP_C_SYSPARAM is '系统参数表';
comment on column TP_C_SYSPARAM.param_id is '参数编号';
comment on column TP_C_SYSPARAM.param_type is '参数类型';
comment on column TP_C_SYSPARAM.param_name is '参数名称';
comment on column TP_C_SYSPARAM.param_desc is '参数描述';
comment on column TP_C_SYSPARAM.content is '参数内容';
comment on column TP_C_SYSPARAM.order_no is '序号';
comment on column TP_C_SYSPARAM.status is '状态	0-已删除,1-有效';
alter table TP_C_SYSPARAM 
	add constraint PRIMARYKEY_TP_C_SYSPARAM primary key (param_id)
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

drop table TB_C_GOODS;
create table TB_C_GOODS (
	goods_id       	VARCHAR2(8) not null,
	goods_name     	VARCHAR2(30) not null,
	category_id    	VARCHAR2(8) not null,
	branch_id      	VARCHAR2(6) not null,
	supplier_id    	VARCHAR2(8) not null,
	price          	NUMBER(10,2) not null,
	integral       	NUMBER(6),
	specifications 	VARCHAR2(4) not null,
	unit           	VARCHAR2(4) not null,
	production_date	DATE,
	sale_type      	VARCHAR2(1) not null,
	record_user    	VARCHAR2(8) not null,
	record_time    	DATE	default	sysdate,
	status         	VARCHAR2(1) not null,
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
comment on table TB_C_GOODS is '商品资料表';
comment on column TB_C_GOODS.goods_id is '商品编号';
comment on column TB_C_GOODS.goods_name is '商品名';
comment on column TB_C_GOODS.category_id is '商品类型	通用产品用*表示';
comment on column TB_C_GOODS.branch_id is '门店编号';
comment on column TB_C_GOODS.supplier_id is '供应商';
comment on column TB_C_GOODS.price is '售价';
comment on column TB_C_GOODS.integral is '兑换积分';
comment on column TB_C_GOODS.specifications is '规格';
comment on column TB_C_GOODS.unit is '单位';
comment on column TB_C_GOODS.production_date is '生产日期';
comment on column TB_C_GOODS.sale_type is '售卖类型	1-店内销售,2-网上商城,3-积分商城';
comment on column TB_C_GOODS.record_user is '录入人';
comment on column TB_C_GOODS.record_time is '录入时间';
comment on column TB_C_GOODS.status is '状态	0-已删除,1-有效';
comment on column TB_C_GOODS.remark is '备注';
alter table TB_C_GOODS 
	add constraint PRIMARYKEY_TB_C_GOODS primary key (goods_id)
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

drop table TP_C_GOODSCATEGORY;
create table TP_C_GOODSCATEGORY
(
  category_id    VARCHAR2(8) not null,
  branch_id      VARCHAR2(6) not null,
  charge_room    VARCHAR2(8),
  super_category VARCHAR2(8) not null,
  category_name  VARCHAR2(10) not null,
  record_user    VARCHAR2(8) not null,
  record_time    DATE default sysdate,
  status         VARCHAR2(1) not null,
  remark         VARCHAR2(200)
)
tablespace PMS_DAT
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 16K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table TP_C_GOODSCATEGORY is '商品类型表';
-- Add comments to the columns 
comment on column TP_C_GOODSCATEGORY.category_id is '商品类型';
comment on column TP_C_GOODSCATEGORY.branch_id is '门店编号';
comment on column TP_C_GOODSCATEGORY.charge_room is '是否挂账';
comment on column TP_C_GOODSCATEGORY.super_category is '父级种类';
comment on column TP_C_GOODSCATEGORY.category_name is '类别名';
comment on column TP_C_GOODSCATEGORY.record_user is '录入人';
comment on column TP_C_GOODSCATEGORY.record_time is '录入时间';
comment on column TP_C_GOODSCATEGORY.status is '状态	0-已删除,1-有效';
comment on column TP_C_GOODSCATEGORY.remark is '备注';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TP_C_GOODSCATEGORY
  add constraint PRIMARYKEY_TP_C_GOODSCATEGORY primary key (CATEGORY_ID)
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

drop table TB_C_PURCHASE;
create table TB_C_PURCHASE (
	purchase_id    	VARCHAR2(12) not null,
	purchase_category	VARCHAR2(2) not null,
	branch_id         VARCHAR2(6) not null,
	purchase_amount	NUMBER(10,2) not null,
	auditor        	NUMBER(8) not null,
	record_user    	VARCHAR2(8) not null,
	record_time    	DATE	default	sysdate,
	status         	VARCHAR2(1) not null,
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

comment on table TB_C_PURCHASE is '采购申请表';
comment on column TB_C_PURCHASE.purchase_id is '采购编号	YYMMDD+门店号+2位序列';
comment on column TB_C_PURCHASE.purchase_category is '采购类型';
comment on column TB_C_PURCHASE.branch_id is '分店编号';
comment on column TB_C_PURCHASE.purchase_amount is '采购金额';
comment on column TB_C_PURCHASE.auditor is '审核人';
comment on column TB_C_PURCHASE.record_user is '录入人';
comment on column TB_C_PURCHASE.record_time is '录入时间';
comment on column TB_C_PURCHASE.status is '状态	0-已删除,1-待审核,2-审核通过,3-修改,4-作废';
comment on column TB_C_PURCHASE.remark is '备注';
alter table TB_C_PURCHASE 
	add constraint PRIMARYKEY_TB_C_PURCHASE primary key (purchase_id)
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

drop table TB_C_PURCHASEDETAIL;
create table TB_C_PURCHASEDETAIL (
	purchase_id    	VARCHAR2(12) not null,
	data_id        	VARCHAR2(2) not null,
	goods_id       	VARCHAR2(8) not null,
	purchase_price 	NUMBER(10,2) not null,
	amount         	NUMBER(4) not null,
	record_user    	VARCHAR2(8) not null,
	record_time    	DATE	default	sysdate,
	status         	VARCHAR2(1) not null,
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
comment on table TB_C_PURCHASEDETAIL is '采购明细表';
comment on column TB_C_PURCHASEDETAIL.purchase_id is '采购编号';
comment on column TB_C_PURCHASEDETAIL.data_id is '数据编号';
comment on column TB_C_PURCHASEDETAIL.goods_id is '商品编号';
comment on column TB_C_PURCHASEDETAIL.purchase_price is '采购价格';
comment on column TB_C_PURCHASEDETAIL.amount is '采购数量';
comment on column TB_C_PURCHASEDETAIL.record_user is '录入人';
comment on column TB_C_PURCHASEDETAIL.record_time is '录入时间';
comment on column TB_C_PURCHASEDETAIL.status is '状态	0-已删除,1-有效';
comment on column TB_C_PURCHASEDETAIL.remark is '备注';
alter table TB_C_PURCHASEDETAIL 
	add constraint PRIMARYKEY_TB_C_PURCHASEDETAIL primary key (purchase_id,data_id)
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

drop table TL_C_AUDITLOG;
create table TL_C_AUDITLOG (
	log_id         	VARCHAR2(18) not null,
	oper_type      	VARCHAR2(8) not null,
	oper_id        	VARCHAR2(14) not null,
	record_user    	VARCHAR2(8) not null,
	record_time    	DATE	default	sysdate,
	status         	VARCHAR2(1) not null,
	remark         	VARCHAR2(100)
)
tablespace PMS_DAT 
	pctfree 10 
	initrans 1 
	maxtrans 255 storage(
	initial 16K 
	minextents 1 
	maxextents unlimited
);
comment on table TL_C_AUDITLOG is '审核日志表';
comment on column TL_C_AUDITLOG.log_id is '日志编号	YYMMDD+门店号+4位序列';
comment on column TL_C_AUDITLOG.oper_type is '操作类型	1-采购';
comment on column TL_C_AUDITLOG.oper_id is '操作编号';
comment on column TL_C_AUDITLOG.record_user is '操作人';
comment on column TL_C_AUDITLOG.record_time is '操作时间';
comment on column TL_C_AUDITLOG.status is '审核状态';
comment on column TL_C_AUDITLOG.remark is '备注';
alter table TL_C_AUDITLOG 
	add constraint PRIMARYKEY_TL_C_AUDITLOG primary key (log_id)
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

drop table TB_C_STOCK;
create table TB_C_STOCK (
	stock_id       	VARCHAR2(14) not null,
	branch_id      	VARCHAR2(4) not null,
	goods_id       	VARCHAR2(8) not null,
	stock_amount   	NUMBER(8) not null,
	record_time    	DATE	default	sysdate,
	status         	VARCHAR2(1) not null
)
tablespace PMS_DAT 
	pctfree 10 
	initrans 1 
	maxtrans 255 storage(
	initial 16K 
	minextents 1 
	maxextents unlimited
);
comment on table TB_C_STOCK is '库存资料表';
comment on column TB_C_STOCK.stock_id is '库存编号	YYMMDD+门店号+4位序列';
comment on column TB_C_STOCK.branch_id is '归属门店';
comment on column TB_C_STOCK.goods_id is '商品编号';
comment on column TB_C_STOCK.stock_amount is '库存数量';
comment on column TB_C_STOCK.record_time is '录入时间';
comment on column TB_C_STOCK.status is '状态	0-已删除,1-有效';
alter table TB_C_STOCK 
	add constraint PRIMARYKEY_TB_C_STOCK primary key (stock_id)
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

drop table TL_C_STORAGELOG;
create table TL_C_STORAGELOG (
	log_id         	VARCHAR2(8) not null,
	branch_id      	VARCHAR2(4) not null,
	goods_id       	VARCHAR2(8) not null,
	amount         	NUMBER(8) not null,
	price          	NUMBER(10,2) not null,
	purchase_price 	NUMBER(10,2) not null,
	stock_price    	NUMBER(10,2) not null,
	record_user    	VARCHAR2(8) not null,
	record_time    	DATE	default	sysdate,
	status         	VARCHAR2(1) not null,
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
comment on table TL_C_STORAGELOG is '库存流水表';
comment on column TL_C_STORAGELOG.log_id is '入库编号	YYMMDD+门店号+4位序列';
comment on column TL_C_STORAGELOG.branch_id is '归属门店';
comment on column TL_C_STORAGELOG.goods_id is '商品编号';
comment on column TL_C_STORAGELOG.amount is '数量';
comment on column TL_C_STORAGELOG.price is '原价格';
comment on column TL_C_STORAGELOG.purchase_price is '采购价格';
comment on column TL_C_STORAGELOG.stock_price is '入库价格';
comment on column TL_C_STORAGELOG.record_user is '录入人';
comment on column TL_C_STORAGELOG.record_time is '录入时间';
comment on column TL_C_STORAGELOG.status is '状态	0-已删除,1-有效';
comment on column TL_C_STORAGELOG.remark is '备注';
alter table TL_C_STORAGELOG 
	add constraint PRIMARYKEY_TL_C_STORAGELOG primary key (log_id)
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

drop table TB_C_SUPPLIER;
create table TB_C_SUPPLIER (
	supplier_id    	VARCHAR2(8) not null,
	supplier_name  	VARCHAR2(30) not null,
	address        	VARCHAR2(60) not null,
	phone          	VARCHAR2(13) not null,
	postcode       	VARCHAR2(6) not null,
	contacts       	VARCHAR2(10) not null,
	mobile         	VARCHAR2(11),
	record_user    	VARCHAR2(8) not null,
	record_time    	DATE	default	sysdate,
	status         	VARCHAR2(1) not null,
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
comment on table TB_C_SUPPLIER is '供应商资料表';
comment on column TB_C_SUPPLIER.supplier_id is '供应商编号';
comment on column TB_C_SUPPLIER.supplier_name is '供应商名';
comment on column TB_C_SUPPLIER.address is '详细地址';
comment on column TB_C_SUPPLIER.phone is '座机';
comment on column TB_C_SUPPLIER.postcode is '邮编';
comment on column TB_C_SUPPLIER.contacts is '联系人';
comment on column TB_C_SUPPLIER.mobile is '手机';
comment on column TB_C_SUPPLIER.record_user is '录入人';
comment on column TB_C_SUPPLIER.record_time is '录入时间';
comment on column TB_C_SUPPLIER.status is '状态	0-已删除,1-有效';
comment on column TB_C_SUPPLIER.remark is '备注';
alter table TB_C_SUPPLIER 
	add constraint PRIMARYKEY_TB_C_SUPPLIER primary key (supplier_id)
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

drop table TB_E_EQUIPMENT;
create table TB_E_EQUIPMENT (
	equip_id       	VARCHAR2(10) not null,
	equip_name     	VARCHAR2(30) not null,
	equip_category 	VARCHAR2(4) not null,
	branch_id      	VARCHAR2(4),
	room_id        	VARCHAR2(4) not null,
	serial_no      	VARCHAR2(20),
	ip             	VARCHAR2(15),
	record_user    	VARCHAR2(8) not null,
	record_time    	DATE	default	sysdate,
	status         	VARCHAR2(1) not null,
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
comment on table TB_E_EQUIPMENT is '设备登记表';
comment on column TB_E_EQUIPMENT.equip_id is '设备编号	门店号+6位序列';
comment on column TB_E_EQUIPMENT.equip_name is '设备名';
comment on column TB_E_EQUIPMENT.equip_category is '设备类别';
comment on column TB_E_EQUIPMENT.branch_id is '归属门店';
comment on column TB_E_EQUIPMENT.room_id is '所属房间';
comment on column TB_E_EQUIPMENT.serial_no is '序列号';
comment on column TB_E_EQUIPMENT.ip is '设备IP';
comment on column TB_E_EQUIPMENT.record_user is '录入人';
comment on column TB_E_EQUIPMENT.record_time is '录入时间';
comment on column TB_E_EQUIPMENT.status is '状态	0-已删除,1-有效';
comment on column TB_E_EQUIPMENT.remark is '备注';
alter table TB_E_EQUIPMENT 
	add constraint PRIMARYKEY_TB_E_EQUIPMENT primary key (equip_id)
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

drop table TP_E_EQUIPCATEGORY;
create table TP_E_EQUIPCATEGORY (
	category_id    	VARCHAR2(4) not null,
	category_name  	VARCHAR2(30) not null,
	record_user    	VARCHAR2(8) not null,
	record_time    	DATE	default	sysdate,
	status         	VARCHAR2(1) not null,
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
comment on table TP_E_EQUIPCATEGORY is '设备类型表';
comment on column TP_E_EQUIPCATEGORY.category_id is '设备类别';
comment on column TP_E_EQUIPCATEGORY.category_name is '类别名';
comment on column TP_E_EQUIPCATEGORY.record_user is '录入人';
comment on column TP_E_EQUIPCATEGORY.record_time is '录入时间';
comment on column TP_E_EQUIPCATEGORY.status is '状态	0-已删除,1-有效';
comment on column TP_E_EQUIPCATEGORY.remark is '备注';
alter table TP_E_EQUIPCATEGORY 
	add constraint PRIMARYKEY_TP_E_EQUIPCATEGORY primary key (category_id)
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

drop table TL_E_CONTROLLOG;
create table TL_E_CONTROLLOG (
	log_id         	VARCHAR2(20) not null,
	equip_id       	VARCHAR2(10) not null,
	oper_command   	VARCHAR2(2) not null,
	record_user    	VARCHAR2(8) not null,
	record_time    	DATE	default	sysdate,
	status         	VARCHAR2(1) not null,
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
comment on table TL_E_CONTROLLOG is '控制日志表';
comment on column TL_E_CONTROLLOG.log_id is '日志编号	YYMMDD+8位序列';
comment on column TL_E_CONTROLLOG.equip_id is '设备编号';
comment on column TL_E_CONTROLLOG.oper_command is '操作指令';
comment on column TL_E_CONTROLLOG.record_user is '录入人';
comment on column TL_E_CONTROLLOG.record_time is '录入时间';
comment on column TL_E_CONTROLLOG.status is '状态	0-已删除,1-有效';
comment on column TL_E_CONTROLLOG.remark is '备注';
alter table TL_E_CONTROLLOG 
	add constraint PRIMARYKEY_TL_E_CONTROLLOG primary key (log_id)
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

drop table TL_E_WARNINGLOG;
create table TL_E_WARNINGLOG (
	log_id         	VARCHAR2(20) not null,
	equip_id       	VARCHAR2(10) not null,
	warning_type   	VARCHAR2(2) not null,
	warning_value  	NUMBER(6) not null,
	record_user    	VARCHAR2(8) not null,
	record_time    	DATE	default	sysdate,
	status         	VARCHAR2(1) not null,
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
comment on table TL_E_WARNINGLOG is '预警日志表';
comment on column TL_E_WARNINGLOG.log_id is '日志编号	YYMMDD+8位序列';
comment on column TL_E_WARNINGLOG.equip_id is '设备编号';
comment on column TL_E_WARNINGLOG.warning_type is '预警类型';
comment on column TL_E_WARNINGLOG.warning_value is '预警值';
comment on column TL_E_WARNINGLOG.record_user is '录入人';
comment on column TL_E_WARNINGLOG.record_time is '录入时间';
comment on column TL_E_WARNINGLOG.status is '状态	0-已删除,1-有效';
comment on column TL_E_WARNINGLOG.remark is '备注';
alter table TL_E_WARNINGLOG 
	add constraint PRIMARYKEY_TL_E_WARNINGLOG primary key (log_id)
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

drop table TL_E_MAINTENANCELOG;
create table TL_E_MAINTENANCELOG (
	log_id         	VARCHAR2(20) not null,
	branch_id      	VARCHAR2(8) not null,
	equip_id       	VARCHAR2(10) not null,
	problem_tag    	VARCHAR2(1) not null,
	describe       	VARCHAR2(200),
	record_user    	VARCHAR2(8) not null,
	record_time    	DATE	default	sysdate,
	status         	VARCHAR2(1) not null,
	remark         	VARCHAR2(200)
)
tablespace PMS_DAT F
	pctfree 10 
	initrans 1 
	maxtrans 255 storage(
	initial 16K 
	minextents 1 
	maxextents unlimited
);
comment on table TL_E_MAINTENANCELOG is '维修日志表';
comment on column TL_E_MAINTENANCELOG.log_id is '日志编号	YYMMDD+6位序列';
comment on column TL_E_MAINTENANCELOG.branch_id is '门店编号';
comment on column TL_E_MAINTENANCELOG.equip_id is '设备编号';
comment on column TL_E_MAINTENANCELOG.problem_tag is '问题标签	见系统参数表定义';
comment on column TL_E_MAINTENANCELOG.describe is '问题描述';
comment on column TL_E_MAINTENANCELOG.record_user is '录入人';
comment on column TL_E_MAINTENANCELOG.record_time is '录入时间';
comment on column TL_E_MAINTENANCELOG.status is '状态	0-已删除,1-已报修,2-已修复';
comment on column TL_E_MAINTENANCELOG.remark is '备注';
alter table TL_E_MAINTENANCELOG 
	add constraint PRIMARYKEY_TL_E_MAINTENANCELOG primary key (log_id)
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
	
drop table TP_C_SQLINFO;	
create table TP_C_SQLINFO
(
  data_id  NUMBER(10) not null,
  sql_name VARCHAR2(20) not null,
  sql_info VARCHAR2(2000) not null,
  db_type  VARCHAR2(20) not null,
  remark   VARCHAR2(400)
)
tablespace PMS_DAT
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 16K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table TP_C_SQLINFO
  is 'SQL参数表';
-- Add comments to the columns 
comment on column TP_C_SQLINFO.data_id
  is '参数编号';
comment on column TP_C_SQLINFO.sql_name
  is 'SQL名称';
comment on column TP_C_SQLINFO.sql_info
  is 'SQL';
comment on column TP_C_SQLINFO.db_type
  is '数据库类型';
comment on column TP_C_SQLINFO.remark
  is '备注';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TP_C_SQLINFO
  add constraint PRIMARYKEY_TP_C_SQLINFO primary key (DATA_ID)
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
  
drop table TB_P_HALTPLAN;	 
create table TB_P_HALTPLAN
(
  log_id          VARCHAR2(12) not null,
  branch_id       VARCHAR2(4) not null,
  room_id         VARCHAR2(6) not null,
  halt_type       VARCHAR2(1) not null,
  halt_reason     VARCHAR2(1) not null,
  start_time      DATE not null,
  end_time        DATE not null,
  status          VARCHAR2(2) not null,
  record_time     DATE default sysdate,
  record_user     VARCHAR2(8) not null,
  remark          VARCHAR2(200)
)
tablespace PMS_DAT
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 16K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table TB_P_HALTPLAN
  is '停售房计划表';
-- Add comments to the columns 
comment on column TB_P_HALTPLAN.log_id 
  is '日志号 YYMMDD+门店号+2位序列';
comment on column TB_P_HALTPLAN.branch_id 
  is '门店编号 民宿时为房屋编号';
comment on column TB_P_HALTPLAN.room_id 
  is '房号';
comment on column TB_P_HALTPLAN.halt_type 
  is '停售类型 1-停售 2-维修';
comment on column TB_P_HALTPLAN.halt_reason 
  is '停售原因  1-马桶损坏 2-天花漏水 3-地板变形 4-设施损坏 5-其他原因';
comment on column TB_P_HALTPLAN.start_time 
  is '开始日期';
comment on column TB_P_HALTPLAN.end_time 
  is '结束日期 ';
comment on column TB_P_HALTPLAN.status 
  is '状态  0-取消计划,1-有效计划,2-完成计划';
comment on column TB_P_HALTPLAN.record_time 
  is '创建时间';
comment on column TB_P_HALTPLAN.record_user 
  is '创建人';
comment on column TB_P_HALTPLAN.remark 
  is '备注';

-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_P_HALTPLAN
  add constraint PRIMARYKEY_TB_P_HALTPLAN primary key (LOG_ID)
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
  
drop table TB_P_PETTYCASH;
create table TB_P_PETTYCASH
(
  log_id            VARCHAR2(12) not null,
  branch_id         VARCHAR2(4) not null,
  shift             VARCHAR2(1) not null,
  cash_box          VARCHAR2(4) not null,
  hand_user         VARCHAR2(8) not null,
  confirm_user      VARCHAR2(8) not null,
  cash_in           NUMBER(9,2)  default 0,
  cash_out          NUMBER(9,2)  default 0,
  payment_value     NUMBER(9,2)  default 0,
  shift_value       NUMBER(9,2)  default 0,
  last_shiftvalue   NUMBER(9,2),
  curr_shiftvalue   NUMBER(9,2),
  card_balance      NUMBER(9,2) default 0,
  cards             VARCHAR2(2) default 0,
  deposit_no        VARCHAR2(28),
  invoice_no        VARCHAR2(28),
  status            VARCHAR2(2) ,
  record_time       DATE default sysdate,
  record_user       VARCHAR2(8) not null,
  remark            VARCHAR2(200)
)
tablespace PMS_DAT
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 16K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table TB_P_PETTYCASH
  is '备用金表';
-- Add comments to the columns 
comment on column TB_P_PETTYCASH.log_id 
  is '日志号 YYMMDD+门店号+2位序列';
comment on column TB_P_PETTYCASH.branch_id 
  is '门店编号';
comment on column TB_P_PETTYCASH.shift 
  is '班次 参数化';
comment on column TB_P_PETTYCASH.cash_box 
  is '备用金柜 参数化';
comment on column TB_P_PETTYCASH.hand_user 
  is '交接人';
comment on column TB_P_PETTYCASH.confirm_user 
  is '确认人';
comment on column TB_P_PETTYCASH.cash_in 
  is '现金收入 ';
comment on column TB_P_PETTYCASH.cash_out 
  is '现金支出';
comment on column TB_P_PETTYCASH.payment_value 
  is '投缴金额';
comment on column TB_P_PETTYCASH.shift_value 
  is '备用金交接金额';
comment on column TB_P_PETTYCASH.last_shiftvalue 
  is '补上班次备用金额';
comment on column TB_P_PETTYCASH.curr_shiftvalue 
  is '本班需补备用金额';
comment on column TB_P_PETTYCASH.card_balance 
  is '银行卡余额';
comment on column TB_P_PETTYCASH.cards 
  is '银行卡张数';
comment on column TB_P_PETTYCASH.deposit_no 
  is '押金收据号';
comment on column TB_P_PETTYCASH.invoice_no 
  is '发票号';
comment on column TB_P_PETTYCASH.status 
  is '状态 0-无效,1-有效';
comment on column TB_P_PETTYCASH.record_time 
  is '操作时间';
comment on column TB_P_PETTYCASH.record_user 
  is '操作人';
comment on column TB_P_PETTYCASH.remark 
  is '备注';  
  
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_P_PETTYCASH
  add constraint PRIMARYKEY_TB_P_PETTYCASH primary key (LOG_ID)
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
  
drop table TB_P_SHIFT; 
create table TB_P_SHIFT
(
  log_id          VARCHAR2(12) not null,
  branch_id       VARCHAR2(4) not null,
  last_shift      VARCHAR2(1) not null,
  curr_shift      VARCHAR2(1) not null,
  last_user       VARCHAR2(8) not null,
  curr_user       VARCHAR2(8) not null,
  record_time     DATE default sysdate,
  record_user     VARCHAR2(8) not null,
  remark          VARCHAR2(200)
)
tablespace PMS_DAT
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 16K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table TB_P_SHIFT
  is '交接班表';
-- Add comments to the columns 
comment on column TB_P_SHIFT.log_id 
  is '日志号 YYMMDD+门店号+2位序列';
comment on column TB_P_SHIFT.branch_id 
  is '门店编号';
comment on column TB_P_SHIFT.last_shift 
  is '上个班次';
comment on column TB_P_SHIFT.curr_shift 
  is '当前班次';
comment on column TB_P_SHIFT.last_user 
  is '交接人 上个班次人员';
comment on column TB_P_SHIFT.curr_user 
  is '当班人';
comment on column TB_P_SHIFT.record_time 
  is '操作时间';
comment on column TB_P_SHIFT.record_user 
  is '操作人';
comment on column TB_P_SHIFT.remark 
  is '备注';  
  
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_P_SHIFT
  add constraint PRIMARYKEY_TB_P_SHIFT primary key (LOG_ID)
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
  
drop table TB_C_SMSTEMPLATE;
create table TB_C_SMSTEMPLATE (
	template_id    	VARCHAR2(8) not null,
	template_name  	VARCHAR2(2) not null,
	category       	VARCHAR2(1) not null,
	template_content	VARCHAR2(400) not null,
	record_user    	VARCHAR2(8) not null,
	record_time    	DATE	default	sysdate,
	status         	VARCHAR2(1) not null,
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
comment on table TB_C_SMSTEMPLATE is '短信模板表';
comment on column TB_C_SMSTEMPLATE.template_id is '短信模板编号';
comment on column TB_C_SMSTEMPLATE.template_name is '模板名称	业务类型：1-门锁密码 2-库存提示 3-水电预警 4-审核结果通知 5-设备维修通知（酒店，维修人员） 6-营销信息推送';
comment on column TB_C_SMSTEMPLATE.category is '模板类型	1-开锁密码 2-短信通知';
comment on column TB_C_SMSTEMPLATE.template_content is '模板内容';
comment on column TB_C_SMSTEMPLATE.record_user is '录入人';
comment on column TB_C_SMSTEMPLATE.record_time is '创建时间';
comment on column TB_C_SMSTEMPLATE.status is '状态	0-无效 1-有效';
comment on column TB_C_SMSTEMPLATE.remark is '备注';
alter table TB_C_SMSTEMPLATE 
	add constraint PRIMARYKEY_TB_C_SMSTEMPLATE primary key (template_id)
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

drop table TL_C_SMSSENDLOG;
create table TL_C_SMSSENDLOG (
	data_id        	VARCHAR2(8) not null,
	sms_content    	VARCHAR2(400) not null,
	sms_recipentno 	VARCHAR2(11) not null,
	error_code     	VARCHAR2(4),
	record_user    	VARCHAR2(8) not null,
	record_time    	DATE	default	sysdate,
	status         	VARCHAR2(1) not null,
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
comment on table TL_C_SMSSENDLOG is '短信流水表';
comment on column TL_C_SMSSENDLOG.data_id is '数据编号';
comment on column TL_C_SMSSENDLOG.sms_content is '短信内容';
comment on column TL_C_SMSSENDLOG.sms_recipentno is '接收人号码';
comment on column TL_C_SMSSENDLOG.error_code is '错误码';
comment on column TL_C_SMSSENDLOG.record_user is '录入人';
comment on column TL_C_SMSSENDLOG.record_time is '创建时间';
comment on column TL_C_SMSSENDLOG.status is '状态	0-无效 1-待发 2-已发 3-失败';
comment on column TL_C_SMSSENDLOG.remark is '备注';
alter table TL_C_SMSSENDLOG 
	add constraint PRIMARYKEY_TL_C_SMSSENDLOG primary key (data_id)
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

drop table TB_C_DEPARTMENT;
create table TB_C_DEPARTMENT (
	branch_id      	VARCHAR2(4) not null,
	depart_id      	VARCHAR2(4) not null,
	depart_name    	VARCHAR2(30) not null,
	depart_level   	VARCHAR2(1) not null,
	super_depart   	VARCHAR2(4),
	record_user    	VARCHAR2(8) not null,
	record_time    	DATE	default	sysdate,
	status         	VARCHAR2(1) not null,
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
comment on table TB_C_DEPARTMENT is '员工部门表';
comment on column TB_C_DEPARTMENT.branch_id is '分店编号';
comment on column TB_C_DEPARTMENT.depart_id is '部门编号';
comment on column TB_C_DEPARTMENT.depart_name is '部门名称';
comment on column TB_C_DEPARTMENT.depart_level is '部门级别';
comment on column TB_C_DEPARTMENT.super_depart is '上级部门';
comment on column TB_C_DEPARTMENT.record_user is '录入人';
comment on column TB_C_DEPARTMENT.record_time is '录入时间';
comment on column TB_C_DEPARTMENT.status is '状态	0-已删除 1-有效';
comment on column TB_C_DEPARTMENT.remark is '备注';
alter table TB_C_DEPARTMENT 
	add constraint PRIMARYKEY_TB_C_DEPARTMENT primary key (branch_id,depart_id)
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

 


