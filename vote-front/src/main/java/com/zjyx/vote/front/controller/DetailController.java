package com.zjyx.vote.front.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zjyx.vote.api.model.persistence.Vote;
import com.zjyx.vote.api.service.IVoteService;
import com.zjyx.vote.common.model.ReturnData;

@Controller
public class DetailController {

	@Resource
	IVoteService voteService;
	
	@RequestMapping("/detail")
	public ModelAndView detail(Long id){
		ModelAndView mv = new ModelAndView("front/detail");
		ReturnData<Vote> returnData = voteService.selectById(id);
		Vote vote = returnData.getResultData();
		mv.addObject("vote", vote);
		return mv;
	}
	
}
