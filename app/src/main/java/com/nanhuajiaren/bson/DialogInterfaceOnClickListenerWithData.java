package com.nanhuajiaren.bson;
import android.content.DialogInterface;

public abstract class DialogInterfaceOnClickListenerWithData<T> implements DialogInterface.OnClickListener
{
	private T data;

	public DialogInterfaceOnClickListenerWithData(T data)
	{
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
