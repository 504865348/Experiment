package com.joshua.experiment.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lister on 2017/7/5.
 * FlowLayout: 用于存放标签的布局
 */

public class FlowLayout extends ViewGroup {

    /**
     * 存储所有的View，按行记录
     */
    private List<List<View>> mAllViews = new ArrayList<List<View>>();
    /**
     * 记录每一行的最大高度
     */
    private List<Integer> mLineHeight = new ArrayList<Integer>();

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 获得父容器的为其设置的宽高和模式
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        // 若为 wrap_content，记录宽和高
        int width = 0, height = 0;

        // 记录每一行宽度
        int lineWidth = 0;

        // 每一行的高度，累加至 height
        int lineHeight = 0;

        int cCount = getChildCount();

        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            // 测量每一个子 View 的宽高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            // 子 View 的 LayoutParams
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
            // 当前子 View 实际占据的宽高
            int childWidth = child.getMeasuredWidth() + params.leftMargin + params.rightMargin;
            int childHeight = child.getMeasuredHeight() + params.topMargin + params.bottomMargin;

            // 如果宽度没有满一行，则叠加宽度，并求出子 View 的最大行高
            if (lineWidth + childWidth <= sizeWidth) {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            } else {
                width = Math.max(width, lineWidth);
                height += lineHeight;
                lineWidth = childWidth; // 新的一行的宽度
                lineHeight = childHeight; // 新的一行的高度
            }
            // 到最后一个子 View 自动加一行
            if (i == cCount - 1) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }

        }
        setMeasuredDimension((modeWidth == MeasureSpec.EXACTLY) ? sizeWidth
                : width, (modeHeight == MeasureSpec.EXACTLY) ? sizeHeight
                : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        mAllViews.clear();
        mLineHeight.clear();

        int width = getWidth();

        int lineWidth = 0;
        int lineHeight = 0;

        // 存储每一行所有的childView
        List<View> lineViews = new ArrayList<>();
        int cCount = getChildCount();

        // 遍历所有的孩子
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            // 如果已经需要换行
            if (childWidth + lp.leftMargin + lp.rightMargin + lineWidth > width) {

                // 记录这一行所有的View以及最大高度
                mLineHeight.add(lineHeight);

                // 将当前行的childView保存，然后开启新的ArrayList保存下一行的childView
                mAllViews.add(lineViews);
                lineWidth = 0; // 重置行宽
                lineViews = new ArrayList<>();
            }
            /**
             * 如果不需要换行，则累加
             */
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin
                    + lp.bottomMargin);
            lineViews.add(child);
        }
        // 记录最后一行
        mLineHeight.add(lineHeight);
        mAllViews.add(lineViews);

        int left = 0;
        int top = 0;
        // 得到总行数
        int lineNums = mAllViews.size();

        for (int i = 0; i < lineNums; i++) {

            // 每一行的所有的views
            lineViews = mAllViews.get(i);

            // 当前行的最大高度
            lineHeight = mLineHeight.get(i);

            Log.e("TAG", "第" + i + "行 ：" + lineViews.size() + " , " + lineViews);
            Log.e("TAG", "第" + i + "行， ：" + lineHeight);

            // 遍历当前行所有的View
            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                if (child.getVisibility() == View.GONE)
                {
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child
                        .getLayoutParams();

                //计算childView的left,top,right,bottom
                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc =lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                Log.e("TAG", child + " , l = " + lc + " , t = " + t + " , r ="
                        + rc + " , b = " + bc);

                child.layout(lc, tc, rc, bc);

                left += child.getMeasuredWidth() + lp.rightMargin
                        + lp.leftMargin;
            }
            left = 0;
            top += lineHeight;
        }
    }
}
