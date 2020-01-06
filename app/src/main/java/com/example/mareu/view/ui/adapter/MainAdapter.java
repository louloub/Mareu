package com.example.mareu.view.ui.adapter;

import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.view.ui.model.MeetingUiModel;

import java.util.Locale;

public class MainAdapter extends ListAdapter<MeetingUiModel, MainAdapter.MainViewHolder> {

    private final CallbackListener callback;

    public MainAdapter(CallbackListener callback) {
        super(new DiffCallback());
        this.callback = callback;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item_meeting, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.bind(getItem(position), callback);
    }

    public interface CallbackListener {
        void onMeetingClicked(int meetingId);
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

        void bind(final MeetingUiModel model, final CallbackListener callbackListener) {

            String hourAndMinutesString = model.getHour();
            int iend = hourAndMinutesString.indexOf("h");

            String minutesString = hourAndMinutesString.substring(iend+1);
            String hourString = hourAndMinutesString.substring(0 , iend);

            int minutesInt = Integer.parseInt(minutesString);
            String minutesStringFormatted = String.format(Locale.FRANCE,"%02d", minutesInt);

            String textWithAllInformationAboutMeeting = model.getSubject() + " à " + hourString + "h" + minutesStringFormatted + " le "
                    + model.getDate() + " salle n° " + model.getRoom();

            textViewInformation.setText(textWithAllInformationAboutMeeting);

            textViewInformation.setTypeface(null, Typeface.BOLD);
            textViewInformation.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);

            textViewParticipant.setText(model.getListOfEmailOfParticipant());

            ivDeleteMeeting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    callbackListener.onMeetingClicked(model.getId());
                }
            });
        }
    }

    private static class DiffCallback extends DiffUtil.ItemCallback<MeetingUiModel> {

        @Override
        public boolean areItemsTheSame(@NonNull MeetingUiModel oldItem, @NonNull MeetingUiModel newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull MeetingUiModel oldItem, @NonNull MeetingUiModel newItem) {
            return oldItem.equals(newItem);
        }
    }
}