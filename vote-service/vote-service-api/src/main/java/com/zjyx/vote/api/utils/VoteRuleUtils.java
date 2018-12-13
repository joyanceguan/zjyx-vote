package com.zjyx.vote.api.utils;

import com.zjyx.vote.api.model.dto.VoteRuleDto;

public class VoteRuleUtils {

	public static int getLimitType(VoteRuleDto voteRule){
		if(voteRule == null){
			return 0;
		}
		int limitType = 0;
		if(voteRule.isLoginLimit()){
			limitType += 1;
		}
		if(voteRule.isIpTimes()){
			limitType += 1 << 1;
		}
		if(voteRule.isRate()){
			limitType += 1 << 2;
		}
		return limitType;
	}
	
}
