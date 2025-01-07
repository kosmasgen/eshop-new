package com.example.demo.serviceTest;

import java.util.Base64;

public class DecryptPasswordCheck {
    public static void main(String[] args) {
        String encryptedPassword = "+JOPuAigzqeJHosUz75RlmE8JxoFtdWCJTeWm3AReX8=";

        try {
            // Αποκωδικοποίηση του Base64 string
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedPassword);

            // Εκτύπωση μήκους αποκωδικοποιημένων δεδομένων
            System.out.println("Μήκος των αποκωδικοποιημένων bytes: " + decodedBytes.length);

            // Έλεγχος αν είναι πολλαπλάσιο του 16
            if (decodedBytes.length % 16 == 0) {
                System.out.println("Το μήκος των αποκωδικοποιημένων δεδομένων είναι σωστό (πολλαπλάσιο του 16).");
            } else {
                System.out.println("Το μήκος των αποκωδικοποιημένων δεδομένων ΔΕΝ είναι πολλαπλάσιο του 16.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Σφάλμα κατά την αποκωδικοποίηση: " + e.getMessage());
        }
    }
}
