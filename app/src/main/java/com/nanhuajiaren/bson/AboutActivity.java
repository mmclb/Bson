package com.nanhuajiaren.bson;
import android.os.Bundle;
import android.widget.TextView;
import com.nanhuajiaren.BasicIO.BasicIO;
import java.io.IOException;
import android.text.Html;
import com.nanhuajiaren.threadannotation.MustCalledInUIThread;

public class AboutActivity extends BaseActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		setupViews(R.string.about);
		setBackIconEnabled(true);
	}

	@Override
	@MustCalledInUIThread
	protected void setupViews(String title)
	{
		super.setupViews(title);
		String description = "";
		try
		{
			description = BasicIO.read(getAssets().open("about.html"));
		}
		catch (IOException e)
		{}
		TextView text = (TextView) findViewById(R.id.aboutTextView1);
		text.setText(Html.fromHtml(description));
	}
}
