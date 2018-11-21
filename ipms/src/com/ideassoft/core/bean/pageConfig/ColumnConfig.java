
package com.ideassoft.core.bean.pageConfig;

import java.io.Serializable;

import javax.persistence.Transient;



/**
 * 报表列配置类
 * @author ZenShin
 * @time 2014-04-16
 */
public class ColumnConfig implements Serializable {
	
	@Transient
	private static final long serialVersionUID = -1653069440586861330L;

	/***列字段,取javaBean字段,大写***/
	private String columnId;

	/***列名,用于title展示***/
	private String name;
	
	/***初始值***/
	private String initValue;

	/***列宽***/
	private String width;

	/***内容对齐***/
	private String align;

	/***列是否隐藏***/
	private boolean hidden;

	/***列是否可编辑***/
	private boolean editable;
	
	/***列是否编辑时隐藏,但数据仍做提交***/
	private boolean edithidden;

	/***列是否可以排序***/
	private boolean sortable;

	/***列是否可以导出***/
	private boolean downloadable;

	/***列是否锁定***/
	private boolean frozen;

	/***列编辑类型 现有checkbox/select/textarea/button/dialog/dialog-radio/dialog-multi/date/datetime/link/funcButton/image/password/file/number
	分别为 勾选框/下拉框/字符区/常规按钮/弹出框/多选弹出框/日期/时间/链接/非常规功能按钮/图片/密码/文件上传/数字***/
	private String editType;

	/***编辑类型为select时,dataFormat里配置的sql键值***/
	private String value;

	/***编辑类型为select时,简化的数据类型,key1:value1;key2:value2;key3:value3***/
	private String editValue;

	/***校验规则  NOTNULL/NUMBER/EMAIL/DATE/TIME/MAX/MIN/IP/LENGTH/IDCARD/MOBILE/UNIQUE
		分别对应 非空/数字/邮件/日期/时间/最大/最小/IP/长度/身份证/手机/唯一性
		其中MAX/MIN/LENGTH均用:分割阈值,多个校验条件用','分隔
		注:UNIQUE为同步校验规则,若有其他校验规则,UNIQUE放在其他规则之后***/
	private String validateRule;

	/***是否主键***/
	private boolean primaryKey;

	/***是否复合主键***/
	private String complexKey;

	/***主键为序列时的序列名称***/
	private String sequence;

	/***序列前缀,可为空***/
	private String seqPrefix;

	/***指定主键生成规则***/
	private String assigned;

	/***数据取值事件 现用于编辑类型为select时的联动,类型有AUTOFILL/MULTI两种,举例 AUTOFILL:列1,列2 ; MULTI:列1***/
	private String dataEvent;

	/***联动时的取值,配置同value***/
	private String eventRule;

	/***编辑类型为button/funcButton时调用的js方法名***/
	private String callFunc;

	/***编辑类型为button时,按钮的文字***/
	private String funcName;

	/***编辑类型为button时,按钮的样式***/
	private String buttonCls;

	/***编辑类型为dialog时,自定义查询的对象、javaBean,可定义多个对象或javaBean,以','分隔***/
	private String dialogTarget;

	/***编辑类型为dialog时,自定义查询展示的列,以','分隔,配置多个对象或javaBean时,以'|'分隔***/
	private String dialogColumns;

	/***编辑类型为dialog时,查询的sql,配置同value***/
	private String dialogQuery;

	/***编辑类型为dialog时,dialog数据的展示类型 checkbox/list***/
	private String dialogType;

	/***编辑类型为dialog时,dialog页须有的查询条件***/
	private String dialogConditions;

	/***编辑类型为dialog时,自定义dialog页***/
	private String dialogCustom;
	
	/***编辑类型为dialog时,用于快速新增数据***/
	private String dialogQuickAdd;

	/***列阈值,可设置固定值,或者查询SQL,格式LT/GT:值/SQL,默认LT***/
	private String threshold;

	/***列阈值查询参数***/
	private String thresholdParam;

	/***自定义响应事件***/
	private String customCallback;

	/***自定义模糊查询***/
	private String fuzzyQuery;

	/***子页面ID***/
	private String subWinId;

	/***editType为file时,是否展示文件图片***/
	private boolean showImage;

	/***editType为file时,是否使用原始文件名***/
	private boolean useDefaultFileName;
	
	private boolean readonly;
	
	/***用于在本地端，要取云端序列的xml配置,cloud表示取云端的，null表示取本地的***/
	private String sequenceSource;
	
	/***初始化select 数据***/
	private String initialSelectData;
	
	/***editing页面 某列加按钮的function方法名***/
	private String cellButtonFunc;
	
	public ColumnConfig() {
		
	}

	public ColumnConfig(String columnId, String name, String initValue, String width, String align, boolean hidden, 
			boolean editable, boolean edithidden, boolean sortable, String editType, String value, 
			String editValue, String validateRule, boolean primaryKey, String sequence, String seqPrefix,
			String complexKey, String assigned, String dataEvent, String eventRule, String buttonCls, 
			String callFunc, String funcName, String dialogTarget, String dialogColumns, String dialogQuery, 
			String dialogType, String dialogConditions, String dialogCustom, String dialogQuickAdd, String threshold, 
			String thresholdParam, String customCallback, String fuzzyQuery, String subWinId, 
			boolean showImage, boolean frozen, boolean downloadable, boolean useDefaultFileName, boolean readonly,
			String sequenceSource, String initialSelectData,String cellButtonFunc) {
		this.setColumnId(columnId);
		this.setName(name);
		this.setInitValue(initValue);
		this.setWidth(width);
		this.setAlign(align);
		this.setEditable(editable);
		this.setHidden(hidden);
		this.setSortable(sortable);
		this.setEditType(editType);
		this.setEdithidden(edithidden);
		this.setValue(value);
		this.setEditValue(editValue);
		this.setValidateRule(validateRule);
		this.setPrimaryKey(primaryKey);
		this.setSequence(sequence);
		this.setSeqPrefix(seqPrefix);
		this.setComplexKey(complexKey);
		this.setAssigned(assigned);
		this.setDataEvent(dataEvent);
		this.setEventRule(eventRule);
		this.setButtonCls(buttonCls);
		this.setCallFunc(callFunc);
		this.setFuncName(funcName);
		this.setDialogTarget(dialogTarget);
		this.setDialogColumns(dialogColumns);
		this.setDialogQuery(dialogQuery);
		this.setDialogType(dialogType);
		this.setDialogConditions(dialogConditions);
		this.setDialogCustom(dialogCustom);
		this.setDialogQuickAdd(dialogQuickAdd);
		this.setThreshold(threshold);
		this.setThresholdParam(thresholdParam);
		this.setCustomCallback(customCallback);
		this.setFuzzyQuery(fuzzyQuery);
		this.setSubWinId(subWinId);
		this.setShowImage(showImage);
		this.setFrozen(frozen);
		this.setDownloadable(downloadable);
		this.setUseDefaultFileName(useDefaultFileName);
		this.setReadonly(readonly);
		this.setSequenceSource(sequenceSource);
		this.setInitialSelectData(initialSelectData);
		this.setCellButtonFunc(cellButtonFunc);
	}


	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setInitValue(String initValue) {
		this.initValue = initValue;
	}

	public String getInitValue() {
		return initValue;
	}
	
	public void setWidth(String width) {
		this.width = width;
	}

	public String getWidth() {
		return width;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEdithidden(boolean edithidden) {
		this.edithidden = edithidden;
	}

	public boolean isEdithidden() {
		return edithidden;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getAlign() {
		return align;
	}

	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	public boolean isSortable() {
		return sortable;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	public String getEditType() {
		return editType;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	public void setValidateRule(String validateRule) {
		this.validateRule = validateRule;
	}

	public String getValidateRule() {
		return validateRule;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSeqPrefix(String seqPrefix) {
		this.seqPrefix = seqPrefix;
	}

	public String getSeqPrefix() {
		return seqPrefix;
	}

	public void setComplexKey(String complexKey) {
		this.complexKey = complexKey;
	}

	public String getComplexKey() {
		return complexKey;
	}

	public void setAssigned(String assigned) {
		this.assigned = assigned;
	}

	public String getAssigned() {
		return assigned;
	}

	public void setEditValue(String editValue) {
		this.editValue = editValue;
	}

	public String getEditValue() {
		return editValue;
	}

	public void setDataEvent(String dataEvent) {
		this.dataEvent = dataEvent;
	}

	public String getDataEvent() {
		return dataEvent;
	}

	public void setEventRule(String eventRule) {
		this.eventRule = eventRule;
	}

	public String getEventRule() {
		return eventRule;
	}

	public void setCallFunc(String callFunc) {
		this.callFunc = callFunc;
	}

	public String getCallFunc() {
		return callFunc;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setDialogTarget(String dialogTarget) {
		this.dialogTarget = dialogTarget;
	}

	public String getDialogTarget() {
		return dialogTarget;
	}

	public String getDialogColumns() {
		return dialogColumns;
	}

	public void setDialogColumns(String dialogColumns) {
		this.dialogColumns = dialogColumns;
	}

	public String getDialogQuery() {
		return dialogQuery;
	}

	public void setDialogQuery(String dialogQuery) {
		this.dialogQuery = dialogQuery;
	}

	public String getDialogType() {
		return dialogType;
	}

	public void setDialogType(String dialogType) {
		this.dialogType = dialogType;
	}

	public String getDialogConditions() {
		return dialogConditions;
	}

	public void setDialogConditions(String dialogConditions) {
		this.dialogConditions = dialogConditions;
	}

	public void setButtonCls(String buttonCls) {
		this.buttonCls = buttonCls;
	}

	public String getButtonCls() {
		return buttonCls;
	}

	public void setDialogCustom(String dialogCustom) {
		this.dialogCustom = dialogCustom;
	}

	public String getDialogCustom() {
		return dialogCustom;
	}

	public void setDialogQuickAdd(String dialogQuickAdd) {
		this.dialogQuickAdd = dialogQuickAdd;
	}

	public String getDialogQuickAdd() {
		return dialogQuickAdd;
	}

	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}

	public String getThreshold() {
		return threshold;
	}

	public void setCustomCallback(String customCallback) {
		this.customCallback = customCallback;
	}

	public String getCustomCallback() {
		return customCallback;
	}

	public void setFuzzyQuery(String fuzzyQuery) {
		this.fuzzyQuery = fuzzyQuery;
	}

	public String getFuzzyQuery() {
		return fuzzyQuery;
	}

	public void setSubWinId(String subWinId) {
		this.subWinId = subWinId;
	}

	public String getSubWinId() {
		return subWinId;
	}

	public void setThresholdParam(String thresholdParam) {
		this.thresholdParam = thresholdParam;
	}

	public String getThresholdParam() {
		return thresholdParam;
	}

	public void setShowImage(boolean showImage) {
		this.showImage = showImage;
	}

	public boolean isShowImage() {
		return showImage;
	}

	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}

	public boolean isFrozen() {
		return frozen;
	}

	public void setDownloadable(boolean downloadable) {
		this.downloadable = downloadable;
	}

	public boolean isDownloadable() {
		return downloadable;
	}

	public void setUseDefaultFileName(boolean useDefaultFileName) {
		this.useDefaultFileName = useDefaultFileName;
	}

	public boolean isUseDefaultFileName() {
		return useDefaultFileName;
	}

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public String getSequenceSource() {
		return sequenceSource;
	}

	public void setSequenceSource(String sequenceSource) {
		this.sequenceSource = sequenceSource;
	}

	public String getInitialSelectData() {
		return initialSelectData;
	}

	public void setInitialSelectData(String initialSelectData) {
		this.initialSelectData = initialSelectData;
	}

	public String getCellButtonFunc() {
		return cellButtonFunc;
	}

	public void setCellButtonFunc(String cellButtonFunc) {
		this.cellButtonFunc = cellButtonFunc;
	}
	
	
	
}
