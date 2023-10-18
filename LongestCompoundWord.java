package ImpledgeTechnologies;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class LongestCompoundWord {
    static boolean x = true;


    public static void processInputFile(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);

        Trie trie = new Trie();
        LinkedList<Pair<String>> queue = new LinkedList<>();
        HashSet<String> compoundWords = new HashSet<>();
        long startTime = System.currentTimeMillis(); // Record start time

        while (scanner.hasNext()) {
            String word = scanner.next();
            List<Integer> sufIndices = trie.getSuffixesStartIndices(word);
            for (int i : sufIndices) {
                if (i >= word.length())
                    break;
                queue.add(new Pair<String>(word, word.substring(i)));
            }
            trie.insert(word);
        }
        long endTime = System.currentTimeMillis();
        long runTime = endTime - startTime;

        String longestCompoundWord = "";
        String secondLongestCompoundWord = "";
        int maxLength = 0;

        while (!queue.isEmpty()) {
            Pair<String> p = queue.removeFirst();
            String word = p.second();
            List<Integer> sufIndices = trie.getSuffixesStartIndices(word);

            if (sufIndices.isEmpty()) {
                continue;
            }

            for (int i : sufIndices) {
                if (i > word.length()) {
                    break;
                }
                if (i == word.length()) {
                    if (p.first().length() > maxLength) {
                        secondLongestCompoundWord = longestCompoundWord;
                        maxLength = p.first().length();
                        longestCompoundWord = p.first();
                    }
                    compoundWords.add(p.first());
                } else {
                    queue.add(new Pair<String>(p.first(), word.substring(i)));
                }
            }
        }

        System.out.println("Longest Compound Word " + ": " + longestCompoundWord);
        System.out.println("Second Longest Compound Word " + ": " + secondLongestCompoundWord);
        if (x) {
            System.out.println("Time taken to process file Input_01.txt " + ": " + runTime + " milli seconds");
            x = false;
        } else {
            System.out.println("Time taken to process file Input_02.txt " + ": " + runTime + " milli seconds");

        }
    }
    public static void main(String[] args) throws FileNotFoundException {
        String inputFile1 = "src/ImpledgeTechnologies/Input_01.txt";
        String inputFile2 = "src/ImpledgeTechnologies/Input_02.txt";
        System.out.println("*******************************************************");
        processInputFile(inputFile1);
        System.out.println("*******************************************************");
        processInputFile(inputFile2);
        System.out.println("*******************************************************");

    }
}

class Pair<T> {
    private T first;
    private T second;

    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public T first() {
        return first;
    }

    public T second() {
        return second;
    }
}

class TrieNode {
    Map<Character, TrieNode> children;
    boolean isTerminating;

    public TrieNode() {
        this.children = new HashMap<>();
        this.isTerminating = false;
    }
}

class Trie {
    TrieNode root;

    public Trie() {
        this.root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            node.children.putIfAbsent(ch, new TrieNode());
            node = node.children.get(ch);
        }
        node.isTerminating = true;
    }

    public List<Integer> getSuffixesStartIndices(String word) {
        // returning indexes of the last words
        List<Integer> indices = new ArrayList<>();
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (node.children.containsKey(ch)) {
                node = node.children.get(ch);
                if (node.isTerminating) {
                    indices.add(i + 1);
                }
            } else {
                break;
            }
        }
        return indices;
    }

}

