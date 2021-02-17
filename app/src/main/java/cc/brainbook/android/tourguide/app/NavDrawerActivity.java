package cc.brainbook.android.tourguide.app;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import cc.brainbook.android.tourguide.Overlay;
import cc.brainbook.android.tourguide.Pointer;
import cc.brainbook.android.tourguide.Sequence;
import cc.brainbook.android.tourguide.ToolTip;
import cc.brainbook.android.tourguide.TourGuide;

public class NavDrawerActivity extends AppCompatActivity {
    TextView mTextView1;
    NavDrawerActivity mActivity;
    Sequence mSequence;
    TourGuide mTutorialHandler;
    DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.activity_nav_drawer);

        /* get views from xml */
        mTextView1 = (TextView)findViewById(R.id.text_view1);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        /* setup toolbar */
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Nav Drawer Example");
        mTutorialHandler = TourGuide.init(mActivity).with(TourGuide.Technique.CLICK)
                .setPointer(new Pointer())
                .setToolTip(new ToolTip().setTitle(null).setDescription("hello world"))
                .setOverlay(new Overlay().setBackgroundColor(Color.parseColor("#66FF0000")));


        final ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,  mDrawerLayout, toolbar, R.string.drawer_open_string, R.string.drawer_close_string){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                /* We need call playOn only after the drawer is opened,
                   so that TourGuide knows the updated location of the targetted view */
                mTutorialHandler.playOn(mTextView1);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        /* setup clean up code */
        mTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTutorialHandler.cleanUp();
                mDrawerLayout.closeDrawers();
            }
        });

        final ViewTreeObserver viewTreeObserver = mTextView1.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mTextView1.getViewTreeObserver().removeGlobalOnLayoutListener(this);// make sure this only run once
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

}