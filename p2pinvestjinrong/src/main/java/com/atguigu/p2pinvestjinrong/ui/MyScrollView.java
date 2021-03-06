package com.atguigu.p2pinvestjinrong.ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

import com.atguigu.p2pinvestjinrong.util.UIUtils;

public class MyScrollView extends ScrollView {
    private View childView;
    private boolean isFinishAnimation = true;//是否动画结束
    private int lastY;//上一次y轴方向操作的坐标位置
    private int lastX, downX, downY;
    private Rect rect = new Rect();//创建一个矩形，用于记录临界状态的左、上、右、下

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * onFinishInflate:加载完成的意思
     * 在 setContentView 之后会调用此方法,或者 比如自己写的 自定义View.inflate (context,R.layout.my_view,null)之后可以调用此方法
     * 但是在 onFinishInflate 中不能获取 view 宽高，需要在 onMeasure 之后获取，
     * setContentView > onFinishInflate > view绘制流程（performMeasure、performLayout、performDraw）
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            childView = getChildAt(0);
        }
    }

    /**
     * onInterceptTouchEvent：事件拦截，用于拦截父控件传递过来的事件，用于判断传递过来的父控件事件是否需要被当前view处理；
     * 有3种返回情况：
     * return true：拦截该事件。把该事件交给当前 view的 onTouchEvent执行；
     * return false：不拦截、放行该事件。事件会被传递到当前 view 的 子控件中，由当前 子控件中的 dispatchTouchEvent进行处理；
     * return super.inInterceptTouchEvent(ev)：和return true 一样，拦截该事件。把该事件交给 当前 view 的 onTouchEvent执行；
     *拦截:实现父视图对子视图的拦截
     * 是否拦截成功，取决于方法的返回值。返回值true:拦截成功。反之，拦截失败
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isIntercept = false;//是否执行拦截，返回值true:拦截。反之，不拦截
        int eventX = (int) ev.getX();
        int eventY = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //记录按下的坐标
                lastX = downX = eventX;
                lastY = downY = eventY;
                break;
            case MotionEvent.ACTION_MOVE:
                //获取水平和垂直方向的移动距离
                int absX = Math.abs(eventX - downX);
                int absY = Math.abs(eventY - downY);
                if (absY > absX && absY >= UIUtils.dp2px(10)) {
                    isIntercept = true;//执行拦截
                }
                lastX = eventX;
                lastY = eventY;
                break;
        }
        return isIntercept;
    }

    /**
     * onTouchEvent:当前的 view 把事件拦截了，事件会传递到这个方法中
     * 有3种返回情况：
     * return true：消费了该事件，事件到此结束；
     * return false：没有消费事件，事件会以冒泡方式传递到 最上层的 view 或者 activity，如果最上边的 view 或者 activity没有处理，还是 返回 false，该事件将消失。接下来的所有事件都会被 最上层的view 的 onTouchEvent捕获；
     * return super.onTouchEvent(event)：默认情况，和 return false一样；
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //如果没有子视图或者动画没有结束，就不做处理，按照原来的方式进行
        if (childView == null || !isFinishAnimation) {
            return super.onTouchEvent(ev);
        }
        int eventY = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //记录按下时的坐标
                lastY = eventY;
                break;

            case MotionEvent.ACTION_MOVE:
                int dy = eventY - lastY;//微小的移动量
                //如果超过临界需要做移动
                if (isNeedMove()) {
                    //如果还没有记录临界状态的左、上、右、下，就记录一遍
                    if (rect.isEmpty()) {
                        //set(int left, int top, int right, int bottom)
                        rect.set(childView.getLeft(), childView.getTop(), childView.getRight(), childView.getBottom());
                    }
                    //重新布局
                    childView.layout(childView.getLeft(), childView.getTop() + dy / 2, childView.getRight(), childView.getBottom() + dy / 2);
                }
                lastY = eventY;//重新赋值
                break;

            case MotionEvent.ACTION_UP:
                if (isNeedAnimation()) {
                    //使用平移动画
                    int translateY = childView.getBottom() - rect.bottom;
                    //TranslateAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta)
                    TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, -translateY);
                    translateAnimation.setDuration(200);
//                    translateAnimation.setFillAfter(true);//停留在最终位置上
                    translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            isFinishAnimation = false;
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            isFinishAnimation = true;
                            //清除动画
                            childView.clearAnimation();
                            //重新布局
                            childView.layout(rect.left,rect.top,rect.right,rect.bottom);
                            //清除rect的数据
                            rect.setEmpty();
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    //启动动画
                    childView.startAnimation(translateAnimation);
                }
                break;

        }

        return super.onTouchEvent(ev);
    }

    //判断是否需要执行平移动画,当有滚动时需要执行平移动画
    private boolean isNeedAnimation() {
        return !rect.isEmpty();
    }

    private boolean isNeedMove() {
        //获取子视图的高度，（是MyScrollView整个布局内容的高度）
        int childMeasuredHeight = childView.getMeasuredHeight();
        //获取布局的高度（MyScrollView在屏幕的区间）
        int scrollViewMeasuredHeight = this.getMeasuredHeight();

        Log.e("TAG", "childMeasuredHeight = " + childMeasuredHeight);
        Log.e("TAG", "scrollViewMeasuredHeight = " + scrollViewMeasuredHeight);
        //dy >= 0
        int dy = childMeasuredHeight - scrollViewMeasuredHeight;
        //获取用户在y轴方向上的偏移量 （上 + 下 -）
        int scrollY = this.getScrollY();
        if (scrollY <= 0 || scrollY >= dy) {
            return true;//按照我们自定义的MyScrollView的方式处理
        }
        //其他处在临界范围内的，返回false。即表示，仍按照ScrollView的方式处理
        return false;
    }
}
