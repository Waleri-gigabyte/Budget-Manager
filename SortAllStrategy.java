package budget.analyze;

import budget.Main;
import budget.Purchase;
import budget.PurchaseView;

import java.util.*;

public class SortAllStrategy  implements  AnalyzeStrategy{

    private final Map<String, List<Purchase>> purchasesByCategory;

    public SortAllStrategy(Map<String, List<Purchase>> purchasesByCategory) {
        this.purchasesByCategory = purchasesByCategory;
    }

    @Override
    public void execute() {
        List<PurchaseView> all = new ArrayList<>();

        for (var entry : purchasesByCategory.entrySet()) {
            for (Purchase p : entry.getValue()) {
                all.add(new PurchaseView(entry.getKey(), p.name, p.price));
            }
        }

        if (all.isEmpty()) {
            System.out.println("The purchase list is empty!");
            return;
        }
        all.sort((a, b) -> Double.compare(b.price, a.price));

        System.out.println("All:");
        double sum = 0.0;

        for (PurchaseView p : all) {
            System.out.printf("%s $%.2f%n", p.name, p.price);
            sum += p.price;
        }

        System.out.println("Total: " + Main.money(sum));
    }
}
