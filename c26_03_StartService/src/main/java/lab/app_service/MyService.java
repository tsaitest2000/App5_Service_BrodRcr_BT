package lab.app_service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import java.util.Random;

public class MyService extends Service {

   private NotificationManager mManager;

   public MyService() {

   }

   @Override
   public void onCreate() {
      super.onCreate();
      mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
   }

   @Override
   public int onStartCommand(Intent intent, int flags, int startId) {
      notification();
      return Service.START_REDELIVER_INTENT;
   }

   @Override
   public IBinder onBind(Intent intent) {
      // TODO: Return the communication channel to the service.
      throw new UnsupportedOperationException("Not yet implemented");
   }

   @Override
   public void onDestroy() {
      super.onDestroy();
   }

   private void notification() {
      System.out.println("notification");
      int n = new Random().nextInt(99);

      Notification notification = new Notification.Builder(this)
         .setSmallIcon(R.mipmap.ic_launcher)
         .setContentTitle("加權指數")
         .setContentText((9100 + n) + "")
         .build();
      mManager.notify(101, notification);
   }

}
