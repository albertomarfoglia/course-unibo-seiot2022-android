public class MyHandler extends Handler {
  public MyHandler(Looper looper, ImageView iv) {
    super(looper); // bind the looper
    this.imageView = iv;
  }
  @Override
  public void handleMessage(Message msg) {
    switch(msg.what){
      case IMAGE_DOWNLOADED_MSG: // equals to 1
        byte[] blob = msg.getData().getByteArray("new-img");
        Bitmap img = BitmapFactory
            .decodeByteArray(blob , 0, blob.length);
        imageView.setImageBitmap(img);
      break;
    }
  }
}