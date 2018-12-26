package com.zjyx.vote.impl.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.zjyx.vote.api.model.constants.RedisKey;
import com.zjyx.vote.api.model.persistence.VoteRecord;
import com.zjyx.vote.api.service.IVoteRecordService;
import com.zjyx.vote.common.model.ReturnData;
import com.zjyx.vote.common.utils.UUIDUtils;

@Service
public class VoteRecordServiceImpl implements IVoteRecordService{
   
	@Resource
	RedisTemplate<String, Object> redisTemplate;
	
	@Override
	public ReturnData<Integer> save(VoteRecord voteRecord) {
		ReturnData<Integer> returnData = new ReturnData<Integer>();
		voteRecord.setId(UUIDUtils.getUUId());
		//这两个字段放在controller层设置
//		voteRecord.setCreate_time(new Date());
//		voteRecord.setIp(ip);
		redisTemplate.opsForList().leftPush(RedisKey.VOTERECORD_SAVE_KEY, voteRecord);
		return returnData;
	}

}
