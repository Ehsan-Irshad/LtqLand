package ltq
import scala.collection.mutable.{HashMap, ListBuffer}
import scala.util.control.Breaks.{break, breakable}

object Graph{
  var nodeList = new HashMap[String, Node]
  var routesFound:ListBuffer[Route] = new ListBuffer[Route]

  def GetNode(key:String): Option[Node] = {
    nodeList.get(key)
  }

  def build(links: List[String]) {
    //Assumption that first, second & third chars have a particular meaning in the string as below
    for (link <- links) {
      val nodeName1 = link.charAt(0).toString
      val nodeName2 = link.charAt(1).toString
      val weight = link.charAt(2).toString.toInt

      val node1 = nodeList.getOrElse(nodeName1, new Node(nodeName1))
      val node2 = nodeList.getOrElse(nodeName2, new Node(nodeName2))
      node1.addEdge(node2, weight)

      nodeList.put(nodeName1, node1)
      nodeList.put(nodeName2, node2)
    }
  }

  def routeDistance(nodeNames: List[String]) : Int = {
    if(nodeNames.length < 2)
      throw new Exception("Incorrect Input")

    var totalDistance = 0
    var index = 0

    breakable{
      while(index < nodeNames.length - 1){
        val n1 = nodeList.get(nodeNames(index))
        val n2 = nodeList.get(nodeNames(index + 1))

        if(n1.isDefined && n2.isDefined){
          val d = n1.get.containsEdge(n2.get)
          if(d == -1) {
            totalDistance = -1
            break
          }
          totalDistance += d
          index = index + 1
        }
        else{
          throw new Exception ("City Not Found")
        }
      }
    }

    if(totalDistance == -1){
      println(s"No Route Found for : $nodeNames")
    }
    else{
      println(s"$nodeNames route distance is: $totalDistance")
    }

    totalDistance
  }

  /** Parameter to stop the recursive behavior can be adjusted before every run */
  val maxStops = 20
  val maxDistance = 200
  private def findRoutes(start:String, end:String): Unit =
  {
    routesFound.clear()
    if(nodeList.get(start).isDefined && nodeList.get(end).isDefined){
      val nodeStart = nodeList.get(start).get
      val nodeEnd = nodeList.get(end).get
      nodeStart.findNode(nodeEnd,0, 0, maxStops, maxDistance, "")
    }
  }

  def filterEqStop(start:String, end:String, stops:Int): Int ={
    findRoutes(start:String, end:String)
    val filteredRoutes = routesFound.filter(_.stops == stops)
    print("Routes Found: ")
    for(route <- filteredRoutes){
      print(s"${route.name} ")
    }
    println("")
    filteredRoutes.length
  }

  def filterLtStop(start:String, end:String, stops:Int): Int={
    findRoutes(start:String, end:String)
    val filteredRoutes = routesFound.filter(_.stops < stops)
    print("Routes Found: ")
    for(route <- filteredRoutes){
      print(s"${route.name} ")
    }
    println("")
    filteredRoutes.length
  }

  def filterLtDistance(start:String, end:String, distance:Int): Int={
    findRoutes(start:String, end:String)
    val filteredRoutes = routesFound.filter(_.distance < distance)
    print("Routes Found: ")
    for(route <- filteredRoutes){
      print(s"${route.name} ")
    }
    println("")
    filteredRoutes.length
  }

  def min(r1: Route, r2: Route): Route = if (r1.distance < r2.distance) r1 else r2
  def filterShortest(start:String, end:String): Int ={
    findRoutes(start:String, end:String)
    val shortestRoute = routesFound.reduceLeft(min)
    println(s"Shortest Route Found: ${shortestRoute.name} Distance:${shortestRoute.distance}")
    shortestRoute.distance
  }
}
