function TaskList() {
}
TaskList.list = new Array();// 任务列表存储数组

TaskList.addTask = function (taskModel) {
    TaskList.list.push(taskModel);
};

TaskList.removeTaskByIndex = function (index) {
    TaskList.list.remove(index);
};

TaskList.updateTaskByIndex = function (index, newTaskModel) {
    TaskList.list[index] = newTaskModel;
};

TaskList.setSuccessByIndex = function (index) {
    TaskList.list[index].state = TASK_STATE_SUCCESS;
};

TaskList.setErrorByIndex = function (index) {
    TaskList.list[index].state = TASK_STATE_ERROR;
};

TaskList.setWaitingByIndex = function (index) {
    TaskList.list[index].state = TASK_STATE_WAITING;
};

TaskList.setRunningByIndex = function (index) {
    TaskList.list[index].state = TASK_STATE_RUNNING;
};

TaskList.taskCount = function () {
    return TaskList.list.length;
};

TaskList.getByIndex = function (index) {
    return TaskList.list[index];
};

TaskList.refreshDisplay = function () {
    var rowStateClassArr = ["active", "info", "success", "danger", "warning"];
    var rowStateNameArr = ["待运行", "执行中", "成功", "失败", "有错误"];
    var listInnerHTML = "";
    for (var index = 0; index < TaskList.taskCount(); index++) {
        var task = TaskList.getByIndex(index);
        console.log("TASK JSON:" + task);
        listInnerHTML += "<tr class='" + rowStateClassArr[task.state] + "'><td>" + (index + 1) + "</td><td>" + task.name + "</td><td>" + rowStateNameArr[task.state] + "</td></tr>";
    }
    document.getElementById("taskListContentBody").innerHTML = listInnerHTML;
};