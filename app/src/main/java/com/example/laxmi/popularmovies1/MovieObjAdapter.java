package com.example.laxmi.popularmovies1;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by laxmi on 30/4/16.
 */
public class MovieObjAdapter extends BaseAdapter {
    MovieObj[] movieObjs=null;
    Context context;

    public MovieObjAdapter(Activity context, MovieObj[] movieObjs) {
        this.context = context;
        this.movieObjs=movieObjs;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return movieObjs.length;
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return movieObjs[position];
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView iconView;

        if (convertView == null) {
            iconView = new ImageView(context);
        }else {
            iconView = (ImageView) convertView;
        }
        Picasso.with(context)
                .load(movieObjs[position].image)
                .into(iconView);
        iconView.setScaleType(android.widget.ImageView.ScaleType.CENTER_CROP);
        return iconView;
    }
}
