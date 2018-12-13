package com.zjyx.vote.impl.service;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.zjyx.vote.api.condition.VoteCdtn;
import com.zjyx.vote.api.persistence.Vote;
import com.zjyx.vote.api.service.IVoteService;
import com.zjyx.vote.common.enums.Error_Type;
import com.zjyx.vote.common.model.PageInfo;
import com.zjyx.vote.impl.mapper.VoteMapper;


@Service
public class VoteServiceImpl implements IVoteService{

	@Resource
	VoteMapper voteMapper;
	
	@Override
	public PageInfo<Vote> list(VoteCdtn voteCdtn) {
		PageInfo<Vote> pageInfo = new PageInfo<Vote>();
		if(voteCdtn == null || voteCdtn.getCurrentPage() < 1 || voteCdtn.getOnePageSize() < 1){
			pageInfo.setErrorType(Error_Type.PARAM_ERROR);
		}
		int counts = voteMapper.count(voteCdtn);
		if(counts > 0){
			List<Vote> list = voteMapper.list(voteCdtn);
			pageInfo.setPageInfo(voteCdtn ,counts, list, null);
		}
		return pageInfo;
	}

}
