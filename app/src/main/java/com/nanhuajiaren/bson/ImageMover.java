package com.nanhuajiaren.bson;
import java.io.File;
import android.os.Environment;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ImageMover
{
	private String getWorkPath()
	{
		return Environment.getExternalStorageDirectory().getAbsolutePath() + "/Bson/";
	}

	public ImageMover()
	{
		File outputDir = new File(getWorkPath());
		if (!outputDir.exists())
		{
			outputDir.mkdirs();
		}
	}

	public File move(File from, String name)
	{
		File out = new File(getWorkPath() + name);
		out.getParentFile().mkdirs();
		try
		{
			FileInputStream fis = new FileInputStream(from);//创建输入流对象
			FileOutputStream fos = new FileOutputStream(out); //创建输出流对象               
			byte datas[] = new byte[1024 * 8];//创建搬运工具
			int len = 0;//创建长度   
			while ((len = fis.read(datas)) != -1)//循环读取数据
			{
				fos.write(datas, 0, len);
			} 
			fis.close();//释放资源
			fos.close();//释放资源
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return out;
	}
}
