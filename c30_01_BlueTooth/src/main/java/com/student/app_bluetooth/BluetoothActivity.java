package com.student.app_bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class BluetoothActivity extends AppCompatActivity {

   private String deviceName = "HC-05"; //"Demo5678";//
   private View view;
   private BluetoothAdapter mBluetoothAdapter;
   private BluetoothSocket mmSocket;
   private BluetoothDevice mmDevice;
   private OutputStream mmOutputStream;
   private InputStream mmInputStream;
   private Thread workerThread;
   private byte[] readBuffer;
   private int readBufferPosition;
   private volatile boolean stopWorker;

   // ==============================================================================================
   boolean findBT(String deviceName) {
      this.deviceName = deviceName;

      mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
      if (mBluetoothAdapter == null) {
         return false;
      }

      if (!mBluetoothAdapter.isEnabled()) {
         Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
         startActivityForResult(enableBluetooth, 0);
      }

      Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
      if (pairedDevices.size() > 0) {
         for (BluetoothDevice device : pairedDevices) {
            if (device.getName().equals(deviceName)) {
               mmDevice = device;
               return true;
            }
         }
      }
      return false;
   }

   // ==============================================================================================
   String openBT(View view) {
      try {
         this.view = view;
         UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //Standard SerialPortService ID
         mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
         mmSocket.connect();
         mmOutputStream = mmSocket.getOutputStream();
         mmInputStream = mmSocket.getInputStream();

         beginListenForData(); //開始傾聽藍芽裝置的資料
         return "↑ (送出) " + mmDevice.getName() + " Opened. (接收) ↓ ";
      } catch (Exception e) {
         return "Open BT Error";
      }
   }

   // ==============================================================================================
   void beginListenForData() {
      final Handler handler = new Handler();
      final byte delimiter = 10; //This is the ASCII code for a newline character

      stopWorker = false;
      readBufferPosition = 0;
      readBuffer = new byte[1024];
      workerThread = new Thread(new Runnable() { //建立一條新執行緒進入傾聽來自藍芽裝置資料輸入程序
         public void run() {
            while (!Thread.currentThread().isInterrupted() && !stopWorker) {
               try {
                  int bytesAvailable = mmInputStream.available();
                  if (bytesAvailable > 0) {
                     System.out.println("R:" + bytesAvailable);
                     byte[] packetBytes = new byte[bytesAvailable];
                     mmInputStream.read(packetBytes);
                     for (int i = 0; i < bytesAvailable; i++) {
                        byte b = packetBytes[i];
                        if (b == delimiter) {
                           byte[] encodedBytes = new byte[readBufferPosition];
                           System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                           final String data = new String(encodedBytes, "US-ASCII");
                           readBufferPosition = 0;

                           handler.post(new Runnable() {
                              public void run() {
                                 view.setTag(data);
                              }
                           });
                        } else {
                           readBuffer[readBufferPosition++] = b;
                        }
                     }
                  }
               } catch (IOException ex) {
                  stopWorker = true;
               }
            }
         }
      });
      workerThread.start();
   }

   // ==============================================================================================
   void sendData(String msg) {
      try {
         if (mmSocket != null) {
            mmOutputStream.write(msg.getBytes());
         } else {
            setTitle("藍芽尚未連結");
         }
      } catch (Exception e) {
         e.printStackTrace(System.err);
      }

   }

   // ==============================================================================================
   void closeBT() {
      try {
         if (mmSocket != null) {
            stopWorker = true;
            mmOutputStream.close();
            mmInputStream.close();
            mmSocket.close();
            setTitle("Bluetooth Closed");
         }
      } catch (Exception e) {
         e.printStackTrace(System.err);
      }
   }

}
