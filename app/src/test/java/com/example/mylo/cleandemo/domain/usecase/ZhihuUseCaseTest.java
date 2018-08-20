package com.example.mylo.cleandemo.domain.usecase;

import org.junit.Test;
import org.mockito.Mockito;


public class ZhihuUseCaseTest {


    @Test
    public void buildUseCaseObservable() {
        ZhihuUseCase zhihuUseCase = Mockito.mock(ZhihuUseCase.class);
        zhihuUseCase.buildUseCaseObservable(null);
    }

}