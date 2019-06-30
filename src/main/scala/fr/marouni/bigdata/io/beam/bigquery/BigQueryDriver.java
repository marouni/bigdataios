package fr.marouni.bigdata.io.beam.bigquery;

import com.google.api.services.bigquery.model.TableFieldSchema;
import com.google.api.services.bigquery.model.TableReference;
import com.google.api.services.bigquery.model.TableRow;
import com.google.api.services.bigquery.model.TableSchema;
import com.google.common.collect.ImmutableList;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;

public class BigQueryDriver {

    public static void main(String[] args) {

        TableReference outputTableSpec =
                new TableReference()
                        .setProjectId("dev-tests-222219")
                        .setDatasetId("dev_tests")
                        .setTableId("active_stations_ref_5");

        TableSchema tableSchema =
                new TableSchema()
                        .setFields(
                                ImmutableList.of(
                                        new TableFieldSchema()
                                                .setName("name")
                                                .setType("STRING")
                                                .setMode("NULLABLE"),
                                        new TableFieldSchema()
                                                .setName("id")
                                                .setType("INTEGER")
                                                .setMode("NULLABLE")));

        PipelineOptions options = PipelineOptionsFactory.fromArgs(args).create();

        Pipeline pipeline = Pipeline.create(options);

        pipeline
                .apply(BigQueryIO.readTableRows().from("bigquery-public-data:austin_bikeshare.bikeshare_stations"))
                .apply(ParDo.of(new FilterRow("status", "active")))
                .apply(ParDo.of(new ProjectRow()))
                .apply(BigQueryIO.writeTableRows()
                        .to(outputTableSpec)
                        .withSchema(tableSchema));

        pipeline.run().waitUntilFinish();
    }

    static class FilterRow extends DoFn<TableRow, TableRow> {

        String columnName;
        String columnValue;

        FilterRow(String columnName, String columnValue) {
            this.columnName = columnName;
            this.columnValue = columnValue;
        }

        @ProcessElement
        public void processElement(@Element TableRow inputRow, OutputReceiver<TableRow> out) {
            if(inputRow.containsKey(columnName)) {
                if(((String) inputRow.get(columnName)).equalsIgnoreCase(columnValue)) {
                    out.output(inputRow);
                }
            }
        }
    }

    static class ProjectRow extends DoFn<TableRow, TableRow> {
        @ProcessElement
        public void processElement(@Element TableRow inputRow, OutputReceiver<TableRow> out) {
            String name = (String) inputRow.get("name");
            Long id = Long.parseLong((String) inputRow.get("station_id"));
            if(name == null) name = "N/A";
            out.output(new TableRow().set("name", name).set("id", id));
        }
    }
}
