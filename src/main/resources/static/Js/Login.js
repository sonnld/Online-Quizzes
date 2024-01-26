const log_in_btn = document.querySelector("#log-in-btn");
const sign_up_btn = document.querySelector("#sign-up-btn");
const container = document.querySelector(".container");

sign_up_btn.addEventListener("click", () => {
  container.classList.add("sign-up-mode");
  document.title = "Sign up"
});

log_in_btn.addEventListener("click", () => {
  container.classList.remove("sign-up-mode");
  document.title = "Log in"
});
