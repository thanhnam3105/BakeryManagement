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

export default formatDate;
