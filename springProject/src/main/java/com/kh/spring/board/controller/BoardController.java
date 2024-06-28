package com.kh.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.common.template.PageTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {

	private final BoardService boardService;
	/*
	private int abc = 0;
	public BoardService getBoardService() {
		return boardService;
	}
	public int getAbc() {
		return abc;
	}
	public BoardService getBoardServiceAndAbc() {
		return boardService + abc;
	}
	*/
	// member.jsp에서 클릭 시 = boardlist
	// 페이징바를 클릭했다 !! => boardlist?page=요청페이지
	
	// localhost/spring/boardlist
	// localhost/spring/boardlist?page=2
	// localhost/spring/boardlist?page=3
	@GetMapping("boardlist")
	public String forwarding(@RequestParam(value="page",defaultValue = "1")int page,
								Model model) {
		
		// --페이징 처리--
		
		// 필요한 변수들
		int listCount; //현재 일반 게시판의 게시글 총 개수 => Board테이블로부터 SELECT COUNT(*)활용해서 조회
		int currentPage;  // 현재 페이지(사용자가 요청한 페이지) => 앞에서 넘길 것
		int pageLimit; // 페이징 하단에 보여질 페이징바의 최대 개수 => 10개
		int boardLimit; // 한 페이지에 보여질 게시글의 최대 개수 => 10개
		
		
		int maxPage;  // 가장 마지막 페이지가 몇 번 페이지인지(총 페이지 개수)
		int startPage; // 페이지 하단에 보여질 페이징바의 시작 수
		int endPage; // 페이지 하단에 보여질 페이징바의 끝 수
		
		//ㅣ * listCount : 총 게시글의 수
		listCount = boardService.boardCount();
		
		// currnetPage : 현재 페이지(사용자가 요청한 페이지)
		currentPage = page;
		log.info("게시글의 총 개수 : {}, 현재 요청 페이지 : {}",listCount,currentPage );
		
		// * pageLimit : 페이징바의 최대 개수
		pageLimit = 10;
		
		// * boardLimit : 한 페이지에 보여질 게시글의 최대 개수
		boardLimit = 10;
		
		// * maxPage : 가장 마지막 페이지가 몇 번 페이지인지(총 페이지 개수)
		/*
		 * listCount, boardLimit에 영향을 받음
		 * 
		 * - 공식 구하기
		 * 	단, boardLimit이 10이라는 가정하에 규칙을 찾아보자
		 * 
		 * 총 개수 (listCount)			게시글 개수(boardLimit)		maxPage(마지막페이지)
		 * 100						10					==		10페이지
		 * 106						10					==		11페이지
		 * 111						10					==		12페이지
		 * => 나누셈 결과에 소수점을 붙여서 올림처리를 할 경우 maxPage가 됨
		 *
		 * listCount/boardLimit
		 * 
		 * 스텝
		 * 1. listCount를 double로 변환
		 * 2. listCount / boardLimit
		 * 3. Math.ceil() => 결과를 올림처리 
		 * 4. 결과값을 int로 형변환
		 */
		
		maxPage = (int)Math.ceil((double)listCount/boardLimit);
		
		//Math math = new Math(); 
		//전부 static 
		// * startPage : 페이지 하단에 보여질 페이징바의 시작 수
		/*
		 * currentPage, pageLimit에 영향을 받음
		 * 
		 * -공식
		 * 		단, pageLimit이 10이라고 가정
		 * 
		 * - startPage : 1, 11, 21, 31, 41 => n * 10 +1
		 * 
		 * 만약에 pageLmit이 5다
		 * - startPage : 1, 6, 11, 16, 21 => n * 5 +1
		 * \
		 * 즉 , startPage = n * pageLimit + 1
		 * 
		 * currentPgae		startPage
		 * 1					1
		 * 5					1
		 * 9					1
		 * 10					1
		 * 13					11
		 * 15					11
		 * 20					11
		 * 21					21

		 * => 1~10 : n * 10 +1 ==> n ==0
		 * => 11~20 : n * 10+1 ==> n ==1
		 * => 21~30 : n * 10+1 ==> n ==2
		 *
		 *1 ~ 10 /10 => 0 ~ 1
		 *11~ 20 /10 => 1 ~ 2
		 *21 ~ 30 /10 => 2 ~3
		 *
		 *  n = (currentPage -1) / pageLimit;
		 *
		 *  startPage = (currentPage -1 )/ pageLimit * pageLimit +1 ;
		 */
		
		startPage = (currentPage -1 )/ pageLimit * pageLimit +1 ;
		
		// * endPage : 페이지 하단에 보여질 페이징 바의 끝 수
		/*
		 * startPage, pageLimit에 영향을 받음(maxPage도 마지막 페이징바에 대해 영향을 끼침)
		 * -공식
		 * 단 , pageLimit이 10이라고 가정하고
		 * 
		 * startPage 1 => endPage : 10
		 * startPage : 11 => endPage : 20
		 * startPage : 21 => endPage : 30
		 *	endPage = startPage + pageLimit -1; 
		 */
		
		// startPage가 1이라서 endPage가 10이 들어갔는데
		// maxPage가 2다
		// endPage를 maxPage값을 변경해야한다.
		//
		
		endPage = startPage + pageLimit -1;
		if(endPage > maxPage) endPage = maxPage;
		
		// 아 떻게 하지?? xxx
		// 아 뭐쓰지?? 000
		// 자ㅏㅂ에서 내가 다뤄야할 값이 많다.
		// 선택지가 없다.
		//1번 클래스/2번 int[]/3번 List/4번 Map/
		
		//1번 클래스
		//PageInfo pageInfo = new PageInfo(listCount, currentPage, pageLimit, boardLimit, maxPage, startPage, endPage);
		//우리 팀은 페이징 처리할때 생색좀 내도 괜춤
		//필드가 많으면 어떤 순서로 넣어야하는지모름
		
		//2번 클래스 @Builder 객체 : 넣고 싶은 필드의 값만 넣을 수 있따. 매서들르 계속 쓸쑤있도록, 순서에 구해맞지않음.
		
		
		 PageInfo pageInfo = PageInfo.builder()
									.listCount(listCount)
									.currentPage(currentPage)
									.pageLimit(pageLimit)
									.boardLimit(boardLimit)
									.maxPage(maxPage)
									.startPage(startPage)
									.endPage(endPage)
									.build();
	
									/*
		 * boardLimit이 10이라는 가정하에
		 * 
		 * currentPage == 1 => 시작값은 1,  끝값 10
		 * currentPage == 2 => 시작값은 11, 끝값 20
		 * currentPage == 3 => 시작값은 21, 끝값 30
		 * 
		 * 시작값 = currentPage * boardLimit -9
		 * 시작값 = (currentPage - 1) * boardLimit + 1
		 * 끝 값 = 시작값 + boardLimit -1
		 */
		Map<String, Integer> map = new HashMap();
		
		int startValue = (currentPage -1) * boardLimit +1;
		int endValue = startValue + boardLimit -1;
		
		map.put("startValue", startValue);
		map.put("endValue", endValue);
		
		List<Board> boardList = boardService.findAll(map);
		
		log.info("조회된 게시글의 개수 : {}", boardList.size());
		log.info("--------------------------------");
		log.info("조회된 게시글 목록 : {}", boardList);
		
		model.addAttribute("list",boardList);
		model.addAttribute("pageInfo",pageInfo);
		
		
		return "board/list";
	}
	
	
	@GetMapping("search.do")
	public String search(String condition, String keyword,
					@RequestParam(value = "page", defaultValue = "1")int page,Model model) {
		
		log.info("검색조건 : {} ", condition);
		log.info("검색 키워드 : {} ", keyword);
		// 사용자가 선택한 조건과 입력한 키워드를 가지고
		// 페이징 처리를 끝낸 후 검색 결과를 들고가야함
		
		// condition 에 들어오는 값 writer, title, content
		// keyword 에 들어오는 값 사용자가 입력한 키워드->모가 들어오는지 알 수 없다.
		
		// String[], List, Set, Map, Class 중 택 1
		Map<String, String> map = new HashMap();
		map.put("condition", condition);
		map.put("keyword", keyword);
		
		int searchCount = boardService.searchCount(map);
		log.info("검색 조건에 부합하는 행의 수 : {}", searchCount);
		
		int currentPage = page;
		int pageLimit = 3;
		int boardLimit = 3;
		/*
		int maxPage = (int)Math.ceil((double)searchCount/boardLimit);
		int startPage = (currentPage-1)/pageLimit * pageLimit +1;
		int endPage = startPage + pageLimit -1;
		if(endPage>maxPage) endPage = maxPage;
		
		PageInfo pageInfo = PageInfo.builder()
									.pageLimit(pageLimit)
									.startPage(startPage)
									.endPage(endPage)
									.listCount(searchCount)
									.currentPage(currentPage)
									.maxPage(maxPage)
									.boardLimit(boardLimit)
									.build();
		*/
		
		PageInfo pageInfo = PageTemplate.getPageInfo(searchCount, currentPage, pageLimit, boardLimit);
		
		
		// 마이바티스에서 제공하는 RowBounds
		// offset : 전체 조회 결과에서 몇, limit : 한번 조회 결과에서 몇개 들고갈지
		RowBounds rowBouds = new RowBounds((currentPage -1) * boardLimit,boardLimit);
		//ㅁMyBatis에서는 페이징 처리르 ㄹ위해 RowBouds라는 클래스를 제공
		// offset, limit
		/*
		 * boardLimit이 3일 경우 		건너뛸 숫자(offset)
		 * 
		 * currentPage : 1 -> 1~3 ==> 0
		 * currentPage : 2 -> 4~6 ==> 3
		 * currentPage : 3 -> 7~9 ==> 6
		 * 
		 * (currentPgae() -1) * boardLmit()
		 * 
		 */
		List<Board> boardList = boardService.findByConditionAndKeyword(map, rowBouds);
		
		model.addAttribute("list",boardList);
		model.addAttribute("pageInfo",pageInfo);
		model.addAttribute("keyword",keyword);
		model.addAttribute("condition",condition);
		
		
		return "board/list";
	}
	
	@GetMapping("insertForm.do")
	public String boardForwarding() {
		return "board/insertForm";
	}
	
	@PostMapping("insert.do")
	public String insert(Board board, MultipartFile upfile ,HttpSession session ,Model model) {
						
	    log.info("게시글정보 : {}", board);
	    log.info("파일의정보 : {}", upfile);
	    
	    if(!upfile.getOriginalFilename().equals("")) {
	         /*
	         //uploadFiles 디렉토리에 파일 업로드
	         //이름 변경 :  KH_년월일시분초_랜덤값.확장자
	         String originName = upfile.getOriginalFilename();
	         String ext = originName.substring(originName.lastIndexOf("."));
	         //랜덤값
	         //Math.random(); >> 0.0 ~ 0.999999999999 의 값
	         int num = (int) (Math.random() * 900) + 100; // 100 위치 : 범위, 1 위치 : 시작값
	         //년월일시분초 
	         String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	         //업로드 경로 : 프로그램 전역에서 접근할 수 있는 Application 객체를 사용
	         String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/");   
	         
	         String changeName = "KH_" + currentTime + "_" + num + ext;
	         
	         //파일 업로드
	         try {
	            upfile.transferTo(new File(savePath + changeName));
	         } catch (IllegalStateException e) {
	            e.printStackTrace();
	         } catch (IOException e) {
	            e.printStackTrace();
	         }*/
	         
	         
	         
	         //Board 객체에 originName + changeName 값 저장 (첨부파일이 존재한다는 조건일 경우의 코드이므로 값 지정해줘야 함!)
	         board.setOriginName(upfile.getOriginalFilename());
	         board.setChangeName(saveFile(upfile, session));
	      } 
	    //첨부파일이 존재하지 않을 경우 board : 제목 내용 작성자
	    // 첨부파일이 존재할 경우 board : 제목 내용 작성자 원본명 변경되 경로와 이름
	    
	    if(boardService.insert(board)>0) {
	    	session.setAttribute("alertMsg","게시물등록완료");
	    	
	    	//무조건 리다이렉트해야함
	    	
	    	return "redirect:boardlist";
	    }else {
	    	model.addAttribute("alertMsg","게시물등록실패다 18");
	    }

	    return "redirect:/insertForm.do";
	}

	
	@GetMapping("board-detail")
		public ModelAndView findByBoardNo(int boardNo,  ModelAndView mv ) {
		//1.데이터가공 : 1개라서 할게없네 
		//2.서비스호출 : 
		//3.응답화면지정
		
		  if (boardService.increaseCount(boardNo) > 0) { // 성공 => 메인~
			  mv.addObject("board", boardService.findById(boardNo)).setViewName("board/boardDetail");
	        } else { // 실패 => 에러메시지담아서 에러페이지로 포워딩
	        	mv.addObject("erroMsg","게시글 상세조회에 실패했습니다").setViewName("common/errorPage");
	        	
	        }
		
		return mv;
		}
	
	/*
	 * deleteById : Client (게시글장석자) 에게 정수행의 boardNO(보드테이블의 PK)를 전달 받아서BOARD테이블의 존재하는 STATUS 칼럼의 값을 'N'으로 생신
	 * 
	 * 
	 * @param boardNO :각행을 식별하기 위한 PK
	 * @param filePath: 요청처리 성공시 첨부파일을 제거하기 위해 파일이 저장되어있는 경로 및 파일명
	 * @return : 반환된 View 논리적은 경로
	 * 
	 */
	
	@PostMapping("boardDelete.do")
	public String deleteById(int boardNo, String filePath ,Model model,HttpSession session) { 
		
		/*
		 * 질문의 구조
		 * 
		 * 1내가 지금 무슨일하고있는데
		 * 
		 * 2어느부분에서 무슨 문제가 발생했는지 
		 * 
		 * 3.제생각에는 ... 요거를 찾아가지고 어떤방법으로 해결을 해보려 했으나,,,
		 * 
		 * 4. 어떤방법을 사용하면될까요????????
		 * 
		 * */
	    	if (boardService.delete(boardNo) > 0) {
	    	    if (!"".equals(filePath)) {
	    	        new File(session.getServletContext().getRealPath(filePath)).delete();
	    	    }
	    	    session.setAttribute("alertMsg", "게시글 삭제 성공 😎");
	    	    return "redirect:boardlist";
	    	

	    }else {
	    	model.addAttribute("erroMsg","게시글 삭제 실패!");
	    	return "common/errorPage";
	    }
		
	}
	
	@PostMapping("boardUpdateForm.do")
	public ModelAndView updateForm(ModelAndView mv, int boardNo) {
	    mv.addObject("board", boardService.findById(boardNo))
	      .setViewName("board/boardUpdate");
	    return mv;
	}
	@PostMapping("board-update.do")
	public String update(Board board,MultipartFile reupFile,HttpSession session) {
		
		// DB가서 BOARD테이블 UPDATE

		// Board board
		/*
		 *  -> boardTitle, boardContent
		 *  -> boardWriter, boardNo
		 * 
		 * + File
		 * 1. 기존 첨부파일 X, 새로운 첨부파일 X  => 그무냐~
		 * 
		 * 2. 기존 첨부파일 O, 새로운 첨부파일 X  => origin : 기존 첨부파일 이름, change : 기존 첨부파일 경로
		 * 
		 * 3. 기존 첨부파일 X, 새로운 첨부파일 O  => origin : 새로운 첨부파일 이름, change : 새로운 첨부파일 경로
		 * 
		 * 4. 기존 첨부파일 O, 새로운 첨부파일 O  => origin : 새로운 첨부파일 이름, change : 새로운 첨부파일 경로
		 */
		if (!reupFile.getOriginalFilename().equals("")) {
		    board.setOriginName(reupFile.getOriginalFilename());
		    board.setChangeName(saveFile(reupFile, session));
		}

		if (boardService.update(board) > 0) {
		    session.setAttribute("alertMsg", "게시물 수정 완료");
		    return "redirect:board-detail?boardNo=" + board.getBoardNo();
		}else {
			 session.setAttribute("alertMsg", "게시물 수정 실패다 이자식아");
			 return "common/errorPage";
		}

		
		
	}
	//파일업로드의 메서드를 만들어줌 
	public String saveFile(MultipartFile upfile,HttpSession session) {
		  String originName = upfile.getOriginalFilename();
	         String ext = originName.substring(originName.lastIndexOf("."));
	         //랜덤값
	         //Math.random(); >> 0.0 ~ 0.999999999999 의 값
	         int num = (int) (Math.random() * 900) + 100; // 100 위치 : 범위, 1 위치 : 시작값
	         //년월일시분초 
	         String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	         //업로드 경로 : 프로그램 전역에서 접근할 수 있는 Application 객체를 사용
	         String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/");   
	         
	         String changeName = "KH_" +currentTime +"_" +num +ext;
	         
	         try {
	                upfile.transferTo(new File(savePath + changeName));
	            } catch (IllegalStateException e) {
	                e.printStackTrace();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	    return "resources/uploadFiles/" +changeName;
	}
	
	@GetMapping("image-board")
	public String images(Model model) {
		//List<Board> images = boardService.selectImages();
		model.addAttribute("board", boardService.selectImages());
		
		
		return "board/imageList";
	}
	
	@ResponseBody
	@GetMapping(value="reply", produces="application/json; charset=UTF-8")
	public String selectReply(int boardNo) {
		
		return new Gson().toJson(boardService.selectReply(boardNo));

	}
	
	@ResponseBody
	@PostMapping("reply")
	public String saveReply(Reply reply) {
		return boardService.insertReply(reply)> 0 ? "success" : "fail";

	}
	
	@ResponseBody
	@GetMapping("board-reply")
	   public Board boardAndReply(int boardNo) {
		return boardService.boardAndReply(boardNo);
	   }
	
	
}


