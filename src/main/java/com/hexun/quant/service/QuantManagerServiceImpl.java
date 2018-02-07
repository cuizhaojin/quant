package com.hexun.quant.service;

import com.alibaba.fastjson.JSONObject;
import com.hexun.quant.utils.HttpUtils;
import com.hexun.quant.utils.PropertiesUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created by hexun on 2018/1/30.
 */
@Service("quantManagerServiceImpl")
public class QuantManagerServiceImpl {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(QuantManagerServiceImpl.class);

    //添加回测策略url 接口地址
    private final String add_algorithm_url = PropertiesUtils.getPropertiesValue("uat_add_algorithm");
    //根据策略Id 查询策略详情 接口地址
    private final String search_algorithm_url = PropertiesUtils.getPropertiesValue("uat_search_algorithm");
    //修改策略url 接口地址
    private final String modify_algorithm_url = PropertiesUtils.getPropertiesValue("uat_modify_algorithm");
    //删除策略url 接口地址
    private final String delete_algorithm_url = PropertiesUtils.getPropertiesValue("uat_delete_algorithm");
    //获取策略执行的任务task_id
    private final String get_tashid_url = PropertiesUtils.getPropertiesValue("uat_get_taskid");
    //执行策略url 接口地址
    private final String execute_task_url= PropertiesUtils.getPropertiesValue("uat_execute_task");
    //查询策略执行结果 接口地址
    private final String get_executeresult_url = PropertiesUtils.getPropertiesValue("uat_execute_task_result");


    /**
     * 添加策略
     * @param userId
     * @param algorithmName
     * @param param
     * @return
     */
    public String apiForAddAlgorithm(String userId,String algorithmName,JSONObject param) {
        String result = "";
        String geturl = add_algorithm_url;
        String params = "?algorithm_name="+algorithmName+"&user_id=" + userId ;
        logger.info("geturl=   " + geturl + params);
        geturl = geturl + params;
        String postresult = HttpUtils.sendPostJson(param, geturl, "UTF-8");
        if (StringUtils.isNotEmpty(postresult)) {
            result = postresult;
        }
        logger.info("result=" + result);
        return result;
    }

    /**
     * 查询策略
     * @param algorithmId
     * @return
     */
    public String apiForGetAlgorithmById(String algorithmId){
        String result = "";
        String geturl = search_algorithm_url;
        String params = "?algorithm_id="+algorithmId;
        logger.info("geturl=   " + geturl + params);
        geturl = geturl + params;
        String postresult = HttpUtils.sendGet(geturl,"UTF-8");
        if (StringUtils.isNotEmpty(postresult)) {
            result = postresult;
        }
        logger.info("result=" + result);
        return result;
    }

    /**
     * 修改策略
     * @param userId
     * @param algorithmName
     * @param param
     * @return
     */
    public String apiForModifyAlgorithm(String userId,String algorithmId,String algorithmName,JSONObject param){
        String result = "";
        String geturl = modify_algorithm_url;
        String params = "?algorithm_id="+algorithmId+"&algorithm_name="+algorithmName+"&user_id=" + userId ;
        logger.info("geturl=   " + geturl + params);
        geturl = geturl + params;
        String postresult = HttpUtils.sendPostJson(param, geturl, "UTF-8");
        if (StringUtils.isNotEmpty(postresult)) {
            result = postresult;
        }
        logger.info("result=" + result);
        return result;
    }

    /**
     * 删除策略
     * @param userId
     * @param algorithmId
     * @return
     */
    public String apiForDeleteAlgorithm(String userId,String algorithmId){
        String result = "";
        String geturl = delete_algorithm_url;
        String params = "?algorithm_id="+algorithmId+"&user_id="+userId ;
        logger.info("geturl=   " + geturl + params);
        geturl = geturl + params;
        String postresult = HttpUtils.sendGet(geturl,"UTF-8");
        if (StringUtils.isNotEmpty(postresult)) {
            result = postresult;
        }
        logger.info("result=" + result);
        return result;
    }

    /**
     * 获取taskId
     * @param userId
     * @return
     */
    public String apiForGetTaskId(String userId){
        String result = "";
        String geturl = get_tashid_url;
        String params = "?user_id="+userId ;
        logger.info("geturl=   " + geturl + params);
        geturl = geturl + params;
        String postresult = HttpUtils.sendGet(geturl,"UTF-8");
        if (StringUtils.isNotEmpty(postresult)) {
            result = postresult;
        }
        logger.info("result=" + result);
        return result;
    }

    /**
     * 执行 策略
     * @param userId
     * @param algorithmId
     * @param param
     * @return
     */
    public String apiForExecuteTask(String userId,String taskId,String algorithmId,JSONObject param){
        String result = "";
        String geturl = execute_task_url;
        String params = "?user_id="+userId+"&task_id="+taskId+"&algorithm_id="+algorithmId;
        logger.info("geturl=   " + geturl + params);
        geturl = geturl + params;
        String postresult = HttpUtils.sendPostJson(param, geturl, "UTF-8");
        if (StringUtils.isNotEmpty(postresult)) {
            result = postresult;
        }
        logger.info("result=" + result);
        return result;
    }


    /**
     * 获取策略执行结果
     * @param taskId
     * @param resultType
     * @return
     */
    public String apiForGetExecuteTaskResult(String taskId,String resultType){
        String result = "";
        String geturl = get_executeresult_url;
        String params = "?task_id="+taskId+"&result_type="+resultType;
        logger.info("geturl=   " + geturl + params);
        geturl = geturl + params;
        String postresult = HttpUtils.sendGet(geturl,"UTF-8");
        if (StringUtils.isNotEmpty(postresult)) {
            result = postresult;
        }
        logger.info("result=" + result);
        return result;
    }

    /**
     * 根据用户id 返回该用户所有的策略
     * @param userId
     * @return
     */
    public String apiForGetAlgorithmByUserId(String userId){
        String result = "";
        String geturl = search_algorithm_url;
        String params = "?user_id="+userId;
        logger.info("geturl=   " + geturl + params);
        geturl = geturl + params;
        String postresult = HttpUtils.sendGet(geturl,"UTF-8");
        if (StringUtils.isNotEmpty(postresult)) {
            result = postresult;
        }
        logger.info("result=" + result);
        return result;
    }
}
