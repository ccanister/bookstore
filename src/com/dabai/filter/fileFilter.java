package com.dabai.filter;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.dabai.vo.fileExt;

/**
 * Servlet Filter implementation class fileFilter
 */
public class fileFilter extends formFilter {
	private String paramCacheName;		//����ʾ���������map���󱣴���request���е���������
	
	private long maxSize = 2048;		//�����ϴ����ļ�Ĭ���ܳߴ磬��λkb
	
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	} 

	

	/**
	 * @see Filter#init(FilterConfig)
	 * ��ȡparamcacheName��maxSize����ֵ
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		super.init(fConfig);
		paramCacheName = fConfig.getInitParameter("paramCacheName");
		String value = fConfig.getInitParameter("maxSize");
		if(value!=null){
			maxSize = Integer.parseInt(value);
		}
	}
	@Override
	protected Map<String,String> getFilterParamNames(){
		Map<String,String> paramNames = super.getFilterParamNames();
		paramNames.put("paramCacheName","paramCacheName");
		paramNames.put("maxSize","maxSize");
		
		return paramNames;
	}
	
	private long getFileSize(List files){
		long size = 0;
		for(Object file:files){
			fileExt fileExt = (fileExt)file;
			size += fileExt.getSize();
		}
		
		return size;
	}
	
	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try{
			System.out.println("file");
			//�ڵ��ø��඼filter֮ǰ���Ƚ�����������map�����У�
			//Ȼ����getParamValue�����д�map�����л�ȡ�������ֵ
			Map<String,Object> paramNames = new HashMap<String,Object>();
			//ʵ�û�common-fileupload�齨����������Ϣ
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			//�������󣬲�������������������ļ������Ϣ����list����
			List<FileItem> items = upload.parseRequest((HttpServletRequest)request);
			String filename = "";
			InputStream is = null;
			for(FileItem item:items){
				//�������ͨ�������������������������������ֵ�ӵ�map������
				if(item.isFormField()){
					paramNames.put(item.getFieldName(),new String(item.getString().getBytes("iso-8859-1"),"utf-8"));
				} else if(item.getName()!=null&&!item.getName().equals("")){
					fileExt fe = new fileExt();
					//���ϴ��ļ�����Ϣ������fileExt�������Ӧ������
					fe.setFileStream(item.getInputStream());
					fe.setFilename(new String(item.getName().split("\\.")[1].getBytes("GBK"),"utf-8"));
					fe.setContentType(item.getContentType());
					//�鿴Map�������Ƿ��Ѿ���ͬ�����������
					//������ڣ���ʾ�ͻ����ϴ����Ƕ���ļ�
					Object value = paramNames.get(item.getFieldName());
					List files = null;
					if(value==null){
						//����һ��arrayList���󣬲������ϴ�һ���ļ����Ƕ���ļ�
						//��ʾ�ϴ��ļ���fileExt���󶼱�����arralList������
						files = new ArrayList();
						files.add(fe);
						paramNames.put(item.getFieldName(), files);
					} else {
						files = (List) value;
						files.add(fe);
						//����Ѿ�������ͬ�ֶ������ļ��룬����ʾ��ǰ�����ļ���fileExt
						//������ӵ���Ӧ��ArrayList������
					}
					long size = getFileSize(files) / 1024;
					if(size > maxSize){
						throw new Exception("�ϴ��ļ��ߴ����");
					}
				}
			}
			request.setAttribute(paramCacheName,paramNames);
			//���������������Ϣ��Map���󱣴���request����
			//������ΪparamCacheName������ֵ
			super.doFilter(request, response, chain);
		}catch(Exception e){
			e.printStackTrace();
		}
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}
	
	@Override
	protected void doMethod(Object form,Object paramValue,Method method){
		try{
			if(paramValue instanceof List){
				List<fileExt> params = (List) paramValue;
				if(params.size() == 0)
					return;
				//����javaBean����ΪfileExt�������
				if(method.getParameterTypes()[0] == fileExt.class){
					method.invoke(form, params.get(0));
				} else if(method.getParameterTypes()[0] == fileExt[].class){
					method.invoke(form, new Object[]{params.<fileExt>toArray(new fileExt[params.size()])});
				} else if(method.getParameterTypes()[0] == List.class){
					//����javabean��������Ϊlist<FileExt>��������
					method.invoke(form, params);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	protected Object getParamValue(HttpServletRequest request,String name){
		Map<String,Object> paramNames = (Map<String,Object>) request.getAttribute(paramCacheName);
		Object result = paramNames.get(name);
		
		return result;
	}
}
