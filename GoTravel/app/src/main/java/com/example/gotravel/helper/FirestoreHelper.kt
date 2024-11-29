package com.example.gotravel.helper

object FirestoreHelper {
    // Accommodation
    const val CL_ACCOM: String = "accommodation"
    const val FL_ACCOMID: String = "accommodationId"
    const val FL_NAME: String = "name"
    const val FL_PRICE: String = "price"
    const val FL_FREEROOM: String = "freeroom"
    const val FL_IMAGE: String = "image"
    const val FL_DESCRIPTION: String = "description"
    const val FL_ADDRESS: String = "address"
    const val FL_LONGTITUDE: String = "longitude"
    const val FL_LATITUDE: String = "latitude"
    const val FL_CITY: String = "city"
    const val FL_TARGETID: String = "idTaget"
    const val CL_USER: String = "users"
    const val FL_ROLE: String = "role"
    const val FL_FULLNAME : String = "fullName"
    const val FL_PHONE : String =  "phone"
    const val FL_PARTNERID: String = "partnerId"
    const val FL_AMENTITIES: String = "amentities"
    const val FL_TOTALRATE: String = "totalRate"


    // Booking
    const val CL_BOOKING: String = "bookings"
    const val FL_BOOKINGID: String = "bookingId"
    const val FL_ROOMID: String = "roomId"
    const val FL_STARTDATE: String = "startDate"
    const val FL_ENDDATE: String = "endDate"
    const val FL_USERID: String = "userId"
    const val FL_STATUS: String = "status"
    const val FL_PHONENUMBER: String = "phone"
    const val FL_ACCOMNAME: String = "accommodationName"

    //Rating
    const val CL_RATING: String = "ratings"
    const val FL_RATINGID: String = "ratingId"
    const val FL_RATE: String = "rate"
    const val FL_CONTENT: String = "content"
    const val FL_USERNAME: String = "userName"
    const val FL_CREATEDAT: String = "createdAt"

    //Room
    const val CL_ROOM: String = "rooms"
    const val FL_ROOMTYPE: String = "roomType"
    const val FL_PEOPLE: String = "people"
    const val FL_BED: String = "bed"
    const val FL_AREA: String = "area"
    const val FL_EMAIL: String = "email"

    //notification
    const val CL_NOTIFICATION: String = "notification"
    const val FL_NOTIFICATION_ID: String = "id_notification"
    const val FL_CREATE_AT: String = "create_at"
    const val FL_ID_SENDER: String = "id_sender"
    const val FL_ID_RECEIVER: String = "id_receiver"
    const val FL_TYPE: String = "type"
    const val FL_ISREAD: String = "isRead"
    const val FL_TITLE: String = "title"

    //conversation
    const val CL_CONVERSATION: String = "conversations"
    const val FL_CONVERSATION_ID: String = "id_conversation"
    const val FL_ID_FIRSTUSER: String = "id_firstUser"
    const val FL_ID_SECONDUSER: String = "id_secondUser"
    const val FL_MESSAGES: String = "messages"
    const val FL_CREATED_AT: String = "createdAt"

    //messages
    const val FL_MESSAGE_ID: String = "id_message"
    const val FL_CONTENT_MESSAGE: String = "content"
    const val FL_USER_ID_MESSAGE: String = "id_user"
}
