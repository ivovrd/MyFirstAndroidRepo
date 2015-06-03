package com.example.admin.myfinalapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

/**
 * Created by ADMIN on 29.4.2015..
 */
public class MyAdapter extends BaseAdapter{
    private Context myContext;
    private LayoutInflater myInflater;

    public MyAdapter(Context context){
        this.myContext = context;
        myInflater = (LayoutInflater)myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return DataStorage.cars.length;
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
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null)
            view = myInflater.inflate(R.layout.list_item, viewGroup, false);

        ImageView imageTmb = (ImageView)view.findViewById(R.id.imageTmb);
        TextView carManufacturer = (TextView)view.findViewById(R.id.manufacturer);
        TextView carModel = (TextView)view.findViewById(R.id.model);

        Car car = DataStorage.cars[position];

        carManufacturer.setText(car.manufacturer);
        carModel.setText(car.model);
        UrlImageViewHelper.setUrlDrawable(imageTmb, car.thumbnail);
        return view;
    }
}
