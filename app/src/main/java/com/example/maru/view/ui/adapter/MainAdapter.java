package com.example.maru.view.ui.adapter;

import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.ImageViewCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maru.R;
import com.example.maru.utility.MeetingManager;
import com.example.maru.view.ViewModelFactory;
import com.example.maru.view.ui.MainViewModel;
import com.example.maru.view.ui.model.PropertyUiModel;

import static android.content.ContentValues.TAG;

public class MainAdapter extends ListAdapter<PropertyUiModel, MainAdapter.MainViewHolder> {

    // private MainViewModel mViewModel;

    public MainAdapter() {
        super(new DiffCallback());
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item_meeting, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.bind(getItem(position));
        // mViewModel = new ViewModelProvider((ViewModelStoreOwner) this, ViewModelFactory.getInstance()).get(MainViewModel.class);
    }

    static class MainViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewInformation;
        private final TextView textViewParticipant;
        private final ImageView ivDeleteMeeting;

        MainViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewInformation = itemView.findViewById(R.id.meeting_tv_information);
            textViewParticipant = itemView.findViewById(R.id.meeting_tv_participant_meeting);
            ivDeleteMeeting = itemView.findViewById(R.id.meeting_iv_delete_meeting);
        }

        void bind(PropertyUiModel model) {

            textViewInformation.setText(
                    model.getSubject() + " à " + model.getHour() + "h le " + model.getDate() + " salle n° " + model.getRoom());
            textViewInformation.setTypeface(null, Typeface.BOLD);
            textViewInformation.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);

            textViewParticipant.setText(model.getListOfEmailOfParticipant().toString());

            ivDeleteMeeting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MeetingManager.getInstance().deleteMeeting(getLayoutPosition());
                }
            });
        }
    }

    private static class DiffCallback extends DiffUtil.ItemCallback<PropertyUiModel> {

        @Override
        public boolean areItemsTheSame(@NonNull PropertyUiModel oldItem, @NonNull PropertyUiModel newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull PropertyUiModel oldItem, @NonNull PropertyUiModel newItem) {
            return oldItem.equals(newItem);
        }
    }
}

