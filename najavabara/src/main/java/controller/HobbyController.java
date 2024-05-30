package controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import dao.HobbyBoardDAO;
import dao.HobbyCommentDAO;
import dto.HobbyBoardDTO;
import dto.HobbyCommentDTO;


@WebServlet("*.hob")
public class HobbyController extends HttpServlet {
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
		
		if(action.equals("/listFile.hob")) {
			System.out.println(action);

			String searchField = request.getParameter("searchField");
			String searchWord = request.getParameter("searchWord");

			Map<String, String> map = new HashMap<>();
			map.put("searchField", searchField);
			map.put("searchWord", searchWord);

			
			HobbyBoardDAO dao = new HobbyBoardDAO();
			
			List<HobbyBoardDTO> boardLists = dao.selectFileList(map);
			int totalCount = dao.selectCount(map);

			
			request.setAttribute("boardLists", boardLists);
			request.setAttribute("totalCount", totalCount);

			// forward
			String path =  "./listFile.jsp"; // 1
			request.getRequestDispatcher(path).forward(request, response);

		}else if(action.equals("/myFile.hob")) {
		    System.out.println(action);

		    String searchField = request.getParameter("searchField");
		    String searchWord = request.getParameter("searchWord");

		    Map<String, String> map = new HashMap<>();
		    map.put("searchField", searchField);
		    map.put("searchWord", searchWord);

		    // HttpSession 객체 가져오기
		    HttpSession session = request.getSession();

		    HobbyBoardDAO dao = new HobbyBoardDAO();
		    List<HobbyBoardDTO> boardLists = dao.selectmyList(map, session);
		    int totalCount = dao.selectMineCount(map,session);

		    request.setAttribute("boardLists", boardLists);
		    request.setAttribute("totalCount", totalCount);
		        
		    String path = "/hobbylist/mine.jsp";
		    request.getRequestDispatcher(path).forward(request, response);
		}else if(action.equals("/view.hob")) {
		    System.out.println(action);

		    request.setCharacterEncoding("utf-8");
		    String numStr = request.getParameter("num");

		    if (numStr == null) {
		        System.out.println("num 파라미터가 null입니다.");
		        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 요청입니다: num 파라미터가 없습니다.");
		        return;
		    }

		    int num = Integer.parseInt(numStr);
		    System.out.println("num 파라미터: " + num);

		    HobbyBoardDAO dao = new HobbyBoardDAO();
		    HobbyCommentDAO cao = new HobbyCommentDAO();

		    // 1. visitcount 증가
		    dao.updateVisitcount(num); // 5초
		    HobbyBoardDTO dto = dao.selectView(new HobbyBoardDTO(num));

		    List<HobbyCommentDTO> commentLists = cao.commentLists(num);

		    if (dto != null && commentLists != null) {
		        // 2. 세션에 저장
		        HttpSession session = request.getSession();
		        session.setAttribute("dto", dto);
		        session.setAttribute("commentLists", commentLists);

		    } else {
		        System.out.println("dto 또는 commentLists 객체가 null입니다.");
		        response.sendError(HttpServletResponse.SC_NOT_FOUND, "게시물 또는 댓글을 찾을 수 없습니다.");
		    }
		    // 3. forward
		    String path = "./view.jsp";
		    request.getRequestDispatcher(path).forward(request, response);
		}else if (action.equals("/updateFileProc.hob")) {
		    System.out.println("업데이트 리뷰 액션 접속 완료");

		    request.setCharacterEncoding("utf-8");

		    String saveDir = "C:/Users/TJ/git/NAJAVABARA/najavabara/src/main/webapp/Uploads/images";
		    System.out.println(saveDir);

		    String encode = "utf-8";
		    int maxSize = 1024 * 1000 * 10;

		    MultipartRequest mr = new MultipartRequest(request, saveDir, maxSize, encode);

		    String snum = mr.getParameter("update_num");
		    int num = Integer.parseInt(snum);
		    String title = mr.getParameter("update_title");
		    String content = mr.getParameter("update_content");

		    HobbyBoardDTO dto = new HobbyBoardDTO(num, title, content);

		    String existingOrifile = mr.getParameter("existing_orifile");
		    String existingNewfile = mr.getParameter("existing_newfile");

		    // 파일 존재 여부 확인 및 파일명 변경
		    if (mr.getFilesystemName("update_file") != null) {
		        String filename = mr.getFilesystemName("update_file");
		        String ext = filename.substring(filename.lastIndexOf("."));
		        String time = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
		        String fTimeName = time + ext;

		        File orifiFile = new File(saveDir + File.separator + filename);
		        File newFile = new File(saveDir + File.separator + fTimeName);
		        orifiFile.renameTo(newFile);

		        dto.setOrifile(filename);
		        dto.setNewfile(fTimeName);
		    } else {
		        // 새 파일이 업로드되지 않은 경우 기존 파일 정보 사용
		        dto.setOrifile(existingOrifile);
		        dto.setNewfile(existingNewfile);
		    }

		    HobbyBoardDAO dao = new HobbyBoardDAO();
		    dao.updateFileWrite(dto);

		    String path = request.getContextPath() + "/hobbyboard/listFile.hob";
		    response.sendRedirect(path);
		}else if(action.equals("/deleteProc.hob")) {
			// 1. 값 받기
			request.setCharacterEncoding("utf-8");
			String sNum = request.getParameter("num");
			int num = Integer.parseInt(sNum);

			// 2. 값 출력
			//System.out.println(num);

			// 3. DTO
			HobbyBoardDTO dto = new HobbyBoardDTO();		
			dto.setNum(num);

			// 4. DAO 
			HobbyBoardDAO dao = new HobbyBoardDAO();
			dao.deleteWrite(dto);

			// 5. move : get
			String path = request.getContextPath() + "/hobbyboard/listFile.hob";
			response.sendRedirect(path);
			
		}else if(action.equals("/deleteCommentsProc.hob")) {
		    // 1. 값 받기
		    request.setCharacterEncoding("utf-8");
		    String xNum = request.getParameter("numx");

		    if (xNum == null) {
		        System.out.println("numx 파라미터가 null입니다.");
		        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 요청입니다: numx 파라미터가 없습니다.");
		        return;
		    }

		    int numx = Integer.parseInt(xNum);

		    // 2. 값 출력
		    System.out.println("Received numx: " + numx);

		    // 3. CTO
		    HobbyCommentDTO cto = new HobbyCommentDTO();        
		    cto.setNumx(numx);

		    // 4. CAO 
		    HobbyCommentDAO cao = new HobbyCommentDAO();
		    cao.delete(cto);

		    // 5. move : get
		    String path = request.getContextPath() + "/hobbyboard/view.jsp";
		    response.sendRedirect(path);
		    
		}else if(action.equals("/deleteAllCommentsProc.hob")) {
		    // 1. 값 받기
		    request.setCharacterEncoding("utf-8");
		    String sNum = request.getParameter("num");
		    int num = Integer.parseInt(sNum);

		    // 2. 값 출력
		    System.out.println("Received num: " + num);

		    // 3. CTO
		    HobbyCommentDTO cto = new HobbyCommentDTO();        
		    cto.setNum(num);

		    // 4. CAO 
		    HobbyCommentDAO cao = new HobbyCommentDAO();
		    cao.deleteAll(cto);

		    // 5. move : get
		    String path = request.getContextPath() + "/hobbyboard/listFile.hob";
		    response.sendRedirect(path);
		    
		}else if(action.equals("/writeFile.hob")) {
			// move : get
			String path = request.getContextPath() + "/hobbyboard/writeFile.jsp";
			response.sendRedirect(path);
		}else if(action.equals("/writeFileProc.hob")) {
		    try {
		        // 1. 값 받기
		        request.setCharacterEncoding("utf-8");

		        // 경로 설정
		        String saveDirectory = "C:/Users/TJ/git/NAJAVABARA/najavabara/src/main/webapp/Uploads/images";
		        System.out.println(saveDirectory);
		        String encoding = "UTF-8";
		        // 사이즈 조정
		        int maxPostSize = 1024 * 1000 * 10; // 1000kb -> 1M -> 10M

		        MultipartRequest mr = new MultipartRequest(request, saveDirectory, maxPostSize, encoding);

		        String fileName = mr.getFilesystemName("input_file");
		        if (fileName != null) {
		            String ext = fileName.substring(fileName.lastIndexOf("."));
		            String time = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
		            // 날짜 + 확장자명으로 새 파일 이름 생성
		            String newFileName = time + ext;
		            System.out.println(fileName);
		            System.out.println(newFileName);

		            File oriFile = new File(saveDirectory + File.separator + fileName);
		            File newFile = new File(saveDirectory + File.separator + newFileName);
		            oriFile.renameTo(newFile);

		            // 나머지 파라미터 처리
		            String title = mr.getParameter("input_title");
		            String content = mr.getParameter("input_content");

		            HttpSession session = request.getSession();
		            String id = (String) session.getAttribute("id");
		            String hobby = mr.getParameter("input_hobby");

		            // 값 확인
		            System.out.println("타이틀: " + title);
		            System.out.println("content: " + content);
		            System.out.println("id: " + id);
		            System.out.println("hobby: " + hobby);

		            HobbyBoardDTO dto = new HobbyBoardDTO(title, id, hobby, content);
		            dto.setOrifile(fileName);
		            dto.setNewfile(newFileName);  // 추가 설정

		            // 4. DAO 
		            HobbyBoardDAO dao = new HobbyBoardDAO();
		            dao.insertFileWrite(dto);

		            // 5. move
		            String path = request.getContextPath() + "/hobbyboard/listFile.hob";
		            response.sendRedirect(path);
		        } else {
		            System.out.println("File upload failed");
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}else if (action.equals("/writeCommentProc.hob")) {
		    // 1. 값 받기
		    request.setCharacterEncoding("utf-8");
		    HttpSession session = request.getSession();
		    String id = (String) session.getAttribute("id");
		    String snum = request.getParameter("num");
		    int num = Integer.parseInt(snum);
		    String content = request.getParameter("content");

		    // 2. 값 출력 (디버깅용)
		    System.out.println("Num: " + num);
		    System.out.println("ID: " + id);
		    System.out.println("Content: " + content);

		    // 3. DTO 및 CTO 객체 생성
		    HobbyBoardDTO dto = new HobbyBoardDTO(num);
		    HobbyCommentDTO cto = new HobbyCommentDTO(content);
		    cto.setId(id);

		    // 4. CAO 호출
		    HobbyCommentDAO cao = new HobbyCommentDAO();
		    cao.insertCommentWrite(cto, dto);

		    // 댓글 목록 세션에 저장 (필요시 사용)
		    List<HobbyCommentDTO> commentLists = cao.commentLists(num);
		    session.setAttribute("commentLists", commentLists);

		    // 리다이렉트
		    String path = request.getContextPath() + "/hobbyboard/view.jsp?num=" + num;
		    response.sendRedirect(path);
		
			
////////////////////////////////////////////////////////////////////////////////////////////////////
		}else if(action.equals("/gardening.hob")) {
		    System.out.println(action);

		    String searchField = request.getParameter("searchField");
		    String searchWord = request.getParameter("searchWord");

		    Map<String, String> map = new HashMap<>();
		    map.put("searchField", searchField);
		    map.put("searchWord", searchWord);

		    HobbyBoardDAO dao = new HobbyBoardDAO();
		    List<HobbyBoardDTO> boardLists = dao.selectGardeningList(map);
		    int totalCount = dao.selectGardeningCount(map);

		    request.setAttribute("boardLists", boardLists);
		    request.setAttribute("totalCount", totalCount);

		    String path = "/hobbylist/gardening.jsp";
		    request.getRequestDispatcher(path).forward(request, response);
		    
		}else if(action.equals("/art.hob")) {
			System.out.println(action);

			String searchField = request.getParameter("searchField");
			String searchWord = request.getParameter("searchWord");

			Map<String, String> map = new HashMap<>();
			map.put("searchField", searchField);
			map.put("searchWord", searchWord);

			HobbyBoardDAO dao = new HobbyBoardDAO();
			List<HobbyBoardDTO> boardLists = dao.selectArtList(map);
			int totalCount = dao.selectArtCount(map);

			request.setAttribute("boardLists", boardLists);
			request.setAttribute("totalCount", totalCount);
				
			String path = "/hobbylist/art.jsp";
			request.getRequestDispatcher(path).forward(request, response);
			
		}else if(action.equals("/puzzle.hob")) {
			System.out.println(action);

			String searchField = request.getParameter("searchField");
			String searchWord = request.getParameter("searchWord");

			Map<String, String> map = new HashMap<>();
			map.put("searchField", searchField);
			map.put("searchWord", searchWord);

			HobbyBoardDAO dao = new HobbyBoardDAO();
			List<HobbyBoardDTO> boardLists = dao.selectPuzzleList(map);
			int totalCount = dao.selectPuzzleCount(map);

			request.setAttribute("boardLists", boardLists);
			request.setAttribute("totalCount", totalCount);
				
			String path = "/hobbylist/puzzle.jsp";
			request.getRequestDispatcher(path).forward(request, response);
			
		}else if(action.equals("/collection.hob")) {
			System.out.println(action);

			String searchField = request.getParameter("searchField");
			String searchWord = request.getParameter("searchWord");

			Map<String, String> map = new HashMap<>();
			map.put("searchField", searchField);
			map.put("searchWord", searchWord);

			HobbyBoardDAO dao = new HobbyBoardDAO();
			List<HobbyBoardDTO> boardLists = dao.selectCollectionList(map);
			int totalCount = dao.selectCollectionCount(map);

			request.setAttribute("boardLists", boardLists);
			request.setAttribute("totalCount", totalCount);
				
			String path = "/hobbylist/collection.jsp";
			request.getRequestDispatcher(path).forward(request, response);
			
		}else if(action.equals("/reading.hob")) {
			System.out.println(action);

			String searchField = request.getParameter("searchField");
			String searchWord = request.getParameter("searchWord");

			Map<String, String> map = new HashMap<>();
			map.put("searchField", searchField);
			map.put("searchWord", searchWord);

			HobbyBoardDAO dao = new HobbyBoardDAO();
			List<HobbyBoardDTO> boardLists = dao.selectReadingList(map);
			int totalCount = dao.selectReadingCount(map);

			request.setAttribute("boardLists", boardLists);
			request.setAttribute("totalCount", totalCount);
				
			String path = "/hobbylist/reading.jsp";
			request.getRequestDispatcher(path).forward(request, response);
			
		}else if(action.equals("/exercise.hob")) {
			System.out.println(action);

			String searchField = request.getParameter("searchField");
			String searchWord = request.getParameter("searchWord");

			Map<String, String> map = new HashMap<>();
			map.put("searchField", searchField);
			map.put("searchWord", searchWord);

			HobbyBoardDAO dao = new HobbyBoardDAO();
			List<HobbyBoardDTO> boardLists = dao.selectExerciseList(map);
			int totalCount = dao.selectExerciseCount(map);

			request.setAttribute("boardLists", boardLists);
			request.setAttribute("totalCount", totalCount);
				
			String path = "/hobbylist/exercise.jsp";
			request.getRequestDispatcher(path).forward(request, response);
			
		}else if(action.equals("/photo.hob")) {
			System.out.println(action);

			String searchField = request.getParameter("searchField");
			String searchWord = request.getParameter("searchWord");

			Map<String, String> map = new HashMap<>();
			map.put("searchField", searchField);
			map.put("searchWord", searchWord);

			HobbyBoardDAO dao = new HobbyBoardDAO();
			List<HobbyBoardDTO> boardLists = dao.selectPhotoList(map);
			int totalCount = dao.selectPhotoCount(map);

			request.setAttribute("boardLists", boardLists);
			request.setAttribute("totalCount", totalCount);
				
			String path = "/hobbylist/photo.jsp";
			request.getRequestDispatcher(path).forward(request, response);
			
		}else if(action.equals("/handmade.hob")) {
			System.out.println(action);

			String searchField = request.getParameter("searchField");
			String searchWord = request.getParameter("searchWord");

			Map<String, String> map = new HashMap<>();
			map.put("searchField", searchField);
			map.put("searchWord", searchWord);

			HobbyBoardDAO dao = new HobbyBoardDAO();
			List<HobbyBoardDTO> boardLists = dao.selectHandmadeList(map);
			int totalCount = dao.selectHandmadeCount(map);

			request.setAttribute("boardLists", boardLists);
			request.setAttribute("totalCount", totalCount);
				
			String path = "/hobbylist/handmade.jsp";
			request.getRequestDispatcher(path).forward(request, response);
			
		}else if(action.equals("/instrument.hob")) {
			System.out.println(action);

			String searchField = request.getParameter("searchField");
			String searchWord = request.getParameter("searchWord");

			Map<String, String> map = new HashMap<>();
			map.put("searchField", searchField);
			map.put("searchWord", searchWord);

			HobbyBoardDAO dao = new HobbyBoardDAO();
			List<HobbyBoardDTO> boardLists = dao.selectInstrumentList(map);
			int totalCount = dao.selectInstrumentCount(map);

			request.setAttribute("boardLists", boardLists);
			request.setAttribute("totalCount", totalCount);
				
			String path = "/hobbylist/instrument.jsp";
			request.getRequestDispatcher(path).forward(request, response);
			
		}else if(action.equals("/astronomical.hob")) {
			System.out.println(action);

			String searchField = request.getParameter("searchField");
			String searchWord = request.getParameter("searchWord");

			Map<String, String> map = new HashMap<>();
			map.put("searchField", searchField);
			map.put("searchWord", searchWord);

			HobbyBoardDAO dao = new HobbyBoardDAO();
			List<HobbyBoardDTO> boardLists = dao.selectAstronomicalList(map);
			int totalCount = dao.selectAstronomicalCount(map);

			request.setAttribute("boardLists", boardLists);
			request.setAttribute("totalCount", totalCount);
				
			String path = "/hobbylist/astronomical.jsp";
			request.getRequestDispatcher(path).forward(request, response);
			
		}
	}
}