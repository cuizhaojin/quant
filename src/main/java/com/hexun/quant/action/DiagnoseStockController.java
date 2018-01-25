package com.hexun.quant.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hexun.quant.service.DiagnoseStockServiceImpl;
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
@RequestMapping("/diagnose")
public class DiagnoseStockController {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DiagnoseStockController.class);

    @Resource
    private DiagnoseStockServiceImpl diagnoseStockServiceImpl;
    /**
     *  //http://10.0.200.59:9600/tech?formula_id=100547&ktype=5&code=300077&last=1
     * 封装 诊股 接口 返回json数据，前端页面 根据返回参数 选择文案 显示
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getDiagnoseStockResult")
    public ModelAndView getDiagnoseStockResult(Model model, HttpServletRequest request, HttpServletResponse response) {

        String stockcode = request.getParameter("stockcode");
        String market = request.getParameter("market");
        try {
            if(!StringUtils.isNotEmpty(stockcode)) {
                return ModelAndViewUtil.Json_error("stockcode参数为空");
            }
            if(!StringUtils.isNotEmpty(market)){
                return ModelAndViewUtil.Json_error("market参数为空");
            }
            if("SSE".equals(market)||"SZSE".equals(market)){
                String result = diagnoseStockServiceImpl.diagnoseStock(market,stockcode);
                JSONObject jsonObject = JSON.parseObject(result);
                return ModelAndViewUtil.Json_ok("out",jsonObject);
            } else{
                return ModelAndViewUtil.Json_error("market参数异常");
            }
        } catch (Exception e) {
            logger.error(" 诊股接口返回json数据异常," + e.getMessage(), e);
            return ModelAndViewUtil.Json_error("诊股接口返回json数据异常");
        }
    }
}
