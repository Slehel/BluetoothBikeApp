package com.example.bluetoothbikeapp;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BluetoothBikeApplication extends Application {

    public static final String TAG = "BluetoothBike";

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    public File getNewRecordingFile() {
        File filesDir = this.getFilesDir();
        File recordsDir = new File(filesDir, "recordings");
        if (!recordsDir.exists() && !recordsDir.mkdir()) {
            return null; // failed to create recordings directory
        }

        //Get current date and time
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.CANADA);
        Date now = new Date();

        //initialize file variable with date and time at the end to ensure the new file wont overwrite previous file
        return new File(recordsDir, "Recording_" + formatter.format(now) + ".3gp");
    }
}
