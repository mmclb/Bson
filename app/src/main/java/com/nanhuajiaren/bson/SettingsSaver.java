package com.nanhuajiaren.bson;
import android.os.Environment;
import com.nanhuajiaren.BasicIO.BasicIO;
import com.google.gson.Gson;
import java.io.File;
import android.content.Context;

public class SettingsSaver
{
	private Context ctx;

	public SettingsSaver(Context ctx)
	{
		this.ctx = ctx;
		init();
	}
	
	public String getFilePath(){
		return ctx.getExternalFilesDir("") + "/settings.json";
	}
	
	private void init(){
		File f = new File(getFilePath());
		f.getParentFile().mkdirs();
		if(!f.exists()){
			try{
				f.createNewFile();
				BasicIO.write(f,new Gson().toJson(new AppSettings()),false);
			}catch(Exception e){
				
			}
		}
	}
	
	public void save(String settings){
		try{
			BasicIO.write(getFilePath(),settings,false);
		}catch(Exception e){
			
		}
	}
	
	public void save(AppSettings settings){
		save(new Gson().toJson(settings));
	}
	
	public AppSettings read(){
		try{
			String t = BasicIO.read(getFilePath());
			return new Gson().fromJson(t,AppSettings.class);
		}catch(Exception e){
			
		}
		return new AppSettings();
	}
}
