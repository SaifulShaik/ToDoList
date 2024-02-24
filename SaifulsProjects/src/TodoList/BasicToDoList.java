package TodoList;
import java.util.*;
import java.io.*;

public class BasicToDoList {
    static ArrayList<String> items = new ArrayList<>();
    static Scanner input = new Scanner(System.in);
    static String[] options = {"options", "Add", "Remove", "Clear", "Save", "Exit"};
    static final String todolist = "todolist.txt";
    static String dash = "---------------------------";

    public static void main(String[] args) {
        loadItemsFromFile();
        initializeList();
        saveItemsToFile();
    }

    public static void initializeList() {
        if (items.size() > 0) {
            displayList(items);
        } else {
        	System.out.println(dash);
            System.out.println("No items in the list.\nDo you want to add items(yes/no)?");
            String ans = input.nextLine().toLowerCase(Locale.ROOT);

            if (ans.equals("yes")) {
                addItem();
            } else if (ans.equals("no")) {
            	System.out.println(dash);
                System.out.println("Thanks for your business!");
                System.out.println("Quiting Application...");
            } else {
                System.out.println("Invalid answer");
            }
        }
    }

    public static void displayList(ArrayList<String> itemsArray) {
        int itemsList = getListLength(itemsArray);

        if (itemsList > 0) {
        	System.out.println("\n"+dash);
            int chartWidth = itemsList + 5;
            int sidePadding = 1;
            String horizontalLine = new String(new char[chartWidth - sidePadding]).replace('\0', '-');
            System.out.println("\nVersion: 02/19/2023");
            System.out.println(" " + horizontalLine);
            System.out.printf("|  %-"+ itemsList +"s  |\n", "Items");
            System.out.println(" "+ horizontalLine);

            for (String item : itemsArray) {
                System.out.printf("| "+(items.indexOf(item)+1)+". %-"+ itemsList +"s |\n", item);
            }
            System.out.println(" -" + horizontalLine);
            displayOptions(options.length - 1);
        } else {
            System.out.println("No items in the list.");
            displayOptions(options.length - 1);
        }
    }

    private static void displayOptions(int optionsIndex) {
        System.out.println();
        for (int placement = 0; placement < optionsIndex; placement++) {
            if (placement == 0) {
                System.out.println(options[placement]);
                placement++;
            }
            System.out.println(placement + ". " + options[placement]);
        }

        System.out.print("Choice(1-" + (optionsIndex-1) + "): ");
        try {
            int choice = Integer.parseInt(input.nextLine());
            switch (choice) {
                case 1:
                    addItem();
                    break;
                case 2:
                    removeItem();
                    break;
                case 3:
                    clearList(options.length);
                    break;
                case 4:
                    break;
                case 5:
                	saveItemsToFile();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number between 1 and 5.");
        }
    }

    public static void addItem() {
    	System.out.println("\n"+dash);
        System.out.println("Add an item to your list: ");
        String addIn = input.nextLine().toLowerCase(Locale.ROOT);
        if (!addIn.trim().isEmpty()) {
            items.add(addIn);
            initializeList();
        } else {
            System.out.println("Invalid item");
        }
    }

    public static void removeItem() {
        if (items.size() > 0) {
            System.out.println("Remove an item from your list: ");
            String removeIn = input.nextLine().toLowerCase(Locale.ROOT);
            try {
            	int indexNum = Integer.parseInt(removeIn);
            	if(indexNum <= items.size()) {
            		items.remove(indexNum-1);
            	} else {
            		System.out.println("This item isnt on the list");
            	}
            } catch (NumberFormatException e) {
            	items.remove(removeIn);
            }
            initializeList();
        } else {
            System.out.println("No items in the list");
        }
    }

    public static void clearList(int itemsLength) {
        if (items.size() > 0) {
            System.out.println("Would you like to clear your list (yes/no)? ");
            String clear = input.nextLine().toLowerCase(Locale.ROOT);
            if (clear.equals("yes")) {
                items.clear();
            } else if (clear.equals("no")) {
                System.out.println("Ok");
            }
        } else {
            System.out.println("List is already clear");
        }
    }

    private static int getListLength(ArrayList<String> itemsArray) {
        int maxLength = 0;
        for (String item : itemsArray) {
            maxLength = Math.max(maxLength, item.length());
        }
        return maxLength;
    }

    private static void loadItemsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(todolist))) {
            String line;
            while ((line = reader.readLine()) != null) {
                items.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error loading items from file: " + e.getMessage());
        }
    }

    private static void saveItemsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(todolist))) {
            for (String item : items) {
                writer.write(item);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving items to file: " + e.getMessage());
        }
    }
}
