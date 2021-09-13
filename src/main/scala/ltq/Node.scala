package ltq

import scala.collection.mutable

class Node(var name: String) {
  private val _name = name
  private var _visited = false
  private val _edges = new mutable.HashMap[Node, Int]

  def getName: String = _name
  def isVisited: Boolean = _visited
  def markVisited:Unit = {_visited = true}
  def reset:Unit = {_visited = false}

  def addEdge(n: Node, weight: Int):Unit = {
    //println(s"Adding edge from:${_name} to:${n.getName} weight:${weight}")
    _edges.put(n, weight)
  }

  def containsEdge(n:Node): Int ={
    val edge = _edges.get(n)
    if(edge.isDefined) {
      edge.get
    } else {
      -1
    }
  }

  def findNode(n:Node, stops: Int, distance:Int, maxStops:Int, maxDistance:Int, route:String) : Boolean = {
    if(_edges.isEmpty || stops > maxStops || distance > maxDistance) {
      //println("Stopping ")
      return false
    }
    else if(_edges.get(n).isDefined) {
      //println(s"Found Route: ${route.concat(_name).concat(n.getName)} Stops:${stops+1} Distance:${distance + _edges.get(n).get}")
      Graph.routesFound += Route(route.concat(_name).concat(n.getName), stops+1, distance + _edges.get(n).get)
      /** Dont stop but keep searching in loops*/
      //return true
    }

    for(edge <- _edges.keys){
      val d = _edges.get(edge).get
      edge.findNode(n,stops+1, distance+d, maxStops, maxDistance, route.concat(_name))
    }
    false
  }
}