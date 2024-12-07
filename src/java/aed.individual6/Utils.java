package aed.individual6;


import es.upm.aedlib.positionlist.*;
import es.upm.aedlib.set.*;
import es.upm.aedlib.graph.*;


public class Utils {

  /**
   * Devuelve un conjunto con todos los vertices alcanzables desde AMBOS
   * v1 y v2.
   */
  public static <V> Set<Vertex<V>>
    reachableFromBoth(DirectedGraph<V,Boolean> g, Vertex<V> v1, Vertex<V> v2) {
    Set<Vertex<V>> resSet = new HashTableMapSet<>();
    if (!g.isEmpty()) {
      Set<Vertex<V>> set1 = findVertexes(g, v1);
      Set<Vertex<V>> set2 = findVertexes(g, v2);

      for (Vertex<V> vertex : set1) {
        if (set2.contains(vertex)) resSet.add(vertex);
      }
    }
    return resSet;
  }

  private static <V> Set<Vertex<V>> findVertexes(DirectedGraph<V, Boolean> g, Vertex<V> start) {
    Set<Vertex<V>> setAux = new HashTableMapSet<>();
    PositionList<Vertex<V>> pList = new NodePositionList<>();
    pList.addLast(start);
    while (!pList.isEmpty()) {
      Vertex<V> current = pList.first().element();
      pList.remove(pList.first());

      if (!setAux.contains(current)){
        setAux.add(current);
        for (Edge<Boolean> edge : g.outgoingEdges(current)) if (edge.element() && !setAux.contains(g.endVertex(edge))) pList.addLast(g.endVertex(edge));
      }
    }
    return setAux;
  }

  /**
   * Devuelve un camino (una lista de aristas) que llevan desde from y to,
   * donde la suma de los elementos de las aristas del camino <= limit.
   * Si no existe ningun camino que cumple con esta restriccion se devuelve
   * el valor null. 
   */
  
  public static <V> PositionList<Edge<Integer>> existsPathLess(UndirectedGraph<V,Integer> g, Vertex<V> from, Vertex<V> to, int limit) {
    return DFS(g,from, to, new NodePositionList<>(), 0, new HashTableMapSet<>(), limit);
  }

  private static <V> PositionList<Edge<Integer>> DFS(UndirectedGraph<V,Integer> g, Vertex<V> current, Vertex<V> to,
                                                     PositionList<Edge<Integer>> resList, int pesoAct,
                                                     Set<Vertex<V>> setAux, int limit){
    if (current.equals(to)) {
      return resList;
    }
    setAux.add(current);
    for (Edge<Integer> edge : g.edges(current)){
      Vertex<V> vecino = g.opposite(current,edge);
      if (!setAux.contains(vecino) && edge.element() + pesoAct < limit) {
        resList.addLast(edge);
        PositionList<Edge<Integer>> result = DFS(g,vecino, to, resList, pesoAct + edge.element(), setAux, limit);
        if (result != null){
          return result;
        }
        resList.remove(resList.last());
      }
    }
    setAux.remove(current);
    return null;
  }

}



