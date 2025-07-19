package com.example.vcompass.data.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.Date

open class Conversation : RealmObject() {
    @PrimaryKey
    var id_conversation: String = ""
    var idFirstUser: String = ""
    var idSecondUser: String?= null
    var messages: RealmList<Message> = RealmList()
    var createdAt: Date? = null
    var userFirst: User? = User()
    var userSecond: User? = User()

    fun copy(): Conversation {
        val copy = Conversation()
        copy.id_conversation = this.id_conversation
        copy.idFirstUser = this.idFirstUser
        copy.idSecondUser = this.idSecondUser
        copy.messages =  this.messages
        copy.createdAt = this.createdAt
        copy.userFirst = this.userFirst
        copy.userSecond = this.userSecond
        return copy
    }
}
