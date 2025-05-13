import { useEffect, useState } from 'react';
import { GridPaginationModel } from '@mui/x-data-grid';
import { LABELS_ORDER } from '../../../../config/constant';
import ApiService from '../../../services/api.services';
import DataGridTable from '../../../components/common/Common_GridTable';
import OrderDialog from './OrderDialog';
import { columns } from './settings/settings-columns';
import { ToastService } from '../../../services/toast.service';
import 'react-toastify/dist/ReactToastify.css';
import { useLoading } from '../../../services/loading.services';

export default function OrderManagement(): JSX.Element {
  const apiService = new ApiService();
  const urlAPI = 'https://localhost:7031/api/Orders';

  const [rows, setRows] = useState<any[]>([]);
  const [search, setSearch] = useState('');
  const [paginationModel, setPaginationModel] = useState<GridPaginationModel>({ page: 0, pageSize: 5 });
  const [openDialog, setOpenDialog] = useState(false);
  const [selectedOrder, setSelectedData] = useState<any>(null);
  const { showLoading, hideLoading } = useLoading();
  const filteredRows = filterRowsByOrderId(rows, search);

  useEffect(() => {
    handleSearch();
  }, []);

  function handleSearch() {
    // const searchParams = formGroupSearch.value;
    showLoading();
    // Call API
    apiService
      .apiGet(urlAPI, {
        // params: searchParams
      })
      .then((response) => {
        setRows(response);
        ToastService.success('Đã search xong!');
      })
      .catch((error) => {
        ToastService.error(error);
      })
      .finally(() => {
        hideLoading();
      });
  }

  function handleEditClick(order: any) {
    setSelectedData(order);
    setOpenDialog(true);
  }

  function handleSave(updatedOrder: any) {
    setRows((prev) => prev.map((o) => (o.id === updatedOrder.id ? updatedOrder : o)));
  }

  function filterRowsByOrderId(rows: any[], search: string) {
    return rows.filter((row) => row.order_id?.toLowerCase().includes(search.toLowerCase()));
  }

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
      <OrderDialog open={openDialog} onClose={() => setOpenDialog(false)} data={selectedOrder} onSave={handleSave} />
    </DataGridTable>
  );
}
