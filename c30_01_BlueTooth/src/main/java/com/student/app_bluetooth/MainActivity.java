package com.student.app_bluetooth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends BluetoothActivity {

   private Context mContext;
   private final String deviceName = "KevinHome";

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      mContext = MainActivity.this;
      dealWithBlueTooth();
   }

   private void dealWithBlueTooth() {
      boolean ok = findBT(deviceName);
      if (ok) {
         openBT(null);
         Toast.makeText(mContext, "連 接 藍 芽 成 功", Toast.LENGTH_SHORT).show();
      } else {
         Toast.makeText(mContext, "連 接 藍 芽 失 敗", Toast.LENGTH_SHORT).show();
      }
   }

   public void onClick(View view) {
      switch (view.getId()) {
         case R.id.btnTurnOn:
            sendData("1");
            break;
         case R.id.btnTurnOff:
            sendData("0");
            break;
         case R.id.btnVocal:
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
            MainActivity.this.startActivityForResult(intent, 101);
            sendData("0");
            break;
         case R.id.btnReset:
            dealWithBlueTooth();
            break;
      }
   }

   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
         ArrayList<String> listExtra = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
         String order = listExtra.get(0);
         if (order.trim().equals("開燈")) {
            Toast.makeText(mContext, order, Toast.LENGTH_SHORT).show();
            sendData("1");
         } else if (order.trim().equals("關燈")) {
            Toast.makeText(mContext, order, Toast.LENGTH_SHORT).show();
            sendData("0");
         }else{
            sendData("100");
         }
      }
   }

   @Override
   protected void onDestroy() {
      super.onDestroy();
      closeBT();
   }

}
