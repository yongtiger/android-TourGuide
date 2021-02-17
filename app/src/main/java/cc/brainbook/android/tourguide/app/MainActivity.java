package cc.brainbook.android.tourguide.app;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import cc.brainbook.android.tourguide.app.adapter.MainAdapter;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tour_guide_demo_main);

        RecyclerView recyclerView = findViewById(R.id.main_recycler_view);
        recyclerView.setAdapter(new MainAdapter());
    }

}
