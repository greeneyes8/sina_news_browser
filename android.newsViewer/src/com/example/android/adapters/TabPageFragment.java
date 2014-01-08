package com.example.android.adapters;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.example.android.newsviewer.R;
import com.example.android.newsviewer.ViewActivity;

@SuppressLint({ "ValidFragment", "SetJavaScriptEnabled" })
public class TabPageFragment extends Fragment {
	private WebView wv;
	private View loadingView;
	// private View loading;
	private View loading_error;
	private boolean error = false;
	private String host_url;
	private int mPositon;
	private static ArrayList<View> mViews = new ArrayList<View>();
	private static boolean creat = true;

	public TabPageFragment(int position) {
		mPositon = position;
		if (creat) {
			creat = false;
			for (int i = 0; i < 6; i++) {
				mViews.add(null);
			}
		}
		// System.out.println("position******"+position);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// System.out.println("onCreateView******");
		Bundle bundle = getArguments();
		String url = bundle.getString("url");
		host_url = url;
		if (mViews.get(mPositon) == null) {
			loadingView = View.inflate(getActivity(), R.layout.tab_page_view,
					null);
			mViews.set(mPositon, loadingView);
			wv = (WebView) loadingView.findViewById(R.id.nwv);
		} else {
			loadingView = mViews.get(mPositon);
			wv = (WebView) loadingView.findViewById(R.id.nwv);
			((FrameLayout) loadingView.getParent()).removeAllViews();
		}
		loading_error = loadingView.findViewById(R.id.loading_error);
		wv.getSettings().setJavaScriptEnabled(true);
		wv.getSettings().setBuiltInZoomControls(true);
		wv.getSettings().setDisplayZoomControls(false);
		// 开启缓存功能，如果之前已经缓存了，就从缓存加载数据，否则就从网络上加载数据
		wv.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		//启动子线程
		mLoadUrl(host_url);
		wv.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (!host_url.equals(url)) {
					Intent intent = new Intent(getActivity(),
							ViewActivity.class);
					intent.putExtra("url", url);
					startActivity(intent);
					return true;
				} else {
					return true;
				}
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				// System.out.println("onPageStarted******");
				loading_error.setVisibility(View.GONE);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				// System.out.println("onPageFinished******");
				if (error) {
					loading_error.setVisibility(View.VISIBLE);
					error = false;
				}
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// System.out.println("onReceivedError******");
				error = true;
				super.onReceivedError(view, errorCode, description, failingUrl);
			}
		});
		return loadingView;
	}

	public void mLoadUrl(final String url) {
		new Thread() {
			@Override
			public void run() {
				wv.loadUrl(url);
				super.run();
			}
		}.start();
	}

	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// outState.putString("url", host_url);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// System.out.println("onActivityCreated******");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		// System.out.println("onAttach******");
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// System.out.println("onCreate******");
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		// System.out.println("onDestroy******");
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		wv = null;
		// loading_error=null;
		loadingView = null;
		// System.out.println("onDestroyView******");
		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		// System.out.println("onDetach******");
		super.onDetach();
	}

	@Override
	public void onPause() {
		// System.out.println("onPause******");
		super.onPause();
	}

	@Override
	public void onResume() {
		// System.out.println("onResume******");
		super.onResume();
	}

	@Override
	public void onStart() {
		// System.out.println("onStart******");
		super.onStart();
	}

	@Override
	public void onStop() {
		// System.out.println("onStop******");
		super.onStop();
	}

}