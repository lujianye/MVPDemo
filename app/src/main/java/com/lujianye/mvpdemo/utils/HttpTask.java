package com.lujianye.mvpdemo.utils;

import android.os.AsyncTask;

import java.util.Map;

public class HttpTask extends AsyncTask<String, Void, String> {

	private Builder builder;

	private HttpTask(Builder builder) {
		this.builder = builder;
	}

	@Override
	protected String doInBackground(String... params) {
		try {
			if (this.builder.method.equals("GET")){
				return HttpUtils.get(this.builder.url);
			} else if (this.builder.method.equals("POST")){
				return HttpUtils.post(this.builder.url, this.builder.paramMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		if (this.builder.onHttpResultListener != null) {
			this.builder.onHttpResultListener.onResult(result);
		}
	}

	public static class Builder {
		private HttpUtils.OnHttpResultListener onHttpResultListener;
		private String url;
		private Map<String, Object> paramMap;
		private String method;

		public Builder(){
			this.method = "GET";
		}

		public Builder url(String url){
			this.url = url;
			return this;
		}

		public Builder paramMap(Map<String, Object> paramMap){
			this.paramMap = paramMap;
			return this;
		}

		public Builder method(String method){
			this.method = method;
			return this;
		}

		public Builder onHttpResultListener(HttpUtils.OnHttpResultListener onHttpResultListener){
			this.onHttpResultListener = onHttpResultListener;
			return this;
		}

		public HttpTask build(){
			return new HttpTask(this);
		}

	}

}
