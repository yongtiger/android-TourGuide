package cc.brainbook.android.tourguide.app;


import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import cc.brainbook.android.tourguide.Overlay;
import cc.brainbook.android.tourguide.Pointer;
import cc.brainbook.android.tourguide.ToolTip;
import cc.brainbook.android.tourguide.TourGuide;

/**
 * InSequenceActivity demonstrates how to use TourGuide in sequence one after another
 */
public class ManualSequenceActivity extends AppCompatActivity {
    public TourGuide mTutorialHandler;
    public Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.activity_in_sequence);

        /* Get 3 buttons from layout */
        Button button = (Button)findViewById(R.id.button);
        final Button button2 = (Button)findViewById(R.id.button2);
        final Button button3 = (Button)findViewById(R.id.button3);

        /* setup enter and exit animation */
        Animation enterAnimation = new AlphaAnimation(0f, 1f);
        enterAnimation.setDuration(600);
        enterAnimation.setFillAfter(true);

        Animation exitAnimation = new AlphaAnimation(1f, 0f);
        exitAnimation.setDuration(600);
        exitAnimation.setFillAfter(true);

        /* initialize TourGuide without playOn() */
        mTutorialHandler = TourGuide.init(this).with(TourGuide.Technique.CLICK)
                .setPointer(new Pointer())
                .setToolTip(new ToolTip()
                        .setTitle("Hey!")
                        .setDescription("I'm the top fellow")
                        .setGravity(Gravity.END)
                )
                .setOverlay(new Overlay()
                        .setEnterAnimation(enterAnimation)
                        .setExitAnimation(exitAnimation)
                );

        /* setup 1st button, when clicked, cleanUp() and re-run TourGuide on button2 */
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mTutorialHandler.cleanUp();
                mTutorialHandler.setToolTip(new ToolTip().setTitle("Hey there!").setDescription("Just the middle man").setGravity(Gravity.BOTTOM|Gravity.START)).playOn(button2);
            }
        });

        /* setup 2nd button, when clicked, cleanUp() and re-run TourGuide on button3 */
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mTutorialHandler.cleanUp();
                mTutorialHandler.setToolTip(new ToolTip().setTitle("Hey...").setDescription("It's time to say goodbye").setGravity(Gravity.TOP|Gravity.END)).playOn(button3);
            }
        });

        /* setup 3rd button, when clicked, run cleanUp() */
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mTutorialHandler.cleanUp();
            }
        });

        mTutorialHandler.playOn(button);
    }
}