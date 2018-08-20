package com.example.mylo.cleandemo;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mylo.cleandemo.presentation.base.BaseActivity;
import com.example.mylo.cleandemo.presentation.presenter.MainPresenter;
import com.example.mylo.cleandemo.presentation.view.MainView;

import java.util.List;

public class MainActivity extends BaseActivity<MainPresenter> implements MainView {

    private ListView list_view;


    @Override
    protected int getContentResId() {
        return R.layout.activity_main;
    }

    @Override
    public void showList(List<String> list) {
        MAdapter adapter = new MAdapter(list);
        list_view.setAdapter(adapter);
    }

    @Override
    protected void initView() {
        list_view = findViewById(R.id.list_view);
    }

    @Override
    protected void initData() {
        mPresenter.getZhihuList();
        mPresenter.getLogin();
    }

    @Override
    protected void initPresenter() {
        this.mPresenter = new MainPresenter(this);

    }

    private class MAdapter extends BaseAdapter {

        private List<String> list;

        private MAdapter(List<String> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public String getItem(int position) {
            return list == null ? "" : list.get(position);

        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(MainActivity.this, R.layout.adapter_list, null);
            }
            TextView tv = convertView.findViewById(R.id.text);
            tv.setText(getItem(position));
            return convertView;
        }
    }
}
