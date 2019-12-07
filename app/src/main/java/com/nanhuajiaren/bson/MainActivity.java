package com.nanhuajiaren.bson;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import com.google.gson.Gson;
import com.nanhuajiaren.BasicIO.BasicIO;

public class MainActivity extends AppCompatActivity
{
	private String modelJson;
	private BiliEntry modelEntry;
	Gson gson = new Gson();
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		setupViews();
		load();
    }

	@Override
	protected void onResume()
	{
		// TODO: Implement this method
		super.onResume();
		checkInternetAccess();
	}

	private void setupViews()
	{
		Toolbar tool = (Toolbar) findViewById(R.id.toolbar);
		tool.setTitle(R.string.app_name);
		tool.setBackgroundResource(R.color.colorMain);
		setSupportActionBar(tool);
	}

	protected void print(String s)
	{
		Toast.makeText(this, s, Toast.LENGTH_LONG).show();
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

	protected void checkInternetAccess()
	{
		int connection = getConnectedType();
		if (connection == -1)
		{
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
	}

    public void openSettingUI()
	{
        startActivity(new Intent(Settings.ACTION_SETTINGS));
    }
	
	private void load(){
		try{
			modelJson = BasicIO.read(getAssets().open("entry.model.json"));
			modelEntry = gson.fromJson(modelJson,BiliEntry.class);
		}catch(Exception e){
			showException(e);
		}
	}
	
	private void showException(Exception e){
		print(getString(R.string.read_error,e.getClass().getName()));
	}
}
