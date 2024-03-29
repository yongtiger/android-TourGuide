package cc.brainbook.android.tourguide;

import android.animation.AnimatorSet;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;

import java.util.ArrayList;

/**
 * TODO: document your custom view class.
 */
public class FrameLayoutWithHole extends FrameLayout {
    private TextPaint mTextPaint;
    private Activity mActivity;
    private TourGuide.MotionType mMotionType;
    private Paint mEraser;

    private Bitmap mEraserBitmap;
    private Canvas mEraserCanvas;
    private Paint mPaint;
    private Paint transparentPaint;
    private View mViewHole; // This is the targeted view to be highlighted, where the hole should be placed
    private int mRadius;
    private int[] mPos;
    private float mDensity;
    private Overlay mOverlay;
    private RectF mRectF;

    private ArrayList<AnimatorSet> mAnimatorSetArrayList;

    public void setViewHole(View viewHole) {
        this.mViewHole = viewHole;
        enforceMotionType();
    }

    public void addAnimatorSet(AnimatorSet animatorSet) {
        if (mAnimatorSetArrayList == null) {
            mAnimatorSetArrayList = new ArrayList<AnimatorSet>();
        }
        mAnimatorSetArrayList.add(animatorSet);
    }

    private void enforceMotionType() {
        Log.d("tourguide", "enforceMotionType 1");
        if (mViewHole != null) {
            Log.d("tourguide", "enforceMotionType 2");
            if (mMotionType != null && mMotionType == TourGuide.MotionType.CLICK_ONLY) {
                Log.d("tourguide", "enforceMotionType 3");
                Log.d("tourguide", "only Clicking");
                mViewHole.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        mViewHole.getParent().requestDisallowInterceptTouchEvent(true);
                        return false;
                    }
                });
            } else if (mMotionType != null && mMotionType == TourGuide.MotionType.SWIPE_ONLY) {
                Log.d("tourguide", "enforceMotionType 4");
                Log.d("tourguide", "only Swiping");
                mViewHole.setClickable(false);
            }
        }
    }

    public FrameLayoutWithHole(Activity context, View view) {
        this(context, view, TourGuide.MotionType.ALLOW_ALL);
    }

    public FrameLayoutWithHole(Activity context, View view, TourGuide.MotionType motionType) {
        this(context, view, motionType, new Overlay());
    }

    public FrameLayoutWithHole(Activity context, View view, TourGuide.MotionType motionType, Overlay overlay) {
        super(context);
        mActivity = context;
        mViewHole = view;
        init(null, 0);
        enforceMotionType();
        mOverlay = overlay;

        int[] pos = new int[2];
        mViewHole.getLocationOnScreen(pos);
        mPos = pos;

        mDensity = context.getResources().getDisplayMetrics().density;
        int padding = (int) (20 * mDensity);

        if (mViewHole.getHeight() > mViewHole.getWidth()) {
            mRadius = mViewHole.getHeight() / 2 + padding;
        } else {
            mRadius = mViewHole.getWidth() / 2 + padding;
        }
        mMotionType = motionType;

        // Init a RectF to be used in OnDraw for a ROUNDED_RECTANGLE Style Overlay
        if (mOverlay !=null && mOverlay.mStyle == Overlay.Style.ROUNDED_RECTANGLE) {
            int recfFPaddingPx = (int) (mOverlay.mPaddingDp * mDensity);
            mRectF = new RectF(mPos[0] - recfFPaddingPx + mOverlay.mHoleOffsetLeft,
                    mPos[1] - recfFPaddingPx + mOverlay.mHoleOffsetTop,
                    mPos[0] + mViewHole.getWidth() + recfFPaddingPx + mOverlay.mHoleOffsetLeft,
                    mPos[1] + mViewHole.getHeight() + recfFPaddingPx + mOverlay.mHoleOffsetTop);
        }
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
//        final TypedArray a = getContext().obtainStyledAttributes(
//                attrs, FrameLayoutWithHole, defStyle, 0);
//
//
//        a.recycle();
        setWillNotDraw(false);
        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        Point size = new Point();
        size.x = mActivity.getResources().getDisplayMetrics().widthPixels;
        size.y = mActivity.getResources().getDisplayMetrics().heightPixels;

        ///[FIX#Bitmap.createBitmap导致内存溢出]
        ///1:尽量少用慎用createBitmap，用了必须try catch
        ///2:在catch中处理，不然就不走了，判断是否是OutOfMemoryError，在处理
        ///3:处理一般返回默认图片，要么就根据path，用BitmapFactory.decodeStream（new FileInputStream(path)）来获取bitmap对象
        ///4:bitmap用完一定要回收recycle
        ///https://blog.csdn.net/qq_16131393/article/details/103458663
        ///https://blog.csdn.net/tangnengwu/article/details/36185361
//        mEraserBitmap = Bitmap.createBitmap(size.x, size.y,Bitmap.Config.ARGB_8888);
        try {
            mEraserBitmap = Bitmap.createBitmap(size.x, size.y, Bitmap.Config.ARGB_4444);
            mEraserCanvas = new Canvas(mEraserBitmap);
        } catch (Exception error) {
            mEraserBitmap = null;
            mEraserCanvas = new Canvas();
        }

        mPaint = new Paint();
        mPaint.setColor(0xcc000000);
        transparentPaint = new Paint();
        transparentPaint.setColor(getResources().getColor(android.R.color.transparent));
        transparentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        mEraser = new Paint();
        mEraser.setColor(0xFFFFFFFF);
        mEraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mEraser.setFlags(Paint.ANTI_ALIAS_FLAG);

        Log.d("tourguide", "getHeight: " + size.y);
        Log.d("tourguide", "getWidth: " + size.x);

    }

    private boolean mCleanUpLock = false;

    protected void cleanUp() {
        if (getParent() != null) {
            if (mOverlay != null && mOverlay.mExitAnimation != null) {
                performOverlayExitAnimation();
            } else {
                ((ViewGroup) this.getParent()).removeView(this);
            }
        }
    }

    private void performOverlayExitAnimation() {
        if (!mCleanUpLock) {
            final FrameLayout _pointerToFrameLayout = this;
            mCleanUpLock = true;
            Log.d("tourguide", "Overlay exit animation listener is overwritten...");
            mOverlay.mExitAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    ((ViewGroup) _pointerToFrameLayout.getParent()).removeView(_pointerToFrameLayout);
                }
            });
            this.startAnimation(mOverlay.mExitAnimation);
        }
    }

    /* comment this whole method to cause a memory leak */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        /* cleanup reference to prevent memory leak */
        mEraserCanvas.setBitmap(null);

        ///[FIX#Bitmap.createBitmap导致内存溢出]
        ///1:尽量少用慎用createBitmap，用了必须try catch
        ///2:在catch中处理，不然就不走了，判断是否是OutOfMemoryError，在处理
        ///3:处理一般返回默认图片，要么就根据path，用BitmapFactory.decodeStream（new FileInputStream(path)）来获取bitmap对象
        ///4:bitmap用完一定要回收recycle
        ///https://blog.csdn.net/qq_16131393/article/details/103458663
        ///https://blog.csdn.net/tangnengwu/article/details/36185361
        if (mEraserBitmap != null && !mEraserBitmap.isRecycled()) {
            mEraserBitmap.recycle();
            mEraserBitmap = null;
        }

        if (mAnimatorSetArrayList != null && !mAnimatorSetArrayList.isEmpty()) {
            for (int i = 0; i < mAnimatorSetArrayList.size(); i++) {
                mAnimatorSetArrayList.get(i).end();
                mAnimatorSetArrayList.get(i).removeAllListeners();
            }
        }
    }

    /**
     * Show an event in the LogCat view, for debugging
     */
    private static void dumpEvent(MotionEvent event) {
        String[] names = {"DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
                "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?"};
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(names[actionCode]);
        if (actionCode == MotionEvent.ACTION_POINTER_DOWN
                || actionCode == MotionEvent.ACTION_POINTER_UP) {
            sb.append("(pid ").append(
                    action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")");
        }
        sb.append("[");
        for (int i = 0; i < event.getPointerCount(); i++) {
            sb.append("#").append(i);
            sb.append("(pid ").append(event.getPointerId(i));
            sb.append(")=").append((int) event.getX(i));
            sb.append(",").append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())
                sb.append(";");
        }
        sb.append("]");
        Log.d("tourguide", sb.toString());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //first check if the location button should handle the touch event
//        dumpEvent(ev);
//        int action = MotionEventCompat.getActionMasked(ev);
        if (mViewHole != null) {

//            Log.d("tourguide", "[dispatchTouchEvent] mViewHole.getHeight(): "+mViewHole.getHeight());
//            Log.d("tourguide", "[dispatchTouchEvent] mViewHole.getWidth(): "+mViewHole.getWidth());
//
//            Log.d("tourguide", "[dispatchTouchEvent] Touch X(): "+ev.getRawX());
//            Log.d("tourguide", "[dispatchTouchEvent] Touch Y(): "+ev.getRawY());

//            Log.d("tourguide", "[dispatchTouchEvent] X of image: "+pos[0]);
//            Log.d("tourguide", "[dispatchTouchEvent] Y of image: "+pos[1]);

//            Log.d("tourguide", "[dispatchTouchEvent] X lower bound: "+ pos[0]);
//            Log.d("tourguide", "[dispatchTouchEvent] X higher bound: "+(pos[0] +mViewHole.getWidth()));
//
//            Log.d("tourguide", "[dispatchTouchEvent] Y lower bound: "+ pos[1]);
//            Log.d("tourguide", "[dispatchTouchEvent] Y higher bound: "+(pos[1] +mViewHole.getHeight()));

            if (isWithinButton(ev) && mOverlay != null && mOverlay.mDisableClickThroughHole) {
                Log.d("tourguide", "block user clicking through hole");
                // block it
                return true;
            } else if (isWithinButton(ev)) {
                // let it pass through
                return false;
            }
        }
        // do nothing, just propagating up to super
        return super.dispatchTouchEvent(ev);
    }

    private boolean isWithinButton(MotionEvent ev) {
        int[] pos = new int[2];
        mViewHole.getLocationOnScreen(pos);
        return ev.getRawY() >= pos[1] &&
                ev.getRawY() <= (pos[1] + mViewHole.getHeight()) &&
                ev.getRawX() >= pos[0] &&
                ev.getRawX() <= (pos[0] + mViewHole.getWidth());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mEraserBitmap != null)
            mEraserBitmap.eraseColor(Color.TRANSPARENT);

        if (mOverlay != null) {
            mEraserCanvas.drawColor(mOverlay.mBackgroundColor);
            int padding = (int) (mOverlay.mPaddingDp * mDensity);
            Log.i("TOURGUIDE", String.format("**********PADDING: %s**********", padding));

            if (mOverlay.mStyle == Overlay.Style.RECTANGLE) {
                mEraserCanvas.drawRect(
                        mPos[0] - padding + mOverlay.mHoleOffsetLeft,
                        mPos[1] - padding + mOverlay.mHoleOffsetTop,
                        mPos[0] + mViewHole.getWidth() + padding + mOverlay.mHoleOffsetLeft,
                        mPos[1] + mViewHole.getHeight() + padding + mOverlay.mHoleOffsetTop, mEraser);
            } else if (mOverlay.mStyle == Overlay.Style.NO_HOLE) {
                mEraserCanvas.drawCircle(
                        mPos[0] + mViewHole.getWidth() / 2 + mOverlay.mHoleOffsetLeft,
                        mPos[1] + mViewHole.getHeight() / 2 + mOverlay.mHoleOffsetTop,
                        0, mEraser);
            } else if (mOverlay.mStyle == Overlay.Style.ROUNDED_RECTANGLE) {
                int roundedCornerRadiusPx;
                if (mOverlay.mRoundedCornerRadiusDp != 0) {
                    roundedCornerRadiusPx = (int) (mOverlay.mRoundedCornerRadiusDp * mDensity);
                } else {
                    roundedCornerRadiusPx = (int) (10 * mDensity);
                }
                mEraserCanvas.drawRoundRect(mRectF, roundedCornerRadiusPx, roundedCornerRadiusPx, mEraser);
            } else {
                int holeRadius = mOverlay.mHoleRadius != Overlay.NOT_SET ? mOverlay.mHoleRadius : mRadius;
                mEraserCanvas.drawCircle(
                        mPos[0] + mViewHole.getWidth() / 2 + mOverlay.mHoleOffsetLeft,
                        mPos[1] + mViewHole.getHeight() / 2 + mOverlay.mHoleOffsetTop,
                        holeRadius, mEraser);
            }
        }
        if (mEraserBitmap != null)
            canvas.drawBitmap(mEraserBitmap, 0, 0, null);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mOverlay != null && mOverlay.mEnterAnimation != null) {
            this.startAnimation(mOverlay.mEnterAnimation);
        }
    }

    /**
     * Convenient method to obtain screen width in pixel
     *
     * @param activity
     * @return screen width in pixel
     */
    public int getScreenWidth(Activity activity) {
        return activity.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * Convenient method to obtain screen height in pixel
     *
     * @param activity
     * @return screen width in pixel
     */
    public int getScreenHeight(Activity activity) {
        return activity.getResources().getDisplayMetrics().heightPixels;
    }
}