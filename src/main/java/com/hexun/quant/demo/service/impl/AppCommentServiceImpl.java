package com.hexun.quant.demo.service.impl;

import com.hexun.quant.demo.dao.AppCommentDao;
import com.hexun.quant.demo.service.AppCommentService;
import com.hexun.quant.utils.GridDataModel;
import com.hexun.quant.utils.PageUtil;
import com.hexun.quant.vo.AppComment;
import com.hexun.quant.vo.UserZoneAppComment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hexun on 2016/12/19.
 */
@Service("appCommentServiceImpl")
public class AppCommentServiceImpl implements AppCommentService {
    @Resource
    private AppCommentDao appCommentDaoImpl;
    @Override
    public GridDataModel<UserZoneAppComment> getCommentList(UserZoneAppComment userZoneAppComment, PageUtil page) {

        GridDataModel<UserZoneAppComment> commentList = new GridDataModel<UserZoneAppComment>();

        commentList.setRows(appCommentDaoImpl.getCommentPageList(userZoneAppComment, page));
        commentList.setTotal(appCommentDaoImpl.getCommentPageListCount(userZoneAppComment));

        return commentList;
    }

    @Override
    public void addAppComment(AppComment appComment) {
        appCommentDaoImpl.addAppComment(appComment);
    }

    @Override
    public int getCommentCount(UserZoneAppComment userZoneAppComment) {
        return appCommentDaoImpl.getCommentPageListCount(userZoneAppComment);
    }

	@Override
	public int getCommentTotalNum(String userid) {
		return appCommentDaoImpl.getCommentTotalNum(userid);
	}

    @Override
    public GridDataModel<UserZoneAppComment> getCommentListUserZone(String userid, PageUtil page) {
        GridDataModel<UserZoneAppComment> commentList = new GridDataModel<UserZoneAppComment>();

        commentList.setRows(appCommentDaoImpl.getCommentPageListUserZone(userid, page));
        commentList.setTotal(appCommentDaoImpl.getCommentPageListCountUserZone(userid));

        return commentList;
    }

    @Override
    public int getCommentCountNoraml(String appid, String app_version) {
        return appCommentDaoImpl.getCommentCountNoraml(appid,app_version);
    }
}
