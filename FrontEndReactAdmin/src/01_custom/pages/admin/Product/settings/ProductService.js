import axios from 'axios';

const API_BASE_URL = 'https://your-api.com/api'; // Đổi lại URL thật

const orderService = {
  getAll: () => axios.get(`${API_BASE_URL}/orders`),
  getById: (id) => axios.get(`${API_BASE_URL}/orders/${id}`),
  create: (data) => axios.post(`${API_BASE_URL}/orders`, data),
  update: (id, data) => axios.put(`${API_BASE_URL}/orders/${id}`, data),
  delete: (id) => axios.delete(`${API_BASE_URL}/orders/${id}`)
};

export default orderService;
