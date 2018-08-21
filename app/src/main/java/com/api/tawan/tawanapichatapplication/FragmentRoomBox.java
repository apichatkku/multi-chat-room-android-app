package com.api.tawan.tawanapichatapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class FragmentRoomBox extends Fragment {
    private static FragmentRoomBox fragmentRoomBox;

    private FragmentRoomBoxListener listener;

    private RecyclerView rvRoomBox;
    private LinearLayoutManager layoutManagerRoomBox;
    private RecyclerView.Adapter adapterRoomBox;

    public static FragmentRoomBox newInstance() {
        if (fragmentRoomBox == null) {
            fragmentRoomBox = new FragmentRoomBox();
        }
        return fragmentRoomBox;
    }

    public FragmentRoomBox() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_room_box, container, false);

        rvRoomBox = rootView.findViewById(R.id.rv_room_box);
        rvRoomBox.setHasFixedSize(true);
        layoutManagerRoomBox = new LinearLayoutManager(this.getContext());
        rvRoomBox.setLayoutManager(layoutManagerRoomBox);

        return rootView;
    }

    public void setRvRoomBox(List<RoomBox> datasetRoomBoxes) {
        adapterRoomBox = new RoomBoxAdapter(this.getContext(), datasetRoomBoxes);
        rvRoomBox.setAdapter(adapterRoomBox);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (FragmentRoomBoxListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Must implement MyFragmentListener");
        }
    }

    public interface FragmentRoomBoxListener {
        public void onBtnSendMsgClick(String msg);
    }
}
