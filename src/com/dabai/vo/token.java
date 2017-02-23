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
	
	//�õ�session�е�����
	private String getSesToken(HttpServletRequest request){
		HttpSession ses = request.getSession();
		
		return ses.getAttribute(TOKEN_KEY) == null?null:
			(String)ses.getAttribute(TOKEN_KEY);
	}
	
	//�ж������Ƿ���Ч
	public synchronized boolean isValid(String reqToken,HttpServletRequest request){
		String sesToken = this.getSesToken(request);
		if(sesToken == null)
			return false;
		
		this.resetToken(request);
		
		return sesToken.equals(reqToken);
	}

	//��������
	private void resetToken(HttpServletRequest request) {
		HttpSession ses = request.getSession();
		
		ses.removeAttribute(TOKEN_KEY);
	}
	
	//����һ���µ�����ֵ
	//������session��
	public synchronized void savedToken(HttpServletRequest request){
		HttpSession ses = request.getSession();
		String token = generateToken(request);
		if(token != null)
			ses.setAttribute(TOKEN_KEY, token);
	}

	//���ݵ�ǰ���û��ỰID��ϵͳʱ�����Ψһ������
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
	 * ���ֽ�����ת����ʮ�������ַ���
	 * */
	private String toHex(byte[] buffer) {
		StringBuffer sb = new StringBuffer(buffer.length * 2);
		for(int i=0 ;i<buffer.length; i++){
			sb.append(Character.forDigit((buffer[i] & 0xF0) >> 4, 16));
			sb.append(Character.forDigit(buffer[i] & 0x0F, 16));
		}
		
		return sb.toString();
	}
	
	//��session�еõ�һ���µ�����ֵ
	//���session��û�У�������һ���µĲ����䱣��
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
