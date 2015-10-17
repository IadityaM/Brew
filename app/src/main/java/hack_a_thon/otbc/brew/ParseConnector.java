package hack_a_thon.otbc.brew;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Aditya on 10/17/2015.
 */
public class ParseConnector extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "yzhdX6QaCobWvPDWSwwNK2bWBosWUFgpti7d3dvO", "tA4NH9wxTEjeb6BD2rXyY6cFK3zX6F1xSgiAsMVm");
        //the first is the device app id and the second is the backend id
    }
}