package com.example.maru.view.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maru.R;
import com.example.maru.view.ui.model.PropertyUiModel;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;

public class MainAdapter extends ListAdapter<PropertyUiModel, MainAdapter.MainViewHolder> {

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
    }

    static class MainViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewInformation;
        private final TextView textViewParticipant;

        MainViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewInformation = itemView.findViewById(R.id.meeting_tv_information);
            textViewParticipant = itemView.findViewById(R.id.meeting_tv_participant_meeting);
        }

        void bind(PropertyUiModel model) {
            
            textViewInformation.setText(model.getSubject() + " à " + model.getHour() + " le " + model.getDate() + " salle n° " + model.getRoom());
            textViewParticipant.setText(model.getListOfEmailOfParticipant().toString());
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

