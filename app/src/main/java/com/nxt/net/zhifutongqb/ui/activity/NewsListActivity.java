package com.nxt.net.zhifutongqb.ui.activity;import android.content.Intent;import android.os.Handler;import android.os.Message;import android.support.v4.widget.SwipeRefreshLayout;import android.util.Log;import android.view.LayoutInflater;import android.view.View;import android.widget.AbsListView;import android.widget.ListView;import com.google.gson.Gson;import com.google.gson.reflect.TypeToken;import com.lzy.okgo.OkGo;import com.lzy.okgo.callback.StringCallback;import com.nxt.net.zhifutongqb.R;import com.nxt.net.zhifutongqb.app.API;import com.nxt.net.zhifutongqb.app.WebActivity;import com.nxt.net.zhifutongqb.bean.ParameterBean;import com.nxt.net.zhifutongqb.ui.adapter.CommonAdapter;import com.nxt.net.zhifutongqb.ui.adapter.ViewHolder;import com.nxt.net.zhifutongqb.base.BaseTitleActivity;import com.nxt.net.zhifutongqb.bean.NewsBean;import com.nxt.net.zhifutongqb.utils.ZPreferenceUtils;import com.nxt.net.zhifutongqb.utils.ZToastUtils;import java.util.ArrayList;import java.util.List;import butterknife.BindView;import okhttp3.Call;import okhttp3.Response;/** * Created by Maven on 2017/3/6. * Email: cyjiang_11@163.com * Description: */public class NewsListActivity extends BaseTitleActivity implements SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener {    private static final String TAG = "NewsListActivity";    @BindView(R.id.listview_common)    ListView mListview;    @BindView(R.id.swipe_container)    SwipeRefreshLayout swipeRefreshLayout;    private ParameterBean parameterBean;    private String parameter;    private View footerview;    private View headerView;    private List<NewsBean.RowsBean> rowsBeanList = new ArrayList<>();    private boolean scrollFlag = false;// 标记是否滑动    private int page = 1;    private String rows = "10";    @Override    protected void initView() {        parameterBean = new ParameterBean();        parameterBean.setParamName("type");        parameterBean.setParamValue("NewsC_PolicyInt");        parameterBean.setOperation("Equal");        parameterBean.setLogic("AND");        parameter = beanToJSONString(parameterBean);        headerView = LayoutInflater.from(this).inflate(R.layout.layout_empty_heade, null);        mListview.addHeaderView(headerView);        footerview = LayoutInflater.from(this).inflate(R.layout.default_loading, null);        swipeRefreshLayout.setColorSchemeResources(                android.R.color.holo_blue_light,                android.R.color.holo_green_light,                android.R.color.holo_orange_light,                android.R.color.holo_red_light        );        swipeRefreshLayout.setOnRefreshListener(this);        mListview.setOnScrollListener(this);        showDialog();        getNews();    }    @Override    protected int getLayout() {        return R.layout.activity_news_list;    }    private void getNews() {        Log.e(TAG, "getNews: parameter---------->" + ZPreferenceUtils.getPrefString(API.USER_ID, ""));        if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);        mListview.removeFooterView(footerview);        if (isnetconnected) {            String parameterjson = "[" + parameter.toString() + "]";            Log.e(TAG, "getNews: parameter---------->" + parameterjson);            OkGo.post(API.NEWS_URL)                    .tag(this)                    .params("key", API.API_KEY)                    .params("typeKey", "NewsCenter")                    .params("ParameterJson", parameterjson)                    .params("page", page)                    .params("rows", rows)                    .params("sidx", "createdate")                    .params("sord", "desc")                    .params("UserId", ZPreferenceUtils.getPrefString(API.USER_ID, ""))                    .execute(new StringCallback() {                        @Override                        public void onSuccess(String s, Call call, Response response) {                            if (page == 1) {                                NewsBean newsBean = new Gson().fromJson(s, new TypeToken<NewsBean>() {                                }.getType());                                rowsBeanList = newsBean.getRows();                                showNewListView();                            } else {                                NewsBean newsBean = new Gson().fromJson(s, new TypeToken<NewsBean>() {                                }.getType());                                List<NewsBean.RowsBean> addRowsBeanList = newsBean.getRows();                                if (addRowsBeanList.size() > 0) {                                    rowsBeanList.addAll(addRowsBeanList);                                    showNewListView();                                } else {                                    ZToastUtils.showShort(NewsListActivity.this, "数据加载完毕");                                }                            }                            Log.e(TAG, "onSuccess: result----------->" + s);                        }                    });        } else {            ZToastUtils.showShort(this, R.string.net_error);            dismissDialog();        }    }    private void showNewListView() {        mListview.setAdapter(new CommonAdapter<NewsBean.RowsBean>(NewsListActivity.this, rowsBeanList, R.layout.item_news) {            @Override            public void convert(final ViewHolder holder, final NewsBean.RowsBean rowsBean, final int position, View convertView) {                String str = rowsBean.getCreatedate().trim();                String spStr[] = str.split("T");                String date = spStr[0].trim();                holder.setText(R.id.tv_title, rowsBean.getTitle());                holder.setText(R.id.tv_content, rowsBean.getGuide());                holder.setText(R.id.tv_source, rowsBean.getArticlesource());                holder.setText(R.id.tv_time, date);                holder.setImageUrl(R.id.iv_pic, API.HOST + rowsBean.getImgurl());                dismissDialog();                //监听事件                holder.setOnClickListener(R.id.ll_news, new View.OnClickListener() {                    @Override                    public void onClick(View v) {                        startActivity(new Intent(NewsListActivity.this, WebActivity.class)                                .putExtra("title", "新闻详情")                                .putExtra("url", String.format(API.NEWS_DETAIL_URL, rowsBean.getNewsid())));                    }                });            }        });    }    private Handler handler = new Handler() {        @Override        public void handleMessage(Message msg) {            if (msg.what == 0) {                page = 1;                if (rowsBeanList != null) rowsBeanList.clear();            } else {                page++;            }            getNews();            super.handleMessage(msg);        }    };    private void loadMore() {        runOnUiThread(new Runnable() {            @Override            public void run() {                handler.sendEmptyMessageDelayed(1, 1500);            }        });    }    @Override    public void onRefresh() {        page = 1;        getNews();    }    @Override    public void onScrollStateChanged(AbsListView view, int scrollState) {        switch (scrollState) {            // 当不滚动时            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:// 是当屏幕停止滚动时                scrollFlag = false;                // 判断滚动到底部                if (mListview.getLastVisiblePosition() == (mListview                        .getCount() - 1)) {                    mListview.addFooterView(footerview);                    loadMore();                }                // 判断滚动到顶部                if (mListview.getFirstVisiblePosition() == 0) {                }                break;            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滚动时                scrollFlag = true;                break;            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:// 是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时                scrollFlag = false;                break;        }    }    @Override    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {    }}