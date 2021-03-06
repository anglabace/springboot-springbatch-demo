package com.official.demo18_parallelJob.run;

import com.official.demo18_parallelJob.domain.JdbcTradeDao;
import com.official.demo18_parallelJob.domain.StagingItemProcessor;
import com.official.demo18_parallelJob.domain.StagingItemReader;
import com.official.demo18_parallelJob.domain.TradeWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.jdbc.support.incrementer.OracleSequenceMaxValueIncrementer;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing //可自动注入对象：jobBuilderFactory、stepBuilderFactory、jobLauncher
public class BuildStep_loading {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("oracleDataSource")
    DataSource oracleDataSource;

    @Bean("tradeDao")
    public JdbcTradeDao getJdbcTradeDao(){
        OracleSequenceMaxValueIncrementer oracleIncrementer = new OracleSequenceMaxValueIncrementer();
        oracleIncrementer.setDataSource(oracleDataSource);
        oracleIncrementer.setIncrementerName("TRADE_SEQ"); // TRADE_SEQ reference src/main/resources/init-sql/init-demo18.sql

        JdbcTradeDao dao = new JdbcTradeDao();
        dao.setDataSource(oracleDataSource);
        dao.setIncrementer(oracleIncrementer);
        return dao;
    }


    @Bean("loadingStep")
    public TaskletStep getStep1(
            @Qualifier("tradeDao") JdbcTradeDao tradeDao
    ){
        StagingItemReader itemReader = new StagingItemReader();
        itemReader.setDataSource(oracleDataSource);

        StagingItemProcessor itemProcessor = new StagingItemProcessor();
        itemProcessor.setDataSource(oracleDataSource);

        TradeWriter tradeWriter = new TradeWriter();
        tradeWriter.setDao(tradeDao);

        //为每个任务触发一个新线程的实现，异步地执行它。
        //支持通过“concurrencyLimit”bean属性限制并发线程。默认情况下，并发线程的数量是无限的。
        //注意:此实现不重用线程!考虑一个线程池TaskExecutor实现，特别是为执行大量的短期任务。
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();

        SimpleStepBuilder simpleStepBuilder = this.stepBuilderFactory.get("loadingStep")
                .<String, String>chunk(1);  // commit-interval="2"
        TaskletStep taskletStep = simpleStepBuilder
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(tradeWriter)
                .taskExecutor(taskExecutor)
                .build();

        return taskletStep;
    }

}
