import Page from "@/@core/utils/page";
import TeamResponse from "@/administrator/team/model/teamResponse";
import TeamService from "@/administrator/team/service/teamService";
import WorkflowResponse from "@/administrator/workflow/model/workflowResponse";
import WorkflowService from "@/administrator/workflow/service/workflowService";
import KanbanBoard from "../model/kanbanBoard";
import KanbanBoardResponse from "../model/kanbanBoardResponse";
import KanbanBoardService from "../service/kanbanBoardService";

export const useKanbanBoardStore = defineStore("kanbanBoard", {
    state: () => ({
        KanbanBoard: ref<KanbanBoard | null>(new KanbanBoard()),
        kanbanPage: ref<Page<KanbanBoardResponse> | null>(null),
        teamSelected: ref<TeamResponse | null>(null),
        workflowSelected: ref<WorkflowResponse | null>(null),
        teamPageResult: ref<Page<TeamResponse> | null>(null),
        workflowPageResult: ref<Page<WorkflowResponse> | null>(null),
    }),

    getters: {},

    actions: {
        async save(kanbanBordToSave: KanbanBoard) {
            const service = new KanbanBoardService();
            await service.save(kanbanBordToSave);

            //Se eu uso o kanbanBoard do store eu não deveria usar ele ?
            this.KanbanBoard = new KanbanBoard();
        },
        async findTeam() {
            const teamService = new TeamService();
            this.teamPageResult = await teamService.findByFilter();
        },

        async findWorkflow() {
            const workflowService = new WorkflowService();
            this.workflowPageResult = await workflowService.findByFilter();
        },

        resetForm() {
            this.KanbanBoard = new KanbanBoard();
            this.teamSelected = new TeamResponse();
            this.workflowSelected = new WorkflowResponse();
        },

        resetState() {
            this.resetForm();
            this.kanbanPage = null;
        },
    },
});
