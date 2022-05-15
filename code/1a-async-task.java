// DownloadImageTask.java
class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {  
  protected Bitmap doInBackground(String... urls) {
    Bitmap b = downloadImage(urls[0]);
    return b;
  }

  protected void onPostExecute(Bitmap result) {
    imageView.setImageBitmap(result);
  }
}

// MainActivity.java
public void onButtonClick(View v) {
  DownloadImageTask task = new DownloadImageTask();
  task.execute("http://site.com/img.png");
}