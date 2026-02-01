package ru.yandex.practicum;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PrintWriter {
    private final String log;

    public PrintWriter(String log) {
        this.log = log;
    }

    public void logWriter() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.log, StandardCharsets.UTF_8))) {
            writer.write("Строка для записи лога, в натуре");
        } catch (IOException exception) {
            System.out.println("Произошла ошибка во время записи файла");
        }
    }
}
