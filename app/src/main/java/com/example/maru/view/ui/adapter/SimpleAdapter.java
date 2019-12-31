package com.example.maru.view.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maru.R;
import com.example.maru.service.model.Meeting;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.MeetingViewHolder> {

    private Context mCtx;
    private ArrayList<Meeting> listMeeting;

    public SimpleAdapter(Context mCtx, ArrayList<Meeting> listMeeting) {
        this.mCtx = mCtx;
        this.listMeeting = listMeeting;
    }

    @Override
    public MeetingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.main_item_meeting, parent, false);
        return new MeetingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MeetingViewHolder holder, int position) {
        Meeting meeting = listMeeting.get(position);

        String subject = meeting.getSubject();
        List<String> listOfEmailOfParticipant = meeting.getListOfEmailOfParticipant();
        int room = meeting.getRoom();
        String hour = meeting.getHour();
        LocalDate date = meeting.getDate();

        holder.tvMeetingInformation.setText(subject + " à " + hour + " le " + date + " salle n° " + room);

        // Delete button
        holder.btDeleteMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listMeeting.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), listMeeting.size());
                Toast.makeText(v.getContext(), "Réunion supprimée", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMeeting.size();
    }

    class MeetingViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView btDeleteMeeting;
        TextView tvMeetingInformation;
        TextView tvParticipantMeeting;

        public MeetingViewHolder(View itemView) {
            super(itemView);

            btDeleteMeeting = itemView.findViewById(R.id.meeting_iv_delete_meeting);
            tvMeetingInformation = itemView.findViewById(R.id.meeting_tv_information);
            tvParticipantMeeting = itemView.findViewById(R.id.meeting_tv_participant_meeting);
        }
    }
}