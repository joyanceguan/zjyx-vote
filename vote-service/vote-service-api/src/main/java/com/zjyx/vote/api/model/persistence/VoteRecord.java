package com.zjyx.vote.api.model.persistence;

import java.util.Date;

import com.zjyx.vote.api.model.enums.See_Type;

public class VoteRecord {

	private String id;
	//投票id
	private Long vote_id;
	//选项id
	private Long option_id;
	//选项内容（冗余字段）
	private String option_desc;
	//用户id
	private Long user_id;
	//ip
	private String ip;
	//投票响应时间
	private Date response_time;
	//查看结果再投
	private See_Type see_type;
	//创建时间
	private Date create_time;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getVote_id() {
		return vote_id;
	}
	public void setVote_id(Long vote_id) {
		this.vote_id = vote_id;
	}
	public Long getOption_id() {
		return option_id;
	}
	public void setOption_id(Long option_id) {
		this.option_id = option_id;
	}
	public String getOption_desc() {
		return option_desc;
	}
	public void setOption_desc(String option_desc) {
		this.option_desc = option_desc;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Date getResponse_time() {
		return response_time;
	}
	public void setResponse_time(Date response_time) {
		this.response_time = response_time;
	}
	public See_Type getSee_type() {
		return see_type;
	}
	public void setSee_type(See_Type see_type) {
		this.see_type = see_type;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
