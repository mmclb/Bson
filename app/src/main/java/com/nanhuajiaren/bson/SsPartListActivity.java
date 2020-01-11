package com.nanhuajiaren.bson;

public class SsPartListActivity extends EpPartListActivity
{
	@Override
	public String formatTitle()
	{
		return getString(R.string.ssid_format,typeId);
	}
}
