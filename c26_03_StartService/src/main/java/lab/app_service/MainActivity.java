package lab.app_service;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

   private Context mContext;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      mContext = MainActivity.this;
   }

   public void onClick(View view) {
      Intent intent = new Intent(mContext, MyService.class);
      switch (view.getId()) {
         case R.id.btnStart:
            startService(intent);
            break;
         case R.id.btnStop:
            break;
      }
   }

}
