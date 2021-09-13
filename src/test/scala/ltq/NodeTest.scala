package ltq

import org.scalatest.FunSuite

class NodeTest extends FunSuite {
  val links = List("AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7")
  Graph.build(links)

  test("1. The distance of the route A-B-C."){
    assert(Graph.routeDistance(List("A", "B", "C")) === 9)
  }
  test("2. The distance of the route A-D."){
    assert(Graph.routeDistance(List("A", "D")) === 5)
  }
  test("3. The distance of the route A-D-C."){
    assert(Graph.routeDistance(List("A", "D", "C")) === 13)
  }
  test("4. The distance of the route A-E-B-C-D."){
    assert(Graph.routeDistance(List("A", "E", "B", "C", "D")) === 22)
  }
  test("5. The distance of the route A-E-D."){
    assert(Graph.routeDistance(List("A", "E", "D")) === -1)
  }

  test("6. The number of trips starting at C and ending at C with a maximum of 3 stops."){
    /** In the sample data below, there are two such trips: C-D-C (2 stops). and C-E-B-C (3 stops). */
    assert(Graph.filterLtStop("C", "C", 4) === 2)
  }

  test("7. The number of trips starting at A and ending at C with exactly 4 stops."){
    /** In the sample data below, there are three such trips: A to C (via B,C,D); A to C (via D,C,D); and A to C (via D,E,B).*/
    assert(Graph.filterEqStop("A", "C", 4) === 3)
  }
  test("8. The length of the shortest route (in terms of distance to travel) from A to C."){
    assert(Graph.filterShortest("A", "C") === 9)
  }
  test("9. The length of the shortest route (in terms of distance to travel) from B to B."){
    assert(Graph.filterShortest("B", "B") === 9)
  }
  test("10. The number of different routes from C to C with a distance of less than 30."){
    /**
     * In the sample data, the trips are: CDC, CEBC, CEBCDC, CDCEBC, CDEBC, CEBCEBC, CEBCEBCEBC.
     * This can be improved by adding a variable to see if the Node was visited before
     * */
    assert(Graph.filterLtDistance("C", "C", 30) === 7)
  }
}
