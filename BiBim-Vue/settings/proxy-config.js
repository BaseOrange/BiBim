const proxyConfigMappings = {
  dev: {
    prefix: '/api',
    target: 'http://192.168.23.1:8800',
  },
  test: {
    prefix: '',
    target: 'http://192.168.23.1:8800',
  },
  prod: {
    prefix: '',
    target: 'http://192.168.23.1:8800',
  },
}

export function getProxyConfig(envType = 'dev') {
  return proxyConfigMappings[envType]
}
