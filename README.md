# SILENT NOTIFICATION

## Introduction
* This is a demo app with silent notification feature implemented. The actual objective for this silent notification is 
 to fetch device/user live location when server needed.
  

## Getting Started
* Features Implemented
-> Managed notification when app is in
  - foreground
  - background
  - closed state
  - 
-> Managed Fetching device location based on GPS or network provider

-> Managed sending current device location into firebase realtime DB when a silent notification receives

* PAYLOAD STRUCTURE TOBE SENT FROM BACKEND
----------------------------------------
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
* NOTE : Should have “data” payload only and shouldnt have “notification” payload
* NOTE : Is_silent_notification - to check silent notification or not.

## Build and Test
        - Run app, sent a notification with above strucure and to same notification TOPIC(fetch_location) from POSTMAN,
         and visit firebase realtime DB belongs to silent notification project( will assign TL to you) and see if user location
         updated or not.
        - "is_silent_notification":true -> will fetch and update user location without showing notification to user;
        - "is_silent_notification":false -> will show notification as normal without fetching location

## NOTE : For android version 9+ -> should enable location permission for ALWAYS to work even app is closed.

## Reference Links :
https://stackoverflow.com/questions/37947541/what-is-the-difference-between-firebase-push-notifications-and-fcm-messages
