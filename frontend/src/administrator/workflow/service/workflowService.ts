import Page from "@/@core/utils/page";
import { http } from "@/plugins/axios/axios";
import Workflow from "../model/workflow";
import WorkflowResponse from "../model/workflowResponse";

export default class WorkflowService {
    private _endpoint: string;

    constructor() {
        this._endpoint = "/api/v1/workflows";
    }

    async save(workflow: Workflow): Promise<Workflow> {
        if (workflow.id === null) {
            return this.create(workflow);
        }

        return this.update(workflow);
    }

    private async create(workflow: Workflow): Promise<Workflow> {
        const response = await http.post(this._endpoint, workflow);

        return response.data;
    }

    private async update(workflow: Workflow): Promise<Workflow> {
        const response = await http.put(this._endpoint, workflow);

        return response.data;
    }

    async findById(workflowId: number): Promise<Workflow> {
        const response = await http.get(`${this._endpoint}/${workflowId}`);

        return response.data;
    }

    async findByFilter(): Promise<Page<WorkflowResponse>> {
        const response = await http.get(`${this._endpoint}/filter`);

        return response.data;
    }
}
