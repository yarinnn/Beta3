package com.example.user.alpha3;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.data.DataHolder;

import java.util.Date;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<Dataitem> {

    Context context;
    int layoutResourceId;
    List<Dataitem> data=null;

    public CustomAdapter(Context context, int resource, List<Dataitem> objects){
        super(context, resource, objects);

        this.layoutResourceId = resource;
        this.context=context;
        this.data=objects;
    }
    static class DataHolder
    {
        ImageView ivPhoto;
        TextView tvText;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        DataHolder holder= null;
        if(convertView==null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent);
            holder=new DataHolder();
            holder.ivPhoto = (ImageView)convertView.findViewById(R.id.ivPhoto);
            holder.tvText = (TextView)convertView.findViewById(R.id.tvText);

            convertView.setTag(holder);

        }
        else{
            holder=(DataHolder)convertView.getTag();
        }

        Dataitem dataitem = data.get(position);
        holder.tvText.setText(dataitem.RecipeName);
        holder.ivPhoto.setImageResource(dataitem.resIdThumbnail);

        return convertView;
    }
}
