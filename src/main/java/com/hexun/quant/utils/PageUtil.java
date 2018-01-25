package com.hexun.quant.utils;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 分页工具类 Created by hexun on 2016/12/13.
 */
public class PageUtil {

	private int beginRow;
	private int endRow;

	public PageUtil(HttpServletRequest request) {

		String startStr = request.getParameter("start");
		String limitStr = request.getParameter("limit");
		if (StringUtils.isNotEmpty(startStr)
				&& StringUtils.isNotEmpty(limitStr)) {
			int statr = Integer.parseInt(startStr);
			int limit = Integer.parseInt(limitStr);

			int beginRow = statr;
			int endRow = limit;
			this.setBeginRow(beginRow);
			this.setEndRow(endRow);
		} else {
			this.setBeginRow(0);// 默认从第0行记录截取
			this.setEndRow(10);
		}
	}

	public PageUtil() {
	}

	public PageUtil(int pageNo, int records) {
		int start = (pageNo - 1) * records;
		int end = records;
		this.setBeginRow(start);
		this.setEndRow(end);
	}

	public int getBeginRow() {
		return beginRow;
	}

	public void setBeginRow(int beginRow) {
		this.beginRow = beginRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
}
