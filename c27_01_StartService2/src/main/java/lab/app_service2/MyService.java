package lab.app_service2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.Random;

public class MyService extends Service {

   private NotificationManager mManager;
   private Handler mHandler;
   private MediaPlayer mPlayer;

   @Override
   public void onCreate() {
      Log.d("message", "Service：onCreate★★★★");
      super.onCreate();
      mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
      mHandler = new Handler();
      mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.gogogo);

      mPlayer.start();
      mHandler.post(mRunnable);
   }

   private Runnable mRunnable = new Runnable() {
      @Override
      public void run() {
         int number = new Random().nextInt(100);
         if (MainActivity.sIsFront) {
            mManager.cancel(101); //當TextView顯示數值時，刪除上方通知列的顯示
            MainActivity.showData(number); //讓MainActivity畫面中的TextView元件顯示亂數數值
            if (number == 101) { // ★ ★ 讓音樂停止的第二種方法
               MyService.this.stopSelf();
            }
         } else {
            Notification notification = new Notification.Builder(MyService.this)
               .setSmallIcon(R.mipmap.ic_launcher)
               .setContentTitle("幸運數字")
               .setContentText(number + "")
               .build();
            mManager.notify(101, notification); //讓MainActivity畫面中的通知列顯示亂數數值
         }

         mHandler.postDelayed(mRunnable, 100); //★每隔100毫秒執行一次，產生遞迴呼叫的效果★
      }
   };

   @Override
   public int onStartCommand(Intent intent, int flags, int startId) {
      Log.d("message", "Service：onStartCommand★★★★");
      return START_REDELIVER_INTENT;
   }

   @Override
   public IBinder onBind(Intent intent) {
      Log.d("message", "Service：onBind★★★★");
      throw new UnsupportedOperationException("Not yet implemented");
   }

   //★記得移除Callback，否則會一直浪費系統資源★
   @Override
   public void onDestroy() {
      Log.d("message", "Service：onDestroy★★★★");
      super.onDestroy();
      mHandler.removeCallbacks(mRunnable);
      mPlayer.stop();
   }

}
