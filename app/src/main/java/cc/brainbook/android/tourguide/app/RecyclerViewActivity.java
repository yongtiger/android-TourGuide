package cc.brainbook.android.tourguide.app;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cc.brainbook.android.tourguide.Overlay;
import cc.brainbook.android.tourguide.TourGuide;
import cc.brainbook.android.tourguide.app.adapter.RecyclerViewAdapter;

public class RecyclerViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recycler_view);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        TourGuide tourGuide = TourGuide.init(this)
                .setOverlay(new Overlay().setBackgroundColor(Color.parseColor("#55000000")));

        recyclerView.setAdapter(new RecyclerViewAdapter(new RecyclerViewAdapter.LaunchTourGuideCallback() {
            @Override
            public void invoke(View view) {
                tourGuide.playOn(view);
            }
        }, new RecyclerViewAdapter.DismissTourGuideCallback() {
            @Override
            public void invoke() {
                tourGuide.cleanUp();
            }
        }));
    }

}
