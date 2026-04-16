package com.fiap.pj.infra.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {


    @Value("${broker.queue.analise.concluida}")
    private String queueAnaliseConcluida;

    @Bean
    public Queue queueAnaliseConcluida() {
        return new Queue(queueAnaliseConcluida, true);
    }


    @Value("${broker.queue.analise.solicitada}")
    private String queueSolicitarAnaliseDiagrama;

    @Bean
    public Queue queueSolicitarAnaliseDiagrama() {
        return new Queue(queueSolicitarAnaliseDiagrama, true);
    }


    @Value("${broker.queue.analise.falhou}")
    private String queueAnaliseComFalha;

    @Bean
    public Queue queueAnaliseComFalha() {
        return new Queue(queueAnaliseComFalha, true);
    }


    @Bean
    MessageConverter messageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }


}
