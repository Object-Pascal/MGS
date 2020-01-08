package DataTier.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;

import LogicTier.RouteManager.Route.Waypoint;
import PresentationTier.Fragments.Setting.Settings;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String dbName = "GPSDatabase";
    private static final int dbVersion = 1;
    private static final String dbTableSettings = "Settings";
    private static final String CREATE_TABLE_SETTINGS = "CREATE TABLE " + dbTableSettings +
            "(language Text, colorBlindMode Integer)";

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
        db.execSQL(CREATE_TABLE_SETTINGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertSettingsIntoDB(Settings settings) {

        String getStatement = "SELECT * FROM " + dbTableSettings;

        int colorBlindModeInteger = 0;
        if(settings.isColorBlindmode()) {
            colorBlindModeInteger = 1;
        }

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(getStatement, null);

        if(cursor.getCount() > 0) {
            String insertStatement = "UPDATE " + dbTableSettings + " SET language = '" + settings.getLanguage() + "', colorBlindMode = " + colorBlindModeInteger +" WHERE 1 = 1";

            db.execSQL(insertStatement);
            db.close();
        }
        else {
            String insertStatement = "INSERT INTO " + dbTableSettings + " (language, colorBlindMode) VALUES ('" + settings.getLanguage() + "', " + colorBlindModeInteger +")";

            db.execSQL(insertStatement);
            db.close();
        }

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
                int colorBlindModeInt = cursor.getInt(cursor.getColumnIndex("colorBlindMode"));

                boolean colorBlindMode = false;
                if(colorBlindModeInt >= 1) {
                    colorBlindMode = true;
                }

                settings = new Settings(language, colorBlindMode);
                break;
            }
        }
        else {
            insertSettingsIntoDB(new Settings("NL", false));
            return new Settings("NL", false);
        }

        return settings;

    }

}
