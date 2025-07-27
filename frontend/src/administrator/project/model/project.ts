import ProjectStatus from "./projectStatus";

export default class Project {
    id: number | null;
    description: string;
    name: string;
    status: ProjectStatus;
    teamId: number | null;
    teamName: string | null;
    prefix: string | null;

    constructor() {
        this.id = null;
        this.description = "";
        this.name = "";
        this.status = new ProjectStatus();
        this.teamId = null;
        this.teamName = null;
        this.prefix = null;
    }
}
