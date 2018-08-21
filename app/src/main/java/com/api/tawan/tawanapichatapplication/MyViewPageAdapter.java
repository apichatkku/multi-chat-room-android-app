package com.api.tawan.tawanapichatapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyViewPageAdapter extends FragmentPagerAdapter {
    private final int PAGE_COUNT = 3;

    public MyViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0)
            return FragmentChatBox.newInstance();
        else if(position == 1)
            return FragmentRoomBox.newInstance();
        else if(position == 2)
            return FragmentMemberBox.newInstance();
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
