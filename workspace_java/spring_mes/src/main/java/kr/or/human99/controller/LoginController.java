package kr.or.human99.controller;

import kr.or.human99.dto.UserDTO;
import kr.or.human99.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;

@Controller
public class LoginController {

    @Autowired  // ✅ 스프링이 AuthService를 자동 주입
    private AuthService authService;

    @GetMapping("/login")
    public String loginForm(@RequestParam(value="error", required=false) String error, Model model) {
        if (error != null)
            model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String loginId,
                          @RequestParam String password,
                          HttpServletRequest req) {

        UserDTO user = authService.authenticate(loginId, password);
        if (user == null) {
            return "redirect:/login?error=1";
        }

        HttpSession session = req.getSession(true);
        session.setAttribute("loginUser", user);
        session.setAttribute("loggedIn", true);
        session.setAttribute("role", user.getUser_role());
        return "redirect:/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) session.invalidate();
        return "redirect:/login";
    }
}
