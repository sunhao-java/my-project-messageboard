package com.message.main.event.pojo;

import java.util.Date;

import com.message.main.user.pojo.User;

/**
 * 事件实体
 * @author sunhao(sunhao.java@gmail.com)
 */
public class BaseEvent {
	private Long pkId;					//主键
	private Long operationType;			//操作类型
	private Long operatorId;			//操作者ID
	private Long ownerId;				//拥有者ID
	private int resourceType;			//被操作对象类型标识
	private Long resourceId;			//被操作对象的ID
	private Date operationTime;			//操作发生时间
	private String operationIP;			//操作发生地IP
	private String description;			//操作事件的描述
	
	//VO Field
	private User operator;				//操作者
	private User owner;					//对象拥有者
	
	/**
	 * 默认构造器
	 */
	public BaseEvent(){
		
	}
	
	/**
	 * 操作事件的超类
	 * @param operatorId		操作者ID
	 * @param operationType     操作类型
	 * @param ownerId			拥有者ID
	 * @param resourceType		被操作对象类型标识
	 * @param resourceId		被操作对象的ID
	 * @param operationIP		操作发生地IP
	 * @param description		操作事件的描述
	 */
	public BaseEvent(Long operatorId, Long operationType, Long ownerId, int resourceType,
			Long resourceId, String operationIP, String description) {
		this.operatorId = operatorId;
		this.operationType = operationType;
		this.ownerId = ownerId;
		this.resourceType = resourceType;
		this.resourceId = resourceId;
		this.operationIP = operationIP;
		this.description = description;
	}

	public Long getPkId() {
		return pkId;
	}

	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}

	public Long getOperationType() {
		return operationType;
	}

	public void setOperationType(Long operationType) {
		this.operationType = operationType;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public int getResourceType() {
		return resourceType;
	}

	public void setResourceType(int resourceType) {
		this.resourceType = resourceType;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}

	public String getOperationIP() {
		return operationIP;
	}

	public void setOperationIP(String operationIP) {
		this.operationIP = operationIP;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getOperator() {
		return operator;
	}

	public void setOperator(User operator) {
		this.operator = operator;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
}