package com.gilshelef.feedmeassociations;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;

/**
 * Created by gilshe on 2/27/17.
 */
abstract class BaseFragment extends Fragment implements RecycledBaseAdapter.OnActionEvent {

    @Override
    public void onSaveEvent(Donation donation) {
        DataManager.get().saveEvent(donation);
    }

    @Override
    public void onCallEvent(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(phone));
        getActivity().startActivity(intent);
    }
}
