package com.zjyx.vote.impl.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

import com.zjyx.vote.api.model.condition.VoteCdtn;
import com.zjyx.vote.api.model.condition.VoteTypeCdn;
import com.zjyx.vote.api.model.constants.RedisKey;
import com.zjyx.vote.api.model.enums.Vote_Status;
import com.zjyx.vote.api.model.persistence.Vote;
import com.zjyx.vote.api.model.result.VoteResult;
import com.zjyx.vote.api.service.IVoteService;
import com.zjyx.vote.api.utils.VoteRecordUtils;
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
	public PageInfo<Vote> typeList(VoteTypeCdn condition) {
		PageInfo<Vote> pageInfo = new PageInfo<Vote>();
		if(condition == null || condition.getTypeId() < 1 || condition.getTypeId() < 1){
			pageInfo.setErrorType(Error_Type.PARAM_ERROR);
			return pageInfo;
		}
		List<Long> voteIds = voteTypeRelateMapper.selectByTypeId(condition.getTypeId());
		if(voteIds.size() > 0){
			String sort = " order by update_time desc";
			List<Vote> list = voteMapper.selectByIds(voteIds,sort,condition.getStatus());
			pageInfo.setPageInfo(condition ,voteIds.size(), list, null);
		}
		return pageInfo;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Vote> hotList(){
		List<Vote> sortList = null;
		List<VoteResult> list = (List<VoteResult>) redisTemplate.opsForValue().get(RedisKey.VOTE_HOT_KEY);
	    if(list!=null && !list.isEmpty()){
	    	sortList = new ArrayList<Vote>();
	    	List<Long> ids = new ArrayList<Long>();
	    	for(VoteResult voteResult : list){
	    		ids.add(voteResult.getId());
	    	}
	    	List<Vote> voteList = voteMapper.selectByIds(ids, null, null);
	    	Map<Long,Vote> map = Vote.listToMap(voteList);
	    	for(VoteResult voteResult : list){
	    		sortList.add(map.get(voteResult.getId()));
	    	}
	    }
	    return sortList;
	}
	
	@Override
	public PageInfo<Vote> rankList(BasePageCondition condition) {
		PageInfo<Vote> pageInfo = new PageInfo<Vote>();
		if(condition == null || condition.getCurrentPage() < 1 || condition.getOnePageSize() < 1){
			pageInfo.setErrorType(Error_Type.PARAM_ERROR);
			return pageInfo;
		}
		//初始化数据
		int totalCount = 0;
		int beginNum = condition.getBeginNum();
		int onePageSize = condition.getOnePageSize();
		String redisKey = RedisKey.VOTE_RANK_KEY;
		Set<TypedTuple<Object>> values = redisTemplate.opsForZSet().reverseRangeByScoreWithScores(redisKey, 0, -1, beginNum, onePageSize);
		if(condition.isNeedTotalResults()){
	    	totalCount = redisTemplate.opsForZSet().zCard(redisKey).intValue();
	    }
		List<Long> voteIds = new ArrayList<Long>();
		//获取投票id
		for(TypedTuple<Object> type:values){
			Long voteId = (Long) type.getValue();
			voteIds.add(voteId);
	    }
	    //获取id对应的投票列表
	    List<Vote> voteList = voteMapper.selectByIds(voteIds, null,null);
	    Map<Long,Vote> map = Vote.listToMap(voteList);
	    //投票id，投票数量映射map
	    Map<Long,Long> extendMap = new HashMap<Long,Long>();
	    voteList = new ArrayList<Vote>();
	    for(TypedTuple<Object> type:values){
	    	Long voteId = (Long) type.getValue();
	    	Vote vote= map.get(voteId);
	    	voteList.add(vote);
	    	extendMap.put(voteId,type.getScore().longValue());
	    }
	    pageInfo.setPageInfo(condition ,totalCount, voteList, extendMap);
		return pageInfo;
	}

	@Override
	public ReturnData<Vote> randomList(Long userId) {
		ReturnData<Vote> returnData = new ReturnData<Vote>();
		//用户必须登录才行
		if(userId == null){
			returnData.setErrorType(Error_Type.PARAM_ERROR);
			return returnData;
		}
		String redisKey = RedisKey.VOTE_RANK_KEY;
		int onePageSize = 50;
		int totalCount = redisTemplate.opsForZSet().zCard(redisKey).intValue();
		//
		int index = totalCount > onePageSize ? totalCount - onePageSize : 0;
		Random random = new Random(index);
		Set<TypedTuple<Object>> values = redisTemplate.opsForZSet().reverseRangeByScoreWithScores(redisKey, 0, -1, random.nextInt(), onePageSize);
		//获取用户投过票的项目
		List<Long> voteIdList1 = voteRecordMapper.getVoteIdByUser(VoteRecordUtils.VOTE_RECORD_1, userId);
		List<Long> voteIdList2 = voteRecordMapper.getVoteIdByUser(VoteRecordUtils.VOTE_RECORD_2, userId);
		voteIdList1.addAll(voteIdList2);
		//获取投票id
		Long randomVoteId = null;
		for(TypedTuple<Object> type:values){
			Long voteId = (Long) type.getValue();
			if(!voteIdList1.contains(voteId)){
				randomVoteId = voteId;
			}
		}
		Vote vote = null;
		if(randomVoteId != null){
		    vote = voteMapper.selectById(randomVoteId);
		}
		returnData.setResultData(vote);
		return returnData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<Vote> typeWithRankList(VoteTypeCdn  condition) {
		PageInfo<Vote> pageinfo = new PageInfo<Vote>();
		if(condition == null || !condition.isRightPageInfo() || condition.getTypeId() < 1){
			pageinfo.setErrorType(Error_Type.PARAM_ERROR);
			return pageinfo;
		}
		List<Vote> sortList = null;
		//如果需要榜单排列，当第一页时是需要的
		if(condition.getCurrentPage() == 1){
			Object v = redisTemplate.opsForValue().get(RedisKey.TYPE_PREFIX + condition.getTypeId());
			if(v != null){
				List<VoteResult> voteResultList = (List<VoteResult>) v;
		    	List<Long> ids = new ArrayList<Long>();
		    	for(VoteResult voteResult : voteResultList){
		    		ids.add(voteResult.getId());
		    	}
		    	List<Vote> voteList = voteMapper.selectByIds(ids, null, null);
		    	Map<Long,Vote> map = Vote.listToMap(voteList);
		    	sortList = new ArrayList<Vote>();
		    	for(VoteResult voteResult : voteResultList){
		    		sortList.add(map.get(voteResult.getId()));
		    	}
			}
		}
		pageinfo = typeList(condition);
		pageinfo.setExtendInfo(sortList);
		return pageinfo;
	}
	
}
