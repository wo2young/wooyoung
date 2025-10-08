package kr.or.human99.controller;

import kr.or.human99.dto.UserDTO;
import kr.or.human99.service.MyPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class MyPageController {

    @Autowired
    private MyPageService myPageService;

    // 마이페이지 화면
    @GetMapping("/mypage")
    public String myPage(HttpSession session, Model model) {
        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
        if (loginUser == null) return "redirect:/login";

        UserDTO user = myPageService.getUserById(loginUser.getUser_id());
        model.addAttribute("user", user);
        return "mypage";
    }

    // 비밀번호 변경
    @PostMapping("/mypage/change-password")
    public String changePassword(HttpSession session,
                                 @RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 Model model) {
        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
        if (loginUser == null) return "redirect:/login";

        boolean success = myPageService.changePassword(loginUser.getUser_id(), currentPassword, newPassword);
        model.addAttribute("result", success ? "비밀번호가 변경되었습니다." : "현재 비밀번호가 올바르지 않습니다.");
        return "mypage";
    }
}
