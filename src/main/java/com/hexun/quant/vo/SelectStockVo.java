package com.hexun.quant.vo;

/**
 * 量化选股  stock VO
* Title: 			SelectStockVo
* Description: 		
* Company: 			www.hexun.com
* @author 			zhaojin
* @date 			2017年6月16日
 */
public class SelectStockVo {
	
	private String stockcode;  //股票代号
	private String stockname; //股票名称
	private int stockid;	//股票内码
	private Double price;	//入选价格 （不是实时价格）
	private int formulaid;  //选股指标
	private String formulaname; //选股指标名称
	private String markePrefix;
	
	
	
	public String getStockcode() {
		return stockcode;
	}
	public void setStockcode(String stockcode) {
		this.stockcode = stockcode;
	}
	public String getStockname() {
		return stockname;
	}
	public void setStockname(String stockname) {
		this.stockname = stockname;
	}
	public int getStockid() {
		return stockid;
	}
	public void setStockid(int stockid) {
		this.stockid = stockid;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public int getFormulaid() {
		return formulaid;
	}
	public void setFormulaid(int formulaid) {
		this.formulaid = formulaid;
	}
	public String getFormulaname() {
		return formulaname;
	}
	public void setFormulaname(String formulaname) {
		this.formulaname = formulaname;
	}
	public String getMarkePrefix() {
		return markePrefix;
	}
	public void setMarkePrefix(String markePrefix) {
		this.markePrefix = markePrefix;
	}
	
	
}
