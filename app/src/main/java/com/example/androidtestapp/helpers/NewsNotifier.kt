package com.example.androidtestapp.helpers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.androidtestapp.NewsActivity
import com.example.androidtestapp.R

class NewsNotifier(var context: Context) {
    private val CHANNEL_ID:String = "channel_id_ex_01"
    private val notificationId:Int = 123456

    init {
        createChannel()
    }

    private fun createChannel(){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
        {
            val name = "Notification Title"
            val descriptionText = "Notificaiton Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(CHANNEL_ID, name, importance)
                .apply {
                    description = descriptionText
                }
            val notificationManager: NotificationManager = context.getSystemService(
                Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun sendNotification(title:String, text:String){

        var intent = Intent(context, NewsActivity::class.java)
            .apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                putExtra("notificationId", notificationId)
                putExtra("notificationTitle", title)
                putExtra("notificationText", text)
            }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        //Фабрика не успевает создать...почему то
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ic_rabbit)
        val bitmap2 = BitmapFactory.decodeResource(context.resources, R.drawable.ic_cat)

        val builder =  NotificationCompat.Builder(context , CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_rabbit)
            .setContentTitle(title)
            .setContentText(text)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(context)){
            notify(notificationId, builder.build())
        }
    }

    fun cancelNotification(notificationId: Int){
        with(NotificationManagerCompat.from(context)){
            cancel(notificationId)
        }
    }
}