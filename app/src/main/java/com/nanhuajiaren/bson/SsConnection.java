package com.nanhuajiaren.bson;

public class SsConnection extends BangumiConnection<SsConnection.SsApiDownloadListener>
{

	@Override
	public String getApiUrl()
	{
		return "https://m.bilibili.com/bangumi/play/ss";
	}
	
	@Override
	public static String epApiURL = "https://m.bilibili.com/bangumi/play/ss";

	@Override
	public void onFinish(SsConnection.SsApiDownloadListener listener, String data)
	{
		listener.onSsApiDownloaded(data);
	}
	
	public SsConnection(long ss,SsApiDownloadListener listener){
		super(ss,listener);
	}
	
	public static interface SsApiDownloadListener{
		public void onSsApiDownloaded(String s)
	}
}
