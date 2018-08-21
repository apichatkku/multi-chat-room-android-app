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
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class FragmentChatBox extends Fragment implements View.OnClickListener {
    private static FragmentChatBox fragmentChatBox;

    private FragmentChatBoxListener listener;

    private RecyclerView rvChatBox;
    private LinearLayoutManager layoutManagerChatBox;
    private RecyclerView.Adapter adapterChatBox;

    private EditText etMsg;
    private Button btnSendMsg;

    public static FragmentChatBox newInstance() {
        if (fragmentChatBox == null) {
            fragmentChatBox = new FragmentChatBox();
        }
        return fragmentChatBox;
    }

    public FragmentChatBox() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat_box, container, false);

        rvChatBox = rootView.findViewById(R.id.rv_chat_box);
        rvChatBox.setHasFixedSize(true);
        layoutManagerChatBox = new LinearLayoutManager(this.getContext());
        layoutManagerChatBox.setStackFromEnd(true);
        rvChatBox.setLayoutManager(layoutManagerChatBox);

        etMsg = rootView.findViewById(R.id.et_msg);
        btnSendMsg = rootView.findViewById(R.id.btn_send);
        btnSendMsg.setOnClickListener(this);

        return rootView;
    }

    public void setRvChatBox(List<MsgBox> datasetChatBox) {
        adapterChatBox = new ChatBoxAdapter(this.getContext(), datasetChatBox);
        rvChatBox.setAdapter(adapterChatBox);
        //rvChatBox.setScrollingTouchSlop(datasetChatBox.size() - 1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                String msg = etMsg.getText().toString();
                etMsg.setText("");
                listener.onBtnSendMsgClick(msg);
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (FragmentChatBoxListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Must implement MyFragmentListener");
        }
    }

    public interface FragmentChatBoxListener {
        public void onBtnSendMsgClick(String msg);
    }
}
