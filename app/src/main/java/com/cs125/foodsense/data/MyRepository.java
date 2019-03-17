package com.cs125.foodsense.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.cs125.foodsense.data.MyDatabase;
import com.cs125.foodsense.data.dao.BodyConstitutionDAO;
import com.cs125.foodsense.data.dao.FoodJournalDAO;
import com.cs125.foodsense.data.dao.FoodRegimenDAO;
import com.cs125.foodsense.data.dao.HeartRateDAO;
import com.cs125.foodsense.data.dao.UserConstitutionDAO;
import com.cs125.foodsense.data.dao.UserDAO;
import com.cs125.foodsense.data.entity.BodyConstitution;
import com.cs125.foodsense.data.entity.FoodJournal;
import com.cs125.foodsense.data.entity.FoodRegimen;
import com.cs125.foodsense.data.entity.HeartRate;
import com.cs125.foodsense.data.entity.User;
import com.cs125.foodsense.data.entity.UserConstitution;

import java.util.Arrays;
import java.util.List;

//Repository - use to
//1. abstracts access to multiple data sources
//2. handles data operations such as insert, update, delete, get
//- manages query threads and allows use of multiple back-ends
//
//NOTE:
//- DO NOT PERFORM OPERATION ON MAIN THREAD AS APP WILL CRASH
//- To use database, need to create instance of it in application
//- Room is resource-intensive (BTS: Room doing extra work to manage/map/generate SQL)
//    -> use singleton pattern
//       Create instance of database when app loaded
//       Then reference it at Application level

public class MyRepository {

    private String DB_NAME = "app_database";
    private static volatile MyDatabase DB_INSTANCE;

    // Date access objects
    private FoodRegimenDAO foodRegDAO;
    private FoodJournalDAO foodJournalDAO;
    private UserDAO userDAO;
    private HeartRateDAO heartRateDAO;
    private UserConstitutionDAO userConstDAO;
    private BodyConstitutionDAO bodyConstDAO;

    // Repository
    public MyRepository(@NonNull Application application) {
        // application is a context
        MyDatabase db = MyDatabase.getDatabase(application);

        foodRegDAO = db.getFoodRegimenDAO();
        userDAO = db.getUserDAO();
        heartRateDAO = db.getHeartRateDAO();
        foodJournalDAO = db.getFoodJournalDAO();
        userConstDAO = db.getUserConstDAO();
        bodyConstDAO = db.getBodyConstitutionDAO();

    }


    /*------------------------- USER ----------------------------------------------*/

    private static class UserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDAO userDao;
        private String job;
        private String userEmail;

        private UserAsyncTask(UserDAO userDao, String job) {
            this.userDao = userDao;
            this.job = job;
        }

        @Override   // must have this override function
        protected Void doInBackground(User... user) {
            if (job == "INSERT"){
                insert(user[0]);
            }
            else if (job == "UPDATE"){
                update(user[0]);
            }
            return null;
        }

        private void insert(User user){
            try {

                if (userDao.isInTable(user.getEmail()) == 0) {
                    userDao.insert(user);
                }

                Log.d("MyRepository", "Inserted " + user);
            } catch (Exception e) {
                Log.d("MyRepository", "Failed to insert " + user);
            }
        }

        private void update(User user){
            try {
                userDao.updateUser(user.getEmail(), user.getFirstName(), user.getAge(), user.getHeight(), user.getWeight(), user.getGender());
                Log.d("test", user.toString());
                Log.d("MyRepository", "UserAsyncTask updated " + user);
            } catch (Exception e) {
                Log.d("MyRepository", "Failed to update " + user);
            }
        }
    }

    private static class UserAsyncTaskLoader extends AsyncTaskLoader<LiveData<User>>{
        private UserDAO userDao;
        private String userEmail;
        private int job;

        protected UserAsyncTaskLoader(@NonNull Context context, MyDatabase db, String email, int job) {
            super(context);
            this.userDao = userDao;
            this.userEmail = userEmail;
            this.job = job;
        }

        @Nullable
        @Override
        public LiveData<User> loadInBackground() {
            return userDao.getUserInfo(this.userEmail);
        }
    }

    public void insertUser(@NonNull User user) {
        new UserAsyncTask(userDAO, "INSERT").execute(user);
    }

    public void updateUser(@NonNull User user) {
        new UserAsyncTask(userDAO, "UPDATE").execute(user);
    }

    public User getUserStatic(String email) {
        return userDAO.getUserInfoStatic(email);
    }

    public LiveData<User> getUser(String email) {
        return userDAO.getUserInfo(email);
    }


    /* --------------------- HEART RATE ----------------------------------------------*/
    private static class HeartRateAsyncTask extends AsyncTask<HeartRate, Void, Void> {
        private HeartRateDAO hrDao;

        private HeartRateAsyncTask(HeartRateDAO hrDao) {
            this.hrDao = hrDao;
        }

        @Override   // must have this override function
        protected Void doInBackground(HeartRate... hr) {
            insert(hr[0]);
            return null;
        }

        private void insert(HeartRate hr){
            try {
                hrDao.insert(hr);
                Log.d("MyRepository", "Inserted " + hr);
            }
            catch (NullPointerException npe){
                Log.d("MyRepository", "Null Pointer Exception - Failed to insert " + hr);
            }
            catch (Exception e) {
                Log.d("MyRepository", "Failed to insert " + hr);
            }
        }
    }

    private static class HeartRateAsyncTaskLoader extends AsyncTaskLoader<LiveData<List<HeartRate>>>{
        private HeartRateDAO hrDao;
        private String userEmail;
        private int job;

        protected HeartRateAsyncTaskLoader(@NonNull Context context, HeartRateDAO hrDao, String email, int job) {
            super(context);
            this.hrDao = hrDao;
            this.userEmail = userEmail;
            this.job = job; // look at loadInBackground to see what jobs are available
        }

        @Nullable
        @Override
        public LiveData<List<HeartRate>> loadInBackground() {
            LiveData<List<HeartRate>> temp = null;
            switch (this.job){
                case 0: // get user's heart rate
                    temp = hrDao.getAllHRByUser(this.userEmail);
                    break;
                case 1: // get all heart rate
                    temp =  hrDao.getAllHR();
                    break;
            }
            return temp;
        }
    }

    public void insertHeartRate(HeartRate hr) {
        new HeartRateAsyncTask(heartRateDAO).execute(hr);
    }

    public LiveData<List<HeartRate>> getAllHRByUser(String email){
        return heartRateDAO.getAllHRByUser(email);
    }

    public LiveData<List<HeartRate>> getAllHRByUserDuration(String email, String duration){
        // duration ex.
        // "-24 hour"
        // "-2 day"
        return heartRateDAO.getAllHRByUserDuration(email, duration);
    }

    /* ---------------------- FOOD REGIMEN -------------------------------------- */
    private static class InsertFoodRegimenAsyncTask extends AsyncTask<FoodRegimen, Void, Void> {
        private FoodRegimenDAO frDao;

        private InsertFoodRegimenAsyncTask(FoodRegimenDAO frDao) {
            this.frDao = frDao;
        }

        @Override   // must have this override function
        protected Void doInBackground(FoodRegimen... fr) {
            FoodRegimen regimen = fr[0];

            try {
                if (frDao.isInTable(regimen.getFoodDesc()) == 0) {
                    frDao.insert(regimen);
                    Log.d("MyRepository", "Inserted " + regimen);
                }
                else{
                    Log.d("MyRepository", "Already exists " + regimen);
                }
            }
            catch (NullPointerException npe){
                Log.d("MyRepository", "Null Pointer Exception - Failed to insert " + regimen);
            }
            catch (Exception e) {
                Log.d("MyRepository", "Failed to insert  " + regimen);
            }
            return null;
        }
    }

    // IF this loader does not work, use the get function below
    private static class FoodRegimenAsyncTaskLoader extends AsyncTaskLoader<LiveData<List<FoodRegimen>>>{
        private FoodRegimenDAO frDao;
        private String userEmail;
        private String job;

        protected FoodRegimenAsyncTaskLoader(@NonNull Context context, FoodRegimenDAO frDao, String email, String job) {
            super(context);
            this.frDao = frDao;
            this.userEmail = userEmail;
            this.job = job; // look at loadInBackground to see what jobs are available
        }

        @Nullable
        @Override
        public LiveData<List<FoodRegimen>> loadInBackground() {
            return frDao.getFoodReg();
        }
    }

    private String getBCVarName(String body_constitution){
        String var = "";
        switch (body_constitution) {
            case ("HEP"):
                var = "for_hep";
                break;
            case ("CHO"):
                var = "for_cho";
                break;
            case ("PAN"):
                var = "for_pan";
                break;
            case ("GAS"):
                var = "for_gas";
                break;
            case ("PUL"):
                var = "for_pul";
                break;
            case ("COL"):
                var = "for_col";
                break;
            case ("REN"):
                var = "for_ren";
                break;
            case ("VES"):
                var = "for_fes";
                break;
        }

        return var;
    }

    public void insertFoodRegimen(FoodRegimen fr) {
        new InsertFoodRegimenAsyncTask(foodRegDAO).execute(fr); // if not exist in table
    }

    public LiveData<List<FoodRegimen>> getAllFoodRegimen(){
        return foodRegDAO.getFoodReg();
    }

    /* -------------------- FOOD JOURNAL ------------------------------------------*/
    private static class FoodJournalAsyncTask extends AsyncTask<FoodJournal, Void, Void> {
        private FoodJournalDAO fjDao;
        private String job;

        private FoodJournalAsyncTask(FoodJournalDAO fjDao, String job) {
            this.fjDao = fjDao;
            this.job = job;
        }

        @Override
        protected Void doInBackground(FoodJournal... fj) {
            FoodJournal foodEntry = fj[0];
            switch (this.job){
                case "INSERT":
                    insert(foodEntry);
                    break;
                case "UPDATE":
                    update(foodEntry);
                    break;
            }
            return null;
        }

        private void insert(FoodJournal foodEntry){
            try {
                fjDao.insert(foodEntry);
                Log.d("MyRepository", "Inserted " + foodEntry);
            } catch (NullPointerException npe){
                Log.d("MyRepository", "Null Pointer Exception - Failed to insert " + foodEntry);
            } catch (Exception e) {
                Log.d("MyRepository", "Failed to insert " + foodEntry);
            }
        }

        private void update(FoodJournal foodEntry){
            try {
                fjDao.update(foodEntry);
                Log.d("MyRepository", "Updated " + foodEntry);
            } catch (Exception e) {
                Log.d("MyRepository", "Failed to update " + foodEntry);
            }
        }
    }

    // IF this loader does not work, use the get function below
    private static class FoodJournalAsyncTaskLoader extends AsyncTaskLoader<LiveData<List<FoodJournal>>>{
        private FoodJournalDAO fjDao;
        private String userEmail;
        private String job;

        protected FoodJournalAsyncTaskLoader(@NonNull Context context, MyDatabase db, String email, String job) {
            super(context);
            this.fjDao = db.getFoodJournalDAO();
            this.userEmail = userEmail;
            this.job = job; // look at loadInBackground to see what jobs are available
        }

        @Nullable
        @Override
        public LiveData<List<FoodJournal>> loadInBackground() {
            LiveData<List<FoodJournal>> temp = null;
            switch (this.job){
                case "USER_HR": // get user's heart rate
                    temp = fjDao.getFoodJournalByUser(this.userEmail);
                    break;
            }
            return temp;
        }
    }

    public void insertIntoFoodJournal(FoodJournal fj) {
        new FoodJournalAsyncTask(foodJournalDAO, "INSERT").execute(fj);
        // need to update/set hits here
        UserConstitution uc = new UserConstitution(fj.getUserEmail(), fj.getFood());
        new UserConstAsyncTask(userConstDAO, userDAO, foodRegDAO, fj).execute(uc);
    }

    public void updateHRDiff(FoodJournal fj) {
        new FoodJournalAsyncTask(foodJournalDAO, "UPDATE").execute(fj);
    }

    public LiveData<List<FoodJournal>> getMyFoodJournal(String userEmail){
        return foodJournalDAO.getFoodJournalByUser(userEmail);
    }

    public LiveData<List<FoodJournal>> getMyFoodJournalByDuration(String userEmail, String duration){
        return foodJournalDAO.getFoodJournalByUserAndDuration(userEmail, duration);
    }

    public LiveData<List<FoodJournal>> getMyFoodJournalByNumberOfMeals(String userEmail, int meals){
        return foodJournalDAO.getFoodJournalByUserAndMeals(userEmail, meals);
    }

    /* ---------------- USER CONSTITUTION ------------*/
    private static class UserConstAsyncTask extends AsyncTask<UserConstitution, Void, Void> {
        private UserConstitutionDAO ucDao;
        private UserDAO userDao;
        private FoodRegimenDAO frDao;
        private FoodJournal foodJournal;
        private FoodRegimen foodRegimen;

        private UserConstAsyncTask(UserConstitutionDAO ucDao, UserDAO userDao, FoodRegimenDAO frDao, FoodJournal fj) {
            this.ucDao = ucDao;
            this.userDao = userDao;
            this.frDao = frDao;
            this.foodJournal = fj;
        }

        @Override
        protected Void doInBackground(UserConstitution... ucs) {
            UserConstitution uc = ucs[0];
            if (ucDao.isInTable(uc.getUserEmail(), uc.getFood())== 0){
                insert(uc);
            }
            else {
                compareSetHits(uc); // update hits, then body constitution
            }
            updateConst(uc);
            return null;
        }

        private void insert(UserConstitution uc) {
            try {
                ucDao.insert(uc);
                Log.d("MyRepository", "Inserted " + uc);
            } catch (NullPointerException npe) {
                Log.d("MyRepository", "Null Pointer Exception - Failed to insert " + uc);
            } catch (Exception e) {
                Log.d("MyRepository", "Failed to insert " + uc);
            }
        }

        private void updateConst(UserConstitution uc){
            try {
                UserConstitutionDAO.UserOverallHits user = ucDao.getByUserOverallHits(uc.getUserEmail());
                Log.d("MyRepository", "Retrieved overall hits for user" + user.toString());
                List<String> constitution = Arrays.asList("HEP", "CHO", "PAN", "GAS", "PUL", "COL", "REN", "VES");
                List<Integer> constHits = Arrays.asList(user.getHepHits(), user.getChoHits(), user.getPanHits(),
                                                        user.getGasHits(), user.getPulHits(), user.getColHits(),
                                                        user.getRenHits(), user.getVesHits());
                int userMaxOverallHit = user.getMaxHits();

                User userGeneral = userDao.getUserInfoStatic(uc.getUserEmail());

                for (int i = 0; i < constitution.size(); ++i){
                    if (userMaxOverallHit == constHits.get(i)){
                        userGeneral.setConstitution(constitution.get(i));
                        Log.d("MyRepository", "Set body constitution to " + constitution.get(i));
                        userDao.updateConstitution(userGeneral.getEmail(), userGeneral.getConstitution());
                        Log.d("MyRepository", "Updated body constitution for user"+  userGeneral.toString());
                        break;
                    }
                }
            } catch (Exception e) {
                Log.d("MyRepository", "Failed to update " + uc);
            }
        }

        private void compareSetHits(UserConstitution uc){
            String food = this.foodJournal.getFood();
            String foodClassification = this.foodJournal.getFoodClassification();
            Log.d("MyRepository", "Setting hits based on " + food + " with status " + foodClassification);
            this.foodRegimen = frDao.getFood(food);

            if (this.foodRegimen != null) {
                if (_isHit(foodRegimen.getForHep(), foodClassification)) {
                    uc.setHepHits(1);
                }
                if (_isHit(foodRegimen.getForCho(), foodClassification)) {
                    uc.setChoHits(1);
                }
                if (_isHit(foodRegimen.getForCol(), foodClassification)) {
                    uc.setColHits(1);
                }
                if (_isHit(foodRegimen.getForGas(), foodClassification)) {
                    uc.setGasHits(1);
                }
                if (_isHit(foodRegimen.getForPan(), foodClassification)) {
                    uc.setPanHits(1);
                }
                if (_isHit(foodRegimen.getForPul(), foodClassification)) {
                    uc.setPulHits(1);
                }
                if (_isHit(foodRegimen.getForRen(), foodClassification)) {
                    uc.setPulHits(1);
                }
                if (_isHit(foodRegimen.getForVes(), foodClassification)) {
                    uc.setVesHits(1);
                }
                try {
                    ucDao.updateHits(uc.getUserEmail(), uc.getFood(), uc.getHepHits(), uc.getChoHits(),
                            uc.getPanHits(), uc.getGasHits(), uc.getPulHits(),
                            uc.getColHits(), uc.getRenHits(), uc.getVesHits());
                    Log.d("MyRepository", "Updated " + uc);
                } catch (Exception e) {
                    Log.d("MyRepository", "Failed to update " + uc);
                }
            }
        }

        private boolean _isHit(int foodRegHit, String userStatus){
            if ((foodRegHit < 0 && userStatus == "BAD") ||
                    (foodRegHit > 0 && userStatus == "GOOD") ||
                    (foodRegHit == 0 && userStatus == "NEUTRAL")){ //bad
                return true;
            }
            return false;
        }
    }

    public List<BodyConstitution> getConstitutionLOV(){
        return bodyConstDAO.getLOV();
    }

    public LiveData<UserConstitution> getUserConst(String userEmail){
        return userConstDAO.getByUser(userEmail);
    }
}



