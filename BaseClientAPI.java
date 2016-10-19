package com.onetoo.www.onetoo.client;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by longShun on 2016/10/19.
 * 提取ClientXxxAPI公共的部分
 */
public class BaseClientAPI implements Callback {

    private ClientCallBack callBack;

	/*默认的actionId，可以自己设定*/
    private static final int DEFAULT_ACTION_ID = -100000000;
    private int actionId = DEFAULT_ACTION_ID;

    protected BaseClientAPI(ClientCallBack callBack) {
        this.callBack = callBack;
    }

    protected void setActionId(int actionId) {
        this.actionId = actionId;
    }

    /*处理消息的handler，在主线程处理数据*/
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ClientResult result = (ClientResult) msg.obj;
            callBack.onTaskFinished(result);
        }
    };

    @Override
    public void onResponse(Response response) throws IOException {
        ClientResult result = new ClientResult();
        result.actionId = this.actionId;
        if (result.actionId == DEFAULT_ACTION_ID) {
            throw new RuntimeException("don't set actionId -100000000 or you should setActionId in BaseClientApi child class!");
        } else {
            if (response.isSuccessful()) {
                result.data = response.body().string();
            } else {
                result.data = null;
                Log.d("clientHomeApi", "Unexpected code " + response.code());
            }
			/*把数据交给主线程处理*/
            Message message = mHandler.obtainMessage(result.actionId, result);
            mHandler.sendMessage(message);
        }
    }

    @Override
    public void onFailure(Request request, IOException e) {
        Log.d("clienthomeapi", "onFailure: load net fail！");
    }
}
