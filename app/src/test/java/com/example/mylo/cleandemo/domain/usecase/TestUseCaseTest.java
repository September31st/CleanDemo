package com.example.mylo.cleandemo.domain.usecase;

import com.example.mylo.cleandemo.domain.repository.TestRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class TestUseCaseTest {

    private TestUseCase testUseCase;

    private static final String FAKE_USER_ACCOUNT = "123456";
    private static final String FAKE_USER_PWD = "123456";

    @Mock
    private TestRepository mockUserRepository;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        testUseCase = new TestUseCase(mockUserRepository);
        testUseCase.setAccount(FAKE_USER_ACCOUNT);
        testUseCase.setPwd(FAKE_USER_PWD);
    }

    @Test
    public void buildUseCaseObservable() {
        testUseCase.buildUseCaseObservable(null);
        verify(mockUserRepository).testLogin(FAKE_USER_ACCOUNT, FAKE_USER_PWD);
        verifyNoMoreInteractions(mockUserRepository);//验证mockUserRepository是否还有其他地方被调用过
    }

}