package com.nanhuajiaren.bson;
import com.nanhuajiaren.BasicIO.BasicIO;
import java.io.IOException;
import com.nanhuajiaren.threadannotation.CalledInOtherThreads;
import android.support.annotation.NonNull;

public class AvConnection
{
	private static final String AV_API_URL = "https://api.bilibili.com/x/web-interface/view?aid=";

	public AvConnection(long avId,@NonNull OnAvApiDownloadedListener listenerAv)
	{
		this.avId = avId;
		this.listenerAv = listenerAv;
	}
	
	public static interface OnAvApiDownloadedListener{
		@CalledInOtherThreads
		public void onAvApiDownloaded(String avApiResult)
	}
	
	private long avId = 0;
	private OnAvApiDownloadedListener listenerAv;
	private boolean used = false;
	
	public void startAvApiRequest(){
		if(used){
			return;
		}
		used = true;
		new Thread(){
			@Override
			public void run(){
				try
				{
					listenerAv.onAvApiDownloaded(BasicIO.readURL(AV_API_URL + AvConnection.this.avId));
				}
				catch (IOException e)
				{
					listenerAv.onAvApiDownloaded("{\"code\":-999}");
				}
			}
		}.start();
	}
}
