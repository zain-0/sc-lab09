package graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Map;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }

    @Test
    public void testAddVertex() {
        Graph<String> graph = emptyInstance();
        assertTrue("expected true when adding a new vertex", graph.add("A"));
        assertTrue("expected vertex to be present in the graph", graph.vertices().contains("A"));
    }

    @Test
    public void testAddDuplicateVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        assertFalse("expected false when adding a duplicate vertex", graph.add("A"));
        assertEquals("expected only one instance of the vertex", 1, graph.vertices().size());
    }

    @Test
    public void testSetEdge() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        assertEquals("expected weight of 0 when no edge exists", 0, graph.set("A", "B", 5));
        assertEquals("expected weight of 5 for the added edge", 5, graph.set("A", "B", 10));
    }

    @Test
    public void testRemoveVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        assertTrue("expected vertex removal to return true", graph.remove("A"));
        assertFalse("expected vertex to no longer exist in the graph", graph.vertices().contains("A"));
    }

    @Test
    public void testSourcesAndTargets() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);

        Map<String, Integer> sources = graph.sources("B");
        Map<String, Integer> targets = graph.targets("A");

        assertTrue("expected A to be a source of B", sources.containsKey("A"));
        assertEquals("expected edge weight of 5 from A to B", (Integer) 5, sources.get("A"));

        assertTrue("expected B to be a target of A", targets.containsKey("B"));
        assertEquals("expected edge weight of 5 from A to B", (Integer) 5, targets.get("B"));
    }
}