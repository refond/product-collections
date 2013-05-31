package org.catch22.collections
package util
import math.pow
/**

 * A very simple class wrapping an Iterable and providing basic weighted 
 * statistics like weighted mean and weighted variance.
 * 
 * There is an implicit conversion from a Seq[A] to a Stats[A] imported
 * with org.catch22.collections.  So you do not need to do anything
 * to use this class.  Simply use the mean, weightedStdDev or weightedVariance
 * methods on an appropriate sequence:
 * 
 * I would prefer to use an external library
 */

class WeightedStats[A,B](underlying:Iterable[Product2[A,B]])(implicit val numA:Numeric[A], implicit val numB:Numeric[B]) {
  if(underlying.isEmpty) throw new Exception("Empty Seq")
  
  /**
   * The arithmetic mean of all the elements
   */ 
  import org.catch22.collections._
  
  lazy val mean :Double= 
    underlying.map(x=>(numA.toDouble(x._1), numB.toDouble(x._2)))
  .map(x=>x._1*x._2).sum / numB.toDouble(underlying.map(_._2).sum)
                      
    
  
  /**
   * The variance of all the elements (the one divided by n not n-1)
   */
  lazy val variance :Double = 
    underlying.map (x=>pow(numA.toDouble(x._1)-mean,2)*numB.toDouble(x._2)).sum /
    numB.toDouble(underlying.map(_._2).sum)
  
  /**
   * The standard deviation of all the elements
   */
  lazy val stdDev:Double= math.sqrt(variance)
}
