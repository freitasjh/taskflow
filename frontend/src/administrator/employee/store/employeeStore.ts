import Page from "@/@core/utils/page";
import EmployeeService from "@/administrator/employee/service/employeeService";
import { defineStore } from "pinia";
import Employee from "../model/employee";

export const useEmployeeStore = defineStore("employee", {
    state: () => ({
        employee: ref<Employee>(new Employee()),
        employeePage: ref<Page<Employee> | null>(null),
    }),

    getters: {
        getEmployee: (state) => state.employee,
    },

    actions: {
        async saveEmployee(employee: Employee): Promise<Employee> {
            const employeeService = new EmployeeService();
            return await employeeService.save(employee);
        },

        async findByFilter() {
            const employeeService = new EmployeeService();
            this.employeePage = await employeeService.findByFilter();
        },

        async findById(id: number) {
            const service = new EmployeeService();
            this.employee = await service.findById(id);
        },

        resetEmployee() {
            this.employee = new Employee();
        },

        resetState() {
            this.resetEmployee();
            this.employeePage = null;
        },
    },
});
