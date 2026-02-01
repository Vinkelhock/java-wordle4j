package ru.yandex.practicum;

import org.junit.jupiter.api.Test;

public class WordleGameTest {
    @Test
    public void createFilteredDictionaryTest() {
        PrintWriter log = new PrintWriter("log.txt");
        WordleDictionaryLoader wordleDictionaryLoader = new WordleDictionaryLoader("words_ru.txt", log);
        WordleDictionary wordleDictionary = wordleDictionaryLoader.getWordleDictionary();
        WordleGame wordleGame = new WordleGame(wordleDictionary, log);

        while (wordleGame.getSteps() > 0) {
            System.out.println("Введите слово");
            String word = wordleGame.getPrompt();
            wordleGame.subtractionStep();
            wordleGame.addWord(word);
            System.out.println("Шаги " + wordleGame.getSteps());
            System.out.println("Загаданное слово " + wordleGame.getAnswer());
            System.out.println(word);
            //Проверка на совпадение по алгоритму Wordle
            String str = wordleGame.checkWordByWordle(word);
            System.out.println(str);
            wordleGame.toStringPlus();
            wordleGame.toStringMinus();
            wordleGame.toStringSome();
            if (str.equals(wordleGame.getAnswer())) return;
            wordleGame.getPrompt();
            System.out.println(wordleGame.getFilteredDictionary());

        }
        System.out.println("Ходы закончились - Вы проиграли");
    }
}
