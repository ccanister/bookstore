package com.dabai.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class downloadServlet
 */
public class downloadServlet extends HttpServlet {
	public static String FILENAME;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(FILENAME);
		request.setCharacterEncoding("utf-8");
		String path = this.getServletConfig().getInitParameter("path");
		String filename = request.getParameter("filename");
		
		File dir = new File(path);
		if(dir.exists()){
			if(filename!=null){
				if(FILENAME==null)
					FILENAME = dir.getPath() + File.separator + filename; 
				else
					FILENAME = FILENAME + File.separator + filename; 
				File downloadFile = new File(FILENAME);
  				if(downloadFile.isFile()){
					download(downloadFile,response);
				} else {
					dir = new File(FILENAME);
					listDir(dir,response);
				}
			}else {
				listDir(dir,response);
			}
		}
		

	}
	//输出path初始化参数指定目录中文件
	private void listDir(File dir, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//扫描目录的子目录和文件
		for (File file : dir.listFiles()){
			out.print("<a href='downloadServlet?filename="+URLEncoder.encode(file.getName(), "UTF-8")+"'>");
			out.println(file.getName()+"</a><br/>");
		}
	}

	private void download(File file, HttpServletResponse response) throws IOException {
		if(file.exists()){
			if((file.getName().length()-file.getName().lastIndexOf(".jpg")) == 4){
				response.setContentType("image/jpeq");
				response.addHeader("Content-Disposostion","fileename="+URLEncoder.encode(file.getName(),"UTF-8"));
			} else {
				response.setContentType("application/octet-stream");
				response.addHeader("Content-Disposition","attachement;filename="+URLEncoder.encode(file.getName(),"UTF-8"));
			}
			response.addHeader("Content-length",String.valueOf(file.length()));
			InputStream is = new FileInputStream(file);
			byte[] buffer = new byte[8192];
			int count = 0;
			ServletOutputStream sos = response.getOutputStream();
			//箱客户端输出下载内容，每次输出8kb
			while((count = is.read(buffer)) > 0){
				sos.write(buffer,0,count);
			}
				is.close();
				sos.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
