package com.gilshelef.feedmeassociations;

import android.content.Context;
import android.location.Location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gilshe on 2/25/17.
 */
 class DataManager {

    private Map<String, Donation> donations;
    private static DataManager instance;
    private static final String AVAILABLE = "0";


    private DataManager(){
        donations = new LinkedHashMap<>();
    }

    static DataManager get() {
        if (instance == null) {
            synchronized (DataManager.class) {
                if (instance == null)
                    return build();
            }
        }
        return instance;
    }

    private static DataManager build() {
        instance = new DataManager();
        return instance;
    }

    List<Donation> getDonationsFromFile(Context context){
        try {
            // Load data
            String jsonString = loadJsonFromAsset("donations.json", context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray donations = json.getJSONArray("donations");

            // Get Donation objects from data
            for(int i = 0; i < donations.length(); i++){
                JSONObject obj = donations.getJSONObject(i);
                Donation donation = new Donation();
                donation.type = obj.getString("type");
                donation.description = obj.getString("description");
                donation.phone = obj.getString("phone");
                donation.firstName = obj.getString("firstName");
                donation.lastName = obj.getString("lastName");
                donation.imageUrl = obj.getString("imageUrl");
                donation.date = obj.getString("date");
                donation.setId(obj.getString("id"));

                String state = obj.getString("state");
                if(state.equals(AVAILABLE))
                    donation.setState(Donation.State.AVAILABLE);

                //TODO: decide initial state!
                else donation.setState(Donation.State.SAVED);

                Location l = new Location("donation's location");
                l.setLatitude(obj.getDouble("latitude"));
                l.setLongitude(obj.getDouble("longitude"));
                donation.location = l;
                donation.defaultImage = getImageByType(donation.type);
                insert(donation);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(this.donations.values());
    }

    private void insert(Donation donation) {
        String id = donation.getId();
        donations.put(id, donation);
    }

    private String loadJsonFromAsset(String filename, Context context) {
        String json;

        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (java.io.IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

    private static int getImageByType(String type) {
        switch (type){
            case "ירקות":
                return R.drawable.list_vegetable;
            case "מאפים":
                return R.drawable.list_pastry;
            case "בגדים":
                return R.drawable.list_clothes;
            default:
                return R.drawable.placeholder;
        }
    }

    List<Donation> getSaved() {
        List<Donation> saved = new ArrayList();
        for(Donation d: donations.values())
            if(!d.isAvailable()) // saved, selected, owned
                saved.add(d);
        return saved;

    }

    List<Donation> getAll() {
        return new ArrayList<>(donations.values());
    }

    void saveEvent(String id) {
        Donation d = donations.get(id);
        if(d.isAvailable())  // available => saved
            d.setState(Donation.State.SAVED);
        else if(d.isSaved() || d.isSelected())  // saved => available
            d.setState(Donation.State.AVAILABLE);
        else if(d.isOwned()) // TODO: notify data base
            d.setState(Donation.State.AVAILABLE);

        AdapterManager.get().updateDataSourceAll();
    }

    void selectedEvent(String id) {
        Donation d = donations.get(id);
        if(d.isSelected())
            d.setState(Donation.State.SAVED);
        else if(d.isSaved() || d.isAvailable())
            d.setState(Donation.State.SELECTED);
        else if(d.isOwned()) // TODO: notify data base
            d.setState(Donation.State.AVAILABLE);

        AdapterManager.get().updateDataSourceAll();
    }
}
