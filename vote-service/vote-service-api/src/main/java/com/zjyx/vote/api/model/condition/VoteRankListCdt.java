package com.zjyx.vote.api.model.condition;

import java.util.Date;
import java.util.List;

import com.zjyx.vote.common.model.BasePageCondition;

public class VoteRankListCdt extends BasePageCondition{

	private Date beginTime;
	
	private List<Long> voteIds;

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public List<Long> getVoteIds() {
		return voteIds;
	}

	public void setVoteIds(List<Long> voteIds) {
		this.voteIds = voteIds;
	}
	
}
