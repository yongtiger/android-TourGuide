package cc.brainbook.android.tourguide.app.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import cc.brainbook.android.tourguide.app.AdjustPaddingOverlayActivity;
import cc.brainbook.android.tourguide.app.BasicActivity;
import cc.brainbook.android.tourguide.app.ManualSequenceActivity;
import cc.brainbook.android.tourguide.app.MultipleToolTipActivity;
import cc.brainbook.android.tourguide.app.NavDrawerActivity;
import cc.brainbook.android.tourguide.app.NoOverlayActivity;
import cc.brainbook.android.tourguide.app.NoPointerActivity;
import cc.brainbook.android.tourguide.app.NoPointerNoToolTipActivity;
import cc.brainbook.android.tourguide.app.OverlayCustomizationActivity;
import cc.brainbook.android.tourguide.app.R;
import cc.brainbook.android.tourguide.app.RecyclerViewActivity;
import cc.brainbook.android.tourguide.app.RoundedRectangleOverlayActivity;
import cc.brainbook.android.tourguide.app.ToolTipCustomizationActivity;
import cc.brainbook.android.tourguide.app.ToolTipGravityActivity;
import cc.brainbook.android.tourguide.app.ToolbarActivity;

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int numOfRow = 20;
    private void startActivity(Context context, Intent intent) {
        ContextCompat.startActivity(context, intent, null);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        decorateBackground(holder.itemView, position);

        switch (position) {
            case 0:
                ((ViewHolder) holder).textView.setText("Basic Activity");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Context context = v.getContext();
                        startActivity(context, new Intent(context, BasicActivity.class));
                    }
                });

                break;
            case 1:
                ((ViewHolder) holder).textView.setText("Pointer: color");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Context context = v.getContext();
                        final Intent intent = new Intent(context, BasicActivity.class);
                        intent.putExtra(BasicActivity.COLOR_DEMO, true);
                        startActivity(context, intent);
                    }
                });

                break;
            case 2:
                ((ViewHolder) holder).textView.setText("Pointer: gravity");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Context context = v.getContext();
                        final Intent intent = new Intent(context, BasicActivity.class);
                        intent.putExtra(BasicActivity.GRAVITY_DEMO, true);
                        startActivity(context, intent);
                    }
                });

                break;
            case 3:
                ((ViewHolder) holder).textView.setText("Toolbar Example");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Context context = v.getContext();
                        final Intent intent = new Intent(context, ToolbarActivity.class);
                        intent.putExtra(ToolbarActivity.STATUS_BAR, true);
                        startActivity(context, intent);
                    }
                });

                break;
            case 4:
                ((ViewHolder) holder).textView.setText("Toolbar Example\n(no status bar)");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Context context = v.getContext();
                        final Intent intent = new Intent(context, ToolbarActivity.class);
                        intent.putExtra(ToolbarActivity.STATUS_BAR, false);
                        startActivity(context, intent);
                    }
                });

                break;
            case 5:
                ((ViewHolder) holder).textView.setText("ToolTip Gravity I");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Context context = v.getContext();
                        final Intent intent = new Intent(context, ToolTipGravityActivity.class);
                        intent.putExtra(ToolTipGravityActivity.TOOLTIP_NUM, 1);
                        startActivity(context, intent);
                    }
                });

                break;
            case 6:
                ((ViewHolder) holder).textView.setText("ToolTip Gravity II");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Context context = v.getContext();
                        final Intent intent = new Intent(context, ToolTipGravityActivity.class);
                        intent.putExtra(ToolTipGravityActivity.TOOLTIP_NUM, 2);
                        startActivity(context, intent);
                    }
                });

                break;
            case 7:
                ((ViewHolder) holder).textView.setText("ToolTip Gravity III");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Context context = v.getContext();
                        final Intent intent = new Intent(context, ToolTipGravityActivity.class);
                        intent.putExtra(ToolTipGravityActivity.TOOLTIP_NUM, 3);
                        startActivity(context, intent);
                    }
                });

                break;
            case 8:
                ((ViewHolder) holder).textView.setText("ToolTip Gravity IV");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Context context = v.getContext();
                        final Intent intent = new Intent(context, ToolTipGravityActivity.class);
                        intent.putExtra(ToolTipGravityActivity.TOOLTIP_NUM, 4);
                        startActivity(context, intent);
                    }
                });

                break;
            case 9:
                ((ViewHolder) holder).textView.setText("ToolTip Customization");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Context context = v.getContext();
                        startActivity(context, new Intent(context, ToolTipCustomizationActivity.class));
                    }
                });

                break;
            case 10:
                ((ViewHolder) holder).textView.setText("Multiple ToolTip");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Context context = v.getContext();
                        startActivity(context, new Intent(context, MultipleToolTipActivity.class));
                    }
                });

                break;
            case 11:
                ((ViewHolder) holder).textView.setText("Overlay Customization");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Context context = v.getContext();
                        startActivity(context, new Intent(context, OverlayCustomizationActivity.class));
                    }
                });

                break;
            case 12:
                ((ViewHolder) holder).textView.setText("ToolTip & Overlay only, no Pointer");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Context context = v.getContext();
                        startActivity(context, new Intent(context, NoPointerActivity.class));
                    }
                });

                break;
            case 13:
                ((ViewHolder) holder).textView.setText("Overlay only, no Tooltip, no Pointer");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Context context = v.getContext();
                        startActivity(context, new Intent(context, NoPointerNoToolTipActivity.class));
                    }
                });

                break;
            case 14:
                ((ViewHolder) holder).textView.setText("ToolTip & Pointer only, no Overlay");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Context context = v.getContext();
                        startActivity(context, new Intent(context, NoOverlayActivity.class));
                    }
                });

                break;
            case 15:
                ((ViewHolder) holder).textView.setText("Button Tour (Manual)");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Context context = v.getContext();
                        startActivity(context, new Intent(context, ManualSequenceActivity.class));
                    }
                });

                ((ViewHolder) holder).infoIcon.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).infoIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(v.getContext())
                                .setTitle("Button Tour")
                                .setMessage("- Button Tour example shows a sequence of TourGuide running on different buttons. \n- The method of proceeding to the next TourGuide is to press on the button itself. \n- This is suitable when you actually want the user to click on the button during the Tour.\n")
                                .show();
                    }
                });

                break;
            case 16:
                ((ViewHolder) holder).textView.setText("Navigational Drawer");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Context context = v.getContext();
                        startActivity(context, new Intent(context, NavDrawerActivity.class));
                    }
                });

                break;
            case 17:
                ((ViewHolder) holder).textView.setText("Rounded Rectangle Overlay");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Context context = v.getContext();
                        startActivity(context, new Intent(context, RoundedRectangleOverlayActivity.class));
                    }
                });

                break;
            case 18:
                ((ViewHolder) holder).textView.setText("Adjust Overlay Padding");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Context context = v.getContext();
                        startActivity(context, new Intent(context, AdjustPaddingOverlayActivity.class));
                    }
                });

                break;
            default:
                ((ViewHolder) holder).textView.setText("Recycler View");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Context context = v.getContext();
                        startActivity(context, new Intent(context, RecyclerViewActivity.class));
                    }
                });
            }
    }

    @Override
    public int getItemCount() {
        return numOfRow;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textView;
        final ImageView infoIcon;

        ViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.text_view);
            infoIcon = (ImageView) itemView.findViewById(R.id.info_icon);
        }
    }

    private void decorateBackground(View view, int position) {
        view.setBackgroundColor(ContextCompat.getColor(view.getContext(), position % 2 == 0 ? R.color.blue1 : R.color.blue2));
    }
}
