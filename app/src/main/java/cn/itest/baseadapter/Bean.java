package cn.itest.baseadapter;

/**
 * Created by Administrator on 2016/6/29.
 */
public class Bean {
    private String title;
    private String desc;
    private String phone;
    private String time;
    private int imgId;

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public int getImgId() {

        return imgId;
    }

    public Bean(String title, String desc, String phone, String time,int imgId) {
        this.title = title;
        this.desc = desc;
        this.phone = phone;
        this.time = time;
        this.imgId = imgId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {

        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getPhone() {
        return phone;
    }

    public String getTime() {
        return time;
    }
}
