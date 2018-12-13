package com.zjyx.vote.api.service;

import com.zjyx.vote.api.condition.UserCdtn;
import com.zjyx.vote.api.persistence.User;
import com.zjyx.vote.common.model.PageInfo;
import com.zjyx.vote.common.model.ReturnData;

public interface IUserService {

	public ReturnData<User> selectById(Long id);
	
	public ReturnData<Integer> update(User user);
	
	public PageInfo<User> list(UserCdtn userCdtn);
}
