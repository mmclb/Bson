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
	public void onFinish(String data)
	{
		listener.onSsApiDownloaded(ep,data);
	}
	
	public SsConnection(long ss,SsApiDownloadListener listener){
		super(ss,listener);
	}
	
	public static interface SsApiDownloadListener{
		public void onSsApiDownloaded(long ssId,String s)
	}
}
