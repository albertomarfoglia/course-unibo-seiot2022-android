public void onButtonClick(View v) {
  Thread worker = new Thread(new Runnable(){
    public void run() {
       final Bitmap b = downloadImage("http://site.com/img.png");
       runOnUiThread(new Runnable(){
         public void run() {
           imageView.setImageBitmap(b);
         }
       });
    }
  });

  worker.start();
}

private Bitmap downloadImage(String url){/* Long-Running Task */}