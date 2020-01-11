package com.nanhuajiaren.bson;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.CallSuper;
import android.support.v7.widget.Toolbar;
import android.support.annotation.StringRes;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.app.AlertDialog;
import com.nanhuajiaren.threadannotation.MustCalledInUIThread;
import android.os.Bundle;
import android.content.Intent;
import android.content.ComponentName;

public abstract class BaseActivity extends AppCompatActivity
{
	private AlertDialog loadDialog;
	protected SettingsSaver settingsHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		settingsHelper = new SettingsSaver(this);
	}
	
	@CallSuper
	@MustCalledInUIThread
	protected void setupViews(String title){
		Toolbar tool = (Toolbar) findViewById(R.id.toolbar);
		tool.setTitle(title);
		tool.setBackgroundResource(R.color.colorMain);
		setSupportActionBar(tool);
	}
	
	@CallSuper
	@MustCalledInUIThread
	protected void setupViews(@StringRes int titleId){
		setupViews(getString(titleId));
	}
	
	@CallSuper
	@MustCalledInUIThread
	protected void setupViews(){
		setupViews(R.string.app_name);
	}
	
	@CallSuper
	@MustCalledInUIThread
	protected void setBackIconEnabled(boolean enabled){
		getSupportActionBar().setDisplayHomeAsUpEnabled(enabled);//左侧添加一个默认的返回图标
		getSupportActionBar().setHomeButtonEnabled(enabled); //设置返回键可用
	}
	
	@Override
	@CallSuper
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId()){
			case android.R.id.home:
				return onHomePressed();
		}
		return false;
	}
	
	public boolean onHomePressed(){
		finish();
		return true;
	}
	
	public AppSettings getSettings(){
		return settingsHelper.read();
	}
	
	public void print(String string){
		Toast.makeText(this,string,Toast.LENGTH_LONG).show();
	}
	
	public void print(@StringRes int res){
		print(getString(res));
	}
	
	public void print(@StringRes int res,Object[] obj){
		print(getString(res,obj));
	}
	
	@MustCalledInUIThread
	public final void showLoadDialog(){
		if(loadDialog == null){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setView(R.layout.load_dialog);
			builder.setCancelable(false);
			loadDialog = builder.show();
		}
	}

	@MustCalledInUIThread
	public final void hideLoadDialog(){
		if(loadDialog != null){
			loadDialog.dismiss();
			loadDialog =null;
		}
	}

	@Override
	public void startActivity(Intent intent)
	{
		try{
			super.startActivity(intent);
		}catch(Exception e){
			print(getString(R.string.no_suitable_app,e.toString()));
		}
	}
	
	public Intent getBiliBaseIntent(){
		Intent intent = new Intent();
		if(getSettings().forceUseBilibiliApp){
			intent.setComponent(new ComponentName("tv.danmaku.bili","tv.danmaku.bili.ui.intent.IntentHandlerActivity"));
		}
		return intent;
	}
}
