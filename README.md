FirstAccessibilityService
=========================

Proof of concept to detect notifications on Android using the Accesibility framework.

Idealy:
  1) Detect android notification of specific types 
		com.google.android.gm (For gmail)
		com.google.android.tak (for google talk)
  2) Intercept the remoteview of the notification
  3) Read the email sender
  3.2) Even when you have more than 10 mails waiting..

  Used the code published to stackoverflow to read the RemoteViews
  
  http://stackoverflow.com/questions/9292032/extract-notification-text-from-parcelable-contentview-or-contentintent 


  Results:
    * Possible to intercept the Notification
	* Only Available of read the notificationRemoteViews so any information
	  NOT in the Notification is not possible to get
    * Using Accesibility would be possible for specifics apps to get extra informatin 
	  when app is launched
