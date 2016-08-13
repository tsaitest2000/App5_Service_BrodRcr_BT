package lab.app_listservice;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

   private ActivityManager mManager;
   private TextView mTextView;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      mTextView = (TextView) findViewById(R.id.textView);
      mManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

      List<ActivityManager.RunningServiceInfo> list = mManager.getRunningServices(100); //指定最大量

      for (ActivityManager.RunningServiceInfo info : list) {
         mTextView.append(info.service.getClassName() + "\n\n");
      }
   }

}
