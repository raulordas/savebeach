package uem.dam.sharethebeach.sharethebeach;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;

import org.junit.Test;
import org.junit.runner.RunWith;

import uem.dam.sharethebeach.sharethebeach.activities.Login_Activity;


import static android.view.KeyEvent.KEYCODE_BACK;
import static org.junit.Assert.*;

/**
 * Instrumented Base_Activity, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleInstrumentedTest extends ActivityInstrumentationTestCase2<Login_Activity> {
    private Button btnSplashLogin;

    public ExampleInstrumentedTest() {
        super(Login_Activity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        Login_Activity activity = getActivity();
        btnSplashLogin = activity.findViewById(R.id.btnSplashLogin);
        sendKeys(KEYCODE_BACK);

    }

    public void testLogin() {
        TouchUtils.tapView(this, btnSplashLogin);
    }
}
