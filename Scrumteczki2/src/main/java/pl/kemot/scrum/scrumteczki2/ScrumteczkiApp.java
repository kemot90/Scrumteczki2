package pl.kemot.scrum.scrumteczki2;

import android.app.Application;
import android.content.Context;

/**
 * Created by Tomek on 07.10.13.
 */
public class ScrumteczkiApp extends Application {
    private static Context context;

    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
    }

    /**
     * @return globalny kontekst aplikacji.
     */
    public static Context getAppContext() {
        return context;
    }
}
