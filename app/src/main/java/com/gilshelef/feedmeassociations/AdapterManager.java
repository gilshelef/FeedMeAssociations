package com.gilshelef.feedmeassociations;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gilshe on 2/26/17.
 */
class AdapterManager {
    private Map<String, DonationBaseAdapter> adapters;
    private static AdapterManager instance;

    private AdapterManager(){
        adapters = new HashMap<>();
    }

    static AdapterManager get() {
        if (instance == null) {
            synchronized (AdapterManager.class) {
                if (instance == null)
                    return build();
            }
        }
        return instance;
    }

    private static AdapterManager build() {
        instance = new AdapterManager();
        return instance;
    }

    void updateAll() {
        for(DonationBaseAdapter a: adapters.values()) {
            a.updateItems();
        }
    }

    void setAdapter(String name, DonationBaseAdapter adapter) {
        adapters.put(name, adapter);
    }

    void notifyDataSetChangeAll() {
        for(DonationBaseAdapter a: adapters.values()) {
            a.notifyDataSetChanged();
        }
    }
}
