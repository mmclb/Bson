package com.nanhuajiaren.bson;
import com.nanhuajiaren.BasicIO.BasicIO;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import android.util.Log;


public class EpConnection
{
	private static final String epApiURL = "https://m.bilibili.com/bangumi/play/ep";
	private static final String startPoint = "window.__INITIAL_STATE__={";
	//                                                                 ↑
	private static final String endPoint = ";";

	private long ep;
	private EpApiDownloadListener listener;
	private boolean used = false;

	public EpConnection(long ep, EpApiDownloadListener listener)
	{
		this.ep = ep;
		this.listener = listener;
	}

	public static interface EpApiDownloadListener
	{
		public void onEpApiDownloaded(String initialInfo)
	}

	public void startConnection()
	{
		if (used)
		{
			return;
		}
		used = true;
		new Thread(){
			@Override
			public void run()
			{
				try
				{
					URL url = new URL(epApiURL + ep);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestProperty("User-Agent", "Mozilla/5.0(Macintosh;IntelMacOSX10_7_0)AppleWebKit/535.11(KHTML,likeGecko)Chrome/17.0.963.56Safari/535.11");
					if(conn.getResponseCode() != 200){
						listener.onEpApiDownloaded("{\"loaded\":false,\"location\":\"Response:" + conn.getResponseCode() + "\"}");
					}
					StringBuilder builder = new StringBuilder();
					BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					String l = reader.readLine();
					while(l != null){
						builder.append(l);
						builder.append("\n");
						l = reader.readLine();
					}
					reader.close();
					String s = builder.toString();
					int positionStart = s.indexOf(startPoint);
					if (positionStart == -1)
					{
						listener.onEpApiDownloaded("{\"loaded\":false,\"location\":\"StartPointNotFound\"}");
					}
					else
					{
						s = s.substring(positionStart + startPoint.length());
						s = "{\"loaded\":true," + s;
						s = s.split(endPoint)[0];
						listener.onEpApiDownloaded(s);
					}
				}
				catch (IOException e)
				{
					listener.onEpApiDownloaded("{\"loaded\":false,\"location\":\"IOException\"}");
				}
				catch (Exception e)
				{
					try
					{
						BasicIO.write("/sdcard/e.txt", e.toString(), false);
					}
					catch (IOException e2)
					{}
				}
			}
		}.start();
	}
}
