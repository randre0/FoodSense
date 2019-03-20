# FoodSense

## Introduction
According to eastern medicine and culture, every person has a unique body constitution which is an indicator of what type of diet they should have. Some foods may aggravate symptoms (i.e. headaches, bloatedness, rashes) for certain body types indicating a food sensitivity. Other research shows that heart rate (HR) can be used as a biomarker for health. 

FoodSense is for anyone who wants to maintain or improve their overall health through diet. Our application focuses on gathering information and providing a personalized diet to its users based on the principles of the body constitutions, specifically the Eight-Constitution Medicine, using HR as a biomarker. The Eight-Constitution recommends a diet for people to follow for their current body constitution; negative or positive effects on HR levels from food intake could help categorize a user into a specific body constitution. The application will continuously measure the user’s HR levels and recalibrate the user’s body constitution to help maintain or improve HR levels

### Files
MenuActivity.Java
```
This class creates the layout for the bottom menu bar and allows other fragments to populate the area above the menu bar. The menu bar will launch the corresponding fragmment for each menu button.
```
UserProfileFragment.java
```
This fragment shows the user their current information which includes weight, height, age, gender, email. This information is extracted from the database, and can be updated with a button at the bottom of the screen.
```
LogFoodFragment.java
```
This fragment gives the user the option to log their food or measure their heart rate. Each button starts a new fragment that will send information back to this fragment.
```
InputFoodActivity.java
```
Loaded from the LogFoodFragment, this class allows the user to choose a category and then choose a food from that category. The user can choose to submit this information to the LogFoodFragment
```
HeartRateActivity.java
```
Loaded from the LogFoodFragment, this class allows the user to measure their heart rate using the built in phone infared sensor. The user can choose to submit this information the LogFoodFragment.
```

## How it works

#### Login/Register (Main Activity)
* Screen that user sees first time they open the app. Has user input their age, height, weight, gender, and email. This is like a registration screent that the user never sees again. This way, our recommendation system has initial information about the user.

#### Menu Options (Menu Activity)
Provides a menu for the user to what they want to select. The options are: 
* Food Journal
* Log (Log Food & Collect Heart Rate)
* User Profile
* Health State (View graphs & food recommendation list)

#### Food Journal
* Provides user with history of foods they ate along with classifying whether the food was good, neutral, or bad. 

#### Heart Rate/Food Input

* Heart rate can be measured from the LogFoodFragment. 
* Here the user is given the option to measure their before and after eating a meal or just to check their heart rate. Both of these cases will be stored in the device's database. 
* If the user measures their heart rate, submits a food option and measures their heart rate again, then the device will store the heart rates, and also store the HR diff with the food entry. 
* This information is used in determining the classification of the food(good, bad and neutral) and update the health state/body constitution of the user as it's being stored, which is reflected in the health state. 
* If the user continually measures their heart rate without submitting a food entry then the heart rates are stored as resting heart rates which are also reflected in the health state.


#### User Profile
* User can update their personal information.

#### Health State
* Displays a graph of the user's heart rate over a specified duration and displays a graph of their heart rate change over that same duration. Along with telling the user their current body constitution and giving them a list of food recommendations.

#### The Data Side
* The architecture of FoodSense consists of UI controllers, ViewModels that serves LiveData, a Repository, and a Room database. The Room database is backed by SQLite Database, accessed via data access objets (DAOs).


## Built With

* Android Studio 
* Room Database (backed by SQLite database)
* Samsung Heart Rate Sensor - Heart rate sensor

## Authors

* **Jessica Chen** - [Jessica's github](https://github.com/chenjessica1920)
* **Kayvaan Azad** - [Kavyaan's github](https://github.com/kazad123)
* **Hahnara Hyun** - [Hahnara's github](https://github.com/hahnarahyun)
* **Rymmy Andre** - [Rymmy's github](https://github.com/randre0)

