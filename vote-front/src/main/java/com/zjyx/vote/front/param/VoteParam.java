package com.zjyx.vote.front.param;

import java.util.List;

import com.zjyx.vote.api.dto.VoteDto;
import com.zjyx.vote.api.persistence.VoteOption;
import com.zjyx.vote.common.model.VoteRuleDto;


public class VoteParam {

   private VoteDto vote;
  
   private List<VoteOption> options;
   
   private VoteRuleDto voteRule;
   
   
   public VoteDto getVote() {
	  return vote;
   }

   public void setVote(VoteDto vote) {
	  this.vote = vote;
   }

   public List<VoteOption> getOptions() {
	  return options;
   }

   public void setOptions(List<VoteOption> options) {
	  this.options = options;
   }

   public VoteRuleDto getVoteRule() {
	  return voteRule;
   }

   public void setVoteRule(VoteRuleDto voteRule) {
	  this.voteRule = voteRule;
   }

}
