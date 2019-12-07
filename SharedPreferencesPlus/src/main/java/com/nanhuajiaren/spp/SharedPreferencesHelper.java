package com.nanhuajiaren.spp;
import android.content.Context;

public class SharedPreferencesHelper
{
	private Context ctx;

	public SharedPreferencesHelper(Context ctx)
	{
		this.ctx = ctx;
	}
	
	public Context getCtx(){
		return ctx;
	}
	
	
}
