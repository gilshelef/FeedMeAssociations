package com.gilshelef.feedmeassociations;

import android.content.Context;
import android.view.View;

import java.util.List;

/**
 * Created by gilshe on 2/21/17.
 */
class ListAdapter extends RecycledBaseAdapter {

    public static final String TAG = ListAdapter.class.getSimpleName();

    ListAdapter(Context context, List<Donation> dataSource, OnActionEvent listener) {
        super(context, dataSource, listener);
    }

    @Override
    int getListItemLayout() {
        return R.layout.list_row;
    }

    @Override
    void updateDataSource() {
        mDataSource = DataManager.get().getAll();
    }

    @Override
    void setViewListeners(View view) {

    }
}
