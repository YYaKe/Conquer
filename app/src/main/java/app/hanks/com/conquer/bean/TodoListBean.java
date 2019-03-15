package app.hanks.com.conquer.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * author：wiki on 2019/3/10
 * email：zhengweiqunemail@qq.com
 */
public class TodoListBean extends BmobObject {

    private int type;
    private List<TodoBean> list;
    private List<FinishBean> finishBeans;

    public TodoListBean(int type, List<TodoBean> list, List<FinishBean> finishBeans) {
        this.type = type;
        this.list = list;
        this.finishBeans = finishBeans;
    }

    public TodoListBean(int type, List<TodoBean> list) {
        this.type = type;
        this.list = list;
    }

    public TodoListBean(String tableName, int type, List<TodoBean> list) {
        super(tableName);
        this.type = type;
        this.list = list;
    }

    public List<TodoBean> getList() {
        return list;
    }

    public void setList(List<TodoBean> list) {
        this.list = list;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<FinishBean> getFinishBeans() {
        return finishBeans;
    }

    public void setFinishBeans(List<FinishBean> finishBeans) {
        this.finishBeans = finishBeans;
    }
}
