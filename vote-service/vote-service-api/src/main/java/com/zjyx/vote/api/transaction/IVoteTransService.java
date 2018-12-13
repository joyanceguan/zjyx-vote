package com.zjyx.vote.api.transaction;

import java.util.List;

import com.zjyx.vote.api.dto.VoteDto;
import com.zjyx.vote.api.persistence.Vote;
import com.zjyx.vote.api.persistence.VoteOption;
import com.zjyx.vote.common.model.ReturnData;
import com.zjyx.vote.common.model.VoteRuleDto;


public interface IVoteTransService {

	public ReturnData<Vote> saveVote(VoteDto voteDto,VoteRuleDto ruleDto,List<VoteOption> voteOptions);

}
