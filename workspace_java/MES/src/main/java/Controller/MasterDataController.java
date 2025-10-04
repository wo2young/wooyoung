package Controller;

import Service.MasterDataCodeService;
import yaDTO.BomViewDTO;

import Service.MasterDataItemService;
import Service.MasterDataBomService;
import Service.MasterDataRoutingService;

import yaDTO.CodeDetailDTO;
import yaDTO.ItemMasterDTO;
import yaDTO.BomDTO;
import yaDTO.RoutingDTO;
import yaDTO.RoutingViewDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@WebServlet(urlPatterns = { "/master", "/file" })
@MultipartConfig(maxFileSize = 10 * 1024 * 1024, fileSizeThreshold = 1024 * 1024) // 10MB
public class MasterDataController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final MasterDataCodeService codeSvc = new MasterDataCodeService();
	private final MasterDataItemService itemSvc = new MasterDataItemService();
	private final MasterDataBomService bomSvc = new MasterDataBomService();
	private final MasterDataRoutingService routingSvc = new MasterDataRoutingService();

	// 업로드 저장 상대 경로(웹앱 루트 기준)
	private static final String ROUTING_UPLOAD_DIR = "/upload/routing";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		commonPrepare(req, resp);

		// /file 요청은 파일 스트리밍 처리
		if ("/file".equals(req.getServletPath())) {
			handleFileViewOrDownload(req, resp);
			return;
		}
		route(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		commonPrepare(req, resp);
		route(req, resp);
	}

	private void commonPrepare(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
	}

	private void route(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String act = nv(req.getParameter("act"), "index");
		try {
			switch (act) {
//			TODO 코드 페이지

			case "code.detail.list": {
				String codeId = nv(req.getParameter("codeId"), "ALL");
				List<CodeDetailDTO> list;
				if ("ALL".equalsIgnoreCase(codeId)) {
					List<CodeDetailDTO> p = codeSvc.listDetails("PCD");
					List<CodeDetailDTO> f = codeSvc.listDetails("FCD");
					List<CodeDetailDTO> d = codeSvc.listDetails("DEF"); // ← 추가
					p.addAll(f);
					p.addAll(d);
					list = p;
				} else {
					list = codeSvc.listDetails(codeId);
				}
				req.setAttribute("details", list);
				req.setAttribute("activeNav", "codes");
				forward(req, resp, "codes.jsp");
				return;
			}
			case "code.detail.add": {
				String codeId = nv(req.getParameter("codeId"), "PCD");
				String codeDname = nv(req.getParameter("codeDname"), "");
				boolean ok = !codeDname.isBlank() && codeSvc.addDetail(codeId, codeDname);
				redirect(req, resp, "/master?act=code.detail.list&codeId=" + codeId + "&ok=" + (ok ? 1 : 0));
				return;
			}
			case "code.detail.rename": {
				String dc = nv(req.getParameter("detailCode"), "");
				String nm = nv(req.getParameter("codeDname"), "");
				boolean ok = !dc.isBlank() && codeSvc.renameDetail(dc, nm);
				// 활성값이 같이 오면 동시에 반영(Y/N)
				String active = req.getParameter("active");
				if (active != null && !dc.isBlank()) {
					codeSvc.setActive(dc, "Y".equalsIgnoreCase(active));
				}
				redirect(req, resp, "/master?act=code.detail.list&ok=" + (ok ? 1 : 0));
				return;
			}
			case "code.detail.active": {
				String dc = nv(req.getParameter("detailCode"), "");
				String active = nv(req.getParameter("active"), "N");
				boolean ok = !dc.isBlank() && codeSvc.setActive(dc, "Y".equalsIgnoreCase(active));
				redirect(req, resp, "/master?act=code.detail.list&ok=" + (ok ? 1 : 0));
				return;
			}
			case "code.detail.delete": {
				String dc = nv(req.getParameter("detailCode"), "");
				boolean ok = !dc.isBlank() && codeSvc.deleteDetail(dc); // <-- 새로 추가
				redirect(req, resp, "/master?act=code.detail.list&ok=" + (ok ? 1 : 0));
				return;
			}

			// TODO 제품 탭
			case "item.list": {
				String kw = req.getParameter("kw");
				String kind = req.getParameter("kind");
				int page = parseInt(req.getParameter("page"), 1);
				int size = 20;
				int offset = (page - 1) * size;

				List<ItemMasterDTO> items = (kind == null || "ALL".equalsIgnoreCase(kind))
						? itemSvc.list(kw, offset, size)
						: itemSvc.listByKind(kind);

				req.setAttribute("items", items);
				req.setAttribute("pcdDetails", codeSvc.listDetails("PCD")); // RM용 상세코드
				req.setAttribute("fcdDetails", codeSvc.listDetails("FCD")); // FG용 상세코드
				req.setAttribute("selectedKind", kind == null ? "ALL" : kind);
				req.setAttribute("kw", kw);
				req.setAttribute("activeNav", "products");
				forward(req, resp, "products.jsp");
				return;
			}

			case "item.add": {
				ItemMasterDTO d = new ItemMasterDTO();
				d.setItem_id(parseInt(req.getParameter("item_id"), -1));
				d.setItem_name(req.getParameter("item_name"));
				d.setLot_code(req.getParameter("lot_code"));
				d.setSelf_life_day(parseInt(req.getParameter("self_life_day"), 0));
				d.setKind(req.getParameter("kind")); // FG / RM
				d.setDetail_code(req.getParameter("detail_code")); // PCD_xxx / FCD_xxx
				d.setItem_spec(req.getParameter("item_spec"));
				d.setUnit(req.getParameter("unit"));

				boolean ok = itemSvc.create(d);
				redirect(req, resp, "/master?act=item.list&msg=" + (ok ? "add_ok" : "add_fail"));
				return;
			}

			case "item.edit": {
				int itemId = parseInt(req.getParameter("itemId"), -1);
				ItemMasterDTO d = itemSvc.find(itemId);
				req.setAttribute("item", d);
				req.setAttribute("pcdDetails", codeSvc.listDetails("PCD"));
				req.setAttribute("fcdDetails", codeSvc.listDetails("FCD"));
				forward(req, resp, "item_edit.jsp");
				return;
			}
			case "item.update": {
				ItemMasterDTO d = new ItemMasterDTO();
				d.setItem_id(parseInt(req.getParameter("item_id"), -1));
				d.setItem_name(req.getParameter("item_name"));
				d.setLot_code(req.getParameter("lot_code"));
				d.setSelf_life_day(parseInt(req.getParameter("self_life_day"), 0));
				d.setKind(req.getParameter("kind"));
				d.setDetail_code(req.getParameter("detail_code"));
				d.setItem_spec(req.getParameter("item_spec"));
				d.setUnit(req.getParameter("unit"));

				boolean ok = itemSvc.update(d);
				redirect(req, resp,
						"/master?act=item.edit&itemId=" + d.getItem_id() + "&msg=" + (ok ? "upd_ok" : "upd_fail"));
				return;
			}

			case "item.delete": {
				int itemId = parseInt(req.getParameter("itemId"), -1);
				boolean ok = (itemId > 0) && itemSvc.delete(itemId);
				redirect(req, resp, "/master?act=item.list&msg=" + (ok ? "del_ok" : "del_fail"));
				return;
			}

			case "item.view": { // 단건 조회 (수정폼이나 상세보기용)
				int itemId = parseInt(req.getParameter("itemId"), -1);
				ItemMasterDTO d = null;
				if (itemId > 0) {
					d = itemSvc.find(itemId); // Service에서 find() 구현 필요
				}
				req.setAttribute("item", d);
				req.setAttribute("details", codeSvc.listDetails("PCD"));
				forward(req, resp, "item_form.jsp"); // 수정/상세 폼 JSP
				return;
			}

			// TODO bom
			case "bom.list": {
				req.setAttribute("boms", bomSvc.listView());
				// ▼ 셀렉트(수정/추가용) : 전체 아이템
				req.setAttribute("itemsAll", itemSvc.list(null, 0, 1000));
				// ▼ 상단 필터용 : FG만
				req.setAttribute("itemsFG", itemSvc.listByKind("FG"));
				req.setAttribute("activeNav", "bom");
				forward(req, resp, "bom.jsp");
				return;
			}
			case "bom.add": {
				BomDTO d = new BomDTO();
				d.setBom_id(parseInt(req.getParameter("bom_id"), -1));
				d.setParent_item_id(parseInt(req.getParameter("parent_item_id"), -1));
				d.setChild_item_id(parseInt(req.getParameter("child_item_id"), -1));
				d.setQuantity(bd(req.getParameter("quantity")).setScale(6, RoundingMode.HALF_UP).doubleValue());
				boolean ok = bomSvc.insert(d);
				redirect(req, resp, "/master?act=bom.list&ok=" + (ok ? 1 : 0));
				return;
			}
			case "bom.delete": {
				int bomId = parseInt(req.getParameter("bom_id"), -1);
				boolean ok = (bomId > 0) && bomSvc.delete(bomId);
				redirect(req, resp, "/master?act=bom.list&ok=" + (ok ? 1 : 0));
				return;
			}
			case "bom.update": {
				BomDTO d = new BomDTO();
				d.setBom_id(parseInt(req.getParameter("bom_id"), -1));
				d.setParent_item_id(parseInt(req.getParameter("parent_item_id"), -1));
				d.setChild_item_id(parseInt(req.getParameter("child_item_id"), -1));
				try {
					// 숫자 안정 처리
					double q = Double.parseDouble(req.getParameter("quantity"));
					d.setQuantity(q);
				} catch (Exception ex) {
					d.setQuantity(0);
				}
				boolean ok = bomSvc.update(d);
				redirect(req, resp, "/master?act=bom.list&ok=" + (ok ? 1 : 0));
				return;
			}

			// TODO 공정
			// 목록
			case "routing.list": {
				// 전체 아이템 대신 FG만 (완제품)
				req.setAttribute("itemsFG", itemSvc.listByKind("FG"));

				int itemId = parseInt(req.getParameter("itemId"), -1);
				List<RoutingViewDTO> routings;

				if (itemId > 0) {
					routings = routingSvc.listViewByItem(itemId); // 특정 완제품
				} else {
					routings = routingSvc.listAllView(); // 전체
				}

				req.setAttribute("routings", routings);
				req.setAttribute("selectedItemId", itemId);
				req.setAttribute("activeNav", "routing");
				forward(req, resp, "routing.jsp");
				return;
			}

			// 추가폼 열기
			case "routing.addForm": {
				// 전체 아이템 말고 FG만 전달
				req.setAttribute("itemsFG", itemSvc.listByKind("FG"));
				req.setAttribute("activeNav", "routing");
				forward(req, resp, "routing_add.jsp");
				return;
			}

			// 실제 추가
			case "routing.add": {
				RoutingDTO d = new RoutingDTO();
				d.setItem_id(parseInt(req.getParameter("item_id"), -1));
				d.setProcess_step(parseInt(req.getParameter("process_step"), 1));
				String imgPath = saveUploadedFileIfExists(req, "img_file");
				d.setImg_path(imgPath);
				d.setWorkstation(req.getParameter("workstation"));

				boolean ok = routingSvc.insert(d);
				redirect(req, resp, "/master?act=routing.list&msg=" + (ok ? "add_ok" : "add_fail"));
				return;
			}

			// 수정폼 열기
			case "routing.edit": {
				int routingId = parseInt(req.getParameter("routing_id"), -1);
				RoutingDTO d = routingSvc.find(routingId);
				req.setAttribute("routing", d);

				req.setAttribute("itemsFG", itemSvc.listByKind("FG"));

				forward(req, resp, "routing_edit.jsp");
				return;
			}

			// 실제 수정
			case "routing.update": {
				RoutingDTO d = new RoutingDTO();
				d.setRouting_id(parseInt(req.getParameter("routing_id"), -1));
				d.setItem_id(parseInt(req.getParameter("item_id"), -1));
				d.setProcess_step(parseInt(req.getParameter("process_step"), 1));

				String imgPath = saveUploadedFileIfExists(req, "img_file");
				if (imgPath == null || imgPath.isBlank()) {
					imgPath = req.getParameter("img_path");
				}
				d.setImg_path(imgPath);
				d.setWorkstation(req.getParameter("workstation"));

				boolean ok = routingSvc.update(d);
				redirect(req, resp, "/master?act=routing.edit&routing_id=" + d.getRouting_id() + "&msg="
						+ (ok ? "upd_ok" : "upd_fail"));
				return;
			}

			case "routing.delete": {
				int routingId = parseInt(req.getParameter("routing_id"), -1);
				boolean ok = (routingId > 0) && routingSvc.delete(routingId);
				redirect(req, resp, "/master?act=routing.list&msg=" + (ok ? "del_ok" : "del_fail"));
				return;
			}
			case "routing.list.ajax": {
				int orderId = parseInt(req.getParameter("orderId"), -1);

				List<RoutingViewDTO> routings = routingSvc.getByOrder(orderId);
				List<BomViewDTO> boms = bomSvc.getByOrder(orderId);

				// JSON 수동 생성
				StringBuilder sb = new StringBuilder();
				sb.append("{\"routings\":[");

				for (int i = 0; i < routings.size(); i++) {
					RoutingViewDTO r = routings.get(i);
					sb.append("{").append("\"routingId\":").append(r.getRoutingId()).append(",").append("\"itemId\":")
							.append(r.getItemId()).append(",").append("\"itemName\":\"").append(escape(r.getItemName()))
							.append("\",").append("\"processStep\":").append(r.getProcessStep()).append(",")
							.append("\"imgPath\":\"").append(escape(r.getImgPath())).append("\",")
							.append("\"workstation\":\"").append(escape(r.getWorkstation())).append("\"").append("}");
					if (i < routings.size() - 1)
						sb.append(",");
				}

				sb.append("],\"boms\":[");

				for (int i = 0; i < boms.size(); i++) {
					BomViewDTO b = boms.get(i);
					sb.append("{").append("\"bomId\":").append(b.getBomId()).append(",").append("\"parentName\":\"")
							.append(escape(b.getParentName())).append("\",").append("\"parentLot\":\"")
							.append(escape(b.getParentLot())).append("\",").append("\"childName\":\"")
							.append(escape(b.getChildName())).append("\",").append("\"childLot\":\"")
							.append(escape(b.getChildLot())).append("\",").append("\"quantity\":")
							.append(b.getQuantity()).append(",").append("\"childUnit\":\"")
							.append(escape(b.getChildUnit())).append("\"").append("}");
					if (i < boms.size() - 1)
						sb.append(",");
				}

				sb.append("]}");

				resp.setContentType("application/json; charset=UTF-8");
				try (PrintWriter out = resp.getWriter()) {
					out.print(sb.toString());
				}
				return;
			}

			// TODO 에러 발생시
			default: {
				redirect(req, resp, "/master?act=code.detail.list");
				return;
			}
			}
		} catch (Exception e) {
			req.setAttribute("error", translateOracle(e));
			// 에러 발생 시, 액션에 맞게 같은 페이지로 보냄
			String page = "codes.jsp";
			if (act.startsWith("item."))
				page = "products.jsp";
			else if (act.startsWith("bom."))
				page = "bom.jsp";
			else if (act.startsWith("routing."))
				page = "routing.jsp";
			req.setAttribute("activeNav", page.equals("products.jsp") ? "products"
					: page.equals("bom.jsp") ? "bom" : page.equals("routing.jsp") ? "routing" : "codes");
			forward(req, resp, page);
		}
	}

	// TODO 파일 다운로드 / 업로드
	private void handleFileViewOrDownload(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String rel = nv(req.getParameter("path"), ""); // 예) /upload/routing/uuid.png
		if (rel.isBlank()) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "path 파라미터가 필요합니다.");
			return;
		}
		// 웹앱 루트 기준 실제 경로
		String realRoot = getServletContext().getRealPath("/");
		Path target = Paths.get(realRoot).normalize().resolve(rel.startsWith("/") ? rel.substring(1) : rel).normalize();

		// 보안: 반드시 /upload/routing 하위만 허용
		Path allowedRoot = Paths.get(realRoot).normalize().resolve(ROUTING_UPLOAD_DIR.substring(1)).normalize();
		if (!target.startsWith(allowedRoot) || !Files.exists(target) || Files.isDirectory(target)) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		String mime = Files.probeContentType(target);
		if (mime == null)
			mime = "application/octet-stream";
		resp.setContentType(mime);

		// a[download] 속성으로 내려오든, 그냥 보든 둘 다 지원
		// (브라우저가 download를 붙여 보낼 때는 헤더가 달라질 수 있지만 여기선 항상 inline)
		// 다운로드 강제하려면 아래 줄 주석 해제
		// resp.setHeader("Content-Disposition", "attachment; filename=\"" +
		// target.getFileName().toString() + "\"");

		try (OutputStream os = resp.getOutputStream()) {
			Files.copy(target, os);
			os.flush();
		}
	}

	// TODO 헬퍼
	private void forward(HttpServletRequest req, HttpServletResponse resp, String jsp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/views/" + jsp).forward(req, resp);
	}

	private void redirect(HttpServletRequest req, HttpServletResponse resp, String absPath) throws IOException {
		resp.sendRedirect(req.getContextPath() + absPath);
	}

	private static String nv(String s, String def) {
		return (s == null || s.isBlank()) ? def : s;
	}

	private static int parseInt(String s, int def) {
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
			return def;
		}
	}

	// 안전 BigDecimal 파서(빈값/공백/콤마 방어)
	private static BigDecimal bd(String s) {
		if (s == null)
			return BigDecimal.ZERO;
		s = s.trim().replace(",", "");
		if (s.isEmpty())
			return BigDecimal.ZERO;
		try {
			return new BigDecimal(s);
		} catch (Exception e) {
			return BigDecimal.ZERO;
		}
	}

	// TODO 파일 업로드
	private String saveUploadedFileIfExists(HttpServletRequest req, String partName) {
		try {
			Part part = req.getPart(partName);
			if (part == null || part.getSize() <= 0)
				return null;

			String realRoot = getServletContext().getRealPath("/");
			Path dir = Paths.get(realRoot).resolve(ROUTING_UPLOAD_DIR.substring(1));
			Files.createDirectories(dir);

			String submitted = getSubmittedFileName(part);
			String ext = "";
			if (submitted != null) {
				int p = submitted.lastIndexOf('.');
				if (p >= 0)
					ext = submitted.substring(p);
			}
			String fileName = UUID.randomUUID().toString().replace("-", "") + ext;
			Path target = dir.resolve(fileName);
			try (InputStream is = part.getInputStream()) {
				Files.copy(is, target);
			}
			return ROUTING_UPLOAD_DIR + "/" + fileName;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String escape(String s) {
		if (s == null)
			return "";
		return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
	}

	// javax.servlet 3.1 표준 메서드 대체 구현
	private static String getSubmittedFileName(Part part) {
		String cd = part.getHeader("content-disposition");
		if (cd == null)
			return null;
		for (String token : cd.split(";")) {
			token = token.trim();
			if (token.startsWith("filename")) {
				String fn = token.substring(token.indexOf('=') + 1).trim().replace("\"", "");
				return Paths.get(fn).getFileName().toString(); // IE 대응
			}
		}
		return null;
	}

	private String translateOracle(Exception e) {
		String m = String.valueOf(e.getMessage());
		if (m.contains("-20061"))
			return "지시수량 미충족으로 DONE 전환 불가";
		if (m.contains("-20041"))
			return "재고 잔량 부족";
		if (m.contains("-20042"))
			return "OUT 전 IN LOT 필요";
		if (m.contains("-20070"))
			return "자재 부족(예약 실패)";
		if (m.contains("-20062"))
			return "DONE 상태는 되돌릴 수 없습니다";
		if (m.contains("-20080"))
			return "실적이 존재하여 지시 삭제 불가";
		if (m.contains("-20081"))
			return "자재 예약(OUT) 내역 존재로 지시 삭제 불가";
		if (m.contains("-20082"))
			return "DONE 상태 지시 삭제 불가";
		return m;
	}
}
