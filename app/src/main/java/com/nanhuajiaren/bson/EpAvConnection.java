package com.nanhuajiaren.bson;
import android.support.annotation.NonNull;
import com.google.gson.Gson;
import com.nanhuajiaren.threadannotation.CalledInOtherThreads;

public class EpAvConnection
{
	private EpData data;
	private OnEpAvApiDownloadedListener listener;

	public EpAvConnection(EpData data, OnEpAvApiDownloadedListener listener)
	{
		this.data = data;
		this.listener = listener;
	}
	
	public static interface OnEpAvApiDownloadedListener{
		@CalledInOtherThreads
		public void onEpAvApiDownloaded(EpData transformedData)
	}
	
	public void startConnection(){
		new EpIterator().next();
	}
	
	private class EpIterator implements AvConnection.OnAvApiDownloadedListener{
		private int partGoing = 0;
		
		private void next(){
			if(partGoing < data.epList.size()){
				new AvConnection(data.epList.get(partGoing).aid,this).startAvApiRequest();
			}else{
				listener.onEpAvApiDownloaded(data);
			}
		}

		@Override
		@CalledInOtherThreads
		public void onAvApiDownloaded(String avApiResult)
		{
			AvData avData = new Gson().fromJson(avApiResult,AvData.class);
			data.epList.get(partGoing).linkedAvData = avData;
			partGoing ++;
			next();
		}
	}
}
