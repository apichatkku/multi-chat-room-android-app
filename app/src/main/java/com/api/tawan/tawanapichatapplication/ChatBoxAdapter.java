package com.api.tawan.tawanapichatapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ChatBoxAdapter extends RecyclerView.Adapter<ChatBoxAdapter.ViewHolder>{
    private List<MsgBox> msgBoxes;
    private Context msgContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textId;
        public TextView textMsg;

        public ViewHolder(View view) {
            super(view);

            textId = view.findViewById(R.id.text_id);
            textMsg = view.findViewById(R.id.text_msg);
        }
    }

    public ChatBoxAdapter(Context context, List<MsgBox> dataset) {
        msgBoxes = dataset;
        msgContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(msgContext)
                .inflate(R.layout.recycler_view_msg_row, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        MsgBox msgBox = msgBoxes.get(position);

        viewHolder.textId.setText(msgBox.getId());
        viewHolder.textMsg.setText(msgBox.getMsg());
    }

    @Override
    public int getItemCount() {
        return msgBoxes.size();
    }
}
