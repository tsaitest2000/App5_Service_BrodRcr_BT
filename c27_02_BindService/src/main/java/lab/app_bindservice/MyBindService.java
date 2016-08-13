package lab.app_bindservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Random;

public class MyBindService extends Service {

   // ★★★ Binder實作：回傳MyBindService類別的分身 ★★★
   public class MyBinder extends Binder { // ★★Binder實作介面IBinder 故MyBinder可為onBind()的回傳物件

      public MyBindService getService() { // MyBinder類別中的方法getService()回傳MyBindService類別實體
         return MyBindService.this;
      }

   }

   // 在MainActivity與MyBindService的唯一接口(onBind方法)中回傳MyBindService的"分身"
   @Override
   public IBinder onBind(Intent intent) {
      return new MyBinder(); // ★★★ 刪除預設的throw...，改成回傳MyBinder()物件，否則執行時期錯誤
   }

   @Override
   public boolean onUnbind(Intent intent) {
      return super.onUnbind(intent);
   }

   @Override
   public void onDestroy() {
      super.onDestroy();
   }

   // 主活動呼叫此服務中的此方法時 原本要出Internet抓資料 需要new 新執行緒 等待下堂課完成 ......
   public double getQuotePrice(String symbol) {
      return 8000 + new Random().nextInt(1000);
   }

}
