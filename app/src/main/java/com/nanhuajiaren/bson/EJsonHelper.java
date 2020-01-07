package com.nanhuajiaren.bson;
import java.io.File;
import com.nanhuajiaren.BasicIO.BasicIO;
import com.google.gson.Gson;
import java.io.IOException;
import com.google.gson.JsonSyntaxException;

public class EJsonHelper
{
	private PartListActivity activity;
	private BiliEntry modelEntry;
	
	public EJsonHelper(PartListActivity activity)
	{
		this.activity = activity;
		try
		{
			String modelJson = BasicIO.read(activity.getAssets().open("entry.model.json"));
			modelEntry = new Gson().fromJson(modelJson, BiliEntry.class);
		}
		catch (Exception e)
		{
			activity.finish();
		}
	}
	
	public double getProgress(int part)
	{
		int partInAv = activity.getPartInAv(part);
		if(partInAv < 0){
			return -36d;
		}
		File entryFile = new File(activity.getSettings().bilibiliDownloadDirectory + activity.getAvid(part) + "/" + (activity.getPartInAv(part) + 1) + "/entry.json");
		if (!entryFile.exists())
		{
			return -1d;
		}
		else
		{
			try
			{
				String entryString = BasicIO.read(entryFile);
				BiliEntry entry = new Gson().fromJson(entryString, BiliEntry.class);
				double downloadedBytes = entry.downloaded_bytes;
				double totalBytes = entry.total_bytes;
				if (totalBytes != 0d)
				{
					return downloadedBytes / totalBytes;
				}
				else
				{
					return 0d;
				}
			}
			catch (IOException e)
			{
				return -2d;
			}
			catch (JsonSyntaxException e)
			{
				return -3d;
			}
		}
	}

	public void write(BiliEntry entry)
	{
		File entryFile = new File(activity.getSettings().bilibiliDownloadDirectory + entry.avid + "/" + entry.page_data.page + "/entry.json");
		entryFile.getParentFile().mkdirs();
		try
		{
			BasicIO.write(entryFile, new Gson().toJson(entry), false);
		}
		catch (IOException e)
		{}
	}

	public void write(int part)
	{
		if(activity.getPartInAv(part) < 0){
			return;
		}
		BiliEntry entry = modelEntry;
		entry.time_create_stamp = System.currentTimeMillis();
		entry.time_update_stamp = System.currentTimeMillis();
		entry.title = activity.getVideoTitle();
		entry.type_tag = activity.getSettings().typeTag;
		entry.cover = activity.getCoverUrl(part);
		entry.prefered_video_quality = activity.getSettings().preferedQuality;
		AvData.Data.Stat stat = activity.getStat();
		entry.danmaku_count = stat == null ? 0 : stat.danmaku;
		entry.avid = activity.getAvid(part);
		entry.page_data.cid = activity.getCid(part);
		entry.page_data.page = activity.getPartInAv(part) + 1;
		entry.page_data.part = activity.getPartTitle(part);
		write(entry);
	}
}
