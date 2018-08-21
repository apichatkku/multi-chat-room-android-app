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

public class FragmentMemberBox extends Fragment {
    private static FragmentMemberBox fragmentMemberBox;

    private FragmentMemberBoxListener listener;

    private RecyclerView rvMemberBox;
    private LinearLayoutManager layoutManagerMemberBox;
    private RecyclerView.Adapter adapterMemberBox;

    public static FragmentMemberBox newInstance() {
        if (fragmentMemberBox == null) {
            fragmentMemberBox = new FragmentMemberBox();
        }
        return fragmentMemberBox;
    }

    public FragmentMemberBox() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_member_box, container, false);

        rvMemberBox = rootView.findViewById(R.id.rv_member_box);
        rvMemberBox.setHasFixedSize(true);
        layoutManagerMemberBox = new LinearLayoutManager(this.getContext());
        rvMemberBox.setLayoutManager(layoutManagerMemberBox);

        return rootView;
    }

    public void setRvMemberBox(List<MemberBox> datasetMemberBoxes) {
        adapterMemberBox = new MemberBoxAdapter(this.getContext(), datasetMemberBoxes);
        rvMemberBox.setAdapter(adapterMemberBox);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (FragmentMemberBoxListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Must implement MyFragmentListener");
        }
    }

    public interface FragmentMemberBoxListener {
        public void onBtnSendMsgClick(String msg);
    }
}
