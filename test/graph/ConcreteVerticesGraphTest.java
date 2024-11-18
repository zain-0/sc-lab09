package graph;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class ConcreteVerticesGraphTest extends GraphInstanceTest {

    @Override
    public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }

    /*
     * Tests for ConcreteVerticesGraph...
     */

    @Test
    public void testToStringEmptyGraph() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        assertEquals("Vertices: []\nEdges:\n", graph.toString());
    }

    @Test
    public void testAddVertex() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        assertTrue(graph.add("A"));
        assertFalse(graph.add("A"));
        assertEquals(Set.of("A"), graph.vertices());
    }

    @Test
    public void testSetEdgeAdd() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.add("A");
        graph.add("B");
        int prevWeight = graph.set("A", "B", 5);
        assertEquals(0, prevWeight);
        assertEquals(Map.of("B", 5), graph.targets("A"));
    }

    @Test
    public void testSetEdgeUpdateWeight() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.set("A", "B", 5);
        int prevWeight = graph.set("A", "B", 10);
        assertEquals(5, prevWeight);
        assertEquals(Map.of("B", 10), graph.targets("A"));
    }

    @Test
    public void testRemoveVertex() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.set("A", "B", 5);
        assertTrue(graph.remove("A"));
        assertFalse(graph.vertices().contains("A"));
        assertTrue(graph.sources("B").isEmpty());
    }

    /*
     * Tests for Vertex...
     */

    @Test
    public void testVertexConstructor() {
        Vertex v = new Vertex("A");
        assertEquals("A", v.getLabel());
    }

    @Test
    public void testVertexSetTarget() {
        Vertex v = new Vertex("A");
        assertEquals(0, v.setTarget("B", 5));
        assertEquals(5, v.getWeightTo("B"));
        assertEquals(5, v.setTarget("B", 10));
        assertEquals(10, v.getWeightTo("B"));
        assertEquals(10, v.setTarget("B", 0));
        assertEquals(0, v.getWeightTo("B"));
    }

    @Test
    public void testVertexToString() {
        Vertex v = new Vertex("A");
        v.setTarget("B", 5);
        assertEquals("Vertex(A, {B=5})", v.toString());
    }
}