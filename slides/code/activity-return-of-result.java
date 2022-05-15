public void closeButtonClick(View view){
  Intent returnIntent = new Intent();
  returnIntent.putExtra("username","mario.rossi");
  setResult(Activity.RESULT_OK, returnIntent);
  finish();
}