package com.ideassoft.core.constants;

public class CommonConstants {
	
//	public static String IPMS = "https://www.mohohotel.com";

	public static String IPMSPRONAME = "ipms";
	
//	public static String IPMS = "http://zhangxy.tunnel.echomod.cn";

    public static String IPMS = "https://www.ideassoft.com";

	public interface SystemFunctions {
		public final static String EDIT = "编辑";
		
		public final static String UPDATE = "修改";
		
		public final static String SELECT = "全选";
		
		public final static String ADD = "新增";

		public final static String DELETE = "删除";

		public final static String SAVE = "保存";

		public final static String PRINT = "打印";

		public final static String IMPORT = "导入";
		
		public final static String IMPORTDOOR = "门锁导入";
		
		public final static String EXCEL = "导出";

		public final static String CAMPAIGHSADD = "活动新增";

		public final static String FRESH = "刷新";

		public final static String RPAPPLY = "房租申请";

		public final static String RPSAVE = "保存";

		public final static String TEMPLATEADD = "模板新增";
		
		public final static String RPAPPLYPRICE = "房价调整";
		
		public final static String RPAPPLYPRICEHOUR = "时租调整";
		
		public final static String EQUIPSYNCHRO = "数据同步";
		
		public final static String DOOREQUIPSYNCHRO = "门锁同步";
		
		public final static String ADDDOORRIGHT = "增减授权";
		
		public final static String COPYHOUSE = "复制房源";
	}

	public interface SubSystemNames {
		
		public final static String UPMS = "基础资料管理系统";

		public final static String EMS = "设备管理系统";
		
		public final static String CRM = "客户关系管理系统";

		public final static String IPMS = "门店管理系统";

	}
	

	public interface SubSystemName {

		public final static String UPMS = "UPMS";

		public final static String EMS = "EMS";
		
		public final static String CRM = "CRM";

		public final static String IPMS = "PMS";

	}
	
	public interface SubBranchType {
		public final static String HOTEL = "1";

		public final static String APART = "2";

		public final static String UPMS = "3";

	}

	public interface SystemParams {
		public final static String PAY = "PAY";

		public final static String REMOTE_PATH = "REMOTE_PATH";

		public final static String TRANSPORT_OBJECT = "TRANSPORTOBJECT";
		
		public final static String TRANSPORT_NAME = "数据传输对象";
		
		public final static String CHECK_POINT = "CHECKPOINT";

	}
	
	public interface SecondsUpload {
		public final static String PAY = "SECONDSPAY";

		public final static String REMOTE_PATH = "SECONDSREMOTE_PATH";

		public final static String TRANSPORT_OBJECT = "STRANSPORTOBJECT";

		public final static String CHECK_POINT = "SECONDSCHECKPOINT";

	}

	public interface AJAXRESULT {
		public final static Integer FALSE = 0;

		public final static Integer SUCESS = 1;

	}

	public interface STATUS {
		public final static String WASTED = "0";

		public final static String NORMAL = "1";

	}
	
	public interface AJAXACTION {
		public final static Integer FALSE = 0;

		public final static Integer SUCESS = 1;
		
		public final static Integer OTHER = 2;

	}

	public interface PortalResultCode {
		final public static Integer NULL = -1;

		final public static Integer FAILED = -400;

		final public static Integer DONE = 1;

		final public static Integer EXIST = 3;
	}
	
	public interface LoginSource {
		
		final public static String APP = "1";
		
		final public static String WEB = "2";
		
		final public static String BRANCH = "3";
		
		final public static String WAP = "4";
		
		final public static String WECHAT = "7";
		
	}
	
	public interface SystemTheme {
		public final static String HOTEL = "1";
		
		public final static String APARTMENTS = "2";
		
		public final static String HOMESTAY = "3";
	}
	
	public interface CampaignsType {

		final public static String CampaignOne = "1"; //推荐有礼
		
		final public static String CampaignTwo = "2"; //开卡送抵用劵
		
		final public static String CampaignThree = "3"; // 充值满送
		
		final public static String CampaignFour = "4"; //首住优惠价
		
		final public static String CampaignFifth = "5"; //折上折
		
		final public static String CampaignSix = "6"; //限时特价
		
		final public static String CampaignSeven= "7"; //积分换优惠劵
		
		final public static String CampaignEight = "8"; //尾房（今夜特价）
		
		final public static String CampaignNine = "9"; //连住X日优惠
		
		final public static String CampaignTen = "10"; //提前X日优惠
		
		final public static String CampaignEleven= "11"; //住X送X
		
		final public static String CampaignTwelve = "12"; //免费住
		
		final public static String CampaignThirteen = "13"; //住即返X元
		
		final public static String CampaignFourteen = "14"; //积分兑换
		
	}
	
	public interface Postgrade {
		public final static String STORMANAGE = "店长";
		public final static String Manager = "0007";
		public final static String SuperManagerPost = "1010";
		public final static String CLEANSTAFF = "0006";
	}
	public interface PicStyle {
		public final static String BRANCH_INNEL_PIC = "FP"; //内景
		public final static String BRANCH_HEAD_PIC = "HP"; //头图
		public final static String BRANCH_COMMENT_PIC = "CP";//评论图片
		public final static String BRANCH_ADVERTISE_PIC = "AP";//广告图片
		public final static String BRANCH_BEDROOM_PIC = "WS";//卧室图片
		public final static String BRANCH_LIVEROOM_PIC = "KT";//客厅图片
		public final static String BRANCH_RECLINING_PIC = "CW";//次卧图片
		public final static String BRANCH_KITCHEN_PIC = "CF";//厨房图片
		public final static String BRANCH_TOILET_PIC = "WJ";//卫生间图片
	}
	public interface RoomPicStyle {
		public final static String ROOMTYPE_INNEL_PIC = "qt";
		public final static String ROOMTYPE_HEAD_PIC = "tt";
	}
	public interface BranchPicStyle {
		public final static String BRANCH_INNEL_PIC = "dt";
		public final static String BRANCH_HEAD_PIC = "tt";
	}
	
	public interface Branch {
		public final static String HOTEL = "酒店";
		public final static String APARTMENT = "公寓";
		public final static String HOUSE = "民宿";
		
		public final static String HOTELID = "1";
		public final static String APARTMENTID = "2";
		public final static String HOUSEID = "3";
	}
	public interface BranchRank {
		public final static String TOP ="0" ;
		public final static String SUB ="1" ;
		
	}
	
	public interface CityRank {
		
		public final static String CITY = "1";
		public final static String DISTRICT = "2";
		public final static String STREET = "3";
		public final static String CIRCLE = "4";
		public final static String SCHOOL = "5";
		public final static String SCENIC = "6";
		public final static String SUBWAYLINE = "7";
		public final static String SUBWAYSTATION = "8";

	}
	
	public interface RoomStatus {

		final public static String RoomNull = "1";
		
		final public static String RoomOrder = "2";
		
		final public static String RoomChecked = "3";
		
		final public static String RoomStop = "T";
		
		final public static String RoomDirty = "Z";
		
		final public static String RoomRepair = "W";
		
		final public static String RoomOther = "O";
		
		final public static String RoomUnuse = "0";
		
		final public static String RoomExample = "M";
		
		final public static String RoomStaff = "E";
		
	}
	
	public interface HouseStatus {

		final public static String HouseNull = "1";
		
		final public static String HouseOrder = "2";
		
		final public static String HouseChecked = "3";
		
		final public static String HouseStop = "T";
		
		final public static String HouseDirty = "Z";
		
		final public static String HouseRepair = "W";
		
		final public static String HouseOther = "R";
		
		final public static String HouseUnuse = "0";
		
	}
	
	public interface RoomPlanStatus {
		
		public final static String Valid = "1";
		
		public final static String InValid = "0";
		
	}
	
	public interface HaltPlanStatus {
		
		public final static String CANCEL = "0";
		
		public final static String VALID = "1";
		
		public final static String DONE = "2";
		
		public final static String EXCUTING ="3";
		
	}
	
	public interface CheckStatus {
		
		final public static String CheckOn = "1";
		
		final public static String CheckOff = "2";
		
		final public static String CheckOffAndUnout = "3";
		
		final public static String CheckLeave = "4";
		
	}
	
	public interface CheckUserStatus {
		public final static String ON = "1";
		
		public final static String CANCEL = "0";
		
	}
	
	public interface CheckUserType {
		public final static String HOST = "1";
		
		public final static String GUEST = "2";
		
	}
	
	public interface CheckinType {
		public final static String ORDER = "1";
		
		public final static String CHECK = "2";
		
	}
	
	public interface OrderStatus {
		final public static String Cancel = "0";
		final public static String NewOrder = "1";
		final public static String Absent = "2";
		final public static String CheckOn = "3";
		final public static String CheckOff= "4";
		final public static String Delete = "5";
		final public static String CheckOffAndUnout = "6";
	}
	
	public interface ApartmentStatus {
		final public static String Cancel = "0";
		final public static String Valid = "1";
		final public static String CheckOff = "2";
		final public static String CheckOffAndUnout = "3";
		final public static String CheckOn= "4";
	}
	
	public interface BillStatus {
		final public static String NonPayment = "0";
		final public static String BillNormal = "1";
		final public static String BillCONSUME = "2";
		final public static String BillTranfered = "3";
		final public static String BillCheckout = "4";
		final public static String BillSplitAccount = "5";
	}
	
	public interface WorkBillStatus {
		final public static String NEW = "1";
		
		final public static String DONE = "2";
	}
	
	public interface BillProject {
		
		public final static String CashOutlay = "2001";
		public final static String Cash = "2002";
		public final static String BankCard = "2003";
		public final static String Deposit = "2004";
		public final static String AccountTransfer = "2005";
		public final static String Alipay = "2006";
		public final static String WeChat = "2007";
		public final static String ForeGift = "2008"; 
		public final static String Rent = "2011";
		public final static String DIFF_PRICE = "2018";
		public final static String EX_HOUSE_PRICE = "2019";
		public final static String InsiderTrading = "2501";
		public final static String CLEANPRICE_PAY = "2222";
		
		public final static String Compensation = "1001";
		public final static String Insurance = "1002";
		public final static String CallFee = "1003";
		public final static String Others = "1004";
		public final static String AdjustroomPrice = "1005";
		public final static String Canteen = "1006";
		public final static String Commission = "1007";
		public final static String RentalIncome = "1008";
		public final static String MerchandiseSale = "1009";
		public final static String RoomPrice = "1234";
		public final static String CheckRoomprice = "1235";
		public final static String Fine = "1010";
		public final static String Service = "1011";
		public final static String CLEANPRICE = "2009";
		public final static String CLEANPRICE_COST = "1111";
		
	}
	
	public interface BillPayment {
		
		public final static String NonPayment = "0";
		public final static String Cash = "1";
		public final static String Bankcard = "2";
		public final static String AUTH = "3";
		public final static String Alipay = "4";
		public final static String WeChat = "5";
		public final static String CashOutlay = "6";
		
	}
	
	public interface BillrecordingType{
		
		public final static String billType = "1";
		
		public final static String workbillType = "2";
		
	}
	
	public interface DefaultRoomPrice {
		final public static String DEFAULT_RP_ID = "MSJ";
		
		final public static String DEFAULT_RP_NAME = "门市价";
		
		final public static String DEFAULT_RP_TYPE = "1";
		
		final public static double DEFAULT_RP_DISCOUNT = 1.00;
		
		final public static String DEFAULT_RP_RANK = "1";
		
		final public static String DEFAULT_RPKIND_COMMON = "1";
		
		final public static String DEFAULT_RPKIND_HOURS = "2";
		
		final public static String DEFAULT_RPKIND_MONTH = "3";
		
		final public static String DEFAULT_RP_STATE = "5";
		
	}
	
	public interface TipStatus {
		
		public final static String Add = "1";
		public final static String Cancel = "0";
		public final static String Readed = "2";
	}
	
	public interface TipType {
		
		public final static String OrderTip = "1";
		public final static String RoomTip = "2";
		
	}
	
	public interface TakeEffect {

		final public static String Immediately = "N";
		
		final public static String NotImmediately = "W";
	}
	
	public interface RpApplyStatus {

		final public static String RpUnuse = "0";
		
		final public static String RpAudit = "1";
		
		final public static String RpUse = "2";
		
		final public static String RpActive = "3";
	}
	
	public interface RpBranchSwitch {

		final public static String RbsOpen = "0";
		
		final public static String RbsClose = "1";
		
	}
	
	public interface MembenRank {

		final public static String CM = "0";
		
		final public static String NM = "1";
		
		final public static String PM = "2";
		
		final public static String YM = "3";
		
		final public static String JM = "4";
		
		final public static String BM = "5";
		
		final public static String HM = "6";
		
	}
	
	public interface MessageTextDefinition {

		final public static String CheckOut = "退房";
		
		final public static String Repair = "维修";
		
	}
	
	public interface OrderSource {

		final public static String App = "1";
		
		final public static String Web = "2";
		
		final public static String Branch = "3";
		
		final public static String Wap = "4";
		
		final public static String ChannelPartner = "5";
		
		final public static String Other = "6";
		
		final public static String WeChat = "7";
		
		final public static String CheckImmediately = "8";
		
	}
	
	public interface SystemType {

		final public static String Cloud = "1";
		
		final public static String Branch = "2";
				
	}
	
	public interface SystemLevel {

		final public static String MarketCenter = "0";
		
		final public static String SubBranch = "1";
		
		final public static String CouldMarketCenter = "1";
		
		final public static String SubSystem = "2";
		
				
	}
	
	public interface SysparamStatus {
		public final static String VALID = "1";
		
		public final static String INVALID = "0";
	}
	
	public interface Domain {
		final public static String DOMAINNAME = IPMS + "/" + IPMSPRONAME +"/upload/";
	}
	
    public interface WebSatus {
		
		final public static String inline = "在线";
		
		final public static String outline = "离线";
		
	}


	public interface InitialData {
		
		final public static String SubAdminPost = "0009";
		
		final public static String SubAdminStatus1 = "2";
		
		final public static String SubAdminStatus2 = "3";

	}
   
	
    public interface RoomPriceStatus {
		
		final public static String basic = "1";
		
		final public static String adjust = "2";
		
	}
    
    public interface RoomPriceState {
		
		final public static String unuse = "0";
		
		final public static String auditpending = "1";
		
		final public static String audited = "2";
		
		final public static String turndown = "3";
		
		final public static String unactive = "4";
		
		final public static String active = "5";
		
	}
    
	 public interface RoomRpkind {
			
			final public static String hotelprice = "1";
			
			final public static String hotelhourprice = "2";
			
			final public static String departmentprice = "3";
			
		}
 
	 public interface MemberSource {
		 	
		 	public static final String OFFICAIL_WEB = "1";
		 	public static final String WX = "2";
		 	public static final String IOS = "3";
		 	public static final String ANDROID = "4";
		 	public static final String ENTITY_CARD = "5";
		 	public static final String BUSINESS_CARD = "6";
	 	}
 
	 public interface Guarantee {
	 
			public static final String SECURED  = "2";
			
		 	public static final String UNSECURED = "1";
	 }
	 
	 public interface StaffStatus {
		 
			public final static String VALID = "1";
			
			public final static String INVALID = "0";
			
			public final static String ONESELF= "2";
			
			public final static String JOIN = "3";
	 }
	 
	 public interface GoodsId {
		 public final static String GOODSID_CLEAN = "10000001";
		 
	 }
	 
	public interface WXPAY {
		public final static String APPID = "wxf2031827fccda6ed";
		public final static String BODY = "门票支付";
		public final static String MACH_ID = "1494217052";
		public final static String NOTIFY_URL =	"http://www.weixin.qq.com/wxpay/pay.php";
		public final static String SPBIL_CREATE_IP = "123.12.12.123";
		public final static String TRADE_TYPE = "JSAPI";
		public final static String KEY = "shiyishilingxueshe20171212101000";
	}
 
	 public interface CommitType {
		 public final static String SYSTEMCOMMIT = "1";
		 public final static String COUSTOMERCOMMIT = "2";
	 }
	 
	 public interface ProbrandStatus {
		 public final static String WAITAUDITED = "1";
		 public final static String ALREADYAUDITED = "2";
		 public final static String DELETER = "3";
	 }
	 
	 /**
	  * 1-日租 2-时租 3-长租
	  * 
	  * @author zhengsj
	  * @date 2018年5月15日
	  * @version 1.0
	  */
	 public interface VolatilityPriceType {
		 public final static String DAILYRENT = "1";
		 public final static String TIMERENT = "2";
		 public final static String LONGRENT = "3";
	 }
	 
	 /**
	  * 租赁详情:1天、1个月、3个月
	  * 
	  * @author zhengsj
	  * @date 2018年5月15日
	  * @version 1.0
	  */
	 public interface VolatilityPriceTypeDetail {
		 public final static String ONEDAY = "1";
		 public final static String ONEMONTH = "1";
		 public final static String THREEMONTH = "3";
	 }
	 
	 /**
	  * 优先级 1-活动价 2-调整价 3-基准价
	  * 
	  * @author zhengsj
	  * @date 2018年5月15日
	  * @version 1.0
	  */
	 public interface VolatilityPricePriority {
		 public final static String ACTIVITYPRICE = "1";
		 public final static String ADJUSTPRICE = "2";
		 public final static String BEASEPRICE = "3";
	 }
	 
	 /**
	  * 价格规则:0为无规则
	  * 
	  * @author zhengsj
	  * @date 2018年5月15日
	  * @version 1.0
	  */
	 public interface VolatilityPriceRulesId {
		 public final static String PriceRulesId = "0";
		 
	 }
	 /**
	  * 是否自动退组,0-是,1-否
	  * @author ideas_mengl
	  * @date 2018年5月28日
	  */
	 public interface AutoRefundStatus {
		 
		 public static final String AUTOREFUND_TRUE = "0";
		 public static final String AUTOREFUND_FALSE = "1";

	 }
	 
	 public interface Path {
		final public static String RECIVEDATA = IPMS + "/cdds/reciveData.do";
		final public static String RECIVEFILE = IPMS + "/cdds/reciveFile.do";
		final public static String WSDLURL = IPMS + "/cdds/ws/call?wsdl";
	}
	 
	 public interface MarketCenterIpValue{
		final public static String centerIpValue = IPMS + "/" + IPMSPRONAME;
	}
	
	public interface MyPath {
		final public static String RECIVEDATA = IPMS + "/cdds/reciveData.do";
	}
	
	public interface Alipay {
		public static final String APP_ID = "2017101009231889";
		
		public static final String METHOD = "alipay.trade.app.pay";

		public static final String FORMAT = "JSON";
		
		public static final String CHARSET = "utf-8";

		public static final String SIGN_TYPE = "RSA2";

		public static final String SIGN = "utf-8";
		
		public static final String GBK = "GBK";

		public static final String VERSION = "1.0";

		public static final String NOTIFY_URL = IPMS + "/cdds/alipayServlet";
		
		public static final String ALIPAYREFUND_URL = "https://openapi.alipay.com/gateway.do";
			
		public static final String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDee3MsLByD0/698w5ZvAvdkp21b3MaP4HbvhbXgJW8QE2buLuy7h1sfbvWbQeTJ0Yh4QaEBPfiaXQnXgdCX1Y0ChqEMuQg8/pjK6wMUsp06MMKW1Of2moq7doRr3oymUy+ZpipI3tuJyYIXTVP/1uxKj768UbzdwCs63Mc153JDXYaQ3Hr9wc4yFj8JJcVA5CVVnzBfvkH2z7ZUj5kS9upha5uhvd4223gez4Ey2jHLvIt+sco6UdVLywfX2/7L5fGf5OzZxFUKgNVcSHCQZF8PE0qX0gsQiGw6zFTQcBeUnvAo9CweKRIBxDncJMOGcZhGEj5O3mYY/9w3AKTF9mDAgMBAAECggEAOlbCt6WFgKf5V5PBgpRFxD8q7aWg2BQpGBb7ZF0KZoeyEhBZ36yCuK0rsSl74AFfaYjZLYsj+RHP7HbCjovzTcgMdffmTCHN2W1dOwoGnqteYXvbLMKYLlOaaCLMdzItpou+ojN0rUjEt1LBpQ1/3LAIPcNA6Jye57kBwWFktZA95TVVY98ejz+xs3uuFWceBRPvP0Y9C2Qbf63xCZu4lEAi1q2qfgyoLCHDIdYQ+FQffbbZR8n108nwavhW2hjoMk7HANV4udXQXmgAu7V6rBKnjxDOMhokvF0+6Wk7gjVyl7Aptt+u0nRQn7Kz6dCIiHwKOZ+xp8EdZfMRn5zzuQKBgQDziGwbiMJy5ZxkCSpW1yLdQePUAzgB5gFLxm9rCGKNzIgsEz0zPdmBB4DLe4drKFl+ghPsybdCiFCNR4GnbIlThBoiOMTSbKicxGyx8gHeTe6CY1dECygd26S4bq3fsIo3L+mq+ru2QdF8RTPWNe3TX8kSD+qfIi/b8M2E2qZ1dwKBgQDp3ybVEOxajMnVOzxed0WTIZqquK8Hb0wPWdUf9vHtMuszJ05WV3kQ11c0jpSsl2JRXeXEAJzRqeF2cdemDMiVk8rTXwIZBHTmNo8X4enFH4lPWPQ+imqULLghSlmO53pYmXIC7PB2SE3Q/rngN2ZTA5/xsP4ZFLF2YnMrYE4vVQKBgEGmXhZSGLEmirFA80PzvPdOYt9terSwT1shBDojSYYWRAM1sILWwoKRBjxUcZ4Gg5Gh+UMU4a0y2Bwt0ZrTnHWCIBVnnk9ncVDJds/FgJFwCsLlisnSBB+AbXg2hfLOQYQ9C03HR8ELHzDT6vRtF06q/KY82i/J5ILy+EQ/OS5jAoGBANUVvQvkn3ThUgQ2NIrdoUatF371RoQ/o81YzBaTqVm1SxcQeQ+yI1x017/LKSsXwzzfkLlE1Z0ya3q/vvxMKXYfcm2j4wVoShp3G2lnLNeuJC1nnyJcez8Gf73VQEQ0JAUVygtgVrduqXgCntjroQ+B/fj4iovPjuGOUeDznkkBAoGBALOPPej8M2TOii+PfdM0739YPgvIFJMyrE/1rLw1yl43Xlfs6Ke6Yg6bLey+MuLLvEuKcfIGHQ6r5YA7Jle4pcYHb48y+qDxB4BQzlw7gD4i/dLACHQRxXYQWjS3hDcG8yquSbK0P2q7ILN/tXYhy1/gup1s2ZLutlSz3t4C3yEs";

		public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtfS9t2rQVBvyeBBTcqIYHXVm4oRPNYOTrOJJVQM+tTV7KdUn0swWLNBv0CWPDqwX0bQ/SPIEdVWQO04WhO4A3TEC7gCm/vYVT06ReGjnBJGzemG9c9FqK6RZAdpXjnbTjlzZG9wIl6TPFE8K3hFaAhR3+WyuRhwtl2LipOpd/22ESd/hX0HOqBQxpDQfpTYoos2ZzX6Gg9epr+xARmke/a4e+DPjMJEmjFMmay6eEJCJyy87cz8WzMmGVirt1i5BnU/eaBUL4qmhWQuOZdhQdSMOjDdG/VyVNC4mVuBplMbwgITzEwUEFoMJo2kWzEkUs7YnfLevjocrwyZeWg/tUwIDAQAB";

		public static final String ALIPAY_PUBLIC_KEY_BUSINESS = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA3ntzLCwcg9P+vfMOWbwL3ZKdtW9zGj+B274W14CVvEBNm7i7su4dbH271m0HkydGIeEGhAT34ml0J14HQl9WNAoahDLkIPP6YyusDFLKdOjDCltTn9pqKu3aEa96MplMvmaYqSN7bicmCF01T/9bsSo++vFG83cArOtzHNedyQ12GkNx6/cHOMhY/CSXFQOQlVZ8wX75B9s+2VI+ZEvbqYWubob3eNtt4Hs+BMtoxy7yLfrHKOlHVS8sH19v+y+Xxn+Ts2cRVCoDVXEhwkGRfDxNKl9ILEIhsOsxU0HAXlJ7wKPQsHikSAcQ53CTDhnGYRhI+Tt5mGP/cNwCkxfZgwIDAQAB";
	}
	
	public interface ZyDoorIp {
		
		public static final String IP = "lock.qixutech.com";
		//public static final String IP = "112.25.233.122";
		//public static final String PORT = "20172";
		public static final String PORT = "2016";	
		}
	
	public interface doorInterfaceResult{
		
		public final static String SUCESS = "0";//成功
		
		public final static String FAILED = "1";//失败
	}
	
	public interface doorReturnToJs{
		
		public final static String SUCESS = "1";//成功
		
		public final static String FAILED = "-1";//失败
	}
	
	public interface serviceKind{
		
		public final static String DOOR = "5";//门锁
		
		public final static String ELECTRIC= "3";//电表
		
		public final static String WATER = "4";//水表
		
		public final static String ROUTE = "1";//路由器
		
		public final static String COLLECTOR= "2";//采集器
		
	}
	
	public interface doorInterfaceParam{
		
		public final static String OPERATORID = "jsdr";//接口使用者
		
		public final static String OWNERID= "15851890826";//接口使用者手机号码
		
	}
	
	public interface servieLogStatus{
		public final static String EFFECTIVE = "1";//有效
		
		public final static String INVALID = "0";//无效
		
	}
	public interface tips{
		public final static String PARAM_TYPE = "TIPS";
		public final static String PARAM_NAME = "客房标签";
		public final static String STATUS_VALID = "1";
		public final static String STATUS_INVALID = "0";
	}
	
	public interface warningLogStatus{
		public final static String POWER_LOWER = "1";//电量过低
		
		public final static String GATEWAY_LOST = "2";//网关失联
		
		public final static String SYSTEM_LOST = "3";//门锁平台失联
		
		public final static String DEL_ALL_CARD_FAILED = "4";//删除锁所有身份证失败
		
		public final static String DEL_CARD_FAILED = "5";//删除锁某个身份证失败
		
		public final static String ADD_CARD_FAILED = "6";//添加锁某个身份证失败
		
		public final static String ADD_CARDTOMUTILDOOR_FAILED = "7";//添加锁某个身份证到多个锁里失败
		
		public final static String MUTIL_DOOR_PWD_FAILED = "8";//多个门锁获得动态密码失败
		
	}} 
