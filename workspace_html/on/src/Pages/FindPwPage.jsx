import { useNavigate } from "react-router-dom";
import logoImg from "../assets/logo.png";       // 실제 경로에 맞게!
import "../Styles/FindPwPage.css";

function FindPwPage() {
  const navigate = useNavigate();

  return (
    <div className="FPW">
      <div className="FPWcontainer">
        {/* 상단 로고, 클릭시 홈으로 이동 */}
        <div
          className="FPWlogo"
          style={{
            marginBottom: 24,
            cursor: "pointer",
            width: "100%",
            display: "flex",
            justifyContent: "center"
          }}
          onClick={() => navigate("/")}
        >
          <img
            src={logoImg}
            alt="한끼온 로고"
            style={{
              width: 280,
              height: "auto",
              objectFit: "contain"
            }}
          />
        </div>

        <h1 className="FPWh1">비밀번호 찾기</h1>
        <div className="FPWinput-box">
          <input type="text" placeholder="아이디를 입력해 주세요" />
        </div>
        <div className="FPWinput-box">
          <input type="text" placeholder="가입한 전화번호를 입력해 주세요" />
        </div>

        <button className="FPWbtn FPWbtn-primary">비밀번호 찾기</button>
        <button
          className="FPWbtn FPWbtn-secondary"
          onClick={() => navigate("/login")}
        >
          로그인으로 돌아가기
        </button>

        <div className="FPWlinks">
          <a
            href="#"
            onClick={e => {
              e.preventDefault();
              navigate("/find-id");
            }}
          >
            아이디 찾기
          </a>
          <a
            href="#"
            onClick={e => {
              e.preventDefault();
              navigate("/signup");
            }}
          >
            회원가입
          </a>
        </div>
      </div>
    </div>
  );
}

export default FindPwPage;
