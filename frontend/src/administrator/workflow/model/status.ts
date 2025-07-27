import Workflow from "./workflow";

export default class Status {
    id: number | null;
    name: string;
    order: number;
    workflow: Workflow | null;

    constructor() {
        this.id = null;
        this.name = "";
        this.order = 0;
        this.workflow = null;
    }
}
