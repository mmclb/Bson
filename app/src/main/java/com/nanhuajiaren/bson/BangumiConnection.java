package com.nanhuajiaren.bson;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class BangumiConnection<T>
{
	private static final String startPoint = "window.__INITIAL_STATE__={";
	//                                                                 ↑
	private static final String endPoint = ";";

	protected long ep;
	protected T listener;
	private boolean used = false;

	public BangumiConnection(long ep, T listener)
	{
		this.ep = ep;
		this.listener = listener;
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
					URL url = new URL(getApiUrl() + ep);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestProperty("User-Agent", "Mozilla/5.0(Macintosh;IntelMacOSX10_7_0)AppleWebKit/535.11(KHTML,likeGecko)Chrome/17.0.963.56Safari/535.11");
					if(conn.getResponseCode() != 200){
						onFinish("{\"loaded\":false,\"location\":\"Response:" + conn.getResponseCode() + "\"}");
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
						onFinish("{\"loaded\":false,\"location\":\"StartPointNotFound\"}");
					}
					else
					{
						s = s.substring(positionStart + startPoint.length());
						s = "{\"loaded\":true," + s;
						s = s.split(endPoint)[0];
						onFinish(s);
					}
				}
				catch (IOException e)
				{
					onFinish("{\"loaded\":false,\"location\":\"IOException\"}");
				}
				catch (Exception e)
				{
				}
			}
		}.start();
	}
	
	public abstract void onFinish(String data)
	public abstract String getApiUrl()
}
