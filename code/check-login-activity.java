public class MyActivity extends Activity{
  private static final int REQUEST_CODE = 12345;
  
  public void startButtonClick(View view){
    Intent i = new Intent(this, CheckLoginActivity.class);
    startActivityForResult(i, REQUEST_CODE);
  }
  
  @Override
  protected void onActivityResult(int reqID, int res, Intent data){
    if (reqID == REQUEST_CODE && res == Activity.RESULT_OK){
      //do something
      data.getStringExtra("username")
    }
}