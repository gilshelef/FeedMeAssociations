package com.gilshelef.feedmeassociations;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by gilshe on 2/25/17.
 */
 class DataManager {

    private Map<String, Donation> donations;
    private Map<String, Donation> saved;
    private static DataManager instance;
    private static final String AVAILABLE = "0";


    private DataManager(){
        donations = new TreeMap<>();
        saved = new TreeMap<>();
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
                else donation.setState(Donation.State.OWNED);

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
        if(donation.isAvailable())
            donations.put(id, donation);
        else if(donation.isOwned()) {
            donations.put(id, donation);
            saved.put(id, donation);
        }
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

    void saveEvent(Donation d) {
        if(d.isAvailable()) { // available => saved
            d.setState(Donation.State.SAVED);
            saved.put(d.getId(), d);
        }
        else { // saved => available
            d.setState(Donation.State.AVAILABLE);
            saved.remove(d.getId());
        }
        new UpdateDataTask().execute();
    }

    List<Donation> getSaved() {
        return new ArrayList<>(saved.values());
    }

    List<Donation> getAll() {
        return new ArrayList<>(donations.values());
    }

    private class UpdateDataTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            AdapterManager.get().updateAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            AdapterManager.get().notifyDataSetChangeAll();
        }
    }
}
