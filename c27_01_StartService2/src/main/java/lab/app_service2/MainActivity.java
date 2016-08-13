package lab.app_service2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

   private Context mContext;
   private Button mBtnStart, mBtnStop;
   private static TextView sTextView; // ★商業邏輯(設為static)：讓Service改變Activity中元件的內容
   private Intent myServiceIntent;
   public static boolean sIsFront; // ★商業邏輯(設為static)：判斷目前Activity在前景(onStart)或背景(onStop)

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      Log.d("message", "★★★★Activity：onCreate");
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      mContext = this;
      mBtnStart = (Button) this.findViewById(R.id.btnStartServ);
      mBtnStop = (Button) this.findViewById(R.id.btnStopServ);
      sTextView = (TextView) findViewById(R.id.textView);

      mBtnStart.setOnClickListener(new MyBtnOnClickLnr());
      mBtnStop.setOnClickListener(new MyBtnOnClickLnr());
   }

   @Override
   protected void onStart() { //★目前的Activity在前景(可被看見)★
      Log.d("message", "★★★★Activity：onStart");
      super.onStart();
      sIsFront = true;
   }

   @Override
   protected void onStop() { //★目前的Activity在背景(不被看見)★
      Log.d("message", "★★★★Activity：onStop");
      super.onStop();
      sIsFront = false;
   }

   @Override
   protected void onDestroy() {
      Log.d("message", "★★★★Activity：onDestroy");
      super.onDestroy();
   }

   // ★宣告為static → 讓MyService2的Handler物件呼叫來改變畫面中TextView元件的顯示數值 → 類似Singleton★
   public static void showData(int number) {
      if (sTextView != null) {
         sTextView.setText(number + "");
      }
   }

   private class MyBtnOnClickLnr implements View.OnClickListener {
      @Override
      public void onClick(View view) {
         switch (view.getId()) {
            case R.id.btnStartServ:
               myServiceIntent = new Intent(mContext, MyService.class);
               MainActivity.this.startService(myServiceIntent);
               break;
            case R.id.btnStopServ: // ★ ★ 讓音樂停止的第一種方法
               if (myServiceIntent != null) {
                  MainActivity.this.stopService(myServiceIntent);
               }
               break;
         }
      }
   }

}
