package com.example.whatsapp.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.whatsapp.fragment.calls;
import com.example.whatsapp.fragment.chats;
import com.example.whatsapp.fragment.status;

public class fragmentAdapter extends FragmentPagerAdapter {
    public fragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new chats();
            case 1:
                return new status();
            case 2:
                return new calls();
            default:
                return new chats();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position==0) {
            title = "CHATS";
        }
        if (position==1) {
            title = "STATUS";
        }
        if (position==2) {
            title = "CALLS";
        }
        return title;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
