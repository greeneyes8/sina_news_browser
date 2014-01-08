package com.example.android.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * 给Viewpager设置的adapter类
 * 
 * @author Happy_Bear
 * 
 */
public class TabPageIndicatorAdapter extends FragmentStatePagerAdapter {
	private String[] tabTitles = { "新闻", "娱乐", "体育", "财经", "汽车", "军事" };
	private String[] urlTitls = {
			"http://sina.cn/nc.php?wm=4007_0009",
			"http://ent.sina.cn/?ttp=navent&pos=108&vt=4&wm=4007_0009&clicktime"
					+ "=1387447013517&userid=user13874470135179139659781940281",
			"http://sports.sina.cn/?http=navsport&pos=108&vt=4&wm=4007_0009&clicktime"
					+ "=1387447078368&userid=user138744707836811464035487733781",
			"http://finance.sina.cn/?ttp=navfinance&pos=108&vt=4&wm=4007_0009&clicktime"
					+ "=1387447132681&userid=user13874471326815956536452285945",
			"http://auto.sina.cn/?ttp=navauto&pos=108&vt=4&wm=4007_0009&clicktime"
					+ "=1387447177814&userid=user138744717781433983929455280304",
			"http://mil.sina.cn/?ttp=navmil&pos=108&vt=4&wm=4007_0009&clicktime"
					+ "=1387447218939&userid=user138744721893907410758757032454", };
	private static TabPageIndicatorAdapter mIndicatorAdapter;

	public TabPageIndicatorAdapter(FragmentManager fm) {
		super(fm);
	}

	public static TabPageIndicatorAdapter getTabAdapterInstance(
			FragmentManager fm) {
		if (mIndicatorAdapter == null) {
			mIndicatorAdapter = new TabPageIndicatorAdapter(fm);
		}
		return mIndicatorAdapter;
	}

	@Override
	public Fragment getItem(int arg0) {
		Fragment fragment = new TabPageFragment(arg0);
		Bundle bundle = new Bundle();
		bundle.putString("url", urlTitls[arg0]);
		// System.out.println("url++++++++++++"+urlTitls[arg0]);
		fragment.setArguments(bundle);
//		System.out.println("fragment" + arg0 + "*********" + fragment);
		return fragment;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return tabTitles[position];
	}

	@Override
	public int getCount() {
		return tabTitles.length;
	}

	public static void clearPageAdapter() {
		mIndicatorAdapter = null;
	}
}