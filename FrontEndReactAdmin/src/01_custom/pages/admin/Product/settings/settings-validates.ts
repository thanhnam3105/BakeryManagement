import * as Yup from 'yup';

export const validationSettings = Yup.object().shape({
  cake_id: Yup.string()
    .required('Mã sản phẩm là bắt buộc')
    // .matches(/^[A-Z0-9]+$/, 'Mã sản phẩm chỉ được chứa chữ hoa và số')
    // .min(3, 'Mã sản phẩm phải có ít nhất 3 ký tự')
    .max(10, 'Mã sản phẩm không được vượt quá 10 ký tự'),

  name: Yup.string()
    .required('Tên sản phẩm là bắt buộc')
    // .min(2, 'Tên sản phẩm phải có ít nhất 2 ký tự')
    .max(100, 'Tên sản phẩm không được vượt quá 100 ký tự'),

  price: Yup.number()
    .required('Giá sản phẩm là bắt buộc')
    .min(0, 'Giá sản phẩm không được âm')
    .max(1000000000, 'Giá sản phẩm không được vượt quá 1 tỷ'),

  category: Yup.string()
    .required('Danh mục là bắt buộc')
    // .min(2, 'Danh mục phải có ít nhất 2 ký tự')
    .max(50, 'Danh mục không được vượt quá 50 ký tự'),

  stock: Yup.number()
    .required('Số lượng tồn kho là bắt buộc')
    .min(0, 'Số lượng tồn kho không được âm')
    .max(10000, 'Số lượng tồn kho không được vượt quá 10000'),

  size: Yup.string()
    .required('Kích thước là bắt buộc'),
    // .oneOf(['Small', 'Medium', 'Large'], 'Kích thước phải là Small, Medium hoặc Large'),

  image: Yup.string()
    .required('Hình ảnh là bắt buộc'),
    // .url('Hình ảnh phải là một URL hợp lệ'),

  status: Yup.string()
    .required('Trạng thái là bắt buộc')
    // .oneOf(['Available', 'Unavailable'], 'Trạng thái phải là Available hoặc Unavailable')
}); 