package com.bupt.forum.helper;

import android.graphics.Bitmap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.bupt.forum.eventtype.Event;
import com.bupt.forum.metadata.User;
import com.bupt.forum.network.OkHttpHelper;
import com.bupt.forum.network.PicassoHelper;
import com.bupt.forum.sdkutil.BYR_BBS_API;
import com.bupt.forum.threadpool.ThreadPool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

import de.greenrobot.event.EventBus;
import okhttp3.Response;


public class UserHelper
{
    private OkHttpHelper okHttpHelper;
    private ExecutorService singleTaskExecutor;

    private static final String TAG = "UserHelper";


    public UserHelper()
    {
        okHttpHelper = OkHttpHelper.getM_OkHttpHelper();
        singleTaskExecutor = ThreadPool.getSingleTaskExecutor();
    }


    /**
     * 用户登录，使用EventBus返回登录用户的元数据
     */
    public void user_Login()
    {
        final String url = BYR_BBS_API.buildUrl(BYR_BBS_API.STRING_USER, BYR_BBS_API.STRING_LOGIN);

        singleTaskExecutor.execute(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Response response = okHttpHelper.getExecute(url);
                    String response_result = response.body().string();
                    User user_me = new Gson().fromJson(response_result, new TypeToken<User>() {}.getType());

                    save_UserFace_to_Local(user_me.getFace_url());

                    BYR_BBS_API.Me = user_me;

                    EventBus.getDefault().post(new Event.My_User_Info(user_me));

                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 根据用户头像的url，获取用户的头像bitmap，并将该图片保存到本地
     *
     * @param face_url
     */
    public void save_UserFace_to_Local(final String face_url)
    {
        singleTaskExecutor.execute(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Bitmap bitmap = PicassoHelper.getPicassoHelper().getBitmap(face_url, 1);
                    BYR_BBS_API.My_Face = bitmap;

                    //创建本地储存文件夹及对应文件
                    String root_dic = BYR_BBS_API.LOCAL_FILEPATH;
                    File dirFile = new File(root_dic + BYR_BBS_API.MY_INFO_FOLDER);
                    if (!dirFile.exists())
                        dirFile.mkdirs();
                    File file = new File(dirFile + BYR_BBS_API.MY_FACE_NAME);

                    //将bitmap保存到本地文件中
                    FileOutputStream fout = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fout);
                    fout.flush();
                    fout.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
}
