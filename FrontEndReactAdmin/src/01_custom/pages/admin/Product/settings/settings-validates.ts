import * as Yup from 'yup';
import { MESSAGES } from '../../../../../config/constant';

export const validationSettings = Yup.object().shape({
  cd_product: Yup.string()
    .required(MESSAGES.PRODUCT.CODE_REQUIRED)
    // .matches(/^[A-Z0-9]+$/, 'Mã sản phẩm chỉ được chứa chữ hoa và số')
    // .min(3, 'Mã sản phẩm phải có ít nhất 3 ký tự')
    .max(10, MESSAGES.PRODUCT.CODE_MAX_LENGTH),

  nm_product: Yup.string()
    .required(MESSAGES.PRODUCT.NAME_REQUIRED)
    // .min(2, 'Tên sản phẩm phải có ít nhất 2 ký tự')
    .max(100, MESSAGES.PRODUCT.NAME_MAX_LENGTH),

  price: Yup.number()
    .required(MESSAGES.PRODUCT.PRICE_REQUIRED)
    .min(0, MESSAGES.PRODUCT.PRICE_MIN)
    .max(1000000000, MESSAGES.PRODUCT.PRICE_MAX),

  cd_category: Yup.string()
    .required(MESSAGES.PRODUCT.CATEGORY_REQUIRED)
    // .min(2, 'Danh mục phải có ít nhất 2 ký tự')
    .max(50, MESSAGES.PRODUCT.CATEGORY_MAX_LENGTH),

  cd_size: Yup.string()
    .required(MESSAGES.PRODUCT.SIZE_REQUIRED),
    // .oneOf(['Small', 'Medium', 'Large'], 'Kích thước phải là Small, Medium hoặc Large'),

  image: Yup.string()
    .required(MESSAGES.PRODUCT.IMAGE_REQUIRED),
    // .url('Hình ảnh phải là một URL hợp lệ'),

  cd_status: Yup.string()
    .required(MESSAGES.PRODUCT.STATUS_REQUIRED)
    // .oneOf(['Available', 'Unavailable'], 'Trạng thái phải là Available hoặc Unavailable')
}); 