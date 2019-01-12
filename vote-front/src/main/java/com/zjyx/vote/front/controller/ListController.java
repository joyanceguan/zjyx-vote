package com.zjyx.vote.front.controller;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zjyx.vote.api.model.condition.SexRankCdt;
import com.zjyx.vote.api.model.condition.VoteTypeCdn;
import com.zjyx.vote.api.model.enums.Sex;
import com.zjyx.vote.api.model.persistence.Vote;
import com.zjyx.vote.api.service.IVoteService;
import com.zjyx.vote.common.enums.Error_Type;
import com.zjyx.vote.common.model.BasePageCondition;
import com.zjyx.vote.common.model.PageInfo;
import com.zjyx.vote.common.model.ReturnData;
import com.zjyx.vote.front.utils.WebContextHelper;

@Controller
public class ListController {
	
	private final static List<String> LIST_PAGE = Arrays.asList("hot","rank","sex1","sex2");
	private final static List<String> TYPE_LIST_PAGE = Arrays.asList("technology","ent","live","international","sports");

	@Resource
	IVoteService voteService;
	
	@RequestMapping("/random")
	public ModelAndView random(){
		ModelAndView mv = new ModelAndView("front/random");
		return mv;
	}
	
	@RequestMapping("/list")
	public ModelAndView list(String page){
		String url = "";
		if(LIST_PAGE.contains(page)){
			 url = "front/list";
		}else if(TYPE_LIST_PAGE.contains(page)){
			 url = "front/type_list";
		}
		ModelAndView mv = new ModelAndView(url);
		mv.addObject("page", page);
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("/hotlist")
	public ReturnData<List<Vote>> hotList(){
		ReturnData<List<Vote>> returnData = voteService.hotList();
		return returnData;
	}
	
	@ResponseBody
	@RequestMapping("/ranklist")
	public PageInfo<Vote> rankList(BasePageCondition condition){
		PageInfo<Vote> pageinfo = voteService.rankList(condition);
		return pageinfo;
	}
	
	@ResponseBody
	@RequestMapping("/typeList")
	public PageInfo<Vote> typeList(VoteTypeCdn condition){
		PageInfo<Vote> vm = new PageInfo<Vote>();
		if(condition.getTypeId() < 1){
			vm.setErrorType(Error_Type.PARAM_ERROR);
			return vm;
		}
		vm = voteService.typeWithRankList(condition);
		return vm;
	}
	
	@ResponseBody
	@RequestMapping("/randominfo")
	public ReturnData<Vote> randomInfo(){
		ReturnData<Vote> returnData = new ReturnData<Vote>();
		Long userId = WebContextHelper.getUserId();
		if(userId == null){
			returnData.setErrorInfo(Error_Type.SERVICE_ERROR, null, "随机投仅限登录用户使用");
			return returnData;
		}
		returnData = voteService.random(userId);
		return returnData;
	}
	
	@ResponseBody
	@RequestMapping("/sexrank")
	public PageInfo<Vote> sexRank(SexRankCdt condition){
		PageInfo<Vote> vm = new PageInfo<Vote>();
		if(condition.getSex() == null || condition.getSex() == Sex.unknown){
			vm.setErrorType(Error_Type.PARAM_ERROR);
			return vm;
		}
		vm = voteService.sexRankList(condition);
		return vm;
	}
}
