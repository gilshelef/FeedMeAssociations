package com.gilshelef.feedmeassociations;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by gilshe on 2/22/17.
 */
public class ListFragment extends BaseFragment {

    private static final String TAG = ListFragment.class.getSimpleName();
    private List<Donation> mDataSource;
    private RecyclerView mRecyclerView;
    private RecycledBaseAdapter adapter;
    private ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressBar = (ProgressBar) rootView.findViewById(R.id.list_progress_bar);
        new FetchDataTask().execute();
        return rootView;
    }

    private class FetchDataTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(String... params) {
            mDataSource = new ArrayList<>();
            mDataSource.addAll(DataManager.get().getDonationsFromFile(getActivity()));
            if(mDataSource.size() == 0)
                return 0;
            return 1; // successful
        }

        @Override
        protected void onPostExecute(Integer result) {
            progressBar.setVisibility(View.GONE);
            if (result == 1) {
                adapter = new ListAdapter(getActivity(), mDataSource, ListFragment.this);
                AdapterManager.get().setAdapter(ListAdapter.TAG, adapter);
                mRecyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(getActivity(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
