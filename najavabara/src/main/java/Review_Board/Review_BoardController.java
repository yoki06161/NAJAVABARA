package Review_Board;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import Review_Comment.ReviewCommentDAO;

@WebServlet("*.rev_bo")
// 앞에 뭐가붙든 rev_bo라는 경로?를 갖고 오겠단 뜻
public class Review_BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProc(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProc(req, resp);
	}
	
	private void doProc(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("게시판 컨트롤러 연결");
		
		req.setCharacterEncoding("utf-8");
		
		// 현재 요청의 url가져오기
		String uri = req.getRequestURI();
		// 아까 가져온 url에서 마지막 /가져오기.
		String action = uri.substring(uri.lastIndexOf("/"));
		System.out.println(uri);
		
// ########################### 게시물 작성 액션
		if(action.equals("/WriteReviewAction.rev_bo")) {
			System.out.println("writereveiw액션 포스트 연결완");
			System.out.println("두프로세스연결");
			
			req.setCharacterEncoding("utf-8");
			// MultipartRequest를 쓰면 톰캣의 request(req)를 이용해 값을 전달 받지 못함. 그래서 타이틀과 내용은 널값으로 받아들여짐.
			// 값 받으려면 mr.getparameter로 써야함
//			String title = req.getParameter("input_title"); 안됨
			
			
			 String projectPath = System.getProperty("user.dir");
		        
		        // 경로를 출력합니다.
		        System.out.println("현재 프로젝트 경로: " + projectPath);
			
			// 파일 저장경로 설정
			String saveDir = "C:/Users/TJ/git/NAJAVABARA/najavabara/src/main/webapp/SaveUploads";

			// 이게 상대경로인데 어째선지 이거 쓰면 사진이 자바파일에 저장이 안됨. \라 그런가
//			ServletContext context = getServletContext();
			// context.getRealPath("/SaveUploads")는 웹의 가상경로를 실제 파일 시스템 경로로 변환하는 역.
//			String saveDir = context.getRealPath("/SaveUploads");
			System.out.println(saveDir);
			
			String encode = "utf-8";
			// 사이즈 고정
			int maxSize = 1024 * 1000 * 10;
			
			// MultipartRequest 파일을 서버에 저장하겠단 뜻.
			MultipartRequest mr = new MultipartRequest(req, saveDir, maxSize, encode);
			
			String title = mr.getParameter("input_title");
			String content = mr.getParameter("input_content");
			
			// 세션 연결
			HttpSession session = req.getSession();
			String id = (String)session.getAttribute("id");
			
			// 확인용
			System.out.println("확인용 타이틀"+ title);
			System.out.println(content);
			System.out.println(id);
			
			Review_BoardDTO dto = new Review_BoardDTO(title, content, id);
			
			// 파일 여부
			if(mr.getFilesystemName("input_file") != null) {
				// getFilesystemName파일 이름 받아올때 쓰는 메소드.
				String filename = mr.getFilesystemName("input_file");
				
				// .이후의 문자열을 추출해서 파일 확장자 .jpg, .txt같이 .붙은거 가져오기.
				// substring()은 문자열에서 특정 부분을 잘라 새로운 문자열을 반환하는 메소드.
				// lastIndexOf는 잘라낼 문자열의 끝인덱스. "코카.콜라" 란 문자가 들어오면 ".콜라"만 저장한단뜻
				String ext = filename.substring(filename.lastIndexOf("."));
				
				// 이미지 파일 여부(jpg, png여부)
				if(ext.equals(".jpg") || ext.equals(".png")) {
					// SimpleDateFormat는 날짜와 시간을 문자열로 출력하도록 돕는 도구.
					String time = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
					
					// 새 파일 이름 생성. 날짜+확장자명. 즉, 20240522.jpg같이
					String fTimeName = time + ext;
					
					System.out.println("업로드된 파일 이름은 " + filename);
					System.out.println("부여된 파일 명은 " + fTimeName);
					
					// separator는 디렉토리 경로 구분자. C:/kdigital/의 /이거
					// saveDir경로에 filename(파일)을 저장하겠단 뜻. 원본 파일명과 새로운 파일명을 저장
					File oriFile = new File(saveDir + File.separator + filename);
					File newFile = new File(saveDir + File.separator + fTimeName);
					// orifile이름을 newfile로 바꾸겠단뜻
					oriFile.renameTo(newFile);
					
					dto.setOriFile(filename);
					dto.setNewFile(fTimeName);
					
				} else {
					System.out.println("이미지 파일만 넣어주쇼");
				}
			}

			
			Review_BoardDAO dao = new Review_BoardDAO();
			dao.WriteReview(dto);
			
			String path = req.getContextPath() + "/Review_board/review_list.jsp";
			resp.sendRedirect(path);
		}
// ########################### 게시물 업데이트 액션
		else if(action.equals("/UpdateReviewAction.rev_bo")) {
			System.out.println("업뎃 리뷰액션 접속 완");
			
			req.setCharacterEncoding("utf-8");
			
			String saveDir = "C:/Users/TJ/git/NAJAVABARA/najavabara/src/main/webapp/SaveUploads";
			System.out.println(saveDir);
			
			String encode = "utf-8";
			int maxSize = 1024 * 1000 * 10;
			
			MultipartRequest mr = new MultipartRequest(req, saveDir, maxSize, encode);
			
			String snum = mr.getParameter("update_num");
			int num = Integer.parseInt(snum);
			String title = mr.getParameter("update_title");
			String content = mr.getParameter("update_content");
			
			HttpSession session = req.getSession();
			// String id = (String)session.getAttribute("id");
			// System.out.println("아이디 확인용" + id);
			
			Review_BoardDTO dto = new Review_BoardDTO(title, content, num);
			
			// 문제. 이미 이전에 작성한 게시물의 파일이 안가져옴. 
			// update_file은 파일에서 선택한 파일. 이게 매번 수정할때마다 리셋됨.
			// 5. 그냥 평범한 파일 수정. bf시리즈는 이전 파일 이름 값, up은 새로운 파일 명
			// 1. 이전에 파일을 넣은적이 없으나 수정하면서 파일을 추가함. bfori, bft는 null, update_file은 값있음
			// 2. 이전에도, 이후에도 그냥 파일을 안넣은 상태로 만듦. bf, up모두 널
			// 3. 파일이 있었으나 삭제. bf시리즈는 존재, up은 null
			
			// 4. 이전에 파일을 올린 이력이 있고, 그 파일을 유지한채로 게시판을 새로 만들고 싶음. bf 존재, up null
			
			String bfOrifile = mr.getParameter("bfOrifile");
			String bfTfile = mr.getParameter("bfTfile");
			String delf = mr.getParameter("delf");
			String upfile = mr.getFilesystemName("update_file");
			
			System.out.println("테스트" + bfOrifile);
			System.out.println("테스트" + bfTfile);
			System.out.println("수정된 파일 " + upfile);
			System.out.println("삭제 " + delf);
			
			// 파일 여부. 추가된 파일이 있을시 실행
			if(upfile != null) {
				String filename = mr.getFilesystemName("update_file");
				
				String ext = filename.substring(filename.lastIndexOf("."));
				
				// 새로 올렸을때 이미지를 올린 시간으로 맞추는것.
				String time = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
				// 시간22
				String fTimeName = time + ext;

				// update_file에 올려진 오리지널 파일명 나오는거.
				File oriFile = new File(saveDir + File.separator + filename);
				// 위에서 맞춘 시간파일명
				File newFile = new File(saveDir + File.separator + fTimeName);
				oriFile.renameTo(newFile);
				
				dto.setOriFile(filename);
				dto.setNewFile(fTimeName);
			// 이전에 파일을 올리지 않았고, 또 글만 수정하고 파일을 올리지 않았을시
			}else if(upfile == null && bfOrifile.equals("null")) {
				// bfOrifile을 string형태로 받아버려서 문자열로 인식되는듯 함.
				System.out.println("널임");
			}
			// 이전에 파일을 올렸고, 그 파일을 유지한채로 글쓰기.
			else if(upfile == null && bfOrifile != null && delf.equals("")) {
				
				String filename = mr.getParameter("bfOrifile");
				System.out.println(filename);
				String fTimeName = mr.getParameter("bfTfile");

				// update_file에 올려진 오리지널 파일명 나오는거.
				File oriFile = new File(saveDir + File.separator + filename);
				// 위에서 맞춘 시간파일명
				File newFile = new File(saveDir + File.separator + fTimeName);
				oriFile.renameTo(newFile);
				
				dto.setOriFile(filename);
				dto.setNewFile(fTimeName);
			}
			
			Review_BoardDAO dao = new Review_BoardDAO();
			dao.UpdateReview(dto);
			
			String path = req.getContextPath() + "/Review_board/review_list.jsp";
			resp.sendRedirect(path);
		}
// ########################### 게시물 삭제 액션
		else if(action.equals("/DeleteReviewAction.rev_bo")) {
			System.out.println("리뷰 삭제 연결");
			
			req.setCharacterEncoding("utf-8");
			String snum = req.getParameter("dnum");
			int num = Integer.parseInt(snum);
			
			Review_BoardDAO dao = new Review_BoardDAO();
			Review_BoardDTO dto = new Review_BoardDTO();
			// 게시물 삭제시 댓글도 삭제되게
			ReviewCommentDAO cao = new ReviewCommentDAO();
			
			dto.setNum(num);
			
			dao.DeleteReview(dto);
			cao.DeleteAllComments(dto);
			
			String path = req.getContextPath() + "/Review_board/review_list.jsp";
			resp.sendRedirect(path);
		}
//  ########################### 좋아요 액션. 되긴하는데 나중에 수정 필요
		else if(action.equals("/LikeCount.rev_bo")) {
			System.out.println("되나?");
			// gpt 추가
			resp.setContentType("text/plain");
			
			req.setCharacterEncoding("utf-8");
			
			String snum = req.getParameter("input_conum");
			int num = Integer.parseInt(snum);

			Review_BoardDAO dao = new Review_BoardDAO();
			Review_BoardDTO dto = new Review_BoardDTO();
			dto.setNum(num);
			
			dao.CountLikes(dto);
			
			// gpt 추가
			int updatelike = dto.getLike();
			
			PrintWriter out = resp.getWriter();
			out.print(updatelike);
			out.flush();
			out.close();
		}
//  ########################### 조회수 액션
		else if(action.equals("/Visitors.rev_bo")) {
			System.out.println("조회수온");
			
			req.setCharacterEncoding("utf-8");
			
			String cnum = req.getParameter("cnum");
			int num = Integer.parseInt(cnum);
			System.out.println(cnum);
			
			Review_BoardDAO dao = new Review_BoardDAO();
			Review_BoardDTO dto = new Review_BoardDTO();
			
			dto.setNum(num);
			dao.CountVisitors(dto);
			
			String path = req.getContextPath() + "/Review_board/review_content.jsp?cnum="+num;
			resp.sendRedirect(path);
		}

	}
}
