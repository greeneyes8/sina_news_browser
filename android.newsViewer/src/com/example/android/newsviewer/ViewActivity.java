package com.example.android.newsviewer;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.handmark.pulltorefresh.library.PullToRefreshWebView;

public class ViewActivity extends Activity {
	private WebView mView;
	// private View line;
	private View errorView;
	private PullToRefreshWebView mPullRefreshWebView;
	private ActionBar aBar;
	private boolean error = false;
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view);
//		System.out.println("主线程*****"+Thread.currentThread());
		aBar = getActionBar();
		aBar.setDisplayHomeAsUpEnabled(true);
		aBar.setDisplayShowHomeEnabled(true);
		aBar.setHomeButtonEnabled(true);
		// line=findViewById(R.id.line);
		errorView = findViewById(R.id.error);
		mPullRefreshWebView = (PullToRefreshWebView) findViewById(R.id.mview);
		mView = mPullRefreshWebView.getRefreshableView();
		mView.getSettings().setJavaScriptEnabled(true);
		mView.getSettings().setBuiltInZoomControls(true);
		mView.getSettings().setDisplayZoomControls(false);
		mView.getSettings().setTextZoom(100);
		mView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
				// return super.shouldOverrideUrlLoading(view, url);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				errorView.setVisibility(View.GONE);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				if (error) {
					error = false;
					errorView.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
				error = true;
				// System.out.println("onReceivedError**********");
			}
		});

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		String url = bundle.getString("url");
		mLoadUrl(url);
	}
	public void mLoadUrl(final String url) {
		new Thread() {
			@Override
			public void run() {
//				System.out.println("子线程*****"+Thread.currentThread());
				mView.loadUrl(url);
				super.run();
			}
		}.start();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && mView.canGoBack()) {
			mView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.view, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		finish();
		// System.out.println("ViewActivity Destroyed");
		super.onDestroy();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: {
			Intent intent = new Intent(ViewActivity.this, MainActivity.class);
			/**
			 * MainActivity的启动模式已在manifest中已设置为singleTask模式
			 */
			this.startActivity(intent);
			overridePendingTransition(android.R.anim.slide_in_left,
					android.R.anim.slide_out_right);
			return true;
		}
		case R.id.refresh: {
			mView.reload();
			return true;
		}
		case R.id.clearCache: {
			mView.clearCache(true);
			return true;
		}
		default:
			return true;
		}
	}

}
