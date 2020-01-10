package com.nanhuajiaren.bson;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.BaseAdapter;
import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.io.File;
import com.bumptech.glide.request.target.Target;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageShowingActivity extends BaseActivity
{

	public static final String IMAGE_INFO_KEY = "IMAGE_INFO";
	
	private ListView listView;
	private String imageInfoString;
	private ImageInfoStorage imageInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_showing);
		Bundle bundle = getIntent().getExtras();
		imageInfoString = bundle.getString(IMAGE_INFO_KEY);
		imageInfo = ImageInfoStorage.fromString(imageInfoString);
		listView = (ListView) findViewById(R.id.imageshowingListView1);
		listView.setAdapter(new ImageAdapter());
	}
	
	private class ImageAdapter extends BaseAdapter
	{

		@Override
		public int getCount()
		{
			return imageInfo.infoList.size();
		}

		@Override
		public Object getItem(int p1)
		{
			return null;
		}

		@Override
		public long getItemId(int p1)
		{
			return p1;
		}

		@Override
		public View getView(int p1, View p2, ViewGroup p3)
		{
			if(p2 == null){
				p2 = LayoutInflater.from(ImageShowingActivity.this).inflate(R.layout.image_showing_item,p3,false);
			}
			ImageView mainImage = (ImageView) p2.findViewById(R.id.imageshowingitemImageView1);
			ImageView downloadButton = (ImageView) p2.findViewById(R.id.imageshowingitemImageView2);
			TextView text = (TextView) p2.findViewById(R.id.imageshowingitemTextView1);
			text.setText(imageInfo.infoList.get(p1).desc);
			Glide.with(ImageShowingActivity.this).load(imageInfo.infoList.get(p1).url).into(mainImage);
			downloadButton.setOnClickListener(new OnClickListenerWithData<ImageInfoStorage.WebImageInfo>(imageInfo.infoList.get(p1)){
				@Override
				public void onClick(View v){
					DownLoadImageService service = new DownLoadImageService(
						getData().url,
						new ImageDownLoadCallBack() {

							@Override
							public void onDownLoadSuccess(File file) {
								print(getString(R.string.pic_success,getData().desc,file.getAbsolutePath()));
							}

							@Override
							public void onDownLoadFailed() {
								print(getString(R.string.pic_fail,getData().desc));
							}
						});
					//启动图片下载线程
					runOnQueue(service);
				}
			});
			return p2;
		}
	}
	
	public interface ImageDownLoadCallBack {

		void onDownLoadSuccess(File file);

		void onDownLoadFailed();
	}
	
	
	public class DownLoadImageService implements Runnable {
		private String url;
		private ImageDownLoadCallBack callBack;

		public DownLoadImageService(String url, ImageDownLoadCallBack callBack) {
			this.url = url;
			this.callBack = callBack;
		}

		@Override
		public void run() {
			File file = null;
			try {
				file = Glide.with(ImageShowingActivity.this)
                    .load(url)
                    .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (file != null) {
					callBack.onDownLoadSuccess(file);
				} else {
					callBack.onDownLoadFailed();
				}
			}
		}

	}
	
	private static ExecutorService singleExecutor = null;


    /**
     * 执行单线程列队执行
     */
    public void runOnQueue(Runnable runnable) {
        if (singleExecutor == null) {
            singleExecutor = Executors.newSingleThreadExecutor();
        }
        singleExecutor.submit(runnable);
    }
	
}
