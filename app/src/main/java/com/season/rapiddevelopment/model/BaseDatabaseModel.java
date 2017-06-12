package com.season.rapiddevelopment.model;

import android.content.Context;

import com.season.rapiddevelopment.BaseApplication;
import com.season.rapiddevelopment.tools.Console;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Disc: 本地数据Model基类
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 16:27
 */
public abstract class BaseDatabaseModel {

    public Context mContext;

    public BaseDatabaseModel() {
        mContext = BaseApplication.sContext;
    }

    /**
     * 直接获取数据
     *
     * @param flag
     * @return
     */
    public abstract Object getDataImmediately(int flag);

    /**
     * 直接设置数据
     *
     * @param flag
     * @param value
     * @return
     */
    public abstract boolean setDataImmediately(int flag, Object value);

    /**
     * 使用RxJava响应式获取数据
     *
     * @param flag
     * @param observer
     * @param <T>
     */
    public <T> void getData(int flag, Observer<T> observer) {
        Observable.just(flag)
                .subscribeOn(Schedulers.io())
                .map(new Function<Integer, T>() {
                    @Override
                    public T apply(Integer s) throws Exception {
                        return (T) getDataImmediately(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    class KeyValue {
        int key;
        Object value;
    }

    /**
     * 使用RxJava响应式设置数据
     *
     * @param flag
     * @param item
     * @param observer
     */
    public void setValue(int flag, Object item, Observer<Boolean> observer) {
        KeyValue keyMaps = new KeyValue();
        keyMaps.key = flag;
        keyMaps.value = item;
        Observable observable = Observable.just(keyMaps)
                .subscribeOn(Schedulers.io())
                .map(new Function<KeyValue, Boolean>() {
                    @Override
                    public Boolean apply(KeyValue s) throws Exception {
                        Console.logNetMessage(Thread.currentThread().getName() + " setValue " + s);
                        return setDataImmediately(s.key, s.value);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
        if (observer == null) {
            observable.subscribe();
        } else {
            observable.subscribe(observer);
        }

    }


}
