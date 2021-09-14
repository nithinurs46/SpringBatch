# SpringBatch
Spring Batch Sample

3 sample spring batch applications are created.

1. SpringBatch - 
This application contains both Tasklet based and chunk based approaches.
Chunk based approach further contains itemReader and itemWriter implementations using JDBC and JPA implementations.

Run the application by providing the Job parameters - 
If running the jar directly run as -> 
java -jar SpringBatch-0.0.1-SNAPSHOT.jar "item=toys" "run.date(date)=2020/01/01"

Change the above parameters when running for the next time.

If running the project from eclipse, job parameters can be provided with below steps - 
Right click -> run as -> run configuration -> click on arguments 
Provide program argument - 
"item=toys" "run.date(date)=2021/30/01"

2. SpringBatch_RestApi - 
URL - http://localhost:8082/api/start

Add spring.batch.job.enabled=false in application.properties . This will disable the job from starting immediately on launch
FlatFileReader is used to read the data from the CSV file in this approach.

3. SpringBatch_Scheduling
Cron expression is used to schedule the jobs. Job will be run every 30 seconds. 
Below steps needs to be followed to schedule the job - 
1.	Add @EnableScheduling annotation
2.	Add spring.batch.job.enabled=false in application.properties . This will disable the job from starting immediately on launch. 
3.	Create a Job launcher and a method with @Scheduled to perform scheduling 



