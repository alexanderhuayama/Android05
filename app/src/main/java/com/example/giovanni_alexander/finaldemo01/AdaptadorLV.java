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

import java.util.ArrayList;

/**
 * Created by Giovanni_Alexander on 8/07/2017.
 */

public class AdaptadorLV extends ArrayAdapter<Persona> {
    public AdaptadorLV(@NonNull Context context) {
        super(context, 0, new ArrayList<Persona>());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_item, parent, false);
        }

        TextView tvId, tvNombre, tvApellido, tvDocumento, tvEdad;

        tvId = (TextView) convertView.findViewById(R.id.tvId);
        tvNombre = (TextView) convertView.findViewById(R.id.tvNombre);
        tvApellido = (TextView) convertView.findViewById(R.id.tvApellido);
        tvDocumento = (TextView) convertView.findViewById(R.id.tvDocumento);
        tvEdad = (TextView) convertView.findViewById(R.id.tvEdad);

        Persona persona = getItem(position);
        tvId.setText(String.valueOf(persona.getId()));
        tvNombre.setText(persona.getNombre());
        tvApellido.setText(persona.getApellido());
        tvDocumento.setText(persona.getDocumento());
        tvEdad.setText(String.valueOf(persona.getEdad()));

        return convertView;

    }
}
