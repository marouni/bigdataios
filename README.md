# bigdataios
Comparing Bigdata IOs, the complete code examples of the my Scio vs. Beam talk that I gave for Nantes Scala User Group : https://blog.marouni.fr/nantes-scala-meetup/

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
