package cn.itest.baseadapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/6/30.
 */
public class FoodInfoGrid extends Activity {
    private GridView mGridView,m2GridView, m3GridView;
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

    private FoodBean s1 = new FoodBean("黄鳝",R.drawable.fun_light_off);
    private FoodBean s2 = new FoodBean("黄鳝",R.drawable.fun_light_off);
    private FoodBean s3 = new FoodBean("黄鳝",R.drawable.fun_light_off);
    private FoodBean s4 = new FoodBean("黄鳝",R.drawable.fun_light_off);

    private List<FoodBean> mDatas1 = new ArrayList<FoodBean>(Arrays.asList(fb1,fb2, fb3,
            fb4, fb5, fb6, fb7, fb8, fb9, fb10, fb11, fb5, fb6, fb7, fb8, fb9,
            fb10, fb11, fb5, fb6, fb7, fb8, fb9, fb10, fb11));
    private List<FoodBean> mDatas2 = new ArrayList<FoodBean>(Arrays.asList(s1,s2,s3,s4));
    private List<FoodBean> mDatas;
    private CommonAdapter mAdapter;
    private boolean flag = false;
    private Button bn, bn2;
    private boolean isShowDelete = false;//根据这个变量来判断是否显示删除图标，true是显示，false是不显示

    private StatusBar statusBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodinfo_layout);

        mGridView = (GridView)findViewById(R.id.storge_grid);
        m2GridView = (GridView)findViewById(R.id.freeze_grid);
        m3GridView = (GridView)findViewById(R.id.storge_grid2);
        bn = (Button)findViewById(R.id.open_bn);
        bn2 = (Button)findViewById(R.id.open_bn2);

        statusBar = (StatusBar) findViewById(R.id.status_bar);
        statusBar.setAdapter(new CommonAdapter<FoodBean>(getApplicationContext(), mDatas2, R.layout.list_status_bar) {
            @Override
            public void convert(ViewHolder viewHolder, FoodBean item) {
                viewHolder.setText(R.id.txt_status, item.getName());
//                viewHolder.setImageResource(R.id.img_status, item.getImgId());

                notifyDataSetChanged();
            }
        });

        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag){
                    flag = false;
                    bn.setText("CLOSE");

                    mGridView.setVisibility(View.VISIBLE);
                    m3GridView.setVisibility(View.GONE);
                }else {
                    flag = true;
                    bn.setText("OPEN");

                    mGridView.setVisibility(View.GONE);
                    m3GridView.setVisibility(View.VISIBLE);
                }
            }
        });


        mGridView.setAdapter( new CommonAdapter<FoodBean>(getApplicationContext(), mDatas1, R.layout.item_grid) {
            @Override
            public void convert(ViewHolder viewHolder, FoodBean item) {
                viewHolder.setText(R.id.name_tv, item.getName());
                viewHolder.setImageResource(R.id.food_img, item.getImgId());

                viewHolder.getView(R.id.delete_imgs).setVisibility(isShowDelete ? View.VISIBLE : View.GONE);
                notifyDataSetChanged();
            }
        });
        mAdapter = new CommonAdapter<FoodBean>(getApplicationContext(), mDatas2, R.layout.item_grid) {
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
                       mDatas2.remove(viewHolder.getPosotion());
                        notifyDataSetChanged();
                    }
                });

            }
        };



        m3GridView.setAdapter(mAdapter);

        m3GridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (isShowDelete) {
                    isShowDelete = false;
                } else {
                    isShowDelete = true;
                }
                mGridView.setVisibility(View.VISIBLE);
                m3GridView.setVisibility(View.GONE);
                Toast.makeText(FoodInfoGrid.this,"长点击事件",Toast.LENGTH_LONG).show();
//                mGridAdapter.setIsShowDelete(isShowDelete);
                mAdapter.notifyDataSetChanged();
                return true;
            }
        });
        m3GridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });




        m2GridView.setAdapter(new CommonAdapter<FoodBean>(getApplicationContext(), mDatas1, R.layout.item_grid) {
            @Override
            public void convert(ViewHolder viewHolder, FoodBean item) {
                viewHolder.setText(R.id.name_tv, item.getName());
                viewHolder.setImageResource(R.id.food_img, item.getImgId());
            }
        });

        m2GridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (isShowDelete) {
                    isShowDelete = false;
                } else {
                    isShowDelete = true;
                }

                Toast.makeText(FoodInfoGrid.this,"长点击事件",Toast.LENGTH_LONG).show();
//                mGridAdapter.setIsShowDelete(isShowDelete);
                mAdapter.notifyDataSetChanged();


                return true;
            }
        });
    }

    /**
     * 按返回键取消抖动动画，隐藏删除图片
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if(isShowDelete){
                isShowDelete = false;
                mAdapter.notifyDataSetChanged();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
