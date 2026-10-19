import javax.swing.JOptionPane;


//GMU Campus Budget & Calorie Meal Planner


public class MealPlanner {

   static final int FEMALE_GOAL = 2000;
   static final int MALE_GOAL = 2500;
   static final int CALORIES_PER_MILE = 100;
      

   // Menu stored as parallel arrays: index i in each array describes the same meal
   static String[] breakfastNames = {"Oatmeal with fruit", "Eggs and toast", "Breakfast burrito"};
   static double[] breakfastCosts = {5.0, 6.0, 7.0};
   static int[] breakfastCalories = {350, 400, 550};


   static String[] lunchNames = {"Turkey sandwich", "Pasta with marinara", "Grilled chicken salad"};
   static double[] lunchCosts = {8.0, 9.0, 8.0};
   static int[] lunchCalories = {600, 750, 500};


   static String[] dinnerNames = {"Beef and rice bowl", "Pizza (2 slices)", "Salmon with vegetables"};
   static double[] dinnerCosts = {11.0, 10.0, 12.0};
   static int[] dinnerCalories = {850, 700, 650};


   // The cheapest meal on the menu (Oatmeal with fruit)
   static final double CHEAPEST_MEAL = 5.0;


   static final String TITLE = "GMU Meal Planner";


   public static void main(String[] args) {
       showMessage("Welcome to the GMU Campus Budget & Calorie Meal Planner!");


       // Get the user's budget and calorie goal
       double budget = askForBudget();
       int goal = askForCalorieGoal();
       showMessage("Your daily calorie goal is " + goal + " calories.");


       int totalCalories = 0;


       if (budget < CHEAPEST_MEAL) {
           JOptionPane.showMessageDialog(null, "Your budget of $" + budget + " isn't enough for a meal. The cheapest meal costs $" + CHEAPEST_MEAL);
       }


       // Meal loop: runs until the user stops, runs out of money, or reaches the goal
       boolean keepEating = budget >= CHEAPEST_MEAL;
       while (keepEating) {
           String mealType = askForMealType();


           // Pick the right menu arrays for the chosen meal
           String[] names;
           double[] costs;
           int[] calories;
           if (mealType.equals("breakfast")) {
               names = breakfastNames;
               costs = breakfastCosts;
               calories = breakfastCalories;
           } else if (mealType.equals("lunch")) {
               names = lunchNames;
               costs = lunchCosts;
               calories = lunchCalories;
           } else {
               names = dinnerNames;
               costs = dinnerCosts;
               calories = dinnerCalories;
           }


           // Build the menu text and get the choice (1-3 becomes array index 0-2)
           String menu = "Here are today's " + mealType + " options:\n";
           for (int i = 0; i < names.length; i++) {
               menu += (i + 1) + ". " + names[i] + " - $" + costs[i] + " (" + calories[i] + " calories)\n";
           }
           int index = askForChoice(menu) - 1;


           // Too expensive: tell the user and let them choose again
           if (costs[index] > budget) {
               JOptionPane.showMessageDialog(null, "Sorry, the " + names[index] + " costs $" + costs[index] + " and you don't have enough money. \nLet's pick something else.");

           }else {


           // Update the budget and calorie total
           budget = budget - costs[index];
           totalCalories = totalCalories + calories[index];
           JOptionPane.showMessageDialog(null, "Enjoy your " + names[index] + "!\n" + "That cost $" + costs[index] + " and has " + calories[index] + " calories.\n"
                   + "Remaining budget: $" + budget + "\n" 
                   + "Total calories so far: " + totalCalories);


           // Decide whether to keep going
           if (budget < CHEAPEST_MEAL) {
               showMessage("You don't have enough budget left for another meal today.");
               keepEating = false;
           } else if (totalCalories >= goal) {
               showMessage("You've reached or exceeded your calorie goal for the day.");
               keepEating = false;
           } else {
               keepEating = askForAnotherMeal();
           }
       }
       }

       // End-of-day summary
       String summary = "===== Daily Summary =====\n"
               + "Total calories consumed: " + totalCalories + "\n"
               + "Daily calorie goal: " + goal + "\n"
               + "Remaining budget: $" + budget + "\n\n";


       if (totalCalories < goal) {
           summary += "You are " + (goal - totalCalories) + " calories under your goal.\n"
                   + "Great job staying within your calorie goal today! Go Patriots!";
       } else if (totalCalories == goal) {
           summary += "You hit your goal exactly!\n"
                   + "Great job staying within your calorie goal today! Go Patriots!";
       } else {
           int extraCalories = totalCalories - goal;
           double miles = (double) extraCalories / CALORIES_PER_MILE;
           summary += "You are " + extraCalories + " calories over your goal.\n"
                   + "To burn off the extra calories, try jogging about " + miles + " miles around campus.";
       }
       showMessage(summary);
   }


   /** Shows a message in a dialog box. */
   public static void showMessage(String message) {
       JOptionPane.showMessageDialog(null, message, TITLE, JOptionPane.INFORMATION_MESSAGE);
   }


   /** Shows an input dialog and returns the trimmed answer. Exits if the user clicks Cancel. */
   public static String prompt(String message) {
       String answer = JOptionPane.showInputDialog(null, message, TITLE, JOptionPane.QUESTION_MESSAGE);
       if (answer == null) {
           showMessage("Goodbye!");
           System.exit(0);
       }
       return answer.trim();
   }


   /** Asks for the daily budget until a positive number is entered. */
   public static double askForBudget() {
       while (true) {
           String answer = prompt("What is your daily meal budget? $");
           try {
               double budget = Double.parseDouble(answer);
               if (budget > 0) {
                   return budget;
               }
               showMessage("Your budget must be greater than $0. Please try again.");
           } catch (NumberFormatException e) {
               showMessage("Please enter a valid number (for example, 25 or 30.50).");
           }
       }
   }


   /** Asks for gender (M or F) and returns the matching calorie goal. */
   public static int askForCalorieGoal() {
       while (true) {
           String answer = prompt("What is your gender? (M/F):");
           if (answer.equalsIgnoreCase("M")) {
               return MALE_GOAL;
           }
           if (answer.equalsIgnoreCase("F")) {
               return FEMALE_GOAL;
           }
           showMessage("Please enter M or F.");
       }
   }


   /** Asks which meal to eat and returns "breakfast", "lunch", or "dinner". */
   public static String askForMealType() {
       while (true) {
           String answer = prompt("Which meal? Breakfast (B), Lunch (L), Dinner (D):");
           if (answer.equalsIgnoreCase("B")) {
               return "breakfast";
           }
           if (answer.equalsIgnoreCase("L")) {
               return "lunch";
           }
           if (answer.equalsIgnoreCase("D")) {
               return "dinner";
           }
           showMessage("Please enter B, L, or D.");
       }
   }


   /** Shows the menu and asks for a choice until 1, 2, or 3 is entered. */
   public static int askForChoice(String menu) {
       while (true) {
           String answer = prompt(menu + "\nEnter your choice (1-3):");
           try {
               int choice = Integer.parseInt(answer);
               if (choice >= 1 && choice <= 3) {
                   return choice;
               }
           } catch (NumberFormatException e) {
               // fall through to the error message below
           }
           showMessage("Please enter 1, 2, or 3.");
       }
   }


   /** Asks whether the user wants another meal (Y/N). */
   public static boolean askForAnotherMeal() {
       while (true) {
           String answer = prompt("Want another meal? (Y/N):");
           if (answer.equalsIgnoreCase("Y")) {
               return true;
           }
           if (answer.equalsIgnoreCase("N")) {
               return false;
           }
           showMessage("Please enter Y or N.");
       }
   }
}
