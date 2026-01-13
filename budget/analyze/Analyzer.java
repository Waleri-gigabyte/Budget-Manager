package budget.analyze;

public class Analyzer {
    private AnalyzeStrategy strategy;

    public void setStrategy(AnalyzeStrategy strategy) {
        this.strategy = strategy;
    }

    public void run() {
        strategy.execute();
    }
}
