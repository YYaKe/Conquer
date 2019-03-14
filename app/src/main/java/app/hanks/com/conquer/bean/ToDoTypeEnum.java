package app.hanks.com.conquer.bean;

/**
 * author：wiki on 2019/3/14
 * email：zhengweiqunemail@qq.com
 */
public enum ToDoTypeEnum {

    USER_ONE("只用这一个", 1),
    WORK("工作", 2),
    LEARN("学习", 3),
    LIFE("生活", 4);

    private String type;
    private int flag;

    ToDoTypeEnum(String type, int flag) {
        this.type = type;
        this.flag = flag;
    }

    public static String getName(int index) {
        for (ToDoTypeEnum toDoTypeEnum : ToDoTypeEnum.values()) {
            if (toDoTypeEnum.getFlag() == index)
                return toDoTypeEnum.type;
        }
        return null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
