
  protected void onProgressUpdate(Integer n) { updateDownloadCounter(n); }

  protected void onPostExecute(Long bytes) {
    updateTotalBytesDownloadedCounter(bytes);
  }
}

// MainActivity.java
public void onButtonClick(View v) {
  DownloadFilesTask task = new DownloadFilesTask();
  task.execute(url1, url2, url3);
}