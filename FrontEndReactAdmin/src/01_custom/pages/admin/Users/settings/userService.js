import axiosClient from '@/api/axiosClient';

export const getAllUsers = async () => {
  const response = await axiosClient.get('/users');
  return response.data;
};

export const getUserById = async (id) => {
  const response = await axiosClient.get(`/users/${id}`);
  return response.data;
};

export const createUser = async (data) => {
  const response = await axiosClient.post('/users', data);
  return response.data;
};

export const updateUser = async (id, data) => {
  const response = await axiosClient.put(`/users/${id}`, data);
  return response.data;
};
