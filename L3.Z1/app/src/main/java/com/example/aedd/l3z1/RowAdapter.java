package com.example.aedd.l3z1;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;



class RowAdapter extends ArrayAdapter<RowBean> {

    private Context context;
    private int layoutResourceId;
    private RowBean[] data = null;

    RowAdapter(Context context, int layoutResourceId, RowBean[] data) {
        super(context,layoutResourceId,data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent){
        View row = convertView;
        RowBeanHolder rowBeanHolder;

        if(row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId,parent,false);

            rowBeanHolder = new RowBeanHolder();
            rowBeanHolder.imgIcon = (ImageView) row.findViewById(R.id.imgIcon);
            rowBeanHolder.txtTitle = (TextView) row.findViewById(R.id.txtTitle);
            rowBeanHolder.ratingBar = (RatingBar) row.findViewById(R.id.rateBar);

            row.setTag(rowBeanHolder);
        } else {
            rowBeanHolder = (RowBeanHolder) row.getTag();
        }
        RowBean object = data[position];
        rowBeanHolder.txtTitle.setText(object.title);
        rowBeanHolder.imgIcon.setImageResource(object.icon);
        rowBeanHolder.ratingBar.setRating(object.rate);

        return row;
    }

    private static class RowBeanHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
        RatingBar ratingBar;
    }
}
