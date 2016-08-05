/*
package cn.itest.baseadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

*/
/**
 * Created by Administrator on 2016/6/29.
 *//*

public class MyCommonAdapter<T> extends CommonAdapter<T> {

    public MyCommonAdapter(Context context, List<T> mDatas, int itemLayoutId){
        super(context, mDatas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //实例化ViewHolder对象
        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent,
                R.layout.lv_item, position);
        //通过getView（）获取对象
        TextView tv = viewHolder.getView(R.id.tv_item);
        tv.setText((String) mDatas.get(position));
        return viewHolder.getConvertView();
    }
}
*/
