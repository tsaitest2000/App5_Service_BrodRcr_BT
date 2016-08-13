package lab.app_screenreceiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;

public class MainActivity extends AppCompatActivity {

   private ScreenReceiver mReceiver;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      // ★ 規劃：採用動態註冊Receiver的方式 ★
      mReceiver = new ScreenReceiver();
      IntentFilter filter = new IntentFilter();
      filter.setPriority(2147483647); // ★ 數值愈大 接收廣播的順位就愈高
      filter.addAction(Intent.ACTION_SCREEN_ON); // 瑩幕開啟
      filter.addAction(Intent.ACTION_SCREEN_OFF); // 瑩幕關閉
      filter.addAction(Intent.ACTION_NEW_OUTGOING_CALL); // 電話播出
      filter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED); // 電話播入
      MainActivity.this.registerReceiver(mReceiver, filter);
   }

   @Override
   public void onDestroy() {
      super.onDestroy();
      if (mReceiver != null) {
         MainActivity.this.unregisterReceiver(mReceiver);
      }
   }

}
