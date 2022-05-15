Intent intent = new Intent(this, NewActivity.class);
intent.putExtra("MESSAGE_FROM_CREATOR","I'm your creator!");
startActivity(intent);