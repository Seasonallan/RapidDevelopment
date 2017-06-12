package com.season.rapiddevelopment.ui.pulltorefresh;

import android.view.View;

import com.season.rapiddevelopment.ui.BaseRecycleAdapter;
import com.season.rapiddevelopment.ui.empty.IEmptyView;

import java.util.List;

/**
 * Disc: 下拉刷新组件回调
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 16:22
 */
public interface IPull2RefreshAction<T> {

    /**
     * 查找资源
     * @param id
     * @return
     */
    View findViewById(int id);

    /**
     * 获取加载错误或为空视图
     * @return
     */
    IEmptyView getEmptyView();

    /**
     * 初始化Adapter
     * @param result
     * @return
     */
    BaseRecycleAdapter initAdapter(List<T> result);

    /**
     * 获取Adapter
     * @return
     */
    BaseRecycleAdapter getAdapter();
}