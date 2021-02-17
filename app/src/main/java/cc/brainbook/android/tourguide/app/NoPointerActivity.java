package cc.brainbook.android.tourguide.app;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import cc.brainbook.android.tourguide.Overlay;
import cc.brainbook.android.tourguide.ToolTip;
import cc.brainbook.android.tourguide.TourGuide;

public class NoPointerActivity extends AppCompatActivity {
    public TourGuide mTourGuideHandler;
    public Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* Get parameters from main activity */
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.activity_basic);

        final Button button1 = (Button) findViewById(R.id.button1);
        final Button button2 = (Button) findViewById(R.id.button2);
        button1.setText("Purchase");

        ToolTip toolTip = new ToolTip().
                setTitle("Expensive Item").
                setDescription("Click 'purchase' only when you are ready\nClick on the OVERLAY to dismiss");
        Overlay overlay = new Overlay().disableClickThroughHole(true).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTourGuideHandler.cleanUp();
            }
        });
        // the return handler is used to manipulate the cleanup of all the tutorial elements
        mTourGuideHandler = TourGuide.init(this).with(TourGuide.Technique.CLICK)
                .setPointer(null) // set pointer to null
                .setToolTip(toolTip)
                .setOverlay(overlay)
                .playOn(button1);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTourGuideHandler.playOn(button1);
            }
        });
    }
}