package com.hexun.quant.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hexun.quant.service.QuantManagerServiceImpl;
import com.hexun.quant.utils.ModelAndViewUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hexun on 2017/11/14.
 */
@Controller
@RequestMapping("/manager")
public class QuantManagerController {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(QuantManagerController.class);

    @Resource
    private QuantManagerServiceImpl quantManagerServiceImpl;
    /**
     * 封装 “添加策略接口”
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/addAlgorithm")
    public ModelAndView addAlgorithm(Model model, HttpServletRequest request, HttpServletResponse response) {

        String userId = request.getParameter("userId");
        String algorithmName = request.getParameter("algorithmName");
        String code = request.getParameter("code");
        try {
            if(!StringUtils.isNotEmpty(userId)) {
                return ModelAndViewUtil.Json_error("userId参数为空");
            }
            if(!StringUtils.isNotEmpty(algorithmName)){
                return ModelAndViewUtil.Json_error("algorithmName参数为空");
            }
            if(!StringUtils.isNotEmpty(code)){
                return ModelAndViewUtil.Json_error("code参数为空");
            }
            JSONObject jsonparam = new JSONObject();
            jsonparam.put("body",code);
            String result = quantManagerServiceImpl.addAlgorithm(userId,algorithmName,jsonparam);
            JSONObject jsonObject = JSON.parseObject(result);
            return ModelAndViewUtil.Json_ok("out", jsonObject);
        } catch (Exception e) {
            logger.error("封装 “添加策略接口" + e.getMessage(), e);
            return ModelAndViewUtil.Json_error("封装添加策略接口异常");
        }
    }
}
