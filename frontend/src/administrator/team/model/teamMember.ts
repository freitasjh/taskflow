import EmployeeDTO from "@/administrator/employee/model/employeeDTO";

export default class TeamMember {
    employee: EmployeeDTO | null;
    teamId: number | null;

    constructor() {
        this.employee = null;
        this.teamId = null;
    }
}
