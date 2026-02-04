document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector(".signup-form");
  const pw = document.getElementById("password");
  const pw2 = document.getElementById("password2"); // 너 id 맞춰서

  // 요소 없으면 종료
  if (!form || !pw || !pw2) return;

  // 프론트 비번 확인
  form.addEventListener("submit", (e) => {
    if (pw.value !== pw2.value) {
      e.preventDefault();
      alert("パスワードが一致しません。");
      pw2.focus();
    }
  });

  // 서버에서 넘어온 에러
  const params = new URLSearchParams(window.location.search);
  const error = params.get("error");
  if (error) {
    alert(decodeURIComponent(error));

    // 한 번 띄우고 URL에서 제거 (새로고침/뒤로가기 중복 방지)
    params.delete("error");
    const newUrl = window.location.pathname + (params.toString() ? "?" + params.toString() : "");
    history.replaceState({}, "", newUrl);
  }
});
