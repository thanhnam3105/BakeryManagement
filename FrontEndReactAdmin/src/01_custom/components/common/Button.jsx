import React from 'react';
import PropTypes from 'prop-types';

const Button = ({ text, onClick, type }) => (
  <button onClick={onClick} type={type || 'button'}>
    {text}
  </button>
);

Button.propTypes = {
  text: PropTypes.string.isRequired,
  onClick: PropTypes.func,
  type: PropTypes.string
};

export default Button;
