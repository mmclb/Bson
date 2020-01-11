package com.nanhuajiaren.bson;



public class EpConnection extends BangumiConnection<EpConnection.EpApiDownloadListener>
{

	public static final String epApiUrl = "https://m.bilibili.com/bangumi/play/ep";
	
	@Override
	public String getApiUrl()
	{
		return epApiUrl;
	}


	public EpConnection(long ep,EpApiDownloadListener listener){
		super(ep,listener);
	}
	
	@Override
	public void onFinish(EpConnection.EpApiDownloadListener listener, String data)
	{
		listener.onEpApiDownloaded(data);
	}

	public static interface EpApiDownloadListener{
		public void onEpApiDownloaded(String data)
	}
}
