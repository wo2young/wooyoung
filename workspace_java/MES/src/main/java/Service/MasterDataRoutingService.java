package Service;

import DAO.MasterDataRoutingDAO;
import yaDTO.RoutingDTO;
import yaDTO.RoutingViewDTO;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class MasterDataRoutingService {
	private final MasterDataRoutingDAO dao = new MasterDataRoutingDAO();
	
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
