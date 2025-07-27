import Login from '@/authenticate/model/login'
import { http } from '@/plugins/axios/axios'
import LoginResponse from '../model/loginResponse'

export default class AuthService {
  constructor() {}

  public async login(login: Login): Promise<LoginResponse> {
    const response = await http.post<LoginResponse>('/api/v1/login', login)

    return response.data
  }
}
