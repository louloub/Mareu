package com.example.maru.view.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.maru.R;
import com.example.maru.service.model.Meeting;
import com.example.maru.service.model.MeetingJava;

import java.util.List;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.MeetingViewHolder>  {

    private Context mCtx;
    private List<MeetingJava> listMeeting;
    private MeetingJava mMeeting;

    public SimpleAdapter(Context mCtx, List<MeetingJava> listMeeting) {
        this.mCtx = mCtx;
        this.mMeeting = mMeeting;
        this.listMeeting = listMeeting;
    }

    @Override
    public MeetingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.main_item, null);
        return new MeetingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MeetingViewHolder holder, int position) {
        MeetingJava meeting = listMeeting.get(position);

        String subject = meeting.getSubject();
        List<String> listOfEmailOfParticipant = meeting.getListOfEmailOfParticipant();
        String room = meeting.getPlace();
        String hour = meeting.getHour();
        String date = meeting.getDate();

        holder.tvMeetingInformation.setText(subject);
    }

    @Override
    public int getItemCount() {return listMeeting.size();}

    class MeetingViewHolder extends RecyclerView.ViewHolder {

        TextView tvMeetingInformation, tvMeetingAddress;
        ImageView ivPhoto, ivFacebook, ivSoundcloud, ivBeatport, ivMixcloud, ivTwitter, ivResidentAdvisor, ivInstagram, ivSite;

        public MeetingViewHolder(View itemView) {
            super(itemView);

            tvMeetingInformation = itemView.findViewById(R.id.item_main_tv_meeting_information);
            tvMeetingAddress = itemView.findViewById(R.id.item_main_tv_participant_address);

            /*tvBio = itemView.findViewById(R.id.tvBio);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);
            ivFacebook = itemView.findViewById(R.id.ivFacebook);
            ivSoundcloud = itemView.findViewById(R.id.ivSoundcloud);
            ivBeatport = itemView.findViewById(R.id.ivBeatport);
            ivMixcloud = itemView.findViewById(R.id.ivMixcloud);
            ivTwitter = itemView.findViewById(R.id.ivTwitter);
            ivResidentAdvisor = itemView.findViewById(R.id.ivResidentAdvisor);
            ivInstagram = itemView.findViewById(R.id.ivInstagram);
            ivSite = itemView.findViewById(R.id.ivSite);*/

        }
    }
}