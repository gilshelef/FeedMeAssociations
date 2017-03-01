package com.gilshelef.feedmeassociations;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

/**
 * Created by gilshe on 2/27/17.
 */

abstract class RecycledBaseAdapter extends  RecyclerView.Adapter<RecycledBaseAdapter.ViewHolder>  {

    List<Donation> mDataSource;
    Context mContext;
    OnActionEvent mListener;

    RecycledBaseAdapter(Context context, List<Donation> dataSource, OnActionEvent listener) {
        this.mDataSource = dataSource;
        this.mContext = context;
        this.mListener = listener;
    }

    @Override
    public RecycledBaseAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        int layout = getListItemLayout();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layout, null);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(final RecycledBaseAdapter.ViewHolder holder, final int position) {

        final Donation donation = mDataSource.get(position);
        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(4)
                .oval(false)
                .build();

        //load donation's image
        Picasso pic = Picasso.with(mContext);
        if(donation.getImageUrl().isEmpty()) // image not provided
            pic.load(donation.getDefaultImage()).fit().transform(transformation).error(R.drawable.placeholder).into(holder.image);
        else
            pic.load(donation.getImageUrl()).fit().transform(transformation).placeholder(R.drawable.placeholder).into(holder.image);

        //Setting text views
        holder.type.setText(donation.getType());
        holder.description.setText(donation.getDescription());
        holder.contact.setText(donation.getContactInfo());

        loadDistance(donation, holder);
        loadSave(donation, holder);
        holder.itemView.setSelected((donation.isSelected() || donation.isOwned()));
        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {mListener.onSaveEvent(donation.getId());
            }
        });
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {mListener.onCallEvent(donation.getPhone());}
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSelectEvent(donation.getId());
            }
        });
    }

    private void loadSave(Donation donation, ViewHolder holder) {
        int resource = R.drawable.take_unavailable;
        if(donation.isAvailable())
            resource = R.drawable.save;
        else if(donation.isOwned() || donation.isSaved() || donation.isSelected()) // TODO maybe change style between taken and saved
            resource = R.drawable.take_owned;
        holder.save.setImageResource(resource);
    }

    private void loadDistance(Donation donation, RecycledBaseAdapter.ViewHolder holder) {
        double distance = Association.calcDistance(donation.getLocation());
        String text = String.format(mContext.getString(R.string.distance), distance);
        holder.distance.setText(text);
    }

    @Override
    public int getItemCount() {
        return (null != mDataSource ? mDataSource.size() : 0);
    }

    abstract int getListItemLayout();
    abstract void updateDataSource();

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView type;
        TextView description;
        TextView contact;
        ImageView save;
        ImageView call;
        TextView distance;

        ViewHolder(View view) {
            super(view);
            this.image = (ImageView) view.findViewById(R.id.list_thumbnail);
            this.type = (TextView) view.findViewById(R.id.list_type);
            this.description = (TextView) view.findViewById(R.id.list_description);
            this.distance = (TextView) view.findViewById(R.id.list_distance);
            this.call = (ImageView) view.findViewById(R.id.list_call_thumbnail);
            this.contact = (TextView) view.findViewById(R.id.list_contact);
            this.save = (ImageView) view.findViewById(R.id.list_save_thumbnail);
        }
    }

    interface OnActionEvent{
        void onSaveEvent(String donationId);
        void onCallEvent(String phone);
        void onSelectEvent(String donationId);
    }

}
