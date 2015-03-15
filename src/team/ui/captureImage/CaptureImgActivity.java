package team.ui.captureImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import wh.self.searchpic.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Bitmap.CompressFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.SurfaceHolder.Callback;
import android.widget.EditText;
import android.widget.ImageView;

public class CaptureImgActivity extends Activity{
	SurfaceView sView;
	SurfaceHolder surfaceHolder;
	int screenWidth, screenHeight;
	// 定义系统所用的照相机
	Camera camera;
	// 是否在预览中
	boolean isPreview = false;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 获取传递来的Intent信息用于调试
		Intent intent = getIntent();
		String value = intent.getStringExtra("test_intent_capture");
		System.out.println(value);
		// 设置全屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
			WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.capture_img_activity);
		// 获取窗口管理器
		WindowManager wm = getWindowManager();
		Display display = wm.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		// 获取屏幕的宽和高
		display.getMetrics(metrics);
		screenWidth = metrics.widthPixels;
		screenHeight = metrics.heightPixels;
		// 获取界面中SurfaceView组件
		sView = (SurfaceView) findViewById(R.id.sView);
		// 设置该Surface不需要自己维护缓冲区
		sView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		// 获得SurfaceView的SurfaceHolder
		surfaceHolder = sView.getHolder();
		// 为surfaceHolder添加一个回调监听器
		surfaceHolder.addCallback(new Callback()
		{
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
				int width, int height)
			{
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder)
			{
				// 打开摄像头
				initCamera();
			}

			@Override
			public void surfaceDestroyed(SurfaceHolder holder)
			{
				// 如果camera不为null ,释放摄像头
				if (camera != null)
				{
					if (isPreview) camera.stopPreview();
					camera.release();
					camera = null;
				}
			}
		});
	}

	private void initCamera()
	{
		if (!isPreview)
		{
			// 此处默认打开后置摄像头。
			// 通过传入参数可以打开前置摄像头
			camera = Camera.open(0);  //①
			camera.setDisplayOrientation(90);
		}
		if (camera != null && !isPreview)
		{
			try
			{
				Camera.Parameters parameters = camera.getParameters();
				// 设置预览照片的大小
				parameters.setPreviewSize(screenWidth, screenHeight);
				// 设置预览照片时每秒显示多少帧的最小值和最大值
				parameters.setPreviewFpsRange(4, 10);
				// 设置图片格式
				parameters.setPictureFormat(ImageFormat.JPEG);
				// 设置JPG照片的质量
				parameters.set("jpeg-quality", 85);
				// 设置照片的大小
				parameters.setPictureSize(screenWidth, screenHeight);
				// 通过SurfaceView显示取景画面
				camera.setPreviewDisplay(surfaceHolder);  //②
				// 开始预览
				camera.startPreview();  //③
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			isPreview = true;
		}
	}

	public void capture(View source)
	{
		if (camera != null)
		{
			// 控制摄像头自动对焦后才拍照
			camera.autoFocus(autoFocusCallback);  //④
		}
	}

	AutoFocusCallback autoFocusCallback = new AutoFocusCallback()
	{
		// 当自动对焦时激发该方法
		@Override
		public void onAutoFocus(boolean success, Camera camera)
		{
			if (success)
			{
				// takePicture()方法需要传入3个监听器参数
				// 第1个监听器：当用户按下快门时激发该监听器
				// 第2个监听器：当相机获取原始照片时激发该监听器
				// 第3个监听器：当相机获取JPG照片时激发该监听器
				camera.takePicture(new ShutterCallback()
				{
					public void onShutter()
					{
						// 按下快门瞬间会执行此处代码
					}
				}, new PictureCallback()
				{
					public void onPictureTaken(byte[] data, Camera c)
					{
						// 此处代码可以决定是否需要保存原始照片信息
					}
				}, myJpegCallback);  //⑤
			}
		}
	};

	PictureCallback myJpegCallback = new PictureCallback()
	{
		@Override
		public void onPictureTaken(byte[] data, Camera camera)
		{
			// 设置拍照后照片的保存路径
			final String path = Environment.getExternalStorageDirectory().toString() 
					+ "/Pictures/SearchPic";
			// 根据拍照所得的数据创建位图
			final Bitmap bm = BitmapFactory.decodeByteArray(data, 0,
				data.length);
			// 加载/layout/saver.xml文件对应的布局资源
			View saveDialog = getLayoutInflater().inflate(R.layout.saver,
				null);
			final EditText photoName = (EditText) saveDialog
				.findViewById(R.id.phone_name);
			// 获取saveDialog对话框上的ImageView组件
			ImageView show = (ImageView) saveDialog
				.findViewById(R.id.show);
			// 显示刚刚拍得的照片
			show.setImageBitmap(bm);
			// 使用对话框显示saveDialog组件
			new AlertDialog.Builder(CaptureImgActivity.this).setView(saveDialog)
				.setPositiveButton("保存", new OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						// 创建一个位于SD卡上的文件
//						File file = new File(Environment.getExternalStorageDirectory(), 
//							photoName.getText().toString() + ".jpg");
						File file = new File( path, photoName.getText().toString() + ".jpg");
						FileOutputStream outStream = null;
						try
						{
							// 打开指定文件对应的输出流
							outStream = new FileOutputStream(file);
							// 把位图输出到指定文件中
							bm.compress(CompressFormat.JPEG, 100,
								outStream);
							outStream.close();
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}
					}
				}).setNegativeButton("取消", null).show();
			// 重新浏览
			camera.stopPreview();
			camera.startPreview();
			isPreview = true;
		}
	};
}
