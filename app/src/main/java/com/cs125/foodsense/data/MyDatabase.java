package com.cs125.foodsense.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.cs125.foodsense.R;
import com.cs125.foodsense.data.dao.BodyConstitutionDAO;
import com.cs125.foodsense.data.dao.HeartRateDAO;
import com.cs125.foodsense.data.dao.UserConstitutionDAO;
import com.cs125.foodsense.data.entity.BodyConstitution;
import com.cs125.foodsense.data.entity.UserConstitution;
import com.cs125.foodsense.data.util.Converters;
import com.cs125.foodsense.data.dao.FoodJournalDAO;
import com.cs125.foodsense.data.dao.FoodRegimenDAO;
import com.cs125.foodsense.data.dao.UserDAO;
import com.cs125.foodsense.data.entity.HeartRate;
import com.cs125.foodsense.data.entity.User;
import com.cs125.foodsense.data.entity.FoodRegimen;
import com.cs125.foodsense.data.entity.FoodJournal;
import com.cs125.foodsense.data.util.Utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
/*
Database holder that serves as main access point for connection
to your database
 */

/*
   1. static - only one copy, no matter how many objects of class created
         if 2 threads accessing same object/updating variable,
         each thread make own local copy of same object in respective cache
   2.  volatile - not stored in local cache of thread,
               each thread access variable in main memory
               other threads able to access update value
   3. static volatile - singleton
*/

@Database(entities = {User.class,
                        FoodRegimen.class,
                        FoodJournal.class,
                        HeartRate.class,
                        BodyConstitution.class,
                        UserConstitution.class},
         version = 1)
@TypeConverters({Converters.class})
public abstract class MyDatabase extends RoomDatabase {
    public static final String DB_NAME = "app_database";
    private static MyDatabase DB_INSTANCE;
    private static Context CONTEXT;

    // Declare data access objects (DAO) as abstract
    // Note: the builder in getDatabase generates necessary code for the methods below
    public abstract UserDAO getUserDAO();
    public abstract FoodRegimenDAO getFoodRegimenDAO();
    public abstract FoodJournalDAO getFoodJournalDAO();
    public abstract BodyConstitutionDAO getBodyConstitutionDAO();
    public abstract HeartRateDAO getHeartRateDAO();
    public abstract UserConstitutionDAO getUserConstDAO();


    public static synchronized MyDatabase getDatabase(final Context context){
        if (DB_INSTANCE == null) {      // only instantiate database if not already have instance
            CONTEXT = context;
            DB_INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyDatabase.class, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)
                            .build();
        }
        return DB_INSTANCE;
    }

    // Prepopulate table - gives to access onCreate method in SQLiteOpenHelper
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(DB_INSTANCE).execute();

        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private BodyConstitutionDAO bcDao;
        private FoodRegimenDAO frDao;

        private PopulateDbAsyncTask(MyDatabase db){
            this.bcDao = db.getBodyConstitutionDAO();
            this.frDao = db.getFoodRegimenDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            populateBodyConstitution();
            populateFoodRegimen();
            return null;
        }

        private void populateBodyConstitution(){
            Log.d("MyDatabase", "Start populating body constitutions...");
            bcDao.insert(new BodyConstitution("Hepatonia", "HEP"));
            bcDao.insert(new BodyConstitution("Cholecystonia", "CHO"));
            bcDao.insert(new BodyConstitution("Pancreotonia", "PAN"));
            bcDao.insert(new BodyConstitution("Gastrotonia", "GAS"));
            bcDao.insert(new BodyConstitution("Pulmotonia", "PUL"));
            bcDao.insert(new BodyConstitution("Colonotonia", "COL"));
            bcDao.insert(new BodyConstitution("Renotonia", "REN"));
            bcDao.insert(new BodyConstitution("Vesicotonia", "VES"));
            Log.d("MyDatabase", "Completed populating body constitutions table");
        }

        private void populateFoodRegimen() {
            InputStream is = CONTEXT.getResources().openRawResource(R.raw.regimen);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, Charset.forName("UTF-8"))
            );
            String line;
            try {
                Log.d("MainActivity.readToDB_FoodRegimen()", "Starting to read regimen to DB...");
                while ((line = reader.readLine()) != null) {
                    // Split
                    String delimiter = ";";
                    String[] tokens = line.split(delimiter);

                    if (tokens.length > 9) {
                        // Read data
                        FoodRegimen foodReg = new FoodRegimen(tokens[0],
                                tokens[1],
                                Utility.toInt(tokens[2]),
                                Utility.toInt(tokens[3]),
                                Utility.toInt(tokens[4]),
                                Utility.toInt(tokens[5]),
                                Utility.toInt(tokens[6]),
                                Utility.toInt(tokens[7]),
                                Utility.toInt(tokens[8]),
                                Utility.toInt(tokens[9]));
                        if (frDao.isInTable(foodReg.getFoodDesc()) == 0) {
                            frDao.insert(foodReg);
                            Log.d("MyDatabase", "Inserted " + foodReg);
                        }
                    }
                }
            } catch (IOException e) {
                Log.wtf("MyDatabase", "Error populating datatable with csv data file");
                e.printStackTrace();
            }
        }
    }
};

