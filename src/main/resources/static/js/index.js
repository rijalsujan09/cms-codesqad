const toggleButton = document.getElementById("toggle-button");
const toggleIcon = document.getElementById("toggle-icon");
const navSection = document.querySelector(".nav-section");
const navHide = document.querySelector(".nav-hide");
const topbar = document.getElementById("topbar");
const mainSection = document.querySelector(".main-section");

toggleButton.addEventListener("click", () => {
  navSection.classList.toggle("hidden");
  if (navSection.classList.contains("hidden")) {
    navHide.style.display = "none";
    mainSection.style.marginLeft = "0";
    mainSection.style.width = "100%";
    toggleButton.style.left = "0";
    toggleIcon.classList.remove("fa-chevron-left");
    toggleIcon.classList.add("fa-chevron-right");
  } else {
    navHide.style.display = "block";
    mainSection.style.marginLeft = "15%";
    toggleButton.style.left = "15%";
    toggleIcon.classList.remove("fa-chevron-right");
    toggleIcon.classList.add("fa-chevron-left");
  }
});

function toggleMenu(element) {
  const allSubMenus = document.querySelectorAll(".sub-menu");
  const allIcons = document.querySelectorAll(".toggle-icon");
  const subMenu = element.nextElementSibling;
  const icon = element.querySelector(".toggle-icon");

  allSubMenus.forEach((menu) => {
    if (menu !== subMenu) {
      menu.style.display = "none";
    }
  });

  allIcons.forEach((ic) => {
    if (ic !== icon) {
      ic.classList.remove("fa-chevron-up");
      ic.classList.add("fa-chevron-down");
    }
  });

  if (subMenu.style.display === "block") {
    subMenu.style.display = "none";
    icon.classList.remove("fa-chevron-up");
    icon.classList.add("fa-chevron-down");
  } else {
    subMenu.style.display = "block";
    icon.classList.remove("fa-chevron-down");
    icon.classList.add("fa-chevron-up");
  }

  const allNavItems = document.querySelectorAll(".nav-item");
  allNavItems.forEach((item) => item.classList.remove("active"));

  element.classList.add("active");
}

function setActive(element) {
  const allNavItems = document.querySelectorAll(".nav-item");
  allNavItems.forEach((item) => item.classList.remove("active"));

  element.classList.add("active");
}

function updateTime() {
  const localTimeElement = document.getElementById("localTime");
  const now = new Date();
  const localTime = now.toLocaleString("en-US");
  localTimeElement.textContent = localTime;
}
setInterval(updateTime, 1000);
updateTime();
