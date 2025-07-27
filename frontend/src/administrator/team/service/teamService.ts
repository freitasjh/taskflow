import Page from "@/@core/utils/page";
import { http } from "@/plugins/axios/axios";
import Team from "../model/team";
import TeamResponse from "../model/teamResponse";

export default class TeamService {
    private _endpoint = "/api/v1/teams";

    async save(team: Team): Promise<Team> {
        if (team.id === null) {
            return await this.create(team);
        }

        return this.update(team);
    }

    private async create(team: Team): Promise<Team> {
        const response = await http.post(this._endpoint, team);

        return response.data;
    }

    private async update(team: Team): Promise<Team> {
        const response = await http.put(`${this._endpoint}`, team);

        return response.data;
    }

    async findById(id: number): Promise<Team> {
        const response = await http.get(`${this._endpoint}/${id}`);

        return response.data;
    }

    async findByFilter(): Promise<Page<TeamResponse>> {
        const response = await http.get<Page<TeamResponse>>(
            `${this._endpoint}/filter`,
        );

        return response.data;
    }
}
