package net.coscolla.android.firstaccesibilityservice;

import java.io.Console;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Notification;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.RemoteViews;
import android.widget.Toast;

public class FirstAccessibilityService extends AccessibilityService{

	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {
		int _type = event.getEventType();
		if(_type != AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED)
			return ;
		List<CharSequence >_text  = event.getText();
		String _package = event.getPackageName().toString();
		//if( _package.equals("com.google.android.talk") || _package.equals("com.google.android.gsf") || _package.equals("com.google.android.gm"))
		Parcelable p = event.getParcelableData();
		Notification n = (Notification) p;
		RemoteViews rv = n.contentView;
		Log.e("ACCC" , _package);
		Class secretClass = rv.getClass();		
		
		
		  try {
		        Map<Integer, String> text = new HashMap<Integer, String>();

		        Field outerFields[] = secretClass.getDeclaredFields();
		        for (int i = 0; i < outerFields.length; i++) {
		            if (!outerFields[i].getName().equals("mActions")) continue;

		            outerFields[i].setAccessible(true);

		            ArrayList<Object> actions = (ArrayList<Object>) outerFields[i]
		                    .get(rv);
		            for (Object action : actions) {
		                Field innerFields[] = action.getClass().getDeclaredFields();

		                Object value = null;
		                Integer type = null;
		                Integer viewId = null;
		                for (Field field : innerFields) {
		                    field.setAccessible(true);
		                    if (field.getName().equals("value")) {
		                        value = field.get(action);
		                    } else if (field.getName().equals("type")) {
		                        type = field.getInt(action);
		                    } else if (field.getName().equals("viewId")) {
		                        viewId = field.getInt(action);
		                    }
		                }

		                if (type != null && (type == 9 || type == 10)) {
		                    text.put(viewId, value.toString());
		                }
		                
		            }

		            // Breakpoint here to see the text HashMap
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
	}

	@Override
	public void onInterrupt() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onServiceConnected() {
		setServiceInfo();
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		Toast.makeText(this, "Disconnected", Toast.LENGTH_LONG);
		// TODO Auto-generated method stub
		return super.onUnbind(intent);
	}
	
private void setServiceInfo() {
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        // We are interested in all types of accessibility events.
        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        // We want to provide specific type of feedback.
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        // We want to receive events in a certain interval.
        info.notificationTimeout = 100;
        setServiceInfo(info);
}
}
