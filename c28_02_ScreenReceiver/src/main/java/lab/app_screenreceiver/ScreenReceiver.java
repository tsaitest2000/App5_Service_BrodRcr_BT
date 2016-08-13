package lab.app_screenreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

public class ScreenReceiver extends BroadcastReceiver {

   public ScreenReceiver() {
   }

   @Override
   public void onReceive(Context context, Intent intent) {

      String action = intent.getAction();
      Log.d("MyScreen", action);
      switch (action) {
         case Intent.ACTION_SCREEN_ON: // 瑩幕開啟的情況
            Log.d("MyScreen", "螢幕開啟!!!");
            break;
         case Intent.ACTION_SCREEN_OFF: // 瑩幕關閉的情況
            Log.d("MyScreen", "螢幕關閉...");
            break;
         case Intent.ACTION_NEW_OUTGOING_CALL: // 電話撥出的情況
            String phone = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Log.d("MyPhone", "打出去的電話:" + phone);
            break;
         default: // 電話撥入的情況
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (manager.getCallState() == TelephonyManager.CALL_STATE_RINGING) {
               String incomingNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
               Log.d("MyPhone", "打來的電話:" + incomingNumber);
            }
      }
   }

}
