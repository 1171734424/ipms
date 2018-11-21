package com.ideassoft.core.notifier.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 前台事件通知内容
 * @author ZenShin
 *
 */
public class NotifyContent implements Serializable, Cloneable{
	private static final long serialVersionUID = 2550417284264161154L;

	/**
     * 通知类型, 由后台接口定义常量
     */
    private int notifyType;

    /**
     * 通知相关数据集合
     */
    private List<?> notifyData;
    
    /**
     * 设备ID
     */
    private String equipId;


    public String getEquipId()
    {
        return equipId;
    }

    public void setEquipId(String equipId)
    {
        this.equipId = equipId;
    }

    public int getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(int notifyType) {
        this.notifyType = notifyType;
    }

    public List<?> getNotifyData() {
        return notifyData;
    }

    public void setNotifyData(List<?> notifyData) {
        this.notifyData = notifyData;
    }
    public NotifyContent clone(){
    	NotifyContent notifyContent = null;
		try {
			notifyContent = (NotifyContent)super.clone();
			return notifyContent;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
    }
}
