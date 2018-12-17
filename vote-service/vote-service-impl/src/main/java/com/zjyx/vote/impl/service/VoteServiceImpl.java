package com.zjyx.vote.impl.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.zjyx.vote.api.model.condition.VoteCdtn;
import com.zjyx.vote.api.model.condition.VoteRankListCdt;
import com.zjyx.vote.api.model.enums.Vote_Status;
import com.zjyx.vote.api.model.persistence.Vote;
import com.zjyx.vote.api.model.result.VoteResult;
import com.zjyx.vote.api.service.IVoteService;
import com.zjyx.vote.common.constants.VoteConstants;
import com.zjyx.vote.common.enums.Error_Type;
import com.zjyx.vote.common.model.BasePageCondition;
import com.zjyx.vote.common.model.PageInfo;
import com.zjyx.vote.common.model.ReturnData;
import com.zjyx.vote.impl.mapper.VoteMapper;
import com.zjyx.vote.impl.mapper.VoteRecordMapper;


@Service
public class VoteServiceImpl implements IVoteService{

	@Resource
	VoteMapper voteMapper;
	
	@Resource
	VoteRecordMapper voteRecordMapper;
	
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

	@Override
	public ReturnData<Vote> selectById(Long id) {
		ReturnData<Vote> returnData = new ReturnData<Vote>();
		Vote vote = voteMapper.selectById(id);
		returnData.setResultData(vote);
		return returnData;
	}

	@Override
	public ReturnData<Integer> updateStatus(Long id, Vote_Status vote_Status) {
		ReturnData<Integer> returnData = new ReturnData<Integer>();
		Vote vote = new Vote();
		vote.setId(id);
		vote.setStatus(vote_Status);
		int flag = voteMapper.update(vote);
		returnData.setResultData(flag);
		return returnData;
	}

	@Override
	public PageInfo<Vote> hotList(BasePageCondition condition) {
		return rankList(condition,true);
	}

	@Override
	public PageInfo<Vote> rankList(BasePageCondition condition) {
		return rankList(condition,false);
	}
	
	
	private PageInfo<Vote> rankList(BasePageCondition condition,boolean isNeedBeginTime){
		PageInfo<Vote> pageInfo = new PageInfo<Vote>();
		if(condition == null || condition.getCurrentPage() < 1 || condition.getOnePageSize() < 1){
			pageInfo.setErrorType(Error_Type.PARAM_ERROR);
			return pageInfo;
		}
		VoteRankListCdt condtion = new VoteRankListCdt();
		condtion.setBeginNum(condition.getBeginNum());
		condtion.setCurrentPage(condition.getCurrentPage());
		condtion.setNeedTotalResults(condition.isNeedTotalResults());
		condtion.setOnePageSize(condition.getOnePageSize());
		if(isNeedBeginTime){
			Calendar c = Calendar.getInstance();
			c.set(Calendar.MINUTE, 0 - VoteConstants.HOT_VOTE_TIME);
			condtion.setBeginTime(c.getTime());
		}else{
			condtion.setBeginTime(null);
		}
		int totalCount = 0;
		if(condition.isNeedTotalResults()){
		    totalCount = voteRecordMapper.rankListCount(condtion);
		}
		List<VoteResult> list = voteRecordMapper.rankList(condtion);
		List<Long> idList = new ArrayList<Long>();
		Map<Long,Integer> extendMap = new HashMap<Long,Integer>();
		if(list!=null && !list.isEmpty()){
			for(VoteResult voteResult:list){
				idList.add(voteResult.getId());
				extendMap.put(voteResult.getId(), voteResult.getCount());
			}
		}
		List<Vote> voteList = null;
		if(!idList.isEmpty()){
			voteList = voteMapper.selectByIds(idList);
		}
		pageInfo.setPageInfo(condition ,totalCount, voteList, extendMap);
		return pageInfo;
	}

}
