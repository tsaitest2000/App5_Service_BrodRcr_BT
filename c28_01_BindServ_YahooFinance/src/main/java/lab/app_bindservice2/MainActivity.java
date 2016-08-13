package lab.app_bindservice2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import yahoofinance.Stock;

public class MainActivity extends AppCompatActivity {

   private Context mContext;
   private EditText mEditText;
   private TextView mTextView;
   private ServiceConnection mConnection; // ★★
   private MyBindService.MyBinder mMyBinder; // ★★

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      mContext = MainActivity.this;
      mTextView = (TextView) findViewById(R.id.textView);
      mEditText = (EditText) findViewById(R.id.editText);

      mConnection = new ServiceConnection() {
         @Override
         public void onServiceConnected(ComponentName name, IBinder service) {
            mMyBinder = (MyBindService.MyBinder) service; // ★★
            Toast.makeText(mContext, "Connected", Toast.LENGTH_SHORT).show();
         }

         @Override
         public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(mContext, "Disconnected", Toast.LENGTH_SHORT).show();
         }
      };
      Intent intent = new Intent(mContext, MyBindService.class);
      MainActivity.this.bindService(intent, mConnection, BIND_AUTO_CREATE);
   }

   public void onClick(View view) {

      View container = new View(mContext) { // View型別物件即為交給委託人BindService的"菜籃"
         @Override
         public void setTag(Object data) {
            final Stock stock = (Stock) data;
            runOnUiThread(new Runnable() {
               @Override
               public void run() {
                  String message = String.format("成交價:%.2f\n買價:%.2f\n賣價:%.2f",
                     stock.getQuote().getPrice().doubleValue(),
                     stock.getQuote().getBid().doubleValue(),
                     stock.getQuote().getAsk().doubleValue());
                  mTextView.setText(message);
               }
            });
         }
      };
      mMyBinder.getStock(mEditText.getText().toString(), container);
   }

}
