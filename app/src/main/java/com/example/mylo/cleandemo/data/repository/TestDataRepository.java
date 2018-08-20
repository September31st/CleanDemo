package com.example.mylo.cleandemo.data.repository;


import android.util.Log;

import com.example.mylo.cleandemo.domain.entity.TestEntity;
import com.example.mylo.cleandemo.domain.repository.TestRepository;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TestDataRepository implements TestRepository {

    private static final String TAG = "tag";

    private static final String USER_NAME = "123456";
    private static final String PWD = "123456";


    public TestDataRepository() {
    }

    @Override
    public Flowable<TestEntity> testLogin(String name, String pwd) {
        return test5(name, pwd);
    }

    private Flowable<TestEntity> test5(final String name, final String pwd) {
        Flowable<TestEntity> flowable = Flowable.create(new FlowableOnSubscribe<TestEntity>() {
            @Override
            public void subscribe(FlowableEmitter<TestEntity> emitter) throws Exception {

            }
        }, BackpressureStrategy.BUFFER); //增加了一个参数

        Subscriber<TestEntity> subscriber = new Subscriber<TestEntity>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(TestEntity testEntity) {
                new Thread(() -> {
                    synchronized (Thread.currentThread()) {
                        try {
                            Thread.currentThread().wait(1000);
                            if (name.equals(USER_NAME) && pwd.equals(PWD)) {
                                testEntity.setTestString("登录成功");
                            } else {
                                testEntity.setTestString("登录失败");
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }

            @Override
            public void onError(Throwable t) {
                Log.w(TAG, "onError: ", t);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        };

        return flowable;
    }

}
