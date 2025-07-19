package com.example.vcompass.data.model


import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.Date

open class Notification : RealmObject() {
    @PrimaryKey
    var id_notification: String = ""
    var id_receiver: String = ""
    var id_sender: String = ""
    var content: String = ""
    var title: String = ""
    var type: String = ""
    var create_at: Date = Date()
    var buttonText: String = ""
    var isRead: String = ""

    fun copy(): Notification {
        val copy = Notification()
        copy.id_notification = this.id_notification
        copy.id_receiver = this.id_receiver
        copy.id_sender = this.id_sender
        copy.content = this.content
        copy.create_at = this.create_at
        copy.buttonText = this.buttonText
        copy.isRead = this.isRead
        copy.title = this.title
        copy.type = this.type
        return copy
    }
}