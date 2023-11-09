import { request } from '@/utils'

export default {
  // 获取用户信息
  getUser: () => request.get('/system/user/info'),
  // 刷新token
  refreshToken: () => request.post('/auth/refreshToken', null, { noNeedTip: true }),
  // 获取菜单信息
  getMenus: () => request.get('/system/menu/getMenus'),

  // 获取服务器状态
  getServerState: () => request.get('/system/log/getServerState'),

  // 获取区块链信息
  getBlockchainInfo: () => request.get('/system/blockchain/getBlockchainInfo'),
  // 获取区块链24小时交易量
  getTransVolume: () => request.get('/system/blockchain/getTransVolume'),
  // 获取区块交易列表
  getTransactionList: (Offset, Limit) => request.get('/system/blockchain/getTransactionList/' + Offset + '/10'),
  // 获取区块交易信息
  getTransactionInfo: (TxId) => request.get('/system/blockchain/getTransactionInfo/' + TxId),

  // 获取用户列表
  getUserList: (current, size) => request.get('/system/user/getUserList/' + current + '/' + size),
  // 删除用户
  deleteUser: (userId) => request.delete('/system/user/deleteUser/' + userId),
  // 重置用户密码
  resetUser: (userId) => request.put('/system/user/resetUser/' + userId),
  // 修改用户信息
  updateUser: (userInfo) => request.put('/system/user/updateUser', userInfo),
  // 用户注册
  register: (userInfo) => request.post('/system/user/register', userInfo),

  // 添加模型
  addModel: (modelInfo) => request.post('/three/device/addModel', modelInfo),
  // 获取模型上传链接
  getUploadModelUrl: (extension) => request.get('/three/device/getUploadModelUrl?extension=' + extension),
  // 上传模型
  uploadModel: (url, model) => request.put(url, model),
  // 获取模型列表
  getModelList: (current, size) => request.get('/three/device/getModelList/' + current + '/' + size),
  // 获取模型信息
  getModelInfo: (deviceId) => request.get('/three/device/getModelInfo/' + deviceId),
  // 修改模型信息
  updateModel: (deviceInfo) => request.put('/three/device/updateModel', deviceInfo),
  // 删除模型
  deleteModel: (deviceId) => request.delete('/three/device/deleteModel?deviceId=' + deviceId),

  // 获取场景列表
  getSceneList: (current, size) => request.get('/three/scene/getSceneList/' + current + '/' + size),
  // 删除场景
  deleteScene: (sceneId) => request.delete('/three/scene/deleteScene/?sceneId=' + sceneId),
  // 修改场景
  updateScene: (sceneInfo) => request.put('/three/scene/updateScene', sceneInfo),
  // 添加场景
  addScene: (sceneInfo) => request.post('/three/scene/addScene', sceneInfo),

  // 获取场景中设备部署信息
  getSceneDeviceDeployInfo: (sceneId) => request.get('/three/deviceDeploy/getSceneDeviceDeployInfo/' + sceneId),
  // 获取部署设备信息
  getDeviceDeployInfo: (deviceDeployId) => request.get('/three/deviceDeploy/getDeviceDeployInfo/' + deviceDeployId),
  // 更新部署设备信息
  updataDeviceDeployInfo: (deviceDeployList) => request.post('/three/deviceDeploy/updataDeviceDeployInfo', deviceDeployList),
  //删除部署设备
  deleteDeviceDeploy: (deviceDeployId) => request.delete('/three/deviceDeploy/deleteDeviceDeploy?deviceDeployId=' + deviceDeployId),
  // 更新设备状态信息
  updataDeviceInfo: (deviceDeployInfo) => request.post('/three/deviceDeploy/updataDeviceInfo', deviceDeployInfo),

  // 获取登录日志
  getLoginLog: (current, size) => request.get('/system/log/getLoginLog/' + current + '/' + size),
  // 获取操作日志
  getOperaLog: (current, size) => request.get('/system/log/getOperaLog/' + current + '/' + size),
  // 获取登录日志信息
  getLoginLogInfo: (id) => request.get('/system/log/getLoginLogInfo/' + id),
  // 获取操作日志信息
  getOperaLogInfo: (id) => request.get('/system/log/getOperaLogInfo/' + id),
}
