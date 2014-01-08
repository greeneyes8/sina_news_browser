package com.example.android.newsviewer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.android.adapters.MyListSimpleAdapter;
import com.example.android.adapters.TabPageIndicatorAdapter;
import com.example.android.adapters.UpdateDataAdapter;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

public class MainActivity extends FragmentActivity {
	private static final String ALERT = "再按一次退出手机新浪网";
	private static final int NEWS = 0;
	private String[] menu = { "新闻", "理财", "博客", "视频", "图片", "健康" };
	private int[] icons = { R.drawable.news, R.drawable.licai, R.drawable.read,
			R.drawable.ties, R.drawable.pics, R.drawable.vote };
	private TabPageIndicator indicator;
	private ViewPager mPager;
	private SlidingMenu sm;
	private ActionBar mBar;
	private ImageView openSlideMenu;
	private ListView lv;
	private SimpleAdapter adapter_list;
	private List<Map<String, Object>> list;
	private Set<Integer> set;
	private boolean quit = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// System.out.println("MainActivity Create");
		requestWindowFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_main);
		setActionBar();
		openSlideMenu=(ImageView) findViewById(R.id.openSlideMenu);
		set = new HashSet<Integer>();
		set.add(NEWS);
		indicator = (TabPageIndicator) findViewById(R.id.indicator);
		// 从布局文件中获取Viewpager的实例
		mPager = (ViewPager) findViewById(R.id.pager);
		initMainPage();
		sm = new SlidingMenu(this);
		sm.setMode(SlidingMenu.LEFT);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// 设置标题栏是否随着SlideMenu一起滑动
		// setSlidingActionBarEnabled(true);
		sm.setMenu(R.layout.slidemenu);
		// 将slidemenu绑定到当前的Activity
		sm.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		lv = (ListView) sm.findViewById(R.id.lv);
		list = new ArrayList<Map<String, Object>>();
		int num = menu.length;
		for (int i = 0; i < num; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("item", menu[i]);
			map.put("image", icons[i]);
			list.add(map);
		}
		adapter_list = new MyListSimpleAdapter(this, list, R.layout.list_items,
				new String[] { "image", "item" }, new int[] { R.id.img,
						R.id.tv_item });
		lv.setAdapter(adapter_list);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				quit = false;
				if (position > 0) {
					if (set.add(position)) {
						set.clear();
						set.add(position);
						updateDatas(position - 1);
						sm.toggle();
					}
				} else {
					if (set.add(position)) {
						set.clear();
						set.add(position);
						initMainPage();
						sm.toggle();
					}
				}
				// 设置listView中的Item选中高亮状态
				if (((ListView) parent).getTag() != null) {
					// 恢复前一次选中的item的背景，一般情况下下一个item选中之后前一个item的背景就恢复原来的状态
					((View) ((ListView) parent).getTag()).setBackgroundColor(0);
				}
				// 设置当前选中的item的高亮状态
				((ListView) parent).setTag(view);
				view.setBackgroundColor(Color.rgb(61, 55, 57));
			}
		});
		/**
		 * 只能用indicator设置监听事件，如果用ViewPager设置监听事件的话,标签不会移动
		 */
		indicator.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				quit = false;
				switch (arg0) {
				case 0: {
					sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
				}
					break;
				default:
					sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		//监听ActionBar中自定义视图的点击事件
		openSlideMenu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sm.toggle();
			}
		});
	}

	private void setActionBar() {
		mBar = getActionBar();
		mBar.setDisplayShowHomeEnabled(false);
		mBar.setCustomView(R.layout.main_actionbar);
		mBar.setDisplayShowCustomEnabled(true);
		mBar.setDisplayShowTitleEnabled(false);
	}

	private void initMainPage() {
		/**
		 * 给Viewpager设置adapter，Viewpager要显示内容必须为其设置相应的adapter
		 * 类似于ListView、GridView 之类的，这里如果FragmentPagerAdapter的话会
		 * 报空指向异常，即下面OnSaveInstance(Bundle outState)方法中的outState为空
		 * 若是要用FragmentPagerAdapter的解决办法看OnSaveInstance(Bundle outState) 方法中的说明。
		 */
		FragmentStatePagerAdapter adapter = TabPageIndicatorAdapter
				.getTabAdapterInstance(getSupportFragmentManager());
		// System.out.println("adapter+++++++"+adapter);
		mPager.setOffscreenPageLimit(1);
		mPager.setAdapter(adapter);
		indicator.setViewPager(mPager);
		// 通知更新标签页，否则在滑动过程中会出现不正常情况
		indicator.notifyDataSetChanged();
		indicator.setVisibility(View.VISIBLE);
	}

	private void updateDatas(int position) {
		// System.out.println("++++++"+position);
		FragmentStatePagerAdapter adapter = UpdateDataAdapter
				.getUpdateAdapterInstance(getSupportFragmentManager(), position);
		mPager.setAdapter(adapter);
		indicator.setVisibility(View.GONE);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (sm.isMenuShowing() && keyCode == KeyEvent.KEYCODE_BACK) {
			sm.toggle();
			return true;
		} else if (!quit && keyCode == KeyEvent.KEYCODE_BACK) {
			quit = true;
			Toast.makeText(getApplicationContext(), ALERT, Toast.LENGTH_SHORT)
					.show();
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_HOME) {
			moveTaskToBack(false);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		return super.onMenuItemSelected(featureId, item);
	}

	/**
	 * 
	 * 很奇怪这里的outState老是报空指向异常，即使不重写该方法任然会报错， 查了些资料只好用次方法将其注释掉。
	 */
	/*
	 * @Override protected void onSaveInstanceState(Bundle outState) { // TODO
	 * Auto-generated method stub //System.out.println("*******"+outState);
	 * //super.onSaveInstanceState(outState); }
	 */
	@Override
	protected void onStop() {

		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// finish();
		TabPageIndicatorAdapter.clearPageAdapter();
		UpdateDataAdapter.clearUpdateAdapter();
		// System.out.println("MainActivity Destroyed");
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/*@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: {
			// 监听标题栏上的回退键
			sm.toggle();
			return true;
		}
		}
		return super.onOptionsItemSelected(item);
	}*/

}
