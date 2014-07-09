package com.augmate.gct_mtg_client.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.WindowManager;
import com.augmate.gct_mtg_client.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.segment.android.Analytics;
import com.segment.android.models.Props;

public class WelcomeActivity extends TrackedGuiceActivity {

    private long mLoginStartTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoginStartTime = SystemClock.uptimeMillis();
                new IntentIntegrator(WelcomeActivity.this).initiateScan(IntentIntegrator.QR_CODE_TYPES);
            }
       }, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if(resultCode == RESULT_OK) {

            Analytics.track("GCT Login Scan Time Success", new Props(
                    "value", SystemClock.uptimeMillis() - mLoginStartTime
            ));

            String companyName = intentResult.getContents();

            Intent i = new Intent(this, WalkInstructionActivity.class);
            i.putExtra(WalkInstructionActivity.COMPANY_NAME_EXTRA, companyName);

            startActivity(i);
            finish();
        }else{
            Analytics.track("GCT Login Scan Time Unsuccessful", new Props(
                    "value", SystemClock.uptimeMillis() - mLoginStartTime
            ));
        }
    }
}
