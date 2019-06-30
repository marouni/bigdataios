# bigdataios
Comparing Bigdata IOs

# How to run Spark Drivers
```bash
> sbt
> runMain DRIVER'S_FQCN --args
```

# How to run Beam Drivers
```bash
> sbt
> runMain DRIVER'S_FQCN  --runner=direct --tempLocation=gs://datahubhouse/temp
```

# How to run Scio Drivers
```bash
> sbt
> runMain DRIVER'S_FQCN --runner=direct --tempLocation=gs://datahubhouse/temp
```