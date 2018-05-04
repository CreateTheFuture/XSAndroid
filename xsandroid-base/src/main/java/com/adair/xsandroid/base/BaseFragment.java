package com.adair.xsandroid.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * package：    com.simple.library.ui
 * author：     XuShuai
 * date：       2017/9/20  10:08
 * version:     v1.0
 * describe：   BaseFragment
 */
public abstract class BaseFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
