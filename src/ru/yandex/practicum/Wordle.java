package ru.yandex.practicum;

import java.util.Scanner;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PrintWriter log = new PrintWriter("log.txt");
        WordleDictionaryLoader wordleDictionaryLoader = new WordleDictionaryLoader("words_ru.txt", log);
        WordleDictionary wordleDictionary = wordleDictionaryLoader.getWordleDictionary();
        WordleGame wordleGame = new WordleGame(wordleDictionary, log);

        while (wordleGame.getSteps() > 0) {
            System.out.println("Введите слово");
            String word = scanner.nextLine();
            if (word.isEmpty()) {
                word = wordleGame.getPrompt();
            } else {
                //Проходим проверки слова для соответствия условиям игры
                while (!wordleGame.mainCheckWord(word)) {
                    System.out.println("Введите слово еще раз");
                    word = scanner.nextLine();
                    if (word.isEmpty()) {
                        word = wordleGame.getPrompt();
                        break;
                    }
                }
            }
            //Слово подходит для игры
            wordleGame.subtractionStep();
            wordleGame.addWord(word);
            System.out.println("Шаги " + wordleGame.getSteps());
            System.out.println(word);
            //Проверка на совпадение по алгоритму Wordle
            String str = wordleGame.checkWordByWordle(word);
            System.out.println(str);
            //wordleGame.getPrompt();
            //System.out.println(wordleGame.getFilteredDictionary());
            if (str.equals(wordleGame.getAnswer())) return;
        }
        System.out.println("Ходы закончились - Вы проиграли");
    }
}
