package budget.analyze;

import budget.Main;
import budget.Purchase;
import budget.TypeTotal;

import java.util.*;

public class SortByTypeStrategy implements AnalyzeStrategy {

    private final Map<String, List<Purchase>> purchasesByCategory;

    public SortByTypeStrategy(Map<String, List<Purchase>> purchasesByCategory) {
        this.purchasesByCategory = purchasesByCategory;
    }

    @Override
    public void execute() {
        List<TypeTotal> totals = new ArrayList<>();

        for (var entry : purchasesByCategory.entrySet()) {
            double sum = 0.0;
            for (Purchase p : entry.getValue()) sum += p.price;
            totals.add(new TypeTotal(entry.getKey(), sum));
        }

        totals.sort((a, b) -> Double.compare(b.sum, a.sum));

        System.out.println("Types:");
        double grandTotal = 0.0;

        for (TypeTotal t : totals) {
            System.out.println(t.category + " - " + Main.money(t.sum));
            grandTotal += t.sum;
        }

        System.out.println("Total sum: " + Main.money(grandTotal));
    }
}
