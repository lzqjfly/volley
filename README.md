## 概要
添加了多文件上传和下载，结合项目中的使用习惯进行了二次封装
## get请求
```
	private void doGet() {
		RequestParam req = new RequestParam();
		req.addStringParam("userName", "mrruby");
		req.addStringParam("userPass", "111111");
		HttpService.getInstance(getApplicationContext())
				.doGetRequest(new GsonRequest<>("LoginServlet", RspLogin.class, req, new Listener<RspLogin>() {
					@Override
					public void onResponse(RspLogin response) {
						// TODO Auto-generated method stub
						showToast(response.toString());
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
					}
				}));
	}

```

## post请求
```
	private void doPost() {
		RequestParam req = new RequestParam();
		req.addStringParam("userName", "mrruby");
		req.addStringParam("userPass", "111111");
		HttpService.getInstance(getApplicationContext())
				.doPostRequest(new GsonRequest<>("LoginServlet", RspLogin.class, req, new Listener<RspLogin>() {
					@Override
					public void onResponse(RspLogin response) {
						// TODO Auto-generated method stub
						showToast(response.toString());
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
					}
				}));
	}

```
## 多文件上传

```
	private void doUpload() {
		RequestParam req = new RequestParam();
		req.addStringParam("user", "mrruby");
		req.addStringParam("userid", "234898018902834");
		req.addFileParam("file1", "sdcard/header.png");
		req.addFileParam("file2", "sdcard/header.png");
		HttpService.getInstance(getApplicationContext())
				.doPostRequest(new GsonRequest<>("ImageServlet", RspLogin.class, req, new Listener<RspLogin>() {
					@Override
					public void onResponse(RspLogin response) {
						// TODO Auto-generated method stub
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
					}
				}, new ProgressListener() {
					@Override
					public void onProgress(int total, int progress) {
						// TODO Auto-generated method stub
						Log.d("progress","上传进度：" + progress +"/"+ total);
					}
				}));
	}
	
```

## 文件下载(不支持断点续传)

```
	private void doFileDown() {
		final File saveFile = new File("sdcard/header.png");
		FileDownRequest request = new FileDownRequest(
				"http://img4.duitang.com/uploads/blog/201311/04/20131104193715_NCexN.thumb.jpeg", saveFile,
				new ProgressListener() {
					@Override
					public void onProgress(int read, int total) {
						// TODO Auto-generated method stub
						Log.d("progress", read + "/" + total);
					}
				}, new Listener<String>() {
					// 文件保存的路径
					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub
						showToast(response);
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
					}
				});
		HttpService.getInstance(getApplicationContext()).doGetRequest(request);
	}
```

