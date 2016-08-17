package cn.itest.baseadapter;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.view.View.OnClickListener;

/**
 * 基本算法： 创建一张只能显示ITEM_COUNT的高度的画布，启动线程不断重画，实现文字滚动效果
 * 滑动就采用控制画布的高度，向上的滑动距离不足一半，则向上返回，反之亦然 点击时间短的就相当于是点击马上展开
 * 
 * @author 330868
 * 
 */
public class StatusBar extends ListView implements OnClickListener {

	private static final int ITEM_COUNT = 2; // 自动滚动时，每次可显示的的数目
	private static final int SCROLL_RATE = 40; // 滚动的速率
	private static final int CANVAS_MODIFY = 40;// 画布修正值
	private int nSourceViewItem = 100; // 控件的ITEM高度默认为100
	private Paint mPaint = null;
	private Canvas mCanvas = null;
	private Context context = null;

	private Thread scrollThread = null; // 自动滚动线程
	private Bitmap[] bmpChildren = null; // ListVie的Item的图像
	private int nChildCount = 0; // Item的数目
	private int nItemHeight = 0; // Item的高度
	private int nItemWidth = 0; // Item的宽度
	private int nItemMin = 0; // Item的最小距离
	private int nMoveDistance = 0; // Item移动的距离
	private int nChild = 0; // 滚动到第几个Item
	private int nGuestDistance = 0; // 手势滑动的距离
	private int nDistance = 0; // 画布的长
	private boolean bStart = true; // 开始自动滚动
	private boolean bScroll = true; // 允许滚动标志
	private boolean bGuestScroll = false; // 手动滑动标志
	private long lOnClickTime = 0; // 点击控件的时刻
	private int nScreenWidth = 0; // 屏幕宽度

	// private int nScreenHeight = 0; // 屏幕高度

	public StatusBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		// nScreenHeight =
		// context.getResources().getDisplayMetrics().heightPixels;
		nScreenWidth = context.getResources().getDisplayMetrics().widthPixels;
		// 按照屏幕分辨率重新设置Item的高度
		nSourceViewItem = nSourceViewItem * (nScreenWidth / 480);
		if (nScreenWidth < 580) {

			// nItemHeight = 30;
		} else if (nScreenWidth < 800) {

		} else if (nScreenWidth < 1200) {

		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		setTranslationY(0);
		mCanvas = canvas;
		// 获取Item的数目
		// nChildCount = getChildCount();
		// Item的数目为零，则不做任何处理
		if (0 == nChildCount) {
			return;
		}
		bmpChildren = new Bitmap[nChildCount];

		for (int i = 0; i < nChildCount; i++) {
			View child = getChildAt(i);
			// child.setOnClickListener(this);
			// 获取Item的View的位图
			child.setDrawingCacheEnabled(true);
			bmpChildren[i] = child.getDrawingCache(true);
		}
		if (null == mPaint) {
			mPaint = new Paint();
			mPaint.setAntiAlias(true);
			mPaint.setFilterBitmap(true);
		}
		// 得到Item的高和宽
		nItemHeight = bmpChildren[0].getHeight();
		// 获取Item最宽的
		for (int i = 0; i < bmpChildren.length; i++) {
			int temp = bmpChildren[i].getWidth();
			if (nItemWidth < temp) {
				nItemWidth = temp;
			}
			if (0 == i) {
				nItemMin = temp;
			}
			if (nItemMin > temp) {
				nItemMin = temp;
			}

		}
		// 获取不到高度就给固定的值
		if (0 == nItemHeight) {
			nItemHeight = 72;
			nItemWidth = 150;
		}

		// 不能少于ITEM_COUNT的宽度
		nDistance = nItemHeight * ITEM_COUNT + nGuestDistance;
		if (nDistance < nItemHeight * ITEM_COUNT) {
			nDistance = nItemHeight * ITEM_COUNT;
		}
		if (nDistance > nItemHeight * nChildCount) {
			nDistance = nItemHeight * nChildCount;
		}
		// 画布的大小
		mCanvas.clipRect(0, 0, nItemWidth, nSourceViewItem * nChildCount);
		// !bGuestScroll && bScroll： 自动滚动时的箭头
		// !bGuestScroll && !bScroll： 展开状态栏的箭头
		if (nChildCount > ITEM_COUNT) {
			if (!bGuestScroll && bScroll) {
				setTranslationY(-(nItemHeight * (-2) + nSourceViewItem
						* nChildCount - CANVAS_MODIFY));
				mCanvas.drawBitmap(drawArrow(), caculateArrow(),
						nSourceViewItem * nChildCount - CANVAS_MODIFY, mPaint);
			} else if (!bGuestScroll && !bScroll) {
				if (!bGuestScroll && !bScroll) {
					mCanvas.drawBitmap(drawArrow(), caculateArrow(),
							nItemHeight * nChildCount, mPaint);
				}
			}
		}
	}

	/**
	 * 画箭头
	 * 
	 * @return
	 */
	private Bitmap drawArrow() {

		Bitmap bmpTemp = getBmp(R.drawable.scroll_arrow);
		float nWidth = bmpTemp.getWidth();
		float nHeight = bmpTemp.getHeight();

		Matrix matrix = new Matrix();
		float nScaleWidth = 0.0f;
		float nScaleHeight = 0.0f;
		// if(nScreenWidth > 800) {
		nScaleWidth = ((float) nScreenWidth) / 800.0f;
		nScaleHeight = ((float) nScreenWidth) / 800.0f;
		// }
		// Bitmap bmpReference = getBmp(R.drawable.status_silent);
		// nScaleWidth = bmpReference.getWidth() / nWidth;
		// nScaleHeight = bmpReference.getHeight() / (2.0f * nHeight);
		// if(!bmpReference.isRecycled()) {
		// bmpReference.recycle();
		// bmpReference = null;
		// }
		matrix.postScale(nScaleWidth, nScaleHeight);
		Bitmap bmpArrow = Bitmap.createBitmap(bmpTemp, 0, 0, (int) nWidth,
				(int) nHeight, matrix, true);
		if (!bmpTemp.isRecycled()) {
			bmpTemp.recycle();
			bmpTemp = null;
		}
		return bmpArrow;
	}

	/**
	 * 计算箭头的位置
	 * 
	 * @return
	 */
	private int caculateArrow() {

		int nCaculate = 14;
		// if(nScreenWidth > 800) {
		// nCaculate = (nCaculate + 4) * nScreenWidth / 800;
		// }
		nCaculate = nItemMin / 2;
		return nCaculate;
	}

	/**
	 * 状态滚动
	 * 
	 * @param canvas
	 * @param nMoveDistance
	 *            滚动的距离
	 */
	public void scrollItem(Canvas canvas, int nMoveDistance) {

		// 源Item不在第一栏时，重新排序
		if (bStart && bScroll && nChild != 0) {
			for (int j = 0; j < nChild; j++) {
				for (int i = 0; i < bmpChildren.length - 1; i++) {
					Bitmap bmp = bmpChildren[i];
					bmpChildren[i] = bmpChildren[i + 1];
					bmpChildren[i + 1] = bmp;
				}
			}
		}
		// 重新在画布上画上Item
		if (bScroll && !bGuestScroll && nChildCount > ITEM_COUNT) {
			for (int i = 0; i < bmpChildren.length; i++) {
				canvas.drawBitmap(bmpChildren[i], 0, nItemHeight * (i - 2)
						+ nMoveDistance + nSourceViewItem * nChildCount
						- CANVAS_MODIFY, mPaint);
			}
		} else {
			for (int i = 0; i < bmpChildren.length; i++) {
				canvas.drawBitmap(bmpChildren[i], 0, nItemHeight * i, mPaint);
			}
		}

		// 刷新
		invalidate();
	}

	private Bitmap getBmp(int nDrawable) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		Bitmap bmpScroll = BitmapFactory.decodeStream(context.getResources()
				.openRawResource(nDrawable), null, opts);
		return bmpScroll;
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {

		if (0 == nChildCount) {
			return;
		}

		if (bStart && bScroll) {
			mCanvas.clipRect(0, 0, nItemWidth, nSourceViewItem * nChildCount
					- CANVAS_MODIFY);

		} else {
			// 手动滑动时的箭头
			if (bGuestScroll) {
				mCanvas.drawBitmap(drawArrow(), caculateArrow(),
						nDistance + 15, mPaint);
			}

			mCanvas.clipRect(0, 0, nItemWidth, nDistance);
		}
		// 画Item
		scrollItem(mCanvas, nMoveDistance);
		// 滚动Item
		if (!bGuestScroll && bScroll && null == scrollThread
				&& nChildCount > ITEM_COUNT) {
			scrollThread = new Thread(scrollRunnable);
			scrollThread.start();
		}
		if (nChildCount <= ITEM_COUNT && null != scrollThread) {
			try {
				scrollThread.interrupt();
				scrollThread = null;
			} catch (Exception e) {

			}
		}
	}

	/**
	 * 滚动线程
	 */
	Runnable scrollRunnable = new Runnable() {

		@Override
		public void run() {
			int nListItem = 0;
			int nRate = SCROLL_RATE;
			while (bStart && nChildCount > ITEM_COUNT) {
				nMoveDistance--;
				if (0 == nMoveDistance + nItemHeight) {
					nMoveDistance = 0;
					nChild++;
					nListItem++;
					if (nChild == nChildCount) {
						nChild = 0;
					}
				}
				if (nMoveDistance == -1 && nListItem % 2 == 0) {
					nListItem = 0;
					nRate = SCROLL_RATE;
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (nRate != 6) {
					nRate = nRate - 1;
				}
				try {
					Thread.sleep(nRate);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};

	@Override
	protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
		// if(!bScroll) {
		// super.drawChild(canvas, child, drawingTime);
		// }
		return true;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		// super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// 计算View的宽和高
		int nViewWidth = nScreenWidth / 2;
		// int nViewHeight = screenHeight;
		setTranslationY(-nSourceViewItem * nChildCount);
		if (nChildCount <= ITEM_COUNT) {
			setMeasuredDimension(nViewWidth, nSourceViewItem * nChildCount);
		} else {
			setMeasuredDimension(nViewWidth, nSourceViewItem * nChildCount
					+ CANVAS_MODIFY);
		}
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		super.setAdapter(adapter);

		nChildCount = adapter.getCount();

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// super.onSizeChanged(nItemWidth, nItemHeight * nChildCount, oldw,
		// oldh);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (nChildCount > ITEM_COUNT) {
				setTranslationY(0);
				actionDown((int) event.getX(), (int) event.getY());
			}
			break;

		case MotionEvent.ACTION_MOVE:
			if (bGuestScroll) {
				nGuestDistance = (int) event.getY() - nItemHeight * ITEM_COUNT;
			}
			break;

		case MotionEvent.ACTION_UP:
			if (bGuestScroll) {
				actionUp((int) event.getX(), (int) event.getY());
			}
			break;

		default:
			break;
		}

		return true;
	}

	/**
	 * onTouchEvent的ActionDown
	 * 
	 * @param nScrollX
	 *            横向距离
	 * @param nScrollY
	 *            纵向距离
	 */
	private void actionDown(int nScrollX, int nScrollY) {
		lOnClickTime = System.currentTimeMillis();
		// 计算可以点击手动滑动的区域，以及nGuestDistance的重置
		// int nClickHeight = 0;
		int nClickWidth = 0;
		// nClickWidth = nItemWidth / 3;
		nClickWidth = nScreenWidth / 3;

		if (bStart) {
			// 自动滚动时nGuestDistance为重置为0
			nGuestDistance = 0;
			// nClickHeight = nScrollY - nItemHeight * ITEM_COUNT;
		} else {
			// 状态栏全部展开时nGuestDistance为重置为nItemHeight*(nChildCount - ITEM_COUNT)
			nGuestDistance = nItemHeight * (nChildCount - ITEM_COUNT);
			// nClickHeight = nScrollY - nItemHeight * nChildCount;
		}
		// if(nClickHeight > -20 && nClickHeight < 80 && nClickWidth > nScrollX)
		// {
		// if(nClickHeight < 80 && nClickWidth > nScrollX) {
		if (nClickWidth > nScrollX) {
			bStart = !bStart;
			bGuestScroll = true;
			// 手动滑动时，线程停止
			try {
				if (null != scrollThread) {
					scrollThread.interrupt();
					scrollThread = null;
				}
			} catch (Exception e) {

			}
		} else if (bStart) {
			display();
		} else {
			dismiss();
		}
	}

	/**
	 * onTouchEvent的ActionUp
	 * 
	 * @param nScrollX
	 *            横向距离
	 * @param nScrollY
	 *            纵向距离
	 */
	private void actionUp(int nScrollX, int nScrollY) {
		// 判断点击时间，时间短则认为是点击展开，时间长则认为是手动滑动
		if (System.currentTimeMillis() - lOnClickTime < 300) {
			bScroll = !bScroll;
			if (bScroll) {
				bStart = true;
			} else {
				bStart = false;
			}

		} else {
			// 滑动距离不到一半向上，反之亦然
			int n = nScrollY - (int) nItemHeight * nChildCount / 2;
			if (n > 0) {
				bScroll = false;
				bStart = false;
			} else {
				bScroll = true;
				bStart = true;
			}
		}

		bGuestScroll = false;
		nMoveDistance = 0;
		if (!bScroll) {
			nGuestDistance = nItemHeight * (nChildCount - ITEM_COUNT);
			nChild = 0;
		} else {
			setTranslationY(-(nItemHeight * (-2) + nSourceViewItem
					* nChildCount - CANVAS_MODIFY));
		}
	}

	/**
	 * 还原View为默认模式
	 */
	public void dismiss() {

		if (!bScroll) {
			bScroll = true;
			bStart = true;
		}
		setTranslationY(-nSourceViewItem * nChildCount);
		invalidate();
	}

	public void display() {

		try {
			if (null != scrollThread) {
				scrollThread.interrupt();
				scrollThread = null;
			}
		} catch (Exception e) {

		}
		bScroll = false;
		bStart = false;
		bGuestScroll = false;
		nMoveDistance = 0;
		nGuestDistance = nItemHeight * (nChildCount - ITEM_COUNT);
		nChild = 0;
	}

	@Override
	public void onClick(View v) {
		// dismiss();
	}
	
	
	
}
