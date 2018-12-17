package com.zjyx.vote.api.model.condition;

import java.util.Date;

import com.zjyx.vote.common.model.BasePageCondition;

public class VoteRankListCdt extends BasePageCondition{

	private Date beginTime;

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	
}
