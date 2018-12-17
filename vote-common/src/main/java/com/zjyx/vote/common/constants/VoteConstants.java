package com.zjyx.vote.common.constants;


public class VoteConstants {

	//默认每页展示条数
	public final static int DEFAULT_ONEPAGESIZE = 10;
	//密码盐值
	public final static String PASSWD_SALT = "U90KDFJ081C287PL";
	//session名
	public final static String USER_SESSION_NAME = "user";
	//后台session名
	public final static String ADMIN_USER_SESSION_NAME = "admin";
	//热门投票的时间限定(单位：分钟)
	public final static int HOT_VOTE_TIME = 24*60;
}
