package poet;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
    
    // Testing strategy:
    //   - Test graph creation from a small corpus file
    //     - Single word corpus
    //     - Multiple word corpus with no duplicate edges
    //     - Multiple word corpus with duplicate edges
    //   - Test poem generation:
    //     - No bridge word available
    //     - Single bridge word
    //     - Multiple bridge word candidates, choose maximum-weight
    //     - Input with punctuation
    //     - Case-insensitive behavior
    //   - Edge cases:
    //     - Empty input
    //     - Corpus with one word
    //     - Large corpus with multiple inputs

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // Make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testGraphCreation() throws IOException {
        File corpus = new File("test/poet/small-corpus.txt"); // Example file
        GraphPoet poet = new GraphPoet(corpus);

        // Verify the graph has the expected structure
        assertTrue("Graph should contain word 'hello'", poet.toString().contains("hello"));
        assertTrue("Graph should contain word 'world'", poet.toString().contains("world"));
        // Add more checks for the graph structure if needed
    }

    @Test
    public void testPoemNoBridge() throws IOException {
        File corpus = new File("test/poet/small-corpus.txt"); // Corpus with unrelated words
        GraphPoet poet = new GraphPoet(corpus);
        String input = "Unrelated words";
        String output = poet.poem(input);

        assertEquals("Output should match input when no bridges are available", input, output);
    }

    @Test
    public void testPoemSingleBridge() throws IOException {
        File corpus = new File("test/poet/simple-corpus.txt"); // Example corpus
        GraphPoet poet = new GraphPoet(corpus);
        String input = "Test system";
        String output = poet.poem(input);

        assertEquals("Test of system", output);
    }

    @Test
    public void testPoemMultipleBridges() throws IOException {
        File corpus = new File("test/poet/complex-corpus.txt"); // Example corpus
        GraphPoet poet = new GraphPoet(corpus);
        String input = "Hello world";
        String output = poet.poem(input);

        assertEquals("Hello beautiful world", output);
    }

    @Test
    public void testCaseInsensitive() throws IOException {
        File corpus = new File("test/poet/mixed-case-corpus.txt"); // Corpus with mixed-case words
        GraphPoet poet = new GraphPoet(corpus);
        String input = "Hello WORLD";
        String output = poet.poem(input);

        assertEquals("Hello beautiful WORLD", output);
    }

    @Test
    public void testEmptyInput() throws IOException {
        File corpus = new File("test/poet/small-corpus.txt");
        GraphPoet poet = new GraphPoet(corpus);
        String input = "";
        String output = poet.poem(input);

        assertEquals("Output for empty input should be empty", "", output);
    }

    @Test
    public void testOneWordCorpus() throws IOException {
        File corpus = new File("test/poet/one-word-corpus.txt"); // Corpus with a single word
        GraphPoet poet = new GraphPoet(corpus);
        String input = "Single";
        String output = poet.poem(input);

        assertEquals("Output should match input for single-word corpus", input, output);
    }
}