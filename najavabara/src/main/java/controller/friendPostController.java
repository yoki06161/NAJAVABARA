package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.ArrayList;
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

import dao.friendCommentDAO;
import dao.friendBoardDAO;
import dao.friendReplyDAO;
import dto.friendCommentDTO;
import dto.friendBoardDTO;
import dto.friendReplyDTO;
import dto.UserDTO;

@WebServlet("*.fri")
public class friendPostController extends HttpServlet {
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

		if (action.equals("/friendBoard.fri")) {
			// 페이지 번호, 검색어 및 지역 파라미터 가져오기
			String searchField = request.getParameter("searchField");
			String searchWord = request.getParameter("searchWord");
			String searchArea = request.getParameter("searchArea");
			String order = request.getParameter("order"); // 댓글 정렬 순서

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
			friendCommentDAO commentDAO = new friendCommentDAO();
			for (friendBoardDTO post : postLists) {
				List<friendCommentDTO> comments = commentDAO.getCommentsByPostNum(post.getNum(), order);
				int commentCount = comments != null ? comments.size() : 0;
				post.setCommentCount(commentCount);
			}

			// 페이지 관련 정보를 request 속성에 설정
			request.setAttribute("postLists", postLists);
			request.setAttribute("totalCount", totalCount);
			request.setAttribute("pageNum", pageNum);
			request.setAttribute("order", order); // 댓글 정렬 순서 속성 설정

			// forward
			String path = "./friendBoard.jsp";
			request.getRequestDispatcher(path).forward(request, response);
		} else if (action.equals("/writeForm.fri")) {
			// 새 글 작성 폼으로 이동
			response.sendRedirect(request.getContextPath() + "/friendBoard/writeForm.jsp");
		} else if (action.equals("/write.fri")) {
			// 새 글 작성 처리
			String title = null;
			String content = null;
			List<String> fileNames = new ArrayList<>();
			List<String> ofileNames = new ArrayList<>();

			HttpSession session = request.getSession();
			String user = (String) session.getAttribute("id");
			System.out.println(user);
			if (user != null) {
				// 세션에서 name과 area 값을 가져옵니다.
				String id = (String) session.getAttribute("name");
				String area = (String) session.getAttribute("area");

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
									String filePath = "C:/Users/TJ/git/NAJAVABARA/najavabara/src/main/webapp/friendBoard/uploads";
									if (filePath != null) {
										File uploadDir = new File(filePath);
										// filePath에 디렉토리가 없으면 생성
										if (!uploadDir.exists()) {
											uploadDir.mkdirs();
										}
										String originalFileName = new File(item.getName()).getName();
										String fileExtension = FilenameUtils.getExtension(originalFileName);
										String uniqueFileName = System.currentTimeMillis() + "_" + UUID.randomUUID().toString() + "." + fileExtension;
										File uploadFile = new File(filePath + File.separator + uniqueFileName);
										item.write(uploadFile); // 파일을 직접 저장
										fileNames.add(uniqueFileName); // 파일명을 리스트에 추가
										ofileNames.add(originalFileName); // 원본 파일명을 리스트에 추가
									}
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				if (title != null && content != null) {
					friendBoardDTO newPost = new friendBoardDTO(title, content, id, fileNames, ofileNames, area);

					// PostDAO 객체 생성
					friendBoardDAO postDao = new friendBoardDAO();

					// 게시물 추가 결과 확인
					int result = postDao.insertWrite(newPost);

					if (result == 1) {
						// 게시물 추가 성공 시 게시판 목록 페이지로 이동
						response.sendRedirect(request.getContextPath() + "/friendBoard/friendBoard.fri");
					}
				}
			} else {
				// 세션에서 사용자 정보가 없는 경우 로그인 페이지로 이동
				response.sendRedirect(request.getContextPath() + "/user/login.usr");
			}
		} else if (action.equals("/viewPost.fri")) {
			String numStr = request.getParameter("num");
			String order = request.getParameter("order");

			if (numStr != null && !numStr.isEmpty()) {
				int num = Integer.parseInt(numStr);

				HttpSession session = request.getSession();

				LocalDate currentDate = LocalDate.now();
				LocalDate lastReadDate = (LocalDate) session.getAttribute("lastReadDate_" + num);

				if (lastReadDate == null || !currentDate.equals(lastReadDate)) {
					friendBoardDTO postDTO = new friendBoardDTO();
					postDTO.setNum(num);

					friendBoardDAO postDAO = new friendBoardDAO();
					friendBoardDTO post = postDAO.selectView(postDTO);

					if (post != null) {
						postDAO.updateVisitcount(post);
						request.setAttribute("post", post);

						friendCommentDAO commentDAO = new friendCommentDAO();
						List<friendCommentDTO> commentList = commentDAO.getCommentsByPostNum(num, order);
						request.setAttribute("commentList", commentList);

						friendReplyDAO replyDAO = new friendReplyDAO();
						List<friendReplyDTO> replyList = replyDAO.getRepliesByCommentNum(num);
						request.setAttribute("replyList", replyList);

						int likeCount = postDAO.selectLikeCount(num);
						request.setAttribute("likeCount", likeCount);

						Set<Integer> likedPosts = (Set<Integer>) session.getAttribute("likedPosts");
						boolean isLiked = likedPosts != null && likedPosts.contains(num);
						request.setAttribute("isLiked", isLiked);

						session.setAttribute("lastReadDate_" + num, currentDate);

						request.getRequestDispatcher("/friendBoard/viewPost.jsp").forward(request, response);
					}
				} else {
					friendBoardDTO postDTO = new friendBoardDTO();
					postDTO.setNum(num);

					friendBoardDAO postDAO = new friendBoardDAO();
					friendBoardDTO post = postDAO.selectView(postDTO);

					if (post != null) {
						request.setAttribute("post", post);

						friendCommentDAO commentDAO = new friendCommentDAO();
						List<friendCommentDTO> commentList = commentDAO.getCommentsByPostNum(num, order);
						request.setAttribute("commentList", commentList);

						friendReplyDAO replyDAO = new friendReplyDAO();
						List<friendReplyDTO> replyList = replyDAO.getRepliesByCommentNum(num);
						request.setAttribute("replyList", replyList);

						int likeCount = postDAO.selectLikeCount(num);
						request.setAttribute("likeCount", likeCount);

						Set<Integer> likedPosts = (Set<Integer>) session.getAttribute("likedPosts");
						boolean isLiked = likedPosts != null && likedPosts.contains(num);
						request.setAttribute("isLiked", isLiked);

						request.getRequestDispatcher("/friendBoard/viewPost.jsp").forward(request, response);
					}
				}
			}
		} else if (action.equals("/deletePost.fri")) {
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
					response.sendRedirect(request.getContextPath() + "/friendBoard/friendBoard.fri");
				}
			}
		} else if (action.equals("/editPostForm.fri")) {
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
		} else if (action.equals("/updatePost.fri")) {
			// 게시물 업데이트 처리
			String num = null;
			String title = null;
			String content = null;
			List<String> fileNames = new ArrayList<>(); // 파일명 리스트 초기화
			List<String> ofileNames = new ArrayList<>(); // 원본 파일명 리스트 초기화
			HttpSession session = request.getSession();
			String user = (String) session.getAttribute("id");

			if (user != null) {
				String id = (String) session.getAttribute("name");
				String area = (String) session.getAttribute("area");

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
										fileNames.add(uniqueFileName); // 파일명을 리스트에 추가
										ofileNames.add(originalFileName); // 원본 파일명을 리스트에 추가
										System.out.println("File uploaded: " + uploadFile.getAbsolutePath()); // 디버깅 로그 추가

										// 기존 파일 삭제 로직 (필요한 경우)
										friendBoardDAO postDao = new friendBoardDAO();
										friendBoardDTO existingPost = postDao.getPostByNum(num); // 문자열 매개변수 사용
										if (existingPost != null && existingPost.getFileNames() != null && !existingPost.getFileNames().isEmpty()) {
											for (String existingFileName : existingPost.getFileNames()) {
												File existingFile = new File(filePath + File.separator + existingFileName);
												boolean deleted = existingFile.delete();
												System.out.println("Deleted existing file: " + existingFile.getAbsolutePath() + ", Result: " + deleted); // 디버깅 로그 추가
											}
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
					updatedPost.setFileNames(fileNames); // 파일명 리스트 설정
					updatedPost.setOfileNames(ofileNames); // 원본 파일명 리스트 설정
					updatedPost.setId(id);
					updatedPost.setArea(area);

					// PostDAO 객체 생성
					friendBoardDAO postDao = new friendBoardDAO();

					// 게시물 추가 결과 확인
					int result = postDao.updateWrite(updatedPost);

					if (result == 1) {
						// 게시물 추가 성공 시 게시물 보기 페이지로 이동
						response.sendRedirect(request.getContextPath() + "/friendBoard/viewPost.fri?num=" + num);
					}
				}
			} else {
				// 세션에서 사용자 정보가 없는 경우 로그인 페이지로 이동
				response.sendRedirect(request.getContextPath() + "/user/login.usr");
			}
		}else if (action.equals("/likePost.fri")) {
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
		} else if (action.equals("/download.fri")) {
			String saveDirectory = getServletContext().getRealPath("/friendBoard/uploads");
			String fileName = request.getParameter("fileName");
			String originalFileName = request.getParameter("ofileName");

			if (saveDirectory != null && fileName != null) {
				File downloadFile = new File(saveDirectory, fileName);

				if (downloadFile.exists()) {
					response.setContentType("application/octet-stream");
					response.setContentLength((int) downloadFile.length());

					String encodedFileName = URLEncoder.encode(originalFileName, "UTF-8").replace("+", "%20");
					response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\"");

					try (FileInputStream in = new FileInputStream(downloadFile);
							OutputStream out = response.getOutputStream()) {
						byte[] buffer = new byte[4096];
						int bytesRead = -1;
						while ((bytesRead = in.read(buffer)) != -1) {
							out.write(buffer, 0, bytesRead);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					response.setContentType("text/html");
					response.getWriter().println("<h3>다운로드할 파일을 찾을 수 없습니다.</h3>");
				}
			} else {
				response.setContentType("text/html");
				response.getWriter().println("<h3>다운로드할 파일 경로를 찾을 수 없습니다.</h3>");
			}
		}
	}
}