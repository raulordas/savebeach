package uem.dam.sharethebeach.sharethebeach;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;
import org.junit.runner.RunWith;

import uem.dam.sharethebeach.sharethebeach.activities.Login_Activity;

import static org.junit.Assert.*;

/**
 * Instrumented Base_Activity, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void useAppContext() {
        // Context of the app under Base_Activity.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("uem.dam.sharethebeach.sharethebeach", appContext.getPackageName());
    }
}
