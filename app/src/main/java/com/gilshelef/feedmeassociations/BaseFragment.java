package com.gilshelef.feedmeassociations;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by gilshe on 2/27/17.
 */
abstract class BaseFragment extends Fragment implements RecycledBaseAdapter.OnActionEvent {
    public final String TAG = this.getClass().getSimpleName();

    protected List<Donation> mDataSource;
    protected RecyclerView mRecyclerView;
    protected RecycledBaseAdapter mAdapter;
//    protected ProgressBar progressBar;


    @Override
    public void onSaveEvent(String id) {
        DataManager.get(getActivity()).saveEvent(id);
    }

    @Override
    public void onCallEvent(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(phone));
        getActivity().startActivity(intent);
    }

    @Override
    public void onSelectEvent(String id, boolean selected) {
        ((OnSelectedEvent)getActivity()).onSelectedEvent(id, selected);
    }

    public interface OnSelectedEvent{
        void onSelectedEvent(String id, boolean selected);
    }
}
