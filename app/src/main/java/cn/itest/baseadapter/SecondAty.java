package cn.itest.baseadapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/6/29.
 */
public class SecondAty extends Activity {
    private ListView mListView;
    private Bean bean1 = new Bean("美女一捆","周围都是的马仔，在食堂三楼","10086","周一晚上",R.drawable.loading_5);
    private Bean bean2 = new Bean("美女一捆","周围都是的马仔，在食堂三楼","10086","周一晚上",R.drawable.loading_6);
    private Bean bean3 = new Bean("美女一捆","周围都是的马仔，在食堂三楼","10086","周一晚上",R.drawable.loading_7);
    private Bean bean4 = new Bean("美女一捆","周围都是的马仔，在食堂三楼","10086","周一晚上",R.drawable.loading_8);

    private List<Bean> mDatas = new ArrayList<Bean>(Arrays.asList(bean1,bean2,
            bean3, bean4));
    private CommonAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.lv_main);

        mListView.setAdapter(new CommonAdapter<Bean>(getApplicationContext(),mDatas,R.layout.list_item) {
            @Override
            public void convert(ViewHolder viewHolder, Bean item) {
                viewHolder.setText(R.id.tv_title,item.getTitle());
                viewHolder.setText(R.id.tv_describe,item.getDesc());
                viewHolder.setText(R.id.tv_phone,item.getPhone());
                viewHolder.setText(R.id.tv_time,item.getTime());
                viewHolder.setImageResource(R.id.imgs,item.getImgId());
            }
        });

    }
}
