package pl.kemot.scrum.scrumteczki2;

import android.app.Application;
import android.content.Context;

/**
 * Created by Tomek on 07.10.13.
 */
public class ScrumteczkiApp extends Application {
    private static Context context;
    private ObservableSprintList sprintList;
    private ObservableChangesList changesList;

    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
        sprintList = new ObservableSprintList(this);
        changesList = new ObservableChangesList(this);
    }

    /**
     * @return globalny kontekst aplikacji.
     */
    public static Context getAppContext() {
        return context;
    }
    public ObservableSprintList getObservableSprintList() {
        return sprintList;
    }
    public ObservableChangesList getObservableChangesList() {
        return changesList;
    }
}
