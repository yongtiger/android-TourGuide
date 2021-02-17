package cc.brainbook.android.tourguide.app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import cc.brainbook.android.tourguide.Overlay;
import cc.brainbook.android.tourguide.Pointer;
import cc.brainbook.android.tourguide.ToolTip;
import cc.brainbook.android.tourguide.TourGuide;
import leakcanary.ObjectWatcher;

/**
 * This activity is used for testing memory, it serves no demo purpose, hence it's not listed in TourGuideDemoMain activity (it's commented out)
 * To test this:
 * 1. uncomment MemoryLeakTestActivity in TourGuideDemoMain.java
 * 2. Then launch MemoryLeakTestActivity and click back, launch MemoryLeakTestActivity and click back, repeat many times
 * 3. Then look at the memory usage, also check if LeakCanary freezes the screen and log a memory heap dump
 * 4. To force a memory leak, comment 'onDetachedFromWindow()' method in FrameLayoutWithHole
 * TODO: this should be included as a test, rather than being a commented activity
 */
public class MemoryLeakTestActivity extends AppCompatActivity {
    public TourGuide mTutorialHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);

        Button button = (Button)findViewById(R.id.button1);

        mTutorialHandler = TourGuide.init(this).with(TourGuide.Technique.CLICK)
                .setPointer(new Pointer())
                .setToolTip(new ToolTip()
                        .setTitle("Hey!")
                        .setDescription("Let's hope that there's no memory leak..."))
                .setOverlay(new Overlay())
                .playOn(button);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mTutorialHandler.cleanUp();
            }
        });
    }
    @Override public void onDestroy() {
        super.onDestroy();
        ObjectWatcher objectWatcher = MyApplication.getObjectWatcher(this);
        Log.d("ddw","objectWatcher: "+objectWatcher);
        objectWatcher.watch(this);
        objectWatcher.watch(mTutorialHandler);
    }
}