package com.dabai.vo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class token {
	private static String TOKEN_KEY  = "token";
	
	private static token instance = new token();
	
	public static token getInstance(){
		return instance;
	}
	
	private long previous;
	
	//得到session中的令牌
	private String getSesToken(HttpServletRequest request){
		HttpSession ses = request.getSession();
		
		return ses.getAttribute(TOKEN_KEY) == null?null:
			(String)ses.getAttribute(TOKEN_KEY);
	}
	
	//判断令牌是否有效
	public synchronized boolean isValid(String reqToken,HttpServletRequest request){
		String sesToken = this.getSesToken(request);
		if(sesToken == null)
			return false;
		
		this.resetToken(request);
		
		return sesToken.equals(reqToken);
	}

	//重置令牌
	private void resetToken(HttpServletRequest request) {
		HttpSession ses = request.getSession();
		
		ses.removeAttribute(TOKEN_KEY);
	}
	
	//产生一个新的令牌值
	//保存在session中
	public synchronized void savedToken(HttpServletRequest request){
		HttpSession ses = request.getSession();
		String token = generateToken(request);
		if(token != null)
			ses.setAttribute(TOKEN_KEY, token);
	}

	//根据当前的用户会话ID和系统时间产生唯一的令牌
	private String generateToken(HttpServletRequest request) {
		HttpSession ses = request.getSession();
		
		try {
			byte[] id = ses.getId().getBytes();
			long current = System.currentTimeMillis();
			if(current == previous)
				current ++;
			previous = current;
			byte[] now = new Long(current).toString().getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(id);
			md.update(now);
			return this.toHex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * 将字节数组转换成十六进制字符串
	 * */
	private String toHex(byte[] buffer) {
		StringBuffer sb = new StringBuffer(buffer.length * 2);
		for(int i=0 ;i<buffer.length; i++){
			sb.append(Character.forDigit((buffer[i] & 0xF0) >> 4, 16));
			sb.append(Character.forDigit(buffer[i] & 0x0F, 16));
		}
		
		return sb.toString();
	}
	
	//从session中得到一个新的令牌值
	//如果session中没有，则生成一个新的并将其保存
	public synchronized String getToken(HttpServletRequest request){
		HttpSession ses = request.getSession();
		String token = (String) ses.getAttribute(TOKEN_KEY);
		if(null == token){
			token = this.generateToken(request);
			ses.setAttribute(TOKEN_KEY, token);
		}
		
		return token;
	}
}
