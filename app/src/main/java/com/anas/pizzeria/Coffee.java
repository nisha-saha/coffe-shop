package com.anas.pizzeria;

import android.os.Parcel;
import android.os.Parcelable;

public class Coffee implements Parcelable {
    private final String name;
    private final String price;
    private int quantity;

    public Coffee(String name, String price) {
        this.name = name;
        this.price = price;
        this.quantity = 0;
    }

    protected Coffee(Parcel in) {
        name = in.readString();
        price = in.readString();
        quantity = in.readInt();
    }

    public static final Creator<Coffee> CREATOR = new Creator<Coffee>() {
        @Override
        public Coffee createFromParcel(Parcel in) {
            return new Coffee(in);
        }

        @Override
        public Coffee[] newArray(int size) {
            return new Coffee[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(price);
        dest.writeInt(quantity);
    }
}
