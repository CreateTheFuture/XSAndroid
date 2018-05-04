package com.adair.xsandroid.base;

/**
 * package：    com.adair.xsandroid.base
 * author：     XuShuai
 * date：       2017/12/6  10:52
 * version:     v1.0
 * describe：   当使用Hide()和show()方法显示fragment时，使其在可见时加载数据
 */
public abstract class BaseUnhiddenLoadFragment extends BaseFragment {

    private boolean isFirstLoad = true;//是否第一次加载数据

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            lazyLoad();
        }
    }

    protected void lazyLoad() {
        if (!isFirstLoad) {
            return;
        }
        loadData();
        isFirstLoad = false;
    }

    public abstract void loadData();
}
