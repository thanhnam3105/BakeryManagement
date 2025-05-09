// src/components/DataGridTable.tsx
import React from 'react';
import { Box, Typography, TextField, Stack, IconButton } from '@mui/material';
import { DataGrid, GridColDef, GridPaginationModel, GridToolbar } from '@mui/x-data-grid';
import { Edit } from '@mui/icons-material';

interface DataGridTableProps {
  title: string;
  rows: any[];
  columns: GridColDef[];
  search: string;
  onSearchChange: (value: string) => void;
  paginationModel: GridPaginationModel;
  onPaginationModelChange: (model: GridPaginationModel) => void;
  onEditClick?: (row: any) => void;
  children?: React.ReactNode;
}

const DataGridTable: React.FC<DataGridTableProps> = ({
  title,
  rows,
  columns,
  search,
  onSearchChange,
  paginationModel,
  onPaginationModelChange,
  onEditClick,
  children
}) => {
  const enhancedColumns = columns.map((col) => {
    if (col.field === 'actions' && onEditClick) {
      return {
        ...col,
        sortable: false,
        filterable: false,
        renderCell: (params: any) => (
          <Stack direction="row" spacing={1}>
            <IconButton color="warning" onClick={() => onEditClick(params.row)}>
              <Edit />
            </IconButton>
          </Stack>
        )
      };
    }
    return col;
  });

  return (
    <Box p={3}>
      <Typography variant="h4" gutterBottom>
        {title}
      </Typography>

      <Box mb={2} display="flex" justifyContent="space-between" alignItems="center">
        <TextField
          label="Search"
          variant="outlined"
          value={search}
          onChange={(e) => onSearchChange(e.target.value)}
          sx={{ width: 300 }}
        />
      </Box>

      <Box height={500}>
        <DataGrid
          rows={rows}
          columns={enhancedColumns}
          paginationModel={paginationModel}
          onPaginationModelChange={onPaginationModelChange}
          pageSizeOptions={[5, 10]}
          disableRowSelectionOnClick
          slots={{ toolbar: GridToolbar }}
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

export default DataGridTable;
