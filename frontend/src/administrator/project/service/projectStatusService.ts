import Page from "@/@core/utils/page";
import { http } from "@/plugins/axios/axios";
import ProjectStatus from "../model/projectStatus";
import ProjectStatusResponse from "../model/projectStatusResponse";

export default class ProjectStatusService {
    private _endpoint: string;

    constructor() {
        this._endpoint = "/api/v1/project-status";
    }

    async save(projectStatus: ProjectStatus): Promise<ProjectStatus> {
        if (projectStatus.id === null) {
            return this.create(projectStatus);
        }

        return this.update(projectStatus);
    }

    private async create(projectStatus: ProjectStatus): Promise<ProjectStatus> {
        const response = await http.post(this._endpoint, projectStatus);

        return response.data;
    }

    private async update(projectStatus: ProjectStatus): Promise<ProjectStatus> {
        const response = await http.put(this._endpoint, projectStatus);

        return response.data;
    }

    async findById(id: number): Promise<ProjectStatus> {
        const response = await http.get(`${this._endpoint}/${id}`);

        return response.data;
    }

    async findByFilter(): Promise<Page<ProjectStatusResponse>> {
        const response = await http.get(`${this._endpoint}/filter`);

        return response.data;
    }
}
