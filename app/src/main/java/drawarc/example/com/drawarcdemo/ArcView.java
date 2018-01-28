package drawarc.example.com.drawarcdemo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ArcView extends View {

	private static final String TAG = "ArcView";
	private static final float TWELVE_O_CLOCK = -90f;

	private ValueAnimator animator;
	private float currentAngle = 0f;
	private RectF wheelRect;
	private Paint wheelPaint;

	public ArcView(Context context) {
		super(context);
		init();
	}

	public ArcView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int size = Math.min(getMeasuredWidth(), getMeasuredHeight());
		Log.d(TAG, "onMeasure: " + size);
		setMeasuredDimension(size, size);
	}

	public void stopAnimation() {
		animator.cancel();
	}

	public void setAngle(int angle) {
		this.currentAngle = angle;
		invalidate();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		Log.d(TAG, "onSizeChanged: " + w + " " + h);

		wheelRect = new RectF(0, 0, w, h);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		drawRedCircle(canvas);
	}

	private void init() {
		setupPaints();
	}

	private void setupPaints() {
		wheelPaint = new Paint();
		wheelPaint.setColor(getResources().getColor(R.color.colorPrimary));
		wheelPaint.setAntiAlias(true);
	}

	private void drawRedCircle(Canvas canvas) {
		canvas.drawArc(wheelRect, TWELVE_O_CLOCK, currentAngle, true, wheelPaint);
	}


}
