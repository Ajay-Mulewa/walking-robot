package machine;

import java.util.Arrays;
import java.util.Scanner;

public class CoffeeMachine {

    public static void main(String[] args) {

        CoffeeMaker coffeeMaker = new CoffeeMaker(400, 540, 120, 9, 550);

        Scanner scanner = new Scanner(System.in);
        boolean showdown = false;
        System.out.println();
        System.out.println("Write action (buy, fill, take, remaining, exit):");
        String action;
        do {
            action = scanner.next();
            showdown = coffeeMaker.start(action);
        } while (!showdown);
    }

}

enum State {
    BUY, FILL, TAKE, REMAINING, EXIT, MENU
}

class CoffeeMaker {
    public int water = 400;
    public int milk = 540;
    public int coffeeBeans = 120;
    public int disposableCups = 9;
    public int cash = 550;
    public int fillState = 0;
    public State currentState;

    public CoffeeMaker(int water, int milk, int coffeeBeans, int disposableCups, int cash) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.disposableCups = disposableCups;
        this.cash = cash;
    }

    public void printCurrentState() {
        System.out.println();
        System.out.println("The coffee machine has:");
        System.out.printf("%d ml of water%n", water);
        System.out.printf("%d ml of milk%n", milk);
        System.out.printf("%d g of coffee beans%n", coffeeBeans);
        System.out.printf("%d disposable cups%n", disposableCups);
        System.out.printf("$%d of money%n", cash);
    }

    public boolean start(String action) {
        if (action.toUpperCase().equals(State.REMAINING.name())) {
            currentState = State.REMAINING;
            printCurrentState();
            menu();
        } else if (action.toUpperCase().equals(State.TAKE.name())) {
            currentState = State.TAKE;
            take();
            menu();
        } else if (action.toUpperCase().equals(State.EXIT.name())) {
            return true;
        } else if (action.toUpperCase().equals(State.BUY.name())) {
            currentState = State.BUY;
            System.out.println();
            System.out.println("What do you want to buy 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        } else if (action.toUpperCase().equals(State.FILL.name())) {
            currentState = State.FILL;
            System.out.println();
            System.out.println("Write how many ml of water you want to add:");
            fillState = 0;
        } else if (currentState == State.BUY) {
            if(action.matches("\\d+")) {
                buy(Integer.parseInt(action));
            }
            menu();
        } else if (currentState == State.FILL) {
            fill(Integer.parseInt(action));
            if(fillState == 4) {
                menu();
            }
        }

        return false;
    }

    public void menu() {
        currentState = State.MENU;
        System.out.println();
        System.out.println("Write action (buy, fill, take, remaining, exit):");
    }

    public void buy(int choice) {
        switch (choice) {
            case 1:
                if (stockCheck(250, 16, 0)) {
                    water -= 250;
                    coffeeBeans -= 16;
                    cash += 4;
                    disposableCups--;
                }
                break;
            case 2:
                if (stockCheck(350, 75, 20)) {
                    water -= 350;
                    milk -= 75;
                    coffeeBeans -= 20;
                    cash += 7;
                    disposableCups--;
                }
                break;
            case 3:
                if (stockCheck(200, 100, 12)) {
                    water -= 200;
                    milk -= 100;
                    coffeeBeans -= 12;
                    cash += 6;
                    disposableCups--;
                }
                break;
        }
    }

    public void fill(int value) {
        switch (fillState) {
            case 0:
                water += value;
                System.out.println("Write how many ml of milk you want to add:");
                break;
            case 1:
                milk += value;
                System.out.println("Write how many grams of coffee beans you want to add:");
                break;
            case 2:
                coffeeBeans += value;
                System.out.println("Write how many disposable cups of coffee you want to add:");
                break;
            case 3:
                disposableCups += value;
                break;
        }
        fillState++;
    }

    public void take() {
        System.out.printf("I gave you $%d", cash);
        cash = 0;
        System.out.println();
    }

    public boolean stockCheck(int requiredWater, int requiredMilk, int requiredCoffeeBeans) {
        if(water >= requiredWater && milk >= requiredMilk && coffeeBeans >= requiredCoffeeBeans && disposableCups != 0) {
            System.out.println("I have enough resources, making you a coffee!");
            return true;
        } else if (water < requiredWater) {
            System.out.println("Sorry, not enough water!");
        } else if (milk < requiredMilk) {
            System.out.println("Sorry, not enough milk!");
        } else if (coffeeBeans < requiredCoffeeBeans) {
            System.out.println("Sorry, not enough coffee beans");
        } else {
            System.out.println("Sorry, not enough disposable cups");
        }
        return false;
    }
}
