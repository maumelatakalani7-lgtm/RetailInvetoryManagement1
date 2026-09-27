/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proj1;

/**
 *
 * @author kowny
 */
import java.util.Scanner;

// ============================================================
// 1. PRODUCT MODEL
// ============================================================
class Product {
    int productId;
    String name;
    String category;
    int quantity;      // stock on hand
    double price;      // selling price per unit
    int reorderLevel;  // threshold that triggers reorder

    public Product(int productId, String name, String category,
                   int quantity, double price, int reorderLevel) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
        this.reorderLevel = reorderLevel;
    }

    public double totalValue() {
        return quantity * price;
    }

    public boolean isLowStock() {
        return quantity <= reorderLevel;
    }

    @Override
    public String toString() {
        String status = isLowStock() ? "*** REORDER NEEDED ***" : "OK";
        return String.format(
            "ID:%-4d | %-20s | %-12s | Qty:%-5d | R%-8.2f | Reorder@%-4d | %s",
            productId, name, category, quantity, price, reorderLevel, status);
    }
}

// ============================================================
// 2. SINGLY LINKED LIST IMPLEMENTED FROM SCRATCH
// ============================================================
class ProductLinkedList {

    private static class Node {
        Product data;
        Node next;

        Node(Product data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;
    private int size;

    public ProductLinkedList() {
        head = null;
        size = 0;
    }

    public int getSize() { return size; }
    public boolean isEmpty() { return head == null; }

    // -------- INSERT AT END — O(n) --------
    public void addLast(Product p) {
        Node newNode = new Node(p);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) current = current.next;
            current.next = newNode;
        }
        size++;
    }

    // -------- INSERT AT FRONT — O(1) --------
    public void addFirst(Product p) {
        Node newNode = new Node(p);
        newNode.next = head;
        head = newNode;
        size++;
    }

    // -------- REMOVE BY ID — O(n) --------
    public boolean removeById(int id) {
        if (head == null) return false;

        if (head.data.productId == id) {
            head = head.next;
            size--;
            return true;
        }

        Node current = head;
        while (current.next != null) {
            if (current.next.data.productId == id) {
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // -------- GET BY ID — O(n) --------
    public Product getById(int id) {
        Node current = head;
        while (current != null) {
            if (current.data.productId == id) return current.data;
            current = current.next;
        }
        return null;
    }

    // -------- CONVERT TO ARRAY --------
    public Product[] toArray() {
        Product[] arr = new Product[size];
        Node current = head;
        int i = 0;
        while (current != null) {
            arr[i++] = current.data;
            current = current.next;
        }
        return arr;
    }

    // -------- REBUILD LIST FROM ARRAY --------
    public void rebuildFromArray(Product[] arr) {
        head = null;
        size = 0;
        for (Product p : arr) addLast(p);
    }

    // -------- DISPLAY — O(n) --------
    public void display() {
        if (head == null) {
            System.out.println(">> Inventory is EMPTY.");
            return;
        }
        System.out.println("\n--- PRODUCT INVENTORY ---");
        Node current = head;
        int i = 1;
        while (current != null) {
            System.out.println(i++ + ". " + current.data);
            current = current.next;
        }
        System.out.println("Total products: " + size);
    }

    // -------- DISPLAY LOW STOCK ITEMS --------
    public void displayLowStock() {
        if (head == null) {
            System.out.println(">> Inventory is empty.");
            return;
        }
        System.out.println("\n--- PRODUCTS NEEDING REORDER ---");
        Node current = head;
        boolean found = false;
        while (current != null) {
            if (current.data.isLowStock()) {
                System.out.println("  " + current.data);
                found = true;
            }
            current = current.next;
        }
        if (!found) System.out.println("  All products are sufficiently stocked.");
    }

    // -------- TOTAL INVENTORY VALUE — O(n) --------
    public double totalInventoryValue() {
        double total = 0;
        Node current = head;
        while (current != null) {
            total += current.data.totalValue();
            current = current.next;
        }
        return total;
    }
}

// ============================================================
// 3. SEARCHING & SORTING (from scratch)
// ============================================================
class Algorithms {

    // ---------- LINEAR SEARCH BY ID — O(n) ----------
    public static int linearSearchById(Product[] arr, int id) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].productId == id) return i;
        }
        return -1;
    }

    // ---------- LINEAR SEARCH BY NAME (partial, case-insensitive) ----------
    public static int linearSearchByName(Product[] arr, String name) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].name.toLowerCase().contains(name.toLowerCase())) return i;
        }
        return -1;
    }

    // ---------- BUBBLE SORT BY NAME ----------
    public static void bubbleSortByName(Product[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j].name.compareToIgnoreCase(arr[j + 1].name) > 0) {
                    Product temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    // ---------- BUBBLE SORT BY PRICE ----------
    public static void bubbleSortByPrice(Product[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j].price > arr[j + 1].price) {
                    Product temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    // ---------- BUBBLE SORT BY QUANTITY ----------
    public static void bubbleSortByQuantity(Product[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j].quantity > arr[j + 1].quantity) {
                    Product temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }
}

// ============================================================
// 4. MAIN APPLICATION
// ============================================================
public class PROJ1 {

    private static final Scanner sc = new Scanner(System.in);
    private static final ProductLinkedList inventory = new ProductLinkedList();
    private static int nextId = 3001;

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("   RETAIL INVENTORY MANAGEMENT SYSTEM");
        System.out.println("=================================================");
        System.out.println(">> Inventory starts empty. Use option 1 to add products.");

        int choice;
        do {
            printMenu();
            choice = readInt("Enter choice: ");
            switch (choice) {
                case 1 -> addProduct();
                case 2 -> removeProduct();
                case 3 -> searchProduct();
                case 4 -> sortProducts();
                case 5 -> inventory.display();
                case 6 -> updateStock();
                case 7 -> inventory.displayLowStock();
                case 8 -> showTotalValue();
                case 9 -> reorderProduct();
                case 0 -> System.out.println("Exiting... Goodbye!");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private static void printMenu() {
        System.out.println("\n----------------- MENU -----------------");
        System.out.println("1. Add Product");
        System.out.println("2. Remove Product");
        System.out.println("3. Search Product");
        System.out.println("4. Sort Products");
        System.out.println("5. Display Inventory");
        System.out.println("6. Update Stock (in/out)");
        System.out.println("7. Show Reorder Alerts");
        System.out.println("8. Show Total Inventory Value");
        System.out.println("9. Reorder Product (restock)");
        System.out.println("0. Exit");
        System.out.println("----------------------------------------");
    }

    // ---------- 1. ADD ----------
    private static void addProduct() {
        System.out.print("Enter product name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter category: ");
        String category = sc.nextLine().trim();

        int qty = readInt("Enter quantity: ");
        double price = readDouble("Enter price: ");
        int reorderLevel = readInt("Enter reorder level: ");

        Product p = new Product(nextId++, name, category, qty, price, reorderLevel);
        inventory.addLast(p);
        System.out.println(">> Added: " + p);
    }

    // ---------- 2. REMOVE ----------
    private static void removeProduct() {
        if (inventory.isEmpty()) {
            System.out.println(">> Inventory is empty.");
            return;
        }
        int id = readInt("Enter Product ID to remove: ");
        if (inventory.removeById(id)) {
            System.out.println(">> Product ID " + id + " removed.");
        } else {
            System.out.println(">> Product ID not found.");
        }
    }

    // ---------- 3. SEARCH ----------
    private static void searchProduct() {
        if (inventory.isEmpty()) {
            System.out.println(">> Inventory is empty.");
            return;
        }
        System.out.println("Search by:");
        System.out.println("  1. Product ID");
        System.out.println("  2. Name (partial match)");
        int opt = readInt("Choice: ");

        Product[] arr = inventory.toArray();
        int index = -1;

        if (opt == 1) {
            int id = readInt("Enter ID: ");
            index = Algorithms.linearSearchById(arr, id);
        } else if (opt == 2) {
            System.out.print("Enter name (or part of it): ");
            String name = sc.nextLine().trim();
            index = Algorithms.linearSearchByName(arr, name);
        } else {
            System.out.println("Invalid option.");
            return;
        }

        if (index >= 0) {
            System.out.println(">> Found at position " + (index + 1) + ":");
            System.out.println("   " + arr[index]);
        } else {
            System.out.println(">> Product NOT found.");
        }
    }

    // ---------- 4. SORT ----------
    private static void sortProducts() {
        if (inventory.getSize() < 2) {
            System.out.println(">> Not enough products to sort.");
            return;
        }
        System.out.println("Sort by:");
        System.out.println("  1. Name (A-Z)");
        System.out.println("  2. Price (lowest first)");
        System.out.println("  3. Quantity (lowest first)");
        int opt = readInt("Choice: ");

        Product[] arr = inventory.toArray();

        if (opt == 1) {
            Algorithms.bubbleSortByName(arr);
            System.out.println(">> Sorted by Name.");
        } else if (opt == 2) {
            Algorithms.bubbleSortByPrice(arr);
            System.out.println(">> Sorted by Price (ascending).");
        } else if (opt == 3) {
            Algorithms.bubbleSortByQuantity(arr);
            System.out.println(">> Sorted by Quantity (ascending).");
        } else {
            System.out.println("Invalid option.");
            return;
        }

        inventory.rebuildFromArray(arr);
        inventory.display();
    }

    // ---------- 6. UPDATE STOCK ----------
    private static void updateStock() {
        if (inventory.isEmpty()) {
            System.out.println(">> Inventory is empty.");
            return;
        }
        int id = readInt("Enter Product ID: ");
        Product p = inventory.getById(id);
        if (p == null) {
            System.out.println(">> Product not found.");
            return;
        }
        System.out.println("Current: " + p);
        System.out.println("  1. Stock IN (add)");
        System.out.println("  2. Stock OUT (sell/use)");
        int opt = readInt("Choice: ");
        int amt = readInt("Enter amount: ");
        if (amt <= 0) {
            System.out.println(">> Amount must be positive.");
            return;
        }
        if (opt == 1) {
            p.quantity += amt;
            System.out.println(">> Stock updated. New qty: " + p.quantity);
        } else if (opt == 2) {
            if (amt > p.quantity) {
                System.out.println(">> Not enough stock. Available: " + p.quantity);
            } else {
                p.quantity -= amt;
                System.out.println(">> Stock updated. New qty: " + p.quantity);
                if (p.isLowStock()) {
                    System.out.println(">> WARNING: This product needs reordering!");
                }
            }
        } else {
            System.out.println("Invalid option.");
        }
    }

    // ---------- 8. TOTAL VALUE ----------
    private static void showTotalValue() {
        if (inventory.isEmpty()) {
            System.out.println(">> Inventory is empty.");
            return;
        }
        System.out.printf(">> Total inventory value: R%.2f%n",
                inventory.totalInventoryValue());
    }

    // ---------- 9. REORDER PRODUCT (NEW) ----------
    private static void reorderProduct() {
        if (inventory.isEmpty()) {
            System.out.println(">> Inventory is empty.");
            return;
        }

        System.out.println("\n--- REORDER PRODUCT ---");
        System.out.println("  1. Reorder a specific product by ID");
        System.out.println("  2. Reorder ALL low-stock products");
        int opt = readInt("Choice: ");

        if (opt == 1) {
            reorderSingleProduct();
        } else if (opt == 2) {
            reorderAllLowStock();
        } else {
            System.out.println("Invalid option.");
        }
    }

    // -------- 9a. Reorder one product --------
    private static void reorderSingleProduct() {
        int id = readInt("Enter Product ID to reorder: ");
        Product p = inventory.getById(id);
        if (p == null) {
            System.out.println(">> Product not found.");
            return;
        }

        System.out.println("Current stock: " + p);
        System.out.println("Reorder level: " + p.reorderLevel);
        System.out.println("Suggested reorder qty: " + (p.reorderLevel * 2));

        int amt = readInt("Enter quantity to reorder (0 to cancel): ");
        if (amt <= 0) {
            System.out.println(">> Reorder cancelled.");
            return;
        }

        double cost = amt * p.price;
        System.out.printf(">> Placing order for %d x %s (total cost: R%.2f)%n",
                amt, p.name, cost);

        p.quantity += amt;
        System.out.println(">> Stock updated. New qty: " + p.quantity);

        if (!p.isLowStock()) {
            System.out.println(">> Stock level is now OK. Reorder flag cleared.");
        } else {
            System.out.println(">> Still below reorder level. Consider ordering more.");
        }
    }

    // -------- 9b. Reorder all low-stock products at once --------
    private static void reorderAllLowStock() {
        Product[] arr = inventory.toArray();
        boolean any = false;
        double totalCost = 0;

        System.out.println("\n--- BULK REORDER ---");
        for (Product p : arr) {
            if (p.isLowStock()) {
                int suggested = p.reorderLevel * 2;
                double cost = suggested * p.price;
                System.out.printf("  %s -> order %d units (R%.2f)%n",
                        p.name, suggested, cost);
                p.quantity += suggested;
                totalCost += cost;
                any = true;
            }
        }

        if (!any) {
            System.out.println("  No products need reordering.");
            return;
        }
        System.out.printf(">> Bulk reorder complete. Total cost: R%.2f%n", totalCost);
    }

    // ---------- Utilities ----------
    private static int readInt(String prompt) {
        while (true) {
            try {
                if (!prompt.isEmpty()) System.out.print(prompt);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Invalid number, try again: ");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            try {
                if (!prompt.isEmpty()) System.out.print(prompt);
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Invalid number, try again: ");
            }
        }
    }
}