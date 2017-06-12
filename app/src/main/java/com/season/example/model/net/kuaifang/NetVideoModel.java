package com.season.example.model.net.kuaifang;

import com.season.example.entry.CommentList;
import com.season.example.entry.VideoList;
import com.season.rapiddevelopment.model.BaseEntry;

import retrofit2.Callback;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 23:46
 */
public class NetVideoModel extends KuaifangNetModel {

    public NetVideoModel() {
        super();
        mApi = getHttpClient().create(INetRequestVideo.class);
    }

    private INetRequestVideo mApi;

    public void getVideo(int pageSize, int action, String maxId, Callback<BaseEntry<VideoList>> callback) {
        mApi.getVideo(pageSize, action, maxId).enqueue(callback);
    }

    public void getComment(int pageSize, String comment_id, String vid, Callback<BaseEntry<CommentList>> callback) {
        mApi.getComment(pageSize, comment_id, vid)
                .enqueue(callback);
    }

    public void sentComment(String vid, String content, Callback<BaseEntry<String>> callback) {
        mApi.sentComment(vid, content, "4")
                .enqueue(callback);
    }
}
