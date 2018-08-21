package com.api.tawan.tawanapichatapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MemberBoxAdapter extends RecyclerView.Adapter<MemberBoxAdapter.ViewHolder>{
    private List<MemberBox> memberBoxes;
    private Context memberContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textId;

        public ViewHolder(View view) {
            super(view);

            textId = view.findViewById(R.id.text_id);
        }
    }

    public MemberBoxAdapter(Context context, List<MemberBox> dataset) {
        memberBoxes = dataset;
        memberContext = context;
    }

    @Override
    public MemberBoxAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(memberContext)
                .inflate(R.layout.recycler_view_member_row, parent, false);

        MemberBoxAdapter.ViewHolder viewHolder = new MemberBoxAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MemberBoxAdapter.ViewHolder viewHolder, int position) {
        MemberBox memberBox = memberBoxes.get(position);

        viewHolder.textId.setText(memberBox.getId());
    }

    @Override
    public int getItemCount() {
        return memberBoxes.size();
    }
}
