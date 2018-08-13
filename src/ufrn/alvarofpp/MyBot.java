package ufrn.alvarofpp;

import java.util.Scanner;

public class MyBot {

    private Scanner scan = new Scanner(System.in);

    public void run() {
        while (scan.hasNextLine()) {

            String line = scan.nextLine();

            if (line.length() == 0) { continue; }

            String[] parts = line.split(" ");
            switch (parts[0]) {
                case "settings":
                    // store game settings
                    break;
                case "update":
                    // store game updates
                    break;
                case "action":
                    System.out.println("right");
                    break;
                default:
                    // error
            }
        }
    }

    public static void main(String[] args) {
        //(new MyBot()).run();
        System.out.println("I sou um JAR");

        if (args.length > 0) {
            System.out.println("Argumentos:");
            for (String arg : args) {
                System.out.println(arg);
            }
        }
    }
}
