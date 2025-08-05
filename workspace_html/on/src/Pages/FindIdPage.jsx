import { useNavigate } from "react-router-dom";
import logoImg from "../assets/logo.png";       // 실제 경로에 맞게 수정!
import "../Styles/FindIdPage.css";

function FindIdPage() {
  const navigate = useNavigate();

  return (
    <div className="FID">
      <div className="FIDcontainer">
        {/* 상단 로고! 클릭시 Home */}
        <div
          className="FIDlogo"
          style={{ marginBottom: 24, cursor: "pointer", width: "100%", display: "flex", justifyContent: "center" }}
          onClick={() => navigate("/")}
        >
          <img src={logoImg} alt="한끼온 로고" style={{ width: 280, height: "auto", objectFit: "contain" }} />
        </div>

        <h1 className="FIDh1">아이디 찾기</h1>
        <div className="FIDinput-box">
          <input type="text" placeholder="이름을 입력해 주세요" />
        </div>
        <div className="FIDinput-box">
          <input type="text" placeholder="가입한 전화번호를 입력해 주세요" />
        </div>
        <button className="FIDbtn FIDbtn-primary">아이디 찾기</button>
        <button
          className="FIDbtn FIDbtn-secondary"
          onClick={() => navigate("/login")}
        >
          로그인으로 돌아가기
        </button>
        <div className="FIDlinks">
          <a
            href="#"
            onClick={e => {
              e.preventDefault();
              navigate("/find-pw");
            }}
          >
            비밀번호 찾기
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

export default FindIdPage;
