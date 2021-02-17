package cc.brainbook.android.tourguide.app;

import android.content.Intent;
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

public class ToolTipMeasureTestActivity extends AppCompatActivity {
    public static final String TOOLTIP_NUM = "tooltip_num";

    public TourGuide tourGuide;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        int tooltipNumber = intent.getIntExtra(TOOLTIP_NUM, 1);
        super.onCreate(savedInstanceState);

        int gravity;
        switch(tooltipNumber) {
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

        Button button = findViewById(R.id.button);

        tourGuide = TourGuide.init(this)
                .setPointer(new Pointer())
                .setOverlay(new Overlay())
                .setToolTip(new ToolTip()
                        .setTitle("Welcome!")
                        .setDescription("This is a really really long title....This is a really really long title....This is a really really long title....This is a really really long title....This is a really really long title....This is a really really long title....This is a really really long title....")
                        .setBackgroundColor(Color.parseColor("#2980b9"))
                        .setGravity(gravity)
                        .setShadow(true))

                .playOn(button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tourGuide.cleanUp();
            }
        });
    }
}
