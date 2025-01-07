package com.example.demo.security;
import com.example.demo.security.AESEncryptionUtil;

public class DecryptExample {
    public static void main(String[] args) {
        // Το κρυπτογραφημένο password
        String encryptedPassword = "+JOPuAigzqeJHosUz75RlmE8JxoFtdWCJTeWm3AReX8="; // Παράδειγμα κρυπτογραφημένων δεδομένων

        // Αποκρυπτογράφηση
        try {
            String decryptedPassword = AESEncryptionUtil.decrypt(encryptedPassword);
            System.out.println("Το αποκρυπτογραφημένο password είναι: " + decryptedPassword);
        } catch (Exception e) {
            System.out.println("Σφάλμα κατά την αποκρυπτογράφηση: " + e.getMessage());
        }
    }
}
