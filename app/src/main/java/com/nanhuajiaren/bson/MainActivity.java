package com.nanhuajiaren.bson;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import com.nanhuajiaren.threadannotation.CalledInOtherThreads;
import com.nanhuajiaren.threadannotation.MustCalledInUIThread;

public class MainActivity extends BaseActivity implements AvConnection.OnAvApiDownloadedListener,EpConnection.EpApiDownloadListener
{
	
	private static final int REQUIRE_EXTERNAL_STORAGE = 1;

	private boolean haveMobileDataPromoted = false;
	
	private boolean isEp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		setupViews();
		requireExternalStorage();
    }

	@Override
	@MustCalledInUIThread
	protected void setupViews(String title)
	{
		super.setupViews(title);
		RadioGroup rg = (RadioGroup) findViewById(R.id.mainRadioGroup1);
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(RadioGroup radiogroup, int id)
				{
					updateEditTextHint(id);
				}
			});
		updateEditTextHint(R.id.mainRadioButton1);
	}

	@MustCalledInUIThread
	private void updateEditTextHint(int radioButtonId)
	{
		EditText edit = (EditText) findViewById(R.id.mainEditText1);
		edit.setHint(getString(R.string.no_letters, ((RadioButton) findViewById(radioButtonId)).getText().toString()));
		isEp = radioButtonId == R.id.mainRadioButton2;
	}

	@Override
	protected void onResume()
	{
		// TODO: Implement this method
		super.onResume();
		checkInternetAccess();
	}

	public int getConnectedType()
	{
        ConnectivityManager mConnectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
		if (mNetworkInfo != null && mNetworkInfo.isAvailable())
		{
			return mNetworkInfo.getType();
		}
        return -1;
    }

	@MustCalledInUIThread
	protected void checkInternetAccess()
	{
		int connection = getConnectedType();
		if (connection == -1)
		{
			//无连接
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.no_internet_title);
			builder.setMessage(R.string.no_internet_description);
			builder.setCancelable(false);
			builder.setPositiveButton(R.string.go_to_settings, new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface p1, int p2)
					{
						openSettingUI();
					}
				});
			builder.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface p1, int p2)
					{
						checkInternetAccess();
					}
				});
			builder.show();
		}
		else if (connection == 0 && !haveMobileDataPromoted && getSettings().promotOnMobileData)
		{
			//移动数据连接
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.mobile_data_title);
			builder.setMessage(R.string.mobile_data_description);
			builder.setCancelable(true);
			builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface p1, int p2)
					{

					}
				});
			builder.show();
			haveMobileDataPromoted = true;
		}
	}

    public void openSettingUI()
	{
        startActivity(new Intent(Settings.ACTION_SETTINGS));
    }

	protected void requireExternalStorage()
	{
		if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
		{
			requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUIRE_EXTERNAL_STORAGE);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
	{
		// TODO: Implement this method
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		boolean flag = grantResults.length > 0;
		for (int i = 0;i < grantResults.length;i ++)
		{
			if (grantResults[i] != PackageManager.PERMISSION_GRANTED)
			{
				flag = false;
			}
		}
		if (!flag)
		{
			finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.item_settings:
				startActivity(new Intent(this, AppSettingsActivity.class));
				return true;
			case R.id.item_about:
				startActivity(new Intent(this, AboutActivity.class));
				return true;
			case R.id.item_freead:
				freead();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@MustCalledInUIThread
	public void onImportButtonClicked(View v)
	{
		EditText edit = (EditText) findViewById(R.id.mainEditText1);
		if (!edit.getText().toString().equals(""))
		{
			if (!isEp)
			{
				//av型
				showLoadDialog();
				new AvConnection(Long.parseLong(edit.getText().toString()),this).startAvApiRequest();
			}else{
				//ep型
				showLoadDialog();
				new EpConnection(Long.parseLong(edit.getText().toString()),this).startConnection();
			}
		}
	}

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
	
	@MustCalledInUIThread
	public void freead()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.freead_title);
		builder.setMessage(R.string.freead_description);
		builder.setCancelable(true);
		builder.setPositiveButton(R.string.freead_open, new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					Uri uri = Uri.parse("market://details?id=com.rair.gsonformat4aide");
					Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					try
					{
						startActivity(intent);
					}
					catch (Exception ex)
					{
						ex.printStackTrace();
						Toast.makeText(MainActivity.this, getString(R.string.read_error, ex.toString()), Toast.LENGTH_LONG).show();
					}
				}
			});
		builder.show();
	}
}
