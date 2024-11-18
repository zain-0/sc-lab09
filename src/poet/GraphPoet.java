package poet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graph.Graph;

/**
 * A graph-based poetry generator.
 */
public class GraphPoet {

    private final Graph<String> graph = Graph.empty();

    // Abstraction function:
    //   Represents a word affinity graph where vertices are words from the corpus,
    //   and edge weights represent the frequency of adjacency between words in the corpus.
    // Representation invariant:
    //   Graph must not contain null vertices or edges.
    //   Edge weights must be positive integers.
    // Safety from rep exposure:
    //   The graph field is private and final. Defensive copying is used where needed.

    /**
     * Create a new poet with the graph from the corpus (as described above).
     *
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
        List<String> lines = Files.readAllLines(corpus.toPath());

        for (String line : lines) {
            String[] words = line.toLowerCase().split("\\s+");
            for (int i = 0; i < words.length - 1; i++) {
                String w1 = cleanWord(words[i]);
                String w2 = cleanWord(words[i + 1]);

                if (!w1.isEmpty() && !w2.isEmpty()) {
                    graph.add(w1);
                    graph.add(w2);
                    int currentWeight = graph.set(w1, w2, 0); // Get existing weight or 0
                    graph.set(w1, w2, currentWeight + 1);     // Increment weight
                }
            }
        }
        checkRep();
    }

    // Helper method to normalize words (remove punctuation and convert to lowercase)
    private String cleanWord(String word) {
        return word.replaceAll("[^a-zA-Z]", "").toLowerCase();
    }

    // Check the representation invariant
    private void checkRep() {
        for (String vertex : graph.vertices()) {
            assert vertex != null : "Graph contains null vertex";
            for (String target : graph.targets(vertex).keySet()) {
                assert graph.targets(vertex).get(target) > 0 : "Edge weight must be positive";
            }
        }
    }

    /**
     * Generate a poem.
     *
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
        StringBuilder poem = new StringBuilder();
        String[] words = input.split("\\s+");

        // Handle empty or single-word input
        if (words.length == 0) {
            return input;
        }
        if (words.length == 1) {
            return input.trim();
        }

        for (int i = 0; i < words.length - 1; i++) {
            String w1 = cleanWord(words[i]);
            String w2 = cleanWord(words[i + 1]);

            poem.append(words[i]).append(" "); // Append original word with original casing

            // Find the bridge word
            String bridgeWord = null;
            int maxWeight = 0;

            Map<String, Integer> targetsW1 = graph.targets(w1);
            for (String candidate : targetsW1.keySet()) {
                Map<String, Integer> targetsCandidate = graph.targets(candidate);
                if (targetsCandidate.containsKey(w2)) {
                    int weight = targetsW1.get(candidate) + targetsCandidate.get(w2);
                    if (weight > maxWeight) {
                        maxWeight = weight;
                        bridgeWord = candidate;
                    }
                }
            }

            if (bridgeWord != null) {
                poem.append(bridgeWord).append(" "); // Append bridge word
            }
        }

        // Append the last word
        poem.append(words[words.length - 1]);

        return poem.toString().trim(); // Trim to remove trailing spaces
    }

    @Override
    public String toString() {
        return "GraphPoet [graph=" + graph + "]";
    }
}