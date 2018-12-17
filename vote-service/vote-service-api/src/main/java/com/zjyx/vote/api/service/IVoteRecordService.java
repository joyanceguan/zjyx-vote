package com.zjyx.vote.api.service;

import java.util.List;

import com.zjyx.vote.api.model.persistence.VoteRecord;
import com.zjyx.vote.common.model.ReturnData;

public interface IVoteRecordService {

	public ReturnData<Integer> saveVoteRecord(List<VoteRecord> voteRecords);
	
}
