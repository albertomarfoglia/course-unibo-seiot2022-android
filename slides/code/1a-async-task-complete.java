class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {
  private int nFileDownloaded;

  protected void onPreExecute(){
    nFileDownloaded = 0;
    setupDownloadManager();
  }
      
  protected Long doInBackground(URL... urls) {
    long bytesDownloaded = 0;    
    for(int i = 0; i < urls.length; i++){
      bytesDownloaded += downloadFile(urls[i]);
      nFileDownloaded++;
      publishProgress(nFileDownloaded);
    }
    return bytesDownloaded;
  }