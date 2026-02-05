document.addEventListener("DOMContentLoaded", () => {

  /* 1. 비밀번호 길이 프론트 검증 */
  const form = document.getElementById("pwForm");
  if (form) {
    form.addEventListener("submit", (e) => {
      const newPwInput = document.getElementById("newPassword");
      if (!newPwInput) return;

      const newPw = newPwInput.value;

      if (newPw.length < 6) {
        e.preventDefault();
        alert("パスワードは6文字以上で入力してください。");
      }
    });
  }

  /* 2. 비밀번호 변경 성공 alert */
  const pwFlag = document.body.dataset.pw;
  if (pwFlag == "ok") {
    alert("パスワードを変更しました。");
  }

});
