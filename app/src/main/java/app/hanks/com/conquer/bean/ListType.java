package app.hanks.com.conquer.bean;

/**
 * 列表清单
 * author：wiki on 2019/3/10
 * email：zhengweiqunemail@qq.com
 */
public enum ListType {

    TODO("待做", 1),
    DONE("完成", 0);

    private String type;
    private int flag;

    ListType(String type, int flag) {
        this.type = type;
        this.flag = flag;
    }

    public static String getName(int index) {
        for (ListType listType : ListType.values()) {
            if (listType.getFlag() == index) {
                return listType.type;
            }
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
