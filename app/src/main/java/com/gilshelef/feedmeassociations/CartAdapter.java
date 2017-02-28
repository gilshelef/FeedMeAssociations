package com.gilshelef.feedmeassociations;

import android.content.Context;
import android.view.View;

import java.util.List;

/**
 * Created by gilshe on 2/26/17.
 */

class CartAdapter extends RecycledBaseAdapter {

    static final String TAG = CartAdapter.class.getSimpleName();

    CartAdapter(Context context, List<Donation> dataSource, OnActionEvent listener) {
        super(context, dataSource, listener);
    }

    @Override
    int getListItemLayout() {
        return R.layout.list_row;
    }

    @Override
    void updateDataSource() {
        mDataSource = DataManager.get().getSaved();
    }

    @Override
    void setViewListeners(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected = v.isSelected();
                v.setSelected(!selected);
            }
        });
    }

}
