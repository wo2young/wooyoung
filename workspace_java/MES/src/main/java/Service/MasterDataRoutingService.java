package Service;

import DAO.MasterDataRoutingDAO;
import DAO.ProductionOrderDAO;
import yaDTO.RoutingDTO;
import yaDTO.RoutingViewDTO;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class MasterDataRoutingService {
	private final MasterDataRoutingDAO dao = new MasterDataRoutingDAO();
	private final ProductionOrderDAO orderDao = new ProductionOrderDAO();
	
	public List<RoutingViewDTO> getByOrder(int orderId) {
        try {
            int itemId = orderDao.findItemIdByOrder(orderId).orElse(-1);
            if (itemId == -1) return Collections.emptyList();
            return dao.listViewByItem(itemId);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
	
	public List<RoutingViewDTO> listAllView() {
	    try {
	        return dao.listAllView();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return Collections.emptyList();
	    }
	}

	public List<RoutingViewDTO> listViewByItem(int itemId) {
	    try {
	        return dao.listViewByItem(itemId);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return Collections.emptyList();
	    }
	}
	
	public RoutingDTO find(int routingId) {
	    try {
	        return dao.find(routingId).orElse(null);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	public boolean insert(RoutingDTO d) {
		try {
			return dao.insert(d);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean update(RoutingDTO d) {
		try {
			return dao.update(d);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean delete(int routingId) {
		try {
			return dao.delete(routingId);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}