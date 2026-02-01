package ru.yandex.practicum;

import java.util.*;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {

    private final String answer;
    private int steps = 6;
    private String pattern = "";
    private final WordleDictionary wordleDictionary;
    private final List<String> usedWords = new ArrayList<>();
    private final Random random = new Random();
    private List<String> filteredDictionary = new ArrayList<>();
    private final HashMap<Integer, String> plus = new HashMap<>();
    private final HashMap<Integer, ArrayList<String>> minus = new HashMap<>();
    private final ArrayList<String> somewhere = new ArrayList<>();

    public WordleGame(WordleDictionary dictionary, PrintWriter log) {
        this.wordleDictionary = dictionary;
        this.answer = guessWord();
    }

    private String guessWord() {
        List<String> words = wordleDictionary.getWords();
        int index = this.random.nextInt(words.size());
        return words.get(index);
    }

    public boolean mainCheckWord(String word) {
        try {
            if (word.length() != 5) {
                throw new InputException("Слово состоит из большего или меньшего количества букв");
            }
            boolean isLower = word.equals(word.toLowerCase());
            if (!isLower) {
                throw new InputException("Слово написано не в нижнем регистре");
            }
            if (word.isBlank()) {
                throw new InputException("Слово состоит из пробельных символов");
            }
            for (char c : word.toCharArray()) {
                if (c >= 'a' && c <= 'z') {
                    throw new InputException("Слово состоит из английских букв");
                }
            }
        } catch (InputException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
        return true;
    }

    public String checkWordByWordle(String word) {
        if (word.equals(this.answer)) {
            System.out.println("Слово угадано. Это " + this.answer);
            return this.answer;
        } else {
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < word.length(); i++) {
                if (word.substring(i, i + 1).equals(this.answer.substring(i, i + 1))) {
                    str.append("+");
                    this.plus.put(i, word.substring(i, i + 1));
                } else {
                    String searchElement = word.substring(i, i + 1);
                    int index = this.answer.indexOf(searchElement);
                    if (index == -1) {
                        str.append("-");
                        ArrayList<String> value = new ArrayList<>();
                        if (this.minus.containsKey(i)) {
                            value = this.minus.get(i);
                            value.add(word.substring(i, i + 1));
                        } else {
                            value.add(word.substring(i, i + 1));
                        }
                        this.minus.put(i, value);
                    } else {
                        str.append("^");
                        this.somewhere.add(word.substring(i, i + 1));
                    }
                }
            }
            this.pattern = str.toString();
            return str.toString();
        }
    }

    public String getPrompt() {
        List<String> dictionary = this.wordleDictionary.getWords();
        List<String> tempDictionary = new ArrayList<>();
        if (this.pattern.isEmpty()) {
            int index = random.nextInt(dictionary.size());
            return dictionary.get(index);
        }
        if (!this.filteredDictionary.isEmpty()) {
            dictionary = filteredDictionary;
        }
        for (int i = 0; i < this.pattern.length(); i++) {
            if (this.plus.containsKey(i)) {
                if (this.minus.containsKey(i)) {
                    this.minus.remove(i);
                }
            }
        }

        for (String line : dictionary) {

            boolean label = true;
            for (Map.Entry<Integer, String> entry : plus.entrySet()) {
                if (!line.substring(entry.getKey(), entry.getKey() + 1).equals(entry.getValue())) {
                    label = false;
                    break;
                }
            }
            if (!label) continue;

            for (Map.Entry<Integer, ArrayList<String>> entry : minus.entrySet()) {
                for (String element : entry.getValue()) {
                    if (line.substring(entry.getKey(), entry.getKey() + 1).equals(element)) {
                        label = false;
                        break;
                    }
                }
                if (!label) break;
            }
            if (!label) continue;

            for (String element : somewhere) {
                int index = line.indexOf(element);
                if (index == -1) {
                    label = false;
                    break;
                }
            }
            if (!label) continue;
            if (usedWords.contains(line)) continue;

            tempDictionary.add(line);
        }
        this.filteredDictionary = tempDictionary;
        int index = random.nextInt(tempDictionary.size());
        return tempDictionary.get(index);
    }

    public void subtractionStep() {
        this.steps--;
    }

    public void addWord(String word) {
        this.usedWords.add(word);
    }

    public String getPattern() {
        return this.pattern;
    }

    public List<String> getUsedWords() {
        return this.usedWords;
    }

    public int getSteps() {
        return this.steps;
    }

    public String getAnswer() {
        return this.answer;
    }

    public WordleDictionary getWordleDictionary() {
        return wordleDictionary;
    }

    public List<String> getFilteredDictionary() {
        return this.filteredDictionary;
    }

    public void toStringPlus() {
        System.out.println("Плюс" + plus);
    }

    public void toStringMinus() {
        System.out.println("Минус" + minus);
    }

    public void toStringSome() {
        System.out.println("Somewhere" + somewhere);
    }
}
