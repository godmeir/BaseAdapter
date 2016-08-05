package cn.itest.baseadapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/6/29.
 */
public class ViewHolder {
    private final SparseArray<View> mViews;
    private View mConvertView;
    private int position;
    private ViewHolder(Context context, ViewGroup parent, int layoutId,
        int position){
        this.position = position;
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId,parent, false);
        //setTag()
        mConvertView.setTag(this);
    }

    /**
     * 拿到一个viewHolder的对象
     * @param context
     * @param convertVeiw
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static ViewHolder get(Context context, View convertVeiw,
                                 ViewGroup parent, int layoutId, int position){
        if (convertVeiw == null){
            return new ViewHolder(context, parent, layoutId, position);
        }else {
            return (ViewHolder) convertVeiw.getTag();
        }
    }

    /**
     * 通过空间的Id获取对应的控件，如果没有则加入到views(SparseArray中)
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);
        if (view == null){
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView(){
        return mConvertView;
    }

    /**
     * 为TextView设置字符串
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, String text){
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 为ImageView设置图片
     * @param viewId
     * @param drawableId
     * @return
     */
    public ViewHolder setImageResource(int viewId, int drawableId){
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;
    }

    public ViewHolder setImageBitmap(int viewId, Bitmap bm){
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

   public int getPosotion(){
       return position;
   }

    public void setClickShake(Context mContext, boolean isShowDelete){

        if(isShowDelete){	//判断是否显示删除图标

            Animation shake = AnimationUtils.loadAnimation(
                    mContext, R.anim.anim);
            shake.reset();
            shake.setFillAfter(true);
            getConvertView().startAnimation(shake);
        }else{

            getConvertView().clearAnimation();
        }
    }


//    public ViewHolder setImageByUrl(int viewId, String url){
//        ImageLoader.getInstance(3, Type.LIFO).loadImage(url,
//                (ImageView) getView(viewId));
//        return this;
//    }

//    public int getPosition()
//    {
//        return mPosition;
//    }


}
