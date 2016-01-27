package com.android.volley;

import java.util.HashMap;
import java.util.Map;

public class RequestParam {
	/**
	 * requestParams,get or post
	 */
	private Map<String, String> reqBodyParams;

	/**
	 * post File upload
	 */
	private Map<String, String> reqFileParams;
	
	
	private Map<String, String> reqHeaders;

	private boolean encrypt;

	public RequestParam() {
	};

	public RequestParam(boolean encrypt) {
		this.encrypt = encrypt;
	};

	public Map<String, String> getReqStringParams() {
		return reqBodyParams;
	}

	public void setReqBodyParams(Map<String, String> reqBodyParams) {
		this.reqBodyParams = reqBodyParams;
	}

	public Map<String, String> getReqFileParams() {
		return reqFileParams;
	}

	public void setReqFileParams(Map<String, String> reqFileParams) {
		this.reqFileParams = reqFileParams;
	}

	/**
	 * add body request param
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public RequestParam addStringParam(String key, String value) {
		if (reqBodyParams == null) {
			reqBodyParams = new HashMap<String, String>();
		}
		if (encrypt) {
			reqBodyParams.put(key, value);
		}
		reqBodyParams.put(key, value);
		return this;
	}

	/**
	 * 
	 * @param key user for server
	 * @param value file path
	 * @return
	 */
	public RequestParam addFileParam(String fileName, String filePath) {
		if (reqFileParams == null) {
			reqFileParams = new HashMap<String, String>();
		}
		reqFileParams.put(fileName, filePath);
		return this;
	}
	
	/**
	 * add request headers
	 * @param key
	 * @param value
	 * @return
	 */
	public RequestParam addHeader(String key,String value){
		if(reqHeaders == null){
			reqHeaders = new HashMap<String,String>();
		}
		reqHeaders.put(key, value);
		return this;
	}

	public Map<String, String> getReqHeaders() {
		return reqHeaders;
	}

	public void setReqHeaders(Map<String, String> reqHeaders) {
		this.reqHeaders = reqHeaders;
	}

}
