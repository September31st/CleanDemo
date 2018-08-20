package com.example.mylo.cleandemo.data.repository;

import com.example.mylo.cleandemo.data.api.HttpHelper;
import com.example.mylo.cleandemo.data.service.ZhihuService;
import com.example.mylo.cleandemo.data.util.RxTransformerUtil;
import com.example.mylo.cleandemo.domain.entity.ZhihuNewsEntity;
import com.example.mylo.cleandemo.domain.repository.ZhihuRepository;

import io.reactivex.Flowable;
import retrofit2.Retrofit;

public class ZhihuDataRepository implements ZhihuRepository {


    private ZhihuService mZhihuService;


    @Override
    public Flowable<ZhihuNewsEntity> getMainList() {
        return mZhihuService.getZhihuNewsList()
                .compose(RxTransformerUtil.normalTransformer());
    }

    public ZhihuDataRepository(HttpHelper httpHelper) {
        mZhihuService = httpHelper.getZhihuService();
    }

}
