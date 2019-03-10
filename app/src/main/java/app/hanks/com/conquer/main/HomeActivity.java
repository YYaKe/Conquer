package app.hanks.com.conquer.main;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import app.hanks.com.conquer.R;
import app.hanks.com.conquer.adapter.HomeAdapter;
import app.hanks.com.conquer.base.BaseActivity;
import app.hanks.com.conquer.bean.TodoBean;
import app.hanks.com.conquer.bean.TodoListBean;
import app.hanks.com.conquer.util.L;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;


/**
 * author：wiki on 2019/3/10
 * email：zhengweiqunemail@qq.com
 */
public class HomeActivity extends BaseActivity {

    //---UIView--
    private SwipeRefreshLayout mRefresh;
    private RecyclerView recyclerView;
    private FrameLayout mFragmentContainerView;
    private FloatingActionButton mFab;
    private HomeAdapter homeAdapter;
    private List<TodoListBean> todoListBeans = new ArrayList<>();

    private int mCategory;

    @Override
    protected void initView() {
        setContentView(R.layout.home);
        mFragmentContainerView = (FrameLayout) findViewById(R.id.fragmentContainer);
        recyclerView = (RecyclerView) findViewById(R.id.todoListView);
        mRefresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mFab = (FloatingActionButton) findViewById(R.id.fab);

        //---测试数据----
        List<TodoBean> listBeans = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            listBeans.add(new TodoBean(1222, "", i + "--毕业设计",
                    0, "", 0, 1, "", 1, 1, 1));
        }
        for (int i = 0; i < 3; i++) {
            todoListBeans.add(new TodoListBean(1, listBeans));
        }
        //---测试数据----
        homeAdapter = new HomeAdapter(todoListBeans);
        recyclerView.setAdapter(homeAdapter);
        findToDoList();

    }

    private void findToDoList() {
        BmobQuery<TodoListBean> query = new BmobQuery<TodoListBean>();
        //按照时间降序
        query.order("-createdAt");
        //执行查询，第一个参数为上下文，第二个参数为查找的回调
        query.findObjects(this, new FindListener<TodoListBean>() {

            @Override
            public void onSuccess(List<TodoListBean> todoListBeans) {
                L.i("findToDoList onSuccess");
                homeAdapter.addList(todoListBeans);
            }

            @Override
            public void onError(int code, String arg0) {
                L.i("findToDoList onError");
            }
        });
    }
}
