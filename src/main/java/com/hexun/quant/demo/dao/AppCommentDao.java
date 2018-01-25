package com.hexun.quant.demo.dao;

import com.hexun.quant.utils.PageUtil;
import com.hexun.quant.vo.AppComment;
import com.hexun.quant.vo.UserZoneAppComment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hexun on 2016/12/19.
 */
@Repository("appCommentDaoImpl")
public interface AppCommentDao {
    /**
     * 添加评论
     * @param appComment
     */
    public void  addAppComment(@Param("appComment")AppComment appComment);

    /**
     * 修改评论
     * @param appComment
     */
    public void updateAppComment(@Param("appComment")AppComment appComment);


    /**
     *获取小程序评论列表页
     * @param userZoneAppComment
     * @param page
     * @return
     */
    List<UserZoneAppComment> getCommentPageList(@Param("userZoneAppComment")UserZoneAppComment userZoneAppComment, @Param("page")PageUtil page);

    /**
     * 获取小程序评论列表个数 根据appid app_version_id 可以传递  app_id 或者app_id app_version_id
     * @param userZoneAppComment
     * @return
     */
    int getCommentPageListCount(@Param("userZoneAppComment")UserZoneAppComment userZoneAppComment);


	@Select("SELECT\n" +
			"\tcount(*)\n" +
			"FROM\n" +
			"\tapp_comment\n" +
			"WHERE\n" +
			"\tapp_id IN (\n" +
			"\t\tSELECT DISTINCT\n" +
			"\t\t\t(m.app_id)\n" +
			"\t\tFROM\n" +
			"\t\t\tapp_main m where m.create_user =#{userid}\n" +
			"\t)")
	int getCommentTotalNum(@Param("userid")String userid);

    /**
     * 获取评论列表  位置： 主页评论管理 打开后的列表
     * @param userid
     * @param page
     * @return
     */
    List<UserZoneAppComment> getCommentPageListUserZone(@Param("userid")String userid,@Param("page")PageUtil page);

    /**
     * 根据userid获取评论列表个数 位置：主页评论管理 打开后的列表
     * @param userid
     * @return
     */
    int getCommentPageListCountUserZone(@Param("userid")String userid);

    /**
     * 查询某一个app的个数
     * @param appid
     * @param app_version
     * @return
     */
    @Select("select count(*) from app_comment where status = '1' and app_id = #{appid} and app_version_id = #{app_version}")
    int getCommentCountNoraml(@Param("appid")String appid,@Param("app_version")String app_version);


}
