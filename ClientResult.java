package com.onetoo.www.onetoo.client;

/**
 * 异步任务返回结果通用数据对象<br//>
 * 1.clientId，代表当前的结果由哪一个一部任务来返回的<br/>
 * 2.data，代表Object对象，就是异步任务返回的实际对象
 * Created by longShun on 2016/10/14.
 */

public class ClientResult {
	/*一般用不上，用来标识ClientXxxAPI;在主线程中根据这个clientId来区分是那个ClientXxxPAI返回来的数据*/
	public int clientId;
    /**
     * ClientXxxAPI里的方法标识，例如ClientRegLogin中，标识是登录(login)还是注册(reg)
	 *在主线程中根据这个actionId来区分是那个方法返回来的数据
     */
    public int actionId;

    /*数据 可以根据自己的需求改变data的类型，一般从网络上获取到的就是json字符串，所以我封装成String类型*/
    public String data;
}
