/**
 * Copyright 2017 Sun Jian
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.mylo.cleandemo.data.api;

import android.content.Context;
import android.text.TextUtils;

import com.example.mylo.cleandemo.data.service.ZhihuService;
import com.example.mylo.cleandemo.data.util.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author: sunjian
 * created on: 2017/9/6 下午5:25
 * description:https://github.com/crazysunj/CrazyDaily
 */
public class HttpHelper {

    private ZhihuService mZhihuService;
    private OkHttpClient mOkHttpClient;

    public HttpHelper(Context context) {
        if (mOkHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            //设置缓存 20M
            Cache cache = new Cache(new File(context.getExternalCacheDir(), "Api"), 20 * 1024 * 1024);
            builder.cache(cache);
            builder.addInterceptor(new CrazyDailyCacheInterceptor());
            builder.addNetworkInterceptor(new CrazyDailyCacheNetworkInterceptor());
            //设置超时
            builder.connectTimeout(10, TimeUnit.SECONDS);
            builder.readTimeout(20, TimeUnit.SECONDS);
            builder.writeTimeout(20, TimeUnit.SECONDS);
            //错误重连
            builder.retryOnConnectionFailure(true);
            mOkHttpClient = builder.build();
        }
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public ZhihuService getZhihuService() {
        if (mZhihuService == null) {
            synchronized (this) {
                if (mZhihuService == null) {
                    mZhihuService = new Retrofit.Builder()
                            .baseUrl(ZhihuService.HOST)
                            .client(mOkHttpClient)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build().create(ZhihuService.class);
                }
            }
        }
        return mZhihuService;
    }


    /**
     * 有网才会执行哦
     */
    private static class CrazyDailyCacheNetworkInterceptor implements Interceptor {

        private static final String CACHE_CONTROL = "Cache-Control";

        @Override
        public Response intercept(Chain chain) throws IOException {
            final Request request = chain.request();
            final Response response = chain.proceed(request);
            final String requestHeader = request.header(CACHE_CONTROL);
            //判断条件最好加上TextUtils.isEmpty(response.header(CACHE_CONTROL))来判断服务器是否返回缓存策略，如果返回，就按服务器的来，我这里全部客户端控制了
            if (!TextUtils.isEmpty(requestHeader)) {
                return response.newBuilder().header(CACHE_CONTROL, requestHeader).removeHeader("Pragma").build();
            }
            return response;
        }
    }

    private static class CrazyDailyCacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            CacheControl cacheControl = request.cacheControl();
            //header可控制不走这个逻辑
            boolean noCache = cacheControl.noCache() || cacheControl.noStore() || cacheControl.maxAgeSeconds() == 0;
            if (!noCache && !NetworkUtils.isNetworkAvailable()) {
                Request.Builder builder = request.newBuilder();
                CacheControl newCacheControl = new CacheControl.Builder().maxStale(1, TimeUnit.DAYS).build();
                request = builder.cacheControl(newCacheControl).build();
                return chain.proceed(request);
            }
            return chain.proceed(request);
        }
    }
}
