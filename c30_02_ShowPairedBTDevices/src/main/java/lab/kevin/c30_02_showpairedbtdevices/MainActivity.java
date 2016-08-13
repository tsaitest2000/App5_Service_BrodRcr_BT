package lab.kevin.c30_02_showpairedbtdevices;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

   private TextView mTextView;
   private BluetoothAdapter mBluetoothAdapter;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      mTextView = (TextView) this.findViewById(R.id.textView);

      dealWithBlueTooth();
   }

   private void dealWithBlueTooth() {
      mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
      if (mBluetoothAdapter == null) {
         return;
      }

      if (!mBluetoothAdapter.isEnabled()) {
         Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
         startActivityForResult(enableBluetooth, 0);
      }

      Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
      if (pairedDevices.size() > 0) {
         for (BluetoothDevice device : pairedDevices) {
            mTextView.append(device.getName() + " , " + device.getAddress());
         }
      }
   }

}
