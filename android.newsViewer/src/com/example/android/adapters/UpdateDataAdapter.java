package com.example.android.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class UpdateDataAdapter extends FragmentStatePagerAdapter {
	private String[] url = {
			"http://bst.sina.cn/?sa=t731d5v3879&vt=4&wm=4007_0009",
			"http://blog.sina.cn/?vt=4&wm=4007_0009&mtch=news ",
			"http://video.sina.cn/?vt=4&wm=4007_0009",
			"http://dp.sina.cn/dpool/hdpic/index.php?ttp=navmeitu&pos=108&vt=4&wm=4007_0009",
			"http://health.sina.cn/?vt=4&wm=4007_0009" };
	private static int mposition;
	private static UpdateDataAdapter mAdapter = null;

	public UpdateDataAdapter(FragmentManager fm, int position) {
		super(fm);
	}

	public static UpdateDataAdapter getUpdateAdapterInstance(
			FragmentManager fm, int position) {
		if (mAdapter == null) {
			mAdapter = new UpdateDataAdapter(fm, position);
		}
		mposition = position;
		return mAdapter;
	}

	@Override
	public Fragment getItem(int arg0) {
		Fragment fragment = UpdateDataFragment
				.getFragmentInstance(url[mposition]);
		return fragment;
	}

	@Override
	public int getCount() {
		return 1;
	}

	public static void clearUpdateAdapter() {
		mAdapter = null;
	}
}
