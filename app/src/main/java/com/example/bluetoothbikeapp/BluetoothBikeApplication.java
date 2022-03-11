package com.example.bluetoothbikeapp;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

public class BluetoothBikeApplication extends Application {

    public static final String TAG = "BluetoothBike";

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
}
