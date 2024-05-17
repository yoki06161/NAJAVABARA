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
import dto.RegionDTO;


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

			Map<String, String> map = new HashMap<>();
			map.put("searchField", searchField);
			map.put("searchWord", searchWord);

			// service : dao
			RegionDAO dao = new RegionDAO();
			List<RegionDTO> regionList =  dao.selectList(map);
			int totalCount = dao.selectCount(map);

			request.setAttribute("regionList", regionList);
			request.setAttribute("totalCount", totalCount);

			String path =  "./list.jsp"; // 1
			//String path =  "./user/userList.jsp"; // 2
			//			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			//			dispatcher.forward(request, response);
			request.getRequestDispatcher(path).forward(request, response);

		}else if(action.equals("/view.reg")) {
			// move. get, 2. forward - reqeust.setAttribute("v","o")			
			// 값 받아오기
			request.setCharacterEncoding("utf-8");
			String sNum = request.getParameter("num"); 
			int num = Integer.parseInt(sNum);
			RegionDTO dto = new RegionDTO();
			dto.setNum(num);
			//System.out.print(num);  //찍히는 것 확인...

			RegionDAO dao = new RegionDAO();

			//1. 조회수 update
			dao.updateVisitcount(dto); // 5초

			// 파일 경로를 request에 설정
//			HttpSession session = request.getSession();
//			String relativePath = "/Uploads/" + fileName; // 이미지 파일의 상대적인 경로
//			String absolutePath = request.getContextPath() + relativePath; // 웹 애플리케이션의 절대 URL
//			session.setAttribute("filePath", absolutePath);
//			System.out.println(absolutePath);

			RegionDTO fdto = dao.selectViewFile(dto);

			request.setAttribute("fdto", fdto);
			//2. 글 상세보기
			dto = dao.selectView(dto);

//			RegionDTO fdto = dao.selectViewFile(dto);

			request.setAttribute("dto", dto);

			request.setAttribute("fdto", fdto);

			//3. forward
			String path =  "./view.jsp"; // 1
			request.getRequestDispatcher(path).forward(request, response);
		}else if(action.equals("/write.reg")) {
			String path = request.getContextPath() + "/region/write.jsp";
			response.sendRedirect(path);
		}else if(action.equals("/writeProc.reg")) {
			// 값 한글처리
			request.setCharacterEncoding("utf-8");

			// 값 받아오기			
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			HttpSession session = request.getSession();    
			String id = (String)session.getAttribute("id");
			String sPostNum = request.getParameter("postNum");
			int postNum = Integer.parseInt(sPostNum);

			RegionDTO dto = new RegionDTO(title, content, id);
			dto.setTitle(title);
			dto.setContent(content);
			dto.setId(id);
			dto.setpostNum(postNum);

			System.out.println(title);
			System.out.println(content);
			System.out.println(id);

			// 4. DAO 
			RegionDAO dao = new RegionDAO();
			dao.insertWrite(dto);

			// 5. move
			String path = request.getContextPath() + "/region/list.reg";
			response.sendRedirect(path);
		}else if(action.equals("/writeFileProc.reg")) {
			// 값 한글처리
			request.setCharacterEncoding("utf-8");

			// 파일 업로드
			String saveDirectory = request.getServletContext().getRealPath("/Uploads");
			String encoding = "UTF-8";
			int maxPostSize = 1024 * 1000 * 10; // 10MB

			// Apache Commons FileUpload 라이브러리 사용
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setRepository(new File(saveDirectory));
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(maxPostSize);

			String fileName = null;
			String newFileName = null;
			try {
				List<FileItem> items = upload.parseRequest(request);
				for (FileItem item : items) {
					if (!item.isFormField()) {
						fileName = item.getName();
						String ext = fileName.substring(fileName.lastIndexOf("."));
						String now = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
						newFileName = now + ext;
						File uploadedFile = new File(saveDirectory, newFileName);
						item.write(uploadedFile);
					}
				}
			} catch (FileUploadException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println(fileName);
			System.out.println(newFileName);

			String ofile = fileName;
			String sfile = newFileName;

			// 3. DTO
			RegionDTO dto = new RegionDTO(ofile, sfile);
			RegionDAO dao = new RegionDAO();
			dao.insertFile(dto);

			// 파일 경로를 request에 설정
			HttpSession session = request.getSession();
			String relativePath = "/Uploads/" + fileName; // 이미지 파일의 상대적인 경로
			String absolutePath = request.getContextPath() + relativePath; // 웹 애플리케이션의 절대 URL
			session.setAttribute("filePath", absolutePath);
			System.out.println(absolutePath);

			RegionDTO fdto = dao.selectViewFile(dto);

			request.setAttribute("fdto", fdto);

			// JSP로 포워딩
			RequestDispatcher dispatcher = request.getRequestDispatcher("write.jsp");
			dispatcher.forward(request, response);
			//			String path = request.getContextPath() + "/region/write.reg";
			//			response.sendRedirect(path);

		}else if(action.equals("/update.reg")) {
			// 값 받아오기
			request.setCharacterEncoding("utf-8");
			String sNum = request.getParameter("num"); 
			int num = Integer.parseInt(sNum);	

			// 게시물 데이터 불러오기
			RegionDTO dto = new RegionDTO();
			dto.setNum(num);

			RegionDAO dao = new RegionDAO();
			RegionDTO reg = dao.selectView(dto);

			request.setAttribute("reg", reg);

			//3. forward
			String path =  "./update.jsp"; // 1
			request.getRequestDispatcher(path).forward(request, response);
		}else if(action.equals("/updateProc.reg")) {
			// 값 받기
			request.setCharacterEncoding("utf-8");
			String sNum = request.getParameter("num"); 
			int num = Integer.parseInt(sNum);	
			String title = request.getParameter("title");
			String content = request.getParameter("content");

			//DTO
			RegionDTO dto = new RegionDTO();	
			dto.setNum(num);
			dto.setTitle(title);
			dto.setContent(content);

			//DAO 
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
