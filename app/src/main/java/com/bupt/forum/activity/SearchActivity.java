package com.bupt.forum.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bupt.forum.R;
import com.bupt.forum.helper.SearchHelper;
import com.bupt.forum.metadata.Article;
import com.bupt.forum.eventtype.Event;
import com.bupt.forum.adapter.SearchResultArticleListAdapter;
import com.bupt.forum.global.ContextApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView searchList;
    private List<Map<String, Object>> listItems = new ArrayList<>();
    private List<Article> articleList = new ArrayList<>();
    public String board_info;
    public String search_content;
    public SearchResultArticleListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchList = (ListView) findViewById(R.id.search_list);
        Intent intent = getIntent();
        board_info = intent.getStringExtra("Board_Info");
        search_content = intent.getStringExtra("Search_Content");
        searchList.setOnItemClickListener(this);

        HashMap<String, String> params_map = new HashMap<>();
        params_map.put("o","1");
        params_map.put("title1", search_content);
        params_map.put("board", board_info);
        SearchHelper searchHelper = new SearchHelper();
        searchHelper.SearchResult(params_map);

        EventBus.getDefault().registerSticky(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Article article = articleList.get(position);

        Intent intent = new Intent(this, ReadArticleActivity.class);
        intent.putExtra("board_name", article.getBoard_name());
        intent.putExtra("article_id", article.getId());
        startActivity(intent);
    }

    public void onEventMainThread(Event.Search_ArticleList search_articleList)
    {
        for(Article article : search_articleList.getSearch_list())
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("board", article.getBoard_name());
            map.put("title", article.getTitle());

            listItems.add(map);
            articleList.add(article);
        }
        if(adapter == null)
        {
            adapter = new SearchResultArticleListAdapter(ContextApplication.getAppContext(), listItems);
            searchList.setAdapter(adapter);
        }
        else
            adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        //注销EventBus
        //注意此处一定要注销，否则会出现问题，具体内容见 http://bbs.byr.cn/#!article/MobileTerminalAT/30560
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        //注册EventBus
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        //注销EventBus
        EventBus.getDefault().unregister(this);
    }


}
