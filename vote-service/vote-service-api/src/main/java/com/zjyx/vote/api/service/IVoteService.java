package com.zjyx.vote.api.service;

import com.zjyx.vote.api.model.condition.VoteCdtn;
import com.zjyx.vote.api.model.enums.Vote_Status;
import com.zjyx.vote.api.model.persistence.Vote;
import com.zjyx.vote.common.model.PageInfo;
import com.zjyx.vote.common.model.ReturnData;

public interface IVoteService {

	public PageInfo<Vote> list(VoteCdtn voteCdtn);
	
	public ReturnData<Vote> selectById(Long id);
	
	public ReturnData<Integer> updateStatus(Long id,Vote_Status vote_Status);
	
}
