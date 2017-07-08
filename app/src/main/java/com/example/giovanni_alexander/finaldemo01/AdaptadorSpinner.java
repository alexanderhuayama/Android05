package com.example.giovanni_alexander.finaldemo01;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Giovanni_Alexander on 8/07/2017.
 */

public class AdaptadorSpinner extends ArrayAdapter<String> {
    public AdaptadorSpinner(Context context) {
        super(context, 0, new ArrayList<String>());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.tvSpinnerItem);
        String edad = getItem(position);

        textView.setText(edad);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.tvSpinnerItem);
        String edad = getItem(position);

        textView.setText(edad);
        return convertView;
    }
}
