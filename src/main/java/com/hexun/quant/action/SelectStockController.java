package com.hexun.quant.action;

import com.hexun.quant.service.DiagnoseStockServiceImpl;
import com.hexun.quant.utils.ModelAndViewUtil;
import com.hexun.quant.vo.SelectStockVo;
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
@RequestMapping("/select")
public class SelectStockController {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SelectStockController.class);

    @Resource
    private DiagnoseStockServiceImpl diagnoseStockServiceImpl;

    /**
     * 2.调取四海的指标选股结果 接口
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getSelectStockResultByFormulaId/{formulaids}/{pageId}/{pageSize}")
    public ModelAndView getSelectResultByFormulaId(Model model,HttpServletRequest request,
                                                   HttpServletResponse response,
                                                   @PathVariable("formulaids")String formulaids,
                                                   @PathVariable("pageId")String pageId,
                                                   @PathVariable("pageSize")String pageSize) {
        try{
            int start = (Integer.parseInt(pageId)-1)*Integer.parseInt(pageSize);
            int end = start + Integer.parseInt(pageSize);
            Map<String ,Object> map = new HashMap<String ,Object>();
            List<String> lIntegers = new ArrayList();
            if ((formulaids == null) || (formulaids.equals(""))) {
                logger.error("选股指标为空");
                return ModelAndViewUtil.Json_error("选股指标为空");
            } else {
                if (formulaids.indexOf(",") > -1) {
                    String[] d = formulaids.split(",");
                    int i = 0;
                    for (int size = d.length; i < size; i++) {
                        lIntegers.add(d[i]);
                    }
                } else {
                    lIntegers.add(formulaids);
                }
            }
            List<SelectStockVo> selectstocklist = diagnoseStockServiceImpl.getSelectStockList(lIntegers);
            map.put("totalnum", selectstocklist.size());//选股总个数
            /**分页开始**/
            if(start>selectstocklist.size()){
                map.put("selectstocklist",new ArrayList<SelectStockVo>());
                return ModelAndViewUtil.Json_ok(map);
            }
            if(selectstocklist.size()<=end){
                end = selectstocklist.size();
            }
            selectstocklist = selectstocklist.subList(start,end);
            /**分页结束**/
            map.put("selectstocklist", selectstocklist);
            return ModelAndViewUtil.Json_ok(map);
        }catch(Exception e){
            logger.error("获取选股结果列表信息异常,"+e.getMessage(),e);
            return ModelAndViewUtil.Json_error("获取选股结果列表信息异常");
        }
    }
}
