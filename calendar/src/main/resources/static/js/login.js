document.addEventListener("DOMContentLoaded", () => {
  const params = new URLSearchParams(window.location.search);

  const error = params.get("error");
  if (error) {
    alert("IDまたはパスワードが正しくありません。");
  }

  const success = params.get("success");
  if (success) {
    alert(decodeURIComponent(success));
  }
});
