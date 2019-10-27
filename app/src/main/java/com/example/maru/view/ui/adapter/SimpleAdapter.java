package com.example.maru.view.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maru.R;
import com.example.maru.service.model.MeetingJava;

import java.util.ArrayList;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.MeetingViewHolder>  {

    private Context mCtx;
    private ArrayList<MeetingJava> listMeeting;

    public SimpleAdapter(Context mCtx, ArrayList<MeetingJava> listMeeting) {
        this.mCtx = mCtx;
        this.listMeeting = listMeeting;
    }

    @Override
    public MeetingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.main_item_test, null);
        return new MeetingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MeetingViewHolder holder, int position) {
        MeetingJava meeting = listMeeting.get(position);

        String subject = meeting.getSubject();
        ArrayList<String> listOfEmailOfParticipant = meeting.getListOfEmailOfParticipant();
        String room = meeting.getRoom();
        String hour = meeting.getHour();
        String date = meeting.getDate();

        holder.tvMeetingSubject.setText(subject);
        holder.tvMeetingHour.setText(hour);
        holder.tvMeetingRoom.setText(room);

        ParticipantOnMeetingAdapter participantMeetingAdapter = new ParticipantOnMeetingAdapter(mCtx,listOfEmailOfParticipant);
        holder.rvParticipantMeeting.setAdapter(participantMeetingAdapter);

        // holder.tvMeetingParticipant.setText(listOfEmailOfParticipant.get(position));

        /*for (int i = 0; i < listOfEmailOfParticipant.size(); i++) {
            String participantMail = String.valueOf(listOfEmailOfParticipant.get(i));
            holder.tvMeetingParticipant.setText(participantMail);
        }*/

        // holder.tvMeetingParticipant.setText((CharSequence) listOfEmailOfParticipant);

    }

    @Override
    public int getItemCount() {return listMeeting.size();}

    class MeetingViewHolder extends RecyclerView.ViewHolder {

        TextView tvMeetingSubject, tvMeetingHour, tvMeetingRoom, tvMeetingParticipant;
        RecyclerView rvParticipantMeeting;

        public MeetingViewHolder(View itemView) {
            super(itemView);

            tvMeetingSubject = itemView.findViewById(R.id.create_meeting_tv_subject_meeting);
            tvMeetingHour = itemView.findViewById(R.id.create_meeting_tv_hour_meeting);
            tvMeetingRoom = itemView.findViewById(R.id.create_meeting_tv_room_meeting);
            // tvMeetingParticipant = itemView.findViewById(R.id.create_meeting_tv_participant_meeting);
            rvParticipantMeeting = itemView.findViewById(R.id.create_meeting_rc_participant_meeting);
            // Affichage horizontal des artistes dans la liste des événements
            rvParticipantMeeting.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));

            // Affichage d'une bar entre chaque artistes de la liste
            // recyclerViewArtistesEventList.addItemDecoration(new DividerItemDecoration(itemView.getContext(), DividerItemDecoration.HORIZONTAL));

        }
    }
}