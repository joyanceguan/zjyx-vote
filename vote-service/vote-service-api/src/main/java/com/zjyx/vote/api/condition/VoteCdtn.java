package com.zjyx.vote.api.condition;

import java.util.List;

import com.zjyx.vote.api.enums.Vote_Status;
import com.zjyx.vote.api.enums.Vote_Type;
import com.zjyx.vote.common.model.BasePageCondition;

public class VoteCdtn extends BasePageCondition{

    private String title;
    private String beginTime;
    private String endTime;
    private String createBeginTime;
    private String createEndTime;
    private Vote_Status status;
    private List<Vote_Type> types;
    private Long limitType;
    private Long createUserId;
      
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getCreateBeginTime() {
		return createBeginTime;
	}
	public void setCreateBeginTime(String createBeginTime) {
		this.createBeginTime = createBeginTime;
	}
	public String getCreateEndTime() {
		return createEndTime;
	}
	public void setCreateEndTime(String createEndTime) {
		this.createEndTime = createEndTime;
	}
	public Vote_Status getStatus() {
		return status;
	}
	public void setStatus(Vote_Status status) {
		this.status = status;
	}
	public List<Vote_Type> getTypes() {
		return types;
	}
	public void setTypes(List<Vote_Type> types) {
		this.types = types;
	}
	public Long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	public Long getLimitType() {
		return limitType;
	}
	public void setLimitType(Long limitType) {
		this.limitType = limitType;
	}
      
}
