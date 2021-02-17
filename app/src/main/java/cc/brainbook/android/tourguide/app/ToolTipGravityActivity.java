package cc.brainbook.android.tourguide.app;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import cc.brainbook.android.tourguide.ToolTip;
import cc.brainbook.android.tourguide.TourGuide;

public class ToolTipGravityActivity extends AppCompatActivity {
    public final static String TOOLTIP_NUM = "status_bar";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        final int tooltipNum = getIntent().getIntExtra(TOOLTIP_NUM, 1);

        super.onCreate(savedInstanceState);

        int gravity;
        switch (tooltipNum) {
            case 1:
                setContentView(R.layout.activity_tooltip_gravity_i);
                gravity = Gravity.END | Gravity.BOTTOM;
                break;
            case 2:
                setContentView(R.layout.activity_tooltip_gravity_ii);
                gravity = Gravity.START | Gravity.BOTTOM;
                break;
            case 3:
                setContentView(R.layout.activity_tooltip_gravity_iii);
                gravity = Gravity.START | Gravity.TOP;
                break;
            default:
                setContentView(R.layout.activity_tooltip_gravity_iv);
                gravity = Gravity.END | Gravity.TOP;
        }

        final Button button = findViewById(R.id.button);
        final TourGuide tourGuide = TourGuide.init(this)
                .setToolTip(new ToolTip()
                        .setTitle("Welcome!")
                        .setDescription("Click on Get Started to begin...")
                        .setBackgroundColor(Color.parseColor("#2980b9"))
                        .setTextColor(Color.parseColor("#FFFFFF"))
                        .setGravity(gravity)
                        .setShadow(true))
                .setPointer(null)
                .setOverlay(null)
                .playOn(button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tourGuide.cleanUp();
            }
        });
    }

}
