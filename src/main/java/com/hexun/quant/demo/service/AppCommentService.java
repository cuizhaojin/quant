package com.hexun.quant.demo.service;

import com.hexun.quant.utils.GridDataModel;
import com.hexun.quant.utils.PageUtil;
import com.hexun.quant.vo.AppComment;
import com.hexun.quant.vo.UserZoneAppComment;

/**
 * Created by hexun on 2016/12/19.
 */
public interface AppCommentService {

    /**
     * 获取小程序评论列表页
     * @param userZoneAppComment
     * @param page
     * @return
     */
    GridDataModel<UserZoneAppComment> getCommentList(UserZoneAppComment userZoneAppComment,PageUtil page);

    /**
     * 评论入库
     * @param appComment
     */
    void addAppComment(AppComment appComment);

    /**
     * 获取userZoneAppComment的数量根据appid 和App_version_id
     * @param userZoneAppComment
     * @return
     */
    int getCommentCount(UserZoneAppComment userZoneAppComment);

    /**
     * 评论数量数量根据appid 和App_version_id
     * @param appid
     * @param app_version
     * @return
     */
    int getCommentCountNoraml(String appid,String app_version);

	/**
	 * 根据userid获取他所上传的小程序的所有的应的评论总和
	 * @param userid
	 * @return
	 */
	int getCommentTotalNum(String userid);


    /**
     * 用户管理页面 获取小程序评论列表页
     * @param userid
     * @param page
     * @return
     */
    GridDataModel<UserZoneAppComment> getCommentListUserZone(String userid,PageUtil page);



}
