package com.android.volley.toolbox;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.Response.ProgressListener;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class FileDownRequest extends Request<String> {

	private ProgressListener progressListener;
	private File saveFile;  //文件保存路径
	private Listener<String> mListener;
	private boolean stop;

	// 更新进度
	private Handler handler = new Handler(Looper.getMainLooper()) {
		public void handleMessage(android.os.Message msg) {
			if (progressListener != null) {
				progressListener.onProgress(msg.arg1,msg.arg2);
			}
		};
	};


	public FileDownRequest(String url,File saveFile, ProgressListener progressListener,Listener<String> listenter,
			ErrorListener listener) {
		super(Method.GET, url, listener);
		this.progressListener = progressListener;
		this.saveFile = saveFile;
		this.mListener = listenter;
		setHasInputStream(true);
		setShouldCache(false);
	}

	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {
		// TODO Auto-generated method stub
		try {
			readStream(response.httpEntity, saveFile);
			return Response.success(saveFile.toString(), HttpHeaderParser.parseCacheHeaders(response));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if(saveFile != null && saveFile.exists()){
				saveFile.delete();
			}
			e.printStackTrace();
			return Response.success(null, HttpHeaderParser.parseCacheHeaders(response));
		}
	}

	@Override
	protected void deliverResponse(String response) {
		// TODO Auto-generated method stub
		mListener.onResponse(response);

	}

	/**
	 * 读取文件操作 如果传入的流为空 将数据写入指定的 file 如果写入之前存在该文件，会删除该文件
	 * @param entity
	 * @param file
	 * 需要保存文件的路径、必须是完整的路径，包括后缀名
	 */
	public void readStream(HttpEntity entity, File file) throws Exception{
		int len = (int) entity.getContentLength();
		FileOutputStream fos = null;
		InputStream is = null;
		try {
			is = entity.getContent();
			byte[] buf = new byte[1024];
			int count = -1;
			if (file.exists()) {
				file.delete();
			}
			int read = 0;
			fos = new FileOutputStream(file);
			while ((count = is.read(buf)) != -1 &&!stop) {
				fos.write(buf, 0, count);
				read += count;
				Message msg = Message.obtain();
				msg.arg1 = read;
				msg.arg2 = len;
				handler.sendMessage(msg);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			file.delete();
			throw e;
		} finally {
			try {
				entity.consumeContent();
				if (fos != null){
					fos.close();
				}
				if(is != null){
					is.close();
				}
			} catch (Exception e) {
			}
		}
	}
	
	public void stopDown(){
		stop = true;
	}

}
