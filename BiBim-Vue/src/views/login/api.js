import { request } from '@/utils'

export default {
  login: (data) => request.post('/system/admin/login', data, { noNeedToken: true }),
}
