package cn.itest.baseadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2016/6/29.
 */
public class MyAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<String> mDatas;


    public MyAdapter(Context context, List<String> mDatas){
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //实例化viewHolder
        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent,
                R.layout.lv_item, position);
        //通过getVeiw()获取控件
        TextView tv = viewHolder.getView(R.id.tv_item);
        tv.setText(mDatas.get(position));
        return viewHolder.getConvertView();
    }

    /**
     * 第一版
     */
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder viewHolder = null;
//        if (convertView == null){
//            convertView = mInflater.inflate(R.layout.lv_item, parent, false);
//            viewHolder = new ViewHolder();
//            viewHolder.mTextView = (TextView) convertView.findViewById(R.id.tv_item);
//            convertView.setTag(viewHolder);
//        }else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//        viewHolder.mTextView.setText(mDatas.get(position));
//        return convertView;
//    }
//    private final class ViewHolder{
//        TextView mTextView;
//    }
}
