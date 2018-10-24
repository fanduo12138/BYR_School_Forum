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


public class BoardHelper
{
    private static final String TAG = "BoardHelper";
    private OkHttpHelper okHttpHelper;
    private ExecutorService singleTaskExecutor;

    public BoardHelper()
    {
        okHttpHelper = OkHttpHelper.getM_OkHttpHelper();
        singleTaskExecutor = ThreadPool.getSingleTaskExecutor();
    }


    public void getSpecifiedBoard(String board_name, final int page)
    {
        HashMap<String, String> params = new HashMap<>();
        params.put("page", String.valueOf(page));
        final String url = BYR_BBS_API.buildGETUrl(params, BYR_BBS_API.STRING_BOARD, board_name);

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
                    List<Article> articles = new Gson().fromJson(response_result, new TypeToken<List<Article>>() {}.getType());
                    EventBus.getDefault().post(new Event.Specified_Board_Articles(articles));
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
}
