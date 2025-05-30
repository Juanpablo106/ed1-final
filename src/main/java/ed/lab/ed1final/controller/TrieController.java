package ed.lab.ed1final.controller;

import ed.lab.ed1final.trie.Trie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trie")
public class TrieController {

    private final Trie trie = new Trie();

    private boolean isValidWord(String word) {
        return word != null && word.matches("[a-z]+");
    }

    @GetMapping("/{word}/count")
    public ResponseEntity<?> countWordsEqualTo(@PathVariable String word) {
        int count = trie.countWordsEqualTo(word);
        return ResponseEntity.ok(new WordCountResponse(word, count));
    }
    @GetMapping("/{prefix}/prefix")
    public ResponseEntity<?> countWordsStartingWith(@PathVariable String prefix) {
        int count = trie.countWordsStartingWith(prefix);
        return ResponseEntity.ok(new PrefixCountResponse(prefix, count));
    }
    @PostMapping("/{word}")
    public ResponseEntity<String> insertWord(@PathVariable String word) {
        if (!isValidWord(word)) {
            return ResponseEntity.badRequest().body("Palabra inválida");
        }
        trie.insert(word);
        return new ResponseEntity<>("Palabra agregada", HttpStatus.CREATED);
    }

    @DeleteMapping("/{word}")
    public ResponseEntity<Void> deleteWord(@PathVariable String word) {
        trie.erase(word);
        return ResponseEntity.noContent().build();
    }
    record WordCountResponse(String word, int wordsEqualTo) {}
    record PrefixCountResponse(String prefix, int wordsStartingWith) {}
}