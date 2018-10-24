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
import java.util.List;
import java.util.concurrent.ExecutorService;

import de.greenrobot.event.EventBus;
import okhttp3.Response;


public class WidgetHelper
{
    private OkHttpHelper okHttpHelper;
    private ExecutorService singleTaskExecutor;
    private ExecutorService fixedTaskExcutor;

    private List<Article> articleList;

    private static final String TAG = "WidgetHelper";

    public WidgetHelper()
    {
        okHttpHelper = OkHttpHelper.getM_OkHttpHelper();
        singleTaskExecutor = ThreadPool.getSingleTaskExecutor();
        fixedTaskExcutor = ThreadPool.getFixedTaskExecutor();
    }


    /**
     * 获取当日十大热门话题内容
     * @throws IOException
     */
    public void getTopten()
    {
        final String url = BYR_BBS_API.buildUrl(BYR_BBS_API.STRING_WIDGET, BYR_BBS_API.STRING_TOPTEN);

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

                    EventBus.getDefault().post(new Event.Topten_ArticleList(articleList, false));
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

}
