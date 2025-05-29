export const BASE_URL = '/app/dashboard/default';
export const BASE_TITLE = ' | Datta Able Free React Hooks + Admin Template';

export const CONFIG = {
  layout: 'vertical'
};

export const PRODUCT_STATUS = {
  Available: {
    value: '1',
    color: 'success'
  },
  OutOfStock: {
    value: '2',
    color: 'error'
  },
  Hidden: {
    value: '3',
    color: 'default'
  }
};

export const DataCbbProductStatus = [
  { value: '1', name: 'Đang bán' },
  { value: '2', name: 'Hết hàng' },
  { value: '3', name: 'Tạm ẩn' }
];

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
    value: '1',
    name: 'Đã giao hàng',
    color: 'success'
  },
  PENDING: {
    value: '2',
    name: 'Chờ xử lý',
    color: 'warning'
  },
  PROCESSING: {
    value: '3',
    name: 'Đang xử lý',
    color: 'info'
  },
  CANCELLED: {
    value: '4',
    name: 'Đã hủy',
    color: 'error'
  }
};

export const DataCbbOrderStatus = [
  { value: '1', name: 'Đã giao hàng' },
  { value: '2', name: 'Chờ xử lý' },
  { value: '3', name: 'Đang xử lý' },
  { value: '4', name: 'Đã hủy' }
];

export const LABELS_ORDER = {
  TITLE: '📦 Quản lý đơn hàng',
  SEARCH: 'Tìm kiếm đơn hàng',
  ACTIONS: 'Thao tác',
  ORDER_ID: 'Mã đơn hàng',
  CUSTOMER_ID: 'Khách hàng',
  STAFF_ID: 'Nhân viên',
  BRANCH_ID: 'Mã chi nhánh',
  ORDER_DATE: 'Ngày đặt hàng',
  DELIVERY_DATE: 'Ngày giao hàng',
  STATUS: 'Trạng thái',
  TOTAL_AMOUNT: 'Tổng tiền',
  DELIVERY_ADDRESS: 'Địa chỉ giao hàng',
  PAYMENT_METHOD: 'Phương thức thanh toán',
  DIALOG_TITLE: 'Cập nhật trạng thái đơn hàng',
  DIALOG_STATUS: 'Trạng thái',
  BUTTON_CANCEL: 'Hủy',
  BUTTON_SAVE: 'Lưu',
  BUTTON_SAVING: 'Đang lưu...',
  SUCCESS_UPDATE: 'Cập nhật trạng thái đơn hàng thành công!',
  ERROR_UPDATE: 'Lỗi khi cập nhật trạng thái đơn hàng: ',
  CLOSE: 'Đóng',
  DETAIL_TITLE: 'Chi tiết đơn hàng',
  CUSTOMER: 'Khách hàng',
  ORDER_DATE: 'Ngày đặt hàng',
  STATUS: 'Trạng thái',
  PRODUCT_LIST: 'Danh sách sản phẩm',
  TOTAL_PRICE: 'Tổng tiền',
  DETAIL_INDEX: 'No.',
  DETAIL_IMAGE: 'Image',
  DETAIL_PRODUCT_NAME: 'Product',
  DETAIL_QUANTITY: 'Quantity',
  DETAIL_PRICE: 'Price',
  DETAIL_TOTAL: 'Total',
  DETAIL_NOTE: 'Note',
  DETAIL_UNIT: 'Unit'
};

export const paymentMethodOptions = [
  { value: '1', name: 'Credit Card' },
  { value: '2', name: 'Bank Transfer' },
  { value: '3', name: 'Cash on Delivery' }
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

export const TABLE_HEADERS = {
  ACTIONS: '',
  STATUS: 'Status',
  IMAGE: 'Image',
  PRODUCT_CODE: 'Product Code',
  PRODUCT_NAME: 'Product Name',
  PRICE: 'Price',
  CATEGORY: 'Category',
  SIZE: 'Size'
};
