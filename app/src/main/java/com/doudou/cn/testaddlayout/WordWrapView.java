package com.doudou.cn.testaddlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by jinliang on 16/1/24.
 */
public class WordWrapView extends ViewGroup {
    private static final String TAG = WordWrapView.class.getSimpleName();
    private static final int PADDING_HOR = 10;// 水平方向padding
    private static final int PADDING_VERTICAL = 1;// 垂直方向padding
    private static final int SIDE_MARGIN = 10;// 左右间距
    private static final int TEXT_MARGIN = 10;

    public WordWrapView(Context context) {
        super(context);
    }

    public WordWrapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WordWrapView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    //先进行测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(TAG, "onMeasure widthMeasureSpec:" + widthMeasureSpec + "   heightMeasureSpec:" + heightMeasureSpec);
        int x = 0;// 横坐标
        int y = 0;// 纵坐标
        int rows = 1;// 总行数
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        int actualWidth = specWidth - SIDE_MARGIN * 2;// 实际宽度
        int childCount = getChildCount();
        for (int index = 0; index < childCount; index++) {
            View child = (TextView) getChildAt(index);
            child.setPadding(
                    PADDING_HOR, PADDING_VERTICAL, PADDING_HOR,
                    PADDING_VERTICAL
            );
            child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            x += width + TEXT_MARGIN;
            if (x > actualWidth) {// 换行
                x = width;
                rows++;
            }
            y = rows * (height + TEXT_MARGIN);
        }
        setMeasuredDimension(actualWidth, y);
    }

    //后进行 布局
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.i(TAG, "onLayout :left:" + l + "   top:" + t + "  right:" + r + "  bottom:" + b);
        int childCount = getChildCount();
        Log.i(TAG, "onLayout :childCount:" + getChildCount());
        int autualWidth = r - l;
        int x = SIDE_MARGIN;// 横坐标开始
        int y = 0;// 纵坐标开始
        int rows = 1;
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
//            view.setBackgroundResource(R.drawable.shopdetail_keytag_bg);
//            view.setBackgroundColor(Color.RED);
            int width = view.getMeasuredWidth();
            int height = view.getMeasuredHeight();
            x += width + TEXT_MARGIN;
            if (x > autualWidth) {
                x = width + SIDE_MARGIN;
                rows++;
            }
            y = rows * (height + TEXT_MARGIN);
            if (i == 0) {
                view.layout(
                        x - width - TEXT_MARGIN, y - height, x
                                - TEXT_MARGIN, y
                );
            } else {
                view.layout(x - width, y - height, x, y);
            }
        }

    }
}
