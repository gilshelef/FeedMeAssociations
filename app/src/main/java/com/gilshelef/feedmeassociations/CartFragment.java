package com.gilshelef.feedmeassociations;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by gilshe on 2/23/17.
 */

public class CartFragment extends BaseFragment{

    private static final String TAG = ListFragment.class.getSimpleName();
    private List<Donation> mDataSource;
    private RecyclerView mRecyclerView;
    private RecycledBaseAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cart, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.cart_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDataSource = DataManager.get().getSaved();
        adapter = new CartAdapter(getActivity(), mDataSource, this);
        AdapterManager.get().setAdapter(CartAdapter.TAG, adapter);
        mRecyclerView.setAdapter(adapter);
        return rootView;
    }



}
