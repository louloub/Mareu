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

import java.util.ArrayList;
import java.util.List;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.MeetingViewHolder>  {

    private Context mCtx;
    private ArrayList<MeetingJava> listMeeting;
    // private MeetingJava mMeeting;

    public SimpleAdapter(Context mCtx, ArrayList<MeetingJava> listMeeting) {
        this.mCtx = mCtx;
        // this.mMeeting = mMeeting;
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
        String room = meeting.getPlace();
        String hour = meeting.getHour();
        String date = meeting.getDate();

        holder.tvMeetingInformation.setText(subject);
        holder.tvMeetingAddress.setText(room);
    }

    @Override
    public int getItemCount() {return listMeeting.size();}

    class MeetingViewHolder extends RecyclerView.ViewHolder {

        TextView tvMeetingInformation, tvMeetingAddress;

        public MeetingViewHolder(View itemView) {
            super(itemView);

            /*tvMeetingInformation = itemView.findViewById(R.id.test1);
            tvMeetingAddress = itemView.findViewById(R.id.test2);*/

            tvMeetingInformation = itemView.findViewById(R.id.test3);
            tvMeetingAddress = itemView.findViewById(R.id.test4);

        /*    tvMeetingInformation = itemView.findViewById(R.id.create_meeting_tiet_subject);
            tvMeetingAddress = itemView.findViewById(R.id.create_meeting_teit_listOfParticipant);
        */
        }
    }
}