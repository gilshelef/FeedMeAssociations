package com.gilshelef.feedmeassociations;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

/**
 * Created by gilshe on 2/23/17.
 */

public class CartFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cart, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.cart_take_view);
        final List<Donation> donationList = DataManager.get().getSaved();
        final CartAdapter adapter = new CartAdapter(getActivity(), donationList);
        AdapterManager.get().setAdapter(adapter.TAG, adapter);
        listView.setAdapter(adapter);
        return rootView;
    }
}
