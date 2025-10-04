package Service;

import java.sql.SQLException;
import java.util.List;

import DAO.ProductionOrderDAO;
import yaDTO.ItemMasterDTO;
import yaDTO.ProductionOrderDTO;


 
    public class ProductionOrderService {
        private final ProductionOrderDAO dao = new ProductionOrderDAO();

        public int countTotalOrders(int itemId, String status) {
            return dao.countTotalOrders(itemId, status); // alias 사용
        }

        public List<ProductionOrderDTO> listOrders(int itemId, String status, int page, int pageSize) {
            return dao.listOrders(itemId, status, page, pageSize);
        }

        public List<ItemMasterDTO> getTargetItems() {
            return dao.getTargetItems(); // alias 사용
        }

        public boolean createOrder(ProductionOrderDTO dto) {
            return dao.insertOrder(dto) > 0;
        }

        public boolean updateOrder(ProductionOrderDTO dto) {
            return dao.updateOrder(dto) > 0;
        }

        public boolean deleteOrder(int orderId) throws SQLException {
            return dao.deleteOrder(orderId) > 0;
        }
    }