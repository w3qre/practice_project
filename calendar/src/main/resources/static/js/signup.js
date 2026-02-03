document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector(".signup-form");
  const pw = document.getElementById("password");
  const pw2 = document.getElementById("password2");

  // 요소 없으면 종료 (페이지 깨짐 방지)
  if (!form || !pw || !pw2) return;

  form.addEventListener("submit", (e) => {
    if (pw.value !== pw2.value) {
      e.preventDefault();
      alert("パスワードが一致しません。");
      pw2.focus();
    }
  });
  
  const params = new URLSearchParams(window.location.search);
   const error = params.get("error");
   if (error) alert(decodeURIComponent(error));
   
});
