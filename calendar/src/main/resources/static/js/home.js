document.addEventListener("DOMContentLoaded", () => {
  let activeDate = null;

  // 변수 참조
  const modal = document.getElementById("memoModal");
  const modalDateText = document.getElementById("modalDateText");
  const memoText = document.getElementById("memoText");
  const btnCancel = document.getElementById("btnCancel");
  const btnSave = document.getElementById("btnSave");

  // 모달 관련 요소가 하나라도 없으면 종료
  if (!modal || !modalDateText || !memoText || !btnCancel || !btnSave) {
    console.error("[home.js] modal elements missing. check ids in home.html");
    return;
  }

  // CSRF headers
  function csrfHeaders() {
    const tokenMeta = document.querySelector('meta[name="_csrf"]');
    const headerMeta = document.querySelector('meta[name="_csrf_header"]');
    if (!tokenMeta || !headerMeta) return {};
    return { [headerMeta.content]: tokenMeta.content };
  }

  // 오늘 날짜 "YYYY-MM-DD"
  function todayIso() {
    const t = new Date();
    const y = t.getFullYear();
    const m = String(t.getMonth() + 1).padStart(2, "0");
    const d = String(t.getDate()).padStart(2, "0");
    return `${y}-${m}-${d}`;
  }

  // 今日のやること 표시
  function renderTodayBox(content) {
    const box = document.getElementById("todayTodoBox");
    if (!box) return;

    const txt = (content ?? "").trim();
    box.textContent = txt ? txt : "（今日はまだ予定がありません）";
  }

  // 오늘 내용 로드
  async function loadTodayTodo(today) {
    try {
      const res = await fetch(`/api/todo?date=${encodeURIComponent(today)}`);
      if (!res.ok) return renderTodayBox("");
      const data = await res.json();
      renderTodayBox(data.content);
    } catch (e) {
      renderTodayBox("");
    }
  }

  // 달력 칸 렌더링
  function renderTodoToCell(date, content) {
    const cell = document.querySelector(`.day-cell[data-date="${date}"]`);
    if (!cell) return;

    const box = cell.querySelector(".todo-preview");
    if (!box) return;

    box.textContent = "";

    const txt = (content ?? "").trim();
    if (!txt) return;

    const div = document.createElement("div");
    div.className = "todo-line";
    div.textContent = txt.length > 30 ? txt.slice(0, 30) + "…" : txt;
    box.appendChild(div);
  }

  // 새로고침 시 달력 전체 로드
  async function loadAllTodosOnCalendar() {
    const cells = document.querySelectorAll(".day-cell[data-date]");
    for (const cell of cells) {
      const date = cell.dataset.date;
      try {
        const res = await fetch(`/api/todo?date=${encodeURIComponent(date)}`);
        if (!res.ok) continue;
        const data = await res.json();
        renderTodoToCell(data.date, data.content);
      } catch (e) {}
    }
  }

  // 초기 로드
  loadAllTodosOnCalendar();
  const today = todayIso();
  loadTodayTodo(today);

  // Cancel
  btnCancel.addEventListener("click", () => {
    modal.classList.add("hidden");
    activeDate = null;
  });

  // + 버튼 클릭 시 모달 열기 + 기존 내용 로드
  document.addEventListener("click", async (e) => {
    const btn = e.target.closest(".add-btn");
    if (!btn) return;

    const cell = btn.closest(".day-cell");
    if (!cell) return;

    activeDate = cell.dataset.date;

    // 날짜 표시 : YYYY年 M月 D日
    const d = new Date(activeDate);
    modalDateText.textContent = `${d.getFullYear()}年 ${d.getMonth() + 1}月 ${d.getDate()}日`;

    memoText.value = "";
    modal.classList.remove("hidden");
    memoText.focus();

    try {
      const res = await fetch(`/api/todo?date=${encodeURIComponent(activeDate)}`);
      if (!res.ok) return;
      const data = await res.json();
      memoText.value = data.content || "";
      memoText.focus();
    } catch (err) {
      console.error("GET error:", err);
    }
  });

  // 저장
  btnSave.addEventListener("click", async () => {
    if (!activeDate) return;

    const content = memoText.value.trim();

    try {
      const res = await fetch("/api/todo", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          ...csrfHeaders(),
        },
        body: JSON.stringify({ date: activeDate, content }),
      });

      if (!res.ok) {
        const text = await res.text();
        alert(`登録に失敗しました。\nstatus=${res.status}\n${text}`);
        return;
      }

      const data = await res.json();

      // 달력 즉시 반영
      renderTodoToCell(data.date, data.content);

      // 오늘 날짜면 今日のやること도 즉시 반영
      if (data.date === todayIso()) {
        renderTodayBox(data.content);
      }

      modal.classList.add("hidden");
      activeDate = null;
    } catch (err) {
      console.error("POST error:", err);
      alert("通信エラーです。");
    }
  });
});
