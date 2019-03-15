package app.hanks.com.conquer.bean;

import com.google.gson.annotations.SerializedName;

import cn.bmob.v3.BmobObject;

/**
 * author：wiki on 2019/3/10
 * email：zhengweiqunemail@qq.com
 */
public class FinishBean extends BmobObject {
    /**
     * completeDate : null
     * completeDateStr :
     * content :
     * date : 1538841600000
     * dateStr : 2018-10-07
     * id : 2852
     * status : 0
     * title : 第二条清单
     * type : 0
     * userId : 5603
     */

    @SerializedName("completeDate")
    private long completeDate;
    @SerializedName("completeDateStr")
    private String completeDateStr;
    @SerializedName("content")
    private String content;
    @SerializedName("date")
    private long date;
    @SerializedName("dateStr")
    private String dateStr;
    @SerializedName("id")
    private int id;
    @SerializedName("status")
    private int status;
    @SerializedName("title")
    private String title;
    @SerializedName("type")
    private int type;
    @SerializedName("userId")
    private String userId;
    @SerializedName("priority")
    private int priority;

    public FinishBean(long completeDate, String completeDateStr, String content, long date, String dateStr, int id, int status, String title, int type, String userId, int priority) {
        this.completeDate = completeDate;
        this.completeDateStr = completeDateStr;
        this.content = content;
        this.date = date;
        this.dateStr = dateStr;
        this.id = id;
        this.status = status;
        this.title = title;
        this.type = type;
        this.userId = userId;
        this.priority = priority;
    }

    public FinishBean(FinishBean bean){
        this.completeDate = bean.getCompleteDate();
        this.completeDateStr = bean.completeDateStr;
        this.content = bean.content;
        this.date = bean.date;
        this.dateStr = bean.dateStr;
        this.id = bean.id;
        this.status = bean.status;
        this.title = bean.title;
        this.type = bean.type;
        this.userId = bean.userId;
        this.priority = bean.priority;
    }


    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public long getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(long completeDate) {
        this.completeDate = completeDate;
    }

    public String getCompleteDateStr() {
        return completeDateStr;
    }

    public void setCompleteDateStr(String completeDateStr) {
        this.completeDateStr = completeDateStr;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}