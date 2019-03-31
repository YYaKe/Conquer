package app.hanks.com.conquer.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
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
import app.hanks.com.conquer.base.OnItemClickListener;
import app.hanks.com.conquer.bean.FinishBean;
import app.hanks.com.conquer.bean.ListType;
import app.hanks.com.conquer.bean.ToDoTypeEnum;
import app.hanks.com.conquer.bean.TodoBean;
import app.hanks.com.conquer.bean.TodoListBean;
import app.hanks.com.conquer.config.Constants;
import app.hanks.com.conquer.util.L;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * author：wiki on 2019/3/10
 * email：zhengweiqunemail@qq.com
 */
public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> implements View.OnClickListener {

    private OnItemClickListener onItemClickListener;
    private List<TodoListBean> mList;
    private Context context;

    public ToDoAdapter() {
        mList = new ArrayList<>();
    }

    static class ViewHolder extends BaseViewHolder {

        TextView titleTv;
        View lineView;
        LinearLayout toDoLayout;

        public ViewHolder(View view) {
            super(view);
            titleTv = (TextView) view.findViewById(R.id.tvTitle);
            lineView = view.findViewById(R.id.line);
            toDoLayout = (LinearLayout) view.findViewById(R.id.linear_layout);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (mList == null)
            return;
        holder.titleTv.setText(ListType.getName(mList.get(position).getType()));
        holder.lineView.setBackgroundColor(context.getResources().getColor(mList.get(position).getType()
                == Constants.TO_DO ? R.color.purple_500 : R.color.grey_800));
        holder.toDoLayout.removeAllViews();

        final List<TodoBean> beanList = mList.get(position).getList();
        final List<FinishBean> finishBeans = mList.get(position).getFinishBeans();
        //待做列表
        if (beanList != null) {
            for (int i = 0; i < beanList.size(); i++) {
                final View view = LayoutInflater.from(context).inflate(R.layout.item_todo, null);
                final TextView contentTv = (TextView) view.findViewById(R.id.text);
                final View line = view.findViewById(R.id.line);
                final TextView typeTv = (TextView) view.findViewById(R.id.type);
                ConstraintLayout item = (ConstraintLayout) view.findViewById(R.id.item_id);
                item.setTag(R.id.position, position);
                item.setTag(R.id.object_id, beanList.get(i).getObjectId());
                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(view, (Integer) view.getTag(R.id.position));
                        }
                    }
                });
                ImageView priorityImg = (ImageView) view.findViewById(R.id.imgPriority);
                RippleCheckBox checkBox = (RippleCheckBox) view.findViewById(R.id.rippleCheckBox);
                contentTv.setText(beanList.get(i).getContent());
                typeTv.setText(ToDoTypeEnum.getName(beanList.get(i).getType()));
                checkBox.setTag(R.id.tv_tag, i);
                checkBox.setClickable(mList.get(position).getType() == Constants.TO_DO);
                final TodoBean todoBean = beanList.get(i);
                checkBox.setOnCheckedChangeListener(new RippleCheckBox.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RippleCheckBox checkBox, boolean isChecked) {
                        if (isChecked) {
                            final int index = (Integer) checkBox.getTag(R.id.tv_tag);
                            final FinishBean bean = new FinishBean(todoBean.getCompleteDate(), todoBean.getCompleteDateStr(),
                                    todoBean.getContent(), todoBean.getDate(), todoBean.getDateStr(), todoBean.getId(),
                                    todoBean.getStatus(), todoBean.getTitle(), todoBean.getType(), todoBean.getUserId(),
                                    todoBean.getPriority());

                            TodoBean deleteToDoBean = new TodoBean();
                            deleteToDoBean.setObjectId(todoBean.getObjectId());
                            deleteToDoBean.delete(context, todoBean.getObjectId(), new DeleteListener() {
                                @Override
                                public void onSuccess() {
                                    L.i("delete todo bean success");
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    L.e("delete todo bean failed");
                                }
                            });
                            bean.save(context, new SaveListener() {
                                @Override
                                public void onSuccess() {
                                    L.i("add success");
                                    beanList.remove(index);
                                    findFinishList();
                                    holder.toDoLayout.removeView(view);
                                    notifyDataSetChanged();
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    L.e("add failed");
                                }
                            });
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
                holder.toDoLayout.addView(view);
            }
        }
        //完成列表
        if (finishBeans != null) {
            for (int i = 0; i < finishBeans.size(); i++) {
                L.i("finishBeans-size:" + finishBeans.size());
                final View view = LayoutInflater.from(context).inflate(R.layout.item_todo, null);
                final TextView contentTv = (TextView) view.findViewById(R.id.text);
                final View line = view.findViewById(R.id.line);
                final TextView typeTv = (TextView) view.findViewById(R.id.type);
                ImageView priorityImg = (ImageView) view.findViewById(R.id.imgPriority);
                RippleCheckBox checkBox = (RippleCheckBox) view.findViewById(R.id.rippleCheckBox);
                contentTv.setText(finishBeans.get(i).getContent());
                typeTv.setText(ToDoTypeEnum.getName(finishBeans.get(i).getType()));
                checkBox.setChecked(true);
                checkBox.setClickable(false);
                //动画效果，横线上移，文字alpha为0.3
                line.animate()
                        .setDuration(400)
                        .translationY(-(RippleCheckBoxUtil.dp2px(context, 12) + contentTv.getHeight() / 2))
                        .start();
                contentTv.animate()
                        .setDuration(400)
                        .alpha(0.3F)
                        .start();
                typeTv.animate()
                        .setDuration(400)
                        .alpha(0.3F)
                        .start();

                switch (finishBeans.get(i).getPriority()) {
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
                holder.toDoLayout.addView(view);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }

    public void addList(TodoListBean listBeans) {
        this.mList.add(listBeans);
        notifyDataSetChanged();
    }

    public void clear() {
        this.mList.clear();
    }

    private void findFinishList() {
        BmobQuery<FinishBean> query = new BmobQuery<>();
        //按照时间降序
        query.order("-createdAt");
        //执行查询，第一个参数为上下文，第二个参数为查找的回调
        //执行查询，第一个参数为上下文，第二个参数为查找的回调
        query.findObjects(context, new FindListener<FinishBean>() {

            @Override
            public void onSuccess(List<FinishBean> finishBeans) {
                L.i("findFinishList onSuccess");
                TodoListBean listBean = new TodoListBean(Constants.FINISH, null, finishBeans);
                mList.remove(1);
                addList(listBean);
            }

            @Override
            public void onError(int code, String arg0) {
                L.i("findFinishList onError");
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
