package org.rubychina.android.activity;

import org.rubychina.android.GlobalResource;
import org.rubychina.android.R;
import org.rubychina.android.RCApplication;
import org.rubychina.android.api.request.NodesRequest;
import org.rubychina.android.api.response.NodesResponse;
import org.rubychina.android.database.RCDBResolver;

import yek.api.ApiCallback;
import yek.api.ApiException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;

public class RCActivity extends Activity {

	private NodesRequest request;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rc_layout);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(GlobalResource.INSTANCE.getNodes().isEmpty()) {
			startNodesRequest();
		} else {
			new CountDownTimer(1000, 500) {
				
				@Override
				public void onTick(long millisUntilFinished) {
					
				}
				
				@Override
				public void onFinish() {
					go2Topics();
				}
				
			}.start();
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return true;
	}
	
	private void go2Topics() {
		Intent i = new Intent();
		i.setClass(getApplicationContext(), TopicsActivity.class);
		startActivity(i);
		finish();
	}
	
	private void startNodesRequest() {
		if(request == null) {
			request = new NodesRequest();
		}
		((RCApplication) getApplication()).getAPIClient().request(request, new NodesCallback());
	}
	
	private class NodesCallback implements ApiCallback<NodesResponse> {

		@Override
		public void onException(ApiException e) {
			go2Topics();
		}

		@Override
		public void onFail(NodesResponse r) {
			go2Topics();
		}

		@Override
		public void onSuccess(NodesResponse r) {
			GlobalResource.INSTANCE.setNodes(r.getNodes());
			RCDBResolver.INSTANCE.clearNodes(getApplicationContext());
			RCDBResolver.INSTANCE.insertNodes(getApplicationContext(), r.getNodes());
			go2Topics();
		}
		
	}

}
