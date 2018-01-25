package com.hexun.quant.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hexun.quant.utils.HttpUtils;
import com.hexun.quant.utils.PropertiesUtils;
import com.hexun.quant.vo.SelectStockVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hexun on 2017/11/14.
 */
@Service("diagnoseStockServiceImpl")
public class DiagnoseStockServiceImpl {
    //添加四海诊股的接口地址
    private final String diagnosestock_url = PropertiesUtils.getPropertiesValue("online_diagnosestock_url");//

    private static final Logger logger = org.slf4j.LoggerFactory
            .getLogger(DiagnoseStockServiceImpl.class);

    public String diagnoseStock(String market, String code) {
        String result = "";
        String geturl = diagnosestock_url;
        String params = "?formula_id=100547&ktype=5&market=" + market + "&code=" + code + "&last=1";
        logger.info("geturl=   " + geturl + params);
        geturl = geturl + params;
        String postresult = HttpUtils.sendGet(geturl, "utf-8");
        if (StringUtils.isNotEmpty(postresult)) {
            JSONArray arrayJson = JSONArray.parseArray(postresult);
            for (int i = 0; i < arrayJson.size(); i++) {
                JSONObject data_subobj = (JSONObject) arrayJson.get(i);
                result = data_subobj.getString("out");
            }
        }
        logger.info("result=   " + result);
        return result;
    }


    /**
     * 调取四海的接口 获取选股结果列表
     *
     * @param indexIds
     * @return
     * @throws Exception
     */
    public List<SelectStockVo> getSelectStockList(List<String> indexIds) throws Exception {
        String formula_ids = "";
        for (String formula_id : indexIds) {
            formula_ids += formula_id + ",";
        }
        formula_ids = formula_ids.substring(0, formula_ids.length() - 1);
        logger.info("量化选股 检索开始 start, 指标 fromulaids =" + formula_ids);
        List<SelectStockVo> list = new ArrayList<SelectStockVo>();//初始化结果集
        String geturl = PropertiesUtils.getPropertiesValue("online_select_stock") + "?formula_id=" + formula_ids + "&ktype=5";
        String postresult = HttpUtils.sendGet(geturl, "utf-8");
        if (StringUtils.isNotEmpty(postresult)) {
            list = analySelectResult(list, postresult);
        }
        logger.info("量化选股 检索开始 end, 指标 fromulaids =" + formula_ids);
        return list;
    }


    /**
     * 分析四海的选股接口返回值
     *
     * @param list
     * @param getResultJson
     * @return
     */
    public List<SelectStockVo> analySelectResult(List<SelectStockVo> list, String getResultJson) {
        JSONArray array = JSON.parseArray(getResultJson);
        logger.info("量化选股结果 json集合 array,size=  " + array.size());
        for (int i = 0; i < array.size(); i++) {
            JSONObject subObject = (JSONObject) array.get(i);
            JSONArray arraycodes = subObject.getJSONArray("codes");
            String formulaid = String.valueOf(subObject.getIntValue("formula_id"));
            logger.info(formulaid + " 指标对应的量化选股 选股结果  arraycodes=" + arraycodes.size());
            list = rebulidResult(list, subObject, arraycodes);
        }
        return list;
    }

    /**
     * 封装 选股结果
     *
     * @param subObject
     * @param arraycodes
     * @return
     */
    public List<SelectStockVo> rebulidResult(List<SelectStockVo> list, JSONObject subObject, JSONArray arraycodes) {

        for (int a = 0; a < arraycodes.size(); a++) {
            JSONObject codeObject = (JSONObject) arraycodes.get(a);
            SelectStockVo selectStockVo = new SelectStockVo();
            selectStockVo.setFormulaid(subObject.getIntValue("formula_id"));
            selectStockVo.setFormulaname(subObject.getString("formula_name"));
            selectStockVo.setPrice(codeObject.getDoubleValue("price"));
            selectStockVo.setStockcode(codeObject.getString("code"));
            selectStockVo.setStockname(codeObject.getString("name"));
            selectStockVo.setStockid(codeObject.getIntValue("id"));
            selectStockVo.setMarkePrefix(codeObject.getString("market"));
            list.add(selectStockVo);
        }
        return list;
    }

}
