package org.apache.cordova.plugin.smssendingplugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.telephony.SmsManager;
import android.util.Log;

public class SmsSendingPlugin extends CordovaPlugin {
	public final String ACTION_HAS_SMS_POSSIBILITY = "HasSMSPossibility";
	public final String ACTION_SEND_SMS = "SendSMS";

	// private SmsManager smsManager = null;

	// public SmsSendingPlugin() {
	// 	super();
	// 	smsManager = SmsManager.getDefault();
	// }

	@Override
	public boolean execute(String action, JSONArray args,
			final CallbackContext callbackContext) throws JSONException {

		if (action.equals(ACTION_HAS_SMS_POSSIBILITY)) {

			Activity ctx = this.cordova.getActivity();
			if(ctx.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY)){
				callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, true));
			} else {
				callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, false));
			}
			return true;
		}
		else if (action.equals(ACTION_SEND_SMS)) {
			try {
				String phoneNumber = args.getString(0);
				String message = args.getString(1);
				this.sendSMS(phoneNumber, message);

				callbackContext.sendPluginResult(new PluginResult(
						PluginResult.Status.OK));
			}
			catch (JSONException ex) {
				callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, ex.getMessage()));
			}
			return true;
		}
		return false;
	}

	private void sendSMS(String phoneNumber, String message) {
		SmsManager manager = SmsManager.getDefault();
        PendingIntent sentIntent = PendingIntent.getActivity(this.cordova.getActivity(), 0, new Intent(), 0);
		manager.sendTextMessage(phoneNumber, null, message, sentIntent, null);
	}
}
