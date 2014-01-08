package com.example.android.adapters;
import android.annotation.SuppressLint;
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
public class UpdateDataFragment extends Fragment {
	private static WebView wv;
	private boolean error = false;
	private static String host_url;
	private static UpdateDataFragment mUpdateDataFragment;
	private static View loading;
	// private View loading_View;
	private static View errorView;

	public static UpdateDataFragment getFragmentInstance(String url) {
		host_url = url;
		if (mUpdateDataFragment == null) {
			mUpdateDataFragment = new UpdateDataFragment();
		}
		return mUpdateDataFragment;
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (loading == null) {
			loading = View.inflate(getActivity(), R.layout.myloading, null);
			// loading_View=loading.findViewById(R.id.view);
			errorView = loading.findViewById(R.id.error);
			wv = (WebView) loading.findViewById(R.id.wv);
			wv.clearMatches();
			wv.getSettings().setJavaScriptEnabled(true);
			wv.getSettings().setBuiltInZoomControls(true);
			wv.getSettings().setDisplayZoomControls(false);
			wv.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
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
				public void onPageStarted(WebView view, String url,
						Bitmap favicon) {
					super.onPageStarted(view, url, favicon);
					errorView.setVisibility(View.GONE);
					// System.out.println("onPageStarted******");
				}

				@Override
				public void onPageFinished(WebView view, String url) {
					super.onPageFinished(view, url);
					// System.out.println("onPageFinished******");
					if (error) {
						errorView.setVisibility(View.VISIBLE);
					}
				}

				@Override
				public void onReceivedError(WebView view, int errorCode,
						String description, String failingUrl) {
					// System.out.println("onReceivedError******");
					super.onReceivedError(view, errorCode, description,
							failingUrl);
					error = true;
				}
			});
			mLoadUrl(host_url);
			return loading;
		} else {
			wv.clearView();
//			wv.loadUrl(host_url);
			mLoadUrl(host_url);
			((FrameLayout) loading.getParent()).removeAllViews();
			// System.out.println("loadingView++++"+loading);
			return loading;
		}
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
	}

}
