# Budget Manager

Console-based budget management application written in Java.

The program allows users to track income and expenses, store purchases by category, persist data between runs, and analyze expenses using different sorting strategies implemented with the **Strategy design pattern**.

---

## ✨ Features

- Add income
- Add purchases by category:
  - Food
  - Clothes
  - Entertainment
  - Other
- View purchases by category or all at once
- Calculate current balance
- Save and load data from file
- Analyze expenses:
  - Sort all purchases by price (descending)
  - Sort total expenses by category
  - Sort purchases within a specific category

---

## Technologies & Concepts

- Java 17
- Collections (`Map`, `List`)
- File I/O (`Scanner`, `PrintWriter`)
- **Strategy Design Pattern**
- Clean code structure with packages

---

## 📁 Project Structure
```text
src/main/java
└── budget
├── Main.java
├── Purchase.java
├── PurchaseView.java
├── TypeTotal.java
└── analyze
├── Analyzer.java
├── AnalyzeStrategy.java
├── SortAllStrategy.java
├── SortByTypeStrategy.java
└── SortCertainTypeStrategy.java
```

## Strategy Pattern Usage

The expense analysis feature is implemented using the Strategy pattern:

- `AnalyzeStrategy` – common interface for all sorting strategies
- `SortAllStrategy` – sorts all purchases by price
- `SortByTypeStrategy` – sorts categories by total expense
- `SortCertainTypeStrategy` – sorts purchases inside a chosen category
- `Analyzer` – context class that executes the selected strategy at runtime

This approach allows adding new analysis strategies without modifying existing code.

---

## ▶️ How to Run

1. Clone the repository
2. Open the project in IntelliJ IDEA (or another Java IDE)
3. Run `Main.java`
4. Interact with the application via the console menu

---

## 📸 Example
```text
- Choose your action:
- Add income
- Add purchase
- Show list of purchases
- Balance
- Save
- Load
- Analyze (Sort)
- Exit
```

---

## 👌 Author

Created as a learning project while studying Java, collections, file handling, and design patterns.

---

## 🧠 What I Learned

While working on this project, I learned and practiced:

- Designing console applications with clear menu-driven logic
- Working with Java collections (`Map`, `List`) to organize and aggregate data
- Reading from and writing to files to persist application state
- Applying the **Strategy design pattern** to encapsulate and switch algorithms at runtime
- Separating responsibilities between context, strategies, and data models
- Refactoring a large `Main` class into multiple classes and packages
- Improving code readability and maintainability through clean structure

This project helped me better understand how design patterns are used in real applications, not just in theory.


