package com.zjyx.vote.impl.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.zjyx.vote.api.model.condition.VoteRankListCdt;
import com.zjyx.vote.api.model.constants.RedisKey;
import com.zjyx.vote.api.model.persistence.VoteRecord;
import com.zjyx.vote.api.model.result.VoteResult;
import com.zjyx.vote.api.service.IVoteRecordService;
import com.zjyx.vote.common.constants.VoteConstants;
import com.zjyx.vote.common.enums.Error_Type;
import com.zjyx.vote.common.model.PageInfo;
import com.zjyx.vote.common.model.ReturnData;
import com.zjyx.vote.common.utils.UUIDUtils;
import com.zjyx.vote.impl.mapper.VoteRecordMapper;

@Service
public class VoteRecordServiceImpl implements IVoteRecordService{
   
	private static final Logger errorLog = LoggerFactory.getLogger("errorLog");
	
	@Resource
	RedisTemplate<String, Object> redisTemplate;
	
	@Resource
	VoteRecordMapper voteRecordMapper;
	
	@Override
	public ReturnData<Integer> save(VoteRecord voteRecord) {
		ReturnData<Integer> returnData = verfiySave(voteRecord);
		if(returnData.getErrorType()!=Error_Type.SUCCESS){
			return returnData;
		}
		voteRecord.setId(UUIDUtils.getUUId());
		//发送redis
		sendRecordToRedis(voteRecord);
		//更新统计redis
		sendCountToRedis(voteRecord);
		return returnData;
	}
	
	private ReturnData<Integer> verfiySave(VoteRecord voteRecord){
		ReturnData<Integer> returnData = new ReturnData<Integer>();
		if(voteRecord == null){
			returnData.setErrorType(Error_Type.PARAM_ERROR);
			return returnData;
		}
		if(StringUtils.isBlank(voteRecord.getOption_desc())){
			returnData.setErrorType(Error_Type.PARAM_ERROR);
			return returnData;
		}
		if(voteRecord.getResponse_time() == null){
			returnData.setErrorType(Error_Type.PARAM_ERROR);
			return returnData;
		}
		if(voteRecord.getCreate_time() == null){
			returnData.setErrorType(Error_Type.PARAM_ERROR);
			return returnData;
		}
		if(voteRecord.getOption_id() == null || voteRecord.getOption_id() < 1){
			returnData.setErrorType(Error_Type.PARAM_ERROR);
			return returnData;
		}
		if(voteRecord.getSee_type() == null){
			returnData.setErrorType(Error_Type.PARAM_ERROR);
			return returnData;
		}
		if(voteRecord.getVote_id() == null){
			returnData.setErrorType(Error_Type.PARAM_ERROR);
			return returnData;
		}
		return returnData;
	}
	
	/*
	 * 发送投票记录给redis
	 */
    private Long recordToRedis(VoteRecord voteRecord){
    	Long flag = null;
    	try{
    		flag =redisTemplate.opsForSet().add(RedisKey.VOTERECORD_SAVE_KEY, voteRecord);
    	}catch(Exception e){
    		//日志
    		errorLog.error("save redis exception", e);
    	}
    	return flag;
    }
    
    private void resendRecordToRedis(VoteRecord voteRecord,Long flag){
    	//如果失败了 重试
        if(flag == null || flag < 1 ){
        	int retryTime = 0;
        	while(retryTime < VoteConstants.RESEND_REDIS_TIME && (flag == null || flag < 1)){
        		flag = recordToRedis(voteRecord);
        		retryTime++;
            }
        	//如果重试几次还不成功,可以异步线程扔进数据库
        	if(flag == null || flag < 1){
        		// JOY TODO
        	}
        }
    }
    
    private void sendRecordToRedis(VoteRecord voteRecord){
    	Long flag = recordToRedis(voteRecord);
    	resendRecordToRedis(voteRecord,flag);
    }
    
    
    /*
	 * 发送投票记录给redis
	 */
    private Double countToRedis(VoteRecord voteRecord){
    	Double flag = null;
    	try{
    		//更新统计redis
    		redisTemplate.opsForZSet().incrementScore(RedisKey.VOTE_RANK_KEY, voteRecord.getVote_id(), 1);
    	}catch(Exception e){
    		//日志
    		errorLog.error("save redis exception", e);
    	}
    	return flag;
    }
    
    private void resendCountToRedis(VoteRecord voteRecord,Double flag){
    	//如果失败了 重试
        if(flag == null || flag < 1 ){
        	int retryTime = 0;
        	while(retryTime < VoteConstants.RESEND_REDIS_TIME && (flag == null || flag < 1)){
        		flag = countToRedis(voteRecord);
        		retryTime++;
            }
        	//如果重试几次还不成功,可以异步线程扔进数据库
        	if(flag == null || flag < 1){
        		// JOY TODO
        	}
        }
    }
    
    private void sendCountToRedis(VoteRecord voteRecord){
    	Double flag = countToRedis(voteRecord);
    	resendCountToRedis(voteRecord,flag);
    }

	@Override
	public PageInfo<VoteResult> rankList(VoteRankListCdt condition) {
		PageInfo<VoteResult> pageinfo = new PageInfo<VoteResult>();
		if(condition == null || !condition.isRightPageInfo()){
			pageinfo.setErrorType(Error_Type.PARAM_ERROR);
			return pageinfo;
		}
		int count = 0;
		if(condition.isNeedTotalResults()){
		    count = voteRecordMapper.rankListCount(condition);
		}
		List<VoteResult> list = voteRecordMapper.rankList(condition);
		pageinfo.setPageInfo(condition ,Long.valueOf(count), list, null);
		return pageinfo;
	}
}


