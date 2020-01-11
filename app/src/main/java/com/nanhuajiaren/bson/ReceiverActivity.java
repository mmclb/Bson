package com.nanhuajiaren.bson;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import android.view.View;

public class ReceiverActivity extends ApiHostActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.receiver);
		setupViews();
		if(!dealIntent()){
			findViewById(R.id.receiverLinearLayout1).setVisibility(View.VISIBLE);
		}
	}
	
	private boolean dealIntent(){
		String sharedText = getIntent().getStringExtra(Intent.EXTRA_TEXT);
		
		Pattern patternAv = Pattern.compile("/av[1-9][0-9]*");
		Matcher matchAv = patternAv.matcher(sharedText);
		if (matchAv.find())
		{
			String av = matchAv.group();
			long avid = Integer.parseInt(av.substring(3));
			new AvConnection(avid, this).startAvApiRequest();
			return true;
		}
		
		Pattern patternEp = Pattern.compile("/ep[1-9][0-9]*");
		Matcher matchEp = patternEp.matcher(sharedText);
		if (matchEp.find())
		{
			String ep = matchEp.group();
			long epid = Integer.parseInt(ep.substring(3));
			new EpConnection(epid, this).startConnection();
			return true;
		}
		
		Pattern patternSs = Pattern.compile("/ss[1-9][0-9]*");
		Matcher matchSs = patternSs.matcher(sharedText);
		if (matchSs.find())
		{
			String ss = matchSs.group();
			long ssid = Integer.parseInt(ss.substring(3));
			new SsConnection(ssid, this).startConnection();
			return true;
		}
		
		return false;
	}

	@Override
	public void onAvApiDownloaded(long avId, String avApiResult)
	{
		super.onAvApiDownloaded(avId, avApiResult);
		finish();
	}

	@Override
	public void onEpApiDownloaded(long epId, String initialInfo)
	{
		super.onEpApiDownloaded(epId, initialInfo);
		finish();
	}

	@Override
	public void onSsApiDownloaded(long ssId, String s)
	{
		super.onSsApiDownloaded(ssId, s);
		finish();
	}
}
