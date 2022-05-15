class LooperThread extends Thread {
  public Handler handler;
  public void run() {
    Looper.prepare();
    handler = new Handler() { /*override handleMessage()*/ };
    Looper.loop();
  }
}