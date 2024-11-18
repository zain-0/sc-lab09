package graph;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {

    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override
    public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph();
    }

    /*
     * Testing ConcreteEdgesGraph...
     */

    // Testing strategy for ConcreteEdgesGraph.toString():
    // - Test an empty graph
    // - Test a graph with vertices only
    // - Test a graph with edges only
    // - Test a graph with both vertices and edges

    @Test
    public void testToStringEmptyGraph() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        assertEquals("Vertices: []\nEdges:\n", graph.toString());
    }

    @Test
    public void testToStringGraphWithVerticesOnly() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        graph.add("A");
        graph.add("B");
        assertEquals("Vertices: [A, B]\nEdges:\n", graph.toString());
    }

    @Test
    public void testToStringGraphWithEdges() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);
        assertEquals("Vertices: [A, B]\nEdges:\nA -> B (5)\n", graph.toString());
    }

    @Test
    public void testToStringGraphWithMultipleEdges() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        graph.add("A");
        graph.add("B");
        graph.add("C");
        graph.set("A", "B", 5);
        graph.set("B", "C", 10);
        assertEquals("Vertices: [A, B, C]\nEdges:\nA -> B (5)\nB -> C (10)\n", graph.toString());
    }

    /*
     * Testing Edge...
     */

    // Testing strategy for Edge:
    // - Test valid construction
    // - Test invalid construction (null source, null target, or negative weight)
    // - Test getters (source, target, weight)
    // - Test toString format

    @Test
    public void testEdgeConstructorValid() {
        ConcreteEdgesGraph.Edge edge = new ConcreteEdgesGraph.Edge("A", "B", 5);
        assertEquals("A", edge.getSource());
        assertEquals("B", edge.getTarget());
        assertEquals(5, edge.getWeight());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEdgeConstructorNullSource() {
        new ConcreteEdgesGraph.Edge(null, "B", 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEdgeConstructorNullTarget() {
        new ConcreteEdgesGraph.Edge("A", null, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEdgeConstructorNegativeWeight() {
        new ConcreteEdgesGraph.Edge("A", "B", -1);
    }

    @Test
    public void testEdgeToString() {
        ConcreteEdgesGraph.Edge edge = new ConcreteEdgesGraph.Edge("A", "B", 5);
        assertEquals("A -> B (5)", edge.toString());
    }

    /*
     * Additional tests for ConcreteEdgesGraph...
     */

    @Test
    public void testAddVertex() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        assertTrue(graph.add("A"));
        assertFalse(graph.add("A")); // Adding duplicate vertex
        assertEquals(Set.of("A"), graph.vertices());
    }

    @Test
    public void testSetEdgeAdd() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        graph.add("A");
        graph.add("B");
        int prevWeight = graph.set("A", "B", 5);
        assertEquals(0, prevWeight); // No edge existed before
        assertEquals(Map.of("B", 5), graph.targets("A"));
    }

    @Test
    public void testSetEdgeUpdateWeight() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);
        int prevWeight = graph.set("A", "B", 10);
        assertEquals(5, prevWeight); // Edge existed with weight 5
        assertEquals(Map.of("B", 10), graph.targets("A"));
    }

    @Test
    public void testSetEdgeRemove() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);
        int prevWeight = graph.set("A", "B", 0); // Removing edge
        assertEquals(5, prevWeight);
        assertTrue(graph.targets("A").isEmpty());
    }

    @Test
    public void testRemoveVertex() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);
        assertTrue(graph.remove("A"));
        assertFalse(graph.vertices().contains("A"));
        assertTrue(graph.sources("B").isEmpty());
    }

    @Test
    public void testSourcesAndTargets() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        graph.add("A");
        graph.add("B");
        graph.add("C");
        graph.set("A", "B", 5);
        graph.set("C", "B", 10);

        assertEquals(Map.of("A", 5, "C", 10), graph.sources("B"));
        assertEquals(Map.of("B", 5), graph.targets("A"));
    }
}