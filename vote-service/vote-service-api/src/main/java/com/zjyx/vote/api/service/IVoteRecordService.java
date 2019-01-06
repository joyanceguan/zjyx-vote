package com.zjyx.vote.api.service;

import com.zjyx.vote.api.model.condition.VoteRankListCdt;
import com.zjyx.vote.api.model.persistence.User;
import com.zjyx.vote.api.model.persistence.VoteRecord;
import com.zjyx.vote.api.model.result.VoteResult;
import com.zjyx.vote.common.model.PageInfo;
import com.zjyx.vote.common.model.ReturnData;

public interface IVoteRecordService {

	/**
	 * 给redis发送数据(有重试机制)
	 * @param voteRecord
	 * @return
	 */
	public ReturnData<Integer> save(VoteRecord voteRecord,User user);
	
    /**
     * 根据条件排行	
     * @param cdt
     * @return
     */
	public PageInfo<VoteResult> rankList(VoteRankListCdt cdt);
}
