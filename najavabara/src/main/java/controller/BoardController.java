package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.SimpleAttributeSet;

import com.oreilly.servlet.MultipartRequest;

import dao.BoardDAO;
import dto.BoardDTO;
import dto.PageDTO;


@WebServlet("*.free")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/// join.jsp? login.jsp? joinProc?
		System.out.println("doProcess");
		request.setCharacterEncoding("utf-8"); // 占쎈립疫뀐옙筌ｌ꼶�봺

		String uri = request.getRequestURI();
		String action = uri.substring(uri.lastIndexOf("/"));
		System.out.println(uri);
		if(action.equals("/list.free")) {
			// move. get, 2. forward - reqeust.setAttribute("v","o")			
			System.out.println(action);

			String searchField = request.getParameter("searchField");
			String searchWord = request.getParameter("searchWord");

			Map<String, String> map = new HashMap<>();
			map.put("searchField", searchField);
			map.put("searchWord", searchWord);

			BoardDAO dao = new BoardDAO();
			List<BoardDTO> boardLists = dao.selectList(map);
			int totalCount = dao.selectCount(map);

			request.setAttribute("boardLists", boardLists);
			request.setAttribute("totalCount", totalCount);

			// forward
			String path =  "./list.jsp"; // 1
			//			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			//			dispatcher.forward(request, response);
			request.getRequestDispatcher(path).forward(request, response);
		}else if(action.equals("/listFile.free")) {
			// move. get, 2. forward - reqeust.setAttribute("v","o")			
			System.out.println(action);

			String searchField = request.getParameter("searchField");
			String searchWord = request.getParameter("searchWord");

			Map<String, String> map = new HashMap<>();
			map.put("searchField", searchField);
			map.put("searchWord", searchWord);

			
			BoardDAO dao = new BoardDAO();
			
			List<BoardDTO> boardLists = dao.selectPageList(map);
			int totalCount = dao.selectCount(map);

			
			request.setAttribute("boardLists", boardLists);
			request.setAttribute("totalCount", totalCount);

			
			// forward
			String path =  "./listFile.jsp"; // 1
			request.getRequestDispatcher(path).forward(request, response);

		}else if(action.equals("/listPage.free")) {
			// move. get, 2. forward - reqeust.setAttribute("v","o")			
			System.out.println(action);

			String searchField = request.getParameter("searchField");
			String searchWord = request.getParameter("searchWord");

			Map<String, String> map = new HashMap<>();
			map.put("searchField", searchField);
			map.put("searchWord", searchWord);

			// paging info
			int amount = 10;
			int pageNum = 1;
			
			
			String sPageNum = request.getParameter("pageNum");
			if(sPageNum != null) pageNum = Integer.parseInt(sPageNum);
			int offset = (pageNum-1) * amount;

			map.put("offset", offset+"");
			map.put("amount", amount+"");
			
			BoardDAO dao = new BoardDAO();
			
			List<BoardDTO> boardLists = dao.selectPageList(map);
			int totalCount = dao.selectCount(map);

			// Paging
			PageDTO paging = new PageDTO(pageNum, amount, totalCount);
			
			request.setAttribute("boardLists", boardLists);
			request.setAttribute("paging", paging);

			
			// forward
			String path =  "./listPage.jsp"; // 1
			request.getRequestDispatcher(path).forward(request, response);

		}else if(action.equals("/view.free")) {
			// move. get, 2. forward - reqeust.setAttribute("v","o")			
			System.out.println(action);

			request.setCharacterEncoding("utf-8");
			String sNum = request.getParameter("num"); 
			int num = Integer.parseInt(sNum);
			BoardDTO dto = new BoardDTO();
			dto.setNum(num);

			BoardDAO dao = new BoardDAO();

			//1. visitcount update
			dao.updateVisitcount(dto);

			//2. view content
			dto = dao.selectView(dto);

			request.setAttribute("dto", dto);

			//3. forward
			String path =  "./view.jsp"; // 1
			request.getRequestDispatcher(path).forward(request, response);
		}else if(action.equals("/update.free")) {
			// move. get, 2. forward - reqeust.setAttribute("v","o")			
			System.out.println(action);

			request.setCharacterEncoding("utf-8");
			String sNum = request.getParameter("num"); 
			int num = Integer.parseInt(sNum);
			BoardDTO dto = new BoardDTO();
			dto.setNum(num);

			BoardDAO dao = new BoardDAO();

			//2. view content
			dto = dao.selectView(dto);

			request.setAttribute("dto", dto);

			//3. forward
			String path =  "./update.jsp"; // 1
			request.getRequestDispatcher(path).forward(request, response);
		}else if(action.equals("/updateProc.free")) {
			// 1. 揶쏉옙 獄쏆룄由�
			request.setCharacterEncoding("utf-8");
			String sNum = request.getParameter("num");
			int num = Integer.parseInt(sNum);
			String title = request.getParameter("title");
			String content = request.getParameter("content");		

			// 2. 揶쏉옙 �빊�뮆�젾
			System.out.println(num);
			System.out.println(title);
			System.out.println(content);

			// 3. DTO
			BoardDTO dto = new BoardDTO();		
			dto.setNum(num);
			dto.setTitle(title);
			dto.setContent(content);

			// 4. DAO 
			BoardDAO dao = new BoardDAO();
			dao.updateWrite(dto);

			// 5. move : get
			String path = request.getContextPath() + "/free/view.free?num="+num;
			response.sendRedirect(path);
		}else if(action.equals("/deleteProc.free")) {
			// 1. 揶쏉옙 獄쏆룄由�
			request.setCharacterEncoding("utf-8");
			String sNum = request.getParameter("num");
			int num = Integer.parseInt(sNum);

			// 2. 揶쏉옙 �빊�뮆�젾
			//System.out.println(num);

			// 3. DTO
			BoardDTO dto = new BoardDTO();		
			dto.setNum(num);

			// 4. DAO 
			BoardDAO dao = new BoardDAO();
			dao.deleteWrite(dto);

			// 5. move : get
			String path = request.getContextPath() + "/free/list.free";
			response.sendRedirect(path);
		}else if(action.equals("/write.free")) {
			// move : get
			String path = request.getContextPath() + "/free/write.jsp";
			response.sendRedirect(path);
		}else if(action.equals("/writeFile.free")) {
			// move : get
			String path = request.getContextPath() + "/free/writeFile.jsp";
			response.sendRedirect(path);
		}else if(action.equals("/writeProc.free")) {
			// 1. 揶쏉옙 獄쏆룄由�
			request.setCharacterEncoding("utf-8");
			String title = request.getParameter("title");
			String content = request.getParameter("content");

			HttpSession session = request.getSession();		
			String id = (String)session.getAttribute("id");
			System.out.println(id);
			

			// 2. 揶쏉옙 �빊�뮆�젾
			System.out.println(title);
			System.out.println(content);

			// 3. DTO
			BoardDTO dto = new BoardDTO(title, content, id);

			// 4. DAO 
			BoardDAO dao = new BoardDAO();
			dao.insertWrite(dto);

			// 5. move
			String path = request.getContextPath() + "/free/list.free";
			response.sendRedirect(path);
		}else if(action.equals("/listJstl.free")) {
			// move. get, 2. forward - reqeust.setAttribute("v","o")			
			System.out.println(action);

			String searchField = request.getParameter("searchField");
			String searchWord = request.getParameter("searchWord");

			Map<String, String> map = new HashMap<>();
			map.put("searchField", searchField);
			map.put("searchWord", searchWord);

			BoardDAO dao = new BoardDAO();
			List<BoardDTO> boardLists = dao.selectList(map);
			int totalCount = dao.selectCount(map);

			request.setAttribute("boardLists", boardLists);
			request.setAttribute("totalCount", totalCount);

			// forward
			String path =  "./listJstl.jsp"; // 1
			request.getRequestDispatcher(path).forward(request, response);
		}else if(action.equals("/writeFileProc.free")) {
			// 1. 揶쏉옙 獄쏆룄由�
			request.setCharacterEncoding("utf-8");
			// eclips 占쎌뒠 野껋럥以�
			//			ServletContext application = request.getServletContext();
			//			String saveDirectory = application.getRealPath("/Uploads");
			String saveDirectory = "C:/kdigital/jsp/jspws/mvcpjt/src/main/webapp/Uploads";
			System.out.println(saveDirectory);
			String encoding = "UTF-8";
			int maxPostSize = 1024 * 1000 * 10; // 1000kb -> 1M > 10M

			MultipartRequest mr = new MultipartRequest(request, saveDirectory, maxPostSize, encoding);

			String fileName = mr.getFilesystemName("attachedFile");
			String ext = fileName.substring(fileName.lastIndexOf("."));
			String now = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
			String newFileName = now + ext;
			System.out.println(fileName);
			System.out.println(newFileName);
			//			
			File oldFile = new File(saveDirectory + File.separator + fileName); 
			File newFile = new File(saveDirectory + File.separator + newFileName); 
			oldFile.renameTo(newFile);

			String title = mr.getParameter("title");
			String content = mr.getParameter("content");

			HttpSession session = request.getSession();		
			String id = (String)session.getAttribute("id");


			BoardDTO dto = new BoardDTO(title, content, id);
			dto.setOfile(fileName);
			dto.setSfile(newFileName);
			//			
			//			// 4. DAO 
			BoardDAO dao = new BoardDAO();
			dao.insertFileWrite(dto);
			//			
			//			// 5. move
			String path = request.getContextPath() + "/free/listFile.free";
			response.sendRedirect(path);

		}else if(action.equals("/update.bo")) {
			request.setCharacterEncoding("utf-8");
			String sNum = request.getParameter("num"); 
			int num = Integer.parseInt(sNum);
			BoardDTO dto = new BoardDTO();
			dto.setNum(num);

			BoardDAO dao = new BoardDAO();

			// view content
			dto = dao.selectView(dto);
			System.out.println(dto);

			request.setAttribute("dto", dto);
			
			String path = ".update.jsp";
			request.getRequestDispatcher(path).forward(request, response);
		}
	}
}












