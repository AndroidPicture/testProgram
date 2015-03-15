package team.ui.captureImage;

import wh.self.searchpic.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SettingActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		Intent intent = getIntent();
		String value = intent.getStringExtra("test_intent_setting");
		System.out.println(value);
	}
}