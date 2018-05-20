package com.ckr.mediabrowser.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by PC大佬 on 2018/5/20.
 */

public abstract class BaseAdapter<T, E extends BaseViewHolder> extends RecyclerView.Adapter<E> {

	protected Context mContext;
	protected ArrayList<T> data;

	public BaseAdapter(Context context){
		mContext = context;
		data = new ArrayList<>();
	}

	@Override
	public int getItemCount() {
		return data.size();
	}

	@NonNull
	@Override
	public E onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return getViewHolder(LayoutInflater.from(mContext).inflate(getLayoutId(viewType), parent, false), viewType);
	}

	@Override
	public void onBindViewHolder(@NonNull E holder, int position) {
		convert(holder, position, data.get(position));
	}

	protected abstract int getLayoutId(int viewType);

	protected abstract E getViewHolder(View itemView, int viewType);

	protected abstract void convert(E holder, int position, T t);

}
