package com.onetoo.www.onetoo.client.order;

import com.onetoo.www.onetoo.MyApplication;
import com.onetoo.www.onetoo.client.BaseClientAPI;
import com.onetoo.www.onetoo.client.ClientCallBack;
import com.onetoo.www.onetoo.config.NetWorkCons;
import com.onetoo.www.onetoo.utils.OkHttpUtil;

import java.util.HashMap;

/**
 * Created by longShun on 2016/10/19.
 * 订单相关网络操作 引入BaseClientAPI
 * 注意：每进行一次方法调用都需要new ClientOrderAPI
 */
public class ClientOrderAPI extends BaseClientAPI {

    /*获取用户购物车*/
    public static final int ACTION_GET_USER_SHOP_CAR = 1;
    /*添加商品到购物车*/
    public static final int ACTION_ADD_PRODUCT_TO_SHOP_CAR = 2;
    /*调整购物车商品数量*/
    public static final int ACTION_ADJUST_SHOP_CAR_NUM = 3;
    /*从购物车中删除商品*/
    public static final int ACTION_DELETE_PRODUCT_FROM_SHOP_CAR = 4;
    /*清除购物车*/
    public static final int ACTION_CLEAR_USER_SHOP_CAR = 5;

    public ClientOrderAPI(ClientCallBack callBack) {
        super(callBack);
    }

    public void getUserShopCar(MyApplication app, int page) {
        setActionId(ACTION_GET_USER_SHOP_CAR);
        //token page默认为1，每页5条记录
        String token = app.getMtoken();
        HashMap<String, String> params = new HashMap<>();
        params.put("token",token);
        params.put("page",page+"");
        String url = OkHttpUtil.attachHttpGetParams(NetWorkCons.GET_USER_SHOP_CAR_URL, params);
        OkHttpUtil.asynGetFromServer(url,this);
    }

    public void addProductToShopCar(MyApplication app,String productId,String quantity){
        setActionId(ACTION_ADD_PRODUCT_TO_SHOP_CAR);
        //token product_id quantity
        String token = app.getMtoken();
        HashMap<String, String> params = new HashMap<>();
        params.put("token",token);
        params.put("product_id",productId);
        params.put("quantity",quantity);
        String url = OkHttpUtil.attachHttpGetParams(NetWorkCons.ADD_PRODUCT_TO_SHOP_CAR_URL, params);
        OkHttpUtil.asynGetFromServer(url,this);
    }

    public void adjustShopCarNum(MyApplication app,String cartId,String type){
        setActionId(ACTION_ADJUST_SHOP_CAR_NUM);
        //token pk_product_cart_id type 1：增加1, 2：减少1
        String token = app.getMtoken();
        HashMap<String, String> params = new HashMap<>();
        params.put("token",token);
        params.put("pk_product_cart_id",cartId);
        params.put("type",type);
        String url = OkHttpUtil.attachHttpGetParams(NetWorkCons.ADJUST_SHOP_CAR_NUM_URL, params);
        OkHttpUtil.asynGetFromServer(url,this);
    }

    public void deleteProductFromShopCar(MyApplication app,String cartId){
        setActionId(ACTION_DELETE_PRODUCT_FROM_SHOP_CAR);
        //token pk_product_cart_id
        String token = app.getMtoken();
        HashMap<String, String> params = new HashMap<>();
        params.put("token",token);
        params.put("pk_product_cart_id",cartId);
        String url = OkHttpUtil.attachHttpGetParams(NetWorkCons.DELETE_PRODUCT_FROM_SHOP_CAR_URL, params);
        OkHttpUtil.asynGetFromServer(url,this);
    }

    public void clearUserShopCar(MyApplication app){
        setActionId(ACTION_CLEAR_USER_SHOP_CAR);
        //token
        String token = app.getMtoken();
        String url = OkHttpUtil.attachHttpGetParam(NetWorkCons.CLEAR_USER_SHOP_CAR_URL, "token", token);
        OkHttpUtil.asynGetFromServer(url,this);
    }
}
