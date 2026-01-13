package budget;

import budget.analyze.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Main {

    static final Scanner scanner = new Scanner(System.in);

    static double income = 0.0;

    static final String FOOD = "Food";
    static final String CLOTHES = "Clothes";
    static final String ENTERTAINMENT = "Entertainment";
    static final String OTHER = "Other";

    static final Map<String, List<Purchase>> purchasesByCategory = new LinkedHashMap<>();

    static {
        purchasesByCategory.put(FOOD, new ArrayList<>());
        purchasesByCategory.put(CLOTHES, new ArrayList<>());
        purchasesByCategory.put(ENTERTAINMENT, new ArrayList<>());
        purchasesByCategory.put(OTHER, new ArrayList<>());
    }

    static final File PURCHASES_FILE = new File("purchases.txt");

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        while (true) {
            printMainMenu();
            int choice = readInt();

            switch (choice) {
                case 1 -> addIncome();
                case 2 -> addPurchase();
                case 3 -> showPurchases();
                case 4 -> showBalance();
                case 5 -> save();
                case 6 -> load();
                case 7 -> analyze();
                case 0 -> { System.out.println("Bye!"); return; }
                default -> {}
            }
        }
    }

    // ----- analyze -----
    static void analyze() {
        while (true) {
            printSortMenu();
            int choice = readInt();

            if (choice == 4) {
                System.out.println();
                return;
            }

            Analyzer analyzer = new Analyzer();

            switch (choice) {
                case 1 -> analyzer.setStrategy(new SortAllStrategy(purchasesByCategory));
                case 2 -> analyzer.setStrategy(new SortByTypeStrategy(purchasesByCategory));
                case 3 -> {
                    String category = askCategoryForCertainSort();
                    if (category == null) continue;
                    analyzer.setStrategy(new SortCertainTypeStrategy(purchasesByCategory, category));
                }
                default -> { System.out.println(); continue; }
            }

            analyzer.run();
            System.out.println();
        }
    }

    static String askCategoryForCertainSort() {
        while (true) {
            System.out.println("Choose the type of purchase");
            System.out.println("1) Food");
            System.out.println("2) Clothes");
            System.out.println("3) Entertainment");
            System.out.println("4) Other");
            System.out.println();
            int c = readInt();

            String category = categoryFromChoice(c);
            if (category != null) return category;
        }
    }

    // ----- menus -----
    static void printMainMenu() {
        System.out.println("Choose your action:");
        System.out.println("1) Add income");
        System.out.println("2) Add purchase");
        System.out.println("3) Show list of purchases");
        System.out.println("4) Balance");
        System.out.println("5) Save");
        System.out.println("6) Load");
        System.out.println("7) Analyze (Sort)");
        System.out.println("0) Exit");
        System.out.println();
    }
    static void printSortMenu() {
        System.out.println("How do you want to sort?");
        System.out.println("1) Sort all purchases");
        System.out.println("2) Sort by type");
        System.out.println("3) Sort certain type");
        System.out.println("4) Back");
        System.out.println();
    }
    static void printPurchaseTypeMenuForAdd() {
        System.out.println("Choose the type of purchase");
        System.out.println("1) Food");
        System.out.println("2) Clothes");
        System.out.println("3) Entertainment");
        System.out.println("4) Other");
        System.out.println("5) Back");
        System.out.println();
    }
    static void printPurchaseTypeMenuForShow() {
        System.out.println("Choose the type of purchases");
        System.out.println("1) Food");
        System.out.println("2) Clothes");
        System.out.println("3) Entertainment");
        System.out.println("4) Other");
        System.out.println("5) All");
        System.out.println("6) Back");
        System.out.println();
    }

    static void save(){
        try (PrintWriter printWriter = new PrintWriter(PURCHASES_FILE)){
            printWriter.println("INCOME|" + income);
            for(var entry : purchasesByCategory.entrySet()){
                String category = entry.getKey();
                for (Purchase p : entry.getValue()){
                    printWriter.println(category + "|" + p.name + "|" + p.price);
                }
            }
            System.out.println("Purchases were saved!\n");
        }catch (IOException e){
            System.out.println("Error while saving!");
        }
    }

    static void load(){
        if (!PURCHASES_FILE.exists()) {
            System.out.println("Purchases were not loaded!\n");
            return;
        }
        income = 0.0;
        for (var list : purchasesByCategory.values()){ list.clear(); }
        try (Scanner fielScanner = new Scanner(PURCHASES_FILE)){
            while (fielScanner.hasNextLine()){
                String line = fielScanner.nextLine();
                String[] parts = line.split("\\|");
                if (parts[0].equals("INCOME")) {
                    income = Double.parseDouble(parts[1]);
                } else {
                    String category = parts[0];
                    String name = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    purchasesByCategory .get(category) .add(new Purchase(name, price));
                }
            } System.out.println("Purchases were loaded!\n");
        }catch (IOException e){
            System.out.println("Error while loading purchases!");
        }
    }

    static void addIncome() {
        System.out.println("Enter income:");
        income += readDouble();
        System.out.println("Income was added!");
        System.out.println();
    }

    static void addPurchase() {
        while (true) {
            printPurchaseTypeMenuForAdd();
            int choice = readInt();
            if (choice == 5) { System.out.println();
                return;
            } String category = categoryFromChoice(choice);
            if (category == null) {
                System.out.println();
                continue;
            }
            System.out.println("Enter purchase name:");
            String name = scanner.nextLine();
            System.out.println("Enter its price:");
            double price = readDouble();
            purchasesByCategory.get(category).add(new Purchase(name, price));
            System.out.println("Purchase was added!");
            System.out.println();
        }
    }

    static void showPurchases() {
        while (true) {
            printPurchaseTypeMenuForShow();
            int choice = readInt();
            if (choice == 6) {
                System.out.println();
                return;
            }
            if (choice == 5) {
                showAll();
                System.out.println();
                continue;
            }
            String category = categoryFromChoice(choice);

            if (category == null) {
                System.out.println();
                continue;
            }
            showCategory(category);
            System.out.println();
        }
    }

    static void showCategory(String category) {
        List<Purchase> list = purchasesByCategory.get(category);
        System.out.println(category + ":"); if (list.isEmpty()) {
            System.out.println("The purchase list is empty!");
            return;
        }
        double sum = 0.0;
        for (Purchase p : list) {
            System.out.printf("%s $%.2f%n", p.name, p.price); sum += p.price;
        }
        System.out.printf("Total sum: $%.2f%n", sum);
    }

    static void showBalance() {
        double spent = totalSpent();
        double balance = income - spent;
        System.out.printf("Balance: $%.2f%n%n", balance);
    }

    // ----- helpers -----
    static String money(double v) {
        if (Math.abs(v) < 1e-7) return "$0";
        return String.format("$%.2f", v);
    }

    static String categoryFromChoice(int choice) {
        return switch (choice) {
            case 1 -> FOOD;
            case 2 -> CLOTHES;
            case 3 -> ENTERTAINMENT;
            case 4 -> OTHER;
            default -> null;
        };
    }

    static double totalSpent() {
        double total = 0.0;
        for (List<Purchase> list : purchasesByCategory.values()) {
            for (Purchase p : list) {
                total += p.price;
            }
        }
        return total;
    }

    static void showAll() {
        System.out.println("All:");

        double total = 0.0;
        boolean empty = true;

        for (List<Purchase> list : purchasesByCategory.values()) {
            for (Purchase p : list) {
                System.out.printf("%s $%.2f%n", p.name, p.price);
                total += p.price;
                empty = false;
            }
        }

        if (empty) {
            System.out.println("The purchase list is empty!");
        } else {
            System.out.printf("Total sum: $%.2f%n", total);
        }
    }

    static int readInt() {
        int v = scanner.nextInt();
        scanner.nextLine();
        return v;
    }

    static double readDouble() {
        double v = scanner.nextDouble();
        scanner.nextLine();
        return v;
    }
}