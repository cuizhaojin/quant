package com.hexun.quant.vo;

import java.io.Serializable;
import java.util.Date;

/**App评论vo
 * Created by hexun on 2016/12/19.
 */
public class AppComment implements Serializable {

    private static final long serialVersionUID = 6730294510769128486L;
    private String id;
    private String app_id;
    private String app_version_id;
    private String user_id;
    private String user_role;
    private int status;
    private String commentbody;
    private Date create_date;
    private String rank_value;
    private String head_url;
    private String user_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getHead_url() {
        return head_url;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}

