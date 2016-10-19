1.网络框架：我使用的是okHttp；可以根据自己的需求选择网络框架。
2.里面的demo仅供参考代码的写法，直接拷贝使用不了，需要根据自己的业务逻辑进行修改；
3.okHttp导入方式：app-》build.gradle-》dependencies-》compile 'com.squareup.okhttp:okhttp:2.4.0'；
4.注意：每进行一次方法调用都需要new 一个ClientXxxAPI；
5.Activity中使用方式：
	private void loadServerCategoryList() {
        ClientMyAPI xxxAPI = new ClientXxxAPI(this);
        xxxAPI.方法1(参数);
		ClientMyAPI xxxAPI = new ClientXxxAPI(this);
        xxxAPI.方法2(参数);
    }

    @Override
    public void onTaskFinished(ClientResult clientResult) {
        switch (clientResult.actionId){
            case ClientXxxAPI.ACTION_XXX:
                //todo deal with clientResult.data;
                break;
        }
    }