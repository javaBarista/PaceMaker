package com.example.pacemaker;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebase";
    private static String imgurl, iconurl;

    public MyFirebaseMessagingService(){ }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {  //data payload로 보내면 실행

        //Log.d(TAG, "From: " + remoteMessage.getFrom());

        //여기서 메세지의 두가지 타입(1. data payload 2. notification payload)에 따라 다른 처리를 한다.
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
            @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
            wakeLock.acquire(3000);
            wakeLock.release();

            imgurl = remoteMessage.getData().get("imgurl");
            iconurl = remoteMessage.getData().get("iconurl");
            sendNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("body"), getBitmapfromUrl(imgurl), getBitmapfromUrl(iconurl));
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }

    private void sendNotification(String title, String body, Bitmap image, Bitmap icon) {

        if (title == null){
            title = "공지사항";
        }
        Intent userIntent = new Intent(this, LoginActivity.class);

        userIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //newItem.setTitle(title);
        //newItem.setBody(body);

        //pendingIntent 에 mainIntent를 적재하여 외부 앱이나 백/포 그란운드에서 해당앱의 메인화면을 요청
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, userIntent,
                PendingIntent.FLAG_ONE_SHOT);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        //안드로이드 버전이 Oreo(26)보다 낮은 버전일시 채널 생성 없이 푸시를 사용한다.
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
            NotificationCompat.Builder notificationBuilder;
            if(icon == null) {
                notificationBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.university)
                        .setContentTitle(title) //디폴트 타이틀이 아닌 사용자가 원하는 타이틀로 대체
                        .setContentText(body)   //디폴트 내용가 아닌 사용자가 원하는 내용으로 대체
                        .setAutoCancel(true)    //사용자가 알림을 터치시 삭제(?)
                        //.setLargeIcon(icon)
                        .setStyle(new NotificationCompat.BigTextStyle().setBigContentTitle(title).bigText(body))
                        .setVibrate(new long[]{1000, 1000}) //진동 알림
                        .setSound(defaultSoundUri) //기본 사용자의 알림소리에 따른다
                        .setContentIntent(pendingIntent);
            }
            else{
                notificationBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.university)
                        .setContentTitle(title) //디폴트 타이틀이 아닌 사용자가 원하는 타이틀로 대체
                        .setContentText("↓scroll down")   //디폴트 내용가 아닌 사용자가 원하는 내용으로 대체
                        .setAutoCancel(true)    //사용자가 알림을 터치시 삭제(?)
                        .setLargeIcon(icon)
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(image).setSummaryText(body).bigLargeIcon(null))
                        .setVibrate(new long[]{1000, 1000}) //진동 알림
                        .setSound(defaultSoundUri) //기본 사용자의 알림소리에 따른다
                        .setContentIntent(pendingIntent);
            }
            mNotificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
            // The id of the channel.
        }
        String id = "my_channel_01";

// The user-visible name of the channel.
        CharSequence name = getString(R.string.channel_name);

// The user-visible description of the channel.
        String description = getString(R.string.channel_description);

        int importance = NotificationManager.IMPORTANCE_DEFAULT;    //IMPORTANCE_DEFAULT: 사용자의 핸드폰에 따른다. /  IMPORTANCE_LOW: 무조건 무음알림

        NotificationChannel mChannel = null;

        //안드로이드 버전이 Oreo(26)버전을 포함해 그 이상 일시 사용자가 채널을 생성해 채널에 알림을 바인딩해준다.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(id, name, importance);

            AudioAttributes att = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build();
// Configure the notification channel.
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            mChannel.setLockscreenVisibility(-1000);
// Sets the notification light color for notifications posted to this
// channel, if the device supports this feature.
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setSound(defaultSoundUri,att);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            mNotificationManager.createNotificationChannel(mChannel);

            // notificationManager.notify(4 /* ID of notification */, notificationBuilder.build());
            mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

// Sets an ID for the notification, so it can be updated.
            int notifyID = 1;

// Create a notification and set the notification channel.
            Notification notification;
            if(image == null) {
                notification = new Notification.Builder(this)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setSmallIcon(R.drawable.university)
                        .setLargeIcon(icon)
                        .setChannelId(id)
                        .setStyle(new Notification.BigTextStyle().setBigContentTitle(title).bigText(body))
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .build();
            }
            else {
                notification = new Notification.Builder(this)
                        .setSmallIcon(R.drawable.university)
                        .setContentTitle(title)
                        .setLargeIcon(icon)
                        .setContentText("↓scroll down")
                        .setStyle(new Notification.BigPictureStyle().bigPicture(image).setSummaryText(body).bigLargeIcon((Bitmap) null))
                        .setChannelId(id)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .build();
            }

// Issue the notification. //
            mNotificationManager.notify(notifyID, notification); //push message 전송
            mNotificationManager.deleteNotificationChannel(String.valueOf(mChannel)); //알림채널 삭제 -> 무한 알림반복 예방
        }
    }

    //수신한 url로 부터 비트맴 이미지를 얻는다.
    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}