public void onButtonClick(View v) {
  new Thread(new Runnable (){
    public void run() {
      final Bitmap img = downloadImage("http://site.com/img.png");
      final byte[] blob = convertImage(img);

      final Bundle b = new Bundle();
      b.putByteArray(MyHandler.NEW_IMG , blob);

      final Message msg = new Message();
      msg.what = MyHandler.IMAGE_DOWNLOADED_MSG;
      msg.setData(b);
      h.sendMessage(msg);
    }
  }).start();
}