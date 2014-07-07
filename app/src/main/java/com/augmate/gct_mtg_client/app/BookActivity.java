package com.augmate.gct_mtg_client.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import com.augmate.gct_mtg_client.R;
import com.augmate.gct_mtg_client.app.tasks.ActivityCallbacks;
import com.augmate.gct_mtg_client.app.tasks.BookAsyncTask;

public class BookActivity extends Activity implements ActivityCallbacks {

    public static final String ROOM_NUMBER_EXTRA = "room_number";
    private int roomNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.booking);

        roomNumber = getIntent().getIntExtra(ROOM_NUMBER_EXTRA,-1);

        new BookAsyncTask(this, this, roomNumber).execute();
    }

    @Override
    public void onTaskSuccess() {
        ((TextView) findViewById(R.id.booking_view)).setText("Booked room " + roomNumber+ "!");
    }

    @Override
    public void onTaskFailed() {
        Toast.makeText(this, "Could not book room!", Toast.LENGTH_LONG).show();
        finish();
    }
}
