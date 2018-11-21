package com.ideassoft.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TB_C_STAYINCONNECT", schema = "UCR_PMS")
public class StayInConnect implements java.io.Serializable{

		private static final long serialVersionUID = 873807013422126710L;
		private String connectId;
		private String name;
		private String idCard;
		private String phone;
		private String gendor;
		private String memberId;
		private String connectMemberId;
		private String status;
		private Date recordTime;
		private String remark;
		
		public StayInConnect() {
			super();
		}

		public StayInConnect(String connectId, String name, String idCard,
				String phone, String gendor, String memberId,
				String connectMemberId, String status, Date recordTime,
				String remark) {
			super();
			this.connectId = connectId;
			this.name = name;
			this.idCard = idCard;
			this.phone = phone;
			this.gendor = gendor;
			this.memberId = memberId;
			this.connectMemberId = connectMemberId;
			this.status = status;
			this.recordTime = recordTime;
			this.remark = remark;
		}
		
		@Id
		@Column(name = "CONNECT_ID", unique = true, nullable = false, length = 12)
		public String getConnectId() {
			return connectId;
		}

		public void setConnectId(String connectId) {
			this.connectId = connectId;
		}
		
		@Column(name = "NAME", nullable = false, length = 20)
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		@Column(name = "IDCARD", nullable = false, length = 18)
		public String getIdCard() {
			return idCard;
		}

		public void setIdCard(String idCard) {
			this.idCard = idCard;
		}
		
		@Column(name = "PHONE", length = 11)
		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}
		
		@Column(name = "GENDOR", nullable = false, length = 1)
		public String getGendor() {
			return gendor;
		}

		public void setGendor(String gendor) {
			this.gendor = gendor;
		}
		
		@Column(name = "MEMBER_ID", nullable = true, length = 8)
		public String getMemberId() {
			return memberId;
		}

		public void setMemberId(String memberId) {
			this.memberId = memberId;
		}

		@Column(name = "CONNECT_MEMBERID", nullable = false, length = 8)
		public String getConnectMemberId() {
			return connectMemberId;
		}

		public void setConnectMemberId(String connectMemberId) {
			this.connectMemberId = connectMemberId;
		}
		
		@Column(name = "STATUS", nullable = false, length = 1)
		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}
		
		@Temporal(TemporalType.TIMESTAMP)
		@Column(name = "RECORD_TIME", insertable = false, updatable = true)
		public Date getRecordTime() {
			return recordTime;
		}

		public void setRecordTime(Date recordTime) {
			this.recordTime = recordTime;
		}
		
		@Column(name = "REMARK", nullable = true, length = 200)
		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}
		

}
