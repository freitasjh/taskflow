import EmployeeDTO from "@/administrator/employee/model/employeeDTO";
import TeamMember from "./teamMember";

export default class Team {
    id: number | null;
    name: string;
    description: string;
    members: TeamMember[];
    prefix: string;

    constructor() {
        this.id = null;
        this.name = "";
        this.description = "";
        this.members = [];
        this.prefix = "";
    }

    hasMembers() {
        return this.members.length > 0;
    }

    addMember(employee: EmployeeDTO) {
        if (this.members === null) {
            this.members = [];
        }
        let teamMember = new TeamMember();
        teamMember.employee = employee;
        this.members.push(teamMember);
    }
}
