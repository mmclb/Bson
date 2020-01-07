package com.nanhuajiaren.bson;
import android.view.View.OnClickListener;

public abstract class OnClickListenerWithData<T> implements OnClickListener
{
	T data;
	
	public OnClickListenerWithData(T data){
		this.data = data;
	}
	
	public void setData(T data)
	{
		this.data = data;
	}

	public T getData()
	{
		return data;
	}
}
