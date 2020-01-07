package com.nanhuajiaren.bson;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.net.Uri;
import android.content.Intent;
import android.widget.CheckBox;
import android.util.Base64;

public class TestingActivity extends BaseActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		setupViews(R.string.test_activity);
	}
	
	public void onTestUriClick(View v){
		EditText edit = (EditText) findViewById(R.id.testEditText1);
		CheckBox check = (CheckBox) findViewById(R.id.testCheckBox1);
		String s = edit.getText().toString();
		if(!s.equals("")){
			if(check.isChecked()){
				s = Base64.encodeToString(s.getBytes(),Base64.DEFAULT);
			}
			Uri uri = Uri.parse("bilibili://" + s);
			Intent i = new Intent();
			i.setData(uri);
			try{
				startActivity(i);
			}catch(Exception e){
				print(e.toString());
			}
		}
	}
}
