package com.example.iknow243;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CoreectionAdaptor extends BaseAdapter {
    private List<String> catList;
    public CoreectionAdaptor (List<String> catList) {
        this.catList = catList;
    }
    @Override
    public int getCount() {
        return catList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView , ViewGroup viewGroup) {
        View view;

        if(convertView == null)
        {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.correction_items,viewGroup,false);
        }
        else
        {
            view = convertView;
        }

        ((TextView) view.findViewById(R.id.catName)).setText(catList.get(i));



        return view;
    }
}
