package com.bupt.forum.helper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.bupt.forum.eventtype.Event;
import com.bupt.forum.metadata.Article;
import com.bupt.forum.network.OkHttpHelper;
import com.bupt.forum.sdkutil.BYR_BBS_API;
import com.bupt.forum.threadpool.ThreadPool;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;

import de.greenrobot.event.EventBus;
import okhttp3.Response;


public class SearchHelper
{
    private OkHttpHelper okHttpHelper;
    private ExecutorService singleTaskExecutor;

    private List<Article> articleList;

    private static final String TAG = "SearchHelper";

    public SearchHelper()
    {
        okHttpHelper = OkHttpHelper.getM_OkHttpHelper();
        singleTaskExecutor = ThreadPool.getSingleTaskExecutor();
    }


    /**
     * 获取搜索结果
     */
    public void SearchResult(final HashMap<String, String> params_map)
    {
        final String url = BYR_BBS_API.buildGETUrl(params_map, BYR_BBS_API.STRING_SEARCH, BYR_BBS_API.STRING_ARTICLE);

        singleTaskExecutor.execute(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Response response = okHttpHelper.getExecute(url);
                    String response_result = response.body().string();

                    JSONObject jsonObject = JSON.parseObject(response_result);
                    response_result = jsonObject.getString("article");

                    //为了得到包含在[]中的Article数组，不然使用Gson.fromJson时会报错。后续会看看有没有优化的方法。
                    articleList = new Gson().fromJson(response_result, new TypeToken<List<Article>>(){}.getType());

                    EventBus.getDefault().post(new Event.Search_ArticleList(articleList));
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

}
