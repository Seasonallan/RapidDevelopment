package com.season.example.presenter;

import com.season.example.entry.CommentItem;
import com.season.example.entry.CommentList;
import com.season.example.model.ModelFactory;
import com.season.rapiddevelopment.Configure;
import com.season.rapiddevelopment.presenter.BasePresenter;
import com.season.rapiddevelopment.tools.Console;
import com.season.rapiddevelopment.ui.BaseRecycleAdapter;
import com.season.rapiddevelopment.ui.IView;

import javax.inject.Inject;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 00:28
 */
public class CommentPresenter extends BasePresenter {

    @Inject
    public CommentPresenter(IView view){
        super(view);
    }

    protected <T> void onResponse2UI(int type, T result) {
        super.onResponse2UI(type, result);
        if (type == REFRESH){
            ModelFactory.local().file().commcon().setValue("DetailView", result, null);
        }
    }

    /**
     * 发送评论
     * @param comment
     */
    public void sendComment(String vid, String comment){
        ModelFactory.net().kuaifang().video().sentComment(vid, comment, new HttpCallback<String>(11));
    }

    /**
     * 获取列表数据
     * @param callType
     */
    public void loadList(final String vid, int callType) {
        if (callType == CREATE){
            getView().getLoadingView().showLoadingView();
            Console.logNetMessage("check local cache");
            ModelFactory.local().file().commcon().getValue("DetailView" + vid, new LocalObserver<CommentList>() {
                @Override
                public void onError(Throwable e) {
                    Console.logNetMessage("empty local cache, load from net");
                    loadList(vid, REFRESH);
                }

                @Override
                public void onNext(CommentList o) {
                    Console.logNetMessage("local cache");
                    super.onNext(o);
                }
            });
            return;
        }

        String maxId = "0";

        BaseRecycleAdapter adapter = getView().getAdapter();
        if (callType == MORE) {
            if (adapter != null && adapter.getCount() > 0) {
                maxId = ((CommentItem)adapter.getRealItem(adapter.getCount() - 1)).id;
            }
        }
        ModelFactory.net().kuaifang().video().getComment(Configure.PAGE_SIZE, maxId, vid, new HttpCallback<CommentList>(callType));

    }


}
