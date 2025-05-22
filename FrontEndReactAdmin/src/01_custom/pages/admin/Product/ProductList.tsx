import { useEffect, useState } from 'react';
import AddIcon from '@mui/icons-material/Add';
import ProductDialog from './ProductDialog';
import { useConfirm } from '../../../services/confirm.services';
import { LBL_PRODUCT } from '../../../../config/constant';
import { columns } from './settings/settings-table';
import Common_GridTable from '../../../components/common/Common_GridTable';
import ApiService from '../../../services/api.services';
import { ToastService } from '../../../services/toast.service';
import { useLoading } from '../../../services/loading.services';
import 'react-toastify/dist/ReactToastify.css';

interface ConfirmContextType {
  confirm: (options: { title: string; content: string; onConfirm: () => void }) => void;
}

export default function ProductManagement() {
  const apiService = new ApiService();
  const urlAPI = 'https://localhost:7031/api/Products';
  const { showLoading, hideLoading } = useLoading();

  const [rows, setRows] = useState([]);
  const [search, setSearch] = useState('');
  const [paginationModel, setPaginationModel] = useState({ page: 0, pageSize: 5 });
  const [openDialog, setOpenDialog] = useState(false);
  const [selectedProduct, setSelectedProduct] = useState(null);
  const { confirm } = useConfirm() as ConfirmContextType;

  const filteredRows = filterRowsByName(rows, search);

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
        console.log(error);
      })
      .finally(() => {
        hideLoading();
      });
  }

  function handleEditClick(product) {
    setSelectedProduct(product);
    setOpenDialog(true);
  }

  function handleSave(updatedProduct) {
    handleSearch();
  }

  function handleDeleteClick(product) {
    confirm({
      title: LBL_PRODUCT.CONFIRM_DELETE,
      content: `${LBL_PRODUCT.CONFIRM_DELETE_MESSAGE}${product.nm_product}"?`,
      onConfirm: () => {
        apiService
          .apiDelete(urlAPI, { cd_product: product.cd_product })
          .then(() => {
            ToastService.success('Xóa sản phẩm thành công!');
            handleSearch();
          })
          .catch((error) => {
            ToastService.error(error);
          });
      }
    });
  }

  function filterRowsByName(rows, search) {
    return rows.filter((row) => row.nm_product?.toLowerCase().includes(search.toLowerCase()));
  }

  return (
    <Common_GridTable
      title={LBL_PRODUCT.TITLE}
      rows={filteredRows}
      columns={columns}
      search={search}
      onSearchChange={setSearch}
      paginationModel={paginationModel}
      onPaginationModelChange={setPaginationModel}
      onEditClick={handleEditClick}
      onDeleteClick={handleDeleteClick}
      addButton={{
        label: LBL_PRODUCT.ADD_PRODUCT,
        onClick: () => {
          setSelectedProduct(null);
          setOpenDialog(true);
        },
        icon: <AddIcon />
      }}
    >
      <ProductDialog open={openDialog} onClose={() => setOpenDialog(false)} data={selectedProduct} onSave={handleSave} />
    </Common_GridTable>
  );
}
