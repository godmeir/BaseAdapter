package cn.itest.baseadapter;

/**
 * Created by Administrator on 2016/6/30.
 */
public class FoodBean {
    private String name;
    private int imgId;


    public FoodBean(String name, int imgId) {
        this.name = name;
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
