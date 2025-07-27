import Page from "@/@core/utils/page";
import ProjectStatus from "../model/projectStatus";
import ProjectStatusResponse from "../model/projectStatusResponse";
import ProjectStatusService from "../service/projectStatusService";

export const useProjectStatustore = defineStore("projectStatus", {
    state: () => ({
        projectStatus: ref<ProjectStatus | null>(null),
        pageResultProjectStatus: ref<Page<ProjectStatusResponse> | null>(null),
    }),

    actions: {
        async save(projectStatus: ProjectStatus) {
            const service = new ProjectStatusService();
            await service.save(projectStatus);
        },

        async findById(id: number) {
            const service = new ProjectStatusService();
            this.projectStatus = await service.findById(id);
        },

        async findByFilter() {
            const service = new ProjectStatusService();
            this.pageResultProjectStatus = await service.findByFilter();
        },

        resetProjectStatus() {
            this.projectStatus = new ProjectStatus();
        },

        resetState() {
            this.resetProjectStatus();
            this.pageResultProjectStatus = null;
        },
    },
});
