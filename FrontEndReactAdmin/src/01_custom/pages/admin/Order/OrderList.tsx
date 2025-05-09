import { useEffect, useState } from 'react';
import { GridPaginationModel } from '@mui/x-data-grid';
import { LABELS_ORDER } from '../../../../config/constant';
import ApiService from '../../../services/api.services';
import DataGridTable from '../../../components/common/Table';
import OrderDialog from './OrderDialog';
import { columns } from './settings/columns';

export default function OrderManagement(): JSX.Element {
  const apiService = new ApiService();
  const urlAPI = 'https://localhost:7031/api/Orders';

  const [rows, setRows] = useState<any[]>([]);
  const [search, setSearch] = useState('');
  const [paginationModel, setPaginationModel] = useState<GridPaginationModel>({ page: 0, pageSize: 5 });
  const [openDialog, setOpenDialog] = useState(false);
  const [selectedOrder, setSelectedOrder] = useState<any>(null);

  useEffect(() => {
    handleSearch();
  }, []);

  function handleSearch() {
    // apiService.apiGet(urlAPI).then(setRows).catch(console.error);

    // loadingStore.show();
    // const searchParams = formGroupSearch.value;

    // Call API 
    apiService.apiGet(urlAPI, { 
      // params: searchParams
    }).then((response) => {
      setRows(response);
    }).catch((error) => {
      console.error
      // toastService.showErrorToast(error)
    }).finally(() => {
      // loadingStore.hide();
      // toggleSidebar();
    });
  }

  function handleEditClick(order: any) {
    setSelectedOrder(order);
    setOpenDialog(true);
  }

  function handleSave(updatedOrder: any) {
    setRows((prev) => prev.map((o) => (o.id === updatedOrder.id ? updatedOrder : o)));
  }

  function filterRowsByOrderId(rows: any[], search: string) {
    return rows.filter((row) => row.order_id?.toLowerCase().includes(search.toLowerCase()));
  }

  const filteredRows = filterRowsByOrderId(rows, search);

  return (
    <DataGridTable
      title={LABELS_ORDER.TITLE}
      rows={filteredRows}
      columns={columns}
      search={search}
      onSearchChange={setSearch}
      paginationModel={paginationModel}
      onPaginationModelChange={setPaginationModel}
      onEditClick={handleEditClick}
    >
      <OrderDialog open={openDialog} onClose={() => setOpenDialog(false)} order={selectedOrder} onSave={handleSave} />
    </DataGridTable>
  );
}
