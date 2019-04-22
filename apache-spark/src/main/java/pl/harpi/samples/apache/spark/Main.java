package pl.harpi.samples.apache.spark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import scala.Function0;

public class Main {
    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .appName("Main")
                .master("local")
                .getOrCreate();

        spark.logInfo(() -> ( "Log info..."));

        Dataset<Row> df = spark.read().json("apache-spark/src/main/resources/");

        df.show();
        df.printSchema();
        df.select("name").show();
    }

}
