package com.zjyx.vote.impl.mapper;

import java.util.List;

public interface VoteTypeRelateMapper {

	public int batchSave(List<VoteTypeRelateMapper> list);
	
	public List<Long> selectByTypeId(int type_id);
	
	public List<Integer> selectByVoteId(Long vote_id);
}
