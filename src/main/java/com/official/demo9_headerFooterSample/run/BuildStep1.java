package com.official.demo9_headerFooterSample.run;

import com.official.demo9_headerFooterSample.domain.SummaryFooterCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Configuration
@EnableBatchProcessing //可自动注入对象：jobBuilderFactory、stepBuilderFactory、jobLauncher
@Component
public class BuildStep1 {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private JobLauncher jobLauncher;

    @Bean("footerCallback")
    public SummaryFooterCallback getSummaryFooterCallback(){
        SummaryFooterCallback callback = new SummaryFooterCallback();
        return callback;
    }

    @Bean("step1")
    public Step getPlayerloadStep(
            @Qualifier("reader") FlatFileItemReader reader
            ,@Qualifier("writer") FlatFileItemWriter writer
            ,@Qualifier("footerCallback") SummaryFooterCallback footerCallback
    ){
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        String jobName = "step1-" + uuid;
        StepBuilder stepBuilder = stepBuilderFactory.get(jobName);

        SimpleStepBuilder builder = stepBuilder.chunk(3); // reader一次处理3行

        /**
         * the CompositeItemWriter is not an ItemStream, but both of its delegates are.
         * Therefore, both delegate writers must be explicitly registered as streams in order for the framework to handle them correctly.
         * CompositeItemWriter不是一个ItemStream，但是它的两个委托都是。因此，必须显式地将这两个委托编写器注册为流，以便框架正确地处理它们。
         */
        builder.stream(reader);
        builder.stream(writer);

        builder.reader(reader);
        builder.writer(writer);

        TaskletStep taskletStep = builder.build();
        taskletStep.setStepExecutionListeners(new StepExecutionListener[]{ footerCallback });
        return taskletStep;
    }
}
