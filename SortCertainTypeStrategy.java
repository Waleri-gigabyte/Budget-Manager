package budget.analyze;

import budget.Main;
import budget.Purchase;

import java.util.*;

public class SortCertainTypeStrategy implements AnalyzeStrategy {

    private final Map<String, List<Purchase>> purchasesByCategory;
    private final String category;

    public SortCertainTypeStrategy(Map<String, List<Purchase>> purchasesByCategory, String category) {
        this.purchasesByCategory = purchasesByCategory;
        this.category = category;
    }

    @Override
    public void execute() {
        List<Purchase> list = purchasesByCategory.get(category);

        if (list == null || list.isEmpty()) {
            System.out.println("The purchase list is empty!");
            return;
        }

        List<Purchase> copy = new ArrayList<>(list);
        copy.sort((a, b) -> Double.compare(b.price, a.price));

        System.out.println(category + ":");
        double sum = 0.0;
        for (Purchase p : copy) {
            System.out.printf("%s $%.2f%n", p.name, p.price);
            sum += p.price;
        }

        System.out.println("Total sum: " + Main.money(sum));
    }
}
