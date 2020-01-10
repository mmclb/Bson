package com.nanhuajiaren.bson;
import android.os.Bundle;
import java.util.List;
import com.nanhuajiaren.threadannotation.MustCalledInUIThread;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import android.widget.Switch;
import android.widget.CompoundButton;
import com.nanhuajiaren.BasicIO.BasicIO;
import com.google.gson.Gson;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ProgressBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.nanhuajiaren.bson.AvData.Data.Stat;
import android.text.SpannableString;
import com.nanhuajiaren.threadannotation.CalledInOtherThreads;

public class EpPartListActivity extends PartListActivity<EpData> implements EpAvConnection.OnEpAvApiDownloadedListener
{

	@Override
	public ImageInfoStorage getImageInfo()
	{
		ImageInfoStorage storage = new ImageInfoStorage();
		storage.addInfo(typeInfo.mediaInfo.cover,getString(R.string.ep_cover_normal));
		storage.addInfo(typeInfo.mediaInfo.squareCover,getString(R.string.ep_cover_square));
		for(int i = 0;i < getTotalPages();i ++){
			storage.addInfo(typeInfo.epList.get(i).cover,getString(R.string.ep_cover_part,i + 1));
		}
		return storage;
	}

	@Override
	protected void onLoadSuccess()
	{
		new EpAvConnection(typeInfo, this).startConnection();
		showLoadDialog();
	}

	@Override
	@CalledInOtherThreads
	public void onEpAvApiDownloaded(EpData transformedData)
	{
		typeInfo = transformedData;
		runOnUiThread(new Runnable(){
				@Override
				public void run()
				{
					refreshAllProgress();
					hideLoadDialog();
				}
			});
	}

	@Override
	public String getTypeIdKey()
	{
		return EPID_KEY;
	}

	@Override
	public String getDataKey()
	{
		return INITIAL_INFO_KEY;
	}

	@Override
	public String getErrorHandlingData(String error)
	{
		return "{\"loaded\":false,\"err\":\"" + error + "\"}";
	}

	@Override
	public String formatTitle()
	{
		return getString(R.string.epid_format, typeId);
	}

	@Override
	public String getVideoTitle()
	{
		return typeInfo.mediaInfo.title;
	}

	@Override
	public SpannableString getInfo()
	{
		SpannableString ss = new SpannableString(typeInfo.mediaInfo.evaluate);
		return ss;
	}

	@Override
	public String getPartTitle(int part)
	{
		return typeInfo.epList.get(part).long_title;
	}

	@Override
	public int getTotalPages()
	{
		List<EpData.EpList> eplist = typeInfo.epList;
		if (eplist == null)
		{
			return 0;
		}
		return eplist.size();
	}

	@Override
	public double getPartProgress(int part)
	{
		return helper.getProgress(part);
	}

	@Override
	public String getPartInfo(int part)
	{
		return getString(R.string.epinfo_format, getAvid(part), getCid(part));
	}

	@Override
	public boolean isAvailable()
	{
		return getTotalPages() != 0;
	}

	@Override
	public boolean showStats()
	{
		return false;
	}

	@Override
	public AvData.Data.Stat getStat()
	{
		return null;
	}

	@Override
	public void spawnEntry(int part)
	{
		helper.write(part);
	}

	@Override
	public long getAvid(int part)
	{
		return typeInfo.epList.get(part).aid;
	}

	@Override
	public int getCid(int part)
	{
		return typeInfo.epList.get(part).cid;
	}

	@Override
	public String getCoverUrl(int part)
	{
		return typeInfo.epList.get(part).cover;
	}

	@Override
	public int getPartInAv(int part)
	{
		AvData data = typeInfo.epList.get(part).linkedAvData;
		if (data == null || data.code != 0)
		{
			return -1;
		}
		else
		{
			for (int i = 0;i < data.data.pages.size();i ++)
			{
				if (data.data.pages.get(i).cid == getCid(part))
				{
					return i;
				}
			}
			return -1;
		}
	}

	@Override
	public String getBiliUri()
	{
		return EpConnection.epApiURL + typeId;
	}
	
	public static final String EPID_KEY = "EPID_KEY";
	public static final String INITIAL_INFO_KEY = "INITIAL_INFO_KEY";
}
