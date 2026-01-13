package budget;

public class PurchaseView {
    public final String category;
    public final String name;
    public final double price;

    public PurchaseView(String category, String name, double price) {
        this.category = category;
        this.name = name;
        this.price = price;
    }
}
