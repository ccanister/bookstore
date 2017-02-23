package com.dabai.vo;

import java.util.List;

public class page {
	private int totalpage;	//总页数
	public static final int PAGESIZE = 3;	//页面大小
	public static final int PAGECAP = 3;	//一个页面实现几组数据
	private double	totalrecord;	//总记录数
	private int pagenum;	//记住当前页
	private int startpage;	//开始页
	private int endpage;	//结束页
	private List list;	//页面数据
	private int startindex;	//开始取出的地方
	
	public page(int pagenum,int totalrecord){
		this.pagenum = pagenum;
		this.totalrecord = totalrecord;
		//计算一共多少页
		this.totalpage = (int) Math.ceil(this.totalrecord/this.PAGESIZE);
	}
	public int getTotalpage() {
		return totalpage;
	}
	public void setTotalpage(int totalpage) {
		this.totalpage = totalpage;
	}
	
	public int getTotalrecord() {
		return (int)totalrecord;
	}
	public void setTotalrecord(int totalrecord) {
		this.totalrecord = totalrecord;
	}
	public int getPagenum() {
		return pagenum;
	}
	public void setPagenum(int pagenum) {
		this.pagenum = pagenum;
	}
	public int getStartpage() {
		return startpage;
	}
	public void setStartpage(int startpage) {
		this.startpage = startpage;
	}
	public int getEndpage() {
		return endpage;
	}
	public void setEndpage(int endpage) {
		this.endpage = endpage;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public int getStartindex() {
		return startindex;
	}
	public void setStartindex(int startindex) {
		this.startindex = startindex;
	}
	
}
