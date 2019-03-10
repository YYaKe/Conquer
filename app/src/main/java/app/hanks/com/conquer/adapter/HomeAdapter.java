package app.hanks.com.conquer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import app.hanks.com.conquer.R;
import app.hanks.com.conquer.bean.ListType;
import app.hanks.com.conquer.bean.TodoListBean;

/**
 * author：wiki on 2019/3/10
 * email：zhengweiqunemail@qq.com
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    public static final int TYPE_TODO_TYPE = 0;
    public static final int TYPE_TODO_DATE = 1;
    public static final int TYPE_TODO = 2;

    private List<TodoListBean> mList;
    private Context context;


    public HomeAdapter() {
        mList = new ArrayList<>();
    }

    public HomeAdapter(List<TodoListBean> mList) {
        this.mList = mList;
    }

    static class ViewHolder extends BaseViewHolder {

        TextView titleTv;
        View lineView;
        LinearLayout linearLayout;

        public ViewHolder(View view) {
            super(view);
            titleTv = (TextView) view.findViewById(R.id.tvTitle);
            lineView = view.findViewById(R.layout.line);
            linearLayout = (LinearLayout) view.findViewById(R.id.linear_layout);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titleTv.setText(ListType.getName(mList.get(position).getType()));
        for (int i = 0; i < mList.get(position).getList().size(); i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_todo, null);
            TextView contentTv = (TextView) view.findViewById(R.id.text);
            contentTv.setText(mList.get(position).getList().get(i).getContent());
            holder.linearLayout.addView(view);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addList(List<TodoListBean> listBeans) {
        this.mList = listBeans;
        notifyDataSetChanged();
    }

}
