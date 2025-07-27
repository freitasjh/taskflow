import Status from "./status";

export default class Workflow {
    id: number | null;
    name: string;
    teamId: number | null;
    listOfStatus: Status[];

    constructor() {
        this.id = null;
        this.name = "";
        this.teamId = null;
        this.listOfStatus = [];
    }
}
