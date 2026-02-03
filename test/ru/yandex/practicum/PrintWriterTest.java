package ru.yandex.practicum;

import org.junit.jupiter.api.Test;

public class PrintWriterTest {
    @Test
    public void writingToLogTest() {
        PrintWriter log = new PrintWriter("log.txt");
        log.logWriter();
    }
}
