package com.zjyx.vote.impl.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

import com.zjyx.vote.api.model.condition.VoteCdtn;
import com.zjyx.vote.api.model.condition.VoteRankListCdt;
import com.zjyx.vote.api.model.condition.VoteTypeCdn;
import com.zjyx.vote.api.model.constants.RedisKey;
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
	
	
	@Override
	public PageInfo<Vote> hotList(BasePageCondition condition){
		PageInfo<Vote> pageInfo = new PageInfo<Vote>();
		if(condition == null || condition.getCurrentPage() < 1 || condition.getOnePageSize() < 1){
			pageInfo.setErrorType(Error_Type.PARAM_ERROR);
			return pageInfo;
		}
		//设置入参数据
		VoteRankListCdt cdt = new VoteRankListCdt();
		cdt.setCurrentPage(condition.getCurrentPage());
		cdt.setOnePageSize(condition.getOnePageSize());
		cdt.setNeedTotalResults(condition.isNeedTotalResults());
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MINUTE, 0 - VoteConstants.HOT_VOTE_TIME);
		cdt.setBeginTime(c.getTime());
		int count = 0;
		List<Vote> voteSortList = null;
		Map<Long,Long> extendMap = null;
		if(condition.isNeedTotalResults()){
			 count = voteRecordMapper.rankListCount(cdt);
		}
		List<VoteResult> list = voteRecordMapper.rankList(cdt);
		//如果从记录表查出数据
		if(list!=null && list.size()>0){
			List<Long> voteIds = new ArrayList<Long>();
			extendMap = new HashMap<Long,Long>();
			for(VoteResult voteResult:list){
				voteIds.add(voteResult.getId());
			}
			List<Vote> voteList = voteMapper.selectByIds(voteIds, null);
			Map<Long,Vote> map = Vote.listToMap(voteList);
			voteSortList = new ArrayList<Vote>();
			for(VoteResult voteResult:list){
				voteSortList.add(map.get(voteResult.getId()));
				extendMap.put(voteResult.getId(), Long.valueOf(voteResult.getCount()));
			}
		}
		pageInfo.setPageInfo(condition ,count, voteSortList, list);
		return pageInfo;
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
		Set<TypedTuple<Object>> values = redisTemplate.opsForZSet().reverseRangeWithScores(redisKey, beginNum, onePageSize);
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
	    List<Vote> voteList = voteMapper.selectByIds(voteIds, null);
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
	public ReturnData<List<Vote>> randomList(int size) {
		ReturnData<List<Vote>> returnData = new ReturnData<List<Vote>>();
		if(size < 1 || size > 100){
			returnData.setErrorType(Error_Type.PARAM_ERROR);
			return returnData;
		}
		String redisKey = RedisKey.VOTE_RANK_KEY;
		Set<Object> ids= redisTemplate.opsForZSet().range(redisKey, 0, 1000);
		Object[] values = ids.toArray();
		Random random = new Random(values.length);
		Set<Long> randomIndex = new HashSet<Long>();
//		while(randomIndex.size() < size){
//			randomIndex.add(random.nextInt());
//		}
		return returnData;
	}
	
//	private PageInfo<Vote> rankList(BasePageCondition condition,boolean isNeedBeginTime) {
//		PageInfo<Vote> pageInfo = new PageInfo<Vote>();
//		if(condition == null || condition.getCurrentPage() < 1 || condition.getOnePageSize() < 1){
//			pageInfo.setErrorType(Error_Type.PARAM_ERROR);
//			return pageInfo;
//		}
//		//初始化数据
//		VoteRankListCdt condtion = new VoteRankListCdt();
//		condtion.setBeginNum(condition.getBeginNum());
//		condtion.setCurrentPage(condition.getCurrentPage());
//		condtion.setNeedTotalResults(condition.isNeedTotalResults());
//		condtion.setOnePageSize(condition.getOnePageSize());
//		int totalCount = 0;
//		int beginNum = condition.getBeginNum();
//		Collection<Object> list = new ArrayList<Object>();
//		//获取要查找的id列表
//		for(int i = beginNum;i<beginNum+condition.getOnePageSize();i++){
//			list.add(i);
//		}
//		String redisKey = null;
//		if(isNeedBeginTime){
//			//热投
//			redisKey = RedisKey.VOTE_HOT_KEY;
//			Calendar c = Calendar.getInstance();
//			c.set(Calendar.MINUTE, 0 - VoteConstants.HOT_VOTE_TIME);
//			condtion.setBeginTime(c.getTime());
//		}else{
//			//榜单投
//			redisKey = RedisKey.VOTE_RANK_KEY;
//			condtion.setBeginTime(null);
//		}
//		List<Object> voteRankList = redisTemplate.opsForHash().multiGet(redisKey, list);
//		if(condition.isNeedTotalResults()){
//	    	totalCount = redisTemplate.opsForHash().size(redisKey).intValue();
//	    }
//		List<Long> voteIds = new ArrayList<Long>();
//		//获取投票id
//	    for(Object obj:voteRankList){
//	    	RankVote rv = (RankVote) obj;
//	    	voteIds.add(rv.getVote_id());
//	    }
//	    //获取id对应的投票列表
//	    List<Vote> voteList = voteMapper.selectByIds(voteIds, null);
//	    Map<Long,Vote> map = Vote.listToMap(voteList);
//	    //投票id，投票数量映射map
//	    Map<Long,Long> extendMap = new HashMap<Long,Long>();
//	    voteList = new ArrayList<Vote>();
//	    for(Object obj:voteRankList){
//	    	RankVote rv = (RankVote) obj;
//	    	Vote vote= map.get(rv.getVote_id());
//	    	voteList.add(vote);
//	    	extendMap.put(rv.getVote_id(), rv.getVote_count());
//	    }
//	    pageInfo.setPageInfo(condition ,totalCount, voteList,extendMap);
//		return pageInfo;
//	}

}
