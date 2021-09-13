import ltq.Graph

object LtqLand extends App {
  /**
   * LIST OF ASSUMPTIONS
   * 1. City Name is always one char
   * 2. Distance is always one char
   * 3. Run the Tests to see complete results
   * 4. Used recursion to keep the code simple, I am aware of Call Stack Overflow exception can occur.
   * 5. Alternative version is available here: https://github.com/edwinbosire/Train-problem---Algorithm
   * 6. In SBT Shell please type "test"
   * */

    try{
      val links = List("AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7")
      Graph.build(links)
    }
    catch {
      case e: Exception => println(e.getMessage)
    }
}
