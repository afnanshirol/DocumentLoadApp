package com.alphasense.document.load.config;

import com.alphasense.document.load.constants.Constants;
import com.alphasense.document.load.dto.DocumentDTO;
import com.alphasense.document.load.processor.DocumentProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.util.HashMap;
import java.util.Map;

/**
 * This class holds all the configuration for reading and writing the file.
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    DocumentProcessor processor;

    @Bean
    public Step documentLoadStep() {
        return stepBuilderFactory.get(Constants.DOCUMENT_LOAD_STEP)
                .<DocumentDTO, DocumentDTO>chunk(1)
                .reader(reader())
                .processor(processor)
                .writer(writer())
                .build();
    }

    @Bean
    public Job documentProcessorJob() {
        return jobBuilderFactory.get(Constants.DOCUMENT_FILE_LOAD_JOB)
                .incrementer(new RunIdIncrementer())
                .flow(documentLoadStep())
                .end()
                .build();

    }

    @Bean
    public StaxEventItemReader<DocumentDTO> reader() {
        StaxEventItemReader<DocumentDTO> reader = new StaxEventItemReader<>();
        reader.setResource(new ClassPathResource(Constants.INPUT_DOCUMENTS_XML));
        reader.setFragmentRootElementName(Constants.DOCUMENT);

        Map<String, String> aliases = new HashMap<>();
        aliases.put(Constants.DOCUMENT, Constants.DOCUMENT_DTO_NAME);

        XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
        xStreamMarshaller.setAliases(aliases);
        reader.setUnmarshaller(xStreamMarshaller);
        return reader;
    }

    @Bean
    public JsonFileItemWriter<DocumentDTO> writer() {
        return new JsonFileItemWriterBuilder<DocumentDTO>()
                .resource(new FileSystemResource(Constants.OUTPUT_FILE_DIRECTORY + Constants.OUTPUT_DOCUMENT_JSON))
                .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
                .name(Constants.DOCUMENT_JSON_FILE_ITEM_WRITER)
                .encoding(Constants.UTF_8)
                .build();
    }

}
