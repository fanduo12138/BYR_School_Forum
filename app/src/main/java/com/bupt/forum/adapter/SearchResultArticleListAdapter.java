package com.bupt.forum.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.BaseAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bupt.forum.R;
import com.bupt.forum.global.ContextApplication;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Arthur on 2017/12/30.
 */

public class SearchResultArticleListAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String, Object>> listItems;
    private LayoutInflater listContainer;

    //本地 SharedPreferences
    private SharedPreferences My_SharedPreferences;

    public class ListItemViewHolder
    {
        public TextView article_board;
        public TextView article_title;
    }

    public SearchResultArticleListAdapter(Context context, List<Map<String, Object>> listItems)
    {
        this.context = context;
        listContainer = LayoutInflater.from(context);
        this.listItems = listItems;

        My_SharedPreferences = ContextApplication.getAppContext().getSharedPreferences("My_SharePreference", Context.MODE_PRIVATE);
    }


    @Override
    public int getCount()
    {
        return listItems.size();
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
//        final int selectID = position;

        //自定义视图
        SearchResultArticleListAdapter.ListItemViewHolder listItemViewHolder;
        if (convertView == null)
        {
            listItemViewHolder = new SearchResultArticleListAdapter.ListItemViewHolder();

            //获取list_item布局文件的视图
            convertView = listContainer.inflate(R.layout.list_item_search_result, null);

            //获取控件对象
            listItemViewHolder.article_board = (TextView) convertView.findViewById(R.id.search_article_board);
            listItemViewHolder.article_title = (TextView) convertView.findViewById(R.id.search_article_title);

            //设置控件集到convertView
            convertView.setTag(listItemViewHolder);
        } else
        {
            listItemViewHolder = (SearchResultArticleListAdapter.ListItemViewHolder) convertView.getTag();
        }

        String board = (String) listItems.get(position).get("board");
        board = My_SharedPreferences.getString(board, board);

        board = board.substring(0, 1);
        listItemViewHolder.article_board.setText(board);

        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        int mColor = Color.rgb(r, g, b);                    // 随机生成颜色

        GradientDrawable drawable = (GradientDrawable) listItemViewHolder.article_board.getBackground();
        drawable.setColor(mColor);

        listItemViewHolder.article_title.setText((String) listItems.get(position).get("title"));

        return convertView;
    }
}
