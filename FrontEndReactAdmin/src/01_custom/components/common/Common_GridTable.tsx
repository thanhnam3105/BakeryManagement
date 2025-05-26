import React from 'react';
import { Box, Typography, TextField, Stack, IconButton, Button, Chip } from '@mui/material';
import { DataGrid, GridColDef, GridPaginationModel, GridToolbar } from '@mui/x-data-grid';
import { Edit, Delete, Visibility } from '@mui/icons-material';
import { v4 as uuidv4 } from 'uuid';

export type ExtendedGridColDef = GridColDef & {
  formatType?: 'decimal' | 'status' | 'image';
  dataOptions?: { value: string; name: string; color?: string }[];
  actionEdit?: boolean;
  actionDelete?: boolean;
};

interface Common_GridTableProps {
  title: string;
  rows: any[];
  columns: ExtendedGridColDef[];
  search?: string;
  onSearchChange?: (value: string) => void;
  paginationModel?: GridPaginationModel;
  onPaginationModelChange?: (model: GridPaginationModel) => void;
  onEditClick?: (row: any) => void;
  onDeleteClick?: (row: any) => void;
  onViewClick?: (row: any) => void;
  children?: React.ReactNode;
  addButton?: {
    label: string;
    onClick: () => void;
    icon?: React.ReactNode;
  };
  hideSearch?: boolean;
  rowHeight?: number;
  tableHeight?: number;
}


const Common_GridTable: React.FC<Common_GridTableProps> = ({
  title,
  rows,
  columns,
  search,
  onSearchChange,
  paginationModel,
  onPaginationModelChange,
  onEditClick,
  onDeleteClick,
  onViewClick,
  children,
  addButton,
  hideSearch,
  rowHeight,
  tableHeight
}) => {
  const rowsWithId = rows.map((row) => ({
    ...row,
    id: row.id ?? uuidv4()
  }));

  const enhancedColumns = columns.map((col) => {
    if (col.field === 'actions' && (onEditClick || onDeleteClick || onViewClick)) {
      return {
        ...col,
        sortable: false,
        filterable: false,
        renderCell: (params: any) => (
          <Stack direction="row" spacing={1}>
            {onViewClick && (
              <IconButton color="primary" onClick={() => onViewClick(params.row)}>
                <Visibility />
              </IconButton>
            )}
            {onEditClick && (
              <IconButton color="warning" onClick={() => onEditClick(params.row)}>
                <Edit />
              </IconButton>
            )}
            {col.actionDelete && onDeleteClick && (
              <IconButton color="error" onClick={() => onDeleteClick(params.row)}>
                <Delete />
              </IconButton>
            )}
          </Stack>
        )
      };
    }

    // Add decimal formatting for columns with formatType: 'decimal'
    if (col.formatType === 'decimal') {
      return {
        ...col,
        valueFormatter: (params: any) => {
          if (params == null) return '';
          const numValue = Number(params);
          return isNaN(numValue) ? params : numValue.toLocaleString('en-US') + ' VNĐ';
        }
      };
    }

    // Add image formatting for columns with formatType: 'image'
    if (col.formatType === 'image') {
      return {
        ...col,
        renderCell: (params: any) => <img src={params.value} style={{ width: 60, height: 60, objectFit: 'cover' }} />
      };
    }

    if (col.dataOptions) {
      return {
        ...col,
        renderCell: (params: any) => {
          const option = col.dataOptions?.find((option) => option.value === params.value);
          return option ? option.name : params.value;
        }
      };
    }

    return col;
  });

  return (
    <Box p={3}>
      {title && ( <Typography variant="h4" gutterBottom>{title}</Typography> )}

      {!hideSearch && (
        <Box mb={2} display="flex" justifyContent="space-between" alignItems="center">
          <TextField
            label="Search"
            variant="outlined"
            value={search}
            onChange={(e) => onSearchChange?.(e.target.value)}
            sx={{ width: 300 }}
          />
          {addButton && (
            <Button variant="contained" startIcon={addButton.icon} onClick={addButton.onClick}> {addButton.label} </Button>
          )}
        </Box>
      )}

      <Box height={tableHeight ?? 500}>
        <DataGrid
          rows={rowsWithId}
          columns={enhancedColumns}
          paginationModel={paginationModel}
          onPaginationModelChange={onPaginationModelChange}
          pageSizeOptions={[5, 10]}
          disableRowSelectionOnClick
          slots={{ toolbar: GridToolbar }}
          rowHeight={rowHeight ?? 70}
          sx={{
            borderRadius: 2,
            boxShadow: 2,
            backgroundColor: '#fff'
          }}
        />
      </Box>

      {children}
    </Box>

  );
};

export default Common_GridTable;
