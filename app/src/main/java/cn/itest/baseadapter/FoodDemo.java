package cn.itest.baseadapter;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/7/1.
 */
public class FoodDemo extends Activity {
    private GridView m1GridView,m1GridViewOpen, m2GridView;
    private FoodBean fb1 = new FoodBean("土豆",R.drawable.loading_8);
    private FoodBean fb2 = new FoodBean("贝壳",R.drawable.loading_7);
    private FoodBean fb3 = new FoodBean("海螺",R.drawable.loading_6);
    private FoodBean fb4 = new FoodBean("白菜",R.drawable.loading_7);
    private FoodBean fb5 = new FoodBean("龙虾",R.drawable.loading_5);
    private FoodBean fb6 = new FoodBean("螃蟹",R.drawable.loading_6);
    private FoodBean fb7 = new FoodBean("黄鳝",R.drawable.loading_8);
    private FoodBean fb8 = new FoodBean("白菜",R.drawable.loading_7);
    private FoodBean fb9 = new FoodBean("龙虾",R.drawable.loading_5);
    private FoodBean fb10 = new FoodBean("螃蟹",R.drawable.loading_6);
    private FoodBean fb11 = new FoodBean("黄鳝",R.drawable.loading_8);

    private List<FoodBean> mDatas1 = new ArrayList<FoodBean>(Arrays.asList(fb1,fb2, fb3,
            fb4, fb5, fb6, fb7, fb8, fb9, fb10, fb11));
    private List<FoodBean> mDatas2 = new ArrayList<FoodBean>(Arrays.asList(fb1,fb2, fb3,
            fb4, fb5));
    private List<FoodBean> mDatas = mDatas2;
    private CommonAdapter mAdapter;
    private boolean flag = false;
    private Button bn, bn2;
    private boolean isShowDelete = true;//根据这个变量来判断是否显示删除图标，true是显示，false是不显示

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodinfo_layout);
        m1GridView = (GridView)findViewById(R.id.storge_grid);
        m1GridViewOpen = (GridView)findViewById(R.id.storge_grid2);

        bn = (Button)findViewById(R.id.open_bn);
        bn2 = (Button)findViewById(R.id.open_bn2);

        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag){
                    flag = false;
                    bn.setText("CLOSE");
                    mDatas = mDatas1;

                }else {
                    flag = true;
                    bn.setText("OPEN");

                    mDatas = mDatas2;
                }
                mAdapter.notifyDataSetChanged();
            }
        });


        mAdapter = new CommonAdapter<FoodBean>(getApplicationContext(), mDatas, R.layout.item_grid) {
            @Override
            public void convert(final ViewHolder viewHolder, FoodBean item) {
                viewHolder.setText(R.id.name_tv, item.getName());
                viewHolder.setImageResource(R.id.food_img, item.getImgId());

                viewHolder.getView(R.id.delete_imgs).setVisibility(isShowDelete ? View.VISIBLE : View.GONE);

                viewHolder.setClickShake(getApplicationContext(),isShowDelete);

                viewHolder.getView(R.id.delete_imgs).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //需要添加一个根据view获取当前的position的方法
                        mDatas.remove(viewHolder.getPosotion());
//                        mDatas = mDatas1;
                        notifyDataSetChanged();
                    }
                });

                notifyDataSetChanged();
            }
        };
        m1GridView.setAdapter(mAdapter);
        m1GridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (isShowDelete) {
                    isShowDelete = false;
//                    mDatas = mDatas1;
//                    mAdapter.notifyDataSetChanged();
                } else {
                    isShowDelete = true;
//                    mDatas = mDatas2;
//                    mAdapter.notifyDataSetChanged();
                }
//                m1GridViewOpen.setVisibility(View.VISIBLE);
//                m1GridViewOpen.setVisibility(View.GONE);
                Toast.makeText(FoodDemo.this,"长点击事件",Toast.LENGTH_LONG).show();
                mDatas = mDatas1;
//                mDatas.remove(position);
//                mAdapter.notifyDataSetInvalidated();
                m1GridView.invalidate();

                return true;
            }

        });



    }
}
