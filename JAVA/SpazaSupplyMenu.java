/*
 * Author: Fantastic 4 (Spaza Supply group)
 * Date: 2026-10-20
 * Purpose: Console menu demonstrating the Phase 1 Spaza Supply calculations in Java.
 */
import java.util.Scanner;

public class SpazaSupplyMenu {
    private static final double DEFAULT_FLAT_BASE_FEE = 25.00;
    private static final double DEFAULT_RATE_PER_KM = 4.50;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt(input, "Choose an option: ");

            switch (choice) {
                case 1:
                    calculateSellingPrice(input);
                    break;
                case 2:
                    calculateDeliveryFee(input);
                    break;
                case 3:
                    calculateGrossRevenue(input);
                    break;
                case 4:
                    calculateNetProfit(input);
                    break;
                case 5:
                    calculateBreakEvenQuantity(input);
                    break;
                case 0:
                    running = false;
                    System.out.println("Spaza Supply menu closed.");
                    break;
                default:
                    System.out.println("Invalid choice. Select an option from 0 to 5.");
            }
            System.out.println();
        }
        input.close();
    }

    private static void printMenu() {
        System.out.println("=== Spaza Supply Phase 1 Calculator ===");
        System.out.println("1. Calculate marked-up selling price");
        System.out.println("2. Calculate delivery fee");
        System.out.println("3. Calculate total platform gross revenue");
        System.out.println("4. Calculate net monthly platform profit");
        System.out.println("5. Calculate break-even quantity");
        System.out.println("0. Exit");
    }

    private static void calculateSellingPrice(Scanner input) {
        double wholesaleCost = readDouble(input, "Wholesale cost per case (R): ");
        double markupRate = readDouble(input, "Markup rate as a decimal (for example, 0.10): ");
        if (markupRate < 0) {
            System.out.println("Markup rate cannot be negative.");
            return;
        }
        // Phase 1 Equation 1: P = C_w * (1 + m).
        double sellingPrice = wholesaleCost * (1 + markupRate);
        System.out.printf("Selling price per case: R %.2f%n", sellingPrice);
    }

    private static void calculateDeliveryFee(Scanner input) {
        double distanceKm = readDouble(input, "One-way distance (km): ");
        if (distanceKm < 0) {
            System.out.println("Distance cannot be negative.");
            return;
        }
        // Phase 1 Equation 2: C_d = F_b + (d * R_k).
        double deliveryFee = DEFAULT_FLAT_BASE_FEE + (distanceKm * DEFAULT_RATE_PER_KM);
        System.out.printf("Delivery fee: R %.2f%n", deliveryFee);
    }

    private static void calculateGrossRevenue(Scanner input) {
        double quantitySold = readDouble(input, "Quantity sold (cases): ");
        double wholesaleCost = readDouble(input, "Wholesale cost per case (R): ");
        double markupRate = readDouble(input, "Markup rate as a decimal (for example, 0.10): ");
        double totalDeliveryFees = readDouble(input, "Total delivery fees collected (R): ");
        if (quantitySold < 0 || wholesaleCost < 0 || markupRate < 0 || totalDeliveryFees < 0) {
            System.out.println("Values cannot be negative.");
            return;
        }
        // Derive P using Equation 1 before applying Phase 1 Equation 3.
        double sellingPrice = wholesaleCost * (1 + markupRate);
        // Phase 1 Equation 3: R = (q * P) + sum of delivery fees.
        double totalRevenue = (quantitySold * sellingPrice) + totalDeliveryFees;
        System.out.printf("Total platform gross revenue: R %.2f%n", totalRevenue);
    }

    private static void calculateNetProfit(Scanner input) {
        double grossRevenue = readDouble(input, "Gross revenue (R): ");
        double quantitySold = readDouble(input, "Quantity sold (cases): ");
        double wholesaleCost = readDouble(input, "Wholesale cost per case (R): ");
        double transportPayouts = readDouble(input, "Driver and transport payouts (R): ");
        double fixedCosts = readDouble(input, "Fixed monthly costs (R): ");
        if (grossRevenue < 0 || quantitySold < 0 || wholesaleCost < 0 || transportPayouts < 0 || fixedCosts < 0) {
            System.out.println("Values cannot be negative.");
            return;
        }
        // Phase 1 Equation 4: profit = gross revenue - procurement - payouts - fixed costs.
        double netProfit = grossRevenue - (quantitySold * wholesaleCost) - transportPayouts - fixedCosts;
        System.out.printf("Net monthly platform profit: R %.2f%n", netProfit);
    }

    private static void calculateBreakEvenQuantity(Scanner input) {
        double fixedCosts = readDouble(input, "Fixed monthly costs (R): ");
        double sellingPrice = readDouble(input, "Selling price per case (R): ");
        double wholesaleCost = readDouble(input, "Wholesale cost per case (R): ");
        double contributionPerCase = sellingPrice - wholesaleCost;
        if (fixedCosts < 0 || contributionPerCase <= 0) {
            System.out.println("Fixed costs must be non-negative and selling price must exceed wholesale cost.");
            return;
        }
        // Round up because the target is a whole number of cases.
        // Phase 1 Equation 5: q* = F_c / (P - C_w).
        long breakEvenQuantity = (long) Math.ceil(fixedCosts / contributionPerCase);
        System.out.println("Break-even quantity: " + breakEvenQuantity + " cases");
    }

    private static int readInt(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (input.hasNextInt()) {
                return input.nextInt();
            }
            // Consume invalid input so the loop can prompt again instead of stalling.
            input.next();
            System.out.println("Enter a whole number.");
        }
    }

    private static double readDouble(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (input.hasNextDouble()) {
                return input.nextDouble();
            }
            // Consume invalid input so the loop can prompt again instead of stalling.
            input.next();
            System.out.println("Enter a number.");
        }
    }
}

