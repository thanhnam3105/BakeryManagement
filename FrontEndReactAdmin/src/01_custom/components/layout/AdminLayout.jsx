// import React from 'react';
// import { Link } from 'react-router-dom';
// import PropTypes from 'prop-types';
// import './AdminLayout.css'; // Optional CSS module or regular CSS

// const AdminLayout = ({ title, children }) => {
//   return (
//     <div className="admin-layout">
//       <aside className="sidebar">
//         <h2>Admin Panel</h2>
//         <nav>
//           <ul>
//             <li>
//               <Link to="/admin/users">Users</Link>
//             </li>
//             <li>
//               <Link to="/admin/products">Products</Link>
//             </li>
//             <li>
//               <Link to="/admin/dashboard">Dashboard</Link>
//             </li>
//           </ul>
//         </nav>
//       </aside>

//       <main className="main-content">
//         <header className="header">
//           <h1>{title || 'Admin'}</h1>
//         </header>

//         <section className="content">{children}</section>
//       </main>
//     </div>
//   );
// };

// AdminLayout.propTypes = {
//   title: PropTypes.string,
//   children: PropTypes.node.isRequired
// };

// export default AdminLayout;
