package com.hexun.quant.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hexun.quant.service.QuantManagerServiceImpl;
import com.hexun.quant.utils.ModelAndViewUtil;
import com.hexun.quant.vo.AlgorithmMain;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        try{
            //是否分页
            boolean ispage = false;
            int total =0;
            //获取我的策略列表
            String userId = "123456";
            String userAlgorithListStr = quantManagerServiceImpl.apiForGetAlgorithmByUserId(userId);
            JSONObject algorithListJsonObject = JSON.parseObject(userAlgorithListStr);
            //判断json userAlgorithListStr
            if("0".equals(algorithListJsonObject.get("code").toString())) {
                List<AlgorithmMain> algorithmMainList = quantManagerServiceImpl.convertJavaBean(algorithListJsonObject);
                total = algorithmMainList.size();
                if(total>7){
                    ispage = true;
                }
            }
            model.addAttribute("ispage", ispage); //是否分页
            model.addAttribute("total",total);//总条数
            model.addAttribute("currentPage",1);
            model.addAttribute("totalpage", totalpage(total,7));//总页数
            //获取策略详情
            String jsonstr = quantManagerServiceImpl.apiForGetAlgorithmById(algorithmId);
            JSONObject jsonObject = JSON.parseObject(jsonstr);
            //判断json jsonstr
            if("0".equals(jsonObject.get("code").toString())){
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                //判断返回值中是否有策略代码
                if(jsonArray.size()<1){
                    return ModelAndViewUtil.Jsp("error/404");
                }
                model.addAttribute("algorithmId",algorithmId);
                model.addAttribute("pageflag","edit");
                model.addAttribute("data",jsonObject);
            }else{
                //接口反馈code非 0状态码
                return ModelAndViewUtil.Jsp("error/error");
            }

        }catch (Exception ex){
            logger.error("编辑策略页面跳转异常" + ex.getMessage(), ex);
            return ModelAndViewUtil.Jsp("error/error");
        }
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
        //是否分页
        boolean ispage = false;
        int total =0;
        //获取我的策略列表
        String userId = "123456";
        String userAlgorithListStr = quantManagerServiceImpl.apiForGetAlgorithmByUserId(userId);
        JSONObject algorithListJsonObject = JSON.parseObject(userAlgorithListStr);
        //判断json userAlgorithListStr
        if("0".equals(algorithListJsonObject.get("code").toString())) {
            List<AlgorithmMain> algorithmMainList = quantManagerServiceImpl.convertJavaBean(algorithListJsonObject);
            total = algorithmMainList.size();
            if(total>7){
                ispage = true;
            }
        }
        model.addAttribute("ispage", ispage); //是否分页
        model.addAttribute("total",total);//总条数
        model.addAttribute("currentPage",1);
        model.addAttribute("totalpage", totalpage(total,7));//总页数
        model.addAttribute("pageflag","new");
        model.addAttribute("data",false);
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

        try{
            String userId = "123456";
            String jsonstr = quantManagerServiceImpl.apiForGetAlgorithmByUserId(userId);
            JSONObject jsonObject = JSON.parseObject(jsonstr);
            //判断json str
            if("0".equals(jsonObject.get("code").toString())){
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                //判断返回值中是否有策略代码
                if(jsonArray.size()<1){
                    return ModelAndViewUtil.Jsp("error/404");
                }
                model.addAttribute("algorithmList",jsonObject);
            }else{
                //接口反馈code非 0状态码
                return ModelAndViewUtil.Jsp("error/error");
            }

        }catch (Exception ex){
            logger.error("我的策略列表页面跳转异常" + ex.getMessage(), ex);
            return ModelAndViewUtil.Jsp("error/error");
        }
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
            algorithmName = java.net.URLEncoder.encode(algorithmName,"utf-8");
            String result = quantManagerServiceImpl.apiForAddAlgorithm(userId,algorithmName,jsonparam);
            JSONObject jsonObject = JSON.parseObject(result);
            if("0".equals(jsonObject.get("code").toString())){
                return ModelAndViewUtil.Json_ok(jsonObject);
            }else{
                return ModelAndViewUtil.Json_error(jsonObject); //添加策略失败，将异常信息前抛出，方便页面 console.info
            }
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
        String algorithmId = request.getParameter("algorithmId");
        String algorithmName = request.getParameter("algorithmName");
        String code = request.getParameter("code");
        try {
            if(!StringUtils.isNotEmpty(algorithmId)) {
                return ModelAndViewUtil.Json_error("algorithmId参数为空");
            }
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
            algorithmName = java.net.URLEncoder.encode(algorithmName,"utf-8");
            String result = quantManagerServiceImpl.apiForModifyAlgorithm(userId,algorithmId,algorithmName,jsonparam);
            JSONObject jsonObject = JSON.parseObject(result);
            if("0".equals(jsonObject.get("code").toString())){
                return ModelAndViewUtil.Json_ok("修改策略成功");
            }else{
                return ModelAndViewUtil.Json_error(jsonObject); //修改策略失败，将异常信息前抛出，方便页面 console.info
            }
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
                return ModelAndViewUtil.Json_error("algorithmId参数为空");
            }
            String result = quantManagerServiceImpl.apiForDeleteAlgorithm(userId,algorithmId);
            JSONObject jsonObject = JSON.parseObject(result);
            if("0".equals(jsonObject.get("code").toString())){
                return ModelAndViewUtil.Json_ok("删除策略成功");
            }else{
                return ModelAndViewUtil.Json_error(jsonObject); //删除策略失败，将异常信息前抛出，方便页面 console.info
            }
        } catch (Exception e) {
            logger.error("删除策略接口出现异常" + e.getMessage(), e);
            return ModelAndViewUtil.Json_error("删除策略接口异常");
        }
    }

    /**
     * 执行 策略
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/executeAlgorithm")
    public ModelAndView executeAlgorithm(Model model, HttpServletRequest request, HttpServletResponse response) {

        String userId = request.getParameter("userId");
        String algorithmId = request.getParameter("algorithmId");
        String code = request.getParameter("code");
        JSONObject jsonparam = new JSONObject();
        jsonparam.put("body",code);
        try {
            if(!StringUtils.isNotEmpty(code)){
                return ModelAndViewUtil.Json_error("code参数为空");
            }
            if(!StringUtils.isNotEmpty(algorithmId)){
                return ModelAndViewUtil.Json_error("algorithmId参数为空");
            }
            //分配taskid 开始
            logger.info("策略引擎分配taskid start---------");
            String result = quantManagerServiceImpl.apiForGetTaskId(userId);
            JSONObject gettask_jsonObject= JSON.parseObject(result);
            if("0".equals(gettask_jsonObject.get("code").toString())){
                String taskId = gettask_jsonObject.get("task_id").toString();
                logger.info("分配 taskid success; taskid:"+taskId);
                String execute_result = quantManagerServiceImpl.apiForExecuteTask(userId,taskId,algorithmId,jsonparam);
                JSONObject jsonObject = JSON.parseObject(execute_result);
                if("0".equals(jsonObject.get("code").toString())){
                    return ModelAndViewUtil.Json_ok("taskid",taskId);
                }else{
                    return ModelAndViewUtil.Json_error(jsonObject);
                }
            }else{
                logger.info("获取taskid 失败 reason:"+gettask_jsonObject.get("info"));
                return ModelAndViewUtil.Json_error(gettask_jsonObject);
            }
        } catch (Exception e) {
            logger.error("执行策略接口出现异常" + e.getMessage(), e);
            return ModelAndViewUtil.Json_error("执行策略接口异常");
        }
    }

    /**
     * 查询策略执行结果  分类型
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/selectExecuteResult")
    public ModelAndView selectExecuteResult(Model model, HttpServletRequest request, HttpServletResponse response) {

        String taskId = request.getParameter("taskId");
        String resultType = request.getParameter("resultType");
        try {
            if(!StringUtils.isNotEmpty(taskId)){
                return ModelAndViewUtil.Json_error("taskId参数为空");
            }
            if(!StringUtils.isNotEmpty(resultType)){
                return ModelAndViewUtil.Json_error("resultType参数为空");
            }
            String execute_result = quantManagerServiceImpl.apiForGetExecuteTaskResult(taskId,resultType);
            JSONObject jsonObject = JSON.parseObject(execute_result);
            if("0".equals(jsonObject.get("code").toString())){
                return ModelAndViewUtil.Json_ok(jsonObject);
            }else{
                return ModelAndViewUtil.Json_error(jsonObject);
            }
        } catch (Exception e) {
            logger.error("查询策略结果接口出现异常" + e.getMessage(), e);
            return ModelAndViewUtil.Json_error("查询策略结果接口异常");
        }
    }

    /**
     * 获取我的策略列表接口
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getAlgorithmList")
    public ModelAndView getAlgorithmList(Model model,HttpServletRequest request,
                                                   HttpServletResponse response) {
        try{
            String pageId = request.getParameter("pageId");
            String pageSize= request.getParameter("pageSize");
            //获取我的策略列表
            String userId = "123456";//user_id 默认写死 123456
            int start = (Integer.parseInt(pageId)-1)*Integer.parseInt(pageSize);
            int end = start + Integer.parseInt(pageSize);
            Map<String ,Object> map = new HashMap<String ,Object>();
            String userAlgorithListStr = quantManagerServiceImpl.apiForGetAlgorithmByUserId(userId);
            JSONObject jsonObject = JSON.parseObject(userAlgorithListStr);
            List<AlgorithmMain> algorithmMainList = quantManagerServiceImpl.convertJavaBean(jsonObject);
            int total = algorithmMainList.size();
            /**分页开始**/
            if(start>total){
                map.put("algorithmMainList",new ArrayList<AlgorithmMain>());
                return ModelAndViewUtil.Json_ok(map);
            }
            if(total<=end){
                end = total;
            }
            algorithmMainList = algorithmMainList.subList(start,end);
            /**分页结束**/
            map.put("algorithmMainList", algorithmMainList);
            return ModelAndViewUtil.Json_ok(map);
        }catch(Exception e){
            logger.error("获取我的策略列表信息异常,"+e.getMessage(),e);
            return ModelAndViewUtil.Json_error("获取我的策略列表信息异常");
        }
    }

    public int totalpage(int recordCount,int pageSize) {
        int size = recordCount / pageSize;//总条数/每页显示的条数=总页数
        int mod = recordCount % pageSize;//最后一页的条数
        if (mod != 0)
            size++;
        return recordCount == 0 ? 1 : size;
    }

}



