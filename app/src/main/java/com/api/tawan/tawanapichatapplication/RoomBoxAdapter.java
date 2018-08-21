package com.api.tawan.tawanapichatapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

class RoomBoxAdapter extends RecyclerView.Adapter<RoomBoxAdapter.ViewHolder>{
    private List<RoomBox> roomBoxes;
    private Context roomContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textId;
        public TextView textName;
        public TextView textNumMember;
        public ImageView imgLock;

        public ViewHolder(View view) {
            super(view);

            textId = view.findViewById(R.id.text_id);
            textName = view.findViewById(R.id.text_name);
            textNumMember = view.findViewById(R.id.text_num_member);
            imgLock = view.findViewById(R.id.img_lock);
        }
    }

    public RoomBoxAdapter(Context context, List<RoomBox> dataset) {
        roomBoxes = dataset;
        roomContext = context;
    }

    @Override
    public RoomBoxAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(roomContext)
                .inflate(R.layout.recycler_view_room_row, parent, false);

        RoomBoxAdapter.ViewHolder viewHolder = new RoomBoxAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RoomBoxAdapter.ViewHolder viewHolder, int position) {
        RoomBox roomBox = roomBoxes.get(position);

        viewHolder.textId.setText(roomBox.getId());
        viewHolder.textName.setText(roomBox.getName());
        viewHolder.textNumMember.setText(roomBox.getNumMember()+"");
        Log.e("ROOMMM", roomBox.getLock()+"");
        if(!roomBox.getLock()){
            viewHolder.imgLock.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return roomBoxes.size();
    }
}
