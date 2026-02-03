package ru.yandex.practicum;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {
    private final String file;
    private final PrintWriter log;
    List<String> editedDictionary = new ArrayList<>();
    List<String> dictionary = new ArrayList<>();

    public WordleDictionaryLoader(String file, PrintWriter log) {
        this.file = file;
        this.log = log;
    }

    public WordleDictionary getWordleDictionary() {

        try (BufferedReader br = new BufferedReader(new FileReader(this.file, StandardCharsets.UTF_8))) {

            while (br.ready()) {
                String line = br.readLine();
                dictionary.add(line);
            }

            for (String word : dictionary) {
                boolean check = checkLetterSize(word);
                if (!check) continue;
                word = makeLowerCase(word);
                word = checkChar(word);
                editedDictionary.add(word);
            }

        } catch (IOException exception) {
            System.out.println("Произошла ошибка во время чтения файла");
        }
        return new WordleDictionary(editedDictionary);
    }

    private boolean checkLetterSize(String word) {
        return word.length() == 5;
    }

    private String makeLowerCase(String word) {
        return word.toLowerCase();
    }

    private String checkChar(String word) {
        char searchChar = 'ё';
        int index = word.indexOf(searchChar);
        if (index != -1) {
            return word.replace('ё', 'е');
        } else return word;
    }

    public List<String> getDictionary() {
        return this.dictionary;
    }

    public List<String> getEditedDictionary() {
        return this.editedDictionary;
    }
}
