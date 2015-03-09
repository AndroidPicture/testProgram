package wh.self.searchpic;

import java.util.Set;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Advanceable;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView test_show = null;
	private Button choose = null;
	private Button set = null;
	private Button photo = null; 
	private Button adv = null;
	
	class chooseListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			todo(R.string.choose);
		}
	}
	
	class setListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			todo(R.string.setting);
		}
	}
	
	class photoListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			todo(R.string.photo);
		}
	}
	
	class advListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			todo(R.string.adv);
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		test_show = (TextView)findViewById(R.id.test_show);
		choose = (Button)findViewById(R.id.choose);
		set = (Button)findViewById(R.id.set);
		photo = (Button)findViewById(R.id.photo);
		adv = (Button)findViewById(R.id.adv);
		choose.setOnClickListener(new chooseListener());
		set.setOnClickListener(new setListener());
		photo.setOnClickListener(new photoListener());
		adv.setOnClickListener(new advListener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public boolean todo(int text)
	{
		test_show.setText(text);
		return true;
	}
}
