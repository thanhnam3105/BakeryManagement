import { useEffect, useState } from 'react';
import { GridPaginationModel } from '@mui/x-data-grid';
import { LABELS_ORDER } from '../../../../config/constant';
import ApiService from '../../../services/api.services';
import Common_GridTable from '../../../components/common/Common_GridTable';
import OrderDialog from './OrderDialog';
import { settingTable } from './settings/settings-table';
import { ToastService } from '../../../services/toast.service';
import 'react-toastify/dist/ReactToastify.css';
// import { useLoading } from '../../../services/loading.services';
import OrderDetail from './OrderDetail';
import { Card, Col } from 'react-bootstrap';
import Common_Card from '01_custom/components/common/Common_Card';

export default function OrderManagement(): JSX.Element {
  const apiService = new ApiService();
  const urlAPI = 'https://localhost:7031/api/Orders';

  const [rows, setRows] = useState<any[]>([]);
  // const [search, setSearch] = useState('');
  const [paginationModel, setPaginationModel] = useState<GridPaginationModel>({ page: 0, pageSize: 5 });
  const [openDialog, setOpenDialog] = useState(false);
  const [selectedOrder, setSelectedData] = useState<any>(null);
  // const { showLoading, hideLoading } = useLoading();
  const [loading, setLoading] = useState(false);
  const [openDetailDialog, setOpenDetailDialog] = useState(false);
  const [selectedOrderDetail, setSelectedOrderDetail] = useState<any>(null);

  useEffect(() => {
    handleSearch();
  }, []);

  function handleSearch() {
    setLoading(true);
    apiService.apiGet(urlAPI)
      .then((response) => {
          setRows(response);
      }).catch((error) => {
        ToastService.error(error);
      }).finally(() => {
          setLoading(false);
      });
  }

  function handleEditClick(order: any) {
    setSelectedData(order);
    setOpenDialog(true);
  }

  function handleSave(updatedOrder: any) {
    handleSearch(); // Refresh the list after update
  }

  function handleViewClick(order: any) {
    setSelectedOrderDetail(order);
    setOpenDetailDialog(true);
  }

  return (
    <>
      <div className='row'>
        <Common_Card title="Chờ duyệt" value={10} percentage={10} icon="fa-solid fa-chart-line" color="primary" />
        <Common_Card title="Đang giao hàng" value={2} percentage={10} icon="fa-solid fa-chart-line" color="primary" />
        <Common_Card title="Hoàn thành" value={10} percentage={10} icon="fa-solid fa-chart-line" color="primary" />
        <Common_Card title="Đã hủy" value={1} percentage={10} icon="fa-solid fa-chart-line" color="primary" />
      </div>

      <Common_GridTable
        rows={rows}
        columns={settingTable}
        paginationModel={paginationModel}
        onPaginationModelChange={setPaginationModel}
        onEditClick={handleEditClick}
        onViewClick={handleViewClick}
        tableHeight={500}
        loading={loading}
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
    </>
  );
}
