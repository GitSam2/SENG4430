package org.example;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    static void main(String[] args) {
        IO.println(String.format("Hello and welcome!"));

        Path path = Paths.get(args[0]);
    }
}
