const paraEl = $("p");
function myFunction() {
  let textEl = $("#myInput").val().toUpperCase();
  let count = $("#myUL li").length;
  $("a").each(function (index, value) {
    if (!value.text.toUpperCase().includes(textEl)) {
      $(this).hide();
      count--;
    }
  });
  $("a").each(function (index, value) {
    if (value.text.toUpperCase().includes(textEl)) {
      $(this).show();
    }
  });
  if (count === 0) {
    paraEl
      .html('Name: <span class="nameInput">' + textEl + "</span> not found")
      .css("color", "red");
  } else {
    paraEl.html("");
  }
}
function myToggleButton() {
  $(".dropdown-content").toggleClass("show");
  $("#myInput").focus();
  $("#myInput").val("");
  myFunction();
}
$("a").on("click", function () {
  const valueEl = $(this).attr("value");
  paraEl.text($(this).text() + " : phone " + valueEl);
  $("#myInput").focus();
});
$(window).on("click", function (e) {
  if (!e.target.matches("a")) {
    paraEl.text("");
    $("#myInput").focus();
  }
});
