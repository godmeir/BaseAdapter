//package cn.itest.baseadapter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.gree.bean.AirConditioner;
//import com.gree.contact.Contact;
//import com.gree.g_life.c.R;
//
//import android.content.Context;
//import android.graphics.Typeface;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//public class StatusAdapter extends BaseAdapter {
//
//	private LayoutInflater mInflater = null;
//	private List<String> listStatus;
//	private List<ViewHolder> listView = null;
//	private Context context = null;
//
//	public StatusAdapter(Context context, List<String> acStatus) {
//
//		this.context = context;
//		this.mInflater = LayoutInflater.from(context);
//		listView = new ArrayList<ViewHolder>();
//		this.listStatus = acStatus;
//	}
//
//
//
//	@Override
//	public int getCount() {
//		// TODO Auto-generated method stub
//		return listStatus == null ? 0 : listStatus.size();
//	}
//
//	@Override
//	public Object getItem(int arg0) {
//		// TODO Auto-generated method stub
//		return arg0;
//	}
//
//	@Override
//	public long getItemId(int position) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		// TODO Auto-generated method stub
//		ViewHolder holder;
//		if (null == convertView) {
//			synchronized(StatusAdapter.this) {
//				convertView = mInflater.inflate(R.layout.list_status_bar, null);
//				holder = new ViewHolder();
//				holder.txtStatus = (TextView) convertView.findViewById(R.id.txt_status);
//				//20160513
//				setTextReguFontImWay(holder.txtStatus);
//
//				holder.imgStatus = (ImageView) convertView.findViewById(R.id.img_status);
//				convertView.setTag(holder);
//				listView.add(holder);
//			}
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}
//		holder.txtStatus.setText(listStatus.get(position).getStrName());
//		holder.imgStatus.setImageResource(listStatus.get(position).getnImgView());
//		return convertView;
//	}
//
//	public class ViewHolder {
//
//		public TextView txtStatus = null;
//		public ImageView imgStatus = null;
//	}
//
//	private void setTextReguFontImWay(TextView txt) {
//		Typeface face = Typeface.createFromAsset(context.getAssets(),
//				"fonts/FuturaStd-Medium.otf");
//		txt.setTypeface(face);
//	}
//
//}
