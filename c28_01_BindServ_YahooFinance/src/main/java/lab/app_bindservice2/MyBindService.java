package lab.app_bindservice2;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.view.View;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class MyBindService extends Service {

   public MyBindService() {

   }

   @Override
   public IBinder onBind(Intent intent) {
      return new MyBinder();
   }

   class MyBinder extends Binder {

      public void getStock(String symbol, View container) {
         new RunWork_YahooFinance(symbol, container).start();
      }
   }

   class RunWork_YahooFinance extends Thread {

      String symbol;
      View container;

      public RunWork_YahooFinance(String symbol, View container) {
         this.symbol = symbol;
         this.container = container;
      }

      @Override
      public void run() {
         try {
            Stock stock = YahooFinance.get(symbol);
            container.setTag(stock);
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
   }

}
