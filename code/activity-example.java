import android.app.Activity;
public class MyActivity extends Activity {
  
  @Override
  public void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.myactivity_layout);
  }
  
  @Override
  public void onPause(){
    super.onPause();
    //Release resources, make state persistent, ...
  }
}