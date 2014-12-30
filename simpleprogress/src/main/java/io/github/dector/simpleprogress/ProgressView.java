package io.github.dector.simpleprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ProgressView extends View {

    private int value;
    private int max;

    private Paint paint;
    private int lineWidth;
    private int lineY;

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeCap(Paint.Cap.ROUND);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SimpleProgressView);
        paint.setColor(a.getColor(R.styleable.SimpleProgressView_spv_color, 0xffff0000));
        setMax(100);
        setMax(a.getInt(R.styleable.SimpleProgressView_spv_max, 0));
        setValue(a.getInt(R.styleable.SimpleProgressView_spv_value, 0));
        a.recycle();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if (value < 0) {
            return;
        }

        this.value = value;

        recountProgressDrawable();
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        if (max <= 0) {
            return;
        }

        this.max = max;
    }

    public int getColor() {
        return paint.getColor();
    }

    public void setColor(int color) {
        paint.setColor(color);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        lineY = h / 2;
        paint.setStrokeWidth(h);

        recountProgressDrawable();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(0, lineY, lineWidth, lineY, paint);
    }

    private void recountProgressDrawable() {
        lineWidth = Math.round(1f * getWidth() * value / max);
        invalidate();
    }
}
