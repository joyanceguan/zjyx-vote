package com.zjyx.vote.api.persistence;

import java.util.Date;

import com.zjyx.vote.api.enums.Vote_Status;
import com.zjyx.vote.api.enums.Vote_Type;
import com.zjyx.vote.common.utils.DateUtils;

public class Vote {

	//id
	private Long id;
	//标题
	private String title;
	//投票开始时间
	private Date begin_time;
	//投票结束时间
	private Date end_time;
	//创建时间
	private Date create_time;
	//修改时间
	private Date update_time;
	//状态
	private Vote_Status status;
	//投票类型
	private Vote_Type type;
	//限制类型(二进制数的（从右向左数）1:登录限制 2:ip次数限制 3:频率限制)，有的位置为1
	private int limit_type;
	//限制规则(json格式)
	private String limit_rule;
	//选项冗余字段
	private String option_mini;
	//创建人id
	private Long create_user_id;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getBegin_time() {
		return begin_time;
	}
	public void setBegin_time(Date begin_time) {
		this.begin_time = begin_time;
	}
	public void setBegin_time(String begin_time) {
		this.begin_time = DateUtils.parseDateFromString(begin_time);
	}
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = DateUtils.parseDateFromString(end_time);;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public Vote_Status getStatus() {
		return status;
	}
	public void setStatus(Vote_Status status) {
		this.status = status;
	}
	public Vote_Type getType() {
		return type;
	}
	public void setType(Vote_Type type) {
		this.type = type;
	}
	public String getOption_mini() {
		return option_mini;
	}
	public void setOption_mini(String option_mini) {
		this.option_mini = option_mini;
	}
	public Long getCreate_user_id() {
		return create_user_id;
	}
	public void setCreate_user_id(Long create_user_id) {
		this.create_user_id = create_user_id;
	}
	public String getLimit_rule() {
		return limit_rule;
	}
	public void setLimit_rule(String limit_rule) {
		this.limit_rule = limit_rule;
	}
	public int getLimit_type() {
		return limit_type;
	}
	public void setLimit_type(int limit_type) {
		this.limit_type = limit_type;
	}
    	
}
