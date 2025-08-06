package ghayatech.ihubs


import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        Log.d("FCM", "Message received: ${remoteMessage.notification?.title}")
        Log.d("FCM", "Message received: ${remoteMessage.notification?.body}")

        val title = remoteMessage.notification?.title ?: "new message"
        val body = remoteMessage.notification?.body ?: "details"
//cY0xdkZmTKKeP-FkrHdOa0:APA91bGui3sZ5D9B46tqQBoa3w4elQoDGu4KQHakCacMiKJiZfmMv8ao6oNFgjXAN5GN3W9KdvJXuFCIWARU6OyMuQOuXDyb0ESTW_kIEk6YuAbhGRRg35Q
        showNotification(title, body)
    }

    private fun showNotification(title: String, body: String) {
        val channelId = "default_channel"
        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId, "Default Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}