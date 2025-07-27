import { defineStore } from "pinia";
import Team from "../model/team";

import Page from "@/@core/utils/page";
import EmployeeDTO from "@/administrator/employee/model/employeeDTO";
import EmployeeService from "@/administrator/employee/service/employeeService";
import TeamResponse from "../model/teamResponse";
import TeamService from "../service/teamService";

export const useTeamStore = defineStore("team", {
    state: () => ({
        team: ref<Team>(new Team()),
        teamPage: ref<Page<TeamResponse> | null>(null),
        employeePage: ref<Page<EmployeeDTO> | null>(null),
        employeeSelected: ref<EmployeeDTO | null>(null),
    }),

    getters: {},

    actions: {
        async save(team: Team): Promise<Team> {
            const service = new TeamService();
            return await service.save(team);
        },

        async findByFilter() {
            const service = new TeamService();
            this.teamPage = await service.findByFilter();
        },

        async findById(id: number) {
            const service = new TeamService();
            this.team = await service.findById(id);
        },
        async findEmployee() {
            const employeeService = new EmployeeService();
            this.employeePage = await employeeService.findByFilter();
        },

        resetTeam() {
            this.team = new Team();
            this.employeeSelected = null;
        },

        resetState() {
            this.resetTeam();
            this.teamPage = null;
        },
    },
});
