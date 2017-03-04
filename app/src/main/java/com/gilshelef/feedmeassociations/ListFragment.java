package com.gilshelef.feedmeassociations;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * Created by gilshe on 2/22/17.
 */
public class ListFragment extends BaseFragment {

    @Override
    protected RecycledBaseAdapter getAdapter() {
        return new ListAdapter(getActivity(), mDataSource, this);
    }

    @Override
    protected List<Donation> getDataSource() {
        return DataManager.get(getActivity()).getAll(getActivity());
    }

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

}
