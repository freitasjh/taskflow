package br.com.systec.taskflow.workflow.jms;

import br.com.systec.taskflow.rabbitmq.utils.RabbitMQConstants;
import br.com.systec.taskflow.workflow.api.service.WorkflowService;
import br.com.systec.taskflow.workflow.api.vo.StatusVO;
import br.com.systec.taskflow.workflow.api.vo.WorkflowVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class WorkflowTemplateInstallJMS {
    private static final Logger log = LoggerFactory.getLogger(WorkflowTemplateInstallJMS.class);

    private final WorkflowService workflowService;
    private final RabbitTemplate rabbitTemplate;

    public WorkflowTemplateInstallJMS(WorkflowService workflowService, RabbitTemplate rabbitTemplate) {
        this.workflowService = workflowService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = RabbitMQConstants.TASKFLOW_TEMPLATE_WORKFLOW_INSTALL)
    @Transactional(propagation = Propagation.REQUIRED)
    public void execute(@Payload Message message) {
        try {
            Long projectId = (Long) message.getPayload();

            WorkflowVO workflow = new WorkflowVO();
            workflow.setProjectId(projectId);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            try(InputStream inputStream = classLoader.getResourceAsStream("json/workflow-template.json")) {
                StatusTemplate listCategory = objectMapper.readValue(inputStream, StatusTemplate.class);

                for (StatusVO status : listCategory.getListStatus()) {
                    if(workflow.getListOfStatus() == null) {
                        workflow.setListOfStatus(new ArrayList<>());
                    }

                    workflow.getListOfStatus().add(status);
                    log.info("Status a cadastrar {}", status.getName());
                }
            }

            WorkflowVO workflowSaved = workflowService.create(workflow);
            Map<String, String> kanbanTemplateVO = new HashMap<>();
            kanbanTemplateVO.put("workflowId", String.valueOf(workflowSaved.getId()));
            kanbanTemplateVO.put("projectId", String.valueOf(workflowSaved.getProjectId()));

            log.info("Enviando as informações  para o RabbitMQ criar o Kanban Template: {}", kanbanTemplateVO);
            rabbitTemplate.convertAndSend(RabbitMQConstants.TASKFLOW_KANBAN_PROJECT_TEMPLATE, kanbanTemplateVO);

        } catch (Exception e) {
            log.error("Erro ao executar o JOB", e);
        }
    }
}
