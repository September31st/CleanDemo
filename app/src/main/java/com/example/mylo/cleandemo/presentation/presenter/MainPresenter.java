package com.example.mylo.cleandemo.presentation.presenter;

import android.content.Context;
import android.util.Log;

import com.example.mylo.cleandemo.data.api.HttpHelper;
import com.example.mylo.cleandemo.data.repository.TestDataRepository;
import com.example.mylo.cleandemo.data.repository.ZhihuDataRepository;
import com.example.mylo.cleandemo.domain.entity.TestEntity;
import com.example.mylo.cleandemo.domain.entity.ZhihuNewsEntity;
import com.example.mylo.cleandemo.domain.usecase.TestUseCase;
import com.example.mylo.cleandemo.domain.usecase.ZhihuUseCase;
import com.example.mylo.cleandemo.presentation.base.BasePresenter;
import com.example.mylo.cleandemo.presentation.view.MainView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.subscribers.DisposableSubscriber;

public class MainPresenter extends BasePresenter<MainView> {

    private ZhihuUseCase zhihuUseCase;
    private TestUseCase testUseCase;

    public MainPresenter(Context context) {
        this.zhihuUseCase = new ZhihuUseCase(new ZhihuDataRepository(new HttpHelper(context)));
        this.testUseCase = new TestUseCase(new TestDataRepository());
    }

    public void getZhihuList() {
        zhihuUseCase.execute(new DisposableSubscriber<ZhihuNewsEntity>() {
            @Override
            public void onNext(ZhihuNewsEntity zhihuNewsEntity) {
                List<ZhihuNewsEntity.StoriesEntity> tempList = zhihuNewsEntity.getStories();
                List<String> list = new ArrayList<>();
                for (ZhihuNewsEntity.StoriesEntity entity : tempList) {
                    list.add(entity.getTitle());
                }
                mView.showList(list);
            }

            @Override
            public void onError(Throwable e) {
                mView.showError(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });

    }

    public void getLogin() {
        testUseCase.setAccount("123456").setPwd("123456").execute(new DisposableSubscriber<TestEntity>() {
            @Override
            public void onNext(TestEntity testEntity) {
                Log.e("TAG", testEntity.getTestString());
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

}
