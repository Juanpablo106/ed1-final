package ed.lab.ed1final.trie;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class Trie {

    private static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        int contadorPrefijo = 0;
        int contadorPalabra = 0;
    }

    private TrieNode root;

    public Trie() {
        this.root = new TrieNode();
    }

    public void insert(String word) {
        if (word == null || word.isEmpty()) return;

        word = word.trim().toLowerCase();  // Normalización
        TrieNode current = root;
        current.contadorPrefijo++;

        for (char c : word.toCharArray()) {
            current = current.children.computeIfAbsent(c, k -> new TrieNode());
            current.contadorPrefijo++;
        }
        current.contadorPalabra++;
    }

    public int countWordsStartingWith(String prefix) {
        if (prefix == null || prefix.isEmpty()) return 0;

        prefix = prefix.trim().toLowerCase();
        TrieNode current = root;

        for (char c : prefix.toCharArray()) {
            current = current.children.get(c);
            if (current == null) return 0;
        }
        return current.contadorPrefijo;
    }

    public int countWordsEqualTo(String word) {
        if (word == null || word.isEmpty()) return 0;

        word = word.trim().toLowerCase();
        TrieNode current = root;

        for (char c : word.toCharArray()) {
            current = current.children.get(c);
            if (current == null) return 0;
        }
        return current.contadorPalabra;
    }

    public void erase(String word) {
        if (word == null || word.isEmpty() || countWordsEqualTo(word) == 0) return;

        word = word.trim().toLowerCase();
        TrieNode current = root;
        current.contadorPrefijo--;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            TrieNode child = current.children.get(c);

            if (child.contadorPrefijo == 1) {
                current.children.remove(c);
                return;
            }

            child.contadorPrefijo--;
            current = child;
        }

        current.contadorPalabra--;
    }

    public boolean isEmpty() {
        return root.contadorPrefijo == 0;
    }

    public void clear() {
        root.children.clear();
        root.contadorPrefijo = 0;
        root.contadorPalabra = 0;
    }
}
