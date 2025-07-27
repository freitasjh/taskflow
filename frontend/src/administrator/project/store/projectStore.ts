import Page from "@/@core/utils/page";
import TeamResponse from "@/administrator/team/model/teamResponse";
import { defineStore } from "pinia";
import Project from "../model/project";
import ProjectResponse from "../model/projectRespone";
import ProjectStatusResponse from "../model/projectStatusResponse";
import ProjectService from "../service/projectService";

export const useProjectStore = defineStore("projectStore", {
    state: () => ({
        project: ref<Project | null>(null),
        pageResultProject: ref<Page<ProjectResponse> | null>(null),
        projectStatusSelect: ref<ProjectStatusResponse | null>(null),
        teamSelected: ref<TeamResponse | null>(null),
    }),

    actions: {
        async save(projectToSave: Project) {
            const service = new ProjectService();

            if (this.teamSelected !== null) {
                projectToSave.teamId = this.teamSelected.id;
            }

            if (this.projectStatusSelect != null) {
                projectToSave.status.id = this.projectStatusSelect.id;
            }
            console.log(projectToSave);
            await service.save(projectToSave);
        },

        async findById(id: number) {
            const service = new ProjectService();
            this.project = await service.findById(id);
        },

        async findByFilter() {
            const service = new ProjectService();
            this.pageResultProject = await service.findByFilter();
        },

        resetProject() {
            this.project = new Project();
            this.projectStatusSelect = null;
            this.teamSelected = null;
        },

        resetState() {
            this.resetProject();
            this.pageResultProject = null;
        },
    },
});
