package mvc.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import mvc.dao.CommentDAO;
import mvc.dao.friendBoardDAO;
import mvc.dao.ReplyDAO;
import mvc.dto.CommentDTO;
import mvc.dto.friendBoardDTO;
import mvc.dto.ReplyDTO;
import mvc.dto.UserDTO;

@WebServlet("*.po")
public class PostController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); // 한글처리

		String uri = request.getRequestURI();
		String action = uri.substring(uri.lastIndexOf("/"));

		if (action.equals("/friendBoard.po")) {
		    // 페이지 번호, 검색어 및 지역 파라미터 가져오기
		    String searchField = request.getParameter("searchField");
		    String searchWord = request.getParameter("searchWord");
		    String searchArea = request.getParameter("searchArea");

		    int pageNum = 1; // 기본 페이지 번호 설정
		    String pageNumStr = request.getParameter("pageNum");
		    if (pageNumStr != null && !pageNumStr.isEmpty()) {
		        try {
		            pageNum = Integer.parseInt(pageNumStr);
		        } catch (NumberFormatException e) {
		            e.printStackTrace(); 
		        }
		    }

		    // 검색 조건과 페이지 정보를 Map에 저장
		    Map<String, String> map = new HashMap<>();
		    map.put("searchField", searchField);
		    map.put("searchWord", searchWord);
		    map.put("searchArea", searchArea); 

		    // 페이지당 글 수와 요청된 페이지에 해당하는 글 목록 가져오기
		    int pageSize = 10; // 페이지당 글 수
		    int offset = (pageNum - 1) * pageSize; // offset 계산
		    map.put("amount", String.valueOf(pageSize));
		    map.put("offset", String.valueOf(offset));

		    // friendBoardDAO를 사용하여 해당 페이지의 글 목록 및 총 글 수 가져오기
		    friendBoardDAO dao = new friendBoardDAO();
		    List<friendBoardDTO> postLists = dao.selectPageList(map); // 요청된 페이지에 해당하는 글 목록 가져오기
		    int totalCount = dao.selectCount(map); // 전체 글 수 가져오기

		    // 각 글에 대한 댓글 수 가져오기
		    CommentDAO commentDAO = new CommentDAO();
		    for (friendBoardDTO post : postLists) {
		        List<CommentDTO> comments = commentDAO.getCommentsByPostNum(post.getNum());
		        int commentCount = comments != null ? comments.size() : 0;
		        post.setCommentCount(commentCount);
		    }

		    // 페이지 관련 정보를 request 속성에 설정
		    request.setAttribute("postLists", postLists);
		    request.setAttribute("totalCount", totalCount);
		    request.setAttribute("pageNum", pageNum);

		    // forward
		    String path = "./friendBoard.jsp";
		    request.getRequestDispatcher(path).forward(request, response);
		} else if (action.equals("/writeForm.po")) {
			// 새 글 작성 폼으로 이동
			response.sendRedirect(request.getContextPath() + "/friendBoard/writeForm.jsp");
		} else if (action.equals("/write.po")) {
		    // 새 글 작성 처리
		    String title = null;
		    String content = null;
		    String fileName = null;
		    
		    HttpSession session = request.getSession();
		    UserDTO user = (UserDTO) session.getAttribute("user");

		    if (user != null) {
		        String id = user.getId();
		        String area = user.getArea();

		        // 파일 업로드 처리를 위한 설정
		        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		        if (isMultipart) {
		            FileItemFactory factory = new DiskFileItemFactory();
		            ServletFileUpload upload = new ServletFileUpload(factory);
		            try {
		                List<FileItem> items = upload.parseRequest(request);
		                for (FileItem item : items) {
		                    if (item.isFormField()) {
		                        // Form fields 처리
		                        String fieldName = item.getFieldName();
		                        if (fieldName.equals("title")) {
		                            title = item.getString("UTF-8");
		                        } else if (fieldName.equals("content")) {
		                            content = item.getString("UTF-8");
		                        }
		                    } else {
		                        // 파일 필드 처리
		                        if (!item.getName().isEmpty()) {
		                            String filePath = getServletContext().getRealPath("/friendBoard/uploads");
		                            System.out.println("filePath: " + filePath); // 디버깅 로그 추가
		                            if (filePath != null) {
		                                File uploadDir = new File(filePath);
		                                // filePath에 디렉토리가 없으면 생성
		                                if (!uploadDir.exists()) {
		                                    boolean dirCreated = uploadDir.mkdirs(); // 디렉토리 생성
		                                    System.out.println("Directory created: " + dirCreated); // 디버깅 로그 추가
		                                }
		                                String originalFileName = new File(item.getName()).getName();
		                                String fileExtension = FilenameUtils.getExtension(originalFileName);
		                                String uniqueFileName = System.currentTimeMillis() + "_" + UUID.randomUUID().toString() + "." + fileExtension;
		                                File uploadFile = new File(filePath + File.separator + uniqueFileName);
		                                try {
		                                    item.write(uploadFile);
		                                    fileName = uniqueFileName;
		                                    System.out.println("File uploaded: " + uploadFile.getAbsolutePath()); // 디버깅 로그 추가
		                                } catch (Exception e) {
		                                    e.printStackTrace();
		                                    System.out.println("Error writing file: " + e.getMessage()); // 디버깅 로그 추가
		                                }
		                            } else {
		                                System.out.println("파일 경로를 찾을 수 없습니다.");
		                            }
		                        }
		                    }
		                }
		            } catch (Exception e) {
		                e.printStackTrace();
		                System.out.println("Error parsing request: " + e.getMessage()); // 디버깅 로그 추가
		            }
		        }

		        if (title != null && content != null) {
		            // 작성된 내용을 데이터베이스에 저장하는 로직 추가
		            friendBoardDTO newPost = new friendBoardDTO(title, content, id, fileName, area); // 새로운 게시물 객체 생성

		            // PostDAO 객체 생성
		            friendBoardDAO postDao = new friendBoardDAO();

		            // 게시물 추가 결과 확인
		            int result = postDao.insertWrite(newPost);

		            if (result == 1) {
		                // 게시물 추가 성공 시 게시판 목록 페이지로 이동
		                response.sendRedirect(request.getContextPath() + "/friendBoard/friendBoard.po");
		            }
		        }
		    } else {
		        // 세션에서 사용자 정보가 없는 경우 로그인 페이지로 이동
		        response.sendRedirect(request.getContextPath() + "/user/loginForm.jsp");
		    }
		} else if (action.equals("/viewPost.po")) {
		    // 게시물 번호를 받아옴
		    String numStr = request.getParameter("num");
		    if (numStr != null && !numStr.isEmpty()) {
		        int num = Integer.parseInt(numStr);

		        HttpSession session = request.getSession();

		        // 현재 날짜와 사용자가 마지막으로 해당 게시글을 읽은 날짜를 비교하여 하루가 지났는지 확인
		        LocalDate currentDate = LocalDate.now();
		        LocalDate lastReadDate = (LocalDate) session.getAttribute("lastReadDate_" + num);

		        // 게시글을 처음 읽거나 하루가 지난 경우에만 조회수를 증가시킴
		        if (lastReadDate == null || !currentDate.equals(lastReadDate)) {
		            // PostDTO 객체 생성 후 해당 게시물 번호를 설정
		            friendBoardDTO postDTO = new friendBoardDTO();
		            postDTO.setNum(num);

		            // PostDAO 객체 생성
		            friendBoardDAO postDAO = new friendBoardDAO();

		            // selectView 메서드 호출 시에는 PostDTO 객체를 전달
		            friendBoardDTO post = postDAO.selectView(postDTO);

		            if (post != null) {
		                // 조회수 증가
		                postDAO.updateVisitcount(post);

		                // 게시물 상세보기 페이지로 포워딩
		                request.setAttribute("post", post);

		                // 댓글 목록 가져오기
		                CommentDAO commentDAO = new CommentDAO();
		                List<CommentDTO> commentList = commentDAO.getCommentsByPostNum(num);
		                request.setAttribute("commentList", commentList);

		                // 답글 목록 가져오기
		                ReplyDAO replyDAO = new ReplyDAO();
		                List<ReplyDTO> replyList = replyDAO.getRepliesByCommentNum(num);
		                request.setAttribute("replyList", replyList);

		                // 좋아요 수 가져오기
		                int likeCount = postDAO.selectLikeCount(num);
		                request.setAttribute("likeCount", likeCount);

		                // 해당 게시물에 대한 현재 사용자의 좋아요 여부 확인
		                Set<Integer> likedPosts = (Set<Integer>) session.getAttribute("likedPosts");
		                boolean isLiked = likedPosts != null && likedPosts.contains(num);
		                request.setAttribute("isLiked", isLiked);

		                // 게시글을 마지막으로 읽은 날짜를 현재 날짜로 업데이트
		                session.setAttribute("lastReadDate_" + num, currentDate);

		                request.getRequestDispatcher("/friendBoard/viewPost.jsp").forward(request, response);
		            }
		        } else {
		            // 이미 게시물을 본 경우에는 조회수가 증가하지 않도록 하고 게시물 상세보기 페이지로 포워딩
		            // PostDTO 객체 생성 후 해당 게시물 번호를 설정
		            friendBoardDTO postDTO = new friendBoardDTO();
		            postDTO.setNum(num);

		            // PostDAO 객체 생성
		            friendBoardDAO postDAO = new friendBoardDAO();

		            // selectView 메서드 호출 시에는 PostDTO 객체를 전달
		            friendBoardDTO post = postDAO.selectView(postDTO);

		            if (post != null) {
		                // 게시물 상세보기 페이지로 포워딩
		                request.setAttribute("post", post);

		                // 댓글 목록 가져오기
		                CommentDAO commentDAO = new CommentDAO();
		                List<CommentDTO> commentList = commentDAO.getCommentsByPostNum(num);
		                request.setAttribute("commentList", commentList);

		                // 답글 목록 가져오기
		                ReplyDAO replyDAO = new ReplyDAO();
		                List<ReplyDTO> replyList = replyDAO.getRepliesByCommentNum(num);
		                request.setAttribute("replyList", replyList);

		                // 좋아요 수 가져오기
		                int likeCount = postDAO.selectLikeCount(num);
		                request.setAttribute("likeCount", likeCount);

		                // 해당 게시물에 대한 현재 사용자의 좋아요 여부 확인
		                Set<Integer> likedPosts = (Set<Integer>) session.getAttribute("likedPosts");
		                boolean isLiked = likedPosts != null && likedPosts.contains(num);
		                request.setAttribute("isLiked", isLiked);

		                request.getRequestDispatcher("/friendBoard/viewPost.jsp").forward(request, response);
		            }
		        }
		    }
		} else if (action.equals("/deletePost.po")) {
			// 게시물 번호를 받아옴
			String numStr = request.getParameter("num");

			if (numStr != null && !numStr.isEmpty()) {
				int num = Integer.parseInt(numStr);

				// PostDTO 객체 생성 후 해당 게시물 번호를 설정
				friendBoardDTO postDTO = new friendBoardDTO();
				postDTO.setNum(num);

				// PostDAO 객체 생성
				friendBoardDAO postDAO = new friendBoardDAO();

				// delete 메서드 호출
				int result = postDAO.deleteWrite(postDTO);

				if (result == 1) {
					// 삭제 성공 시 게시판 목록 페이지로 이동
					response.sendRedirect(request.getContextPath() + "/friendBoard/friendBoard.po");
				}
			}
		} else if (action.equals("/editPostForm.po")) {
		    // 게시물 수정을 위한 폼으로 이동
		    String postNum = request.getParameter("num");
		    
		    // 게시물 번호를 이용하여 해당 게시물 정보를 가져옴
		    friendBoardDTO post = friendBoardDAO.getPostByNum(postNum);
		    if (post != null) {
		    // 가져온 게시물 정보를 request 속성에 저장
		    request.setAttribute("post", post);
		    
		    // 게시물 수정 폼 페이지로 이동
		    request.getRequestDispatcher("/friendBoard/editPostForm.jsp").forward(request, response);
		    }
		} else if (action.equals("/updatePost.po")) {
		    // 게시물 업데이트 처리
		    String num = null;
		    String title = null;
		    String content = null;
		    String fileName = null;
		    HttpSession session = request.getSession();
		    UserDTO user = (UserDTO) session.getAttribute("user");

		    if (user != null) {
		        String id = user.getId();

		        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		        if (isMultipart) {
		            FileItemFactory factory = new DiskFileItemFactory();
		            ServletFileUpload upload = new ServletFileUpload(factory);
		            try {
		                List<FileItem> items = upload.parseRequest(request);
		                for (FileItem item : items) {
		                    if (item.isFormField()) {
		                        // Form fields 처리
		                        String fieldName = item.getFieldName();
		                        if (fieldName.equals("num")) {
		                            num = item.getString("UTF-8");
		                        } else if (fieldName.equals("title")) {
		                            title = item.getString("UTF-8");
		                        } else if (fieldName.equals("content")) {
		                            content = item.getString("UTF-8");
		                        }
		                    } else {
		                        // 파일 필드 처리
		                        String filePath = getServletContext().getRealPath("/friendBoard/uploads");
		                        System.out.println("filePath: " + filePath); // 디버깅 로그 추가
		                        if (filePath != null) {
		                            File uploadDir = new File(filePath);
		                            if (!uploadDir.exists()) {
		                                boolean dirCreated = uploadDir.mkdirs(); // 디렉토리 생성
		                                System.out.println("Directory created: " + dirCreated); // 디버깅 로그 추가
		                            }
		                            String originalFileName = new File(item.getName()).getName();
		                            String fileExtension = FilenameUtils.getExtension(originalFileName);
		                            String uniqueFileName = System.currentTimeMillis() + "_" + UUID.randomUUID().toString() + "." + fileExtension;
		                            File uploadFile = new File(filePath + File.separator + uniqueFileName);
		                            try {
		                                item.write(uploadFile);
		                                fileName = uniqueFileName;
		                                System.out.println("File uploaded: " + uploadFile.getAbsolutePath()); // 디버깅 로그 추가

		                                // 기존 파일 삭제 로직 (필요한 경우)
		                                friendBoardDAO postDao = new friendBoardDAO();
		                                friendBoardDTO existingPost = postDao.getPostByNum(num); // 문자열 매개변수 사용
		                                if (existingPost != null && existingPost.getFileName() != null) {
		                                    File existingFile = new File(filePath + File.separator + existingPost.getFileName());
		                                }
		                            } catch (Exception e) {
		                                e.printStackTrace();
		                                System.out.println("Error writing file: " + e.getMessage()); // 디버깅 로그 추가
		                            }
		                        } else {
		                            System.out.println("파일 경로를 찾을 수 없습니다.");
		                        }
		                    }
		                }
		            } catch (Exception e) {
		                e.printStackTrace();
		                System.out.println("Error parsing request: " + e.getMessage()); // 디버깅 로그 추가
		            }
		        }

		        if (num != null && title != null && content != null) {
		            // 작성된 내용을 데이터베이스에 저장하는 로직 추가
		            friendBoardDTO updatedPost = new friendBoardDTO();
		            updatedPost.setNum(Integer.parseInt(num));
		            updatedPost.setTitle(title);
		            updatedPost.setContent(content);
		            updatedPost.setFileName(fileName);
		            updatedPost.setId(id);

		            // PostDAO 객체 생성
		            friendBoardDAO postDao = new friendBoardDAO();

		            // 게시물 추가 결과 확인
		            int result = postDao.updateWrite(updatedPost);

		            if (result == 1) {
		                // 게시물 추가 성공 시 게시물 보기 페이지로 이동
		                response.sendRedirect(request.getContextPath() + "/friendBoard/viewPost.po?num=" + num);
		            }
		        }
		    } else {
		        // 세션에서 사용자 정보가 없는 경우 로그인 페이지로 이동
		        response.sendRedirect(request.getContextPath() + "/user/loginForm.jsp");
		    }
		} else if (action.equals("/likePost.po")) {
		    String numStr = request.getParameter("num");
		    
		    if (numStr != null && !numStr.isEmpty()) {
		        int num = Integer.parseInt(numStr);

		        friendBoardDAO postDAO = new friendBoardDAO();
		        HttpSession session = request.getSession();
		        Set<Integer> likedPosts = (Set<Integer>) session.getAttribute("likedPosts");
		        
		        if (likedPosts == null) {
		            likedPosts = new HashSet<>();
		        }

		        int likeCount;
		        if (!likedPosts.contains(num)) {
		            postDAO.incrementLikeCount(num);
		            likedPosts.add(num);
		            session.setAttribute("likedPosts", likedPosts);
		            likeCount = postDAO.selectLikeCount(num);
		        } else {
		            postDAO.decrementLikeCount(num);
		            likedPosts.remove(num);
		            session.setAttribute("likedPosts", likedPosts);
		            likeCount = postDAO.selectLikeCount(num);
		        }

		        // 좋아요 수를 JSON 형식으로 응답
		        response.setContentType("application/json");
		        PrintWriter out = response.getWriter();
		        out.print("{\"likeCount\": " + likeCount + "}");
		        out.flush();
		        return;
		    }
		}
	}
}