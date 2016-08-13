package lab.app_bindservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

   private Context mContext;
   private TextView mTextView;
   private ServiceConnection mConnection; // ★★ 連線到服務Service
   private MyBindService mMyBindService; // ★★ MyBindService的變數宣告

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      mContext = MainActivity.this;
      mTextView = (TextView) findViewById(R.id.textView);

      mConnection = new ServiceConnection() {
         @Override // ★★★★★ 觀念
         public void onServiceConnected(ComponentName name, IBinder service) {
            mMyBindService = ((MyBindService.MyBinder) service).getService(); //★★★★★ 觀念
            Toast.makeText(mContext, "onServiceConnected", Toast.LENGTH_SHORT).show();
         } // ★★ 參數IBinder service就是MyBindService的"分身"

         @Override
         public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(mContext, "onServiceDisconnected", Toast.LENGTH_SHORT).show();
         }
      };
      // ★★★★ 以下兩句語法撰寫在new ServiceConnection()外面，否則執行時期錯誤
      Intent intent = new Intent(mContext, MyBindService.class);
      //★★ 此種做法優點：活動在onCreate時就與服務建立連線
      MainActivity.this.bindService(intent, mConnection, Context.BIND_AUTO_CREATE); //三：自動繫結參數
   }

   public void onClick(View view) {
      switch (view.getId()) {
         case R.id.button:
            double price = mMyBindService.getQuotePrice("^TWII");
            mTextView.setText(price + "");
            break;
         case R.id.button2:
            MainActivity.this.unbindService(mConnection);
            break;
      }
   }

}
