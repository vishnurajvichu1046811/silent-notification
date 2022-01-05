# SILENT NOTIFICATION

# DEMO APP WITH SILENT NOTIFICATION FEATURE IMPLEMENTED

# Features
* Managed notification when app is in
        - foreground
        - background
        - closed state
* Managed Fetching device location based on GPS or network provider

* Managed sending current device location into firebase realtime DB when a silent notification receives

# PAYLOAD STRUCTURE TOBE SENT FROM BACKEND
{
"to":"/topics/fetch_location",

"data": {
"type":"test",
"title":"fromPostman",
"message":"message",
"image":null,
"id":"1213",
"is_silent_notification":true
}
}

# NOTE : Should have “data” payload only and shouldnt have “notification” payload
# NOTE : Is_silent_notification - to check silent notification or not.

# Reference Links :
https://stackoverflow.com/questions/37947541/what-is-the-difference-between-firebase-push-notifications-and-fcm-messages
