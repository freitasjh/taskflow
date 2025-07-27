import Page from "@/@core/utils/page";
import { defineStore } from "pinia";
import Workflow from "../model/workflow";
import WorkflowResponse from "../model/workflowResponse";
import WorkflowService from "../service/workflowService";

export const useWorkflowStore = defineStore("workflow", {
    state: () => ({
        workflow: ref<Workflow>(new Workflow()),
        workflowPageResult: ref<Page<WorkflowResponse> | null>(null),
    }),

    actions: {
        async save(workflow: Workflow) {
            const service = new WorkflowService();
            await service.save(workflow);
        },

        async findById(id: number) {
            const service = new WorkflowService();
            this.workflow = await service.findById(id);
        },

        async findByFilter() {
            const service = new WorkflowService();
            this.workflowPageResult = await service.findByFilter();
        },
    },
});
