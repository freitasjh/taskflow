export default class WorkflowResponse {
    id: number | null;
    name: string;
    totalStatus: number;

    constructor() {
        this.id = null;
        this.name = "";
        this.totalStatus = 0;
    }
}
