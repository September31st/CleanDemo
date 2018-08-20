package com.example.mylo.cleandemo.domain.usecase;

import com.example.mylo.cleandemo.domain.entity.TestEntity;
import com.example.mylo.cleandemo.domain.repository.TestRepository;

import io.reactivex.Flowable;

public class TestUseCase extends UseCase<TestEntity, Void> {

    private final TestRepository testRepository;


    private String name;
    private String pwd;

    public TestUseCase(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Override
    protected Flowable<TestEntity> buildUseCaseObservable(Void aVoid) {
        return testRepository.testLogin(name, pwd);
    }

    public TestUseCase setAccount(String name) {
        this.name = name;
        return this;
    }

    public TestUseCase setPwd(String pwd) {
        this.pwd = pwd;
        return this;
    }

}
