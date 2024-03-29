package com.augmate.gct_mtg_client.app.tasks;

import android.content.Context;
import com.augmate.gct_mtg_client.app.BookingTime;
import com.augmate.gct_mtg_client.app.CredentialGen;
import com.augmate.gct_mtg_client.app.MeetingBooker;
import com.augmate.gct_mtg_client.app.Room;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;
import roboguice.util.SafeAsyncTask;

import java.util.List;

public class CheckRoomAvailabilityTask extends SafeAsyncTask<List<BookingTime>> {

    private Context context;
    private VoiceTimeSelectActivityCallbacks callbacks;
    private Room requestedRoom;

    public CheckRoomAvailabilityTask(Context context, VoiceTimeSelectActivityCallbacks callbacks, Room requestedRoom) {
        this.context = context;
        this.callbacks = callbacks;
        this.requestedRoom = requestedRoom;
    }

    @Override
    public List<BookingTime> call() throws Exception {
        GoogleCredential credentials = new CredentialGen(context).getCreditials();

        Calendar calendarService = buildCalendarService(credentials);

        return new MeetingBooker(calendarService).getAvailability(requestedRoom);
    }

    @Override
    protected void onSuccess(List<BookingTime> availabilities) throws Exception {
        super.onSuccess(availabilities);

        callbacks.onRecieveAvailabilities(availabilities);
    }

    private Calendar buildCalendarService(Credential credential) {
        return new Calendar.Builder(
                AndroidHttp.newCompatibleTransport(), new GsonFactory(), credential)
                .setApplicationName("GCTMeetingClient/1.0")
                .build();
    }

    @Override
    protected void onException(Exception e) throws RuntimeException {
        super.onException(e);
    }
}
