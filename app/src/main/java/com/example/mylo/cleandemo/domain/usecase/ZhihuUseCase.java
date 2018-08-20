package com.example.mylo.cleandemo.domain.usecase;

import android.util.Log;

import com.example.mylo.cleandemo.domain.entity.ZhihuNewsEntity;
import com.example.mylo.cleandemo.domain.exception.ApiException;
import com.example.mylo.cleandemo.domain.repository.ZhihuRepository;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;

public class ZhihuUseCase extends UseCase<ZhihuNewsEntity, Void> {
    private final ZhihuRepository mZhihuRepository;

    public ZhihuUseCase(ZhihuRepository zhihuRepository) {
        mZhihuRepository = zhihuRepository;
    }

    public Flowable<ZhihuNewsEntity> buildUseCaseObservable(Void aVoid) {
        return mZhihuRepository.getMainList()
                .flatMap(this::handleException);
    }

    private Publisher<ZhihuNewsEntity> handleException(ZhihuNewsEntity zhihuNewsList) {
        if (zhihuNewsList == null) {
            return Flowable.error(new ApiException(0, "数据为空，请求个毛线！"));
        }
        return Flowable.just(zhihuNewsList);
    }


}
