package com.zjyx.vote.impl.mapper;

import java.util.List;

import com.zjyx.vote.api.condition.VoteCdtn;
import com.zjyx.vote.api.persistence.Vote;

public interface VoteMapper {

	public int save(Vote vote);
	
	public int update(Vote vote);
	
	public Vote selectById(Long id);
	
	public int delelteById(Long id);
	
	public List<Vote> list(VoteCdtn voteCdtn);
	
	public int count(VoteCdtn voteCdtn);
	
}
