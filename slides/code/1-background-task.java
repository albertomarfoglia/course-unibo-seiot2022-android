public void onButtonClick(View v) {
  Thread worker = new Thread(new Runnable(){
    public void run() {
       Bitmap b = downloadImage("http://site.com/img.png");
       imageView.setImageBitmap(b);
    }
  });  
  worker.start();
}
private Bitmap downloadImage(String url){ /* Long-Running Task */ }