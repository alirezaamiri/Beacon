package com.sensify.acnp.beacon;

import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseSettings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.BeaconTransmitter;

import java.util.Arrays;


public class MainActivity extends ActionBarActivity {

    Button beacon;
    String TAG = "beacon";
    EditText room_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beacon = (Button) findViewById(R.id.beacon);
        room_number = (EditText)findViewById(R.id.room_number);

        beacon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Beacon beacon = new Beacon.Builder()
                        .setId1("2f234454-cf6d-4a0f-adf2-f4911ba9ffa6")
                        .setId2(room_number.getText().toString())
                        .setId3("3")
                        .setManufacturer(0x0118) // Radius Networks.  Change this for other beacon layouts
                        .setTxPower(-59)
                        .setDataFields(Arrays.asList(new Long[]{0l})) // Remove this for beacon layouts without d: fields
                        .build();
                // Change the layout below for other beacon types
                BeaconParser beaconParser = new BeaconParser()
                        .setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25");
                BeaconTransmitter beaconTransmitter = new BeaconTransmitter(getApplicationContext(), beaconParser);
                beaconTransmitter.startAdvertising(beacon, new AdvertiseCallback() {

                    @Override
                    public void onStartFailure(int errorCode) {
                        Log.e(TAG, "Advertisement start failed with code: " + errorCode);
                    }

                    @Override
                    public void onStartSuccess(AdvertiseSettings settingsInEffect) {
                        Log.i(TAG, "Advertisement start succeeded.");
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
