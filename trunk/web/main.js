var startMessage = null;
var dropClicked = false;
var currentMenu = null;
var closedItemImageUrl = null;
var openedItemImageUrl = null;

window.onload = function() {
	var menus = document.getElementById("header").getElementsByClassName("drop");
	for(var index = 0, size = menus.length; index < size; index++) {
		menus[index].style.display = "none";
		menus[index].parentNode.getElementsByTagName("A")[0].onclick = dropMenu;
	}
	var lists = document.getElementById("page").getElementsByClassName("drop");
	for(var index = 0, size = lists.length; index < size; index++) {
		var element = lists[index];
		if(element.tagName == "UL" || element.tagName == "DIV") {
			var parent = element.parentNode;
			var child = document.createElement("IMG");
			child.className = "arrow";
			child.onclick = dropList;
			child.style.cursor = "pointer";
			child.style.marginRight = "10px";
			child.src = closedItemImageUrl;
			parent.insertBefore(child, parent.firstChild);
			element.style.display = "none";
		}
	}
	if(startMessage !== null) {
		show(startMessage);
	}
}

window.onclick = function() {
	if(currentMenu != null) {
		if(!dropClicked) {
			currentMenu.style.display = "none";
			currentMenu = null;
		} else {
			dropClicked = false;
		}
	}
}

function show(message, action) {
	showMessage(message, [{
		caption: "Закрыть",
		handler: (action !== undefined ? action : function() {})
	}]);
}

function confirmation(form, message) {
	showMessage(message, [{
		caption: "Удалить",
		handler: function() {
			form.submit();
		}
	}, {
		caption: "Отменить",
		handler: function() {}
	}]);
	return false;
}

function submitFormById(id) {
	var form = document.getElementById(id);
	var isSubmit = true;
	if(form.onsubmit != null) {
		isSubmit = form.onsubmit();
	}
	if(isSubmit) {
		form.submit();
	}
	return false;
}

function showMessage(message, buttons) {
	var body = document.getElementsByTagName("BODY")[0];
	var messageElement = document.createElement("DIV");
	messageElement.id = "confirm-message";
	var messageContent = document.createElement("DIV");
	var messageText = document.createElement("P");
	messageText.innerHTML = message;
	messageContent.appendChild(messageText);
	var buttonsElement = document.createElement("FORM");
	for(var index = 0, size = buttons.length; index < size; index++) {
		var button = document.createElement("BUTTON");
		button.type = "BUTTON";
		button.handler = buttons[index];
		button.onclick = function() {
			body.removeChild(messageElement);
			this.handler.handler();
		}
		button.appendChild(document.createTextNode(buttons[index].caption));
		buttonsElement.appendChild(button);
	}
	messageContent.appendChild(buttonsElement);
	messageElement.appendChild(messageContent);
	body.insertBefore(messageElement, body.firstChild);
}

function dropMenu(e) {
	var menu = e.currentTarget.parentNode.getElementsByClassName("drop")[0];
	if(menu.style.display === "none") {
		if(currentMenu != null && menu != currentMenu) {
			currentMenu.style.display = "none";
		}
		menu.style.display = "block";
		currentMenu = menu;
		dropClicked = true;
	}
	return false;
}

function dropList(e) {
	var parent = e.currentTarget.parentNode;
	var element = parent.getElementsByTagName("DIV")[0];
	if(element === undefined || parent != element.parentNode) {
		element = parent.getElementsByTagName("UL")[0];
	}
	if(element.style.display === "none") {
		element.style.display = "block";
		parent.getElementsByTagName("IMG")[0].src = openedItemImageUrl;
	} else {
		element.style.display = "none";
		parent.getElementsByTagName("IMG")[0].src = closedItemImageUrl;
	}
}
