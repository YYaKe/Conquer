package app.hanks.com.conquer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.xujiaji.library.RippleCheckBox;
import com.xujiaji.library.RippleCheckBoxUtil;

import java.util.ArrayList;
import java.util.List;

import app.hanks.com.conquer.R;
import app.hanks.com.conquer.bean.ListType;
import app.hanks.com.conquer.bean.TodoBean;
import app.hanks.com.conquer.bean.TodoListBean;
import app.hanks.com.conquer.config.Constants;
import app.hanks.com.conquer.util.L;

/**
 * author：wiki on 2019/3/10
 * email：zhengweiqunemail@qq.com
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {


    private List<TodoListBean> mList;
    private Context context;


    public HomeAdapter() {
        mList = new ArrayList<>();
    }

    static class ViewHolder extends BaseViewHolder {

        TextView titleTv;
        View lineView;
        LinearLayout linearLayout;

        public ViewHolder(View view) {
            super(view);
            titleTv = (TextView) view.findViewById(R.id.tvTitle);
            lineView = view.findViewById(R.id.line);
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.titleTv.setText(ListType.getName(mList.get(position).getType()));
        holder.lineView.setBackgroundColor(context.getResources().getColor(mList.get(position).getType()
                == Constants.TO_DO ? R.color.purple_500 : R.color.grey_800));
        List<TodoBean> beanList = mList.get(position).getList();
        holder.linearLayout.removeAllViews();
        for (int i = 0; i < beanList.size(); i++) {
            L.i("beanList-size:" + beanList.size());
            View view = LayoutInflater.from(context).inflate(R.layout.item_todo, null);
            final TextView contentTv = (TextView) view.findViewById(R.id.text);
            final View line = view.findViewById(R.id.line);
            ImageView priorityImg = (ImageView) view.findViewById(R.id.imgPriority);
            RippleCheckBox checkBox = (RippleCheckBox) view.findViewById(R.id.rippleCheckBox);
            contentTv.setText(beanList.get(i).getContent());
            checkBox.setOnCheckedChangeListener(new RippleCheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RippleCheckBox checkBox, boolean isChecked) {
                    if (isChecked) {
                        //动画效果，横线上移，文字alpha为0.3
                        line.animate()
                                .setDuration(400)
                                .translationY(-(RippleCheckBoxUtil.dp2px(context, 12) + contentTv.getHeight() / 2))
                                .start();
                        contentTv.animate()
                                .setDuration(400)
                                .alpha(0.3F)
                                .start();
                    } else {
                        line.animate()
                                .setDuration(400)
                                .translationY(0)
                                .start();
                        contentTv.animate()
                                .setDuration(400)
                                .alpha(1F)
                                .start();
                    }
                }
            });
            switch (beanList.get(i).getPriority()) {
                case Constants.URGENT_IMPROTANT://紧急重要
                    priorityImg.setColorFilter(context.getResources().getColor(R.color.red_800));
                    break;
                case Constants.IMPROTANT_NOT_URGENT://重要不紧急
                    priorityImg.setColorFilter(context.getResources().getColor(R.color.orange_800));
                    break;
                case Constants.URGETN_NOT_IMPORTANT://紧急不重要
                    priorityImg.setColorFilter(context.getResources().getColor(R.color.yellow_800));
                    break;
                case Constants.NOT_URGENT_NOT_IMPORTANT://不重要不紧急
                    priorityImg.setColorFilter(context.getResources().getColor(R.color.grey_500));
                    break;
                default:
                    break;
            }
            holder.linearLayout.addView(view);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addList(List<TodoListBean> listBeans) {
        this.mList.clear();
        this.mList = listBeans;
        notifyDataSetChanged();
    }

}
