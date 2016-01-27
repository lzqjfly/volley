package com.android.volley.toolbox;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestParam;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.Response.ProgressListener;
import com.android.volley.utils.HttpService;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import android.text.TextUtils;

public class GsonRequest<T> extends Request<T>{
	private final Gson gson = new Gson();
    private final Class<T> clazz;
    private final Listener<T> listener;
    
    
    public GsonRequest(String methodName,Class<T> clazz,RequestParam requestParam,Listener<T> listener, ErrorListener errorListener,ProgressListener progressListener) {
		// TODO Auto-generated constructor stub
    	this(methodName,clazz,requestParam,listener,errorListener);
    	setmProgressListerner(progressListener);
	} 
    
    public GsonRequest(String methodName,Class<T> clazz,RequestParam requestParam,Listener<T> listener, ErrorListener errorListener) {
		// TODO Auto-generated constructor stub
    	super(errorListener);
    	String baseUrl = getBaseUrl(methodName);
    	setUrl(baseUrl);
    	setRequestParam(requestParam);
    	this.clazz = clazz;
    	this.listener = listener;
	}
    
    
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
    	// TODO Auto-generated method stub
    	if(getRequestParam() != null && getRequestParam().getReqHeaders() != null){
    		return getRequestParam().getReqHeaders();
    	}
    	return super.getHeaders();
    }
    
    
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
    	// TODO Auto-generated method stub
    	if(getRequestParam() != null)return getRequestParam().getReqStringParams();
    	return super.getParams();
    }
    
    private String getBaseUrl(String methodName){
    	String temUrl = new String(HttpService.URL);
		if (!TextUtils.isEmpty(methodName)) {
			temUrl = temUrl.replace("?", methodName);
		}
		return temUrl;
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(
                    gson.fromJson(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

}
