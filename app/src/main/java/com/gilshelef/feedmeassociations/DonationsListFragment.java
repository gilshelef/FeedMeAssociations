package com.gilshelef.feedmeassociations;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by gilshe on 2/22/17.
 */
public class DonationsListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_donation_fragment, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.donation_list_view);

        //TODO fetch donations from DB
        final ArrayList<Donation>  donationList = Donation.getDonationsFromFile(getActivity());
        listView.setAdapter(new DonationAdapter(getActivity(), donationList));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Donation clicked = donationList.get(position);
                try {
                    ((OnItemClicked) getActivity()).onItemClicked(clicked);
                }catch (ClassCastException e){}
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Donation clicked = donationList.get(position);
                try {
                    ((OnItemLongClicked) getActivity()).onItemLongClicked(clicked);
                }catch (ClassCastException e){}
                return true;
            }
        });

        return rootView;
    }

    public interface OnItemClicked {
        void onItemClicked(Donation clicked);
    }

    public interface OnItemLongClicked {
        void onItemLongClicked(Donation clicked);
    }





}
