package com.android.volley.utils;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class HttpService {

	private static HttpService mInstance;
	private RequestQueue mRequestQueue;
	private Context mContext;
	private ImageLoader mImageLoader;
	/**
	 * 基础请求地址,"?"为请求参数的替换符号
	 */
	public static final String URL = "http://192.168.30.100:8080/VolleyServer/?";

	private HttpService(Context context) {
		this.mContext = context;
		mRequestQueue = getRequestQueue();
		mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
			private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(20);

			@Override
			public Bitmap getBitmap(String url) {
				return cache.get(url);
			}

			@Override
			public void putBitmap(String url, Bitmap bitmap) {
				cache.put(url, bitmap);
			}
		});
	}

	public static synchronized HttpService getInstance(Context context) {
		if (mInstance == null) {
			synchronized (HttpService.class) {
				if (mInstance == null) {
					mInstance = new HttpService(context);
				}
			}
		}
		return mInstance;
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			// getApplicationContext() is key, it keeps you from leaking the
			// Activity or BroadcastReceiver if someone passes one in.
			mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
		}
		return mRequestQueue;
	}

	public <T> void addToRequestQueue(Request<T> req) {
		getRequestQueue().add(req);
	}

	public ImageLoader getImageLoader() {
		return mImageLoader;
	}

	/**
	 * Perform post request
	 * 
	 * @param req
	 */
	public <T> void doPostRequest(Request<T> req) {
		req.setMethod(Method.POST);
		addToRequestQueue(req);
	}

	/**
	 * Perform get request
	 * 
	 * @param req
	 */
	public <T> void doGetRequest(Request<T> req) {
		req.setMethod(Method.GET);
		addToRequestQueue(req);
	}

}
