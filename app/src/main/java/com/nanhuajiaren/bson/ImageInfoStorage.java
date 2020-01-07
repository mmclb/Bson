package com.nanhuajiaren.bson;
import java.util.List;
import java.util.ArrayList;
import com.google.gson.Gson;

public class ImageInfoStorage
{
	public List<WebImageInfo> info_list = new ArrayList<WebImageInfo>();
	
	public class WebImageInfo{
		public String url;
		public String desc;
	}

	@Override
	public String toString()
	{
		return new Gson().toJson(this);
	}
	
	public static ImageInfoStorage fromString(String json){
		return new Gson().fromJson(json,ImageInfoStorage.class);
	}
}
