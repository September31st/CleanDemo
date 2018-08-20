package com.example.mylo.cleandemo.domain.repository;

import com.example.mylo.cleandemo.domain.entity.TestEntity;
import com.example.mylo.cleandemo.domain.entity.ZhihuNewsEntity;

import io.reactivex.Flowable;

public interface TestRepository {
    Flowable<TestEntity> testLogin(String name, String pwd);
}
