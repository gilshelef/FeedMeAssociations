package com.gilshelef.feedmeassociations;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by gilshe on 2/21/17.
 */
class Donation {


    private static final String TAG = Donation.class.getName();
    private String type;
    private String description;
    private String imageUrl;
    private String phone;
    private String firstName;
    private String lastName;
    private int defaultImage;
    private String date;
    private boolean available;
    private String id;
    private String associationId;
    // location

    static ArrayList<Donation> getDonationsFromFile(Context context){
        final ArrayList<Donation> donationList = new ArrayList<>();

        try {
            // Load data
            String jsonString = loadJsonFromAsset("donations.json", context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray donations = json.getJSONArray("donations");

            // Get Donation objects from data
            for(int i = 0; i < donations.length(); i++){
                Donation donation = new Donation();
                JSONObject obj = donations.getJSONObject(i);
                donation.type = obj.getString("type");
                donation.description = obj.getString("description");
                donation.phone = obj.getString("phone");
                donation.firstName = obj.getString("firstName");
                donation.lastName = obj.getString("lastName");
                donation.imageUrl = obj.getString("imageUrl");
                donation.date = obj.getString("date");
                donation.available = obj.getBoolean("available");
                donation.id = obj.getString("id");
                donation.associationId = donation.available ? "" : obj.getString("associationId");
                donation.defaultImage = getImageByType(donation.type);
                donationList.add(donation);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return donationList;
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
                return R.drawable.list_donation;
        }
    }

    private static String loadJsonFromAsset(String filename, Context context) {
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

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getContactInfo() {
        return firstName + " " + lastName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getDefaultImage() {
        return defaultImage;
    }

    public String getDate() {
        return date;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getPhone() {
        return phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
