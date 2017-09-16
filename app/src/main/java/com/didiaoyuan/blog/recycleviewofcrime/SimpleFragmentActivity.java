package com.didiaoyuan.blog.recycleviewofcrime;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Mr.Qu on 2017/9/10.
 * 这是一个通用的加载Fragment的超类
 */

public abstract class SimpleFragmentActivity extends AppCompatActivity{
//    添加一个抽象方法来生成Fragment
    protected abstract Fragment createFragment();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        加载Fragment 托管布局
        setContentView(R.layout.activity_fragment);
//        初始化FragmentManager
        FragmentManager fm=getSupportFragmentManager();
//        获取Fragment的组件
        Fragment fragment=fm.findFragmentById(R.id.fragment_container);
//        提交Fragment的事务
        if(fragment==null){
            fragment=createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container,fragment)
                    .commit();
        }
    }
}
