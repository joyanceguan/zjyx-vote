package com.zjyx.vote.impl.transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.zjyx.vote.api.dto.VoteDto;
import com.zjyx.vote.api.dto.VoteOptionMini;
import com.zjyx.vote.api.enums.Vote_Option_Type;
import com.zjyx.vote.api.enums.Vote_Status;
import com.zjyx.vote.api.persistence.Vote;
import com.zjyx.vote.api.persistence.VoteOption;
import com.zjyx.vote.api.transaction.IVoteTransService;
import com.zjyx.vote.common.enums.Error_Type;
import com.zjyx.vote.common.exceptions.TransactionException;
import com.zjyx.vote.common.model.ReturnData;
import com.zjyx.vote.common.model.VoteRuleDto;
import com.zjyx.vote.common.utils.VoteRuleUtils;
import com.zjyx.vote.impl.mapper.VoteMapper;
import com.zjyx.vote.impl.mapper.VoteOptionMapper;

@Service
public class VoteTransSerivceImpl implements IVoteTransService{

	@Resource
	VoteMapper voteMapper;
	
	@Resource
	VoteOptionMapper voteOptionMapper;

	@Transactional
	@Override
	public ReturnData<Vote> saveVote(VoteDto voteDto, VoteRuleDto ruleDto, List<VoteOption> voteOptions) {
		ReturnData<Vote> returnData = new ReturnData<Vote>();
		if(voteDto == null || voteOptions == null || voteOptions.isEmpty()){
			throw new TransactionException(Error_Type.PARAM_ERROR,null,null);
		}
		if(StringUtils.isBlank(voteDto.getTitle())){
			throw new TransactionException(Error_Type.PARAM_ERROR,null,"标题不能为空");
		}
		if(voteDto.getType() == null){
			throw new TransactionException(Error_Type.PARAM_ERROR,null,"投票类型为空");
		}
		Date now = new Date();
		if(voteDto.getEndTime()!=null){
			if(voteDto.getEndTime().compareTo(now) < 0){
				throw new TransactionException(Error_Type.PARAM_ERROR,null,"无效的投票截止时间");
			}
			if(voteDto.getBeginTime()!=null){
				if(voteDto.getEndTime().compareTo(voteDto.getBeginTime())<0){
					throw new TransactionException(Error_Type.PARAM_ERROR,null,"无效的投票时间区间");
				}
			}
		}
		int limitType = 0;
		String limitRule = null;
		//如果限制类型为空，则不设置限制规则
		if(ruleDto!=null){
			if(ruleDto.isIpTimes() && ruleDto.getIpTimesCount() < 1){
				throw new TransactionException(Error_Type.PARAM_ERROR,null,"无效的ip次数限制");
			}
			if(ruleDto.isRate() && ruleDto.getRateCount() < 1){
				throw new TransactionException(Error_Type.PARAM_ERROR,null,"无效的rate次数");
			}
		    limitType = VoteRuleUtils.getLimitType(ruleDto);
		    limitRule = JSON.toJSONString(ruleDto);
 		}
		List<VoteOptionMini> list = new ArrayList<VoteOptionMini>();
		for(VoteOption voteOption : voteOptions){
			Vote_Option_Type type = voteOption.getVote_option_type();
			if(type == null) {
			   	throw new TransactionException(Error_Type.PARAM_ERROR,null,"选项类型为空");
			}
			if(StringUtils.isBlank(voteOption.getOption_desc())){
				throw new TransactionException(Error_Type.PARAM_ERROR,null,"选项内容不能为空");
			}
			VoteOptionMini voteOptionMini = new VoteOptionMini();
			voteOptionMini.setOption(voteOption.getOption_desc());
			voteOptionMini.setSort(voteOption.getSort());
			voteOptionMini.setVote_option_type(voteOption.getVote_option_type());
			list.add(voteOptionMini);
		}
		Collections.sort(list, new Comparator<VoteOptionMini>(){
		    @Override
		    public int compare(VoteOptionMini o1, VoteOptionMini o2) {
		    	if(o1.getSort() > o2.getSort()){
		    		return 1;
		    	}else
		    		return -1;
		}});
		if(voteDto.getBeginTime()!=null){
			if(voteDto.getBeginTime().compareTo(now) < 0){
				voteDto.setBeginTime(now);
			}
		}
		Vote vote = new Vote();
		vote.setTitle(voteDto.getTitle());
		vote.setType(voteDto.getType());
		vote.setBegin_time(voteDto.getBeginTime());
		vote.setEnd_time(voteDto.getEndTime());
		vote.setLimit_rule(limitRule);
		vote.setLimit_type(limitType);
		vote.setStatus(Vote_Status.close);
		vote.setOption_mini(JSON.toJSONString(list));
		if(voteDto.getCreateUserId() == null){
			//-1代表系统创建
			vote.setCreate_user_id(-1L);
		}
		int flag = 0;
		if(voteDto.getVoteId() == null){
			flag = voteMapper.save(vote);
		}else{
			flag = voteMapper.update(vote);
		}
		if(flag < 1){
			throw new TransactionException(Error_Type.SYSTEM_ERROR,null,"保存投票失败");
		}
		for(VoteOption voteOption : voteOptions){
			voteOption.setVote_id(vote.getId());
		}
		flag = voteOptionMapper.batchSave(voteOptions);
		if(voteOptions.size()!=flag){
			throw new TransactionException(Error_Type.SYSTEM_ERROR,null,"保存选项失败");
		}
		returnData.setResultData(vote);
		return returnData;
	}
	

}
