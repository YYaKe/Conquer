package app.hanks.com.conquer.main;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

import app.hanks.com.conquer.R;
import app.hanks.com.conquer.activity.PostActivity;
import app.hanks.com.conquer.adapter.HomeAdapter;
import app.hanks.com.conquer.base.BaseActivity;
import app.hanks.com.conquer.bean.FinishBean;
import app.hanks.com.conquer.bean.TodoBean;
import app.hanks.com.conquer.bean.TodoListBean;
import app.hanks.com.conquer.config.Constants;
import app.hanks.com.conquer.util.L;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;


/**
 * author：wiki on 2019/3/10
 * email：zhengweiqunemail@qq.com
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    //---UIView--
    private SwipeRefreshLayout mRefresh;
    private RecyclerView recyclerView;
    private FrameLayout mFragmentContainerView;
    private FloatingActionButton mFab;

    private HomeAdapter homeAdapter;

    @Override
    protected void initView() {
        setContentView(R.layout.home);

        mFragmentContainerView = (FrameLayout) findViewById(R.id.fragmentContainer);
        recyclerView = (RecyclerView) findViewById(R.id.todoListView);
        mRefresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mFab = (FloatingActionButton) findViewById(R.id.fab);

        mFab.setOnClickListener(this);
        mRefresh.setOnRefreshListener(this);
        homeAdapter = new HomeAdapter();
        recyclerView.setAdapter(homeAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        homeAdapter.clear();
        findToDoList();
        findFinishList();
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, PostActivity.class));
    }

    @Override
    public void onRefresh() {
        if (mRefresh.isRefreshing())
            onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mRefresh.isRefreshing()) {
                    mRefresh.setRefreshing(false);
                }
            }
        }, 1000);
    }

    private void findToDoList() {
        BmobQuery<TodoBean> query = new BmobQuery<>();
        //按照时间降序
        query.order("-createdAt");
        //执行查询，第一个参数为上下文，第二个参数为查找的回调
        query.findObjects(this, new FindListener<TodoBean>() {

            @Override
            public void onSuccess(List<TodoBean> todoBeans) {
                L.i("findToDoList onSuccess");
                TodoListBean listBean = new TodoListBean(Constants.TO_DO, todoBeans, null);
                homeAdapter.addList(listBean);
            }

            @Override
            public void onError(int code, String arg0) {
                L.i("findToDoList onError");
            }
        });
    }

    private void findFinishList() {
        BmobQuery<FinishBean> query = new BmobQuery<>();
        //按照时间降序
        query.order("-createdAt");
        //执行查询，第一个参数为上下文，第二个参数为查找的回调
        query.findObjects(this, new FindListener<FinishBean>() {

            @Override
            public void onSuccess(List<FinishBean> finishBeans) {
                L.i("findFinishList onSuccess");
                TodoListBean listBean = new TodoListBean(Constants.FINISH, null, finishBeans);
                homeAdapter.addList(listBean);
            }

            @Override
            public void onError(int code, String arg0) {
                L.i("findFinishList onError");
            }
        });
    }

}
