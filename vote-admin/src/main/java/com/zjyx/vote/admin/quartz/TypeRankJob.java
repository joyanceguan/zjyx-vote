package com.zjyx.vote.admin.quartz;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.zjyx.vote.api.model.condition.VoteRankListCdt;
import com.zjyx.vote.api.model.condition.VoteTypeCdn;
import com.zjyx.vote.api.model.constants.RedisKey;
import com.zjyx.vote.api.model.persistence.Vote;
import com.zjyx.vote.api.model.persistence.VoteType;
import com.zjyx.vote.api.model.result.VoteResult;
import com.zjyx.vote.api.service.IVoteRecordService;
import com.zjyx.vote.api.service.IVoteService;
import com.zjyx.vote.api.service.IVoteTypeService;
import com.zjyx.vote.api.utils.VoteRecordUtils;
import com.zjyx.vote.common.enums.Error_Type;
import com.zjyx.vote.common.model.PageInfo;
import com.zjyx.vote.common.model.ReturnData;

/**
 * 根据投票记录算出每个类型排行前10放入redis中
 */
@Component
public class TypeRankJob {
	
	private static final Logger errorLog = LoggerFactory.getLogger("errorLog");

	@Resource
	IVoteTypeService voteTypeService;
	
	@Resource
	private ExecutorService typeRankExecutor;
	
	@Resource
	IVoteService voteService; 
	
	@Resource
	IVoteRecordService voteRecordService; 
	
	@Resource
	RedisTemplate<String, Object> redisTemplate;
	
	//每个类型算出前多少
	public final static int rank = 10;
	
	//统计多长时间内的排行(分钟为单位)
	public final static int period = 60*24*365;
	
	//发送redis失败重试次数
	public final static int retryTime = 3;
	
	//每1分钟执行一次同步redis
    @Scheduled(cron = "* 0/1 * * * ? ")
	public void typeRank(){
    	ReturnData<List<VoteType>> returnData = voteTypeService.selectAll();
    	if(returnData.getErrorType() == Error_Type.SUCCESS){
    		List<VoteType> list = returnData.getResultData();
    		for(VoteType voteType : list){
    			Integer typeId = voteType.getId();
    			//投票id和投票记录表对对号入座
    			Map<String,List<Long>> map = getRecordMap(typeId);
    			//算排行数据
    			List<VoteResult> rankedList = getVoteRank(map,typeId);
    			//组装数据发送redis
    			sendToRedis(rankedList,typeId);
    		}
    	}
    }
    
    /**
     * 查找该类型的投票表的所有数据，并拿到每个投票对应的投票记录表是哪个，组装map，map格式为key是投票记录分表名，value是分表对应的投票id集合
     * @param typeId
     * @return
     */
    private Map<String,List<Long>> getRecordMap(Integer typeId){
    	Map<String,List<Long>> map = null;
    	int currentPage = 1;
    	int onePageSize = 100;
		VoteTypeCdn condition = new VoteTypeCdn();
		condition.setCurrentPage(currentPage);
		condition.setOnePageSize(onePageSize);
		condition.setTypeId(typeId);
		PageInfo<Vote> pageinfo = voteService.typeList(condition);
		List<Vote> voteList = pageinfo.getObjects();
		if(voteList != null && !voteList.isEmpty()){
			map = new HashMap<String,List<Long>>();
			while(voteList != null && !voteList.isEmpty()){
				for(Vote vote:voteList){
					//获取所在的分表
					String tableName = VoteRecordUtils.getRecordTableName(vote);
					//根据分表插入id列表
					if(map.containsKey(tableName)){
						List<Long> voteIds = map.get(tableName);
						voteIds.add(vote.getId());
					}else{
						List<Long> voteIds = new LinkedList<Long>();
						voteIds.add(vote.getId());
						map.put(tableName, voteIds);
					}
				}
				condition.setCurrentPage(++currentPage);
			    pageinfo = voteService.typeList(condition);
				voteList = pageinfo.getObjects();
			}
		}
		return map;
    }
    
    /**
     * 投票记录表排序（多线程搜索投票记录）
     * @param map
     * @param typeId
     */
    private List<VoteResult> getVoteRank(Map<String,List<Long>> map,Integer typeId){
        final List<VoteResult> rankList = new LinkedList<VoteResult>();
    	//算出排行
		if(!map.isEmpty()){
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.MINUTE, 0 - period);
			int size = map.keySet().size();//记录表个数
			CountDownLatch latch = new CountDownLatch(size);
		    //将每个记录表前n名拿到，放到rankList,在排序rankList 获取到最终前端n名
			for(String tableName : map.keySet()){
				//每个分表开启一个线程，CyclicBarrier控制是否都查完返回
				typeRankExecutor.execute(new Runnable() {
					@Override
					public void run() {
						try {
							VoteRankListCdt cdt = new VoteRankListCdt();
							cdt.setCurrentPage(1);
							cdt.setOnePageSize(rank);
							cdt.setBeginTime(calendar.getTime());
							cdt.setTable_name(tableName);
							cdt.setVoteIds(map.get(tableName));
							PageInfo<VoteResult> voteResultPageInfo = voteRecordService.rankList(cdt);
							List<VoteResult> voteResultList = voteResultPageInfo.getObjects();
							if(voteResultList != null && !voteResultList.isEmpty()){
								rankList.addAll(voteResultList);
							}
							latch.countDown();
						}catch (Exception e) {
							errorLog.error("select mysql exception", e);
						}
					}
				});
			}
			try {
				latch.await();
				List<VoteResult> rankedList= sort(rankList,typeId);
				return rankedList;
			} catch (Exception e) {
				//日志
	    		errorLog.error("save redis exception", e);
			}
		}
		return rankList;
    }
    
    /**
     * 排序并获取最终的列表
     * @param rankList
     * @param typeId
     */
    private List<VoteResult> sort(List<VoteResult> rankList,Integer typeId){
    	//排序rankList
		Collections.sort(rankList, new Comparator<VoteResult>(){
	    	@Override
	    	public int compare(VoteResult o1, VoteResult o2) {
	    		if(o1.getCount() > o2.getCount()){
	    			return -1;
	    		}else
	    			return 1;
	    }});
		if(rank < rankList.size()){
			rankList = rankList.subList(0, rank);
		}
		return rankList;
    }
    
    /**
     * 发送redis并带有重试机制(重试几次失败也没关系，仍然使用以前的排行)
     * @param rankedList
     * @param typeId
     */
    private void sendToRedis(List<VoteResult> rankedList,Integer typeId){
    	if(rankedList == null || rankedList.isEmpty())
    		return;
    	int index = 0;
    	boolean isSuccess = false;
        while(index < retryTime && !isSuccess){
        	try{
        	    redisTemplate.opsForValue().getAndSet(RedisKey.TYPE_PREFIX + typeId, JSON.toJSONString(rankedList));
        	    isSuccess = true;
        	}catch(Exception e){
        		//日志
        		errorLog.error("save redis exception", e);
        		index++;
        	}
        }
    }
    
}
