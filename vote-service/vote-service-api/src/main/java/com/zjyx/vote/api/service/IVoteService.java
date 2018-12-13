package com.zjyx.vote.api.service;

import com.zjyx.vote.api.model.condition.VoteCdtn;
import com.zjyx.vote.api.model.persistence.Vote;
import com.zjyx.vote.common.model.PageInfo;

public interface IVoteService {

	public PageInfo<Vote> list(VoteCdtn coteCdtn);
	
}
