package cn.itest.baseadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private List<String> mDatas = new ArrayList<String>(Arrays.asList("hello",
            "world", "welcome"));
    private CommonAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.lv_main);
//        mListView.setAdapter(new MyCommonAdapter<String>(this, mDatas));
        //设置适配器,此时不用再去写自己的adapter，可以直接使用内部类实现，但是我的item里面可能有很多的东西，这样改怎么写呢
        mListView.setAdapter(mAdapter = new CommonAdapter<String>(getApplicationContext(),
                mDatas, R.layout.lv_item) {
            @Override
            public void convert(ViewHolder viewHolder, String item) {
//                TextView tv = viewHolder.getView(R.id.tv_item);
//                tv.setText(item);

                viewHolder.setText(R.id.tv_item, item);
            }
        });
    }
}
