package com.example.mylo.cleandemo.domain.repository;

import com.example.mylo.cleandemo.domain.entity.ZhihuNewsEntity;

import io.reactivex.Flowable;

public interface ZhihuRepository {
    Flowable<ZhihuNewsEntity> getMainList();
}
