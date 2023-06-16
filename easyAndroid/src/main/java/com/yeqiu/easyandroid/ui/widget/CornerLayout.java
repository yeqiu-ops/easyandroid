package com.yeqiu.easyandroid.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yeqiu.easyandroid.R;
import com.yeqiu.easyandroid.utils.ResourceUtil;
import com.yeqiu.easyandroid.utils.ScreenUtils;


/**
 * @project：HailHydra
 * @author：小卷子
 * @date 2019-08-05
 * @describe：圆角布局 摘自 https://github.com/GcsSloop/rclayout
 * @fix：
 */
public class CornerLayout extends FrameLayout {

    /**
     * 四个角的圆角，优先级最高
     */
    private int corners;
    private int topLeftCorner;
    private int topRightCorner;
    private int bottomLeftCorner;
    private int bottomRightCorner;
    private int strokeWidth;
    private int strokeColor = ResourceUtil.getColor(R.color.color_transparent);
    public float[] radii = new float[8];
    /**
     * 画布图层大小
     */
    private RectF layer;
    /**
     * 内容区域
     */
    private Region areaRegion;
    private Paint paint;
    /**
     * 剪裁区域路径
     */
    private Path clipPath;

    public CornerLayout(@NonNull Context context) {
       super(context);
        init();
    }

    public CornerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context,attrs);
        initAttr(attrs);
        init();
    }

    public CornerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttr(attrs);
        init();

    }

    private void initAttr(AttributeSet attrs) {


        if (attrs == null) {
            return;
        }
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CornerLayout);

        corners = typedArray.getDimensionPixelSize(R.styleable.CornerLayout_corners, 0);
        if (corners != 0) {
            topLeftCorner = corners;
            topRightCorner = corners;
            bottomLeftCorner = corners;
            bottomRightCorner = corners;
        } else {
            topLeftCorner = typedArray.getDimensionPixelSize(R.styleable.CornerLayout_top_left_corner, 0);
            topRightCorner = typedArray.getDimensionPixelSize(R.styleable.CornerLayout_top_right_corner, 0);
            bottomLeftCorner = typedArray.getDimensionPixelSize(R.styleable.CornerLayout_bottom_left_corner
                    , 0);
            bottomRightCorner =
                    typedArray.getDimensionPixelSize(R.styleable.CornerLayout_bottom_right_corner, 0);
        }

        strokeWidth = typedArray.getDimensionPixelSize(R.styleable.CornerLayout_stroke_width, 1);
        strokeColor = typedArray.getColor(R.styleable.CornerLayout_stroke_color, ResourceUtil.getColor(R.color.color_transparent));

        typedArray.recycle();


    }


    private void init() {

        layer = new RectF();
        areaRegion = new Region();
        clipPath = new Path();
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);

        setRadii();
    }

    private void setRadii() {
        radii[0] = topLeftCorner;
        radii[1] = topLeftCorner;

        radii[2] = topRightCorner;
        radii[3] = topRightCorner;

        radii[4] = bottomRightCorner;
        radii[5] = bottomRightCorner;

        radii[6] = bottomLeftCorner;
        radii[7] = bottomLeftCorner;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        layer.set(0, 0, w, h);
        refreshRegion(this);
    }

    private void refreshRegion(View view) {
        int w = (int) layer.width();
        int h = (int) layer.height();
        RectF areas = new RectF();
        areas.left = view.getPaddingLeft();
        areas.top = view.getPaddingTop();
        areas.right = w - view.getPaddingRight();
        areas.bottom = h - view.getPaddingBottom();
        clipPath.reset();
        clipPath.addRoundRect(areas, radii, Path.Direction.CW);
        Region clip = new Region((int) areas.left, (int) areas.top,
                (int) areas.right, (int) areas.bottom);
        areaRegion.setPath(clipPath, clip);
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.saveLayer(layer, null, Canvas.ALL_SAVE_FLAG);
        super.dispatchDraw(canvas);
        onClipDraw(canvas);
        canvas.restore();

    }


    public void onClipDraw(Canvas canvas) {
        if (strokeWidth > 0) {
            // 支持半透明描边，将与描边区域重叠的内容裁剪掉
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            paint.setColor(Color.WHITE);
            paint.setStrokeWidth(strokeWidth * 2);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(clipPath, paint);
            // 绘制描边
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
            paint.setColor(strokeColor);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(clipPath, paint);
        }
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1) {
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            canvas.drawPath(clipPath, paint);
        } else {
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

            final Path path = new Path();
            path.addRect(0, 0, (int) layer.width(), (int) layer.height(), Path.Direction.CW);
            path.op(clipPath, Path.Op.DIFFERENCE);
            canvas.drawPath(path, paint);
        }
    }


    //--------------- 公开接口---------------

    public CornerLayout setCorners(int corners) {
        setTopRightCorner(corners);
        setTopLeftCorner(corners);
        setBottomRightCorner(corners);
        setBottomLeftCorner(corners);
        return this;
    }

    public CornerLayout setTopLeftCorner(int topLeftCorner) {
        this.topLeftCorner = ScreenUtils.dp2px(topLeftCorner);
        return this;
    }

    public CornerLayout setTopRightCorner(int topRightCorner) {
        this.topRightCorner = ScreenUtils.dp2px(topRightCorner);
        return this;
    }

    public CornerLayout setBottomLeftCorner(int bottomLeftCorner) {
        this.bottomLeftCorner = ScreenUtils.dp2px(bottomLeftCorner);
        return this;
    }

    public CornerLayout setBottomRightCorner(int bottomRightCorner) {
        this.bottomRightCorner = ScreenUtils.dp2px(bottomRightCorner);
        return this;
    }

    public CornerLayout setStrokeWidth(int strokeWidth) {
        this.strokeWidth = ScreenUtils.dp2px(strokeWidth);
        return this;
    }

    public CornerLayout setStrokeColor(int strokeColor) {
        this.strokeColor = ResourceUtil.getColor(strokeColor);
        return this;
    }

    public void refresh() {

        setRadii();
        refreshRegion(this);
        invalidate();

    }


}