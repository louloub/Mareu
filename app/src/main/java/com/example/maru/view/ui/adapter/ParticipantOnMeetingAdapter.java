package com.example.maru.view.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.maru.R;

import java.util.ArrayList;

public class ParticipantOnMeetingAdapter extends RecyclerView.Adapter<ParticipantOnMeetingAdapter.ParticpantMeetingViewHolder> {

    private Context mCtx;
    private ArrayList<String> listParticipantMeeting;

    public ParticipantOnMeetingAdapter(Context mCtx, ArrayList<String> listParticipantMeeting) {
        this.mCtx = mCtx;
        this.listParticipantMeeting = listParticipantMeeting;
    }

    @Override
    public ParticpantMeetingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.participant_list_in_meeting, null);
        return new ParticpantMeetingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ParticpantMeetingViewHolder holder, int position) {
        // maint_activity_tv_participant

        String participant = listParticipantMeeting.get(position);

        // String artisteNameArtisteEvent = artistes.getName();
        holder.tvParticipantName.setText(participant);

        // Color Background if artiste is on the website (& clickable)
        // loadArtistesForChangeBackground(artisteNameArtisteEvent,holder);


        // String participantMeeting = listParticipantMeeting.get(position);

        /*String subject = meeting.getSubject();
        ArrayList<String> listOfEmailOfParticipant = meeting.getListOfEmailOfParticipant();
        String room = meeting.getRoom();
        String hour = meeting.getHour();
        String date = meeting.getDate();

        holder.tvMeetingInformation.setText(subject);
        holder.tvMeetingHour.setText(hour);
        holder.tvMeetingRoom.setText(room);

        ParticipantOnMeetingAdapter artistesAdapter = new ParticipantOnMeetingAdapter(mCtx,artistesFromList);
        holder.recyclerViewArtistesEventList.setAdapter(artistesAdapter);*/


        // holder.tvMeetingParticipant.setText(listOfEmailOfParticipant.get(position));

        /*for (int i = 0; i < listOfEmailOfParticipant.size(); i++) {
            String participantMail = String.valueOf(listOfEmailOfParticipant.get(i));
            holder.tvMeetingParticipant.setText(participantMail);
        }*/

        // holder.tvMeetingParticipant.setText((CharSequence) listOfEmailOfParticipant);

    }

    @Override
    public int getItemCount() {
        return listParticipantMeeting.size();
    }

    class ParticpantMeetingViewHolder extends RecyclerView.ViewHolder {

        TextView tvParticipantName;

        public ParticpantMeetingViewHolder(View itemView) {
            super(itemView);

            tvParticipantName = itemView.findViewById(R.id.main_activity_tv_participant);

            /*tvMeetingInformation = itemView.findViewById(R.id.create_meeting_tv_subject_meeting);
            tvMeetingHour = itemView.findViewById(R.id.create_meeting_tv_hour_meeting);
            tvMeetingRoom = itemView.findViewById(R.id.create_meeting_tv_room_meeting);
            tvMeetingParticipant = itemView.findViewById(R.id.create_meeting_tv_participant_meeting);*/

        }
    }


}
