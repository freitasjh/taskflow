export default class Employee {
    id: number | null
    name: string
    email: string
    phone: string
    cellphone: string
    federalId: string
    username: string
    password: string
    departament: string

    constructor() {
        this.id = null
        this.name = ''
        this.email = ''
        this.phone = ''
        this.cellphone = ''
        this.federalId = ''
        this.username = ''
        this.password = ''
        this.departament = ''
    }

    public toJson(): string {
        return JSON.stringify(this)
    }
}
