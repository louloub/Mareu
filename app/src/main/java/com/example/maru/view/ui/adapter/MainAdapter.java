package com.example.maru.view.ui.adapter;

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

import com.example.maru.R;
import com.example.maru.service.model.MeetingJava;
import com.example.maru.utility.MeetingManager;
import com.example.maru.view.ui.model.PropertyUiModel;

import org.threeten.bp.LocalTime;

import java.util.List;

public class MainAdapter extends ListAdapter<PropertyUiModel, MainAdapter.MainViewHolder> {

    private CallbackListener callback;

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
        // mViewModel = new ViewModelProvider((ViewModelStoreOwner) this, ViewModelFactory.getInstance()).get(MainViewModel.class);
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

        void bind(final PropertyUiModel model, final CallbackListener callbackListener) {

            // TODO : retrive selected hour & minutes, not minute rightNow
            /*String str = model.getHour();
            int i = Integer.parseInt(str);*/
            // String localMinutesString = String.format("%02d", i);

            List<MeetingJava> list = MeetingManager.getInstance().getMeeting();

            // TODO : work 22/11/19
            /*textViewInformation.setText(
                    model.getSubject() + " à " + model.getHour() + " le "
                            + model.getDate() + " salle n° " + model.getRoom());*/

            // TODO : retrive minutes in hour like line 208 in CreateMeetingActivityJava

            String hourAndMinutesString = model.getHour();
            int iend = hourAndMinutesString.indexOf("h"); //this finds the first occurrence of "."

            // String hourString = hourAndMinutesString.substring(hourAndMinutesString.indexOf("h")-2) ;
            String minutesString = hourAndMinutesString.substring(iend+1);
            String hourString = hourAndMinutesString.substring(0 , iend);

            int minutesInt = Integer.parseInt(minutesString);
            String minutesStringFormatted = String.format("%02d", minutesInt);

            /*if (iend != -1)
            {
                subString = hourAndMinutesString.substring(0 , iend);
            }*/

            // String curTime = String.format("%02d:%02d", hrs, mnts);
            // int i = Integer.parseInt(model.getHour());
            // String formatted = String.format("%02d", i);

            textViewInformation.setText(
                    model.getSubject() + " à " + hourString + "h" + minutesStringFormatted + " le "
                            + model.getDate() + " salle n° " + model.getRoom());

            textViewInformation.setTypeface(null, Typeface.BOLD);
            textViewInformation.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);

            textViewParticipant.setText(model.getListOfEmailOfParticipant().toString());

            ivDeleteMeeting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    callbackListener.onMeetingClicked(model.getId());

                    MeetingManager.getInstance().deleteMeeting(getLayoutPosition());
                }
            });
        }
    }

    // TODO : Appeler quand on fait un submilist (dans observe)
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