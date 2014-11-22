package org.lunapark.develop.wobblebubble.android;

import android.os.Bundle;
import android.view.WindowManager;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import org.lunapark.develop.wobblebubble.WobbleBubble;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new WobbleBubble(), config);
	}
	
	/**
	 * ����� �� ���������
	 */
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		// Handle the back button
//		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			// Ask the user if they want to quit
//			new AlertDialog.Builder(this)
//					.setIcon(android.R.drawable.ic_dialog_alert)
//					.setTitle("Wobble Bubble")
//					.setMessage("Quit?")
//					.setPositiveButton(android.R.string.yes,
//							new DialogInterface.OnClickListener() {
//								@Override
//								public void onClick(DialogInterface dialog,
//										int which) {
//									// TODO Save db before exit
//
//									System.exit(0);
//								}
//
//							}).setNegativeButton(android.R.string.no, null)
//					.show();
//
//			return true;
//		} else {
//			return super.onKeyDown(keyCode, event);
//		}
//
//	}
}
