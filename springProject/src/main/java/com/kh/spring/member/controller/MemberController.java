package com.kh.spring.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {
	   private final MemberService memberService;
	    private final BCryptPasswordEncoder bCryptPasswordEncoder;

	
	
	/*
	 * Spring 에서 Handler 가 요청시 전달값(Parameter)을 받는방법
	 *  
	 * 1. HttpServletRequest를 이용해서 전달받기(
	 * 
	 * 
	 * 
	
	
	@RequestMapping("login.do") //RequeastMapping 타입의 애노테이션을 붙임으로서 HandlerMapping등록
	public String login(HttpServletRequest request) {
		String userId = request.getParameter("id");
		String userPwd = request.getParameter("pw");
		
		log.info("회원이 입력한 아이디 값 : {}",userId );
		log.info("회원이 입력한 비밀번호 값 : {}",userPwd );
		
		return "main";
	}
	*/
	  /*
     * Spring에서 Handler가 요청시 전달값(Parameter)을 받는 방법
     * 
     * 1. HttpServletRequest를 이용해서 전달받기
     * 
     * 2. @RequestParam 애노테이션을 사용하여 전달받기
     *    - @RequestParam("name속성") 자료형 변수명
     *    - 만약, 넘아오는 값이 비어있는 형태라면 defaultValue 속성으로 기본값을 지정할 수 있음
     
    
    @RequestMapping("login.do") //RequeastMapping 타입의 애노테이션을 붙임으로서 HandlerMapping 등록
    public String login(@RequestParam(value="id", defaultValue="aaa") String userId,
                        @RequestParam(value="pwd") String userPwd) {
        log.info("회원이 입력한 아이디 값 : {}", userId);
        log.info("회원이 입력한 비밀번호 값 : {}", userPwd);
        
        return "main";
    }
	*/
    
    /*
     * 3. RequestParam 애노테이션을 생략하는 방법
     * 
     * 단, 매개변수 식별자를 jsp의 name 속성값 (요청시 전달하는 값의 키값)과 동일하게 작성해주어야만 자동으로 값이 주입 
     * 단점이라고 한다면 2에 사용했던 defualtValue 속성을 사용할 수 없음 

    
    @RequestMapping("login.do")
    public String login(String id, String pwd) {
        log.info("회원이 입력한 아이디 값 : {}", id);
        log.info("회원이 입력한 비밀번호 값 : {}", pwd);
        //이걸왜함?
        //데이터를 넘기면서 매개변수가 많아지면 실수할 확률이 높아진다
        
        Member member = new Member();
        member.setUserId(id);
        member.setUserPw(pwd);
        
        return "main";
    }
    */
    //컨트롤러가 해야할일
    //1.데이터 가공 ->DTO 
    //Member member = new Member();
    //member.setUserId(id);
    //member.setUserPw(pwd);
    //
    //
    //2.응답화면 지정 
	
	
	/*
	 * 4. 커맨드 객체 방식
	 *
	 * **** 반드시 name 속성과 담고자하는 필드명이 동일해야함! + setter가 꼭 있어야함 ! + 기본생성자가 꼭 있어야함!!!!!
	 *
	 * 해당 메서드의 매개변수로
	 * 요청 시 전달값을 담고자하는 클래스의 타입을 지정한 뒤
	 * 요청 시 전달값의 키값(jsp의 name 속성)을 클래스의 담고자하는 필드명과 동일하게 작성
	 *
	 * 스프링 컨테이너가 해당 객체를 기본생성자로 생성한 후 내부적으로 setter 메소드를 찾아서 요청 시 전달값을 해당 필드에 담아줌(setter injection)
	 
	

	// localhost/spring/notice  => 공지사항 관련
	// localhost/psring/board   => 게시판 관련
	 private final MemberService memberService;

	    @RequestMapping("login.do")
	    public String login(Member member) {
	        log.info("가공된 맴버객체 :{}", member);
	       
	        
	        Member loginMember = memberService.login(member);
	        log.info("가공된 맴버객체 :{}", loginMember);
	        return "main";
	    }
	    */
	// localhost/spring        ==> ??
	// localhost/spring/member => 회원 관련
	// 회원 상세 조회   /detail.do
	//	         localhost/spring /member/10
	//@GetMapping
	// 회원 추가     /insert.do
	//@PostMapping
	// 회원 삭제     /delete.do
	//@DeleteMapping
	
	//Rest방식의 URL 만들기 
	//localhost/spring/member/12
	/*@GetMapping("/member/{id}")
	public void restTest(@PathVariable String id){
		log.info("앞단에서 넘긴값{}",id);
	}	*/
	
	
	
	/*
	 * 요청 처리 후 응답데이터를 담고 응답페이지로 포워딩 또는 리다이렉트 하는 방법
	 * 
	 * 1. 스프링에서 제공하는 Model 객체를 사용하는 방법
	 * fowarding 할 응답뷰로 전달하고자 하는 데이터를 Key-Value형태로 담을수 있는 영역
	 * Model객체는 requestscope
	 * 여러분들은 화면으로 jsp를 사용하고 있다!
	

	@PostMapping("login.do")
	public String login(Member member, Model model ,HttpSession session) {
	    // 추가 로직을 여기에 작성합니다.
	
		Member loginUser = memberService.login(member);
		if(loginUser == null) {
			model.addAttribute("errorMsg","로그인에 실패했어요");
			return "common/errorPage";
		}else {//로그인 성공 -> 세션에 담고 응답화면
			session.setAttribute("loginuser", loginUser);
			 return "main";
		}
	}
	*/
	    //member 테이블에 사용자가 입력한  userid값이 존재하고 status값이 Y와 일치한다면
	    //loginUser -> 조회되 ResultSet의 컬림이 값이 필드에 차곡차곡 담긴 member의 객체의 주소값
	    //			->userPwd필드 : db에 기록된 암호화된 비밀번호(암호문)
	    
	    // BCryptPasswordEncoder 객채 matches()
	    // match(평문,암호문)
	    
	    // 암호문에 포함되어있는 Salt값을 판단해서 평문에 Salt값을 더해서 암호화를 반복하여 두값이 같은지 비교!
	@PostMapping("login.do")
    public ModelAndView login(Member member, ModelAndView mv, HttpSession session) {
        log.info("로그인 요청된 회원 정보: {}", member);
        Member loginUser = memberService.login(member);
        log.info("무{}",loginUser);
        
          
         if (loginUser != null && bCryptPasswordEncoder.matches(member.getUserPwd(), loginUser.getUserPwd())) {
            log.info("로그인 성공: {}", loginUser);
            session.setAttribute("loginUser", loginUser);
            log.info("{}",loginUser);
            mv.setViewName("redirect:/");
        } else {
            log.error("로그인 실패: 사용자 정보가 없습니다.");
            mv.addObject("errorMsg", "로그인 실패").setViewName("common/errorPage");
        }
        return mv;
    }
	
	@GetMapping("logout.do")
	public String logout(HttpSession session) {
		//sessionScope 에 존재하는 loginUser 제거
		//session.invalidate();//세션초기화 
		session.removeAttribute("loginUser"); 
		return "redirect:/";
	}
	
	@GetMapping("enroll.do")
	public String enrollForm() {
		return "member/enrollFrom";
		
	}
	  @PostMapping("join.do")
	    public String join(Member member,Model model) {
	        // log.info("회원가입 객체 : {}", member);
	        // 1. 한글이 깨짐! => web.xml에 스프링이 제공하는 인코딩 필터 등록!
	        // 2. 비밀번호가 사용자가 입력한 있는 그대로의 평문(plain text)

	        // log.info("평문 : {}", member.getUserPwd());
	        String encPwd = bCryptPasswordEncoder.encode(member.getUserPwd());
	        // log.info("암호문 : {}", encPwd);
	        member.setUserPwd(encPwd);
	        // Insert할 데이터가 필드에 담긴 Member객체의 userPwd필드에 평문이 아닌 암호문을 담아서 DB로 보내기!
	        
	        // 2. 응답화면 지정
	        String viewName ="";
	        
	       
	        if (memberService.insert(member) > 0) { // 성공 => 메인~
	            viewName = "redirect:/";
	        } else { // 실패 => 에러메시지담아서 에러페이지로 포워딩
	            model.addAttribute("errorMsg", "회원가입실패");
	            viewName= "common/errorPage";
	        }
	        
	        return viewName;
	  }
	  
	  @GetMapping("mypage.do")
	  public String myPage() {
		  return "member/mypage";
	  }
	  
	  @PostMapping("update.do")
	  public String update(Member member, HttpSession session, Model model) {

	      log.info("수정 요청 멤버 : {}", member);

	      // 1 / 0
	      if(memberService.update(member) > 0) {

	          // DB로부터 수정된 회원정보를 다시 조회해서
	          // sessionScope에 loginUser라는 키값으로 덮어씌울것!!
	          session.setAttribute("loginUser", memberService.login(member));
	          // 요청됨
	          //return "member/myPage";
	          
	          // 성공 메세지 넘겨주기 
	          session.setAttribute("alertMsg", "회원정보수정성공");
	          
	          // 2. 리다이렉트
	          return "redirect:mypage.do";

	      } else {

	          model.addAttribute("errorMsg", "정보 수정에 실패했습니다.");
	          return "common/errorPage";

	      }
	  }
	  
	  @PostMapping("delete.do")
	  public String delete(Member member, HttpSession session, Model model) {
		  //비밀번호를 맞게 썻는지 확인
		  //매개변수 Member => userPwd : 사용자가 입력한 비밀번호 평문
		  //세션엔 loginuser 키값으로 저장되있는 Meber 객채의 usePwd 필드 :DBdp 기록된 암호화된비밀번호 
		  
		  String plainPwd = member.getUserPwd();
		  String endPwd = ((Member)session.getAttribute("loginUser")).getUserPwd();
		  //1절 Member 의 주소
		  //2절 Object타입
		  
		  if (bCryptPasswordEncoder.matches(plainPwd, endPwd)) {
		        if (memberService.delete(member.getUserId()) > 0) {
		            session.setAttribute("alertMsg", "탈퇴 성공~~");
		            session.removeAttribute("loginUser");
		            return "redirect:/";
		        } else {
		            model.addAttribute("errorMsg", "회원 탈퇴에 실패했습니다.");
		            return "common/errorPage";
		        }
		    } else {
		        session.setAttribute("alertMsg", "비밀번호가 일치하지 않습니다.");
		        return "redirect:mypage.do";
		    }
	  }
	  
	  @ResponseBody
	  @GetMapping("idCheck.do")
	  public String checkId(String checkId) {
		 // log.info(checkId);

		 return memberService.idCheck(checkId) > 0 ? "NNNNN" : "NNNNY";
	  }
	  
	  
	  
	  
	  
	  
}
	//문제 1: 한글이 깨짐 ->web.xml 에 스프링이 제공하는 인코딩 필터 등록!
	//문제 2: 비밀번호가 사용자가 입력한 있는 그대로의 평문(plain text)- 필수적인과정 감사에 걸림


