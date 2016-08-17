package cn.itest.baseadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/6/29.
 * 利用泛型，打造通用的Adapter,只需继承并重写getView（）方法就可以
 */
public abstract  class CommonAdapter<T> extends BaseAdapter {
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mDatas;
    protected final int mItemLayoutId;
    //原来的通用版
//
    public CommonAdapter(Context context, List<T> mDatas, int itemLayoutId){
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mDatas = mDatas;
        this.mItemLayoutId = itemLayoutId;
    }

//    public CommonAdapter(Context context, List<T> mDatas){
//        mInflater = LayoutInflater.from(context);
//        this.mContext = context;
//        this.mDatas = mDatas;
//    }


    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    //以下为最新一版的代码


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //实例化ViewHolder对象
        final ViewHolder viewHolder = getViewHolder(position, convertView, parent);
        convert(viewHolder, getItem(position));
        return viewHolder.getConvertView();
    }
    public abstract void convert(ViewHolder viewHolder, T item);



    private ViewHolder getViewHolder(int position, View convertView, ViewGroup parent){
        return ViewHolder.get(mContext, convertView, parent, mItemLayoutId, position);
    }
}
