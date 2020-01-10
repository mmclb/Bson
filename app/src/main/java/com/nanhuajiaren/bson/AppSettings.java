package com.nanhuajiaren.bson;
import android.os.Environment;

public class AppSettings
{
	private static final String typeTagDefault = "lua.flv.bili2api.80";
	private static final int preferedQualityDefault = 80;
	
	public String bilibiliDownloadDirectory = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/tv.danmaku.bili/download/";
	public boolean promotOnMobileData = true;
	public boolean showStats = true;
	public String typeTag = typeTagDefault;
	public int preferedQuality = preferedQualityDefault;
	public boolean forceUseBilibiliApp = false;
	
	public void recoverDefaultAdvancedSettings(){
		typeTag = typeTagDefault;
		preferedQuality = preferedQualityDefault;
	}
}
