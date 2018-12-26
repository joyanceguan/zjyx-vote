package com.zjyx.vote.api.service;

import com.zjyx.vote.api.model.persistence.VoteRecord;
import com.zjyx.vote.common.model.ReturnData;

public interface IVoteRecordService {

	public ReturnData<Integer> save(VoteRecord voteRecord);
}
