package cc.brainbook.android.tourguide.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import cc.brainbook.android.tourguide.app.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public interface LaunchTourGuideCallback {
        void invoke(View view);
    }
    public interface DismissTourGuideCallback {
        void invoke();
    }
    LaunchTourGuideCallback launchTourGuideCallback;
    DismissTourGuideCallback dismissTourGuideCallback;

    public RecyclerViewAdapter(LaunchTourGuideCallback launchTourGuideCallback, DismissTourGuideCallback dismissTourGuideCallback) {
        this.launchTourGuideCallback = launchTourGuideCallback;
        this.dismissTourGuideCallback = dismissTourGuideCallback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler_view, parent, false);
        return new DemoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((DemoViewHolder) holder).rowTextView.setText(String.format("row #%d", position));

        if (position == 1) {
            launchTourGuideCallback.invoke(((DemoViewHolder) holder).rowTextView);

            ((DemoViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissTourGuideCallback.invoke();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }


    static class DemoViewHolder extends RecyclerView.ViewHolder {
        final TextView rowTextView;

        DemoViewHolder(View itemView) {
            super(itemView);

            rowTextView = (TextView) itemView.findViewById(R.id.row_text_view);
        }
    }

}
