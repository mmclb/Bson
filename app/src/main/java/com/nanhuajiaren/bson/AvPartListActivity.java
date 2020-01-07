package com.nanhuajiaren.bson;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.nanhuajiaren.BasicIO.BasicIO;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.CompoundButton;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.MenuItem;
import android.view.Menu;
import android.net.Uri;
import android.util.Base64;
import com.nanhuajiaren.threadannotation.MustCalledInUIThread;
import android.text.SpannableString;
import com.nanhuajiaren.bson.AvData.Data.Stat;
import android.text.SpannableStringBuilder;
import android.text.style.URLSpan;
import android.text.Spannable;

public class AvPartListActivity extends PartListActivity<AvData>
{
	public static final String AVID_KEY = "AVID_KEY";
	public static final String AVINFO_KEY = "AVINFO_KEY";
	private static final String BILIBILI_Uri = "bilibili://video/";

	@Override
	public String getTypeIdKey()
	{
		return AVID_KEY;
	}

	@Override
	public String getDataKey()
	{
		return AVINFO_KEY;
	}

	@Override
	public boolean isAvailable()
	{
		return typeInfo.code == 0;
	}

	@Override
	public boolean showStats()
	{
		return isAvailable() && getSettings().showStats;
	}

	@Override
	public int getTotalPages()
	{
		List<AvData.Data.Pages> pages = typeInfo.data.pages;
		//在此记录一件诡异的事:
		//我写完代码一直发现av3闪退，但是一直没有弄，
		//重构了以后决定修一下
		//结果我发现这里获得的pages是null所以空指针闪退
		//修复完了能正常使用
		//问题在于当我用浏览器访问同一api接口时
		//返回的code是62002
		//我不知道为什么会发生这种现象
		if(pages != null){
			return pages.size();
		}else{
			return 0;
		}
	}
	
	@Override
	public long getAvid(int part)
	{
		return typeId;
	}

	@Override
	public int getPartInAv(int part)
	{
		return part;
	}
	
	@Override
	public String getVideoTitle()
	{
		return typeInfo.data.title;
	}

	@Override
	public String getPartInfo(int part)
	{
		return getString(R.string.cid_format,typeInfo.data.pages.get(part).cid);
	}

	@Override
	public SpannableString getInfo()
	{
		SpannableStringBuilder ssb = new SpannableStringBuilder();
		ssb.append(getString(R.string.uploader_format));
		String name = typeInfo.data.owner.name;
		SpannableString ss = new SpannableString(name);
		ss.setSpan(new URLSpan("bilibili://space/" + typeInfo.data.owner.mid),0,name.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ssb.append(ss);
		ssb.append("\n\n");
		ssb.append(typeInfo.data.desc);
		return new SpannableString(ssb);
	}

	@Override
	public AvData.Data.Stat getStat()
	{
		return typeInfo.data.stat;
	}

	@Override
	public String getErrorHandlingData(String error)
	{
		return "{\"code\":-404,\"e\":\"" + error+ "\"}";
	}

	@Override
	public void spawnEntry(int part)
	{
		helper.write(part);
	}

	@Override
	public String formatTitle()
	{
		return getString(R.string.avid_format,typeId);
	}

	@Override
	public String getPartTitle(int part)
	{
		return typeInfo.data.pages.get(part).part;
	}

	@Override
	public double getPartProgress(int part)
	{
		return helper.getProgress(part);
	}

	@Override
	public int getCid(int part)
	{
		return typeInfo.data.pages.get(part).cid;
	}

	@Override
	public String getCoverUrl(int part)
	{
		return typeInfo.data.pic;
	}
	
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item)
//	{
//		switch(item.getItemId()){
//			case R.id.item_open_in_bilibili:
//				Intent i = new Intent();
//				Uri uri = Uri.parse(BILIBILI_Uri + avId);
//				i.setData(uri);
//				i.setAction(Intent.ACTION_VIEW);
//				try{startActivity(i);}catch(Exception e){print(e.toString());}
//				break;
//			case R.id.item_open_cover:
//				Intent i2 = new Intent();
//				if(avResult.code != 0){
//					break;
//				}
//				Uri uri2 = Uri.parse(avResult.data.pic);
//				i2.setData(uri2);
//				i2.setAction(Intent.ACTION_VIEW);
//				startActivity(i2);
//				break;
//		}
//		return true;
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu)
//	{
//		getMenuInflater().inflate(R.menu.menu_avpartlist,menu);
//		return true;
//	}
//	
}
