package com.atguigu.p2pinvestjinrong.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.atguigu.p2pinvestjinrong.R;
import com.atguigu.p2pinvestjinrong.util.UIUtils;

public class RoundProgress extends View {

//    private int measuredWidth;//当前视图的宽度（=高度）
//    private Paint paint;
//    private int roundWidth = UIUtils.dp2px(10);//圆环的宽度
//    private int roundColor = Color.GRAY;//圆环的颜
//    private float progress = 60;//圆环的进度
//    private float max = 100;//圆环的最大值
//    private int roundProgressColor = Color.RED;//圆弧的颜色
//    private int textColor = Color.BLUE;//文本的颜色
//    private float textSize = UIUtils.dp2px(20);//文本的字体大小

    private int measuredWidth;//当前视图的宽度（=高度）
    private Paint paint;
    private float roundWidth;//圆环的宽度
    private int roundColor;//圆环的颜
    private int progress;//圆环的进度
    private int max;//圆环的最大值
    private int roundProgressColor;//圆弧的颜色
    private int textColor;//文本的颜色
    private float textSize;//文本的字体大小

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public RoundProgress(Context context) {
        this(context, null);
    }

    public RoundProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);//去除毛边

        //获取自定义的属性
        //1.获取TypeArray的对象(调用两个参数的方法）
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgress);
        //2.取出所有的自定义属性
        roundColor = typedArray.getColor(R.styleable.RoundProgress_roundColor, Color.GRAY);
        roundProgressColor = typedArray.getColor(R.styleable.RoundProgress_roundProgressColor, Color.RED);
        textColor = typedArray.getColor(R.styleable.RoundProgress_textColor, Color.GREEN);
        roundWidth = typedArray.getDimension(R.styleable.RoundProgress_roundWidth, UIUtils.dp2px(10));
        textSize = typedArray.getDimension(R.styleable.RoundProgress_textSize, UIUtils.dp2px(20));
        max = typedArray.getInteger(R.styleable.RoundProgress_max, 100);
        progress = typedArray.getInteger(R.styleable.RoundProgress_progress, 30);
        //3.回收处理
        typedArray.recycle();
    }

    //测量：获取当前视图宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measuredWidth = this.getMeasuredWidth();
    }

    //canvas:画布，对应着视图在布局中的范围区间。范围的左上顶点即为坐标原点
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //1.绘制圆环
        //获取圆心坐标
        int cx = measuredWidth / 2;
        int cy = measuredWidth / 2;
        float radius = measuredWidth / 2 - roundWidth / 2;
        paint.setColor(roundColor);//设置画笔颜色
        paint.setStyle(Paint.Style.STROKE);//设置圆环的样式
        paint.setStrokeWidth(roundWidth);//设置圆环的宽度
        //drawCircle(float cx, float cy, float radius, @NonNull Paint paint);圆心坐标，半径，画笔；
        canvas.drawCircle(cx, cy, radius, paint);

        //2.绘制圆弧
        /**
         * drawArc(RectF oval, float startAngle, float sweepAngle, boolean useCenter,Paint paint)
         * RectF oval 用于定义圆弧形状和大小的椭圆边界
         * float startAngle 开始角度（以度为单位），弧从何处开始
         * float sweepAngle 顺时针测量的扫描角度（单位：度）
         * boolean useCenter 如果为true，请将椭圆的中心包含在弧中，如果正在抚摸椭圆，请将其关闭。这会拉一个楔子
         * Paint paint用于绘制圆弧的绘制
         * RectF(float left, float top, float right, float bottom)
         */
        RectF rectF = new RectF(roundWidth / 2, roundWidth / 2, measuredWidth - roundWidth / 2, measuredWidth - roundWidth / 2);
        paint.setColor(roundProgressColor);//设置画笔颜色
        canvas.drawArc(rectF, 0, progress * 360 / max, false, paint);

        //3.绘制文本
        String text = progress * 100 / max + "%";
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setStrokeWidth(0);//设置笔划宽度
        Rect rect = new Rect();//创建一个放置文本的矩形，此时矩形没有具体的宽度和高度
        /**
         * getTextBounds(String text, int start, int end, Rect bounds)
         * text 要测量并返回其边界的字符串
         * start 要度量的字符串中第一个字符的索引
         * end 超过字符串中要测量的最后一个字符
         * Rect bounds 返回所有文本的联合边界。必须由调用方分配
         */
        paint.getTextBounds(text, 0, text.length(), rect);
        //获取左下顶点的坐标
        int x = measuredWidth / 2 - rect.width() / 2;
        int y = measuredWidth / 2 + rect.height() / 2;
        canvas.drawText(text, x, y, paint);
    }
}
