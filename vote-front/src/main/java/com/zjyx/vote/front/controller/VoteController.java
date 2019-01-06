package com.zjyx.vote.front.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjyx.vote.api.model.persistence.User;
import com.zjyx.vote.api.model.persistence.VoteRecord;
import com.zjyx.vote.api.service.IVoteRecordService;
import com.zjyx.vote.common.enums.Error_Type;
import com.zjyx.vote.common.utils.IPUtils;
import com.zjyx.vote.front.utils.WebContextHelper;
import com.zjyx.vote.front.viewmodel.BaseVM;

@Controller
public class VoteController {

	@Resource
	IVoteRecordService voteRecordService;
	
	@RequestMapping("/save")
	@ResponseBody
	public BaseVM save(HttpServletRequest request,VoteRecord voteRecord){
		BaseVM baseVM = new BaseVM();
		if(voteRecord == null){
			baseVM.setErrorInfo(Error_Type.PARAM_ERROR, null, null);
			return baseVM;
		}
		if(StringUtils.isBlank(voteRecord.getOption_desc())){
			baseVM.setErrorInfo(Error_Type.PARAM_ERROR, null, null);
			return baseVM;
		}
		if(voteRecord.getResponse_time() == null){
			baseVM.setErrorInfo(Error_Type.PARAM_ERROR, null, null);
			return baseVM;
		}
		if(voteRecord.getCreate_time() == null){
			baseVM.setErrorInfo(Error_Type.PARAM_ERROR, null, null);
			return baseVM;
		}
		if(voteRecord.getOption_id() == null || voteRecord.getOption_id() < 1){
			baseVM.setErrorInfo(Error_Type.PARAM_ERROR, null, null);
			return baseVM;
		}
		if(voteRecord.getSee_type() == null){
			baseVM.setErrorInfo(Error_Type.PARAM_ERROR, null, null);
			return baseVM;
		}
		if(voteRecord.getVote_id() == null){
			baseVM.setErrorInfo(Error_Type.PARAM_ERROR, null, null);
			return baseVM;
		}
		voteRecord.setCreate_time(new Date());
		voteRecord.setIp(IPUtils.getIpAddr(request));
		voteRecord.setUser_id(WebContextHelper.getUserId());
		User user = null;
		if(WebContextHelper.getUser()!=null){
			user = WebContextHelper.getUser().getUser();
		}
		voteRecordService.save(voteRecord,user);
		return baseVM;
	}
}
