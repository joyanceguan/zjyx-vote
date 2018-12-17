package com.zjyx.vote.front.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zjyx.vote.api.model.persistence.Vote;
import com.zjyx.vote.api.service.IVoteService;
import com.zjyx.vote.common.model.BasePageCondition;
import com.zjyx.vote.common.model.PageInfo;

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
		PageInfo<Vote> pageInfo = voteService.hotList(condition);
		mv.addObject("pageInfo", pageInfo);
		return mv;
	}
	
	@RequestMapping("/ranklist")
	public ModelAndView rankList(BasePageCondition condition){
		ModelAndView mv = new ModelAndView("front/list");
		PageInfo<Vote> pageInfo = voteService.rankList(condition);
		mv.addObject("pageInfo", pageInfo);
		return mv;
	}
}
