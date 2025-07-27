export default class ProjectResponse {
    id: number | null;
    name: string;
    teamName: string | null;
    prefix: string | null;
    status: string | null;

    constructor() {
        this.id = null;
        this.name = "";
        this.teamName = "";
        this.prefix = "";
        this.status = null;
    }
}
