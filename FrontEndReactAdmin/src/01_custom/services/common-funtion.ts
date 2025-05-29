/**
 * Recursively converts any empty strings in the given value to null.
 * This is useful for preparing data to be sent to the server, since
 * empty strings are not valid for many types of data, but null is.
 * @param value The value to convert. This can be a string, an array, an object, or anything else.
 * @returns The converted value, which is the same as the input except that any empty strings have been replaced with null.
 */
export function convertBlankToNull(value: any): any {
  // If the value is an empty string, return null
  if (value === '') {
    return null;
  }

  // If the value is an array, recursively convert each of its elements
  if (Array.isArray(value)) {
    return value.map(item => convertBlankToNull(item));
  }

  // If the value is an object, recursively convert each of its properties
  if (typeof value === 'object' && value !== null) {
    const convertedObject: any = {};
    for (const key in value) {
      convertedObject[key] = convertBlankToNull(value[key]);
    }
    return convertedObject;
  }
  return value;
}



export function getStatusColor(status: string) : any {
  switch (status) {
    case '1':
      return 'info';
    case '2':
      return 'info';
    case '3':
      return 'warning';
    case '4':
      return 'success';
    case '5':
      return 'error';
    default:
      return 'default';
  }
};

