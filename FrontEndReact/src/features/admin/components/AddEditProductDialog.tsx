import React from "react";
import {
  Dialog, DialogTitle, DialogContent, DialogActions,
  TextField, Button, Stack
} from "@mui/material";

export default function ProductFormDialog({
  open,
  onClose,
  onSave,
  formData,
  setFormData,
}: {
  open: boolean;
  onClose: () => void;
  onSave: () => void;
  formData: any;
  setFormData: (data: any) => void;
}) {
  return (
    <Dialog open={open} onClose={onClose} maxWidth="sm" fullWidth>
      <DialogTitle>
        {formData.id ? "Chỉnh sửa sản phẩm" : "Thêm sản phẩm"}
      </DialogTitle>
      <DialogContent>
      <Stack spacing={2} mt={1}>
            <TextField
              label="Cake ID"
              fullWidth
              value={formData.cake_id}
              onChange={(e) =>
                setFormData({ ...formData, cake_id: e.target.value })
              }
            />
            <TextField
              label="Tên sản phẩm"
              fullWidth
              value={formData.cake_name}
              onChange={(e) =>
                setFormData({ ...formData, cake_name: e.target.value })
              }
            />
            <TextField
              label="Category ID"
              fullWidth
              value={formData.category_id}
              onChange={(e) =>
                setFormData({ ...formData, category_id: e.target.value })
              }
            />
            <TextField
              label="Size"
              fullWidth
              value={formData.size}
              onChange={(e) =>
                setFormData({ ...formData, size: e.target.value })
              }
            />
            <TextField
              label="Giá"
              fullWidth
              type="number"
              value={formData.price}
              onChange={(e) =>
                setFormData({ ...formData, price: Number(e.target.value) })
              }
            />
            <TextField
              label="Hình ảnh"
              fullWidth
              value={formData.image}
              onChange={(e) =>
                setFormData({ ...formData, image: e.target.value })
              }
            />
            <TextField
              label="Mô tả"
              fullWidth
              multiline
              rows={2}
              value={formData.description}
              onChange={(e) =>
                setFormData({ ...formData, description: e.target.value })
              }
            />
            <TextField
              label="Trạng thái"
              fullWidth
              value={formData.status}
              onChange={(e) =>
                setFormData({ ...formData, status: e.target.value })
              }
            />
          </Stack>
      </DialogContent>
      <DialogActions>
        <Button onClick={onSave} variant="contained">Lưu</Button>
        <Button onClick={onClose}>Hủy</Button>
      </DialogActions>
    </Dialog>
  );
}
