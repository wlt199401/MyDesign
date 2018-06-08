package com.example.wlt.mydesign.base.net;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 25122 on 2018/6/7.
 */

public interface GoodsService {

    @POST("Goods/GetGoods")
    Observable<ResponseBody> GetClass(@Query("categoryId") String categoryId);
}
