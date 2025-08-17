import Page from "@/@core/utils/page";
import { http } from "@/plugins/axios/axios";
import KanbanBoard from "../model/kanbanBoard";
import KanbanBoardResponse from "../model/kanbanBoardResponse";

export default class KanbanBoardService {
    private _endpoint: string;

    constructor() {
        this._endpoint = "/api/v1/kanban-boards";
    }

    async save(kanbanBoard: KanbanBoard): Promise<KanbanBoard> {
        if (kanbanBoard.id === null) {
            return this.create(kanbanBoard);
        }

        return this.update(kanbanBoard);
    }

    private async create(KanbanBoard: KanbanBoard): Promise<KanbanBoard> {
        const response = await http.post(this._endpoint, KanbanBoard);

        return response.data;
    }

    private async update(KanbanBoard: KanbanBoard): Promise<KanbanBoard> {
        const response = await http.put(this._endpoint, KanbanBoard);

        return response.data;
    }

    async findById(kanbanId: number): Promise<KanbanBoard> {
        const response = await http.get(this._endpoint);

        return response.data;
    }

    async findByFilter(): Promise<Page<KanbanBoardResponse>> {
        const response = await http.get(`${this._endpoint}/filter`);

        return response.data;
    }
}
