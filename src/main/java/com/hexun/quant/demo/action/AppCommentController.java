package com.hexun.quant.demo.action;

import com.hexun.quant.demo.service.AppCommentService;
import com.hexun.quant.utils.GridDataModel;
import com.hexun.quant.utils.ModelAndViewUtil;
import com.hexun.quant.utils.PageUtil;
import com.hexun.quant.vo.AppComment;
import com.hexun.quant.vo.UserZoneAppComment;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
@RequestMapping("/demos")
public class AppCommentController {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(AppCommentController.class);

	@Resource
	private AppCommentService appCommentServiceImpl;

	/**
	 * app_index 页面根据appid获取评论列表集合
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getAppCommentList")
	public ModelAndView getAppCommentList(Model model,HttpServletRequest request, HttpServletResponse response) {

		String appid = request.getParameter("appid");
		String pageId = request.getParameter("pageId");
		String app_version = request.getParameter("app_version");
		UserZoneAppComment userZoneAppComment = new UserZoneAppComment();
        userZoneAppComment.setApp_id(appid);
        userZoneAppComment.setApp_version_id(app_version);
		PageUtil page = new PageUtil(request);
		if(pageId!=null){
			page.setBeginRow((Integer.parseInt(pageId)-1)*10);
			page.setEndRow(10);
		}
		try{
			if(StringUtils.isNotEmpty(appid)) {
				GridDataModel<UserZoneAppComment> grid = appCommentServiceImpl.getCommentList(userZoneAppComment,page);
                model.addAttribute("totalnum",grid.getTotal());
                model.addAttribute("startflag",page.getBeginRow());
				model.addAttribute("code","success");
				model.addAttribute("commentlist",grid.getRows());
				return ModelAndViewUtil.Jsp("commons/commentList");
			}else{
				model.addAttribute("code","error");
				model.addAttribute("commentlist",null);
				return ModelAndViewUtil.Jsp("commons/commentList");
			}
		}catch(Exception e){
			logger.error("获取app评论列表信息异常,"+e.getMessage(),e);
			model.addAttribute("code","error");
			model.addAttribute("commentlist",null);
			return ModelAndViewUtil.Jsp("commons/commentList");
		}
	}
	
	/**
	 * 插入评论
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addAppComment")
	public ModelAndView addAppComment(Model model,HttpServletRequest request, HttpServletResponse response) {

		String appid = request.getParameter("appid");
        String id = request.getParameter("id");
		String app_version = request.getParameter("app_version");
		String rank_value = request.getParameter("rank_value");
		String commentbody = request.getParameter("commentbody");
		AppComment appComment = new AppComment();
		appComment.setApp_id(appid);
		appComment.setApp_version_id(app_version);
		appComment.setCreate_date(new Date());
		appComment.setRank_value(rank_value);
		appComment.setCommentbody(commentbody);
		appComment.setStatus(1);

		logger.info("评论入库喽! appid={}",appid);
		try{
			if(StringUtils.isNotEmpty(appid)&&StringUtils.isNotEmpty(app_version)) {
				appCommentServiceImpl.addAppComment(appComment);
				return ModelAndViewUtil.Json_ok("提交成功");
			}else{
				return ModelAndViewUtil.Json_error("提交失败");
			}
		}catch(Exception e){
			logger.error("评论入库失败,"+e.getMessage(),e);
			return ModelAndViewUtil.Json_error("提交失败");
		}
	}
}

