package com.nanhuajiaren.bson;
import com.nanhuajiaren.threadannotation.CalledInOtherThreads;
import android.widget.EditText;
import android.content.Intent;
import android.os.Bundle;

public abstract class ApiHostActivity extends BaseActivity implements AvConnection.OnAvApiDownloadedListener,EpConnection.EpApiDownloadListener,SsConnection.SsApiDownloadListener
{
	@Override
	@CalledInOtherThreads
	public void onAvApiDownloaded(String avApiResult)
	{
		EditText edit = (EditText) findViewById(R.id.mainEditText1);
		Intent intent = new Intent(this, AvPartListActivity.class);
		Bundle bundle = new Bundle();
		bundle.putLong(AvPartListActivity.AVID_KEY, Long.parseLong(edit.getText().toString()));
		bundle.putString(AvPartListActivity.AVINFO_KEY,avApiResult);
		intent.putExtras(bundle);
		startActivity(intent);
		runOnUiThread(new Runnable(){
				@Override
				public void run(){
					hideLoadDialog();
				}
			});
	}

	@Override
	@CalledInOtherThreads
	public void onEpApiDownloaded(String initialInfo)
	{
		EditText edit = (EditText) findViewById(R.id.mainEditText1);
		Intent intent = new Intent(this, EpPartListActivity.class);
		Bundle bundle = new Bundle();
		bundle.putLong(EpPartListActivity.EPID_KEY, Long.parseLong(edit.getText().toString()));
		bundle.putString(EpPartListActivity.INITIAL_INFO_KEY,initialInfo);
		intent.putExtras(bundle);
		startActivity(intent);
		runOnUiThread(new Runnable(){
				@Override
				public void run(){
					hideLoadDialog();
				}
			});
	}

	@Override
	public void onSsApiDownloaded(String s)
	{
		EditText edit = (EditText) findViewById(R.id.mainEditText1);
		Intent intent = new Intent(this, SsPartListActivity.class);
		Bundle bundle = new Bundle();
		bundle.putLong(SsPartListActivity.EPID_KEY, Long.parseLong(edit.getText().toString()));
		bundle.putString(SsPartListActivity.INITIAL_INFO_KEY,s);
		intent.putExtras(bundle);
		startActivity(intent);
		runOnUiThread(new Runnable(){
				@Override
				public void run(){
					hideLoadDialog();
				}
			});
	}
	
}
