package poet;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class GraphPoetTest {

    // Testing strategy:
    // - Corpus variations: small, single-word, large
    // - Input types: empty, no bridges, one bridge, multiple bridges, special characters
    // - Case sensitivity, punctuation handling, and edge cases

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // Ensure assertions are enabled
    }

    @Test
    public void testSmallCorpus() throws IOException {
        File corpus = new File("test/poet/small-corpus.txt");
        GraphPoet poet = new GraphPoet(corpus);

        assertTrue("Graph should include word 'hello'", poet.toString().contains("hello"));
        assertTrue("Graph should include word 'world'", poet.toString().contains("world"));
    }

    @Test
    public void testEmptyInput() throws IOException {
        File corpus = new File("test/poet/small-corpus.txt");
        GraphPoet poet = new GraphPoet(corpus);
        assertEquals("", poet.poem(""));
    }

    @Test
    public void testSingleBridgeWord() throws IOException {
        File corpus = new File("test/poet/simple-corpus.txt");
        GraphPoet poet = new GraphPoet(corpus);
        String input = "Test system";
        assertEquals("Test of system", poet.poem(input));
    }

    @Test
    public void testMultipleBridgeWords() throws IOException {
        File corpus = new File("test/poet/complex-corpus.txt");
        GraphPoet poet = new GraphPoet(corpus);
        String input = "Hello world";
        assertEquals("Hello beautiful world", poet.poem(input));
    }

    @Test
    public void testCaseSensitivity() throws IOException {
        File corpus = new File("test/poet/mixed-case-corpus.txt");
        GraphPoet poet = new GraphPoet(corpus);
        String input = "hello WORLD";
        assertEquals("hello beautiful WORLD", poet.poem(input));
    }

    @Test
    public void testPunctuationHandling() throws IOException {
        File corpus = new File("test/poet/punctuation-corpus.txt");
        GraphPoet poet = new GraphPoet(corpus);
        String input = "Well-done task";
        assertEquals("Well-done and task", poet.poem(input));
    }

    @Test
    public void testCorpusWithSingleWord() throws IOException {
        File corpus = new File("test/poet/one-word-corpus.txt");
        GraphPoet poet = new GraphPoet(corpus);
        String input = "Hello";
        assertEquals("Hello", poet.poem(input));
    }
}
