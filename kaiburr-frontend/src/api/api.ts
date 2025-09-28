import axios from 'axios';

const API_BASE = import.meta.env.VITE_API_BASE ?? 'http://localhost:8080';

const api = axios.create({
  baseURL: API_BASE,
  timeout: 10000,
});

api.interceptors.response.use(
  res => res,
  err => {
    // nice centralized error format (optional)
    const msg = err?.response?.data?.message ?? err.message;
    return Promise.reject(new Error(msg));
  }
);

export default api;
