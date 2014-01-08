package com.example.android.adapters;

import java.util.List;
import java.util.Map;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MyListSimpleAdapter extends SimpleAdapter {
	public MyListSimpleAdapter(Context context, List<Map<String, Object>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		/**
		 * 初始化第一项为默认选中高亮状态
		 */
		if (position == 0) {
			View view = super.getView(position, convertView, parent);
			view.setBackgroundColor(Color.rgb(61, 55, 57));
			((ListView) parent).setTag(view);
			return view;
		}
		return super.getView(position, convertView, parent);
	}
}
