package com.example.riccardoamadio.bluetooth;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.AppIndexApi;
import com.google.android.gms.common.api.GoogleApiClient;

import junit.runner.Version;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "DeviceListActivity";


    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    private static BluetoothAdapter mBtAdapter;
    private BluetoothLeScanner abc;
    private ScanSettings settings;
    private Handler mHandler;
    private ArrayAdapter<String> mNewDevicesArrayAdapter;
    private List<ScanFilter> filter;
    private static final long SCAN_PERIOD = 10000;


/*

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
                Toast.makeText(context,"SONO ENTRATO",Toast.LENGTH_LONG).show();
            String action = intent.getAction();

            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                Log.d("SONO PARTITO","PARTITO");
                Toast.makeText(context,"partito",Toast.LENGTH_SHORT).show();
                //discovery starts, we can show progress dialog or perform other tasks
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Toast.makeText(context,"finito",Toast.LENGTH_SHORT).show();
                //discovery finishes, dismis progress dialog
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //bluetooth device found
                Toast.makeText(context,"trovato",Toast.LENGTH_LONG).show();
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                Toast.makeText(context,"Found device " + device.getName(),Toast.LENGTH_SHORT).show();
            }


            String action = intent.getAction();
            Toast.makeText(context,"entrato 1",Toast.LENGTH_LONG).show();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                Log.d("RICHI","entrato2");
                Toast.makeText(context,"entrato 2",Toast.LENGTH_LONG).show();
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Toast.makeText(context,device.getAddress(),Toast.LENGTH_LONG).show();
                UUID u=UUID.fromString(device.getUuids().toString());

                try {
                    BluetoothSocket blu_sock=device.createInsecureRfcommSocketToServiceRecord(u);
                    blu_sock.connect();
                } catch (IOException e) {
                    Toast.makeText(context,"errore 1",Toast.LENGTH_LONG).show();

                }
                // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Toast.makeText(context,"errore2",Toast.LENGTH_LONG).show();

            }

        }
    };
*/


    final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("FUNZIA", "FUNZIA");
            Toast.makeText(context, "SONO ENTRATO", Toast.LENGTH_LONG).show();

            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.d("mac address", device.getName());
                Toast.makeText(context, device.getName(), Toast.LENGTH_LONG).show();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                Toast.makeText(context, "Iniziato la scansione", Toast.LENGTH_LONG).show();
            }
            // Get the bluetoothDevice object from the Intent
            else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Toast.makeText(context, "Scansione finita", Toast.LENGTH_LONG).show();
            }
                /*
                if (device.getName().equals("MI")) {
                    Log.d("ENTRO NELL'IF", "ENTRO NELL'if");
                    // private UUID DEFAULT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
                    for (int i = 0; i < device.getUuids().length; i++) {
                        Log.d("UUID", String.valueOf(device.getUuids()[i].getUuid()));
                    }
                        try {

                        BluetoothSocket blu_sock=device.createInsecureRfcommSocketToServiceRecord(device.getUuids()[0].getUuid());
                        blu_sock.connect();
                        Log.d("CONNESSO","CONNESSO");
                    } catch (IOException e) {
                        Toast.makeText(context,"errore 1",Toast.LENGTH_LONG).show();

                    }*/
        }
    };

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public void onDestroy() {

        super.onDestroy();
        this.unregisterReceiver(mReceiver);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            mBtAdapter.startDiscovery();
            IntentFilter filter = new IntentFilter();
            filter.addAction(BluetoothDevice.ACTION_FOUND);
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
            registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy

        Toast.makeText(this, "partito", Toast.LENGTH_LONG).show();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.riccardoamadio.bluetooth/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.riccardoamadio.bluetooth/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
