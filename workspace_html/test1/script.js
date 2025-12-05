// index.html에서 버튼 클릭 시 result.html로 이동
document.addEventListener("DOMContentLoaded", function () {
  const btn = document.getElementById("goBtn");
  if (!btn) return;

  btn.addEventListener("click", function () {
    // 페이지 이동
    window.location.href = "result.html";
  });
});
