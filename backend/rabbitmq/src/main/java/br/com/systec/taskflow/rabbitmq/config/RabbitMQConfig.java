package br.com.systec.taskflow.rabbitmq.config;

import br.com.systec.taskflow.rabbitmq.utils.RabbitMQConstants;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private final CachingConnectionFactory connectionFactory;

    public RabbitMQConfig(CachingConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Bean
    Queue createWorkflowInstallQueue() {
        return new Queue(RabbitMQConstants.TASKFLOW_TEMPLATE_WORKFLOW_INSTALL, true, false, true);
    }

    @Bean
    Queue createKanbanProjectTemplateQueue() {
        return new Queue(RabbitMQConstants.TASKFLOW_KANBAN_PROJECT_TEMPLATE, true, false, true);
    }

    @Bean
    DirectExchange directExchange(){
        return new DirectExchange(RabbitMQConstants.TASKFLOW_EXCHANGE);
    }

    @Bean
    Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(Jackson2JsonMessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter);
        return template;
    }

}
