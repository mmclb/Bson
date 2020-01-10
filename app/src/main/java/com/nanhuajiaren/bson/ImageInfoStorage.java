package com.nanhuajiaren.bson;
import java.util.List;
import java.util.ArrayList;
import com.google.gson.Gson;

public class ImageInfoStorage
{
	public List<WebImageInfo> infoList = new ArrayList<WebImageInfo>();
	
	public class WebImageInfo{
		public String url;
		public String desc;

		@Override
		public String toString()
		{
			return "{\"url\":\"" + url + "\",\"desc\":\"" + desc + "\"}";
		}
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("{\"infoList\":[");
		for(int i = 0;i < infoList.size();i ++){
			sb.append(infoList.get(i).toString());
			if(i != infoList.size() - 1){
				sb.append(",");
			}
		}
		sb.append("]}");
		return sb.toString();
	}
	
	public static ImageInfoStorage fromString(String json){
		return new Gson().fromJson(json,ImageInfoStorage.class);
	}
	
	public void addInfo(String url,String desc){
		WebImageInfo info = new WebImageInfo();
		info.url = url;
		info.desc = desc;
		infoList.add(info);
	}
}
