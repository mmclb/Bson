package com.nanhuajiaren.bson;
import android.os.Bundle;
import android.content.Intent;
import java.util.List;
import java.util.ArrayList;
import com.nanhuajiaren.threadannotation.MustCalledInUIThread;
import android.widget.Switch;
import android.widget.CompoundButton;
import android.view.View;
import android.widget.CheckBox;
import com.google.gson.Gson;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.text.SpannableString;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ProgressBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.net.Uri;
import android.view.Menu;

public abstract class PartListActivity<T> extends BaseActivity
{
	protected long typeId = -1;
	private String typeInfoString;
	protected T typeInfo;
	protected EJsonHelper helper;

	public abstract String getTypeIdKey()
	public abstract String getDataKey()
	public abstract String getErrorHandlingData(String error)
	public abstract String formatTitle()
	public abstract String getVideoTitle()
	public abstract SpannableString getInfo()
	public abstract String getPartTitle(int part)
	public abstract int getTotalPages()
	public abstract double getPartProgress(int part)
	public abstract String getPartInfo(int part)
	public abstract boolean isAvailable()
	public abstract boolean showStats()
	public abstract AvData.Data.Stat getStat()
	public abstract void spawnEntry(int part)
	public abstract long getAvid(int part)
	public abstract int getCid(int part)
	public abstract String getCoverUrl(int part)
	public abstract int getPartInAv(int part)
	public abstract String getBiliUri()
	public abstract ImageInfoStorage getImageInfo()
	public abstract Class<T> getTClass()
	
	private List<Integer> importButtonIds = new ArrayList<Integer>();
	private List<Integer> selectCheckBoxIds = new ArrayList<Integer>();
	private List<Integer> progressBarIds = new ArrayList<Integer>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		helper = new EJsonHelper(this);
		setContentView(R.layout.part_list);
		Intent intent = getIntent();
		Bundle extra = intent.getExtras();
		typeId = extra.getLong(getTypeIdKey(), typeId);
		typeInfoString = extra.getString(getDataKey(),getErrorHandlingData("onCreate"));
		if (typeId >= 0)
		{
			setupViews(formatTitle());
			showResult();
			if(isAvailable()){
				onLoadSuccess();
			}
		}
		else
		{
			setupViews("lbwnb");
		}
		setBackIconEnabled(true);
		switchIsSelecting(false);
	}
	
	protected void onLoadSuccess(){
		
	}
	
	@Override
	@MustCalledInUIThread
	protected void setupViews(String title)
	{
		// TODO: Implement this method
		super.setupViews(title);
		Switch s = (Switch) findViewById(R.id.partlistSwitch1);
		s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(CompoundButton p1, boolean p2)
				{
					switchIsSelecting(p2);
				}
			});
	}
	
	@MustCalledInUIThread
	public void onButtonClick(View v)
	{
		switch (v.getId())
		{
			case R.id.partlistButton1:
				for (int i = 0;i < selectCheckBoxIds.size();i ++)
				{
					((CheckBox) findViewById(selectCheckBoxIds.get(i))).setChecked(true);
				}
				break;
			case R.id.partlistButton2:
				for (int i = 0;i < selectCheckBoxIds.size();i ++)
				{
					((CheckBox) findViewById(selectCheckBoxIds.get(i))).setChecked(false);
				}
				break;
			case R.id.partlistButton3:
				for (int i = 0;i < selectCheckBoxIds.size();i ++)
				{
					if (((CheckBox) findViewById(selectCheckBoxIds.get(i))).isChecked())
					{
						spawn(i, false);
					}
				}
				break;
		}
	}

	@MustCalledInUIThread
	public void switchIsSelecting(boolean selecting)
	{
		int visibilityA = selecting ? View.VISIBLE : View.GONE;
		int visibilityB = !selecting ? View.VISIBLE : View.GONE;
		findViewById(R.id.partlistLinearLayout2).setVisibility(visibilityA);
		for (int i = 0;i < importButtonIds.size();i ++)
		{
			findViewById(importButtonIds.get(i)).setVisibility(visibilityB);
		}
		for (int i = 0;i < selectCheckBoxIds.size();i ++)
		{
			findViewById(selectCheckBoxIds.get(i)).setVisibility(visibilityA);
		}
	}
	
	@MustCalledInUIThread
	public void showResult()
	{
		try
		{
			typeInfo = new Gson().fromJson(typeInfoString,getTClass());
		}
		catch (Exception e)
		{
			typeInfo = new Gson().fromJson(getErrorHandlingData(e.toString()),getTClass());
		}
		TextView text1 = (TextView) findViewById(R.id.partlistTextView1);
		TextView text2 = (TextView) findViewById(R.id.partlistTextView2);
		LinearLayout layoutStats = (LinearLayout) findViewById(R.id.partlistLinearLayoutStats);
		TextView text4 = (TextView) findViewById(R.id.partlistTextView4);
		TextView text5 = (TextView) findViewById(R.id.partlistTextView5);
		TextView text6 = (TextView) findViewById(R.id.partlistTextView6);
		TextView text7 = (TextView) findViewById(R.id.partlistTextView7);
		TextView text8 = (TextView) findViewById(R.id.partlistTextView8);
		TextView text9 = (TextView) findViewById(R.id.partlistTextView9);
		LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.partlistLinearLayout1);
		if (!isAvailable())
		{
			//视频无效
			text1.setText(getString(R.string.api_error));
			text2.setText(typeInfoString);
			layoutStats.setVisibility(View.GONE);
		}
		else
		{
			//视频有效
			text1.setText(getVideoTitle());
			text2.setText(getInfo());
			text2.setMovementMethod(LinkMovementMethod.getInstance());
			AvData.Data.Stat stat = getStat();
			if (showStats() && stat != null)
			{
				layoutStats.setVisibility(View.VISIBLE);
				text4.setText(getString(R.string.view_format, reformNumber(stat.view)));
				text5.setText(getString(R.string.danmaku_format, reformNumber(stat.danmaku)));
				text6.setText(getString(R.string.reply_format, reformNumber(stat.reply)));
				text7.setText(getString(R.string.like_format, reformNumber(stat.like)));
				text8.setText(getString(R.string.coin_format, reformNumber(stat.coin)));
				text9.setText(getString(R.string.favorite_format, reformNumber(stat.favorite)));
			}
			else
			{
				layoutStats.setVisibility(View.GONE);
			}
			for (int i = 0;i < getTotalPages();i ++)
			{
				linearLayout1.addView(getCellView(i, linearLayout1));
			}
		}
	}
	
	public static String reformNumber(int number)
	{
		if (number < 10000)
		{
			return number + "";
		}
		else
		{
			return ((double)(number / 1000)) / 10 + "w";
		}
	}
	
	private View getCellView(int p1,ViewGroup p3)
	{
		View p2 = LayoutInflater.from(this).inflate(R.layout.avinfo_item, p3, false);
		TextView text1 = (TextView) p2.findViewById(R.id.avinfoitemTextView1);
		TextView text2 = (TextView) p2.findViewById(R.id.avinfoitemTextView2);
		Button button = (Button) p2.findViewById(R.id.avinfoitemButton1);
		ProgressBar bar = (ProgressBar) p2.findViewById(R.id.avinfoitemProgressBar1);
		double progress = getPartProgress(p1);
		bar.setMax(100);
		if (progress < 0)
		{
			bar.setVisibility(View.GONE);
		}
		else
		{
			bar.setProgress((int)(100 * progress));
		}
		int progressId = View.generateViewId();
		bar.setId(progressId);
		progressBarIds.add(progressId);
		int buttonId = View.generateViewId();
		button.setId(buttonId);
		importButtonIds.add(buttonId);
		button.setOnClickListener(new OnClickListenerWithData<Integer>(p1){
				@Override
				public void onClick(View v)
				{
					spawn(getData(), false);
				}
			});
		CheckBox checkBox = (CheckBox) p2.findViewById(R.id.avinfoitemCheckBox1);
		int checkBoxId = View.generateViewId();
		checkBox.setId(checkBoxId);
		selectCheckBoxIds.add(checkBoxId);
		text1.setText(getPartTitle(p1));
		text2.setText(getPartInfo(p1));
		return p2;
	}
	
	@MustCalledInUIThread
	private void spawn(int part, boolean forced)
	{
		if (getPartProgress(part) >= 0 && !forced)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(getString(R.string.already_spawned_warning, getPartTitle(part)));
			builder.setPositiveButton(R.string.still_continue, new DialogInterfaceOnClickListenerWithData<Integer>(part){
					@Override
					public void onClick(DialogInterface p1, int p2)
					{
						spawn(getData(), true);
					}
				});
			builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface p1, int p2)
					{

					}
				});
			builder.show();
			return;
		}
		spawnEntry(part);
		refreshProgress(part);
	}
	
	@MustCalledInUIThread
	public void refreshProgress(int part)
	{
		ProgressBar bar = (ProgressBar) findViewById(progressBarIds.get(part));
		double progress = getPartProgress(part);
		bar.setMax(100);
		if (progress < 0)
		{
			bar.setVisibility(View.GONE);
		}
		else
		{
			bar.setProgress((int)(100 * progress));
			bar.setVisibility(View.VISIBLE);
		}
	}
	
	@MustCalledInUIThread
	public void refreshAllProgress(){
		for(int i = 0;i < progressBarIds.size();i ++){
			refreshProgress(i);
		}
	}

	@Override
	protected void onResume()
	{
		// TODO: Implement this method
		super.onResume();
		refreshAllProgress();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if(super.onOptionsItemSelected(item)){
			return true;
		}
		switch(item.getItemId()){
			case R.id.item_open_in_bilibili:
				Intent i = getBiliBaseIntent();
				Uri uri = Uri.parse(getBiliUri());
				i.setData(uri);
				i.setAction(Intent.ACTION_VIEW);
				try{startActivity(i);}catch(Exception e){print(e.toString());}
				break;
			case R.id.item_open_cover:
				ImageInfoStorage iis = getImageInfo();
				if(iis == null){
					return true;
				}
				Intent intent = new Intent(PartListActivity.this,ImageShowingActivity.class);
				Bundle b = new Bundle();
				String s = iis.toString();
				b.putString(ImageShowingActivity.IMAGE_INFO_KEY,s);
				b.putString(ImageShowingActivity.PAGE_TITLE_KEY,formatTitle());
				intent.putExtras(b);
				startActivity(intent);
				break;
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_partlist,menu);
		return true;
	}
	
}
