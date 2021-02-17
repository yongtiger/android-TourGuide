package cc.brainbook.android.tourguide.app;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import cc.brainbook.android.tourguide.Overlay;
import cc.brainbook.android.tourguide.Pointer;
import cc.brainbook.android.tourguide.ToolTip;
import cc.brainbook.android.tourguide.TourGuide;

public class ToolbarActivity extends AppCompatActivity {
    public final static String STATUS_BAR = "status_bar";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        final boolean hasStatusBar = getIntent().getBooleanExtra(STATUS_BAR, false);

        /* Get parameters from main activity */
        if (!hasStatusBar) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_toolbar);

        final  Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        getMenuInflater().inflate(R.menu.menu_demo_main, menu);

        // We need to get the menu item as a View in order to work with TourGuide
        final MenuItem menuItem = menu.getItem(0);
        final ImageView button = (ImageView) menuItem.getActionView();

        // just adding some padding to look better
        final float density = getResources().getDisplayMetrics().density;
        final int padding = (int) (5 * density);
        button.setPadding(padding, padding, padding, padding);

        // set an image
        button.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_dialog_email));

        final ToolTip toolTip = new ToolTip()
                .setTitle("Welcome!")
                .setDescription("Click on Get Started to begin...")
                .setGravity(Gravity.START | Gravity.BOTTOM);

        final TourGuide tourGuide = TourGuide.init(this).with(TourGuide.Technique.CLICK)
                .motionType(TourGuide.MotionType.CLICK_ONLY)
                .setPointer(new Pointer())
                .setToolTip(toolTip)
                .setOverlay(new Overlay())
                .playOn(button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tourGuide.cleanUp();
            }
        });

        return true;
    }
}
