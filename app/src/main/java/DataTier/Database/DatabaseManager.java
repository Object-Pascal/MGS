package DataTier.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Point;

import java.util.ArrayList;

import LogicTier.RouteManager.Route.Waypoint;
import PresentationTier.Fragments.Setting.Settings;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String dbName = "GPSDatabase";
    private static final int dbVersion = 1;
    private static final String dbTableWaypoints = "Waypoints";
    private static final String dbTableSettings = "Settings";
    private static final String CREATE_TABLE_WAYPOINTS = "CREATE TABLE " + dbTableWaypoints +
            "(hasBeenVisited Integer, visitedIsChecked Integer, id Integer, name Text, latitude Text, longitude Text, height Text)";
    private static final String CREATE_TABLE_SETTINGS = "CREATE TABLE " + dbTableSettings +
            "(language Text)";

    public DatabaseManager(Context context) {
        super(context, dbName, null, dbVersion);
/*        SQLiteDatabase db = getWritableDatabase();

        String drop1 = "DROP TABLE " + dbTableSettings;
        String drop2 = "DROP TABLE " + dbTableWaypoints;

        db.execSQL(drop1);
        db.execSQL(drop2);

        db.execSQL(CREATE_TABLE_WAYPOINTS);
        db.execSQL(CREATE_TABLE_SETTINGS);*/
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

        int hasVisitedAsInteger = 0;
        int visitedIsCheckedAsInteger = 0;

        if(waypoint.isHasBeenChecked()) {
            hasVisitedAsInteger = 1;
        }
        if(waypoint.isVisitedChecked()) {
            visitedIsCheckedAsInteger = 1;
        }

        String insertStatement = "INSERT INTO " + dbTableWaypoints + " (hasBeenVisited, visitedIsChecked, id, name, latitude, longitude, height) " +
                "VALUES (" + hasVisitedAsInteger + ", " + visitedIsCheckedAsInteger + ", " +
                "" + waypoint.getId() + ", '" + waypoint.getName() +"', " +
                "'" + waypoint.getLatitude() + "', '" + waypoint.getLongitude() +"', '" + waypoint.getHeight() +"')";

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(insertStatement);
        db.close();

    }

    public void insertSettingsIntoDB(Settings settings) {

        String getStatement = "SELECT * FROM " + dbTableSettings;

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(getStatement, null);

        if(cursor.getCount() > 0) {
            String insertStatement = "UPDATE " + dbTableSettings + " SET language = '" + settings.getLanguage() + "' WHERE 1 = 1";

            db.execSQL(insertStatement);
            db.close();
        }
        else {
            String insertStatement = "INSERT INTO " + dbTableSettings + " (language) VALUES ('" + settings.getLanguage() + "')";

            db.execSQL(insertStatement);
            db.close();
        }

    }

    public ArrayList<Waypoint> getWaypointsFromDB() {

        ArrayList<Waypoint> waypoints = new ArrayList<>();

        String getStatement = "SELECT * FROM " + dbTableWaypoints;

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(getStatement, null);

        if(cursor.getCount() > 0)
        {
            while(cursor.moveToNext()) {

                boolean hasBeenVisited = false;
                boolean visitedIsChecked= false;
                int id;
                String name;
                String latitude;
                String longitude;
                String height;

                int hasBeenVisitedInt = cursor.getInt(cursor.getColumnIndex("hasBeenVisited"));
                int visitedIsCheckedInt = cursor.getInt(cursor.getColumnIndex("visitedIsChecked"));
                id = cursor.getInt(cursor.getColumnIndex("id"));
                name = cursor.getString(cursor.getColumnIndex("name"));
                latitude = cursor.getString(cursor.getColumnIndex("latitude"));
                longitude = cursor.getString(cursor.getColumnIndex("longitude"));
                height = cursor.getString(cursor.getColumnIndex("height"));

                if(hasBeenVisitedInt == 1) {
                    hasBeenVisited = true;
                }
                if(visitedIsCheckedInt == 1) {
                    visitedIsChecked = true;
                }

                waypoints.add(new Waypoint(hasBeenVisited, visitedIsChecked, id, name,
                        latitude, longitude, Double.parseDouble(height), null));
            }
        }

        db.close();
        return waypoints;

    }

    public Settings getSettingsFromDB() {

        Settings settings = null;

        String getStatement = "SELECT * FROM " + dbTableSettings;

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(getStatement, null);

        if(cursor.getCount() > 0)
        {
            while(cursor.moveToNext()) {

                String language = cursor.getString(cursor.getColumnIndex("language"));
                settings = new Settings(language);
                break;
            }
        }

        return settings;

    }

}
