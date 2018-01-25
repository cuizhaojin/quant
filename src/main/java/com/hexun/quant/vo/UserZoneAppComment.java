package com.hexun.quant.vo;

import java.io.Serializable;
import java.util.Date;

/**user index 页面里面评论列表的管理
 * Created by hexun on 2017/1/9.
 */
public class UserZoneAppComment implements Serializable {

    private static final long serialVersionUID = 1514181592427597873L;
    private String id;
    private String login_name;  //评论者
	private String display_name; //昵称
    private String app_id;
    private String app_version_id;
    private String commentbody;
    private Date create_date;
    private String rank_value;
    private String app_name;
    private String head_photo_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getApp_version_id() {
        return app_version_id;
    }

    public void setApp_version_id(String app_version_id) {
        this.app_version_id = app_version_id;
    }

    public String getCommentbody() {
        return commentbody;
    }

    public void setCommentbody(String commentbody) {
        this.commentbody = commentbody;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public String getRank_value() {
        return rank_value;
    }

    public void setRank_value(String rank_value) {
        this.rank_value = rank_value;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getHead_photo_url() {
        return head_photo_url;
    }

    public void setHead_photo_url(String head_photo_url) {
        this.head_photo_url = head_photo_url;
    }

	public String getDisplay_name() {
		return display_name;
	}

	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}
}
