package com.gilshelef.feedmeassociations;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gilshe on 2/23/17.
 */

public class CartFragment extends BaseFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cart, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDataSource = DataManager.get(getActivity()).getSaved(getActivity());
        mAdapter = new CartAdapter(getActivity(), mDataSource, this);
        AdapterManager.get().setAdapter(TAG, mAdapter);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }



}
