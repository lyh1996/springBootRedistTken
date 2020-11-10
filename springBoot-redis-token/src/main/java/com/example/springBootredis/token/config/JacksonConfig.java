package com.example.springBootredis.token.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * jackson 序列化时间配置 redis 也是用的这个objectMapper 注意不要修改时间格式！
 *
 * @author mcw
 * @author hfb
 * @date 19/11/11
 */
@Configuration
public class JacksonConfig {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static final ThreadLocal<DateFormat> UTIL_DATE_FORMATTER = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    @Bean
    public ObjectMapper mapper() {
        ObjectMapper mapper = new ObjectMapper();
        config(mapper);
        return mapper;
    }

    public static void config(ObjectMapper mapper) {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JavaTimeModule simpleModule = new JavaTimeModule();

        simpleModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DATE_TIME_FORMATTER));
        simpleModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DATE_TIME_FORMATTER));

        simpleModule.addSerializer(LocalDate.class, new LocalDateSerializer(DATE_FORMATTER));
        simpleModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DATE_FORMATTER));

        simpleModule.addSerializer(LocalTime.class, new LocalTimeSerializer(TIME_FORMATTER));
        simpleModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(TIME_FORMATTER));

        mapper.setDateFormat(UTIL_DATE_FORMATTER.get());
        mapper.registerModule(simpleModule).registerModule(new ParameterNamesModule());
    }
}
