import { LBL_PRODUCT_FORM, categoryOptions } from '../../../../../config/constant';

export const formFieldSettings = {
  cd_product: {
    settingInput: {
      name: "cd_product",
      labelName: LBL_PRODUCT_FORM.PRODUCT_CODE,
      type: "text",
      required: true
    },
    controlName: "cd_product",
  },
  nm_product: {
    settingInput: {
      name: "nm_product",
      labelName: LBL_PRODUCT_FORM.PRODUCT_NAME,
      type: "text",
      required: true
    },
    controlName: "nm_product",
  },
  price: {
    settingInput: {
      name: "price",
      labelName: LBL_PRODUCT_FORM.PRICE,
      type: "number",
      required: true
    },
    controlName: "price",
  },
  cd_category: {
    settingInput: {
      name: "cd_category",
      label: LBL_PRODUCT_FORM.CATEGORY,
      type: "select",
      options: categoryOptions,
      required: true
    },
    controlName: "cd_category",
  },
  cd_size: {
    settingInput: {
      name: "cd_size",
      labelName: LBL_PRODUCT_FORM.SIZE,
      type: "text"
    },
    controlName: "cd_size",
  },
  cd_status: {
    settingInput: {
      name: "cd_status",
      label: LBL_PRODUCT_FORM.STATUS,
      type: "select",
      // options: [
      //   { value: '1', name: 'Available' },
      //   { value: '2', name: 'Out of Stock' },
      //   { value: '3', name: 'Pending' },
      //   { value: '4', name: 'Default' }
      // ]
    },
    controlName: "cd_status",
  },
  image: {
    settingInput: {
      name: "image",
      labelName: LBL_PRODUCT_FORM.IMAGE,
      type: "text"
    },
    controlName: "image",
  },
  description: {
    settingInput: {
      name: "description",
      labelName: LBL_PRODUCT_FORM.DESCRIPTION,
      type: "text",
      multiline: true,
      rows: 4
    },
    controlName: "description",
  }
};
