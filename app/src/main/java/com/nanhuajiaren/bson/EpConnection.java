package com.nanhuajiaren.bson;
import com.nanhuajiaren.BasicIO.BasicIO;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import android.util.Log;


public class EpConnection extends BangumiConnection<EpConnection.EpApiDownloadListener>
{

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
