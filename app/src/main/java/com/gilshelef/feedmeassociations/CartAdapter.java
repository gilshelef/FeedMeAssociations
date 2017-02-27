package com.gilshelef.feedmeassociations;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

/**
 * Created by gilshe on 2/26/17.
 */

class CartAdapter extends DonationBaseAdapter{

    CartAdapter(Context context, List<Donation> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    View inflateRowView() {
        return mInflater.inflate(R.layout.list_item_donation, null);
    }

    @Override
    void updateDataSource() {
        mDataSource = DataManager.get().getSaved();
    }


    // take all

    // navigate


}
