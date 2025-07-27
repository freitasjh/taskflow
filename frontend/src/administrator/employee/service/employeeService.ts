import Page from "@/@core/utils/page";
import Employee from "@/administrator/employee/model/employee";
import { http } from "@/plugins/axios/axios";

export default class EmployeeService {
    private _endpoint = "/api/v1/employees";
    constructor() {}

    public async save(employee: Employee): Promise<Employee> {
        console.log(employee);
        if (employee.id === null) {
            return await this.create(employee);
        }

        return await this.update(employee);
    }

    private async create(employee: Employee): Promise<Employee> {
        console.log(employee);
        const response = await http.post<Employee>(
            this._endpoint,
            employee.toJson(),
        );

        return response.data;
    }

    private async update(employee: Employee): Promise<Employee> {
        const response = await http.put<Employee>(
            `${this._endpoint}/${employee.id}`,
            employee,
        );

        return response.data;
    }

    public async findById(id: number): Promise<Employee> {
        const response = await http.get<Employee>(`${this._endpoint}/${id}`);

        return response.data;
    }

    public async findByFilter(): Promise<Page<Employee>> {
        const response = await http.get<Page<Employee>>(
            `${this._endpoint}/filter`,
        );

        return response.data;
    }
}
