package com.dabai.daoImpl;

import com.dabai.vo.page;

public class pageService {
	private int startIndex;
	private int pageNum;
	private page page;
	public pageService(int pageNum,page page){
		this.pageNum = pageNum;
		this.page = page;
	}
	
	public void initPage(){
		if(pageNum<1){
			page.setStartindex(0);
			page.setPagenum(1);
		} else if(pageNum > page.getTotalpage()){
			page.setPagenum(page.getTotalpage());
		} else{
			page.setStartindex(page.PAGESIZE * (this.pageNum-1));
			page.setPagenum(pageNum);
		}
		if(page.getTotalpage() < page.PAGESIZE){	//如果总页数小于3，则只显示12页或者1页
			page.setStartpage(1);
			page.setEndpage(page.getTotalpage());
		}else{
			int startpage = this.pageNum - 1;
			int endpage = this.pageNum + 1;
			if(startpage<1){
				page.setStartpage(1);
				page.setEndpage(3);
			} else if(endpage>page.getTotalpage()){
				page.setStartpage(page.getTotalpage()-2);
				page.setEndpage(page.getTotalpage());
			} else {
				page.setStartpage(startpage);
				page.setEndpage(endpage);
			}
		}
	}

}
