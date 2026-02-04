document.addEventListener("DOMContentLoaded", () => {
  const params = new URLSearchParams(window.location.search);

  if (params.has("error")) {
    alert("IDまたはパスワードが正しくありません。");
    params.delete("error");
  }

  const success = params.get("success");
  if (success) {
    alert(decodeURIComponent(success));
    params.delete("success");
  }

  const newUrl = window.location.pathname + (params.toString() ? "?" + params.toString() : "");
  history.replaceState({}, "", newUrl);
});
