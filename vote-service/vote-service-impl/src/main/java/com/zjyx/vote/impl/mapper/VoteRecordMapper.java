package com.zjyx.vote.impl.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zjyx.vote.api.model.condition.VoteRankListCdt;
import com.zjyx.vote.api.model.persistence.VoteRecord;
import com.zjyx.vote.api.model.result.VoteResult;

public interface VoteRecordMapper {

	public int batchSave(List<VoteRecord> list);
	
	public List<VoteResult> rankList(VoteRankListCdt condtion);
	
	public int rankListCount(VoteRankListCdt condtion);
	
	public List<Long> getVoteIdByUser(@Param("table_name") String tableName,@Param("user_id")Long userId);
}
