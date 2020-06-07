package game.main;


import java.util.InputMismatchException;
import java.util.Scanner;

import game.main.client.Client;
import game.main.packet.AddConnectionRequestPacket;
import game.main.server.Room;
import game.main.server.Server;


public class Main {
    public static void main( String[] args ) {
        boolean running = true;
        while (running) {
            printMenu();
            running = handleUserOption();
        }
    }

    private static boolean handleUserOption() {
        try {
            Scanner scanner = new Scanner(System.in);
            int opt = scanner.nextInt();
            switch (opt) {
                case 1: {
                    createNewGame();
                    break;
                }
                case 2: {
                    joinGame(false);
                    break;
                }
                case 3: {
                    System.out.println("Thank you for using!");
                    return false;
                }
                default:
                    System.out.println("PLEASE choose option from 1 -> 3!");
            }
            return true;
        } catch (InputMismatchException e) {
            System.out.println("PLEASE choose option from 1 -> 3!");
            return true;
        }
    }

    private static void joinGame(boolean isMaster) {
        Scanner scanner = new Scanner(System.in);
        String playerName = enterPlayerName(scanner);
        Client client = new Client(Config.HOST, Config.PORT);
        client.playerName = playerName;
        client.connect();

        try {

            AddConnectionRequestPacket addConnectionRequestPacket = new AddConnectionRequestPacket(playerName, isMaster);
            client.sendObject(addConnectionRequestPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }

        GameSetup gameSetup = new GameSetup("Game may bay", 700, 700);
        gameSetup.start();
        
        while (true) {
        	System.out.print("Enter y to start game, n to exit game: ");
            String command = scanner.nextLine();
            if (command.equals("x")) {
                client.close();
                break;
            } else if (command.equals("y")) {
            	GameSetup game = new GameSetup("SkyForce Game", 500, 600);
                game.start();
            }
        }
    }

    private static String enterPlayerName(Scanner scanner) {
        boolean isDone = false;
        String name = "";
        do {
            try {
                System.out.print("Enter your name: ");
                name = scanner.nextLine();
                if (name.length() > 16) {
                    System.out.println("Name length must be less than 16!");
                } else {
                    isDone = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Name is unacceptable!");
            }
        } while (!isDone);
        return name;
    }

    private static void createNewGame() {
        Room room = new Room(Config.PORT);
        room.start();

        joinGame(true);
    }

    private static void printMenu() {
        System.out.print(new StringBuilder()
                .append("1. Create new game\n")
                .append("2. Join game\n")
                .append("3. Quit\n")
                .append("Your choice: ")
                .toString()
        );
    }
}