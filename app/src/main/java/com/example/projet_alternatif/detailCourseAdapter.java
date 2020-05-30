package com.example.projet_alternatif;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Objects;

public class detailCourseAdapter extends ArrayAdapter<detailCourse> {

    private static final String TAG = "detailCourseListAdapter";
    private Context mContext;
    private int mRessource;

    detailCourseAdapter(Context context, int resource, ArrayList<detailCourse> objects) {
        super(context, resource, objects);
        mContext = context;
        mRessource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String nom = Objects.requireNonNull(getItem(position)).getNom();
        String date_creation = Objects.requireNonNull(getItem(position)).getDate_creation();
        String distance = Objects.requireNonNull(getItem(position)).getDistance();
        String id = Objects.requireNonNull(getItem(position)).getId();


        detailCourse course = new detailCourse(nom,date_creation,distance,id);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mRessource, parent, false);

        TextView tvNom = (TextView) convertView.findViewById(R.id.textViewDetail1);
        TextView tvDate = (TextView) convertView.findViewById(R.id.textViewDetail2);
        TextView tvDistance = (TextView) convertView.findViewById(R.id.textViewDetail3);
        TextView tvId = (TextView) convertView.findViewById(R.id.textViewDetail4);

        tvNom.setText(nom);
        tvDate.setText(date_creation);
        tvDistance.setText(distance);
        tvId.setText(id);

        return convertView;
    }


}
