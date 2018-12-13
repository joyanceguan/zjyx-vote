package com.zjyx.vote.common.model;

public class VoteRuleDto {

	//登录限制
	private boolean loginLimit;
	
	//ip次数限制
	private boolean ipTimes;
	
	//是否频率限制
	private boolean rate;
	
	//没人限投
	private int everyoneCount;
	
	//ip限投多少次
	private int ipTimesCount;
	
	//时间已分钟为单位(例如每天一次 60*24)
	private int rateCount;

	public boolean isLoginLimit() {
		return loginLimit;
	}

	public void setLoginLimit(boolean loginLimit) {
		this.loginLimit = loginLimit;
	}

	public boolean isIpTimes() {
		return ipTimes;
	}

	public void setIpTimes(boolean ipTimes) {
		this.ipTimes = ipTimes;
	}

	public boolean isRate() {
		return rate;
	}

	public void setRate(boolean rate) {
		this.rate = rate;
	}

	public int getIpTimesCount() {
		return ipTimesCount;
	}

	public void setIpTimesCount(int ipTimesCount) {
		this.ipTimesCount = ipTimesCount;
	}

	public int getRateCount() {
		return rateCount;
	}

	public void setRateCount(int rateCount) {
		this.rateCount = rateCount;
	}

	public int getEveryoneCount() {
		return everyoneCount;
	}

	public void setEveryoneCount(int everyoneCount) {
		this.everyoneCount = everyoneCount;
	}

}
