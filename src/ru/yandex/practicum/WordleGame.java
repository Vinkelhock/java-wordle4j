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
    private final HashMap<Integer, String> listWithPatternPlus = new HashMap<>();
    private final HashMap<Integer, ArrayList<String>> listWithPatternMinus = new HashMap<>();
    private final ArrayList<String> listWithPatternSomewhere = new ArrayList<>();

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
            for (char element : word.toCharArray()) {
                if (element >= 'a' && element <= 'z') {
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
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < word.length(); i++) {
                if (word.substring(i, i + 1).equals(this.answer.substring(i, i + 1))) {
                    line.append("+");
                    this.listWithPatternPlus.put(i, word.substring(i, i + 1));
                } else {
                    String searchElement = word.substring(i, i + 1);
                    int index = this.answer.indexOf(searchElement);
                    if (index == -1) {
                        line.append("-");
                        ArrayList<String> value = new ArrayList<>();
                        if (this.listWithPatternMinus.containsKey(i)) {
                            value = this.listWithPatternMinus.get(i);
                            value.add(word.substring(i, i + 1));
                        } else {
                            value.add(word.substring(i, i + 1));
                        }
                        this.listWithPatternMinus.put(i, value);
                    } else {
                        line.append("^");
                        this.listWithPatternSomewhere.add(word.substring(i, i + 1));
                    }
                }
            }
            this.pattern = line.toString();
            return line.toString();
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
            if (this.listWithPatternPlus.containsKey(i) && this.listWithPatternMinus.containsKey(i)) {
                this.listWithPatternMinus.remove(i);
            }
        }

        for (String line : dictionary) {

            boolean label = true;
            for (Map.Entry<Integer, String> entry : listWithPatternPlus.entrySet()) {
                if (!line.substring(entry.getKey(), entry.getKey() + 1).equals(entry.getValue())) {
                    label = false;
                    break;
                }
            }
            if (!label) continue;

            for (Map.Entry<Integer, ArrayList<String>> entry : listWithPatternMinus.entrySet()) {
                for (String element : entry.getValue()) {
                    if (line.substring(entry.getKey(), entry.getKey() + 1).equals(element)) {
                        label = false;
                        break;
                    }
                }
                if (!label) break;
            }
            if (!label) continue;

            for (String element : listWithPatternSomewhere) {
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

    public int getSteps() {
        return this.steps;
    }

    public String getAnswer() {
        return this.answer;
    }

    public List<String> getFilteredDictionary() {
        return this.filteredDictionary;
    }

    public void toStringPlus() {
        System.out.println("Плюс" + listWithPatternPlus);
    }

    public void toStringMinus() {
        System.out.println("Минус" + listWithPatternMinus);
    }

    public void toStringSome() {
        System.out.println("Somewhere" + listWithPatternSomewhere);
    }
}
