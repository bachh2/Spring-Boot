let tasks = getFromLocalStorage();
const li = document.createElement("li");
const btnAdd = document.querySelector(".btn-add");
const btnUpdate = document.querySelector(".btn-update");
const taskNameEl = document.querySelector(".form-control");
const summaryEl = document.querySelector(".summary-text");
const statusAddBtn = document.querySelector(".btn-add");

taskNameEl.focus();

renderTask(tasks);

updateSummary();

function getFromLocalStorage() {
  return localStorage.getItem("tasks")
    ? JSON.parse(localStorage.getItem("tasks"))
    : [];
}

btnAdd.addEventListener("click", function (e) {
  if (!taskNameEl.value) {
    alert("Please add a task");
    return false;
  }
  let tasks = getFromLocalStorage();
  const newTask = {
    name: taskNameEl.value,
  };
  tasks.push(newTask);

  taskNameEl.value = "";
  taskNameEl.focus();

  localStorage.setItem("tasks", JSON.stringify(tasks));
  renderTask(tasks);
});

function renderTask(tasks = []) {
  let content = `<ul class="task-list list-group">`;
  tasks.forEach((task, index) => {
    content += `<li class="list-group-item bg-light text-dark border border-light mb-4">${task.name}
            <button onclick="editTask(${index})" class="btn btn-primary position-absolute top-0 btn-edit h-100"><i class="bi bi-pencil-square"></i></button>
            <button onclick="deleteTask(${index})"class="btn btn-danger position-absolute top-0 end-0 h-100 btn-delete "><i class="bi bi-trash"></i></button>
            </li>`;
  });
  content += `</ul>`;
  document.querySelector(".result").innerHTML = content;
}

function updateSummary() {
  let tasks = getFromLocalStorage();
  const text =
    tasks.length === 0
      ? `<mark>No task , Add one :D</mark>`
      : `  You have <span class="number fw-bold">${tasks.length}</span> pending tasks
              <button onclick="clearAll()" class="btn btn-clear btn-danger">clearAll</button>`;
  summaryEl.innerHTML = text;
}
function clearAll() {
  if (confirm("Are you sure you want to DELETE ALL tasks???")) {
    let tasks = getFromLocalStorage();
    tasks.length === 0;
    localStorage.removeItem("tasks");
    renderTask();
    updateSummary();
  }
  return false;
}

function editTask(id = 0) {
  let tasks = getFromLocalStorage();
  if (tasks.length > 0) {
    taskNameEl.value = tasks[id].name;
    taskNameEl.focus();
    btnAdd.hidden = true;
    statusAddBtn.disabled = true;
    btnUpdate.hidden = !btnAdd.hidden;
  }
  btnUpdate.addEventListener("click", function () {
    if (confirm("Are you sure you want to edit this task?")) {
      tasks[id] = { name: taskNameEl.value };
      taskNameEl.focus();
      localStorage.setItem("tasks", JSON.stringify(tasks));
      renderTask(tasks);
      btnAdd.hidden = false;
      btnUpdate.hidden = true;
      statusAddBtn.disabled = false;
    } else {
      return false;
    }
  });
}

function deleteTask(id = 0) {
  if (confirm("Are you sure you want delete this task")) {
    let tasks = getFromLocalStorage();
    tasks.splice(id, 1);
    localStorage.setItem("tasks", JSON.stringify(tasks));
    renderTask(tasks);
    updateSummary();
  }
}
