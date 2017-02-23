package com.dabai.vo;

import java.util.List;

public class page {
	private int totalpage;	//��ҳ��
	public static final int PAGESIZE = 3;	//ҳ���С
	public static final int PAGECAP = 3;	//һ��ҳ��ʵ�ּ�������
	private double	totalrecord;	//�ܼ�¼��
	private int pagenum;	//��ס��ǰҳ
	private int startpage;	//��ʼҳ
	private int endpage;	//����ҳ
	private List list;	//ҳ������
	private int startindex;	//��ʼȡ���ĵط�
	
	public page(int pagenum,int totalrecord){
		this.pagenum = pagenum;
		this.totalrecord = totalrecord;
		//����һ������ҳ
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
