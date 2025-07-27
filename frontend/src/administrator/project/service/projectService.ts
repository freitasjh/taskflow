import Page from "@/@core/utils/page";
import { http } from "@/plugins/axios/axios";
import Project from "../model/project";
import ProjectResponse from "../model/projectRespone";

export default class ProjectService {
    private _endpoint: string = "/api/v1/projects";

    async save(project: Project): Promise<Project> {
        if (project.id === null) {
            return this.create(project);
        }

        return this.update(project);
    }

    private async create(project: Project): Promise<Project> {
        const response = await http.post(this._endpoint, project);

        return response.data;
    }

    private async update(project: Project): Promise<Project> {
        const respone = await http.put(this._endpoint, project);

        return respone.data;
    }

    async findById(id: number): Promise<Project> {
        const response = await http.get(`${this._endpoint}/${id}`);

        return response.data;
    }

    async findByFilter(): Promise<Page<ProjectResponse>> {
        const response = await http.get(`${this._endpoint}/filter`);

        return response.data;
    }
}
