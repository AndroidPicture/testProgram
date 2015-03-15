package team.ui.captureImage;

import wh.self.searchpic.MainActivity;
import wh.self.searchpic.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.OnGestureListener;

public class StartLogoActivity extends Activity{
	GestureDetector detector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_picture);
		//Toast.makeText(this,"欢迎您成为SCOS新用户",2000).show();
		GestureListener gestruelistener=new GestureListener();
		detector=new GestureDetector(this, gestruelistener);
			
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return detector.onTouchEvent(event);
	}

	class GestureListener implements OnGestureListener{

		@Override
		public boolean onDown(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onShowPress(MotionEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub
			if(e2.getX()<e1.getX()){
				Intent intent = new Intent();
				intent.putExtra("start_logo", "Cat_picture");
				intent.setClass(StartLogoActivity.this,MainActivity.class);
				StartLogoActivity.this.startActivity(intent);
			}
			return false;
		} 
		
	}
}
