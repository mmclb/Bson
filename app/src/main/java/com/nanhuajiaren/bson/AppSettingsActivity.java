package com.nanhuajiaren.bson;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.text.TextWatcher;
import android.text.Editable;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.view.View;
import com.nanhuajiaren.threadannotation.MustCalledInUIThread;
import java.lang.reflect.Field;
import java.util.List;

public class AppSettingsActivity extends BaseActivity
{
	private AppSettings currentSettings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_settings);
		setupViews(R.string.settings);
		setBackIconEnabled(true);
		loadSettings();
		addListeners();
	}

	@MustCalledInUIThread
	protected void loadSettings(){
		currentSettings = getSettings();
		EditText edit1 = (EditText) findViewById(R.id.appsettingsEditText1);
		edit1.setText(currentSettings.bilibiliDownloadDirectory);
		EditText edit2 = (EditText) findViewById(R.id.appsettingsEditText2);
		edit2.setText(currentSettings.typeTag);
		EditText edit3 = (EditText) findViewById(R.id.appsettingsEditText3);
		edit3.setText(currentSettings.preferedQuality + "");
		CheckBox checkbox1 = (CheckBox) findViewById(R.id.appsettingsCheckBox1);
		checkbox1.setChecked(currentSettings.promotOnMobileData);
		CheckBox checkbox2 = (CheckBox) findViewById(R.id.appsettingsCheckBox2);
		checkbox2.setChecked(currentSettings.showStats);
		CheckBox checkbox3 = (CheckBox) findViewById(R.id.appsettingsCheckBox3);
		checkbox3.setChecked(currentSettings.forceUseBilibiliApp);
	}
	
	@MustCalledInUIThread
	protected void addListeners(){
		EditText edit1 = (EditText) findViewById(R.id.appsettingsEditText1);
		edit1.addTextChangedListener(new SettingsEditTextWatch(){
				@Override
				public void saveSettings(String settings){
					currentSettings.bilibiliDownloadDirectory = settings;
					settingsHelper.save(currentSettings);
				}
			});
		EditText edit2 = (EditText) findViewById(R.id.appsettingsEditText2);
		edit2.addTextChangedListener(new SettingsEditTextWatch(){
				@Override
				public void saveSettings(String settings){
					currentSettings.typeTag = settings;
					settingsHelper.save(currentSettings);
				}
			});
		EditText edit3 = (EditText) findViewById(R.id.appsettingsEditText3);
		edit3.addTextChangedListener(new SettingsEditTextWatch(){
				@Override
				public void saveSettings(String settings){
					if(!settings.equals("")){
						currentSettings.preferedQuality = Integer.parseInt(settings);
						settingsHelper.save(currentSettings);
					}
				}
			});
		CheckBox checkbox1 = (CheckBox) findViewById(R.id.appsettingsCheckBox1);
		checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(CompoundButton p1,boolean p2){
					currentSettings.promotOnMobileData = p2;
					settingsHelper.save(currentSettings);
				}
			});
		CheckBox checkbox2 = (CheckBox) findViewById(R.id.appsettingsCheckBox2);
		checkbox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(CompoundButton p1,boolean p2){
					currentSettings.showStats = p2;
					settingsHelper.save(currentSettings);
				}
			});
		CheckBox checkbox3 = (CheckBox) findViewById(R.id.appsettingsCheckBox3);
		checkbox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(CompoundButton p1,boolean p2){
					currentSettings.forceUseBilibiliApp = p2;
					settingsHelper.save(currentSettings);
				}
			});
	}
	
	private abstract class SettingsEditTextWatch implements TextWatcher
	{

		@Override
		public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4)
		{
			
		}

		@Override
		public void onTextChanged(CharSequence p1, int p2, int p3, int p4)
		{
			
		}

		@Override
		public void afterTextChanged(Editable p1)
		{
			saveSettings(p1.toString());
		}
		
		public abstract void saveSettings(String settings)
	}
	
	@MustCalledInUIThread
	public void onRecoverAdvancedSettingsButtonClicked(View v){
		currentSettings.recoverDefaultAdvancedSettings();
		settingsHelper.save(currentSettings);
		loadSettings();
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		AppSettings settings = getSettings();
		String path = settings.bilibiliDownloadDirectory;
		if(!path.endsWith("/")){
			path = path + "/";
			settings.bilibiliDownloadDirectory = path;
			settingsHelper.save(settings);
		}
	}
}
