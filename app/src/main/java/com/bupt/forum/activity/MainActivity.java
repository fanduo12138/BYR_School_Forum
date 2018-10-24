package com.bupt.forum.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RadioGroup;

import com.bupt.forum.R;
import com.bupt.forum.fragment.BoardFragment;
import com.bupt.forum.fragment.SearchFragment;
import com.bupt.forum.fragment.ToptenFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";

    private ToptenFragment toptenFragment;
    private BoardFragment boardFragment;
    private SearchFragment searchFragment;
    private FragmentManager fragmentManager;

    private RadioGroup radioGroup;
    private ViewPager viewPager;

    private int checked_id = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setLogo(R.mipmap.logo_white);
            actionBar.setDisplayUseLogoEnabled(true);
        }

        initView();
    }



    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        outState.putInt("checked_id", checked_id);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Log.v(TAG, "onStart");
    }


    /**
     * 初始化布局
     */
    public void initView()
    {
        fragmentManager = getSupportFragmentManager();
        radioGroup = (RadioGroup) findViewById(R.id.tab_menu);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId)
                {
                    case R.id.topten:
                        viewPager.setCurrentItem(0, true);
                        checked_id = 0;
                        break;
                    case R.id.borad:
                        viewPager.setCurrentItem(1, true);
                        checked_id = 1;
                        break;
                    case R.id.main_search:
                        viewPager.setCurrentItem(2, true);
                        checked_id = 2;
                        break;
                }
            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        toptenFragment = new ToptenFragment();
        boardFragment = new BoardFragment();
        searchFragment = new SearchFragment();
        List<Fragment> allFragments = new ArrayList<>();
        allFragments.add(toptenFragment);
        allFragments.add(boardFragment);
        allFragments.add(searchFragment);

        viewPager.setAdapter(new MyFragmentPagerAdapter(fragmentManager, allFragments));
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                switch (position)
                {
                    case 0:
                        radioGroup.check(R.id.topten);
                        break;
                    case 1:
                        radioGroup.check(R.id.borad);
                        break;
                    case 2:
                        radioGroup.check(R.id.main_search);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
    }


    /**
     * 重写按下返回键的响应
     */
    @Override
    public void onBackPressed()
    {
        moveTaskToBack(true);//true对任何Activity都适用
    }

    class MyFragmentPagerAdapter extends FragmentPagerAdapter
    {
        private List<Fragment> list;

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

}







