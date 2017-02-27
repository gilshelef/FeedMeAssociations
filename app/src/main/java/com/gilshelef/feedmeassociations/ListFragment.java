package com.gilshelef.feedmeassociations;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;


/**
 * Created by gilshe on 2/22/17.
 */
public class ListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.donation_list_view);

        //TODO fetch donations from DB
        final List<Donation> donationList = DataManager.get().getDonationsFromFile(getActivity());
        final ListAdapter adapter = new ListAdapter(getActivity(), donationList);
        AdapterManager.get().setAdapter(adapter.TAG, adapter);
        listView.setAdapter(adapter);

        View itemView = inflater.inflate(R.layout.list_item_donation, container, false);

        ImageView v = (ImageView)itemView.findViewById(R.id.list_save_thumbnail);
        return rootView;
    }

    public interface OnActionEvent{
        void onSaveEvent();
    }

}
