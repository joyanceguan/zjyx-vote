package com.zjyx.vote.api.transaction;

import java.util.List;

import com.zjyx.vote.api.model.persistence.VoteRecord;
import com.zjyx.vote.common.model.ReturnData;

public interface IVoteRecordTransSerivce {

	/**
	 * 批量保存
	 * @param voteRecords
	 * @return
	 */
	public ReturnData<Integer> batchSave(List<VoteRecord> voteRecords);
}
