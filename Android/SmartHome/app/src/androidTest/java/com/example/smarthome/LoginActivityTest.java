package com.example.smarthome;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityActivityTestRule;

    {
        loginActivityActivityTestRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);
    }

    private LoginActivity loginActivity = null;
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(RegisterActivity.class.getName(),null,false);


    @Before
    public void setUp() throws Exception {
        loginActivity = loginActivityActivityTestRule.getActivity();





    }

    @Test
    public void testLaunch(){


        assertNotNull(loginActivity.findViewById(R.id.btnLoginRegister));
        onView(withId(R.id.btnLoginRegister)).perform(click());
        Activity registerActivity = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);
        assertNotNull(registerActivity);
        registerActivity.finish();

        /*EditText id = loginActivity.findViewById(R.id.etLoginUsername);
        EditText password = loginActivity.findViewById(R.id.etLoginPassword);



        id.setText("anil1");
        password.setText("123");

        assertNotNull(loginActivity.findViewById(R.id.btnLogin));
        onView(withId(R.id.btnLogin)).perform(click());
        //Activity dashboardActivity = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);
        assertNotNull(loginActivity);

        loginActivity.finish();*/




    }


    @After
    public void tearDown() throws Exception {
        loginActivity = null;
    }
}