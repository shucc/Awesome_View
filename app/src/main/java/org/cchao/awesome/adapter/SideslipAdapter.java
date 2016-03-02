package org.cchao.awesome.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.cchao.awesome.widget.SideslipView;

import java.util.ArrayList;

/**
 * Created by chenchao on 16/3/2.
 */
public class SideslipAdapter extends BaseAdapter {

    private ArrayList<String> mDataText;

    public SideslipAdapter(ArrayList<String> mDataText) {
        this.mDataText = mDataText;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
