package com.businessvision;

import java.io.File;
import java.util.Scanner;

public class Main {

    public static void printMenu(String[] options) {
        for (String option : options) {
            System.out.println(option);
        }
        System.out.print("Choose your option : ");
    }

    private static String[] options = {
            "1- Print most visited sites",
            "2- Print most unique website visits",
            "3- Exit",
    };

    public static void main(String[] args) {

        File file = new File(System.getProperty("user.dir") + "/com/businessvision/webserver.log");
        Scanner scanner = new Scanner(System.in);
        printMenu(options);
        int option = 1;

        while (option != 3) {
            try {
                option = scanner.nextInt();
                switch (option) {
                    case 1:
                        MapUtil.printVisits(file);
                        System.out.print("\n");
                        printMenu(options);
                        break;
                    case 2:
                        MapUtil.printUniqueViews(file);
                        System.out.print("\n");
                        printMenu(options);
                        break;
                    case 3:
                        System.out.println("Good bye!");
                        break;
                    default:
                        System.out.println("Please enter an integer value between 1 and " + options.length);
                }
            } catch (Exception ex) {
                System.out.println("Please enter an integer value between 1 and " + options.length);
                scanner.next();
            }
        }
    }

}


