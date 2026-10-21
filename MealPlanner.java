
import javax.swing.JOptionPane;
 
//GMU Campus Budget & Calorie Meal Planner
public class MealPlanner {
   public static void main(String[] args) {
      final double CHEAPEST_MEAL = 5.0;
      final double CALORIES_PER_MILE = 100.0;
 
      JOptionPane.showMessageDialog(null, "Welcome to the GMU Campus Budget & Calorie Meal Planner!");
 
      //Get the user's budget and calorie goal
      double budget = askForBudget();
      int goal = askForCalorieGoal();
      JOptionPane.showMessageDialog(null, "Your daily calorie goal is " + goal + " calories.");
 
      int totalCalories = 0;
      boolean keepEating = true;
 
      if (budget < CHEAPEST_MEAL) {
         JOptionPane.showMessageDialog(null, "Your budget of $" + budget + " isn't enough for a meal. The cheapest meal costs $" + CHEAPEST_MEAL);
         keepEating = false;
      }
 
      //Meal loop: runs until the user stops, runs out of money, or reaches the goal
      while (keepEating) {
         //1 = breakfast, 2 = lunch, 3 = dinner
         int mealType = askForNumber("Which meal?\n1 = Breakfast\n2 = Lunch\n3 = Dinner", 1, 3);
         String mealTypeName = getMealTypeName(mealType);
 
         //Build the menu for the chosen meal (choices 1 to 3)
         String menu = "Here are today's " + mealTypeName + " options:\n";
         for (int i = 1; i <= 3; i++) {
            menu += i + ". " + getMealName(mealType, i) + " - $" + getMealCost(mealType, i) + " (" + getMealCalories(mealType, i) + " calories)\n";
         }
         int choice = askForNumber(menu + "\nEnter your choice (1-3):", 1, 3);
 
         String mealName = getMealName(mealType, choice);
         double mealCost = getMealCost(mealType, choice);
         int mealCalories = getMealCalories(mealType, choice);
 
         //Too expensive: tell the user and let them choose again
         if (mealCost > budget) {
            JOptionPane.showMessageDialog(null, "Sorry, the " + mealName + " costs $" + mealCost + " and you don't have enough money.\nLet's pick something else.");
         }
         else {
            //Update the budget and calorie total
            budget -= mealCost;
            totalCalories += mealCalories;
            JOptionPane.showMessageDialog(null, "Enjoy your " + mealName + "!\nThat cost $" + mealCost + " and has " + mealCalories + " calories.\n"
                  + "Remaining budget: $" + budget + "\n"
                  + "Total calories so far: " + totalCalories);
 
            //Decide whether to keep going
            if (budget < CHEAPEST_MEAL) {
               JOptionPane.showMessageDialog(null, "You don't have enough budget left for another meal today.");
               keepEating = false;
            }
            else if (totalCalories >= goal) {
               JOptionPane.showMessageDialog(null, "You've reached or exceeded your calorie goal for the day.");
               keepEating = false;
            }
            else {
               keepEating = askForAnotherMeal();
            }
         }
      }
 
      //End-of-day summary
      String summary = "===== Daily Summary =====\n"
            + "Total calories consumed: " + totalCalories + "\n"
            + "Daily calorie goal: " + goal + "\n"
            + "Remaining budget: $" + budget + "\n\n";
 
      if (totalCalories < goal) {
         summary += "You are " + (goal - totalCalories) + " calories under your goal.\n"
               + "Great job staying within your calorie goal today! Go Patriots!";
      }
      else if (totalCalories == goal) {
         summary += "You hit your goal exactly!\n"
               + "Great job staying within your calorie goal today! Go Patriots!";
      }
      else {
         int extraCalories = totalCalories - goal;
         double miles = extraCalories / CALORIES_PER_MILE;
         summary += "You are " + extraCalories + " calories over your goal.\n"
               + "To burn off the extra calories, try jogging about " + miles + " miles around campus.";
      }
      JOptionPane.showMessageDialog(null, summary);
   }
 
   //Asks for a whole number from low to high and keeps asking until it gets one
   public static int askForNumber(String prompt, int low, int high) {
      int number = 0;
      do {
         try {
            number = Integer.parseInt(JOptionPane.showInputDialog(prompt));
         }
         catch (NumberFormatException e) {
            number = 0;
         }
         if (number < low || number > high)
            JOptionPane.showMessageDialog(null, "Please enter a number from " + low + " to " + high + ".");
      } while (number < low || number > high);
      return number;
   }
 
   //Asks for the daily budget until a number greater than 0 is entered
   public static double askForBudget() {
      double budget = 0;
      do {
         try {
            budget = Double.parseDouble(JOptionPane.showInputDialog("What is your daily meal budget? $"));
         }
         catch (NumberFormatException e) {
            budget = 0;
         }
         if (budget <= 0)
            JOptionPane.showMessageDialog(null, "Please enter a number greater than 0 (for example 25 or 30.50).");
      } while (budget <= 0);
      return budget;
   }
 
   //Asks for gender (1 = male, 2 = female) and returns the matching calorie goal
   public static int askForCalorieGoal() {
      final int FEMALE_GOAL = 2000;
      final int MALE_GOAL = 2500;
      int goal;
      int gender = askForNumber("What is your gender?\n1 = Male\n2 = Female", 1, 2);
      if (gender == 1)
         goal = MALE_GOAL;
      else
         goal = FEMALE_GOAL;
      return goal;
   }
 
   //Asks whether the user wants another meal (1 = yes, 2 = no) and returns true or false
   public static boolean askForAnotherMeal() {
      boolean another;
      int answer = askForNumber("Want another meal?\n1 = Yes\n2 = No", 1, 2);
      if (answer == 1)
         another = true;
      else
         another = false;
      return another;
   }
 
   //Returns the name of the meal type (1 = breakfast, 2 = lunch, 3 = dinner)
   public static String getMealTypeName(int mealType) {
      String typeName;
      if (mealType == 1)
         typeName = "breakfast";
      else if (mealType == 2)
         typeName = "lunch";
      else
         typeName = "dinner";
      return typeName;
   }
 
   //Returns the name of a menu item
   public static String getMealName(int mealType, int choice) {
      String name;
      if (mealType == 1) {
         if (choice == 1)
            name = "Oatmeal with fruit";
         else if (choice == 2)
            name = "Eggs and toast";
         else
            name = "Breakfast burrito";
      }
      else if (mealType == 2) {
         if (choice == 1)
            name = "Turkey sandwich";
         else if (choice == 2)
            name = "Pasta with marinara";
         else
            name = "Grilled chicken salad";
      }
      else {
         if (choice == 1)
            name = "Beef and rice bowl";
         else if (choice == 2)
            name = "Pizza (2 slices)";
         else
            name = "Salmon with vegetables";
      }
      return name;
   }
 
   //Returns the cost of a menu item
   public static double getMealCost(int mealType, int choice) {
      double cost;
      if (mealType == 1) {
         if (choice == 1)
            cost = 5.0;
         else if (choice == 2)
            cost = 6.0;
         else
            cost = 7.0;
      }
      else if (mealType == 2) {
         if (choice == 1)
            cost = 8.0;
         else if (choice == 2)
            cost = 9.0;
         else
            cost = 8.0;
      }
      else {
         if (choice == 1)
            cost = 11.0;
         else if (choice == 2)
            cost = 10.0;
         else
            cost = 12.0;
      }
      return cost;
   }
 
   //Returns the calories of a menu item
   public static int getMealCalories(int mealType, int choice) {
      int calories;
      if (mealType == 1) {
         if (choice == 1)
            calories = 350;
         else if (choice == 2)
            calories = 400;
         else
            calories = 550;
      }
      else if (mealType == 2) {
         if (choice == 1)
            calories = 600;
         else if (choice == 2)
            calories = 750;
         else
            calories = 500;
      }
      else {
         if (choice == 1)
            calories = 850;
         else if (choice == 2)
            calories = 700;
         else
            calories = 650;
      }
      return calories;
   }
}
