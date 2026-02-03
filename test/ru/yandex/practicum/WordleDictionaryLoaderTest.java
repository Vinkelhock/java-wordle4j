package ru.yandex.practicum;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WordleDictionaryLoaderTest {

    @Test
    void getSizeDictionaryTest() {
        PrintWriter log = new PrintWriter("log.txt");
        WordleDictionaryLoader wordleDictionaryLoader = new WordleDictionaryLoader("words_ru.txt", log);
        WordleDictionary wordleDictionary = wordleDictionaryLoader.getWordleDictionary();

        List<String> dictionary = wordleDictionaryLoader.getDictionary();
        System.out.println(dictionary.size());
        List<String> editedDictionary = wordleDictionaryLoader.getEditedDictionary();
        System.out.println(editedDictionary.size());
        System.out.println(editedDictionary);

        assertEquals(67774, dictionary.size());
        assertEquals(4165, editedDictionary.size());
    }

    @Test
    void numbersOfLettersTest() {
        PrintWriter log = new PrintWriter("log.txt");
        WordleDictionaryLoader wordleDictionaryLoader = new WordleDictionaryLoader("words_ru.txt", log);
        WordleDictionary wordleDictionary = wordleDictionaryLoader.getWordleDictionary();
        List<String> editedDictionary = wordleDictionaryLoader.getEditedDictionary();
        System.out.println(editedDictionary.size());
        for (String word : editedDictionary) {
            assertEquals(word.length(), 5);
        }
    }

    @Test
    void lowerCaseTest() {
        PrintWriter log = new PrintWriter("log.txt");
        WordleDictionaryLoader wordleDictionaryLoader = new WordleDictionaryLoader("words_ru.txt", log);
        WordleDictionary wordleDictionary = wordleDictionaryLoader.getWordleDictionary();
        List<String> editedDictionary = wordleDictionaryLoader.getEditedDictionary();
        System.out.println(editedDictionary.size());
        for (String word : editedDictionary) {
            boolean isLower = word.equals(word.toLowerCase());
            assertTrue(isLower);
        }
    }

    @Test
    void findLetterTest() {
        PrintWriter log = new PrintWriter("log.txt");
        WordleDictionaryLoader wordleDictionaryLoader = new WordleDictionaryLoader("words_ru.txt", log);
        WordleDictionary wordleDictionary = wordleDictionaryLoader.getWordleDictionary();
        List<String> editedDictionary = wordleDictionaryLoader.getEditedDictionary();
        System.out.println(editedDictionary.size());
        for (String word : editedDictionary) {
            char searchChar = 'ё';
            boolean isNotFound = word.indexOf(searchChar) == -1;
            assertTrue(isNotFound);
        }
    }
}
