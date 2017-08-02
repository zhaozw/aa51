package com.likeit.a51scholarship.activitys.newsfragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.fragments.BaseFragment;
import com.likeit.a51scholarship.utils.ListScrollUtil;
import com.likeit.a51scholarship.view.MyListview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewFragment01 extends BaseFragment implements
        PullToRefreshBase.OnRefreshListener2<ScrollView>{

    PullToRefreshScrollView mPullToRefreshScrollView;
    // 图片封装为一个数组
    private int[] icon = {R.mipmap.test02, R.mipmap.test02,
            R.mipmap.test02, R.mipmap.test02, R.mipmap.test02};
    private String[] iconName = {"牛津布鲁克斯大学2017年奖学金开发申请 7 月截止", "牛津布鲁克斯大学2017年奖学金开发申请 7 月截止",
            "牛津布鲁克斯大学2017年奖学金开发申请 7 月截止", "牛津布鲁克斯大学2017年奖学金开发申请 7 月截止", "牛津布鲁克斯大学2017年奖学金开发申请 7 月截止"};
    private String[] iconReadNumber = {"阅读520", "阅读560", "阅读500", "阅读560", "阅读500"};
    private String[] iconReadTime = {"20分钟前", "10分钟前", "30分钟前", "10分钟前", "30分钟前"};
    private String[] iconCommmentNumber = {"14", "23", "55", "2", "54"};
    private SimpleAdapter simpleAdapter;
    private List<Map<String, Object>> dataList;
    private MyListview mListview;

    @Override
    protected int setContentView() {
        return R.layout.fragment_new_fragment01;
    }

    @Override
    protected void lazyLoad() {
        initView();
    }

    private void initView() {
        mPullToRefreshScrollView = findViewById(R.id.news_header_scrollview);
        mPullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        mPullToRefreshScrollView.setOnRefreshListener(this);
        mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
                "上次刷新时间");
        mPullToRefreshScrollView.getLoadingLayoutProxy()
                .setPullLabel("下拉刷新");
//          mPullRefreshScrollView.getLoadingLayoutProxy().setRefreshingLabel(
//                      "refreshingLabel");
        mPullToRefreshScrollView.getLoadingLayoutProxy().setReleaseLabel(
                "松开即可刷新");
        mListview = findViewById(R.id.news_header_listview);
        /**
         * 消息
         */
        dataList = new ArrayList<Map<String, Object>>();
        getData();
        String[] from = {"img","name", "readNumber","readTime","commentNumber"};
        int[] to = {R.id.live_list_item_bg, R.id.live_list_item_result_tv, R.id.live_list_item_time_tv,
                R.id.iv_live_avatar,R.id.live_name_tv,R.id.live_speciality_tv};
        simpleAdapter = new SimpleAdapter(getActivity(), dataList, R.layout.news_listview_items, from, to);
        //配置适配器
        mListview.setAdapter(simpleAdapter);
        ListScrollUtil.setListViewHeightBasedOnChildren(mListview);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentNewDetails=new Intent(getActivity(),NewsDetailsActivity.class);
                startActivity(intentNewDetails);
            }
        });
    }
    private List<Map<String, Object>> getData() {
        for (int i = 0; i < icon.length; i++) {
            Log.d("TAG", "" + icon.length);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img", icon[i]);
            map.put("name", iconName[i]);
            map.put("readNumber", iconReadNumber[i]);
            map.put("readTime", iconReadTime[i]);
            map.put("commentNumber", iconCommmentNumber[i]);
            dataList.add(map);
        }
        return dataList;
    }
    public static NewFragment01 newInstance(String text) {
        Bundle args = new Bundle();
        args.putString("text", text);
        NewFragment01 fragment = new NewFragment01();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        ListScrollUtil.setListViewHeightBasedOnChildren(mListview);
        mPullToRefreshScrollView.onRefreshComplete();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        ListScrollUtil.setListViewHeightBasedOnChildren(mListview);
        mPullToRefreshScrollView.onRefreshComplete();
    }
}