package app.hanks.com.conquer.bean;

/**
 * author：wiki on 2019/3/14
 * email：zhengweiqunemail@qq.com
 */
public enum PriorityEnum {

    URGENT_IMPROTANT("既紧急又重要", 1),
    IMPROTANT_NOT_URGENT("重要但不紧急", 2),
    URGETN_NOT_IMPORTANT("紧急但不重要", 3),
    NOT_URGENT_NOT_IMPORTANT("不紧急不重要", 4);

    private String priority;
    private int flag;

    PriorityEnum(String priority, int flag) {
        this.priority = priority;
        this.flag = flag;
    }

    public static String getName(int index) {
        for (PriorityEnum priorityEnum : PriorityEnum.values()) {
            if (priorityEnum.getFlag() == index)
                return priorityEnum.priority;
        }
        return null;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
