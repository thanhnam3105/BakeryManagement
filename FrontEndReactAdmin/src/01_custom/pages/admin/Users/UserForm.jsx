import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { userSchema } from '@/validators/userValidator';
import { createUser, getUserById, updateUser } from '@/services/userService';
import Button from '@/components/common/Button';

const UserForm = () => {
  const [formData, setFormData] = useState({ name: '', email: '', role: '' });
  const [errors, setErrors] = useState({});
  const navigate = useNavigate();
  const { userId } = useParams(); // For editing existing user

  useEffect(() => {
    if (userId) {
      getUserById(userId).then((user) => {
        setFormData({ name: user.name, email: user.email, role: user.role });
      });
    }
  }, [userId]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const validateForm = () => {
    try {
      userSchema.validateSync(formData, { abortEarly: false });
      setErrors({});
      return true;
    } catch (err) {
      const validationErrors = err.inner.reduce((acc, curr) => {
        acc[curr.path] = curr.message;
        return acc;
      }, {});
      setErrors(validationErrors);
      return false;
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (validateForm()) {
      if (userId) {
        updateUser(userId, formData).then(() => navigate('/admin/users'));
      } else {
        createUser(formData).then(() => navigate('/admin/users'));
      }
    }
  };

  return (
    <div>
      <h2>{userId ? 'Edit User' : 'Add New User'}</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Name</label>
          <input type="text" name="name" value={formData.name} onChange={handleChange} placeholder="Enter name" />
          {errors.name && <span>{errors.name}</span>}
        </div>

        <div>
          <label>Email</label>
          <input type="email" name="email" value={formData.email} onChange={handleChange} placeholder="Enter email" />
          {errors.email && <span>{errors.email}</span>}
        </div>

        <div>
          <label>Role</label>
          <input type="text" name="role" value={formData.role} onChange={handleChange} placeholder="Enter role" />
          {errors.role && <span>{errors.role}</span>}
        </div>

        <Button text="Save" type="submit" />
      </form>
    </div>
  );
};

export default UserForm;
