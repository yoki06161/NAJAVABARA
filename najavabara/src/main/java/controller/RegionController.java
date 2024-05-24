package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;

import dao.RegionDAO;
import dao.RegionLikeDAO;
import dto.RegionDTO;
import dto.RegionLikeDTO;



@WebServlet("*.reg")
public class RegionController extends HttpServlet {
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
		request.setCharacterEncoding("utf-8"); // 한글처리

		String uri = request.getRequestURI();
		String action = uri.substring(uri.lastIndexOf("/"));
		System.out.println(uri);

		if(action.equals("/list.reg")) {
			System.out.println(action);

			String searchField = request.getParameter("searchField");
			String searchWord = request.getParameter("searchWord");
			String area = request.getParameter("area");

			Map<String, String> map = new HashMap<>();
			map.put("searchField", searchField);
			map.put("searchWord", searchWord);
			map.put("area", area);

			// service : dao
			RegionDAO dao = new RegionDAO();
			// 좋아요 수 업데이트
			RegionDTO dto = new RegionDTO();
			int rset =  dao.updateLike(dto);
			
			List<RegionDTO> regionList =  dao.selectList(map);
			
			// 총 게시물 수
			int totalCount = dao.selectCount(map);
			

			request.setAttribute("regionList", regionList);
			request.setAttribute("totalCount", totalCount);
			request.setAttribute("area", area);

			String path =  "./list.jsp"; // 1
			request.getRequestDispatcher(path).forward(request, response);

		}else if(action.equals("/view.reg")) {
			// 값 받아오기
			request.setCharacterEncoding("utf-8");
			HttpSession session = request.getSession(); 
			String id = (String) session.getAttribute("id");
			String sNum = request.getParameter("num"); 
			int num = Integer.parseInt(sNum);
			RegionDTO dto = new RegionDTO();
			RegionLikeDTO ldto = new RegionLikeDTO();
			dto.setNum(num);
			//System.out.print(num);  //찍히는 것 확인...
			// 값 - dto - id 값
			RegionDAO dao = new RegionDAO();
			RegionLikeDAO ldao = new RegionLikeDAO();
			
			//1. 조회수 update
			dao.updateVisitcount(dto); // 5초
			//2. 글 상세보기
			dto = dao.selectView(dto);
			
			request.setAttribute("dto", dto);
			request.setAttribute("ldto", ldto);
			
			//3. forward
			String path =  "./view.jsp"; // 1
			request.getRequestDispatcher(path).forward(request, response);
		}else if(action.equals("/write.reg")) {
			String path = request.getContextPath() + "/region/write.jsp";
			response.sendRedirect(path);
		}else if(action.equals("/writeProc.reg")) {
			// 값 한글처리
			request.setCharacterEncoding("utf-8");

			String saveDirectory = "C:/Users/TJ/git/NAJAVABARA/najavabara/src/main/webapp/Uploads";
			System.out.println(saveDirectory);
			String encoding = "UTF-8";
			int maxPostSize = 1024 * 1000 * 10; // 1000kb -> 1M > 10M

			MultipartRequest mr = new MultipartRequest(request, saveDirectory, maxPostSize, encoding);

			String fileName = mr.getFilesystemName("file");

			if(fileName != null) {	
				String ext = fileName.substring(fileName.lastIndexOf("."));
				String now = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
				String newFileName = now + ext;
				System.out.println(fileName);
				System.out.println(newFileName);

				File oldFile = new File(saveDirectory + File.separator + fileName); 
				File newFile = new File(saveDirectory + File.separator + newFileName); 
				oldFile.renameTo(newFile);

				// 나머지 값 받아오기			
				String title = mr.getParameter("title");
				String content = mr.getParameter("content");
				HttpSession session = request.getSession();    
				String id = (String)session.getAttribute("id");

				RegionDTO dto = new RegionDTO(title, content, id);
				dto.setOfile(fileName);
				dto.setSfile(newFileName);

				// 4. DAO 
				RegionDAO dao = new RegionDAO();
				dao.insertWrite(dto);

				System.out.println(title);
				System.out.println(content);
				System.out.println(id);
			} else if(fileName == null) {
				// 값 받아오기			
				String title = mr.getParameter("title");
				String content = mr.getParameter("content");
				HttpSession session = request.getSession();    
				String id = (String)session.getAttribute("id");

				RegionDTO dto = new RegionDTO(title, content, id);

				// 4. DAO 
				RegionDAO dao = new RegionDAO();
				dao.insertWrite(dto);

				System.out.println(title);
				System.out.println(content);
				System.out.println(id);
			}

			// 5. move
			String path = request.getContextPath() + "/region/list.reg";
			response.sendRedirect(path);
		}else if(action.equals("/update.reg")) {
			// 값 받아오기
			request.setCharacterEncoding("utf-8");
			String sNum = request.getParameter("num"); 
			int num = Integer.parseInt(sNum);	

			// 게시물 데이터 불러오기
			RegionDTO dto = new RegionDTO();
			dto.setNum(num);

			RegionDAO dao = new RegionDAO();
			// 앞에 dto =는 꼭 붙여야함!!
			dto = dao.selectView(dto);

			request.setAttribute("dto", dto);

			//3. forward
			String path =  "./update.jsp"; // 1
			request.getRequestDispatcher(path).forward(request, response);
		}else if(action.equals("/updateProc.reg")) {
			// 값 한글처리
			request.setCharacterEncoding("utf-8");

			String saveDirectory = "C:/Users/TJ/git/NAJAVABARA/najavabara/src/main/webapp/Uploads";
			System.out.println(saveDirectory);
			String encoding = "UTF-8";
			int maxPostSize = 1024 * 1000 * 10; // 1000kb -> 1M > 10M

			MultipartRequest mr = new MultipartRequest(request, saveDirectory, maxPostSize, encoding);

			// 게시물 번호 받아오기
			String sNum = mr.getParameter("num"); 
			//System.out.println(sNum);
			int num = Integer.parseInt(sNum);
			//System.out.println(num);
			RegionDTO dto = new RegionDTO();
			dto.setNum(num);
			
			String fileName = mr.getFilesystemName("file");
			
			if(fileName != null) {	
				String ext = fileName.substring(fileName.lastIndexOf("."));
				String now = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
				String newFileName = now + ext;
				System.out.println(fileName);
				System.out.println(newFileName);

				File oldFile = new File(saveDirectory + File.separator + fileName); 
				File newFile = new File(saveDirectory + File.separator + newFileName); 
				oldFile.renameTo(newFile);

				dto.setOfile(fileName);
				dto.setSfile(newFileName);
				
				// 나머지 값 받아오기			
				String title = mr.getParameter("title");
				String content = mr.getParameter("content");
				HttpSession session = request.getSession();    
				String id = (String)session.getAttribute("id");
				
				dto.setNum(num);
				dto.setTitle(title);
				dto.setContent(content);
				
				System.out.println(num);
				System.out.println(title);
				System.out.println(content);
				System.out.println(id);
			} else if(fileName == null) {
				// 값 받아오기			
				String title = mr.getParameter("title");
				String content = mr.getParameter("content");
				HttpSession session = request.getSession();    
				String id = (String)session.getAttribute("id");

				dto.setNum(num);
				dto.setTitle(title);
				dto.setContent(content);
				
				System.out.println(num);
				System.out.println(title);
				System.out.println(content);
				System.out.println(id);
			}

			// DAO 
			RegionDAO dao = new RegionDAO();
			int rs = dao.updateWrite(dto);
			
			// 5. move
			String path = request.getContextPath() + "/region/view.reg?num="+num;
			response.sendRedirect(path);
		}else if(action.equals("/deleteProc.reg")) {
			// 값 받기
			request.setCharacterEncoding("utf-8");
			String sNum = request.getParameter("num");
			int num = Integer.parseInt(sNum);

			// 2. 값 출력
			//System.out.println(num);

			// 3. DTO
			RegionDTO dto = new RegionDTO();		
			dto.setNum(num);

			// 4. DAO 
			RegionDAO dao = new RegionDAO();
			int rs = dao.deleteWrite(dto);

			// 5. move : get
			String path = request.getContextPath() + "/region/list.reg";
			response.sendRedirect(path);
		}
	}
}
