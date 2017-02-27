package com.gilshelef.feedmeassociations;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by gilshe on 2/26/17.
 */

abstract class DonationBaseAdapter extends BaseAdapter {

    final String TAG = this.getClass().getSimpleName();
    Context mContext;
    LayoutInflater mInflater;
    List<Donation> mDataSource;


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
        if (convertView == null) {
            rowView = inflateRowView();
            holder = new ViewHolder();
            holder.image = (ImageView) rowView.findViewById(R.id.list_thumbnail);
            holder.type = (TextView) rowView.findViewById(R.id.list_type);
            holder.description = (TextView) rowView.findViewById(R.id.list_description);
            holder.distance = (TextView) rowView.findViewById(R.id.list_distance);
            holder.call = (ImageView) rowView.findViewById(R.id.list_call_thumbnail);
            holder.contact = (TextView) rowView.findViewById(R.id.list_contact);
            holder.save = (ImageView) rowView.findViewById(R.id.list_save_thumbnail);
            rowView.setTag(holder);
        } else {
            rowView = convertView;
            holder = (ViewHolder) rowView.getTag();
        }

        final Donation current = (Donation) getItem(position);
        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.get().saveEvent(current);

            }
        });

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(current.getPhone()));
                mContext.startActivity(intent);
            }
        });

        loadData(current, holder);
        return rowView;

    }

    void updateItems(){
        updateDataSource();
    }

    private void loadData(Donation current, ViewHolder holder){
        loadText(current, holder);
        loadImage(current, holder);
        loadActions(current, holder);
        loadStyle(holder);
        loadDistance(current, holder);
    }

    private void loadActions(Donation current, ViewHolder holder) {
        Drawable callAction;
        Drawable takeAction;

        callAction = mContext.getDrawable(R.drawable.phone_call);

        if(current.isAvailable())
            takeAction = mContext.getDrawable(R.drawable.take);
        else if(current.isOwned() || current.isSaved()) // TODO maybe change style between taken and saved
            takeAction = mContext.getDrawable(R.drawable.take_owned);
        else takeAction = mContext.getDrawable(R.drawable.take_unavailable); // not suppose to happen!

        holder.call.setImageDrawable(callAction);
        holder.save.setImageDrawable(takeAction);
    }

    private void loadDistance(Donation current, ViewHolder holder) {
        double distance = Association.calcDistance(current.getLocation());
        String text = String.format(mContext.getString(R.string.distance), distance);
        holder.distance.setText(text);
    }

    private void loadText(Donation current, ViewHolder holder) {
        holder.type.setText(current.getType());
        holder.description.setText(current.getDescription());
        holder.contact.setText(current.getContactInfo());
    }

    private void loadImage(Donation current, ViewHolder holder) {

        //Donation image
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
    }

    abstract View inflateRowView();

    abstract void updateDataSource();

    static class ViewHolder {
        ImageView image;
        TextView type;
        TextView description;
        TextView contact;
        ImageView save;
        ImageView call;
        TextView distance;
    }
}
