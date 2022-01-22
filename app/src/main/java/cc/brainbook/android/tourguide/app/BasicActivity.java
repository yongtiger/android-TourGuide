package cc.brainbook.android.tourguide.app;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import cc.brainbook.android.tourguide.Overlay;
import cc.brainbook.android.tourguide.Pointer;
import cc.brainbook.android.tourguide.ToolTip;
import cc.brainbook.android.tourguide.TourGuide;

public class BasicActivity extends AppCompatActivity {
    public static String COLOR_DEMO = "color_demo";
    public static String GRAVITY_DEMO = "gravity_demo";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        /* Get parameters from main activity */
        final boolean colorDemo = getIntent().getBooleanExtra(COLOR_DEMO, false);
        final boolean gravityDemo = getIntent().getBooleanExtra(GRAVITY_DEMO, false);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);

        final Button button1 = findViewById(R.id.button1);
        final Button button2 = findViewById(R.id.button2);

        final TourGuide tourGuide = TourGuide.init(this)
                .setPointer(new Pointer(Gravity.END,
                        colorDemo ? Color.RED : Color.WHITE))
                .setOverlay(new Overlay().setBackgroundColor(Color.parseColor("#66FF0000")))
                .setToolTip(new ToolTip()
                .setTitle("Welcome!")
                .setDescription("Click").setGravity(Gravity.END))
                .playOn(button1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tourGuide.cleanUp();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tourGuide.playOn(button1);
            }
        });
    }

}
