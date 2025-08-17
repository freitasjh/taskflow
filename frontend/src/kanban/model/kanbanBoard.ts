export default class KanbanBoard {
    id: number | null;
    name: string;
    teamId: number | null;
    workflowId: number | null;

    constructor() {
        this.id = null;
        this.name = "";
        this.teamId = null;
        this.workflowId = null;
    }
}
