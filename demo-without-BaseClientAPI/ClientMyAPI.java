package com.onetoo.www.onetoo.client.my;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.onetoo.www.onetoo.MyApplication;
import com.onetoo.www.onetoo.bean.my.Product;
import com.onetoo.www.onetoo.client.ClientCallBack;
import com.onetoo.www.onetoo.client.ClientResult;
import com.onetoo.www.onetoo.config.DBConst;
import com.onetoo.www.onetoo.config.NetWorkCons;
import com.onetoo.www.onetoo.utils.OkHttpUtil;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by longShun on 2016/10/14.
 * “我的”模块相关的网络操作 没有引入BaseClientAPI
 * 注意：每进行一次方法调用都需要new 一个ClientMyAPI；
 */
public class ClientMyAPI implements Callback {

    private ClientCallBack callBack;

    private int actionId;
    /*方法标识*/
    /*获取店铺信息*/
    public static final int ACTION_GET_STORE_INFO = 1;
    /*获取店铺商品*/
    public static final int ACTION_GET_STORE_GOODS = 2;
    public static final int ACTION_GET_PRODUCT_CATEGORY = 3;
    public static final int ACTION_ADD_PRODUCT_CATEGORY = 4;
    public static final int ACTION_PUBLISH_PRODUCT = 5;
    public static final int ACTION_UPLOAD_MULTI_PIC = 6;
    public static final int ACTION_UPLOAD_SINGLE_PIC = 7;

    public ClientMyAPI(ClientCallBack callBack) {
        this.callBack = callBack;
    }

    /*获取店铺商品分类*/
    public void getProductCategory(MyApplication app) {
        this.actionId = ACTION_GET_PRODUCT_CATEGORY;
        HashMap<String, String> params = new HashMap<>();
        String token = app.getMtoken();
        String storeId = app.getStoreId();
        if (token != null && storeId != null) {
            params.put("token", token);
            params.put("store_id", storeId);
            String url = OkHttpUtil.attachHttpGetParams(NetWorkCons.GET_PRODUCT_CATEGORY_URL, params);
            OkHttpUtil.asynGetFromServer(url, this);
        }
    }

    /*添加店铺商品分类*/
    public void addProductCategory(MyApplication app, String categoryName) {
        this.actionId = ACTION_ADD_PRODUCT_CATEGORY;
        //category_name token store_id
        String token = app.getMtoken();
        String storeId = app.getStoreId();
        if (token != null && storeId != null) {
            if (!TextUtils.isEmpty(categoryName)) {
                HashMap<String, String> params = new HashMap<>();
                params.put("token", token);
                params.put("store_id", storeId);
                params.put("category_name", categoryName.trim());
                String url = OkHttpUtil.attachHttpGetParams(NetWorkCons.ADD_PRODUCT_CATEGORY_URL, params);
                OkHttpUtil.asynGetFromServer(url, this);
            }
        }
    }

    /*发布商品*/
    public void publishProduct(MyApplication app, Product product) {
        this.actionId = ACTION_PUBLISH_PRODUCT;
        //【token store_id category_id name price description storage image图片地址用英文逗号  ‘,’ 隔开 】都是必填参数
        String token = app.getMtoken();
        String storeId = app.getStoreId();
        if (token != null && storeId != null && product != null) {
            HashMap<String, String> params = new HashMap<>();
            params.put("token", token);
            params.put("store_id", storeId);
            params.put("category_id", product.getCategoryId());
            params.put("name", product.getName());
            params.put("price", product.getPrice());
            params.put("description", product.getDescription());
            params.put("storage", product.getStorage());
            List<String> imageList = product.getImageList();
            if (imageList != null) {//有图先传图，上传成功再根据返回的图片地址拼接上传其他内容
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < imageList.size(); i++) {
                    builder.append(imageList.get(i)).append(",");
                }
                builder.deleteCharAt(builder.length() - 1);
                params.put("image", builder.toString());
            }
            String url = OkHttpUtil.attachHttpGetParams(NetWorkCons.PUBLISH_PRODUCT_URL, params);
            OkHttpUtil.asynGetFromServer(url, this);
        }
    }

    /*上传一张图片*/
    public void uploadPic(MyApplication app, String imageType, String imgPath) {
        this.actionId = ACTION_UPLOAD_SINGLE_PIC;
        //token img_type 1 用户头像2营业执照 3身份证正反面 4 店铺头图 5 店铺菜单  UploadFileForm[imageFile]
        String token = app.getMtoken();
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("img_type", imageType);
        String url = OkHttpUtil.attachHttpGetParams(NetWorkCons.UPLOAD_IMAGE_URL, params);
        OkHttpUtil.uploadImage(url,imgPath,this);
    }

    /*上传多张图片接口*/
    public void uploadPic(MyApplication app, String imageType, List<String> imgPaths) {
        this.actionId = ACTION_UPLOAD_MULTI_PIC;
        //token img_type 1 用户头像2营业执照 3身份证正反面 4 店铺头图 5 店铺菜单  UploadFileForm[imageFile]
        String token = app.getMtoken();
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("img_type", imageType);
        String url = OkHttpUtil.attachHttpGetParams(NetWorkCons.UPLOAD_IMAGE_URL, params);
        OkHttpUtil.uploadImages(url, imgPaths, this);
    }

    /*获取店铺商品*/
    public void getStoreGoods(MyApplication app) {
        //token store_id
        this.actionId = ACTION_GET_STORE_GOODS;
        String token = app.getMtoken();
        String storeId = app.getStoreId();
        if (token != null && storeId != null) {
            HashMap<String, String> params = new HashMap<>();
            params.put(DBConst._TOKEN, token);
            params.put("store_id", storeId);
            String url = OkHttpUtil.attachHttpGetParams(NetWorkCons.GET_STORE_GOODS_URL, params);
            OkHttpUtil.asynGetFromServer(url, this);
        }
    }

    /*获取店铺信息*/
    public void getStoreInfo(String token) {
        if (token != null) {
            this.actionId = ACTION_GET_STORE_INFO;
            String url = OkHttpUtil.attachHttpGetParam(NetWorkCons.GET_STORE_INFO_URL, "token", token);
            OkHttpUtil.asynGetFromServer(url, this);
        }
    }

    /*处理消息的handler，把数据交给主线程处理*/
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
        if (response.isSuccessful()) {
            result.data = response.body().string();
        } else {
            result.data = null;
            Log.d("clientHomeApi", "Unexpected code " + response.code());
        }
        Message message = mHandler.obtainMessage(result.actionId, result);
        mHandler.sendMessage(message);
    }

    @Override
    public void onFailure(Request request, IOException e) {
        Log.d("clienthomeapi", "onFailure: load net fail！");
    }
}
