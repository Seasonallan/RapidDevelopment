package com.season.rapiddevelopment.model.entry;

import android.text.TextUtils;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 21:22
 */
public class BaseEntry<T> {

    public boolean isTrue(String name) {
        if (TextUtils.isEmpty(name)) {
            return false;
        }
        return name.equals("true");
    }

    public String code;

    public String msg;

    public T data;

    /**
     * 播单创建超出上限，目前最多只能创建 100 个
     * @return
     */
    public boolean isCollectOutOfBounds(){
        return code != null && code.equals("414");
    }

    /**
     * 收藏视频创建超出上限，目前最多只能创建 100 个
     * @return
     */
    public boolean isVideoOutOfBounds(){
        return code != null && code.equals("415");
    }

    /**
     * clientId是否过期
     * @return
     */
    public boolean isClientIdInvalid(){
        return code != null && code.equals("403");
    }

    /**
     * 是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        return !TextUtils.isEmpty(code) && code.equals("200");
    }

}
