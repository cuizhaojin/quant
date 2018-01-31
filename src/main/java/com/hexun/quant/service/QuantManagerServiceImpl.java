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

    public String apiForAddAlgorithm(String userId,String algorithmName,JSONObject param) {
        String result = "";
        String geturl = add_algorithm_url;
        String params = "?algorithm_name="+algorithmName+"&user_id=" + userId ;
        logger.info("geturl=   " + geturl + params);
        geturl = geturl + params;
        String postresult = HttpUtils.sendPostJson(param, geturl, "UTF-8");
        if (StringUtils.isNotEmpty(postresult)) {
            /*JSONArray arrayJson = JSONArray.parseArray(postresult);
            for (int i = 0; i < arrayJson.size(); i++) {
                JSONObject data_subobj = (JSONObject) arrayJson.get(i);
                result = data_subobj.getString("out");
            }*/
        }
        result = postresult;
        logger.info("result=" + postresult);
        return result;
    }

}
