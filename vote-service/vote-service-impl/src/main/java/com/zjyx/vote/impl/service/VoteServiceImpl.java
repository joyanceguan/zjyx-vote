package com.zjyx.vote.impl.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.zjyx.vote.api.model.condition.VoteCdtn;
import com.zjyx.vote.api.model.condition.VoteRankListCdt;
import com.zjyx.vote.api.model.condition.VoteTypeCdn;
import com.zjyx.vote.api.model.constants.RedisKey;
import com.zjyx.vote.api.model.enums.Vote_Status;
import com.zjyx.vote.api.model.persistence.RankVote;
import com.zjyx.vote.api.model.persistence.Vote;
import com.zjyx.vote.api.service.IVoteService;
import com.zjyx.vote.common.constants.VoteConstants;
import com.zjyx.vote.common.enums.Error_Type;
import com.zjyx.vote.common.model.BasePageCondition;
import com.zjyx.vote.common.model.PageInfo;
import com.zjyx.vote.common.model.ReturnData;
import com.zjyx.vote.impl.mapper.VoteMapper;
import com.zjyx.vote.impl.mapper.VoteRecordMapper;
import com.zjyx.vote.impl.mapper.VoteTypeRelateMapper;


@Service
public class VoteServiceImpl implements IVoteService{

	@Resource
	VoteMapper voteMapper;
	
	@Resource
	VoteRecordMapper voteRecordMapper;
	
	@Resource
	VoteTypeRelateMapper voteTypeRelateMapper;
	
	@Resource
	RedisTemplate<String, Object> redisTemplate;
	
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

	@Override
	public PageInfo<Vote> typeList(VoteTypeCdn condition) {
		PageInfo<Vote> pageInfo = new PageInfo<Vote>();
		if(condition == null || condition.getTypeId() < 1){
			pageInfo.setErrorType(Error_Type.PARAM_ERROR);
			return pageInfo;
		}
		List<Long> voteIds = voteTypeRelateMapper.selectByTypeId(condition.getTypeId());
		if(voteIds.size() > 0){
			String sort = " order by update_time desc";
			List<Vote> list = voteMapper.selectByIds(voteIds,sort);
			pageInfo.setPageInfo(condition ,voteIds.size(), list, null);
		}
		return pageInfo;
	}
	
	private PageInfo<Vote> rankList(BasePageCondition condition,boolean isNeedBeginTime) {
		PageInfo<Vote> pageInfo = new PageInfo<Vote>();
		if(condition == null || condition.getCurrentPage() < 1 || condition.getOnePageSize() < 1){
			pageInfo.setErrorType(Error_Type.PARAM_ERROR);
			return pageInfo;
		}
		//初始化数据
		VoteRankListCdt condtion = new VoteRankListCdt();
		condtion.setBeginNum(condition.getBeginNum());
		condtion.setCurrentPage(condition.getCurrentPage());
		condtion.setNeedTotalResults(condition.isNeedTotalResults());
		condtion.setOnePageSize(condition.getOnePageSize());
		int totalCount = 0;
		int beginNum = condition.getBeginNum();
		Collection<Object> list = new ArrayList<Object>();
		//获取要查找的id列表
		for(int i = beginNum;i<beginNum+condition.getOnePageSize();i++){
			list.add(i);
		}
		String redisKey = null;
		if(isNeedBeginTime){
			//热投
			redisKey = RedisKey.VOTE_HOT_KEY;
			Calendar c = Calendar.getInstance();
			c.set(Calendar.MINUTE, 0 - VoteConstants.HOT_VOTE_TIME);
			condtion.setBeginTime(c.getTime());
		}else{
			//榜单投
			redisKey = RedisKey.VOTE_RANK_KEY;
			condtion.setBeginTime(null);
		}
		List<Object> voteRankList = redisTemplate.opsForHash().multiGet(redisKey, list);
		if(condition.isNeedTotalResults()){
	    	totalCount = redisTemplate.opsForHash().size(redisKey).intValue();
	    }
		List<Long> voteIds = new ArrayList<Long>();
		//获取投票id
	    for(Object obj:voteRankList){
	    	RankVote rv = (RankVote) obj;
	    	voteIds.add(rv.getVote_id());
	    }
	    //获取id对应的投票列表
	    List<Vote> voteList = voteMapper.selectByIds(voteIds, null);
	    Map<Long,Vote> map = Vote.listToMap(voteList);
	    //投票id，投票数量映射map
	    Map<Long,Long> extendMap = new HashMap<Long,Long>();
	    voteList = new ArrayList<Vote>();
	    for(Object obj:voteRankList){
	    	RankVote rv = (RankVote) obj;
	    	Vote vote= map.get(rv.getVote_id());
	    	voteList.add(vote);
	    	extendMap.put(rv.getVote_id(), rv.getVote_count());
	    }
	    pageInfo.setPageInfo(condition ,totalCount, voteList,extendMap);
		return pageInfo;
	}

}
