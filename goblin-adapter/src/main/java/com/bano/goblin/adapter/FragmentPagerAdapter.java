package com.bano.goblin.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * Created by Alexandre on 01/03/2017.
 */

public class FragmentPagerAdapter extends BaseFragmentStatePagerAdapter {

    private final List<Fragment> fragments;
    private final List<String> titles;

    public FragmentPagerAdapter(@NonNull FragmentManager fm, @NonNull List<Fragment> fragments, @Nullable String[] titles) {
        super(fm);
        this.fragments = fragments;
        if(titles == null){
            this.titles = null;
            return;
        }
        if(titles.length != fragments.size()) throw new IllegalArgumentException("Tile array size is different of fragments size");
        this.titles = new ArrayList<>(Arrays.asList(titles));
    }

    public void add(@NonNull Fragment fragment, @Nullable String title){
        fragments.add(fragment);
        if(title == null) return;
        titles.add(title);
    }

    public void add(int position, @NonNull Fragment fragment, @Nullable String title){
        fragments.add(position, fragment);
        if(title == null) return;
        titles.add(position, title);
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles == null ? fragments.get(position).toString() : titles.get(position);
    }
}
