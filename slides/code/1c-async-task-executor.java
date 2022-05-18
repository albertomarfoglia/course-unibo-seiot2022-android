val executor = Executors.newSingleThreadExecutor()
val handler = Handler(Looper.getMainLooper())
executor.execute {
    /* it's like doInBackground() */
    handler.post {
        /*  it's like onPostExecute() */
    }
}