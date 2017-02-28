package com.gilshelef.feedmeassociations;

import android.location.Location;

/**
 * Created by gilshe on 2/21/17.
 */
class Donation {



    public enum State {AVAILABLE, SAVED, OWNED}

    String type; // donation type - vegetables, pasty etc
    String description;
    String imageUrl;
    String phone;
    String firstName;
    String lastName;
    int defaultImage;
    String date;
    private String id; // donation id
    private State state;
    Location location;

    Donation(){}

    String getType() {
        return type;
    }

    String getDescription() {
        return description;
    }

    String getContactInfo() {
        return firstName + " " + lastName;
    }

    String getImageUrl() {
        return imageUrl;
    }

    int getDefaultImage() {
        return defaultImage;
    }

    String getPhone() {
        return phone;
    }

    void setId(String id) {
        this.id = id;
    }

    void setState(State state) {
        this.state = state;
    }

    Location getLocation() {
        return location;
    }

    boolean isOwned() {
        return state.equals(State.OWNED);
    }

    boolean isAvailable() {
        return state.equals(State.AVAILABLE);
    }

    boolean isSaved() {
        return state.equals(State.SAVED);
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Donation)) return false;

        Donation donation = (Donation) o;
        return donation.id.equals(this.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
