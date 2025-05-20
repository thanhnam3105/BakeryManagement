export const BASE_URL = '/app/dashboard/default';
export const BASE_TITLE = ' | Datta Able Free React Hooks + Admin Template';

export const CONFIG = {
  layout: 'vertical'
};

export const PRODUCT_STATUS = {
  AVAILABLE: {
    value: 'available',
    color: 'success'
  },
  OUT_OF_STOCK: {
    value: 'out of stock',
    color: 'error'
  },
  PENDING: {
    value: 'pending',
    color: 'warning'
  },
  DEFAULT: {
    value: 'default',
    color: 'default'
  }
};

export const LBL_PRODUCT = {
  TITLE: 'Products',
  ADD_PRODUCT: 'Thêm mới',
  SEARCH: 'Tìm kiếm',
  ACTIONS: 'Thao tác',
  STATUS: 'Trạng thái',
  IMAGE: 'Hình ảnh',
  ID: 'ID',
  PRODUCT_CODE: 'Mã sản phẩm',
  PRODUCT_NAME: 'Tên sản phẩm',
  PRICE: 'Giá',
  CATEGORY: 'Danh mục',
  STOCK: 'Tồn kho',
  SIZE: 'Kích thước',
  CONFIRM_DELETE: 'Xác nhận xóa',
  CONFIRM_DELETE_MESSAGE: 'Bạn có chắc chắn muốn xóa sản phẩm "'
};

export const ORDER_STATUS = {
  DELIVERED: {
    value: 'Delivered',
    color: 'success'
  },
  PENDING: {
    value: 'Pending',
    color: 'warning'
  },
  PROCESSING: {
    value: 'Processing',
    color: 'info'
  },
  CANCELLED: {
    value: 'Cancelled',
    color: 'error'
  },
  DEFAULT: {
    value: 'default',
    color: 'default'
  }
};

export const LABELS_ORDER = {
  TITLE: '📦 Orders',
  SEARCH: 'Tìm kiếm đơn hàng',
  ACTIONS: 'Actions',
  ORDER_ID: 'Order ID',
  CUSTOMER_ID: 'Customer ID',
  STAFF_ID: 'Staff ID',
  BRANCH_ID: 'Branch ID',
  ORDER_DATE: 'Order Date',
  DELIVERY_DATE: 'Delivery Date',
  STATUS: 'Status',
  TOTAL_AMOUNT: 'Total (₫)',
  DELIVERY_ADDRESS: 'Delivery Address',
  PAYMENT_METHOD: 'Payment',
  CONFIRM_DELETE: 'Xác nhận xóa',
  CONFIRM_DELETE_MESSAGE: 'Bạn có chắc chắn muốn xóa đơn hàng "'
};

export const statusOptions = [
  { value: '1', name: 'Pending' },
  { value: '2', name: 'Processing' },
  { value: '3', name: 'Shipped' },
  { value: '4', name: 'Delivered' }
];

export const paymentMethodOptions = [
  { value: '1', name: 'Credit Card' },
  { value: '2', name: 'PayPal' },
  { value: '3', name: 'Bank Transfer' },
  { value: '4', name: 'Cash on Delivery' }
];

export const LBL_PRODUCT_DIALOG = {
  TITLE_ADD: 'Thêm sản phẩm mới',
  TITLE_EDIT: 'Sửa sản phẩm',
  BUTTON_CANCEL: 'Hủy',
  BUTTON_ADD: 'Thêm mới',
  BUTTON_UPDATE: 'Cập nhật',
  SUCCESS_ADD: 'Thêm mới thành công!',
  SUCCESS_UPDATE: 'Cập nhật thành công!',
  ERROR_SAVE: 'Đã xảy ra lỗi khi lưu sản phẩm. Vui lòng thử lại.'
};

export const LBL_PRODUCT_FORM = {
  PRODUCT_CODE: 'Mã sản phẩm',
  PRODUCT_NAME: 'Tên sản phẩm',
  PRICE: 'Giá',
  CATEGORY: 'Danh mục',
  SIZE: 'Kích thước',
  IMAGE: 'Hình ảnh',
  DESCRIPTION: 'Mô tả',
  STATUS: 'Trạng thái'
};

export const MESSAGES = {
  PRODUCT: {
    CODE_REQUIRED: 'Mã sản phẩm là bắt buộc',
    CODE_MAX_LENGTH: 'Mã sản phẩm không được vượt quá 10 ký tự',
    NAME_REQUIRED: 'Tên sản phẩm là bắt buộc',
    NAME_MAX_LENGTH: 'Tên sản phẩm không được vượt quá 100 ký tự',
    PRICE_REQUIRED: 'Giá sản phẩm là bắt buộc',
    PRICE_MIN: 'Giá sản phẩm không được âm',
    PRICE_MAX: 'Giá sản phẩm không được vượt quá 1 tỷ',
    CATEGORY_REQUIRED: 'Danh mục là bắt buộc',
    CATEGORY_MAX_LENGTH: 'Danh mục không được vượt quá 50 ký tự',
    SIZE_REQUIRED: 'Kích thước là bắt buộc',
    IMAGE_REQUIRED: 'Hình ảnh là bắt buộc',
    STATUS_REQUIRED: 'Trạng thái là bắt buộc'
  }
};

export const categoryOptions = [
  { value: 'BM', name: 'Bánh mì' },
  { value: 'BN', name: 'Bánh ngọt' },
  { value: 'BK', name: 'Bánh kem' },
  { value: 'BQ', name: 'Bánh quy' },
  { value: 'BTT', name: 'Bánh trung thu' }
];
