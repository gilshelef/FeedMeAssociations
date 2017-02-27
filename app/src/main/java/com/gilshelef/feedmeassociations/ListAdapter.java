package com.gilshelef.feedmeassociations;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

/**
 * Created by gilshe on 2/21/17.
 */
class ListAdapter extends DonationBaseAdapter {

    ListAdapter(Context context, List<Donation> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    protected View inflateRowView() {
        return mInflater.inflate(R.layout.list_item_donation, null);
    }

    @Override
    protected void updateDataSource() {
        mDataSource = DataManager.get().getAll();
    }

}
