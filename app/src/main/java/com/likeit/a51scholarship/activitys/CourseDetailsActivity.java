package com.likeit.a51scholarship.activitys;


import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.android.tedcoder.wkvideoplayer.util.DensityUtil;
import com.android.tedcoder.wkvideoplayer.view.MediaController;
import com.android.tedcoder.wkvideoplayer.view.SuperVideoPlayer;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.activitys.userdetailsfragment.UserDetailsFragment01;
import com.likeit.a51scholarship.activitys.userdetailsfragment.UserDetailsFragment02;
import com.likeit.a51scholarship.adapters.AnswersUserDetailsTabAdapter;
import com.likeit.a51scholarship.view.NoScrollViewPager01;
import com.likeit.a51scholarship.view.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CourseDetailsActivity extends Container implements View.OnClickListener,
        PullToRefreshBase.OnRefreshListener2<ScrollView> {
    @BindView(R.id.video_player_item_1)
   SuperVideoPlayer mSuperVideoPlayer;
    @BindView(R.id.play_btn)
    View mPlayBtnView;
    @BindView(R.id.iv_header_left)
    ImageView ivLeft;
    @BindView(R.id.course_details_scrollview)
    PullToRefreshScrollView mPullToRefreshScrollView;
    @BindView(R.id.course_details_sliding_tabs)
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.course_details_viewpager)
    NoScrollViewPager01 viewpager;
    private String[] titles=new String[]{"详情","章节","评论"};
    private List<Fragment> fragments=new ArrayList<>();
    private AnswersUserDetailsTabAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        ButterKnife.bind(this);
        initView();


    }

    private void initView() {

        //Uri uri = Uri.parse("http://bvideo.spriteapp.cn/video/2016/0704/577a4c29e1f14_wpd.mp4");
        mPlayBtnView.setOnClickListener(this);
        mSuperVideoPlayer.setVideoPlayCallback(mVideoPlayCallback);
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

        fragments.add(new UserDetailsFragment01());
        fragments.add(new UserDetailsFragment02());
        fragments.add(new UserDetailsFragment01());
        adapter=new AnswersUserDetailsTabAdapter(getSupportFragmentManager(),titles,fragments);
        viewpager.setAdapter(adapter);
        viewpager.setNoScroll(false);
        slidingTabLayout.setCustomTabView(R.layout.custom_tab_view, R.id.tab_item);
        slidingTabLayout.setTabTitleTextSize(14);//标题字体大小
        slidingTabLayout.setTitleTextColor(this.getResources().getColor(R.color.login_btn_bg_color), this.getResources().getColor(R.color.defualt_textcolor_d));//标题字体颜色
        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        slidingTabLayout.setTabStripWidth(width/(titles.length+1));//滑动条宽度
        slidingTabLayout.setSelectedIndicatorColors(this.getResources().getColor(R.color.login_btn_bg_color));//滑动条颜色
        slidingTabLayout.setDistributeEvenly(true); //均匀平铺选项卡
        slidingTabLayout.setViewPager(viewpager);
    }


    @OnClick({R.id.iv_header_left,R.id.play_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_header_left:
                onBackPressed();
                break;
            case R.id.play_btn:
                mPlayBtnView.setVisibility(View.GONE);
                mSuperVideoPlayer.setVisibility(View.VISIBLE);
                mSuperVideoPlayer.setAutoHideController(false);
                Uri uri = Uri.parse("http://bvideo.spriteapp.cn/video/2016/0704/577a4c29e1f14_wpd.mp4");
                mSuperVideoPlayer.loadAndPlay(uri, 0);
                break;
        }
    }
    /**
     * 播放器的回调函数
     */
    private SuperVideoPlayer.VideoPlayCallbackImpl mVideoPlayCallback = new SuperVideoPlayer.VideoPlayCallbackImpl() {
        /**
         * 播放器关闭按钮回调
         */
        @Override
        public void onCloseVideo() {
            mSuperVideoPlayer.close();//关闭VideoView
            mPlayBtnView.setVisibility(View.VISIBLE);
            mSuperVideoPlayer.setVisibility(View.GONE);
            resetPageToPortrait();
        }

        /**
         * 播放器横竖屏切换回调
         */
        @Override
        public void onSwitchPageType() {
            if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                mSuperVideoPlayer.setPageType(MediaController.PageType.SHRINK);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                mSuperVideoPlayer.setPageType(MediaController.PageType.EXPAND);
            }
        }

        /**
         * 播放完成回调
         */
        @Override
        public void onPlayFinish() {

        }
    };

    /***
     * 旋转屏幕之后回调
     *
     * @param newConfig newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (null == mSuperVideoPlayer) return;
        /***
         * 根据屏幕方向重新设置播放器的大小
         */
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().getDecorView().invalidate();
            float height = DensityUtil.getWidthInPx(this);
            float width = DensityUtil.getHeightInPx(this);
            mSuperVideoPlayer.getLayoutParams().height = (int) width;
            mSuperVideoPlayer.getLayoutParams().width = (int) height;
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            final WindowManager.LayoutParams attrs = getWindow().getAttributes();
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attrs);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            float width = DensityUtil.getWidthInPx(this);
            float height = DensityUtil.dip2px(this, 200.f);
            mSuperVideoPlayer.getLayoutParams().height = (int) height;
            mSuperVideoPlayer.getLayoutParams().width = (int) width;
        }
    }

    /***
     * 恢复屏幕至竖屏
     */
    private void resetPageToPortrait() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            mSuperVideoPlayer.setPageType(MediaController.PageType.SHRINK);
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        mPullToRefreshScrollView.onRefreshComplete();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        mPullToRefreshScrollView.onRefreshComplete();
    }
}
