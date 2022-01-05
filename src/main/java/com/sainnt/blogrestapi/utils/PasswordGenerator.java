package com.sainnt.blogrestapi.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Scanner;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Scanner scanner = new Scanner(System.in);
        for (String password = scanner.next(); !(password.isEmpty()||password.equals("-quit")); password = scanner.next() )
        {
            System.out.println(passwordEncoder.encode(password));
        }
    }
}
