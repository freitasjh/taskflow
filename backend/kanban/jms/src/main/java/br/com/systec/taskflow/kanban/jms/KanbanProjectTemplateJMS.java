package br.com.systec.taskflow.kanban.jms;

import br.com.systec.taskflow.kanban.api.service.KanbanService;
import br.com.systec.taskflow.kanban.api.vo.KanbanVO;
import br.com.systec.taskflow.project.api.service.ProjectService;
import br.com.systec.taskflow.project.api.vo.ProjectVO;
import br.com.systec.taskflow.rabbitmq.utils.RabbitMQConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Component
public class KanbanProjectTemplateJMS {
    private static final Logger log = LoggerFactory.getLogger(KanbanProjectTemplateJMS.class);

    private final KanbanService kanbanService;
    private final ProjectService projectService;

    public KanbanProjectTemplateJMS(KanbanService kanbanService, ProjectService projectService) {
        this.kanbanService = kanbanService;
        this.projectService = projectService;
    }

    @RabbitListener(queues = RabbitMQConstants.TASKFLOW_KANBAN_PROJECT_TEMPLATE)
    @Transactional(propagation = Propagation.REQUIRED)
    public void execute(@Payload Message message) {
        try {
            log.info("Iniciando a execução do JMS do Kanban Project Template");
            Map<String, String> payload = (Map<String, String>) message.getPayload();
            ProjectVO project = projectService.findById(Long.parseLong(payload.get("projectId")));

            KanbanVO kanban = new KanbanVO();
            kanban.setProjectId(project.getId());
            kanban.setName(project.getName());
            kanban.setWorkflowId(Long.parseLong(payload.get("workflowId")));

            kanbanService.create(kanban);

        } catch (Exception e) {
            log.error("Erro ao executar o JMS do Kanban Project Template", e);
        }
    }
}
