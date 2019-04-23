package pl.harpi.samples.apache.spark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class Main {
    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .appName("Main")
                .master("local")
                .getOrCreate();

        spark.logInfo(() -> ( "Log info..."));

        Dataset<Row> dfEmp = spark.read().option("multiLine", true).json("apache-spark/src/main/resources/json/emp.json");
        Dataset<Row> dfDept = spark.read().option("header", true).csv("apache-spark/src/main/resources/json/dept.csv");

        dfEmp.createOrReplaceTempView("emp");
        dfDept.createOrReplaceTempView("dept");

        Dataset<Row> dfJoin1 = dfEmp.join(dfDept, dfEmp.col("deptno").equalTo(dfDept.col("deptno")));
        dfJoin1.printSchema();
        dfJoin1.show();

        Dataset<Row> dfJoin2 = spark.sql("select e.*, d.dname, d.loc from emp e join dept d on e.deptno = d.deptno");

        dfJoin2 = dfJoin2.filter(dfJoin2.col("loc").equalTo("CHICAGO"));

        dfJoin2.printSchema();
        dfJoin2.show();

    }

}
