package com.zjyx.vote.impl.service;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zjyx.vote.api.model.persistence.VoteRecord;
import com.zjyx.vote.api.service.IVoteRecordService;
import com.zjyx.vote.common.enums.Error_Type;
import com.zjyx.vote.common.model.ReturnData;
import com.zjyx.vote.impl.mapper.VoteRecordMapper;

@Service
public class VoteRecordServiceImpl implements IVoteRecordService{

	@Resource
	VoteRecordMapper voteRecordMapper;
	
	@Override
	public ReturnData<Integer> saveVoteRecord(List<VoteRecord> voteRecords) {
		ReturnData<Integer> returnData = new ReturnData<Integer>();
		int success = 0;
		if(voteRecords == null || voteRecords.isEmpty()){
			returnData.setErrorInfo(Error_Type.PARAM_ERROR, null, null);
			return returnData;
		}
		Iterator<VoteRecord> iterator = voteRecords.iterator();
		while(iterator.hasNext()){
			VoteRecord voteRecord = iterator.next();
			if(voteRecord == null)
				iterator.remove();
		}
		success = voteRecordMapper.batchSave(voteRecords);
		returnData.setResultData(success);
		return returnData;
	}


}
