## 概要
结合项目中的使用习惯，对Volley进行了一定的封装，新增了GsonRequest类，使用中需要结合Gson包来解析数据；并新增了文件下载和文件上传功能；
在使用中需注意：需要在	HttpServie 类中配置修改URL地址，以Struts为例：比如我们的服务器地址为http://192.168.30.100:8080/VolleyServer,
Action的地址为：hospital/hospitalAction!queryHospitalList.do,
那么需要将URL设置成：ttp://192.168.30.100:8080/VolleyServer/?,
实例Request时传入方法参数为：new GsonRequest("hospital/hospitalAction!queryHospitalList.do",........);在中网络请求的过程中会拼接为：http://192.168.30.100:8080/VolleyServer/hospital/hospitalAction!queryHospitalList.do;
如果我们的请求都在一个Action中，那么可以设置URL为http://192.168.30.100:8080/VolleyServer/hospital/hospitalAction!?.do,
实例Request时传入方法参数为：new GsonRequest("queryHospitalList",........)

注意：使用是服务端的返回值需要为gson格式，客户端根据服务端返回的类容定义相应的类；


这儿有几篇博客关于volly的介绍 ：[volley缓存、超时重试策略介绍](http://blog.csdn.net/mrruby)

## 权限
```
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

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

## 文件下载

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

