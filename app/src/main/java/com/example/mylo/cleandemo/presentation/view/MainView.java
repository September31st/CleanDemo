package com.example.mylo.cleandemo.presentation.view;

import com.example.mylo.cleandemo.presentation.base.IView;

import java.util.List;

public interface MainView extends IView {
    void showList(List<String> list);
}
