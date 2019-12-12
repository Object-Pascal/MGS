package DataTier.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import LogicTier.RouteManager.Route.Waypoint;
import PresentationTier.Fragments.Setting.Settings;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String dbName = "GPSDatabase";
    private static final int dbVersion = 1;
    private static final String dbTableWaypoints = "Waypoints";
    private static final String dbTableSettings = "Settings";
    private static final String CREATE_TABLE_WAYPOINTS = "CREATE TABLE " + dbTableWaypoints + "(Test Integer)";
    private static final String CREATE_TABLE_SETTINGS = "CREATE TABLE " + dbTableSettings + "(Test Integer)";

    public DatabaseManager(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_WAYPOINTS);
        db.execSQL(CREATE_TABLE_SETTINGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertWaypointIntoDB(Waypoint waypoint) {

    }

    public void insertSettingsIntoDB(Settings settings) {

    }

    public void getLocationsFromDB() {

    }

    public void getSettingsFromDB() {

    }

}
