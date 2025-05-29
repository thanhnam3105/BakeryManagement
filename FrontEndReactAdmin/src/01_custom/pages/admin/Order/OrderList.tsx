import { useEffect, useState } from 'react';
import { GridPaginationModel } from '@mui/x-data-grid';
import { LABELS_ORDER } from '../../../../config/constant';
import ApiService from '../../../services/api.services';
import Common_GridTable from '../../../components/common/Common_GridTable';
import OrderDialog from './OrderDialog';
import { settingTable } from './settings/settings-table';
import { ToastService } from '../../../services/toast.service';
import 'react-toastify/dist/ReactToastify.css';
import { useLoading } from '../../../services/loading.services';
import OrderDetail from './OrderDetail';

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
  const [openDetailDialog, setOpenDetailDialog] = useState(false);
  const [selectedOrderDetail, setSelectedOrderDetail] = useState<any>(null);

  useEffect(() => {
    handleSearch();
  }, []);

  function handleSearch() {
    showLoading();
    apiService
      .apiGet(urlAPI)
      .then((response) => {
        setRows(response);
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
    handleSearch(); // Refresh the list after update
  }

  function filterRowsByOrderId(rows: any[], search: string) {
    return rows.filter((row) => row.cd_order?.toLowerCase().includes(search.toLowerCase()));
  }

  function handleViewClick(order: any) {
    setSelectedOrderDetail(order);
    setOpenDetailDialog(true);
  }

  return (
    <Common_GridTable
      title={LABELS_ORDER.TITLE}
      rows={filteredRows}
      columns={settingTable}
      search={search}
      onSearchChange={setSearch}
      paginationModel={paginationModel}
      onPaginationModelChange={setPaginationModel}
      onEditClick={handleEditClick}
      onViewClick={handleViewClick}
    >
      <OrderDialog 
        open={openDialog} 
        onClose={() => setOpenDialog(false)} 
        data={selectedOrder} 
        onSave={handleSave} 
      />
      <OrderDetail
        open={openDetailDialog}
        onClose={() => setOpenDetailDialog(false)}
        cdOrder={selectedOrderDetail?.cd_order}
      />
    </Common_GridTable>
  );
}
