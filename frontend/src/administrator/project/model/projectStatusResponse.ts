export default class ProjectStatusResponse {
    id: number;
    name: string | null;
    description: string | null;

    constructor() {
        this.id = 0;
        this.name = null;
        this.description = null;
    }
}
