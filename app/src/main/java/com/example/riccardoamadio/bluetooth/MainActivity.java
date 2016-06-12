package com.example.riccardoamadio.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {




    private static final String TAG = "DeviceListActivity";


    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    private BluetoothAdapter mBtAdapter;


    private ArrayAdapter<String> mNewDevicesArrayAdapter;



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



    final BroadcastReceiver mReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            Log.d("FUNZIA","FUNZIA");
            Toast.makeText(context,"SONO ENTRATO",Toast.LENGTH_LONG).show();

            String action = intent.getAction();

            if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
            {
                Log.d("FINISCO A CERCARE","FINITO A CERCARE");
                Toast.makeText(context,"FINITO DI CERCARE",Toast.LENGTH_SHORT).show();
            }

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action))
            {
                    Log.d("TROVATO","TROVATO");

                // Get the bluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.d("mac address",device.getName());
                /*
                if(device.getName().equals("P01Y"))
                {
                    Log.d("ENTRO NELL'IF","ENTRO NELL'if");
                   // private UUID DEFAULT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


                        try {

                        BluetoothSocket blu_sock=device.createInsecureRfcommSocketToServiceRecord(device.getUuids()[0].getUuid());
                        blu_sock.connect();
                        Log.d("CONNESSO","CONNESSO");
                    } catch (IOException e) {
                        Toast.makeText(context,"errore 1",Toast.LENGTH_LONG).show();

                    }
                }
            */
                Toast.makeText(context,device.getAddress(),Toast.LENGTH_LONG).show();
            }
        }
    };

    public void onDestroy() {

        super.onDestroy();
        this.unregisterReceiver(mReceiver);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        Intent discoverableIntent = new
                Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
        Toast.makeText(this,"partito",Toast.LENGTH_LONG).show();
        mBtAdapter.startDiscovery();

    }





}
