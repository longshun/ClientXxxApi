package com.onetoo.www.onetoo.client;

/**
 * Created by longShun on 2016/10/14.
 * 异步访问回调,得到结果交给接口处理
 * 一般用在主线程实现该接口，在主线程处理子线程中返回的数据
 */
public interface ClientCallBack {
    void onTaskFinished(ClientResult clientResult);
}
