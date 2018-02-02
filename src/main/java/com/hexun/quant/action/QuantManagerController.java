package com.hexun.quant.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hexun.quant.service.QuantManagerServiceImpl;
import com.hexun.quant.utils.ModelAndViewUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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

    /***
     * 跳转到编辑策略页面
     * @param model
     * @param request
     * @param response
     * @param algorithmId
     * @return
     */
    @RequestMapping(value = "/edit/{algorithmId}")
    public ModelAndView toEdit(Model model,
                               HttpServletRequest request,
                               HttpServletResponse response,
                               @PathVariable("algorithmId")String algorithmId) {

        String jsonstr = quantManagerServiceImpl.apiForGetAlgorithmById(algorithmId);

        //判断json str






        model.addAttribute("data",jsonstr);
        return ModelAndViewUtil.Jsp("WEB-INF/view/edit");
    }

    /**
     * 跳转到创建策略页面
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/new")
    public ModelAndView toAdd(Model model, HttpServletRequest request, HttpServletResponse response) {

        return ModelAndViewUtil.Jsp("WEB-INF/view/edit");
    }


    /**
     * 跳转到我的策略列表页面
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/list")
    public ModelAndView toMyAlgorithmlist(Model model, HttpServletRequest request, HttpServletResponse response) {

        return ModelAndViewUtil.Jsp("WEB-INF/view/algorithmlist");
    }


    /**
     * 跳转到收益图表页面
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/benefit")
    public ModelAndView toBenefitCharts(Model model, HttpServletRequest request, HttpServletResponse response) {

        return ModelAndViewUtil.Jsp("WEB-INF/view/charts");
    }


    /**
     * 跳转到回测详情页面
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/testdetail")
    public ModelAndView toTestDetail(Model model, HttpServletRequest request, HttpServletResponse response) {

        return ModelAndViewUtil.Jsp("WEB-INF/view/testdetail");
    }

    /**
     * 封装 "添加策略接口"
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
            String result = quantManagerServiceImpl.apiForAddAlgorithm(userId,algorithmName,jsonparam);
            JSONObject jsonObject = JSON.parseObject(result);
            return ModelAndViewUtil.Json_ok("out", jsonObject);
        } catch (Exception e) {
            logger.error("添加策略接口出现异常" + e.getMessage(), e);
            return ModelAndViewUtil.Json_error("添加策略接口异常");
        }
    }



    /**
     * 封装 "修改策略接口"
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/modifyAlgorithm")
    public ModelAndView modifyAlgorithm(Model model, HttpServletRequest request, HttpServletResponse response) {

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
            String result = quantManagerServiceImpl.apiForModifyAlgorithm(userId,algorithmName,jsonparam);
            JSONObject jsonObject = JSON.parseObject(result);
            return ModelAndViewUtil.Json_ok("out", jsonObject);
        } catch (Exception e) {
            logger.error("修改策略接口出现异常" + e.getMessage(), e);
            return ModelAndViewUtil.Json_error("修改策略接口异常");
        }
    }

    /**
     * 封装 "删除策略接口"
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/deleteAlgorithm")
    public ModelAndView deleteAlgorithm(Model model, HttpServletRequest request, HttpServletResponse response) {

        String userId = request.getParameter("userId");
        String algorithmId = request.getParameter("algorithmId");
        try {
            if(!StringUtils.isNotEmpty(algorithmId)){
                return ModelAndViewUtil.Json_error("algorithmName参数为空");
            }
            String result = quantManagerServiceImpl.apiForDeleteAlgorithm(userId,algorithmId);
            JSONObject jsonObject = JSON.parseObject(result);
            return ModelAndViewUtil.Json_ok("out", jsonObject);
        } catch (Exception e) {
            logger.error("删除策略接口出现异常" + e.getMessage(), e);
            return ModelAndViewUtil.Json_error("删除策略接口异常");
        }
    }

}



