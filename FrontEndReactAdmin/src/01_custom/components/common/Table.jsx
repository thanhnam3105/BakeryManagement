import React, { useEffect, useState } from 'react';
import { getAllUsers } from '@/services/userService';
import Table from '@/components/common/Table';
import Button from '@/components/common/Button';

const UserTable = () => {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    getAllUsers().then(setUsers);
  }, []);

  const columns = ['ID', 'Name', 'Email', 'Role'];

  return (
    <div>
      <h2>Users</h2>
      <Button text="Add New User" onClick={() => alert('Open Form')} />
      <Table columns={columns} data={users.map((u) => [u.id, u.name, u.email, u.role])} />
    </div>
  );
};

export default UserTable;
