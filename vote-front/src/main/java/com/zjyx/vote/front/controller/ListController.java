package com.zjyx.vote.front.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zjyx.vote.api.model.persistence.Vote;
import com.zjyx.vote.api.service.IVoteService;
import com.zjyx.vote.common.enums.Error_Type;
import com.zjyx.vote.common.model.BasePageCondition;
import com.zjyx.vote.common.model.PageInfo;
import com.zjyx.vote.common.model.ReturnData;
import com.zjyx.vote.front.utils.WebContextHelper;
import com.zjyx.vote.front.viewmodel.BaseVM;

@Controller
public class ListController {

	@Resource
	IVoteService voteService;
	
	@RequestMapping("/list")
	public ModelAndView index(){
		ModelAndView mv = new ModelAndView("front/list");
		return mv;
	}
	
	@RequestMapping("/hotlist")
	public ModelAndView hotList(BasePageCondition condition){
		ModelAndView mv = new ModelAndView("front/list");
		List<Vote> list = voteService.hotList();
		mv.addObject("list", list);
		return mv;
	}
	
	@RequestMapping("/ranklist")
	public ModelAndView rankList(BasePageCondition condition){
		ModelAndView mv = new ModelAndView("front/list");
		PageInfo<Vote> pageInfo = voteService.rankList(condition);
		mv.addObject("pageInfo", pageInfo);
		return mv;
	}
	
	@RequestMapping("/random")
	public ModelAndView random(){
		ModelAndView mv = new ModelAndView("front/random");
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("/randomInfo")
	public BaseVM randomInfo(){
		BaseVM vm = new BaseVM();
		Long userId = WebContextHelper.getUserId();
		if(userId == null){
			vm.setErrorInfo(Error_Type.SERVICE_ERROR, null, "随机投仅限登录用户使用");
			return vm;
		}
		ReturnData<Vote> returnData = voteService.random(userId);
		vm.setErrorInfo(returnData);
		return vm;
	}
}
