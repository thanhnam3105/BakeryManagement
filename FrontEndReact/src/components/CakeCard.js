import React from 'react';
import { Link } from 'react-router-dom';

const CakeCard = ({ cake }) => {
  return (
    <div className="cake-card">
      <img src={cake.imageUrl} alt={cake.name} />
      <h3>{cake.name}</h3>
      <p>{cake.price} VND</p>
      <Link to={`/cake/${cake.id}`}>Xem chi tiết</Link>
    </div>
  );
};

export default CakeCard;
