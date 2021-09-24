import createPersistedState from 'vuex-persistedstate'
import * as Cookies from 'js-cookie'
import cookie from 'cookie'

export default ({store, req, isDev}) => {
  createPersistedState({
    paths: ['configuration'],
    key: 'your_key',
    storage: {
      getItem: (key) => process.client ? (() => Cookies.get(key) != null ? JSON.parse(Cookies.get(key)) : {})() : cookie.parse(req.headers.cookie || '')[key],
      setItem: (key, value) => Cookies.set(key, JSON.stringify(value), {expires: 365, secure: !isDev}),
      removeItem: (key) => Cookies.remove(key)
    }
  })(store)
}
