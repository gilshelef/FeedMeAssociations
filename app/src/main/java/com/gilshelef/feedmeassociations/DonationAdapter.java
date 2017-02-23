package com.gilshelef.feedmeassociations;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by gilshe on 2/21/17.
 */

public class DonationAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Donation> mDataSource;

    public DonationAdapter(Context context, ArrayList<Donation> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView;
        ViewHolder holder;
        if(convertView == null) {
            rowView = mInflater.inflate(R.layout.list_item_donation, null);
            holder = new ViewHolder();
            holder.type = (TextView) rowView.findViewById(R.id.donation_list_type);
            holder.description = (TextView) rowView.findViewById(R.id.donation_list_description);
            holder.contact = (TextView) rowView.findViewById(R.id.donation_list_contact);
            holder.date = (TextView) rowView.findViewById(R.id.donation_list_date);
            holder.image = (ImageView) rowView.findViewById(R.id.donation_list_thumbnail);
            holder.available = (ImageView) rowView.findViewById(R.id.donation_list_available);
            rowView.setTag(holder);
        }

        else {
            rowView = convertView;
            holder = (ViewHolder) rowView.getTag();
        }

        Donation current = (Donation) getItem(position);
        loadText(current, holder);
        loadImage(current, holder);
        loadStyle(holder);
        loadAvailability(holder, current.isAvailable());
        return rowView;
    }

    private void loadAvailability(ViewHolder holder, boolean available) {
        Drawable drawable;
        int imageAvailable = available ? R.drawable.available : R.drawable.not_available;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
            drawable = mContext.getDrawable(imageAvailable);
        else
            drawable = mContext.getResources().getDrawable(imageAvailable);

        holder.available.setImageDrawable(drawable);
    }

    private void loadText(Donation current, ViewHolder holder) {
        holder.type.setText(current.getType());
        holder.description.setText(current.getDescription());
        holder.contact.setText(current.getContactInfo());
        holder.date.setText(current.getDate());
    }

    private void loadImage(Donation current, ViewHolder holder) {
        if(current.getImageUrl().length() == 0) {// image not provided
            Drawable drawable;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
                drawable = mContext.getDrawable(current.getDefaultImage());
            else
                drawable = mContext.getResources().getDrawable(current.getDefaultImage());

            holder.image.setImageDrawable(drawable);
        }

        else
            Picasso.with(mContext)
                    .load(current.getImageUrl())
                    .into(holder.image);
    }

    private void loadStyle(ViewHolder holder) {
        Typeface typeAndDescription = Typeface.createFromAsset(mContext.getAssets(), "fonts/JosefinSans-Bold.ttf");
        holder.type.setTypeface(typeAndDescription);
        holder.description.setTypeface(typeAndDescription);

        Typeface contactAndDate = Typeface.createFromAsset(mContext.getAssets(), "fonts/Quicksand-Bold.otf");
        holder.contact.setTypeface(contactAndDate);
        holder.date.setTypeface(contactAndDate);

    }


    private static class ViewHolder {
        TextView type;
        TextView contact;
        TextView description;
        TextView date;
        ImageView image;
        ImageView available;
    }
}
