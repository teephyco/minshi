package com.yalianxun.mingshi.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.yalianxun.mingshi.R;

public class LoadMoreListView extends ListView implements AbsListView.OnScrollListener{
    private Context mContext;
    private View mFootView;
    private int mTotalItemCount;
    private OnLoadMoreListener mLoadMoreListener;
    private boolean mIsLoading=false;

    public LoadMoreListView(Context context) {
        super(context);
        init(context);
    }

    public LoadMoreListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public LoadMoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        this.mContext=context;
        mFootView= LayoutInflater.from(context).inflate(R.layout.header_view_layout,null);
        setOnScrollListener(this);
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // 滑到顶部后自动加载，判断listview已经停止滚动并且最后可视的条目等于 0
        //举一反三这里也可以判断是不是到底部 ，这样 index就是为 总数-1
        int lastVisibleIndex = view.getLastVisiblePosition();
        int firstIndex = view.getFirstVisiblePosition();
        if (!mIsLoading
                && scrollState == OnScrollListener.SCROLL_STATE_IDLE
                && lastVisibleIndex == mTotalItemCount-1
//                && firstIndex == 0
        ) {
            mIsLoading=true;
            addFooterView(mFootView);
//            addHeaderView(mFootView);
            if (mLoadMoreListener!=null) {
                mLoadMoreListener.onLoadMore();
            }
        }

    }


    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mTotalItemCount=totalItemCount;

    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener){
        mLoadMoreListener=listener;
    }

    public interface OnLoadMoreListener{
        void onLoadMore();
    }
    public void setLoadCompleted(){
        mIsLoading=false;
       removeFooterView(mFootView);
        //removeHeaderView(mFootView);
    }
}
