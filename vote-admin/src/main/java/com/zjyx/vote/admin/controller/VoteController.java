package com.zjyx.vote.admin.controller;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zjyx.vote.admin.param.VoteParam;
import com.zjyx.vote.admin.viewmodel.BaseVM;
import com.zjyx.vote.api.model.dto.VoteDto;
import com.zjyx.vote.api.model.dto.VoteRuleDto;
import com.zjyx.vote.api.model.enums.Vote_Option_Type;
import com.zjyx.vote.api.model.enums.Vote_Type;
import com.zjyx.vote.api.model.persistence.Vote;
import com.zjyx.vote.api.model.persistence.VoteOption;
import com.zjyx.vote.api.service.IVoteService;
import com.zjyx.vote.api.transaction.IVoteTransService;
import com.zjyx.vote.common.enums.Error_Type;
import com.zjyx.vote.common.model.ReturnData;

@Controller
public class VoteController {

	@Resource
	IVoteTransService voteTransService;
	
	@Resource
	IVoteService voteService; 
	
	
	@RequestMapping("/vote")
	public ModelAndView vote(){
		ModelAndView mv = new ModelAndView("admin/vote/vote");
		mv.addObject("voteTypes", Vote_Type.values());
		mv.addObject("optionTypes", Vote_Option_Type.values());
		return mv;
	}
	
	@RequestMapping("/saveVote")
	@ResponseBody
	public BaseVM saveVote(@RequestBody VoteParam voteParam){
		BaseVM vm = new BaseVM();
		VoteDto vote = voteParam.getVote();
		List<VoteOption> options = voteParam.getOptions();
		VoteRuleDto voteRule = voteParam.getVoteRule();
		if(vote == null || options == null || options.isEmpty()){
			vm.setErrorInfo(Error_Type.PARAM_ERROR, null, "无效的投票信息");
			return vm;
		}
		if(StringUtils.isBlank(vote.getTitle())){
			vm.setErrorInfo(Error_Type.PARAM_ERROR, null, "无效的投票标题");
			return vm;
		}
		if(vote.getType() == null){
			vm.setErrorInfo(Error_Type.PARAM_ERROR, null, "无效的投票类型");
			return vm;
		}
		Date now = new Date();
		if(vote.getEndTime().compareTo(now) < 0){
			vm.setErrorInfo(Error_Type.PARAM_ERROR, null, "无效的投票截止时间");
			return vm;
		}
		if(vote.getBeginTime()!=null && vote.getEndTime()!=null){
			if(vote.getEndTime().compareTo(vote.getBeginTime())<0){
				vm.setErrorInfo(Error_Type.PARAM_ERROR, null, "无效的投票时间区间");
				return vm;
			}
		}
		Iterator<VoteOption> optionIt = options.iterator();
		while(optionIt.hasNext()){
			VoteOption option = optionIt.next();
			if(option == null){
				//删除空选项
				optionIt.remove();
			}else{
				Vote_Option_Type type = option.getVote_option_type();
				if(type == null){
					vm.setErrorInfo(Error_Type.PARAM_ERROR, null, "无效的选项类型");
					return vm;
				}
				if(type == Vote_Option_Type.character){
					if(StringUtils.isBlank(option.getOption_desc()) || option.getOption_desc().length() > 100 ||  option.getOption_desc().length() < 1){
						vm.setErrorInfo(Error_Type.PARAM_ERROR, null, "无效的选项");
						return vm;
					}
				}
			}
		}
		if(voteRule != null){
			if(!(voteRule.isIpTimes() || voteRule.isLoginLimit() || voteRule.isRate())){
				voteRule = null;
			}else{
				if(voteRule.isIpTimes() && voteRule.getIpTimesCount() < 1){
					vm.setErrorInfo(Error_Type.PARAM_ERROR, null, "无效的ip次数限制");
					return vm;
				}
				if(voteRule.isRate() && voteRule.getRateCount() < 1){
					vm.setErrorInfo(Error_Type.PARAM_ERROR, null, "无效的rate次数");
					return vm;
				}
			}
		}
		ReturnData<Vote> returnData= voteTransService.saveVote(vote, voteRule, options);
		vm.setErrorInfo(returnData);
		return vm;
	}
}
