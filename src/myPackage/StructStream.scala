package myPackage
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql._

object StructStream 
{
  def main(args:Array[String]):Unit=
  {
    val spark = SparkSession.builder.master("local")
    .appName("StructStream")
    .getOrCreate()
    
    
    import spark.implicits._
    spark.sparkContext.setLogLevel("ERROR")
    
    
       //ds.show()
    
  val mystruct= StructType(Seq(
    StructField("User",StringType,true),
    StructField("Slot",IntegerType,true),
    StructField("Day1",IntegerType,true),
    StructField("Day2",IntegerType,true),
    StructField("Day3",IntegerType,true),
    StructField("Day4",IntegerType,true), 
    StructField("Day5",IntegerType,true),
    StructField("Day6",IntegerType,true),
    StructField("Day7",IntegerType,true),
    StructField("Day8",IntegerType,true),
    StructField("Day9",IntegerType,true),
    StructField("Day10",IntegerType,true),
    StructField("Day11",IntegerType,true),
    StructField("Day12",IntegerType,true),
    StructField("Day13",IntegerType,true),
    StructField("Day14",IntegerType,true),
    StructField("Day15",IntegerType,true)))
    /*.add("Day1",DataTypes.IntegerType
    .add("Day2",DataTypes.IntegerType)
    .add("Day3",DataTypes.IntegerType)
    .add("Day4",DataTypes.IntegerType)
    .add("Day5",DataTypes.IntegerType)
    .add("Day6",DataTypes.IntegerType)
    .add("Day7",DataTypes.IntegerType)
    .add("Day8",DataTypes.IntegerType)
    .add("Day9",DataTypes.IntegerType)
    .add("Day10",DataTypes.IntegerType)
    .add("Day11",DataTypes.IntegerType)
    .add("Day12",DataTypes.IntegerType)
    .add("Day13",DataTypes.IntegerType)
    .add("Day14",DataTypes.IntegerType)
    .add("Day15",DataTypes.IntegerType)*/
    //df.select("User").show()
    val ds = spark.readStream
    .format("kafka")
    .option("kafka.bootstrap.servers","localhost:9092")
    .option("subscribe","stream-topic")
    .option("startingOffsets","earliest")
    .load()
   val parse=ds.selectExpr("CAST(value AS STRING)as json")
   val parseDf=parse.select(from_json($"json",mystruct).as ("data"))
                   .select("data.*")//.filter($"User"==="A")
   //val finalData=parseDf.select("data.User","data.Slot","data.Day1","data.Day2","data.Day3","data.Day4","data.Day5","data.Day6","data.Day7",
     //   "data.Day8","data.Day9","data.Day10","data.Day11","data.Day12","data.Day13","data.Day14","data.Day15").groupBy("User")
        
        
   //select("data.User","data.Slot","data.Day1","data.Day2","data.Day3","data.Day4","data.Day5","data.Day6","data.Day7",
     //  "data.Day8","data.Day9","data.Day10","data.Day11","data.Day12","data.Day13","data.Day14","data.Day15")
      //val q= finalData.selectExpr("data.User","data.Slot").groupBy("data.User")
  val out = parseDf
  .writeStream
  .outputMode("append")
  .format("console")
 // .option("numRows", 30)
  .option("truncate", false)
  .start()
  
  out.awaitTermination()
  spark.stop()
  
  }
}