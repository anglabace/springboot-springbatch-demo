# springboot-springBatch-demo
+ springboot 2.2.5.RELEASE 
+ springbatch ( spring-boot-starter-batch 2.2.5.RELEASE )
+ Oracle 10g+
+ Maven 3.5.3+
<br/>

**Note:** 此项目包含SpringBatch官网所有的demo
<br/>
**Note:** This project will contains all demo of SpringBatch official example (sub-project spring-batch-samples): <br/>
But I did change Gradle to Maven, yes, this is **maven project**.
 <br/>
 
**Spring Batch Official docs :** [https://docs.spring.io/spring-batch/docs/4.2.1.RELEASE/reference/html/index.html](https://docs.spring.io/spring-batch/docs/4.2.1.RELEASE/reference/html/index.html)

**some demos from :** [https://github.com/spring-projects/spring-batch](https://github.com/spring-projects/spring-batch/tree/master/spring-batch-samples) [SpringBatch official example]

**some demos from :** [https://howtodoinjava.com/spring-batch](https://howtodoinjava.com/spring-batch/java-config-multiple-steps/)

<br/><br/>
### com.official.demo1
只要com.official.demo1_adhocLoopJob.job.InfiniteLoopReader能一直read到值，RunJob的@Bean("myjob")就能一直跑。
<br/>
**English:** The job can run forever if the reader(com.official.demo1_adhocLoopJob.job.InfiniteLoopReader) can read something.
<br/>

### com.official.demo2
定义一个start step和next step。
<br/>
**English:** Define a start step and a next step
<br/>

### com.official.demo3
Job有一个reader和3个writer。<br/>
因为有3个writer，所以你可以自定义每个writer的写实现，writer1写入到DB，writer2写入report1.txt，writer3写入report2.txt。
<br/>
**English:** Job have 1 reader and 3 writer.<br/>
 so you can customer these writers, one writer store to DB, one writer generate report1.txt, one writer generate report2.txt
<br/>

### com.official.demo4
Job有listener、reader、processor、writer。<br/>
执行顺序依次是：listener、reader、processor、writer。<br/>
如果processor判断符合条件，则正常返回item对象，否则返回null。如果返回null，则不进入下一步writer里执行。writer负责将数据存储到Oracle.
<br/>
**English:** Job have reader、processor、writer、listener.<br/>
The order of execution is: listener, reader, processor, writer.<br/>
If the processor determines that the condition is met, the item object is returned normally, otherwise null is returned. If null is returned, it is not executed in the writer. Writer is responsible for storing data to Oracle.
<br/>

### com.official.demo5
reader和writer可以在用自定义类里的指定方法去实现。
<br/>
**English:** reader and writer can be use custom class and method.
<br/>

### com.official.demo6
1个job3个step，每个step有自己的reader和writer，并且可以定义每个step的执行顺序。
<br/>
**English:** The job have 3 steps, each step has its own reader and writer, and the execution order of each step can be defined.
<br/>

### com.official.demo7
未完成。
<br/>
**English:** unfinished.
<br/>

### com.official.demo8
未完成。
<br/>
**English:** unfinished.
<br/>

### com.official.demo9
定义处理第一行header和最后一行footer的处理类。
<br/>
**English:**  header and footer of writer.
<br/>

### com.official.demo10
集成 hibernate 框架.
<br/>
**English:**  Integration hibernate framework.
<br/>

### com.official.demo11
演示再无限循环任务情况下，如何优雅地关机。但是我只看出了无限循环，没看出咋优雅关机。
<br/>
**English:**  The tasklet used in this job will run in an infinite loop.  This is useful for testing graceful shutdown from
              		multiple environments..
<br/>



    $ end
