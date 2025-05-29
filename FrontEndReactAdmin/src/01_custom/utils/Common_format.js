/**
 * Collection of common formatting utility functions
 */

/**
 * Formats a date string according to the specified format pattern
 * @param {string} dateString - The date string to format
 * @param {string} dateFormat - The format pattern (e.g., 'yyyy/MM/dd', 'dd/MM/yyyy')
 * @returns {string} Formatted date string
 */
const formatDate = (dateString, dateFormat = 'dd/MM/yyyy') => {
  if (!dateString) return '';
  
  const date = new Date(dateString);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');

  return dateFormat
    .replace('yyyy', year)
    .replace('MM', month)
    .replace('dd', day);
};

/**
 * Formats a number as currency
 * @param {number} amount - The amount to format
 * @param {string} currency - The currency code (default: 'VND')
 * @param {string} locale - The locale to use for formatting (default: 'vi-VN')
 * @returns {string} Formatted currency string
 */
const formatCurrency = (amount, currency = 'VND', locale = 'vi-VN') => {
  if (amount === null || amount === undefined) return '';
  
  return new Intl.NumberFormat(locale, {
    style: 'currency',
    currency: currency,
    minimumFractionDigits: 0,
    maximumFractionDigits: 0
  }).format(amount);
};

export {
  formatDate,
  formatCurrency
};
